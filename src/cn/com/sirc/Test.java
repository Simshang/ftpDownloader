package cn.com.sirc;

import java.io.File;

import cn.com.ftphelp.Ftp;
import cn.com.ftphelp.FtpUtil;
import cn.com.preference.InitPreference;

public class Test {

    public static void main(String[] args) throws Exception{  
    	Ftp f=new Ftp();
    	if(args.length==1){
    		String path=args[0];
    		f=InitPreference.initPreference(path);
    	}else{
    		System.out.println("参数设置错误，请正确设置路径");
    		System.exit(0);
    	}
        FtpUtil.connectFtp(f);
        System.out.println("### FTPserver has connected !");
        FtpUtil.startDown(f, f.getDownloadpath(), f.getServerpath(),f.getBackuppath());//下载ftp文件测试
        FtpUtil.closeFtp();
        System.out.println("This task is finished");
        System.out.println("---------------------");
        System.out.println("Waiting for next task ......");
   }  

}
