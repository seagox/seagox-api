package com.seagox.oa.util;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CompressUtils {

    public static void compressFilesToZip(String folderPath, File zipFile) {
        ZipArchiveOutputStream zipOutput = null;
        try {
            zipOutput = (ZipArchiveOutputStream) new ArchiveStreamFactory()
                    .createArchiveOutputStream(ArchiveStreamFactory.ZIP, new FileOutputStream(zipFile));
            zipOutput.setEncoding("UTF-8");
            zipOutput.setUseZip64(Zip64Mode.AsNeeded);
            File[] files = new File(folderPath).listFiles();
            for (File file : files) {
                InputStream in = null;
                try {
                    if (!file.isDirectory()) {
                        in = new FileInputStream(file);
                        ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());

                        zipOutput.putArchiveEntry(entry);
                        IOUtils.copy(in, zipOutput);
                        zipOutput.closeArchiveEntry();
                    }
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            zipOutput.finish();
            zipOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (zipOutput != null) {
                try {
                    zipOutput.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 把zip文件解压到指定的文件夹
     */
    public static void decompressZip(String zipFilePath, String sourceFilePath) {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            sourceFile.mkdirs();
        }
        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<String>();
        try {
            is = new ZipArchiveInputStream(new FileInputStream(zipFilePath));
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());
                if (!entry.isDirectory()) {
                    OutputStream os = null;
                    try {
                        os = new FileOutputStream(new File(sourceFilePath, entry.getName()));
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
