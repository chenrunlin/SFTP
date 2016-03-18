package com.bsit.sftp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;

public class SFTPTest {
	
	public SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }
	
	/**
     * @param args
	 * @throws Exception 
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
		
		SFTPTest test = new SFTPTest();

        Map<String, String> sftpDetails = new HashMap<String, String>();
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "192.168.1.19");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "uftp");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "123456");
        
        /**
         * 将本地文件名为src的文件上传到目标服务器，目标文件名为dst，
         * 若dst为目录，则目标文件名将与src文件名相同。
         */
        String src = "e:\\Program Files\\ubuntu-15.04-desktop-i386.iso"; // 本地文件名
        String dst = "/home/uftp/"; // 目标文件名
              
        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        
        File file = new File(src);
        long fileSize = file.length();
        
        /**
         * 具体上传的方法：
         * http://www.cnblogs.com/longyg/archive/2012/06/25/2556576.html
         */
        
        /**
         * 代码段1：采用向put方法返回的输出流中写入数据的方式来传输文件。
         * 需要由程序来决定写入什么样的数据，这里是将本地文件的输入流写入输出流。
         * 采用这种方式的好处是，可以自行设定每次写入输出流的数据块大小.
         * 如本示例中的语句：byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
         * 代码段1 : start:
        OutputStream out = chSftp.put(dst, ChannelSftp.OVERWRITE); // 如果不需要监控传输进度，使用OVERWRITE模式
        OutputStream out = chSftp.put(dst, new FileProgressMonitor(), ChannelSftp.OVERWRITE); // 如果要监控传输进度,使用OVERWRITE模式
        byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
        int read;
        if (out != null) {
            System.out.println("Start to read input stream");
            InputStream is = new FileInputStream(src);
            do {
                read = is.read(buff, 0, buff.length);
                if (read > 0) {
                    out.write(buff, 0, read);
                }
                out.flush();
            } while (read >= 0);
            System.out.println("input stream read done.");
        }
                    代码段1 : end **/
        
        /**
         * 直接将本地文件名为src的文件上传到目标服务器，目标文件名为dst。
         * （注：使用这个方法时，dst可以是目录，当dst是目录时，上传后的目标文件名将与src文件名相同）
         * 代码段2 : start  
        chSftp.put(src, dst, ChannelSftp.OVERWRITE); 代码段2 :end */   
        chSftp.put(src, dst, new FileProgressMonitor(fileSize), ChannelSftp.OVERWRITE); // 如果要监控传输进度,
          	       
        /**
         * 将本地文件名为src的文件输入流上传到目标服务器，目标文件名为dst。
         * 代码段3 : start
        chSftp.put(new FileInputStream(src), dst, ChannelSftp.OVERWRITE); // 代码段3
        chSftp.put(new FileInputStream(src), dst, new SFTPProgressMonitorImpl(), ChannelSftp.OVERWRITE); // 如果要监控传输进度,
                      代码段3 : end*/
        
        
        /**
         * 这三段代码实现的功能是一样的，都是将本地的文件src上传到了服务器的dst文件
         */
        
        chSftp.quit();
        channel.closeChannel();
	}

}
