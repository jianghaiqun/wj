package com.sinosoft.search.index;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.search.ProductAnalyzer;
import com.sinosoft.search.ProductSimilarity;
import com.sinosoft.search.ZvingAnalyzer;
import com.sinosoft.search.ZvingSimilarity;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

public abstract class Indexer {
	protected static final Logger logger = LoggerFactory.getLogger(Indexer.class);
	protected String indexPath;

	protected IndexWriter writer;

	protected Date lastDate;
	// 下一次以此时间为依据扫描数据改动
	protected Date nextLastDate;

	protected int threadCount = 8;

	protected int aliveThreadCount;

	private boolean updateFlag;

	private boolean singleThreadMode = true;

	private static Object mutex = new Object();

	 
 	/**
	 * dsahdsahdkjsa
	 * @param path - bbb
	 * @param aaa - xxxx
	 */
	public void setPath(String path) {
		indexPath = path;
		try {
			if (!new File(path).exists()) {
				nextLastDate = lastDate = DateUtil.parse("1970-01-01");
				updateFlag = false;
			} else {
				if (!new File(path + "/time.lock").exists()) {
					FileUtil.deleteFromDir(path);
					nextLastDate = lastDate = new Date();
					updateFlag = false;
				} else {
					// 存在文件内容为空的情况
					String lastDateStr = FileUtil.readText(path + "/time.lock").trim();
					if (StringUtil.isEmpty(lastDateStr)) {
						nextLastDate = lastDate = new Date();
					} else {
						nextLastDate = lastDate = DateUtil.parseDateTime(lastDateStr);
					}
					updateFlag = true;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 开始索引
	 */
	public void start() {
		synchronized (mutex) {// 同一时间只允许有一个实例在运行,以免发生索引冲突
			if (StringUtil.isEmpty(indexPath)) {
				throw new RuntimeException("没有指定索引存放的目录!");
			}
			logger.info("开始建立索引{}......", indexPath);
			long startTime = System.currentTimeMillis();
			FSDirectory directory = null;
			try {
				// 尝试清除write.lock
				try {
					if (new File(indexPath + "/write.lock").exists()) {
						FileUtil.delete(indexPath + "/write.lock");
					}
				} catch (Exception e) {
				}
//				IndexWriter 的创建方法，IndexWriter(String path,
//		                   a,    //分词器   Analyzer analyzer=new StartandAnalyzer();
//		                   boolean create,   //是否重新创建索引，如果为True 是删除原来索引，重新创建
//		                   mfl)   //创建索引词的数据量，分词器分出多少词
				
				if (updateFlag) {
					writer = new IndexWriter(FSDirectory.getDirectory(new File(indexPath)), new ZvingAnalyzer(), true,
							IndexWriter.MaxFieldLength.UNLIMITED);
					 
				} else {
					writer = new IndexWriter(FSDirectory.getDirectory(new File(indexPath)), new ZvingAnalyzer(), true,
							IndexWriter.MaxFieldLength.UNLIMITED);
				}
				writer.setSimilarity(new ZvingSimilarity());
				// writer.setMergeFactor(100);
				if ("true".equals(Config.getValue("App.MinimalMemory"))) {
					writer.setMaxBufferedDocs(10);
				} else {
					writer.setMaxBufferedDocs(50);
				}

				if (this.singleThreadMode) {
					threadCount = 1;
				}
				aliveThreadCount = threadCount;
				for (int i = 0; i < threadCount; i++) {
					IndexerThread t = new IndexerThread();
					t.setIndexer(this);
					t.start();
				}
				try {
					while (aliveThreadCount != 0) {
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
				if (nextLastDate == null || lastDate == nextLastDate) {
					nextLastDate = new Date();
				}
				FileUtil.writeText(indexPath + "/time.lock", DateUtil.toDateTimeString(nextLastDate));
				logger.info("建立索引共耗时 {} 毫秒.",  (System.currentTimeMillis() - startTime));
			} catch (Throwable e) {
				logger.error("建立索引失败," + indexPath + e.getMessage(), e);
			} finally {
				try {
					if (directory != null)
						directory.close();
					if (writer != null) {
					
						if (new File(indexPath + "/optimize.lock").exists()) {
							String lastDateStr = FileUtil.readText(indexPath + "/optimize.lock").trim();
							if (StringUtil.isNotEmpty(lastDateStr)) {
								Date date = DateUtil.parseDateTime(lastDateStr);
								if (date.getTime() - System.currentTimeMillis() >= 24 * 60 * 1000) {
									writer.optimize(true);// 间隔一天才优化
								}
							} else {
								writer.optimize(true);
							}
						} else {
							writer.optimize(true);
						}
						writer.close();
					} 
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	 
	
	/**
	 * 开始索引,产品搜索调用单独分词器-add by lilang-2015-5-8
	 */
	@SuppressWarnings("deprecation")
	public void proStart() {
		synchronized (mutex) {// 同一时间只允许有一个实例在运行,以免发生索引冲突
			if (StringUtil.isEmpty(indexPath)) {
				throw new RuntimeException("没有指定索引存放的目录!");
			}
			logger.info("开始建立索引{}......", indexPath);
			long startTime = System.currentTimeMillis();
			FSDirectory directory = null;
			try {
				// 尝试清除write.lock
				try {
					if (new File(indexPath + "/write.lock").exists()) {
						FileUtil.delete(indexPath + "/write.lock");
					}
				} catch (Exception e) {
				}
//				IndexWriter 的创建方法，IndexWriter(String path,
//		                   a,    //分词器   Analyzer analyzer=new StartandAnalyzer();
//		                   boolean create,   //是否重新创建索引，如果为True 是删除原来索引，重新创建
//		                   mfl)   //创建索引词的数据量，分词器分出多少词
				
				if (updateFlag) {
					writer = new IndexWriter(FSDirectory.getDirectory(new File(indexPath)), new ProductAnalyzer(), false,
							IndexWriter.MaxFieldLength.UNLIMITED);
				} else {
					writer = new IndexWriter(FSDirectory.getDirectory(new File(indexPath)), new ProductAnalyzer(), true,
							IndexWriter.MaxFieldLength.UNLIMITED);
				}
				writer.setSimilarity(new ProductSimilarity());
				//writer.setMergeFactor(100);
				if ("true".equals(Config.getValue("App.MinimalMemory"))) {
					writer.setMaxBufferedDocs(10);
				} else {
					writer.setMaxBufferedDocs(50);
				}

				if (this.singleThreadMode) {
					threadCount = 1;
				}
				aliveThreadCount = threadCount;
				for (int i = 0; i < threadCount; i++) {
					IndexerThread t = new IndexerThread();
					t.setIndexer(this);
					t.start();
				}
				try {
					while (aliveThreadCount != 0) {
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
				if (nextLastDate == null || lastDate == nextLastDate) {
					nextLastDate = new Date();
				}
				FileUtil.writeText(indexPath + "/time.lock", DateUtil.toDateTimeString(nextLastDate));
				logger.info("建立索引共耗时 {} 毫秒.", (System.currentTimeMillis() - startTime));
			} catch (Throwable e) {
				logger.error("建立索引失败," + indexPath + e.getMessage(), e);
			} finally {
				try {
					if (directory != null)
						directory.close();
					if (writer != null) {
						if (new File(indexPath + "/optimize.lock").exists()) {
							String lastDateStr = FileUtil.readText(indexPath + "/optimize.lock").trim();
							if (StringUtil.isNotEmpty(lastDateStr)) {
								Date date = DateUtil.parseDateTime(lastDateStr);
								if (date.getTime() - System.currentTimeMillis() >= 24 * 60 * 1000) {
									writer.optimize(true);// 间隔一天才优化
								}
							} else {
								writer.optimize(true);
							}
						} else {
							writer.optimize(true);
						}
						writer.close();
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	 
	
	public abstract void create();

	public abstract void update();

	protected void addDocument(Document doc) throws Exception {
		writer.addDocument(doc);
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public boolean isSingleThreadMode() {
		return singleThreadMode;
	}

	public void setSingleThreadMode(boolean singleThreadMode) {
		this.singleThreadMode = singleThreadMode;
	}
}
