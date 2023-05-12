package com.seagox.oa.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("deprecation")
public class FileUtils {
	public final static Logger logger = LoggerFactory.getLogger(FileUtils.class);
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
	/**
	 * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
	 *
	 * @param filePath
	 * @param content
	 * @param flag     true:如果文件存在且存在内容，则内容换行追加；false:如果文件存在且存在内容，则内容替换
	 */
	public static String fileLinesWrite(String filePath, String content, boolean flag) {
		String filedo = "write";
		FileWriter fw = null;
		PrintWriter pw = null;
		boolean boolenz = true;
		try {
			File file = new File(filePath);

			// 如果文件夹不存在，则创建文件夹
			if (!file.getParentFile().exists()) {
				boolenz = file.getParentFile().mkdirs();
			}
			if (!boolenz) {
				throw new IOException("抛出异常");
			}
			if (!file.exists()) {// 如果文件不存在，则创建文件,写入第一行内容
				file.createNewFile();
				fw = new FileWriter(file);
				filedo = "create";
			} else {// 如果文件存在,则追加或替换内容
				fw = new FileWriter(file, flag);
			}
			if (fw != null) {
				pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
			}
		} catch (IOException e) {
			logger.info("IOException");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (NullPointerException e) {
					logger.info("NullPointerException", e);
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.info("IOException", e);
				}
			}
		}

		return filedo;
	}
	// https://www.cnblogs.com/chenhuan001/p/6575053.html
	/**
	 * 递归删除文件或者目录
	 *
	 * @param file_path
	 */
	public static void deleteEveryThing(String file_path) {
		Boolean booleanz = true;
		try {
			File file = new File(file_path);
			if (!file.exists()) {
				return;
			}
			if (file.isFile()) {
				booleanz = file.delete();
			} else {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					String root = files[i].getAbsolutePath();// 得到子文件或文件夹的绝对路径
					deleteEveryThing(root);
				}
				booleanz = file.delete();
			}
			if (!booleanz) {
				throw new IOException("抛出异常");
			}
		} catch (IOException e) {
			logger.info("删除文件失败");
		}
	}
	/**
	 * 创建目录
	 *
	 * @param dir_path
	 */
	public static void mkDir(String dir_path) {
		File myFolderPath = new File(dir_path);
		try {
			if (!myFolderPath.exists()) {
				boolean isSuccess = myFolderPath.mkdir();
				if (!isSuccess) {
					logger.error("新建目录操作出错");
				}
			}
		} catch (NullPointerException e) {
			logger.error("新建目录操作出错");
		}
	}
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String name, String path) {
		FileInputStream fis = null;
		try {
			String realPath = path;
			File file = new File(realPath);
			fis = new FileInputStream(file);

			String userAgent = getBrowser(request);

			if (StringUtils.isNotBlank(name)) {
				if ("FF".equals(userAgent) || "CE".equals(userAgent) || "SF".equals(userAgent)) {
					name = new String(name.getBytes("GB2312"), "iso-8859-1");
				} else {
					name = URLEncoder.encode(name, "UTF-8");
				}
				name = StringEscapeUtils.unescapeHtml4(name);
			}

			// 过滤http响应截断
			name = saveFilter(name);

			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=" + name);
			response.setContentType("application/octet-stream;charset=UTF-8");
			IOUtils.copy(fis, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static String getBrowser(HttpServletRequest request) {

		String userAgent = request.getHeader("USER-AGENT");
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.indexOf("chrome") >= 0)
				return "CE";
			if (userAgent.indexOf("msie") >= 0)
				return "IE";
			if (userAgent.indexOf("firefox") >= 0)
				return "FF";
			if (userAgent.indexOf("safari") >= 0)
				return "SF";
		}
		return "";
	}
	public static String  saveFilter(String value){
		value = value.replaceAll("script", "");
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		//value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("\r","");
		value = value.replaceAll("\n","");
		value = value.replaceAll("\r\n","");
		value = value.replaceAll("'","");
		value = value.replaceAll("\t","");
		Pattern scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
		value = scriptPattern.matcher(value).replaceAll("");
		return value;
	}
}
