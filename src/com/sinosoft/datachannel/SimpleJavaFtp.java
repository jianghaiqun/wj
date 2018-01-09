package com.sinosoft.datachannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SimpleJavaFtp {
	private static final Logger logger = LoggerFactory.getLogger(SimpleJavaFtp.class);
	private String server;

	private String userName;

	private String password;

	private int port;

	private String fileName;

	private String ftpDir;

	public SimpleJavaFtp(String server, String userName, String password, int port) {
		this.server = server;
		this.userName = userName;
		this.password = password;
		this.port = port;
	}

	public boolean uploadFile(String filePath, String ftpDir) {
		FtpClient ftp = null;
		try {
			ftp = new FtpClient(server, port);
			ftp.login(userName, password);
			ftp.binary();
		} catch (Exception ex) {
			logger.error("FtpServer错误：" + ex.getMessage(), ex);
			return false;
		}

		if (ftpDir != null && !"".equals(ftpDir)) {
			try {
				ftp.cd(ftpDir);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		try {
			RandomAccessFile sendFile = new RandomAccessFile(filePath, "r");
			sendFile.seek(0);

			int ch;
			TelnetOutputStream outs = ftp.put(filePath.substring(filePath.lastIndexOf("/") + 1));
			DataOutputStream outputs = new DataOutputStream(outs);
			while (sendFile.getFilePointer() < sendFile.length()) {
				ch = sendFile.read();
				outputs.write(ch);
			}

			outs.close();
			sendFile.close();

			ftp.closeServer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		SimpleJavaFtp ftp = new SimpleJavaFtp("127.0.0.1", "root", "1234", 21);
		ftp.uploadFile("E:/RECEIVE_MMS/2008-08-30/13951900000_19-12-24-171/xxxx.txt", "mms");

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFtpDir() {
		return ftpDir;
	}

	public void setFtpDir(String ftpDir) {
		this.ftpDir = ftpDir;
	}
}
