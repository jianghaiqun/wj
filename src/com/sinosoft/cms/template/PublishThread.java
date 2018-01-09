package com.sinosoft.cms.template;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.schema.ZCArticleSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


public class PublishThread extends Observable implements Runnable,Observer{
	private static final Logger logger = LoggerFactory.getLogger(PublishThread.class);

	private static String docType = "article";//发布类型 Article
	private ZCArticleSchema article = new ZCArticleSchema();//发布文章信息
	public static LinkedList<ZCArticleSchema> articleLinkedList =  new  LinkedList<ZCArticleSchema>();
	public static int threadAllCount = 0;//可发布线程数
	public static int currentThreadCount = 0;//可发布线程数
	public static ThreadPool threadPool=null; 
	private static Object mutex = new Object();
	public PublishThread(){
		threadAllCount = getPublishCount();
	}
	public static Map<String,Integer> countPublish = new HashMap<String, Integer>();
	@Override
	public void update(Observable o, Object arg) {
		//每篇文章最多发布5次
		int count = countPublish.get( article.getID() + "");
		if("".equals(count)) count=0;
		if(count <=5) {
			articleLinkedList.add(article);
			countPublish.put(article.getID() + "", count+1);
		}  else {
			countPublish.remove(article.getID() + "");
		}
	}
	@Override
	public void run() {

		
	
	}
	 private static Runnable createTask(final ZCArticleSchema article) {  
	        return new Runnable() {  
	            public void run() {  
	            	PageGenerator p = new PageGenerator();
					try{
						if(!p.staticDoc(docType,article)){
							articleLinkedList.add(article);
						}
					}catch(Exception e){
						if(!articleLinkedList.contains(article)){
							articleLinkedList.add(article);
						}
						logger.error(e.getMessage(), e);
					} 
	            }  
	        };  
	    }
	/**
	 * 获取发布线程数
	 * @return
	 */
    public int getPublishCount(){
    	
    	int count=4;
    	
    	StringBuffer tSQL = new StringBuffer(" SELECT CAST((SELECT (CASE WHEN VALUE IS NULL OR VALUE='' THEN 1 ELSE VALUE END) FROM zdconfig WHERE TYPE='PublishCount') AS UNSIGNED INT) ");
    	QueryBuilder qbArticle = new QueryBuilder(tSQL.toString());
    	count = qbArticle.executeInt();
    	if(count==0){
    		count=4;
    	}
        return count;
    }
    public void doBusiness(){
        if(true){
            super.setChanged();
        }
        notifyObservers();
    }

	public static LinkedList<ZCArticleSchema> getArticleLinkedList() {
		return articleLinkedList;
	}
	public static void setArticleLinkedList(
			LinkedList<ZCArticleSchema> articleLinkedList) {
		PublishThread.articleLinkedList = articleLinkedList;
	}
	public ZCArticleSchema getArticle() {
		return article;
	}
	public void setArticle(ZCArticleSchema article) {
		this.article = article;
	}

}
