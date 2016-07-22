# ftpDownloader
> ftp download and backup tools.

### 主要功能

1. 下载ftp服务器上的文件,并创建相应的`文件标签`
2. 下载完成的文件做备份
3. 下载目录按日期分类, 方便后期处理

### 使用说明
1. 在`preference`目录下创建`.conf`文件,并配置需要连接的ftp服务器参数设置
2. 进入jar包所在的目录,打开`命令行窗口`,使用`java -jar ./XXX.jar [preference path] `,比如:
   `java -jar ./ftpdownload.jar  C:\Users\Shang\IdeaProjects\ftp\preference\qcloud_preference.conf`
   