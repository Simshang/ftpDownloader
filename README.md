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
3. 在备份文件夹内生成`.ok`文件进行文件备份成功标识
   
### 协议流程

1. [ftpUploader](https://github.com/Simshang/ftpUploader)上传工具将将文件上传成功后会生成`filename.ok`文件作为上传成功标签

2. 本下载工具下载完成后会生成`filename.ok.dwd`文件作为下载成功标签
   