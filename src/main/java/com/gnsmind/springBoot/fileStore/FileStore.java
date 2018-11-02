package com.gnsmind.springBoot.fileStore;

public class FileStore {

    private String store_path;
    private String fileName;
    private long fileSize;

	public String getStore_path() {
		return store_path;
	}

	public void setStore_path(String store_path) {
		this.store_path = store_path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileStore [store_path=" + store_path + ", fileName=" + fileName
				+ ", fileSize=" + fileSize + "]";
	}
    
	
    
}