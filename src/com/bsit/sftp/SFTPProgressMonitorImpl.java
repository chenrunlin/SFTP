package com.bsit.sftp;

import com.jcraft.jsch.SftpProgressMonitor;
/**
 * 
 * Title:SFTPProgressMonitorImpl
 * Description: 简单实现
 * @author chenrl
 * 2016年3月18日上午11:24:53
 */
public class SFTPProgressMonitorImpl implements SftpProgressMonitor {
	
	private long transfered;

	public void init(int op, String src, String dest, long max) {
		 System.out.println("Transferring begin.");
	}

	public boolean count(long count) {
		transfered = transfered + count;
        System.out.println("Currently transferred total size: " + transfered + " bytes");
        return true;
	}

	public void end() {
		System.out.println("Transferring done.");
	}

}
