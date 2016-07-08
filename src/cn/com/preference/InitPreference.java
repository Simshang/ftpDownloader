package cn.com.preference;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.com.ftphelp.Ftp;

public class InitPreference {
	
	public static Ftp initPreference(String path){
		Ftp ftp=new Ftp();
		System.out.println("Init preference from file:"+path);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line = null;
			while( (line = br.readLine()) != null) {
				if(line.startsWith("#")){
					continue;
				}
				String[] contents = line.split("=");
				if(contents.length == 2) {
					if(contents[0].trim().equals("ipaddr")) {
						ftp.setIpAddr(contents[1].trim());
					}
					if(contents[0].trim().equals("port")) {
						ftp.setPort(Integer.parseInt(contents[1].trim()));
					}
					if(contents[0].trim().equals("username")) {
						ftp.setUserName(contents[1].trim());
					}
					if(contents[0].trim().equals("pwd")) {
						ftp.setPwd(contents[1].trim());
					}
					if(contents[0].trim().equals("serverpath")) {
						ftp.setServerpath(contents[1].trim());
					}
					if (contents[0].trim().equals("downloadpath")) {
						ftp.setDownloadpath(contents[1].trim()+"/");
					}
					if (contents[0].trim().equals("backuppath")) {
						ftp.setBackuppath(contents[1].trim()+"/");
					}
					
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File path is wrong:"+path);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return ftp;
	}

}
