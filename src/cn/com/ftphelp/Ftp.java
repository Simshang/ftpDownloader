package cn.com.ftphelp;

/**
 * ftp链接常量
 *
 */
public class Ftp {

    private String ipAddr;//ip地址
    
    private Integer port;//端口号
    
    private String userName;//用户名
    
    private String pwd;//密码
    
    private String path;//aaa路径
    

	private String serverpath;//server地址

	private String downloadpath;//下载路径

    private String backuppath;//备份路径

    public String getIpAddr() {
        return ipAddr;
    }
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getServerpath() {
		return serverpath;
	}
    public void setServerpath(String serverpath) {
		this.serverpath = serverpath;
	}

    public String getDownloadpath() {
  		return downloadpath;
  	}
  	public void setDownloadpath(String downloadpath) {
  		this.downloadpath = downloadpath;
  	}

    public String getBackuppath() {
        return backuppath;
    }
    public void setBackuppath(String backuppath) {
        this.backuppath = backuppath;
    }
    
}