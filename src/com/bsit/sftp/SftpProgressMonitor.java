package com.bsit.sftp;

/**
 * 
 * Title:SftpProgressMonitor
 * Description: 监控传输进度，此类由JSch提供。此接口定义如下：
 * @author chenrl
 * 2016年3月18日上午10:59:48
 */
public interface SftpProgressMonitor {

	public static final int PUT=0;
	public static final int GET=1;
	void init(int op, String src, String dest, long max);//当文件开始传输时，调用init方法。
	boolean count(long count);	//当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小。调用太频繁
	void end();	//当传输结束时，调用end方法。
	  
}
