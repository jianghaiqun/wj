/**
 * Project Name:wj
 * File Name:FtpAction.java
 * Package Name:com.alipay.util.fpfClient
 * Date:2016年11月14日下午7:09:32
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.alipay.util.fpfClient;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.util.Properties;
import java.util.Vector;

/**
 * ClassName:FtpAction <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2016年11月14日 下午7:09:32 <br/>
 *
 */
public class SFtpAction {
	private static final Logger logger = LoggerFactory.getLogger(SFtpAction.class);
	
	/***
	 * 
	 * getRateFileInfoByFtp:登录SFtp. <br/>
	 *
	 * @author taoqiwen
	 * @param ftpName
	 * @return
	 * @throws Exception
	 */
	
    public static ChannelSftp getRateFileInfoByFtp(String ftpName) throws Exception{
    	

    	ChannelSftp sftp = null;
    	try{
    		
    		JSch jsch = new JSch();
    		FtpInfo ftpInfo = FtpFactory.getInstance().getFtpInfo(ftpName); 
			
			Session sshSession = jsch.getSession(ftpInfo.getUser(), ftpInfo.getIp(), ftpInfo.getPort());
			sshSession.setPassword(ftpInfo.getPassword());
			
			Properties sshConfig = new Properties();
			
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			 
    	}catch (ConnectException e) {
//		    logger.error("inquireRateFtpFile   ftp连接有误，请检查");
    		sftp.put("SFTP_ERROR","ftp连接有误，请检查");
		    logger.error(e.getMessage(), e);
		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
			sftp.put("SFTP_ERROR","ftp连接有误，请检查" + e.getMessage() );
			logger.error(e.getMessage(), e);
		} 
    	
		return sftp;
    }
    
    
    /**
    * 列出目录下的文件
    * @param directory 要列出的目录
    * @param sftp
    * @return
    * @throws SftpException
    */
    public static Vector listFiles(String directory, ChannelSftp sftp) throws SftpException{
    
    	return sftp.ls(directory);
    }
    
 
    
   /**
    * 下载文件
    * @param directory 下载目录
    * @param downloadFile 下载的文件
    * @param saveFile 存在本地的路径
    * @param sftp
    */
	public static void download(String directory, String downloadFile,String svaeDirectory,
			String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(svaeDirectory,saveFile);
			file.getParentFile().mkdirs();
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

    public static void main(String[] args) {
    	try {
			
    		ChannelSftp a =SFtpAction.getRateFileInfoByFtp("pingAnTuanXian");
//    		Vector aa =FtpAction.listFiles("/wls/paicsftp/sftp4fkxb/", a);
    		SFtpAction.download("/wls/paicsftp/sftp4fkxb/","2016.txt","ftp","D:\\aa1.txt",a); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
	}

    		  
	

}

