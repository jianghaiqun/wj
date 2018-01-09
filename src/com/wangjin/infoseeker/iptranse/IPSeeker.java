package com.wangjin.infoseeker.iptranse;

import com.sinosoft.framework.Config;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPSeeker {
	private static final Logger logger = LoggerFactory.getLogger(IPSeeker.class);
	// 纯真 IP 数据库名
	private String IP_FILE = "QQWry.Dat";
	// 保存的文件夹
	private String INSTALL_DIR = System.getProperty("user.dir")
			+ "\\WebContent";
	// 一些固定常量，比如记录长度等等
	private static final int IP_RECORD_LENGTH = 7;
	private static final byte REDIRECT_MODE_1 = 0x01;
	private static final byte REDIRECT_MODE_2 = 0x02;
	// 用来做为 cache，查询一个 ip 时首先查看 cache，以减少不必要的重 复查找
	private Map<String, IPLocation> ipCache;
	// 随机文件访问类
	private RandomAccessFile ipFile;
	// 内存映射文件
	private MappedByteBuffer mbb;
	// 起始地区的开始和结束的绝对偏移
	private long ipBegin, ipEnd;
	// 为提高效率而采用的临时变量
	private IPLocation loc;
	private byte[] buf;
	private byte[] b4;
	private byte[] b3;

	public IPSeeker() {
		this.INSTALL_DIR = FilenameUtils.concat(Config.getContextRealPath(),
				"Tools");
		this.IP_FILE = "QQWry.Dat";
		ipCache = new HashMap<String, IPLocation>();
		loc = new IPLocation();
		buf = new byte[100];
		b4 = new byte[4];
		b3 = new byte[3];
		try {
			ipFile = new RandomAccessFile(FilenameUtils.concat(FilenameUtils.concat(Config.getContextRealPath(),
					"Tools"),"qqwry.dat"), "r");
			if (ipFile != null) {
				try {
					ipBegin = readLong4(0);

					ipEnd = readLong4(4);
					if (ipBegin == -1 || ipEnd == -1) {
						ipFile.close();
						ipFile = null;
					}
				} catch (IOException e) {
					logger.error("IP 地址信息文件格式有错误，IP 显示功能将无法 使用:{}", e.getMessage());
					ipFile = null;
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("IP 地址信息文件未找到，IP 显示功能将无法 使用:{}", e.getMessage());
		}
		// 如果打开文件成功，读取文件头信息
		if (ipFile != null) {
			try {
				ipBegin = readLong4(0);

				ipEnd = readLong4(4);
				if (ipBegin == -1 || ipEnd == -1) {
					ipFile.close();
					ipFile = null;
				}
			} catch (IOException e) {
				logger.error("IP 地址信息文件格式有错误，IP 显示功能将无法 使用:{}", e.getMessage());
				ipFile = null;
			}
		}
	}

	public void closeFile(){
	    try {
		ipFile.close();
	    } catch (IOException e) {
		logger.error(e.getMessage(), e);
	    }
	}
	/**
	 * * 给定一个地点的不完全名字，得到一系列包含 s 子串的 IP 范围记录 * @param s 地点子串 * @return 包含 IPEntry
	 * 类型的 List
	 */
	public List getIPEntriesDebug(String s) {
		List<IPEntry> ret = new ArrayList<IPEntry>();
		long endOffset = ipEnd + 4;
		for (long offset = ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
			// 读取结束 IP 偏移
			long temp = readLong3(offset);
			// 如果 temp 不等于-1，读取 IP 的地点信息
			if (temp != -1) {
				IPLocation ipLoc = getIPLocation(temp);
				// 判断是否这个地点里面包含了 s 子串，如果包含了，添加这个 记录到 List 中，如果没有，继续
				if (ipLoc.getCountry().indexOf(s) != -1
						|| ipLoc.getArea().indexOf(s) != -1) {
					IPEntry entry = new IPEntry();
					entry.country = ipLoc.getCountry();
					entry.area = ipLoc.getArea();
					// 得到起始 IP
					readIP(offset - 4, b4);
					entry.beginIp = Util.getIpStringFromBytes(b4);
					// 得到结束 IP
					readIP(temp, b4);
					entry.endIp = Util.getIpStringFromBytes(b4);
					// 添加该记录 ret.add(entry);
				}
			}
		}
		return ret;
	}

	public IPLocation getIPLocation(String ip) {
		IPLocation location = new IPLocation();
		location.setArea(this.getArea(ip));
		location.setCountry(this.getCountry(ip));
		return location;
	}

	/**
	 * * 给定一个地点的不完全名字，得到一系列包含 s 子串的 IP 范围记录 * @param s 地点子串 * @return 包含 IPEntry
	 * 类型的 List
	 */
	public List<IPEntry> getIPEntries(String s) {
		List<IPEntry> ret = new ArrayList<IPEntry>();
		try {
			// 映射 IP 信息文件到内存中
			if (mbb == null) {
				FileChannel fc = ipFile.getChannel();
				mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, ipFile.length());
				mbb.order(ByteOrder.LITTLE_ENDIAN);
			}
			int endOffset = (int) ipEnd;
			for (int offset = (int) ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
				int temp = readInt3(offset);
				if (temp != -1) {
					IPLocation ipLoc = getIPLocation(temp);
					// 判断是否这个地点里面包含了 s 子串，如果包含了，添加这 个记录到 List 中，如果没有，继续
					if (ipLoc.getCountry().indexOf(s) != -1
							|| ipLoc.getArea().indexOf(s) != -1) {
						IPEntry entry = new IPEntry();
						entry.country = ipLoc.getCountry();
						entry.area = ipLoc.getArea();
						// 得到起始 IP
						readIP(offset - 4, b4);
						entry.beginIp = Util.getIpStringFromBytes(b4);
						// 得到结束 IP
						readIP(temp, b4);
						entry.endIp = Util.getIpStringFromBytes(b4);
						// 添加该记录
						ret.add(entry);
					}
				}
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return ret;
	}

	/** * 从内存映射文件的 offset 位置开始的 3 个字节读取一个 int * @param offset * @return */
	private int readInt3(int offset) {
		mbb.position(offset);
		return mbb.getInt() & 0x00FFFFFF;
	}

	/** * 从内存映射文件的当前位置开始的 3 个字节读取一个 int * @return */
	private int readInt3() {
		return mbb.getInt() & 0x00FFFFFF;
	}

	/** * 根据 IP 得到国家名 * @param ip ip 的字节数组形式 * @return 国家名字符串 */
	public String getCountry(byte[] ip) {
		// 检查 ip 地址文件是否正常
		if (ipFile == null)
			return Message.bad_ip_file;
		// 保存 ip，转换 ip 字节数组为字符串形式
		String ipStr = Util.getIpStringFromBytes(ip);
		// 先检查 cache 中是否已经包含有这个 ip 的结果， 没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation ipLoc = ipCache.get(ipStr);
			return ipLoc.getCountry();
		} else {
			IPLocation ipLoc = getIPLocation(ip);
			ipCache.put(ipStr, ipLoc.getCopy());
			return ipLoc.getCountry();
		}
	}

	/** * 根据 IP 得到国家名 * @param ip IP 的字符串形式 * @return 国家名字符串 */
	public String getCountry(String ip) {
		return getCountry(Util.getIpByteArrayFromString(ip));
	}

	/** * 根据 IP 得到地区名 * @param ip ip 的字节数组形式 * @return 地区名字符串 */
	public String getArea(byte[] ip) {
		// 检查 ip 地址文件是否正常
		if (ipFile == null)
			return Message.bad_ip_file;
		// 保存 ip，转换 ip 字节数组为字符串形式
		String ipStr = Util.getIpStringFromBytes(ip);
		// 先检查 cache 中是否已经包含有这个 ip 的结果， 没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation ipLoc = ipCache.get(ipStr);
			return ipLoc.getArea();
		} else {
			IPLocation ipLoc = getIPLocation(ip);
			ipCache.put(ipStr, ipLoc.getCopy());
			return ipLoc.getArea();
		}
	}

	/** * 根据 IP 得到地区名 * @param ip IP 的字符串形式 * @return 地区名字符串 */
	public String getArea(String ip) {
		return getArea(Util.getIpByteArrayFromString(ip));
	}

	/**
	 * * 根据 ip 搜索 ip 信息文件，得到 IPLocation 结构，所搜索的 ip 参数从 类成员 ip 中得到 * @param ip
	 * 要查询的 IP * @return IPLocation 结构
	 */
	private IPLocation getIPLocation(byte[] ip) {
		IPLocation info = null;
		long offset = locateIP(ip);
		if (offset != -1)
			info = getIPLocation(offset);
		if (info == null) {
			info = new IPLocation();
			info.setCountry(Message.unknown_country);
			info.setArea(Message.unknown_area);
		}
		return info;
	}

	/**
	 * * 从 offset 位置读取 4 个字节为一个 long， 因为 java 为 big-endian 格式， 所以没办法 *
	 * 用了这么一个函数来做转换 * @param offset * @return 读取的 long 值，返回-1 表示读取文件失败
	 */
	private long readLong4(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ret |= (ipFile.readByte() & 0xFF);
			ret |= ((ipFile.readByte() << 8) & 0xFF00);
			ret |= ((ipFile.readByte() << 16) & 0xFF0000);
			ret |= ((ipFile.readByte() << 24) & 0xFF000000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * * 从 offset 位置读取 3 个字节为一个 long， 因为 java 为 big-endian 格式， 所以没办法 *
	 * 用了这么一个函数来做转换 * @param offset 整数的起始偏移 * @return 读取的 long 值，返回-1 表示读取文件失败
	 */
	private long readLong3(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/** * 从当前位置读取 3 个字节转换成 long * @return 读取的 long 值，返回-1 表示读取文件失败 */
	private long readLong3() {
		long ret = 0;
		try {
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * * 从 offset 位置读取四个字节的 ip 地址放入 ip 数组中，读取后的 ip 为 big-endian 格式，但是 * 文件中是
	 * little-endian 形式，将会进行转换 * @param offset * @param ip
	 */
	private void readIP(long offset, byte[] ip) {
		try {
			ipFile.seek(offset);
			ipFile.readFully(ip);
			byte temp = ip[0];
			ip[0] = ip[3];
			ip[3] = temp;
			temp = ip[1];
			ip[1] = ip[2];
			ip[2] = temp;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * * 从 offset 位置读取四个字节的 ip 地址放入 ip 数组中，读取后的 ip 为 big-endian 格式，但是 * 文件中是
	 * little-endian 形式，将会进行转换 * @param offset * @param ip
	 */
	private void readIP(int offset, byte[] ip) {
		mbb.position(offset);
		mbb.get(ip);
		byte temp = ip[0];
		ip[0] = ip[3];
		ip[3] = temp;
		temp = ip[1];
		ip[1] = ip[2];
		ip[2] = temp;
	}

	/**
	 * * 把类成员 ip 和 beginIp 比较，注意这个 beginIp 是 big-endian 的 * @param ip 要查询的 IP * @param
	 * beginIp 和被查询 IP 相比较的 IP * @return 相等返回 0，ip 大于 beginIp 则返回 1，小于返回-1。
	 */
	private int compareIP(byte[] ip, byte[] beginIp) {
		for (int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if (r != 0)
				return r;
		}
		return 0;
	}

	/**
	 * * 把两个 byte 当作无符号数进行比较 * @param b1 * @param b2 * @return 若 b1 大于 b2 则返回
	 * 1，相等返回 0，小于返回-1
	 */
	private int compareByte(byte b1, byte b2) {
		if ((b1 & 0xFF) > (b2 & 0xFF))
			// 比较是否大于
			return 1;
		else if ((b1 ^ b2) == 0)
			// 判断是否相等
			return 0;
		else
			return -1;
	}

	/**
	 * * 这个方法将根据 ip 的内容，定位到包含这个 ip 国家地区的记录处， 返回一个绝对偏移 * 方法使用二分法查找。 * @param ip
	 * 要查询的 IP * @return 如果找到了，返回结束 IP 的偏移，如果没有找到，返回-1
	 */
	private long locateIP(byte[] ip) {
		long m = 0;
		int r;
		// 比较第一个 ip 项
		readIP(ipBegin, b4);
		r = compareIP(ip, b4);
		if (r == 0)
			return ipBegin;
		else if (r < 0)
			return -1;
		// 开始二分搜索
		for (long i = ipBegin, j = ipEnd; i < j;) {
			m = getMiddleOffset(i, j);
			readIP(m, b4);
			r = compareIP(ip, b4);
			// log.debug(Utils.getIpStringFromBytes(b));
			if (r > 0)
				i = m;
			else if (r < 0) {
				if (m == j) {
					j -= IP_RECORD_LENGTH;
					m = j;
				} else
					j = m;
			} else
				return readLong3(m + 4);
		}
		// 如果循环结束了，那么 i 和 j 必定是相等的，这个记录为最可能的 记录，但是并非
		// 肯定就是，还要检查一下，如果是，就返回结束地址区的绝对偏 移
		m = readLong3(m + 4);
		readIP(m, b4);
		r = compareIP(ip, b4);
		if (r <= 0)
			return m;
		else
			return -1;
	}

	/** * 得到 begin 偏移和 end 偏移中间位置记录的偏移 * @param begin * @param end * @return */
	private long getMiddleOffset(long begin, long end) {
		long records = (end - begin) / IP_RECORD_LENGTH;
		records >>= 1;
		if (records == 0)
			records = 1;
		return begin + records * IP_RECORD_LENGTH;
	}

	/**
	 * * 给定一个 ip 国家地区记录的偏移，返回一个 IPLocation 结构 * @param offset 国家记录的起始偏移 * @return
	 * IPLocation 对象
	 */
	private IPLocation getIPLocation(long offset) {
		try {
			// 跳过 4 字节 ip
			ipFile.seek(offset + 4);
			// 读取第一个字节判断是否标志字节
			byte b = ipFile.readByte();
			if (b == REDIRECT_MODE_1) {
				// 读取国家偏移
				long countryOffset = readLong3();
				// 跳转至偏移处
				ipFile.seek(countryOffset);
				// 再检查一次标志字节，因为这个时候这个地方仍然可能是个重 定向
				b = ipFile.readByte();
				if (b == REDIRECT_MODE_2) {
					loc.setCountry(readString(readLong3()));
					ipFile.seek(countryOffset + 4);
				} else
					loc.setCountry(readString(countryOffset));
				// 读取地区标志
				loc.setArea(readArea(ipFile.getFilePointer()));
			} else if (b == REDIRECT_MODE_2) {
				loc.setCountry(readString(readLong3()));
				loc.setArea(readArea(offset + 8));
			} else {
				loc.setCountry(readString(ipFile.getFilePointer() - 1));
				loc.setArea(readArea(ipFile.getFilePointer()));
			}
			return loc;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * * 给定一个 ip 国家地区记录的偏移，返回一个 IPLocation 结构，此方法 应用与内存映射文件方式 * @param offset
	 * 国家记录的起始偏移 * @return IPLocation 对象
	 */
	private IPLocation getIPLocation(int offset) {
		// 跳过 4 字节
		mbb.position(offset + 4);
		// 读取第一个字节判断是否标志字节
		byte b = mbb.get();
		if (b == REDIRECT_MODE_1) {
			// 读取国家偏移
			int countryOffset = readInt3();
			// 跳转至偏移处
			mbb.position(countryOffset);
			// 再检查一次标志字节，因为这个时候这个地方仍然可能是个重定 向
			b = mbb.get();
			if (b == REDIRECT_MODE_2) {
				loc.setCountry(readString(readInt3()));
				mbb.position(countryOffset + 4);
			} else
				loc.setCountry(readString(countryOffset));
			// 读取地区标志
			loc.setArea(readArea(mbb.position()));
		} else if (b == REDIRECT_MODE_2) {
			loc.setCountry(readString(readInt3()));
			loc.setArea(readArea(offset + 8));
		} else {
			loc.setCountry(readString(mbb.position() - 1));
			loc.setArea(readArea(mbb.position()));
		}
		return loc;
	}

	/**
	 * * 从 offset 偏移开始解析后面的字节，读出一个地区名 * @param offset 地区记录的起始偏移 * @return 地区名字符串
	 * * @throws IOException
	 */
	private String readArea(long offset) throws IOException {
		ipFile.seek(offset);
		byte b = ipFile.readByte();
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			long areaOffset = readLong3(offset + 1);
			if (areaOffset == 0)
				return Message.unknown_area;
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}

	/** * @param offset 地区记录的起始偏移 * @return 地区名字符串 */
	private String readArea(int offset) {
		mbb.position(offset);
		byte b = mbb.get();
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			int areaOffset = readInt3();
			if (areaOffset == 0)
				return Message.unknown_area;
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}

	/**
	 * * 从 offset 偏移处读取一个以 0 结束的字符串 * @param offset 字符串起始偏移 * @return
	 * 读取的字符串，出错返回空字符串
	 */
	private String readString(long offset) {
		try {
			ipFile.seek(offset);
			int i;
			for (i = 0, buf[i] = ipFile.readByte(); buf[i] != 0; buf[++i] = ipFile
					.readByte())
				;
			if (i != 0)
				return Util.getString(buf, 0, i, "GBK");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "";
	}

	/**
	 * * 从内存映射文件的 offset 位置得到一个 0 结尾字符串 * @param offset 字符串起始偏移 * @return
	 * 读取的字符串，出错返回空字符串
	 */
	private String readString(int offset) {
		try {
			mbb.position(offset);
			int i;
			for (i = 0, buf[i] = mbb.get(); buf[i] != 0; buf[++i] = mbb.get())
				;
			if (i != 0)
				return Util.getString(buf, 0, i, "GBK");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
		return "";
	}
}
