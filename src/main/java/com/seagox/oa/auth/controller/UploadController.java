package com.seagox.oa.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.auth.serivce.IAuthService;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.service.IJellyPrintService;
import com.seagox.oa.util.DocumentConverterUtils;
import com.seagox.oa.util.UploadUtils;
import com.seagox.oa.excel.mapper.JellyMetaFunctionMapper;
import com.seagox.oa.excel.mapper.JellyMetaPageMapper;
import com.seagox.oa.excel.mapper.JellyTemplateEngineMapper;
import com.seagox.oa.excel.entity.JellyMetaFunction;
import com.seagox.oa.excel.entity.JellyMetaPage;
import com.seagox.oa.excel.entity.JellyTemplateEngine;
import com.seagox.oa.util.CompressUtils;
import com.seagox.oa.util.FileUtils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

/**
 * 文件上传下载
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${oss.type}")
    private Integer ossType;

    @Value("${oss.endpoint}")
    private String ossEndpoint;

    @Value("${oss.access-key}")
    private String ossAccessKey;

    @Value("${oss.secret-key}")
    private String ossSecretKey;
    
    @Value("${jodconverter.working-dir}")
    private String uploadPath;

    @Autowired
    private DocumentConverterUtils documentConverterUtils;
    
    @Autowired
	private IAuthService authService;
    
    @Autowired
    private IJellyPrintService printService;
    
    @Autowired
    private JellyMetaFunctionMapper metaFunctionMapper;

    @Autowired
    private JellyMetaPageMapper metaPageMapper;

    @Autowired
    private JellyTemplateEngineMapper templateEngineMapper;

    /**
     * 文件上传
     */
    @SysLogPoint("文件上传")
    @PostMapping("/putObject/{bucketName}")
    public ResultData putObject(@RequestParam("file") MultipartFile file, @PathVariable String bucketName, Long companyId) {
        String address = null;
        bucketName = "sea-" + bucketName;
        if (ossType == 1) {
            address = UploadUtils.minioOss(ossEndpoint, ossAccessKey, ossSecretKey, file, bucketName);
        } else if (ossType == 2) {
            address = UploadUtils.aliyunOss(ossEndpoint, ossAccessKey, ossSecretKey, file, bucketName);
        } else if (ossType == 3) {
            address = UploadUtils.ecloudOss(ossEndpoint, ossAccessKey, ossSecretKey, file, bucketName);
        }
        if (StringUtils.isEmpty(address)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "文件服务器配置有误");
        } else {
            return ResultData.success(address);
        }
    }

    /**
     * 下载文件
     *
     * @param response
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response, String url, String fileName) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/octet-stream;charset=UTF-8");
            inputStream = new URL(url).openStream();
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载模版
     *
     * @param response
     */
    @PostMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response, String templateName) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((templateName).getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/octet-stream;charset=UTF-8");
            Resource resource = new ClassPathResource("\\template\\" + templateName);
            inputStream = resource.getInputStream();
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 在线预览
     *
     * @param url      文件路径
     * @param fileName 文件名称
     * @param req      请求参数
     * @return
     */
    @GetMapping("/preview")
    public void preview(String url, String fileName, HttpServletRequest req, HttpServletResponse response) throws IOException {
        //获取文件
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedReader bis = null;
        try {
            String prefix = fileName.substring(0, fileName.lastIndexOf("."));
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            //使用response,将pdf文件以流的方式发送的前端浏览器上
            response.setHeader("Content-Disposition", "filename=" + new String((prefix + ".pdf").getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/pdf");
            outputStream = response.getOutputStream();

            inputStream = new URL(url).openStream();
            documentConverterUtils.convert(suffix, inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    
    /**
     * 导入
     */
    @PostMapping("/import")
    public ResultData importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
    	return authService.importExcel(file, request);
    }
    
    /**
	 * 打印
	 */
	@GetMapping("/print/{id}")
	public void preview(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
        	String path = printService.preview(id, request);
        	//使用response,将pdf文件以流的方式发送的前端浏览器上
            response.setHeader("Content-Disposition", "filename=" + new String(("test.pdf").getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/pdf");
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(path);
            String suffix = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
            documentConverterUtils.convert(suffix, inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 * 打印下载
	 */
	@GetMapping("/printDownload/{id}")
	public void printDownload(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
        	//使用response,将pdf文件以流的方式发送的前端浏览器上
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((request.getParameter("filename")).getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/octet-stream;charset=UTF-8");
            printService.download(id, request);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
     * 元函数,元页面,模板引擎导出
     */
    @PostMapping("/exportScript")
    public void exportScript(HttpServletRequest request, HttpServletResponse response) {
        String exportPath = uploadPath + "\\" + "exportFile\\";
        File zipFile = null;
        String filePath = null;
        if ("元函数".equals(request.getParameter("type"))) {
            LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyMetaFunction::getCompanyId, request.getParameter("companyId"));
            List<JellyMetaFunction> listMx = metaFunctionMapper.selectList(qw);
            for (JellyMetaFunction mx : listMx) {
                filePath = exportPath + mx.getPath() + ".java";
                FileUtils.fileLinesWrite(filePath, mx.getScript(), false);
            }
            FileUtils.mkDir(uploadPath);
            // 指定打包到哪个zip（绝对路径）
            zipFile = new File(uploadPath + "\\" + "元函数.zip");
        } else if ("元页面".equals(request.getParameter("type"))) {
            LambdaQueryWrapper<JellyMetaPage> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyMetaPage::getCompanyId, request.getParameter("companyId"));
            List<JellyMetaPage> listMx = metaPageMapper.selectList(qw);
            for (JellyMetaPage mx : listMx) {
                filePath = exportPath + mx.getPath() + ".vue";
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(mx.getHtml());
                stringBuffer.append(mx.getJs());
                stringBuffer.append(mx.getCss());
                FileUtils.fileLinesWrite(filePath, stringBuffer.toString(), false);
            }
            FileUtils.mkDir(uploadPath);
            // 指定打包到哪个zip（绝对路径）
            zipFile = new File(uploadPath + "\\" + "元页面.zip");
        } else if ("模板引擎".equals(request.getParameter("type"))) {
            LambdaQueryWrapper<JellyTemplateEngine> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyTemplateEngine::getCompanyId, request.getParameter("companyId")).orderByAsc(JellyTemplateEngine::getPath);
            List<JellyTemplateEngine> listMx = templateEngineMapper.selectList(qw);
            for (JellyTemplateEngine mx : listMx) {
                filePath = exportPath + mx.getPath() + ".xml";
                FileUtils.fileLinesWrite(filePath, mx.getScript(), false);
            }
            FileUtils.mkDir(uploadPath);
            // 指定打包到哪个zip（绝对路径）
            zipFile = new File(uploadPath + "\\" + "模板引擎.zip");
        }
        CompressUtils.compressFilesToZip(exportPath, zipFile);
        String path = uploadPath + "\\" + request.getParameter("type") + ".zip";
        // path是指欲下载的文件的路径。
        File file = new File(path);
        // 取得文件名。
        String filename = file.getName();
        FileUtils.downloadFile(request, response, filename, path);
        FileUtils.deleteEveryThing(exportPath);
        FileUtils.deleteEveryThing(path);
    }

}
