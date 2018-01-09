package com.alipay.util.fpfClient;


import com.sinosoft.framework.Config;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.util.HashMap;
 
 
public class FtpFactory {
	private static final Logger logger = LoggerFactory.getLogger(FtpFactory.class);
    private static FtpFactory ftpFactory;
    private HashMap ftpCache = new HashMap();
    private static String FTPINFO_MAPPING_PATH = "WEB-INF/MAPPING-XML/ftpInfo-mapping.xml";
    private static String FTP_PATH = "/ftp/";
    private static String FTP_SUFFIX= ".ftp";
    

    
    private FtpFactory() {
    }

    synchronized public static FtpFactory getInstance() {
        if (ftpFactory == null)
            ftpFactory = new FtpFactory();
        return ftpFactory;
    }

 
    public FtpInfo getFtpInfo(String ftpName)  throws Exception {
        long begin = System.currentTimeMillis();
        FtpInfo ftpInfo = null;
        FileReader reader = null;
        try {

    	    ftpInfo = (FtpInfo) ftpCache.get( ftpName );
    	    if(ftpInfo == null){
	            Mapping mapping = new Mapping();
	            
	            mapping.loadMapping(Config.getContextRealPath() + FTPINFO_MAPPING_PATH);
 				String ftpFileName = Config.getContextRealPath() + FTP_PATH+ftpName +FTP_SUFFIX;
	            reader = new FileReader(ftpFileName);
	            Unmarshaller unmarshaller = new Unmarshaller(FtpInfo.class);   
				
	            unmarshaller.setMapping(mapping);
	            ftpInfo = (FtpInfo) unmarshaller.unmarshal(reader);
	            
	            
	            String workDir = ftpInfo.getWorkDir();
	            if(workDir!=null&&workDir.indexOf("/")!=-1){
	            	ftpInfo.setWorkDir(workDir);
	            	}else if (workDir!=null && workDir.charAt(workDir.length()-1 ) !='/' &&  workDir.charAt(workDir.length()-1 ) !='\\'){
	                workDir = workDir + "/";
	                ftpInfo.setWorkDir(workDir);
	            }
	            String workBackupDir = ftpInfo.getWorkBackupDir();
	            if(workBackupDir!=null && workBackupDir.charAt(workBackupDir.length()-1 ) !='/' &&  workBackupDir.charAt(workBackupDir.length()-1 ) !='\\'){
	                workBackupDir = workBackupDir + "/";
	                ftpInfo.setWorkBackupDir(workBackupDir);
	            }
	            String errorBackUpDir = ftpInfo.getErrorBackupDir();
	            if(errorBackUpDir!=null && errorBackUpDir.charAt(errorBackUpDir.length()-1 ) !='/' &&  errorBackUpDir.charAt(errorBackUpDir.length()-1 ) !='\\'){
	                errorBackUpDir = errorBackUpDir + "/";
	                ftpInfo.setErrorBackupDir(errorBackUpDir);
	            }
	            
				//logger.debug("ftpName-->"+ftpName +"\n" +ftpInfo.toString()  );
				ftpCache.put(ftpName,ftpInfo);
			}else{
				logger.info("getFtpInfo from cache {}", ftpName  );
			}
        } catch (Exception e) {
            logger.error("getFtpInfo:{}",e.getMessage());
            throw new Exception(e);
        }finally{
            if(reader!=null)
                reader.close();
        }
        long end = System.currentTimeMillis();
        logger.info("------getFtpInfo;{}cost:{}",ftpName ,end - begin);
        return ftpInfo;
    }
    
    public static void main(String[] args) throws Exception {

    	FtpFactory ff =new FtpFactory();
    	ff.getInstance();
    	FtpInfo a =	ff.getFtpInfo("pingAnTuanXian");
//    	System.out.println(a.getFtpName());
    }
    
    
    
}