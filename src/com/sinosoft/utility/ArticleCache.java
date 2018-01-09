package com.sinosoft.utility;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCArticleCacheInfoSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发布文章缓存
 * @author lenovo
 *
 */
public class ArticleCache {
	private static final Logger logger = LoggerFactory.getLogger(ArticleCache.class);

	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd");

	private Map<String,Object> mMap = null;
	private List<Object> mList = null;
	//数据缓存类型，DataTable、Map、List
	private String cacheFlag = "DataTable";
	//缓存文件根目录
	public static String FILE_ROOT_PATH = Config.getValue("ArticleCache");//"/ArticleCache//";
	//产品信息缓存路径
	public static String PRODUCT_FILE_PATH =  "RiskCode//";
    //信息缓存标记，product：产品信息;article:文章信息；
	private String cacheInfoFlag="";
	//信息缓存 主类别 article、catalog
	private String chcheMainFlag="article";
	//是否有子缓存文件Y:有;N:没有
	private String hasChild = "N";
	/**
	 * 写入数据到序列化文件
	 * @param key key值
	 * @param articleID 文章ID
	 * @param ModuleID 文章静态页面对应模块
	 * @param cDT 需要缓存的数据
	 */
	public void writeCache(String key, String articleID,DataTable dt){
		
		//如果没有数据，则不进行缓存
		DataTable cDT = dt;
		if(cDT!=null && cDT.getRowCount()>=1){
			String inFileName = articleID+"_"+key;
			checkCacheInfoFlag(cDT);
			if("product".equals(cacheInfoFlag)){
				writeKeyCache(key, articleID,cDT);
				writeDataRowCache(cDT,inFileName);
			}else if("article".equals(cacheInfoFlag)){
				writeDataTableCache(key, articleID,cDT);
			}
		}
	}
	/**
	 * 写入数据到序列化文件
	 * @param key key值
	 * @param articleID 文章ID
	 * @param ModuleID 文章静态页面对应模块
	 * @param cDT 需要缓存的数据
	 */
	public void writeDataTableCache(String key, String articleID,DataTable cDT){
		ObjectOutputStream out = null;
		RandomAccessFile raf = null;
		String inFileName = articleID+"_"+key;
		String catalogID = getChildPath(articleID);
		String articleFilePath = FILE_ROOT_PATH+catalogID+inFileName+".file";
		//判断是否是产品信息缓存，如果是产品信息缓存则缓存为
		try {
			File file  = new File(articleFilePath); 
			File pFile = file.getParentFile();
			if(!pFile.exists()){
				pFile.mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			raf = new RandomAccessFile(articleFilePath, "rw");
			FileOutputStream sof = new FileOutputStream(raf.getFD());
			out = new ObjectOutputStream(sof);
			//System.out.println(file.lastModified());
			//Date date = new Date(file.lastModified());
			//System.out.println(sdf_1.format(date));
			if("DataTable".equals(cacheFlag)){
				out.writeObject(cDT);
			}else if("Map".equals(cacheFlag)){
				out.writeObject(mMap);
			}else if("List".equals(cacheFlag)){
				out.writeObject(mList);
			}
			//写入信息到数据库
			writeKeyToDataBase(inFileName,inFileName,articleFilePath,"article","","");
	        out.flush();
	        out.close();
	        raf.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				if(out!=null){
					out.flush();
					out.close();
					raf.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
	        
		}
	}
	/**
	 * 写入产品信息key值到序列化文件
	 * @param key key值
	 * @param articleID 文章ID
	 * @param ModuleID 文章静态页面对应模块
	 * @param cDT 需要缓存的数据
	 */
	public void writeKeyCache(String key, String articleID,DataTable cDT){
		ObjectOutputStream out = null;
		String inFileName = articleID+"_"+key;
		String catalogID = getChildPath(articleID);
		String articleFilePath = FILE_ROOT_PATH+catalogID+inFileName+".file";
		List<String> keyList = new ArrayList<String>(); 
		for(int i=0;i>cDT.getRowCount();i++){
			keyList.add(cDT.getString(i, "RiskCode"));
		}
		//判断是否是产品信息缓存，如果是产品信息缓存则缓存为
		try {
			File file  = new File(articleFilePath); 
			File pFile = file.getParentFile();
			if(!pFile.exists()){
				pFile.mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream sof = new FileOutputStream(articleFilePath);
			out = new ObjectOutputStream(sof);
			out.writeObject(keyList);
			//写入信息到数据库
			writeKeyToDataBase(inFileName,inFileName,articleFilePath,"article","Y","");
	        out.flush();
	        out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				if(out!=null){
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
	        
		}
	}
	/**
	 * 写入产品信息到序列化文件
	 * @param cDT 需要缓存的数据
	 */
	public void writeDataRowCache(DataTable cDT,String parentIndex){
		ObjectOutputStream out = null;
		if(cDT.getRowCount()>=1){
			for(int i=0;i<cDT.getRowCount();i++){
				String riskcode = cDT.getString(i, "RiskCode");
				String articleFilePath = FILE_ROOT_PATH+PRODUCT_FILE_PATH+riskcode+".file";
				try {
					File file  = new File(articleFilePath); 
					File pFile = file.getParentFile();
					if(!pFile.exists()){
						pFile.mkdirs();
					}
					if(!file.exists()){
						file.createNewFile();
					}
					FileOutputStream sof = new FileOutputStream(articleFilePath);
					out = new ObjectOutputStream(sof);
					out.writeObject(cDT.getDataRow(i));
					//写入信息到数据库
					writeKeyToDataBase(riskcode,riskcode,articleFilePath,"product","",parentIndex);
			        out.flush();
			        out.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}finally{
					try {
						if(out!=null){
							out.flush();
							out.close();
						}
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
			        
				}
			}
		}
		
	}
	/**
	 * 读缓存文件到对象-DataTable
	 */
	public DataTable readCacheByDataTable(String articleID,String key){
		
		String cacheIndex = StringUtil.md5Hex(articleID+"_"+key);
		getParentInfo(cacheIndex);
		if("Y".equals(hasChild)){
			return readCacheByListDataTable(cacheIndex);
		}else{
			return readCacheByOnlyDataTable(cacheIndex);
		}
		
	}
	public DataTable readCacheByListDataTable(String key){
		
		DataTable dt = getChildDataTable(key);
		DataTable returnDT = new DataTable();
		for(int i=0;i<dt.getRowCount();i++){
			DataRow dr = readCacheByDataRow(dt.getString(i, 0));
			if(dr!=null){
				returnDT.insertRow(dr);
			}
		}
		if(returnDT==null || returnDT.getRowCount()<=0){
			return null;
		}
		return returnDT;
	}
	public DataTable getChildDataTable(String key){
		try{
			QueryBuilder qb = new QueryBuilder(" SELECT CacheIndex FROM ZCArticleCacheInfo WHERE ParentCacheIndex=? ");
			qb.add(key);
			return qb.executeDataTable();
		}catch(Exception e){
			logger.error(" 读取子索引文件失败！："+key + e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 读缓存文件到对象-DataTable
	 */
	@SuppressWarnings("resource")
	public DataTable readCacheByOnlyDataTable(String cacheIndex){
		
		ObjectInputStream in=null;
		DataTable tDt=null;
		RandomAccessFile raf=null;
		String outFileName = getCacheFilePath(cacheIndex)+"";
		if(StringUtil.isEmpty(outFileName)){
			return null;
		}
		try {
			raf = new RandomAccessFile(outFileName, "rw");
			in = new ObjectInputStream( new FileInputStream(raf.getFD()));
			if("DataTable".equals(cacheFlag)){
				tDt = (DataTable) in.readObject();
				return tDt;
			}
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return null;
	}
	/**
	 * 读缓存文件到对象-DataRow
	 */
	@SuppressWarnings("resource")
	public DataRow readCacheByDataRow(String key){
		
		ObjectInputStream in=null;
		RandomAccessFile raf=null;
		DataRow tDr=null;
		
		String outFileName = getCacheFilePath(key)+"";
		if(StringUtil.isEmpty(outFileName)){
			return null;
		}
		try {
			raf = new RandomAccessFile(outFileName, "rw");
			in = new ObjectInputStream( new FileInputStream(raf.getFD()));
			tDr = (DataRow) in.readObject();
			in.close();
			return tDr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 读缓存文件到对象-Map
	 */
	@SuppressWarnings({"unchecked" })
	public Map<String,Object> readCacheByMap(String cacheIndex){
		
		ObjectInputStream in=null;
		HashMap<String,Object> tMap=null;
		String outFileName = getCacheFilePath(cacheIndex)+"";
		try {
			in = new ObjectInputStream( new FileInputStream(outFileName));
			if("Map".equals(cacheFlag)){
				tMap = (HashMap<String,Object>) in.readObject();
				return tMap;
			}
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		
		return null;
	}
	/**
	 * 读缓存文件到对象-List
	 */
	@SuppressWarnings({ "unchecked"})
	public List<Object> readCacheByList(String cacheIndex){
		
		ObjectInputStream in=null;
		ArrayList<Object> tList=null;
		String outFileName = getCacheFilePath(cacheIndex)+"";
		try {
			in = new ObjectInputStream( new FileInputStream(outFileName));
			if("List".equals(cacheFlag)){
				tList = (ArrayList<Object>) in.readObject();
				return tList;
			}
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		
		return null;
	}
	
	/**
	 * 把缓存写入磁盘后，已key-文件名的形式，存储到数据库中
	 * 用于读取缓存数据
	 * @param key
	 * @param fileName 缓存文件名称
	 * @param childPath 子路径
	 * @param cacheType 缓存类型
	 * @param hasChild 是否有子缓存
	 * @param parentIndex 父缓存节点
	 */
	public void writeKeyToDataBase(String key,String fileName,String childPath,String cacheType,String hasChild,String parentIndex){
		
		ZCArticleCacheInfoSchema tZCArticleCacheInfoSchema = new ZCArticleCacheInfoSchema();
		Transaction trans = new Transaction();
		String tKey = StringUtil.md5Hex(key);
		String tParentIndex = StringUtil.md5Hex(parentIndex);
		if(StringUtil.isNotEmpty(parentIndex)){
			tKey = StringUtil.md5Hex(parentIndex+"_"+key);
			trans.add(new QueryBuilder(" delete from ZCArticleCacheInfo where CacheIndex=? and ParentCacheIndex=? ",tKey,tParentIndex));
		}else{
			trans.add(new QueryBuilder(" delete from ZCArticleCacheInfo where CacheIndex=? ",tKey));
		}
		tZCArticleCacheInfoSchema.setID(NoUtil.getMaxID("ArticleCacheID") + "");
		tZCArticleCacheInfoSchema.setCacheIndex(tKey);
		tZCArticleCacheInfoSchema.setCacheFileName(fileName);
		tZCArticleCacheInfoSchema.setCacheFilePath(childPath);
		tZCArticleCacheInfoSchema.setCacheType(cacheType);
		if(StringUtil.isNotEmpty(parentIndex)){
			tZCArticleCacheInfoSchema.setParentCacheIndex(tParentIndex);
		}else{
			tZCArticleCacheInfoSchema.setParentCacheIndex("");
		}
		tZCArticleCacheInfoSchema.setHasChild(hasChild);
		tZCArticleCacheInfoSchema.setCreateDate(new Date());
		tZCArticleCacheInfoSchema.setModifyDate(new Date());
		
		trans.add(tZCArticleCacheInfoSchema, Transaction.INSERT);
		
		if(!trans.commit()){
			logger.error("文章缓存信息已生成，但是更新数据库失败！");
		}
	}
	/**
	 * 根据key值获取缓存文件子路径：path+fileName;
	 * @param key
	 */
	public void getFileNameByKey(String key){
		
	}
	/**
	 * 根据文articleId，以及缓存所属模块进行缓存文件夹处理
	 * @param articleID
	 * @param ModuleID
	 * @return
	 */
	public String getChildPath(String articleID){
		
		try{
			QueryBuilder qb = new QueryBuilder(" Select CatalogID From zcarticle where id=? ");
			qb.add(articleID);
			if(qb.executeInt()>=1){
				return getParentID(qb.executeString());
				
			}
		}catch(Exception e){
			logger.error(" 发布栏目文章-创建文章缓存信息失败 ："+articleID + e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 根据栏目id递归取父栏目id
	 * @param id
	 * @return
	 */
	public String getParentID(String catalogID){
		String s = "";
		try{
			QueryBuilder qb = new QueryBuilder("SELECT parentID FROM zccatalog  WHERE id=? ");
			qb.add(catalogID);
			DataTable dt = qb.executeDataTable();
			if(dt.getRowCount()>=1){
				if(!"0".equals(dt.get(0, 0)) && !StringUtil.isEmpty((dt.get(0, 0)))){
					s = catalogID+"//";
					//s = catalogID+"//"+getParentID(qb.executeString());
				}else{
					s = catalogID+"//" ;
				}
			}
			return s;
		}catch(Exception e){
			logger.error(" 发布栏目文章-查询父栏目ID失败 ："+catalogID + e.getMessage(), e);
		}
		return "";
	}
	/**
	 * 根据数据内容判断是否为产品信息缓存
	 */
	public void checkCacheInfoFlag(DataTable dt){
		
		if(dt==null || dt.getRowCount()<=0){
			this.cacheInfoFlag = "article";
		}else{
			String innerCode = StringUtil.isEmpty(dt.getString(0, "CatalogInnerCode")) ? (""):(dt.getString(0, "CatalogInnerCode"));
			if(innerCode.startsWith("002313")){
				this.cacheInfoFlag = "product";
			}else{
				this.cacheInfoFlag = "article";
			}
		}
		
	}
	/**
	 * 判断是否有子索引文件
	 */
	public void getParentInfo(String cacheIndex){
		
		QueryBuilder qb = new QueryBuilder(" SELECT HasChild FROM ZCArticleCacheInfo WHERE CacheIndex=? LIMIT 1 ");
		qb.add(cacheIndex);
		DataTable dt = qb.executeDataTable();
		if(dt.getRowCount()>=1){
		    //this.parentIndexValue = dt.getString(0, 0);
		    hasChild = dt.getString(0, 0);
		}else{
			hasChild = "";
		}
		
	}
	/**
	 * 查询缓存文件绝对路径
	 * @param cacheIndex
	 * @return
	 */
	public String getCacheFilePath(String cacheIndex){
		
		QueryBuilder qb = new QueryBuilder(" SELECT CacheFilePath FROM ZCArticleCacheInfo WHERE CacheIndex=? LIMIT 1 ");
		qb.add(cacheIndex);
		DataTable dt = qb.executeDataTable();
		if(dt.getRowCount()>=1){
		    return dt.getString(0, 0);
		}else{
			logger.info("为查询到缓存物理文件绝对路径，将使用非缓存数据：{}", cacheIndex);
		}
		return "";
	}
	public Map<String, Object> getmMap() {
		return mMap;
	}
	public void setmMap(Map<String, Object> mMap) {
		this.mMap = mMap;
	}
	public List<Object> getmList() {
		return mList;
	}
	public void setmList(List<Object> mList) {
		this.mList = mList;
	}
	public String getCacheFlag() {
		return cacheFlag;
	}
	public void setCacheFlag(String cacheFlag) {
		this.cacheFlag = cacheFlag;
	}
	public String getCacheInfoFlag() {
		return cacheInfoFlag;
	}
	public void setCacheInfoFlag(String cacheInfoFlag) {
		this.cacheInfoFlag = cacheInfoFlag;
	}
	public String getChcheMainFlag() {
		return chcheMainFlag;
	}
	public void setChcheMainFlag(String chcheMainFlag) {
		this.chcheMainFlag = chcheMainFlag;
	}
	public String getHasChild() {
		return hasChild;
	}
	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}
	
}

