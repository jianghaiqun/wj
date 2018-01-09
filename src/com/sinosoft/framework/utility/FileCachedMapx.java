package com.sinosoft.framework.utility;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * 用文件缓存的Mapx,主要用于存储大量的URL及其内容<br>
 * 原理:<br>
 * 一个HashMap在容量为10和容量为100000时通过Key存取一个元素的性能基本相当，<br>
 * 因此可以在HashMap的基础上实现一个基于文件系统的FileMap。<br>
 * 实现步骤:<br>
 * 第一步，我们直接照抄HashMap中的散列算法: Java代码
 * 
 * <pre>
 * public static int hash(Object x, int length) {
 * 	int h = x.hashCode();
 * 	h += &tilde;(h &lt;&lt; 9);
 * 	h &circ;= (h &gt;&gt;&gt; 14);
 * 	h += (h &lt;&lt; 4);
 * 	h &circ;= (h &gt;&gt;&gt; 10);
 * 	return h &amp; (length - 1);
 * }
 * </pre>
 * 
 * 假设length等于10000，那么传入一组URL通过hash()算法返回的值将基本平均分布在这1到10000，<br>
 * 而不管这一组URL的具体内容到底是什么（URL之间要有差异，不能都相同或大部分相同）<br>
 * 这是整个实现最为关键的地方。在实际应用中length的值是随着容量增长而不变化的，<br>
 * 每次扩容后都需要将所有URL重新计算散列值，可以参考HashMap中的实现。<br>
 * 
 * 第二步：存放文件内容<br>
 * 实现存放内容的方法：假如现在需要存放一个URL和它的内容，<br>
 * 那么必须在value.dat的最后写入内容长度和内容本身（如果value.dat不存在，则需要先建立一个），<br>
 * 并且返回一个内容长度起始字节在value.dat中的起始地址。
 * 
 * 第三步：存放键值<br>
 * 实现存放键值的算法：得到内容的起始地址，计算[起始地址+URL]的长度，<br>
 * 将该长度和[起始地址+URL]写入键值辅助文件key.dat的最后（如果key.dat不存在，则需要先建立一个），<br>
 * 并且返回该长度起始字节在key.dat的地址。
 * 
 * 第四步：存放散列值与键值地址的对应关系<br>
 * 实现存放散列值与键值地址对应关系的算法：得到键值的起始地址后（地址长度为4字节,即为long类型的长度），<br>
 * 通过hash()计算URL的散列值，假设散列值为3000的话，<br>
 * 则将该地址写入地址辅助文件address.idx的第12000-12004个字节。<br>
 * 
 * 第五步：取URL内容<br>
 * 实现取URL内容的算法：假设URL已经存入FileMap,当需要通过URL取内容时，步骤如下：<br>
 * 通过hash()计算URL的散列值，通过散列值从address.idx中取键值在key.dat中的地址，<br>
 * 通过键值中内容在value.dat中的地址，即可取到URL对应的内容了。
 * 
 * 第六步：解决散列冲突<br>
 * hash()能将一组URL基本平均地分布在一块地址上，但不可避免地会出现散列冲突的情况，<br>
 * 即多个不同的URL获得同一个散列值的情况，这时候第一个存入的URL将直接写入address.idx中散列值对应的地址，<br>
 * 其他的URL存入时需要将本身的键值地址写入第一个URL在key.dat的记录的末尾，<br>
 * 以便存取时能够通过第一个URL找到其他散列值相同的URL，从面解决散列冲突的问题。
 * 
 * 以上六步是比较简洁的过程，实际中还需要解决value.dat过大的问题（有时操作系统对文件大小有限制，<br>
 * 必须形成value0.data,value1.data,value2.data等一组value文件，从而使得寻址进一步复杂），<br>
 * 解决重新散列的问题，解决压缩存取的问题。
 * 
 * 虽然存取一个URL使用了3个文件，但因address.idx和key.dat的体积都很小，使用时又都是直接定位，
 * 并且因频繁被使用被磁盘的Cache以及操作系统的Cache缓存，时间性能消耗是非常小的。
 * 
 */
public class FileCachedMapx {
	private static final Logger logger = LoggerFactory.getLogger(FileCachedMapx.class);

	private Mapx map;// 缓存Key,通过key取值

	private boolean compressible;

	private int total;// 所有可用位置数

	private int size;// 所有己用位置数

	private int modCount;// 所有己修改位置数

	private short maxFileIndex;

	private int maxItemInMemory = 1000;

	private String cacheDirectory;

	private BufferedRandomAccessFile[] addressFiles = null;

	private BufferedRandomAccessFile[] keyFiles = null;

	private BufferedRandomAccessFile[] valueFiles = null;

	private static final int AddressCountInOneFile = 268435456;// 1024*1024*256,每个文件中的允许的最key数量

	private static final int MaxFileSize = 2097152000;// 2097152000;

	private static final int DefaultCountInMemory = 100;

	private int addressFileCount;

	/**
	 * 创建一个实例，文件缓存存放在cacheDir中
	 * 
	 * @param cacheDir
	 */
	public FileCachedMapx(String cacheDir) {
		this(cacheDir, 65536, DefaultCountInMemory);
	}

	/**
	 * 创建一个实例，文件缓存经zip压缩后存放在cacheDir中
	 * 
	 * @param cacheDir
	 * @param compressiable
	 */
	public FileCachedMapx(String cacheDir, boolean compressiable) {
		this(cacheDir, 65536, compressiable, DefaultCountInMemory);
	}

	/**
	 * 创建一个实例，文件缓存存放在cacheDir中，默认创建initEntrySize个键地址
	 * 
	 * @param cacheDir
	 * @param initEntrySize
	 */
	public FileCachedMapx(String cacheDir, int initEntrySize) {
		this(cacheDir, initEntrySize, true, DefaultCountInMemory);
	}

	/**
	 * 创建一个实例，文件缓存经zip压缩后存放在cacheDir中，并且使用内存缓存
	 * 
	 * @param cacheDir
	 * @param compressiable
	 * @param maxItemInMemory
	 */
	public FileCachedMapx(String cacheDir, boolean compressiable, int maxItemInMemory) {
		this(cacheDir, 65536, compressiable, maxItemInMemory);
	}

	/**
	 * 创建一个实例，文件缓存存放在cacheDir中，默认创建initEntrySize个键地址，并且使用内存缓存
	 * 
	 * @param cacheDir
	 * @param initEntrySize
	 * @param maxItemInMemory
	 */
	public FileCachedMapx(String cacheDir, int initEntrySize, int maxItemInMemory) {
		this(cacheDir, initEntrySize, true, maxItemInMemory);
	}

	/**
	 * 创建一个实例，文件缓存经zip压缩后存放在cacheDir中，默认创建initEntrySize个键地址，并且使用内存缓存
	 * 
	 * @param cacheDir
	 * @param initEntrySize
	 * @param compressiable
	 * @param maxItemInMemory
	 */
	public FileCachedMapx(String cacheDir, int initEntrySize, boolean compressiable, int maxItemInMemory) {
		this.cacheDirectory = cacheDir;
		this.total = new Double(Math.pow(2, Math.ceil(Math.log(initEntrySize) / Math.log(2)))).intValue();
		this.compressible = compressiable;
		this.maxItemInMemory = maxItemInMemory;
		map = new Mapx(maxItemInMemory);
		loadInfo();
	}

	/**
	 * 检查键文件、地址文件和内容文件，如果没有则创建之
	 */
	private void initFiles() {
		try {
			// 键索引数据流
			addressFileCount = new Double(Math.ceil(total * 1.0 / AddressCountInOneFile)).intValue();
			addressFiles = new BufferedRandomAccessFile[addressFileCount];
			int prefix = new Double(Math.log(total / 16) / Math.log(2)).intValue();
			for (int i = 0; i < addressFileCount; i++) {
				addressFiles[i] = new BufferedRandomAccessFile(cacheDirectory + prefix + "key" + i + ".idx", "rw");
				if (i == addressFileCount - 1) {
					addressFiles[i].setLength((total - (addressFileCount - 1) * AddressCountInOneFile) * 9);
				}
			}
			keyFiles = new BufferedRandomAccessFile[maxFileIndex + 1];
			valueFiles = new BufferedRandomAccessFile[maxFileIndex + 1];
			for (int i = 0; i <= maxFileIndex; i++) {
				keyFiles[i] = new BufferedRandomAccessFile(cacheDirectory + "key" + i + ".dat", "rw");
				valueFiles[i] = new BufferedRandomAccessFile(cacheDirectory + "value" + i + ".dat", "rw");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 将键值对写入文件
	 * 
	 * @param k
	 * @param v
	 */
	private void writeData(String k, Object v) {
		int index = hash(k);
		int c = index % AddressCountInOneFile;// 在文件中的位置
		index = index / AddressCountInOneFile;// 在第几个文件中
		if (addressFiles == null) {
			initFiles();
		}
		try {
			BufferedRandomAccessFile f = addressFiles[index];
			f.seek(c * 9);
			Key key = getKey(f);
			if (key == null) {// 未被占用过
				// 写key.idx
				f.seek(c * 9);
				writeFile(f, k, v);
			} else {
				if (key.KeyString.equals(k)) {
					updateByKey(key, v);
					return;
				}
				// 处理键冲突,冲突的键不在key.idx中，而在key.dat中
				while (true) {
					f = keyFiles[key.KeyFileIndex];
					int pos = key.keyAddress + key.KeyLength - 9;
					f.seek(pos);
					key = getKey(f);
					if (key == null) {// 新增的冲突键
						// 写前一个键的后9位
						f.seek(pos);
						writeFile(f, k, v);
						break;
					} else {
						if (key.KeyString.equals(k)) {
							updateByKey(key, v);
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 将键值对写入指定文件
	 * 
	 * @param f
	 * @param k
	 * @param v
	 * @throws IOException
	 */
	private void writeFile(BufferedRandomAccessFile f, String k, Object v) throws IOException {
		f.writeByte(1);// 标明己被占用
		f.writeShort(maxFileIndex);

		byte[] bk = k.getBytes();
		byte[] bv = toBytes(v);// 数据内容

		f.writeShort(22 + bk.length);
		BufferedRandomAccessFile kf = keyFiles[maxFileIndex];
		BufferedRandomAccessFile vf = valueFiles[maxFileIndex];
		int kpos = (int) kf.length();
		// 判断是否需要新建key.dat文件
		long kNewSize = kpos + 22 + bk.length;
		if (kNewSize > MaxFileSize) {// 2^31-1
			maxFileIndex++;
			BufferedRandomAccessFile fk = new BufferedRandomAccessFile(cacheDirectory + "key" + maxFileIndex + ".dat",
					"rw");
			BufferedRandomAccessFile fv = new BufferedRandomAccessFile(
					cacheDirectory + "value" + maxFileIndex + ".dat", "rw");
			keyFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(keyFiles, fk);
			valueFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(valueFiles, fv);
			kf = keyFiles[maxFileIndex];
			kNewSize = 22 + bk.length;
			kpos = 0;
		}
		// 判断是否需要新建value.dat文件
		int vpos = (int) vf.length();
		int vNewSize = vpos + 9 + bk.length + bv.length;
		if (vNewSize > MaxFileSize) {// 2^31-2
			maxFileIndex++;
			BufferedRandomAccessFile fk = new BufferedRandomAccessFile(cacheDirectory + "key" + maxFileIndex + ".dat",
					"rw");
			BufferedRandomAccessFile fv = new BufferedRandomAccessFile(
					cacheDirectory + "value" + maxFileIndex + ".dat", "rw");
			keyFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(keyFiles, fk);
			valueFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(valueFiles, fv);
			vf = valueFiles[maxFileIndex];
			vNewSize = 9 + bk.length + bv.length;
			vpos = 0;
		}
		f.writeInt(kpos);// 地址

		// 写key.dat
		kf.setLength(kNewSize);
		kf.seek(kpos);
		kf.writeByte(1);// 标识是否被删除
		kf.writeShort(maxFileIndex);// 哪个数据文件

		kf.writeInt(9 + bk.length + bv.length);
		kf.writeInt(vpos);// 在数据文件中的位置
		kf.writeShort(bk.length);
		kf.write(bk);
		// 9个字节的冲突键链表为空,不做操作

		// 写入value.dat
		vf.setLength(vNewSize);
		vf.seek(vpos);
		vf.writeByte(1);// 标识是否被删除
		vf.writeInt(bk.length);// 键长度
		vf.writeInt(bv.length);// 数据长度
		vf.write(bk);
		vf.write(bv);
	}

	/**
	 * 更新键的值
	 * 
	 * @param key
	 * @param v
	 * @throws IOException
	 */
	private void updateByKey(Key key, Object v) throws IOException {
		byte[] bv = toBytes(v);// 数据内容
		BufferedRandomAccessFile f = valueFiles[key.DataFileIndex];
		int pos = (int) f.length();
		int newDataLength = key.KeyLength - 13 + bv.length;
		if (newDataLength > key.DataLength) {// 需要重新开辟空间
			if (key.DataFileIndex < maxFileIndex) {// 说明己写满
				key.DataFileIndex = maxFileIndex;
				f = valueFiles[maxFileIndex];
				pos = (int) f.length();
			}
			// 判断是否需要新建value.dat文件
			int newSize = pos + newDataLength;
			if (newSize > MaxFileSize) {// 2^31-2
				// 旧文件里的数据标记为删除
				f.seek(key.DataAddress);
				f.writeByte(0);
				maxFileIndex++;
				BufferedRandomAccessFile fk = new BufferedRandomAccessFile(cacheDirectory + "key" + maxFileIndex
						+ ".dat", "rw");
				BufferedRandomAccessFile fv = new BufferedRandomAccessFile(cacheDirectory + "value" + maxFileIndex
						+ ".dat", "rw");
				keyFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(keyFiles, fk);
				valueFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(valueFiles, fv);
				f = valueFiles[maxFileIndex];
				key.DataFileIndex = maxFileIndex;
				pos = 0;
				f.setLength(newDataLength);
			} else {
				pos = (int) f.length();
				f.setLength(newSize);
			}
			// 重置key.dat
			f = keyFiles[key.KeyFileIndex];
			f.seek(key.keyAddress + 1);
			f.writeShort(maxFileIndex);// 哪个数据文件
			f.writeInt(newDataLength);
			f.writeInt(pos);// 在数据文件中的位置
			modCount++;
		} else {// 不需要重新开辟空间
			f = keyFiles[key.KeyFileIndex];
			f.seek(key.keyAddress + 3);
			f.writeInt(newDataLength);// 重置数据长度
			pos = key.DataAddress;
		}

		// 写入数据内容
		f = valueFiles[key.DataFileIndex];
		f.seek(pos);
		f.writeByte(1);// 标识是否被删除
		byte[] bk = key.KeyString.getBytes();
		f.writeInt(bk.length);// 键长度
		f.writeInt(bv.length);// 数据长度
		f.write(bk);
		f.write(bv);
	}

	/**
	 * 从键文件的当前位置读取一个键
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	private Key getKey(BufferedRandomAccessFile f) throws IOException {
		if (f.length() == 0 || f.readByte() == 0) {
			return null;// 没有此键
		}
		Key key = new Key();
		key.KeyFileIndex = f.readShort();// 第几个键文件中
		key.KeyLength = f.readShort();// 键长度
		key.keyAddress = f.readInt();// 键地址

		f = keyFiles[key.KeyFileIndex];
		f.seek(key.keyAddress);
		if (f.readByte() == 0) {// 第一个byte表示是否己删除
			return null;
		}

		key.DataFileIndex = f.readShort();// 在第几个数据文件中
		key.DataLength = f.readInt();// 数据长度
		key.DataAddress = f.readInt();// 数据地址
		f.readShort();// 键字符串长度，此处不需要再读
		byte[] bs = null;
		try {
			bs = new byte[key.KeyLength - 22];
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		f.read(bs);
		key.KeyString = new String(bs);
		return key;
	}

	/**
	 * 从指定index的键文件读取指定键的信息
	 * 
	 * @param index
	 * @param k
	 * @return
	 */
	private Key readKey(int index, String k) {
		int c = index % AddressCountInOneFile;// 在文件中的位置
		index = index / AddressCountInOneFile;
		if (addressFiles == null) {
			initFiles();
		}
		try {
			BufferedRandomAccessFile f = addressFiles[index];
			f.seek(c * 9);
			Key key = getKey(f);
			if (key == null || key.KeyString.equals(k)) {
				return key;
			}
			// 处理键冲突,冲突的键不在key.idx中，而在key.dat中
			while (true) {
				f = keyFiles[key.KeyFileIndex];
				int pos = key.keyAddress + key.KeyLength - 9;
				f.seek(pos);
				key = getKey(f);
				if (key == null || key.KeyString.equals(k)) {
					return key;
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 按指定键获取数据
	 * 
	 * @param k
	 * @return
	 * @throws IOException
	 */
	private Object readData(String k) throws IOException {
		int i = hash(k);
		Key key = readKey(i, k);
		if (key == null) {
			return null;
		}
		BufferedRandomAccessFile f = valueFiles[key.DataFileIndex];
		f.seek(key.DataAddress + 9 + key.KeyLength - 22);
		byte[] bv = new byte[key.DataLength - 9 - key.KeyLength + 22];
		f.read(bv);
		return toObject(bv);
	}

	/**
	 * 转换二进制数组为对象
	 * 
	 * @param bs
	 * @return
	 */
	private Object toObject(byte[] bs) {
		if (bs == null || bs.length == 0) {
			return null;
		}
		if (compressible) {
			try {
				bs = ZipUtil.unzip(bs);
			} catch (Exception e) {
				return null;
			}
		}
		if (bs.length == 0) {
			return null;
		}
		byte type = bs[0];
		bs = ArrayUtils.subarray(bs, 1, bs.length);
		if (type == 1) {
			return new Integer(NumberUtil.toInt(bs, 0));
		}
		if (type == 2) {
			return new Long(NumberUtil.toLong(bs));
		}
		if (type == 3) {
			return new String(bs);
		}
		if (type == 4) {
			return bs;
		}
		if (type == 5) {
			int[] arr = new int[bs.length / 4];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = NumberUtil.toInt(bs, i * 4);
			}
			return arr;
		}
		if (type == 6) {
			long[] arr = new long[bs.length / 8];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = NumberUtil.toInt(bs, i * 8);
			}
			return arr;
		}
		if (type == 7) {
			ByteBuffer bb = ByteBuffer.allocate(bs.length);
			bb.put(bs);
			bb.flip();
			ArrayList arr = new ArrayList();
			int index = 0;
			while (true) {
				int length = bb.getInt();
				byte[] t = new byte[length];
				bb.get(t);
				arr.add(new String(t));
				index += 4 + length;
				if (index == bs.length) {
					break;
				}
			}
			String[] r = new String[arr.size()];
			for (int i = 0; i < r.length; i++) {
				r[i] = (String) arr.get(i);
			}
			return r;
		}
		if (type == 8) {
			return FileUtil.unserialize(bs);
		}
		return null;
	}

	/**
	 * 将对象转化为二进制数组
	 * 
	 * @param v
	 * @return
	 */
	private byte[] toBytes(Object v) {
		byte type = 0;
		byte[] bs = null;
		if (v instanceof Integer) {
			type = 1;
			bs = NumberUtil.toBytes(((Integer) v).intValue());
		} else if (v instanceof Long) {
			type = 2;
			bs = NumberUtil.toBytes(((Long) v).longValue());
		} else if (v instanceof String) {
			type = 3;
			bs = ((String) v).getBytes();
		} else if (v instanceof byte[]) {
			type = 4;
			bs = (byte[]) v;
		} else if (v instanceof int[]) {
			type = 5;
			int[] arr = (int[]) v;
			bs = new byte[4 * arr.length];
			for (int i = 0; i < arr.length; i++) {
				NumberUtil.toBytes(arr[i], bs, i * 4);
			}
		} else if (v instanceof long[]) {
			type = 6;
			long[] arr = (long[]) v;
			bs = new byte[8 * arr.length];
			for (int i = 0; i < arr.length; i++) {
				NumberUtil.toBytes(arr[i], bs, i * 8);
			}
		} else if (v instanceof String[]) {
			type = 7;
			String[] arr = (String[]) v;
			bs = new byte[0];
			for (int i = 0; i < arr.length; i++) {
				byte[] b = null;
				b = arr[i].getBytes();
				bs = ArrayUtils.addAll(bs, NumberUtil.toBytes(b.length));
				bs = ArrayUtils.addAll(bs, b);
			}
		} else if (v instanceof Serializable) {
			type = 8;
			bs = FileUtil.serialize((Serializable) v);
		}
		if (compressible) {
			return ZipUtil.zip(ArrayUtils.add(bs, 0, type));
		} else {
			return ArrayUtils.add(bs, 0, type);
		}
	}

	/**
	 * 载入Map的基础信息
	 */
	private synchronized void loadInfo() {
		File f = new File(cacheDirectory);
		if (!f.exists()) {
			f.mkdirs();
		}
		// 读取fcm.dat
		f = new File(cacheDirectory + "meta.dat");
		if (f.exists()) {
			byte[] bs = FileUtil.readByte(f);
			size = NumberUtil.toInt(bs, 0);
			total = NumberUtil.toInt(bs, 4);
			modCount = NumberUtil.toInt(bs, 8);
			maxFileIndex = NumberUtil.toShort(bs, 12);
			compressible = NumberUtil.toShort(bs, 14) == 1;
		} else {
			size = 0;
			// total = 65536;// 16;// 1048576;
			maxFileIndex = 0;
			modCount = 0;
		}
	}

	/**
	 * 保存基础信息
	 */
	public synchronized void save() {
		if (cacheDirectory != null) {
			File f = new File(cacheDirectory + "meta.dat");
			byte[] bs = new byte[16];
			NumberUtil.toBytes(size, bs, 0);// 1-4字节存放记录总数
			NumberUtil.toBytes(total, bs, 4);// 5-8字节存放可用位置数
			NumberUtil.toBytes(modCount, bs, 8);// 9-12字节存放自上次整理以来删除的记录数
			NumberUtil.toBytes(maxFileIndex, bs, 12);// 13-14字节存放索引文件序列数
			NumberUtil.toBytes((short) (compressible ? 1 : 0), bs, 14);// 15-16字节存放是压缩标志
			FileUtil.writeByte(f, bs);
		}
	}

	/**
	 * 关闭Map，并保存基础信息
	 */
	public synchronized void close() {
		save();
		if (keyFiles != null) {
			for (int i = 0; i < keyFiles.length; i++) {
				try {
					keyFiles[i].close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		if (addressFiles != null) {
			for (int i = 0; i < addressFiles.length; i++) {
				try {
					addressFiles[i].close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		if (valueFiles != null) {
			for (int i = 0; i < valueFiles.length; i++) {
				try {
					valueFiles[i].close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Map是否含有指定键
	 * 
	 * @param k
	 * @return
	 */
	public synchronized boolean containsKey(String k) {
		if (map.containsKey(k)) {
			return true;
		}
		int i = hash(k);
		if (readKey(i, k) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 获取第一个键值对
	 * 
	 * @return
	 */
	public synchronized Entry firstEntry() {
		return Entry.first(this);
	}

	/**
	 * 同步地将指定键值对存入Map
	 * 
	 * @param k
	 * @param value
	 */
	private synchronized void put2(String k, Object value) {
		Object o = null;
		if (maxItemInMemory != 0) {
			o = map.put(k, value);
		}
		try {
			if (o == null) {// 插入新值
				int i = hash(k);
				Key key = readKey(i, k);
				if (key == null) {
					writeData(k, value);
					size++;
					if (size > total * 0.75) {
						resize();
					}
				} else {
					writeData(k, value);
				}
			} else {// 覆盖原值
				writeData(k, value);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		save();
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, Serializable v) {
		put2(k, v);
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, int v) {
		put2(k, new Integer(v));
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, long v) {
		put2(k, new Long(v));
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, String v) {
		put2(k, v);
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, byte[] v) {
		put2(k, v);
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, int[] v) {
		put2(k, v);
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, long[] v) {
		put2(k, v);
	}

	/**
	 * 存入键值对
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, String[] v) {
		put2(k, v);
	}

	/**
	 * 获取键值，并转化为int
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		Object o = get(key);
		if (o == null) {
			return Integer.MIN_VALUE;
		}
		if (o instanceof Integer) {
			return ((Integer) o).intValue();
		}
		throw new RuntimeException("Key对应的数据不是指定类型!");
	}

	/**
	 * 获取键值，并转化为long
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		Object o = get(key);
		if (o == null) {
			return Long.MIN_VALUE;
		}
		if (o instanceof Long) {
			return ((Long) o).longValue();
		}
		throw new RuntimeException("Key对应的数据不是指定类型!");
	}

	/**
	 * 获取键值，并转换为String
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}
		throw new RuntimeException("Key对应的数据不是指定类型!");
	}

	/**
	 * 获取键值，并转换为byte[]
	 * 
	 * @param key
	 * @return
	 */
	public byte[] getByteArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof byte[]) {
			return (byte[]) o;
		}
		throw new RuntimeException("Key对应的数据不是指定类型:" + key);
	}

	/**
	 * 获取键值，并转换为int[]
	 * 
	 * @param key
	 * @return
	 */
	public int[] getIntArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof int[]) {
			return (int[]) o;
		}
		throw new RuntimeException("Key对应的数据不是指定类型!");
	}

	public long[] getLongArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof long[]) {
			return (long[]) o;
		}
		throw new RuntimeException("Key对应的数据不是指定类型!");
	}

	/**
	 * 获取键值，并转换为String[]
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof String[]) {
			return (String[]) o;
		}
		throw new RuntimeException("Key对应的数据不是指定类型!");
	}

	/**
	 * 扩充可用位置
	 */
	private synchronized void resize() throws IOException {
		if (size < total * 0.75) {// 两个线程同时调用
			return;
		}
		int total2 = total * 2;
		int fileCount = new Double(Math.ceil(total2 * 1.0 / AddressCountInOneFile)).intValue();
		BufferedRandomAccessFile[] files = new BufferedRandomAccessFile[fileCount];
		int prefix = new Double(Math.log(total2 / 16) / Math.log(2)).intValue();
		for (int i = 0; i < fileCount; i++) {
			files[i] = new BufferedRandomAccessFile(cacheDirectory + prefix + "key" + i + ".idx", "rw");
			if (i == addressFileCount - 1) {
				files[i].setLength((total2 - (addressFileCount - 1) * AddressCountInOneFile) * 9);
			} else {
				files[i].setLength(AddressCountInOneFile * 9);
			}
		}
		// key.idx所有位置必须全部重新排列
		byte[] empty = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < keyFiles.length; i++) {
			BufferedRandomAccessFile f = keyFiles[i];
			int pos = 0;
			while (f.length() > pos) {
				f.seek(pos);
				byte deleted = f.readByte();
				f.skipBytes(10);
				short len = f.readShort();
				// System.out.println(len);
				if (deleted == 1) {// =0时己删除，需要滤过
					byte[] bs = new byte[len];
					f.read(bs);
					f.seek(pos + 13 + len);
					f.write(empty);// 最后9位需重置
					String k = new String(bs);
					int index = hash(k, total2);
					int c = index % AddressCountInOneFile;// 在文件中的位置
					index = index / AddressCountInOneFile;
					BufferedRandomAccessFile af = files[index];
					af.seek(c * 9);
					if (af.readByte() == 0) {
						af.seek(c * 9);
						af.writeByte(1);
						af.writeShort(i);
						af.writeShort(22 + bs.length);
						af.writeInt(pos);
					} else {// 己被占用（表明本键是冲突键
						af.seek(c * 9);
						Key key = getKey(af);
						if (key == null) {// 未被占用过
							throw new RuntimeException("发生致命错误，应当有Key的位置未找到Key.");
						} else {
							// 处理键冲突
							while (true) {
								BufferedRandomAccessFile kf = keyFiles[key.KeyFileIndex];
								int pos2 = key.keyAddress + key.KeyLength - 9;
								kf.seek(pos2);
								key = getKey(kf);
								if (key == null) {// 新增的冲突键
									// 写前一个键的后9位
									kf.seek(pos2);
									kf.writeByte(1);
									kf.writeShort(i);
									kf.writeShort(22 + bs.length);
									kf.writeInt(pos);
									break;
								}
							}
						}
					}
				}
				pos += len + 22;

			}
		}
		BufferedRandomAccessFile[] tmp = addressFiles;
		addressFiles = files;
		total = total2;
		for (int i = 0; i < tmp.length; i++) {
			tmp[i].delete();
		}
	}

	/**
	 * 获取键值，返回可序列化的对象
	 * 
	 * @param k
	 * @return
	 */
	public synchronized Serializable get(String k) {
		Object o = null;
		if (maxItemInMemory != 0) {
			o = map.get(k);
		}
		if (o != null) {
			return (Serializable) o;
		}
		try {
			return (Serializable) readData(k);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 删除键值对
	 * 
	 * @param k
	 * @return
	 */
	public synchronized boolean remove(String k) {
		map.remove(k);
		int index = hash(k);
		int c = index % AddressCountInOneFile;// 在文件中的位置
		index = index / AddressCountInOneFile;// 在第几个文件中
		try {
			if (addressFiles == null) {
				initFiles();
			}
			BufferedRandomAccessFile f = addressFiles[index];
			f.seek(c * 9);
			Key key = getKey(f);
			if (key == null) {
				return false;
			}
			if (key.KeyString.equals(k)) {// 需要检查是否有冲突的键
				f = keyFiles[key.KeyFileIndex];
				int pos = key.keyAddress + key.KeyLength - 9;
				f.seek(pos);
				if (f.readByte() == 0) {// 没有冲突键，直接更写key.idx
					f = addressFiles[index];
					f.seek(c * 9);
					f.writeByte(0);
				} else {// 有冲突键，需要复制最后9位到key.idx
					f.seek(pos);
					byte[] bs = new byte[9];
					f.read(bs);
					f = addressFiles[index];
					f.seek(c * 9);
					f.write(bs);
				}
				f = keyFiles[key.KeyFileIndex];
				f.seek(key.keyAddress);
				f.writeByte(0);
				// 写入数据内容
				f = valueFiles[key.DataFileIndex];
				f.seek(key.DataAddress);
				f.writeByte(0);// 标识己被删除
				size--;
				modCount++;
				save();
				return true;
			}
			// 处理键冲突,冲突的键不在key.idx中，而在key.dat中
			while (true) {
				int index2 = key.KeyFileIndex;
				f = keyFiles[index2];
				int pos2 = key.keyAddress + key.KeyLength - 9;
				f.seek(pos2);
				key = getKey(f);
				if (key == null) {
					return false;
				}
				if (key.KeyString.equals(k)) {// 检查后面是否还有冲突的键
					f = keyFiles[key.KeyFileIndex];
					f.seek(key.keyAddress);
					f.writeByte(0);
					int pos = key.keyAddress + key.KeyLength - 9;
					f.seek(pos);
					if (f.readByte() == 1) {// =0时没有冲突键，直接置第一个字节为0
						// 有冲突键，需要复制最后9位到上一个键的最后9位
						f.seek(pos);
						byte[] bs = new byte[9];
						f.read(bs);
						f = keyFiles[index2];
						f.seek(pos2);
						f.write(bs);
					}
					// 写入数据内容
					f = valueFiles[key.DataFileIndex];
					f.seek(key.DataAddress);
					f.writeByte(0);// 标识己被删除
					size--;
					modCount++;
					save();
					return true;
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 检查modCount是否己达到临界值,如果己达到，则重新组织文件
	 */
	public synchronized void refactor() {
		if (total * 0.5 > modCount) {
			return;
		}
		// todo:重新组织文件,未实现
	}

	/**
	 * 根据key计算key地址在key.idx中的位置。算法移自HashMap.hash();
	 */
	private int hash(Object x) {
		return hash(x, total);
	}

	/**
	 * hash算法，移自HashMap类
	 * 
	 * @param x
	 * @param length
	 * @return
	 */
	public static int hash(Object x, int length) {
		int h = x.hashCode();
		h += ~(h << 9);
		h ^= (h >>> 14);
		h += (h << 4);
		h ^= (h >>> 10);
		return h & (length - 1);
	}

	/**
	 * 键值对总数
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * 获取Map缓存文件夹
	 * 
	 * @return
	 */
	public String getCacheDirectory() {
		return cacheDirectory;
	}

	/**
	 * 设置Map缓存文件夹
	 * 
	 * @param cacheDirectory
	 */
	public void setCacheDirectory(String cacheDirectory) {
		if (cacheDirectory.endsWith("/") || cacheDirectory.endsWith("\\")) {
			cacheDirectory += "/";
		}
		this.cacheDirectory = cacheDirectory;
	}

	public int getMaxItemInMemory() {
		return maxItemInMemory;
	}

	public void setMaxItemInMemory(int maxItemInMemory) {
		this.maxItemInMemory = maxItemInMemory;
	}

	public static class Key {
		short KeyFileIndex;

		short KeyLength;

		int keyAddress;

		String KeyString;

		short DataFileIndex;

		int DataLength;

		int DataAddress;
	}

	/**
	 * 表示一个键值对
	 * 
	 */
	public static class Entry {
		private FileCachedMapx fcm;

		private Key key;

		protected Entry(FileCachedMapx fcm, Key key) {
			this.fcm = fcm;
			this.key = key;
		}

		/**
		 * 获取键
		 * 
		 * @return
		 */
		public String getKey() {
			return key.KeyString;
		}

		/**
		 * 获取值
		 * 
		 * @return
		 */
		public Object getValue() {
			synchronized (fcm) {
				BufferedRandomAccessFile f = fcm.valueFiles[key.DataFileIndex];
				try {
					f.seek(key.DataAddress + 9 + key.KeyLength - 22);
					byte[] bv = new byte[key.DataLength - 9 - key.KeyLength + 22];
					f.read(bv);
					return fcm.toObject(bv);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			return null;
		}

		/**
		 * 返回Map的第一个键值对
		 * 
		 * @param fcm
		 * @return
		 */
		protected static Entry first(FileCachedMapx fcm) {
			synchronized (fcm) {
				try {
					if (fcm.addressFiles == null) {
						fcm.initFiles();
					}
					BufferedRandomAccessFile f = fcm.keyFiles[0];
					int pos = 0;
					Key key = new Key();
					while (f.length() > pos) {
						f.seek(pos);
						byte deleted = f.readByte();
						key.DataFileIndex = f.readShort();// 在第几个数据文件中
						key.DataLength = f.readInt();// 数据长度
						key.DataAddress = f.readInt();// 数据地址
						short len = f.readShort();
						key.KeyLength = (short) (len + 22);
						if (deleted == 1) {// =0时己删除，需要滤过
							byte[] bs = new byte[len];
							f.read(bs);
							key.KeyString = new String(bs);
							key.keyAddress = pos;
							return new Entry(fcm, key);
						}
						pos += len + 22;
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			return null;
		}

		/**
		 * 返回下一个键值对
		 * 
		 * @return
		 */
		public Entry next() {
			synchronized (fcm) {
				try {
					BufferedRandomAccessFile f = fcm.keyFiles[key.KeyFileIndex];
					int pos = key.keyAddress + key.KeyLength;
					Key nextKey = new Key();
					nextKey.KeyFileIndex = key.KeyFileIndex;
					if (pos == f.length()) {
						if (key.KeyFileIndex == fcm.maxFileIndex) {// 再也没有键了
							return null;
						}
						nextKey.KeyFileIndex++;
						nextKey.keyAddress = 0;
						f = fcm.keyFiles[nextKey.KeyFileIndex];
						pos = 0;
					}
					nextKey.keyAddress = pos;
					while (f.length() > pos) {
						f.seek(pos);
						byte deleted = f.readByte();
						nextKey.DataFileIndex = f.readShort();// 在第几个数据文件中
						nextKey.DataLength = f.readInt();// 数据长度
						nextKey.DataAddress = f.readInt();// 数据地址
						short len = f.readShort();
						if (deleted == 1) {// =0时己删除，需要滤过
							byte[] bs = new byte[len];
							f.read(bs);
							nextKey.KeyString = new String(bs);
							nextKey.KeyLength = (short) (len + 22);
							nextKey.keyAddress = pos;
							return new Entry(fcm, nextKey);
						}
						pos += len + 22;
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			return null;
		}
	}

	/**
	 * 键值内容是否被压缩
	 * 
	 * @return
	 */
	public boolean isCompressible() {
		return compressible;
	}
}
