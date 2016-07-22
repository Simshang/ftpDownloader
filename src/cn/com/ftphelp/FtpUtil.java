package cn.com.ftphelp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtil {
    
    private static Logger logger=Logger.getLogger(FtpUtil.class);
    
    private static FTPClient ftp;
    
    /**
     * 获取ftp连接
     * @param f
     * @return
     * @throws Exception
     */
    public static boolean connectFtp(Ftp f) throws Exception{
        ftp=new FTPClient();
        boolean flag=false;
        int reply;
        if (f.getPort()==null) {
            ftp.connect(f.getIpAddr(),21);
        }else{
            ftp.connect(f.getIpAddr(),f.getPort());
        }
        ftp.login(f.getUserName(), f.getPwd());
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
              ftp.disconnect();      
              return flag;      
        } 
        //ftp.makeDirectory("hello");
        ftp.changeWorkingDirectory(f.getPath());      
        flag = true;      
        return flag;
    }
    
    /**
     * 关闭ftp连接
     */
    public static void closeFtp(){
        if (ftp!=null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * ftp上传文件
     * @param f
     * @throws Exception
     */
    public static void upload(File f) throws Exception{
        if (f.isDirectory()) {
            ftp.makeDirectory(f.getName());
            ftp.changeWorkingDirectory(f.getName());
            String[] files=f.list();
            for(String fstr : files){
                File file1=new File(f.getPath()+"/"+fstr);
                if (file1.isDirectory()) {
                    upload(file1);
                    ftp.changeToParentDirectory();
                }else{
                    File file2=new File(f.getPath()+"/"+fstr);
                    FileInputStream input=new FileInputStream(file2);
                    ftp.storeFile(file2.getName(),input);
                    input.close();
                }
            }
        }else{
            File file2=new File(f.getPath());
            FileInputStream input=new FileInputStream(file2);
            ftp.storeFile(file2.getName(),input);
            input.close();
        }
    }
    
    /**
     * 下载链接配置
     * @param f
     * @param localBaseDir 本地目录
     * @param remoteBaseDir 远程目录
     * @throws Exception
     */
    public static void startDown(Ftp f,String localBaseDir,String remoteBaseDir,String backuppath ) throws Exception{
        if (FtpUtil.connectFtp(f)) {
            
            try { 
                FTPFile[] files = null; 
                boolean changedir = ftp.changeWorkingDirectory(remoteBaseDir); 
                if (changedir) { 
                    ftp.setControlEncoding("GBK");
                    ftp.enterLocalPassiveMode();
                    files = ftp.listFiles(); 
                    for (int i = 0; i < files.length; i++) { 
                    	//判断目录，去除. 和 ..目录
						if (files[i].getName().equals(".") || files[i].getName().equals(".."))
							continue;
                        try{ 
                            downloadAndcopyFile(files[i], localBaseDir, remoteBaseDir,backuppath);
                        }catch(Exception e){ 
                            logger.error(e); 
                            logger.error("<"+files[i].getName()+">下载失败"); 
                        } 
                    } 
                } 
            } catch (Exception e) { 
                logger.error(e); 
                logger.error("下载过程中出现异常"); 
            } 
        }else{
            logger.error("链接失败！");
        }
        
    }
    
    
    /** 
     * 
     * 下载FTP文件 
     * 当你需要下载FTP文件的时候，调用此方法 
     * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载 
     * 
     * @param ftpFile 
     * @param relativeLocalPath 
     * @param relativeRemotePath 
     */ 
    private  static void downloadAndcopyFile(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath,String backuppath) {
        if (ftpFile.isFile()) {
            //if file
            if (ftpFile.getName().indexOf("?") == -1) { 
				OutputStream outputStream = null;
				String prefix = ftpFile.getName().substring(ftpFile.getName().lastIndexOf(".") + 1);
				try {
					//判断以ok结尾
					if (prefix.equals("ok")) {
						String realfilename =ftpFile.getName().substring(0, ftpFile.getName().lastIndexOf("."));
						File locaFile = new File(relativeLocalPath + realfilename);
						//判断文件在ftp是否存在，否则返回
						File backupFile = new File(backuppath+realfilename);
						
						// 判断文件在本地是否存在，存在则返回
						if (locaFile.exists()) {
							return;
						} else {
							outputStream = new FileOutputStream(relativeLocalPath + realfilename);
							ftp.retrieveFile(realfilename, outputStream);
							outputStream.flush();
							outputStream.close();
                            System.out.println(realfilename+" is downloaded");

							copyFileUsingFileChannels(locaFile,backupFile);
                            System.out.println(realfilename+" is backuped");
                            // output time
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                            String CurrentTime = df.format(new Date());
                            System.out.println(CurrentTime);// new Date()为获取当前系统时间
							//删除服务器的数据
//							ftp.deleteFile(realfilename);
//							ftp.deleteFile(ftpFile.getName());
							ftp.rename(ftpFile.getName(), ftpFile.getName()+".dwd");
							
						}
					}
                } catch (Exception e) { 
                    logger.error(e);
                } finally { 
                    try { 
                        if (outputStream != null){ 
                            outputStream.close(); 
                        }
                    } catch (IOException e) { 
                       logger.error("输出文件流异常"); 
                    } 
                } 
            } 
        } else {
            // if foder
            String newlocalRelatePath = relativeLocalPath + ftpFile.getName(); 
            String newRemote = new String(relativeRemotePath+ "/" +ftpFile.getName().toString());
            String newbackuppath = new String(backuppath+ftpFile.getName().toString());
            File fl = new File(newlocalRelatePath); 
            File bl = new File(newbackuppath);
            if (!fl.exists()) {
                fl.mkdirs(); 
            }
            if (!bl.exists()) {
                bl.mkdirs();
            }
            try { 
                newlocalRelatePath = newlocalRelatePath + '/'; 
                newRemote = newRemote + "/";
                newbackuppath = newbackuppath +"/";
                String currentWorkDir = ftpFile.getName().toString(); 
                boolean changedir = ftp.changeWorkingDirectory(currentWorkDir); 
                if (changedir) { 
                    FTPFile[] files = null; 
                    files = ftp.listFiles(); 
                    for (int i = 0; i < files.length; i++) { 
                    	//判断目录，去除. 和 ..目录
						if (files[i].getName().equals(".") || files[i].getName().equals(".."))
							continue;
                        downloadAndcopyFile(files[i], newlocalRelatePath, newRemote, newbackuppath);
                    } 
                } 
                if (changedir){
                    ftp.changeToParentDirectory(); 
                } 
            } catch (Exception e) { 
                logger.error(e);
            } 
        } 
    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
    
}