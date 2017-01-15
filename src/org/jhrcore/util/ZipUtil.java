/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author mxliteboss
 */
public class ZipUtil {

    private static Logger log = Logger.getLogger(ZipUtil.class);

    public static boolean isZip(File file) {
        return isZip(file.getPath());
    }

    public static boolean isZip(String filePath) {
        try {
            if (!filePath.endsWith(".zip")) {
                return false;
            }
            new ZipFile(filePath);
        } catch (IOException ex) {
            log.error(ex);
            return false;
        }
        return true;
    }

    public static List<String> getFiles(File file) {
        return getFiles(file.getPath());
    }

    public static List<String> getFiles(String filePath) {
        List<String> files = new ArrayList();
        try {
            ZipFile file = new ZipFile(filePath);
            Enumeration enumt = file.entries();
            while (enumt.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) enumt.nextElement();
                files.add(entry.getName());
            }
        } catch (IOException ex) {
            log.error(ex);
        }
        return files;
    }

//    public static boolean upZip(String zipPath, String toPath) {
//        try {
//            log.error("unzipstart");
//            ZipFile zfile = new ZipFile(zipPath);
//            Enumeration zList = zfile.entries();
//            ZipEntry ze = null;
//            byte[] buf = new byte[1024];
//            while (zList.hasMoreElements()) {
//                ze = (ZipEntry) zList.nextElement();
//                if (ze.isDirectory()) {
//                    File f = new File(toPath + ze.getName());
//                    f.mkdir();
//                    continue;
//                }
//                String name = FileUtil.getRealFileName(toPath, "/" + ze.getName()).getName();
//                File file2 = new File(toPath + name);
//                InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
//                OutputStream os = new BufferedOutputStream(new FileOutputStream(file2));
//                int readLen = 0;
//                while ((readLen = is.read(buf, 0, 1024)) != -1) {
//                    os.write(buf, 0, readLen);
//                }
//                is.close();
//                os.close();
//
//            }
//            zfile.close();
//            log.error("unzipend");
//        } catch (Exception ex) {
//            log.error("unziperrror:" + ex);
//        }
//        return true;
//    }

    private static void directoryZip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            if (base.length() == 0) {
                base = "";
            } else {
                base = base + "/";
            }
            for (int i = 0; i < fl.length; i++) {
                directoryZip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            byte[] bb = new byte[10240];
            int aa = 0;
            while ((aa = in.read(bb)) != -1) {
                out.write(bb, 0, aa);
            }
            in.close();
        }
    }

    private static void fileZip(ZipOutputStream zos, File file) throws Exception {
        if (file.isFile()) {
            zos.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fis = new FileInputStream(file);
            byte[] bb = new byte[10240];
            int aa = 0;
            while ((aa = fis.read(bb)) != -1) {
                zos.write(bb, 0, aa);
            }
            fis.close();
        } else {
            directoryZip(zos, file, "");
        }
    }

    private static void fileUnZip(ZipInputStream zis, File file) throws Exception {
        ZipEntry zip = zis.getNextEntry();
        if (zip == null) {
            return;
        }
        String name = zip.getName();
        File f = new File(file.getAbsolutePath() + "/" + name);
        if (zip.isDirectory()) {
            f.mkdirs();
            fileUnZip(zis, file);
        } else {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            byte b[] = new byte[10240];
            int aa = 0;
            while ((aa = zis.read(b)) != -1) {
                fos.write(b, 0, aa);
            }
            fos.close();
            fileUnZip(zis, file);
        }
    }

    public static boolean zip(String zipBeforeFile, String zipAfterFile) {
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(FileUtil.mkdirFiles(zipAfterFile)));
            fileZip(zos, new File(zipBeforeFile));
            zos.close();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    public static boolean unZip(String unZipBeforeFile, String unZipAfterFile) {
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(unZipBeforeFile));
            File f = new File(unZipAfterFile);
            f.mkdirs();
            fileUnZip(zis, f);
            zis.close();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * 加密压缩
     * @param srcFile：源文件
     * @param destfile：加密压缩后文件
     * @param keyStr：加密密码
     * @throws Exception 
     */
    public static boolean encryptZip(String srcFile, String destfile, String keyStr) {
        try {
            File temp = new File(UUID.randomUUID().toString() + ".zip");
            temp.deleteOnExit();
            ZipUtil.zip(srcFile, temp.getAbsolutePath());
            CipherUtil.encodeByAES(temp.getAbsolutePath(), destfile, keyStr);
            temp.delete();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * 解密解压
     * @param srcfile：源文件
     * @param destfile：解密解压后文件
     * @param keyStr：解密密码
     * @throws Exception 
     */
    public static boolean decryptUnzip(String srcfile, String destfile, String keyStr) {
        try {
            File temp = new File(UUID.randomUUID().toString() + ".zip");
            temp.deleteOnExit();
            // 解密 
            CipherUtil.decodeByAES(srcfile, temp.getAbsolutePath(), keyStr);
            // 解压 
            ZipUtil.unZip(temp.getAbsolutePath(), destfile);
            temp.delete();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }
}
