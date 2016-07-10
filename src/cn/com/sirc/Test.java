package cn.com.sirc;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.com.ftphelp.Ftp;
import cn.com.ftphelp.FtpUtil;
import cn.com.preference.InitPreference;

public class Test {

    public static void main(String[] args) throws Exception{
        Runnable runnable = () -> {
            // task to run goes here
            Ftp f=new Ftp();
            if(args.length==1){
                String path=args[0];
                f=InitPreference.initPreference(path);
            }else{
                System.out.println("参数设置错误，请正确设置路径");
                System.exit(0);
            }
            try {
                FtpUtil.connectFtp(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("### FTPserver has connected !");
            try {
                FtpUtil.startDown(f, f.getDownloadpath(), f.getServerpath(),f.getBackuppath());//下载ftp文件测试
            } catch (Exception e) {
                e.printStackTrace();
            }
            FtpUtil.closeFtp();
            System.out.println("This task is finished");
            System.out.println("---------------------");
            System.out.println("Waiting for next task ......");
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);

   }  

}
