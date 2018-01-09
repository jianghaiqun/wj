package com.sinosoft.cms.datachannel;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;
import java.util.Observer;

/**
 * 发布任务监控 外部发布程序向队列中添加任务 由发布线程扫描任务进行处理
 * @author lanjun 2010-5-4
 */
public class PublishMonitor implements Observer{
	private static final Logger logger = LoggerFactory.getLogger(PublishMonitor.class);

	/**
	 * 发布队列
	 */
	public static ZCArticleSet set = new ZCArticleSet();
	
	/**
	 * 发布线程
	 */
	public static PublishThread thread = null;
	
	/**
	 * 发布间隔，默认1s，可配置
	 */
	public static long interval = 1000;
	
	private static Object mutex = new Object();

	/**
	 * 初始化
	 */
    public void init(){
    	String publishInterval = Config.getValue("PublishInterval");
    	if(StringUtil.isDigit(publishInterval)){
    		interval = Long.parseLong(publishInterval);
    	}
    	synchronized(mutex){ 
            if(thread == null){
            	synchronized(mutex){
            		//thread = new PublishThread();
            		//thread.start(); 
            		thread = new PublishThread(); 
            		thread.addObserver(this);
            		Thread td = new Thread(thread);
                    td.start();
                    //thread.doBusiness();
            		
            	}
            }
    	}
    }
 
	/**
	 * 添加发布队列
	 * @param type
	 * @param id
	 */
	public void addJob(SchemaSet newSet){
		init();
		
		synchronized(set){
			set.add(newSet);
		}
	}
	
	/**
	 * 清空发布队列
	 */
	public void clean(){
		synchronized(set){
			set.clear();
		}
	}
	
	/**
	 * 执行任务
	 * @param list
	 */
	public void execute(ZCArticleSet newSet){
		Publisher p = new Publisher();
		p.publishArticle(newSet);
	}
	
	/**
	 * 发布线程 单线程发布 每固定时间扫描一次
	 */
	class PublishThread extends Observable implements Runnable {
		public void run() {
			while(true){
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
					doBusiness();//遇到意外终止，则重新启动
					
				}
				 
				ZCArticleSet newSet = new ZCArticleSet();
				synchronized(set){
					newSet.add(set);
					set.clear();
				} 
				//启动线程
				try{ 
					execute(newSet);
				}catch(Exception e){
					logger.error(e.getMessage(), e);
					doBusiness();//遇到意外终止，则重新启动
				}
				//手动抛出异常--------------------------------------------------------------测试用
				/*try{
					throw new Exception();
				}catch(Exception e){
					e.printStackTrace();
					doBusiness();
					break;
				}*/
			}
		}
	public void doBusiness(){
		        if(true){
		            super.setChanged();
		        }
		        notifyObservers();
		    }
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		    //System.out.println("RunThread死机"); 
			thread = new PublishThread(); 
			thread.addObserver(this);
			Thread td = new Thread(thread);
	        td.start();
	        //System.out.println("RunThread重启");
		
	}
}

