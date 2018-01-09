package com.alipay.util.fpfClient;

/**
 * @author wt
 *
 * ftp��Ϣ
 */
public class FtpInfo {
    
    private String ftpName;
    private String ftpDesc;
    private String ftpEnDesc;
    private String ip;
    private int port;
    private String user;
    private String password;
    private String workDir = "";
    private String workBackupDir = "";
    private String errorBackupDir = "";
    private String serverDir = "";
    private String serverBackupDir = "";
    
	public String toString(){
		StringBuffer info = new StringBuffer();
		info.append("FtpInfo:");
		info.append("|| ftpName		=").append(ftpName);
		info.append("|| ftpDesc		=").append(ftpDesc);
		info.append("|| ftpEnDesc	=").append(ftpEnDesc);
		info.append("|| ip			=").append(ip);
		info.append("|| port		=").append(port);
		info.append("|| user		=").append(user);
		info.append("|| password	=").append(password);
		info.append("|| workDir	    =").append(workDir);
		info.append("|| workBackupDir	=").append(workBackupDir);
		info.append("|| errorBackupDir	=").append(errorBackupDir);
		//info.append("|| serverBackupDir	=").append(serverBackupDir);
		return info.toString();
	}
    
    public String getFtpDesc() {
        return ftpDesc;
    }
    public void setFtpDesc(String ftpDesc) {
        this.ftpDesc = ftpDesc;
    }
    public String getFtpEnDesc() {
        return ftpEnDesc;
    }
    public void setFtpEnDesc(String ftpEnDesc) {
        this.ftpEnDesc = ftpEnDesc;
    }
    public String getFtpName() {
        return ftpName;
    }
    public void setFtpName(String ftpName) {
        this.ftpName = ftpName;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getWorkBackupDir() {
        return workBackupDir;
    }
    public void setWorkBackupDir(String localBackup) {
        this.workBackupDir = localBackup;
    }
    public String getWorkDir() {
        return workDir;
    }
    public void setWorkDir(String localDir) {
        this.workDir = localDir;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getErrorBackupDir() {
        return errorBackupDir;
    }
    public void setErrorBackupDir(String errorBackupDir) {
        this.errorBackupDir = errorBackupDir;
    }
//    public String getServerBackupDir() {
//        return serverBackupDir;
//    }
//    public void setServerBackupDir(String serverBackupDir) {
//        this.serverBackupDir = serverBackupDir;
//    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
	/**
	 * @return ���� serverDir��
	 */
	public String getServerDir() {
		return serverDir;
	}
	/**
	 * @param serverDir Ҫ���õ� serverDir��
	 */
	public void setServerDir(String serverDir) {
		this.serverDir = serverDir;
	}
	/**
	 * @return ���� serverBackupDir��
	 */
	public String getServerBackupDir() {
		return serverBackupDir;
	}
	/**
	 * @param serverBackupDir Ҫ���õ� serverBackupDir��
	 */
	public void setServerBackupDir(String serverBackupDir) {
		this.serverBackupDir = serverBackupDir;
	}
}
