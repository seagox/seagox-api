package com.seagull.oa.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtils {

	/**
	 * url转变为 MultipartFile对象
	 * 
	 * @param url
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static MultipartFile createFileItem(String url, String fileName) throws Exception {
		FileItem item = null;
		try {
			HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
			httpUrl.connect();
			InputStream is = httpUrl.getInputStream();
			FileItemFactory factory = new DiskFileItemFactory(16, null);
			item = factory.createItem("file", "multipart/form-data", false, fileName);
			OutputStream os = item.getOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			is.close();
			httpUrl.disconnect();
		} catch (IOException e) {
			throw new RuntimeException("文件下载失败", e);
		}
		return new CommonsMultipartFile(item);
	}
	
	/**
	 * 将MultipartFile转换成file
	 * 
	 * @param multipartFile
	 * @return
	 */
	public static File transferToFile(MultipartFile multipartFile) {
		//选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
		File file = null;
		try {
			String originalFilename = multipartFile.getOriginalFilename();
			String[] filename = originalFilename.split("\\.");
			file = File.createTempFile(filename[0], filename[1]);
			multipartFile.transferTo(file);
			file.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
