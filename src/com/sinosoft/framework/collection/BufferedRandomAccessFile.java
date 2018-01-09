package com.sinosoft.framework.collection;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.sinosoft.framework.utility.FileUtil;

public class BufferedRandomAccessFile extends RandomAccessFile {
	private String fileName;

	public BufferedRandomAccessFile(String fileName, String mode) throws IOException {
		super(fileName, mode);
		this.fileName = fileName;
	}

	public void delete() {
		FileUtil.delete(this.fileName);
	}
}