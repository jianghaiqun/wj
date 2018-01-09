package com.sinosoft.datachannel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.sinosoft.framework.utility.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

public class SFtp {
	private static final Logger logger = LoggerFactory.getLogger(SFtp.class);

	ChannelSftp sftp = null;

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws JSchException 
	 */
	public void connect(String host, int port, String username,String password) throws JSchException{
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			logger.info("SFTP：{} 开始连接...", host);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			logger.info("SFTP：{} 连接建立...", host);
	}
	
	public void disconnect(){
		if (sftp == null) {
			return;
		}

		if (sftp.isConnected()) {
			try {
				sftp.getSession().disconnect();
				sftp.disconnect();
			} catch (JSchException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 * @throws SftpException 
	 * @throws FileNotFoundException 
	 */
	public boolean upload(String srcFile, String tarFile) throws Exception{
		//System.out.println("upload file " + srcFile + " to " + tarFile);
		boolean flag = true;
		String path = tarFile.substring(0, tarFile.lastIndexOf("/"));
		path = path.replaceAll("///", "/");
		path = path.replaceAll("//", "/");
		
		mkdir(path);

		sftp.cd(path);
		File file = new File(srcFile);
		FileInputStream fis = new FileInputStream(file);
		sftp.put(fis, tarFile);
		fis.close();
		
		//System.out.println("文件SFTP上传成功:"+srcFile);
		return flag;
	}
	
	/**
	 * 建立多层目录
	 * @param path
	 * @return
	 */
	public boolean mkdir(String path) {
		boolean flag = true;
		try {
			sftp.cd(path);
		} catch (SftpException e) {
			try {
				sftp.mkdir(path);
			} catch (SftpException e1) {
				String parentPath = path.substring(0, path.lastIndexOf("/"));
				mkdir(parentPath);
				try {
					sftp.mkdir(path);
				} catch (SftpException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile,
			String saveFile) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public boolean delete(String deleteFile) {
		boolean flag = true;
		try {
			sftp.rm(deleteFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

	public static void main(String[] args) {
		SFtp sf = new SFtp();
		String host = "10.192.18.195";
		int port = 22;
		String username = "";
		String password = "";
		String targetFile = "/app/was6/sftp/wwwroot/Image/gahdzpzs/5542583.gif";
		String uploadFile = "F:/workspace_ibm/cms/UI/wwwroot/cpic/upload/Image/gahdzpzs/5542583.gif";
		try {
			sf.connect(host, port, username, password);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		try {
			sf.upload(uploadFile, targetFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//sf.delete(deleteFile);
	    sf.disconnect();
	}
}