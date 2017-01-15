/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JTree;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jhrcore.comm.HrLog;
import org.jhrcore.ui.CheckTreeNode;

/**
 *
 * @author hflj
 */
public class FileUtil {

    private static Logger log = Logger.getLogger(FileUtil.class.getName());

    public static byte[] readFileToByte(String file_path) {
        try {
            FileInputStream in = new FileInputStream(file_path);
            return readFileToByte(in);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex);
            return null;
        }
    }

    public static byte[] readFileToByte(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            return readFileToByte(in);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex);
            return null;
        }
    }

    public static byte[] readFileToByte(FileInputStream in) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] tmpbuf = new byte[1024];
            int count = 0;
            while ((count = in.read(tmpbuf)) != -1) {
                bout.write(tmpbuf, 0, count);
                tmpbuf = new byte[1024];
            }
            in.close();
            return bout.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex);
            return null;
        }
    }

    public static String readFileToStr(String file_name) {
        try {
            String privateKey = "";
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file_name)));
            String line = in.readLine();
            while (line != null) {
                privateKey = privateKey + line;
                line = in.readLine();
            }
            in.close();
            return privateKey;
        } catch (IOException ex) {
            log.error(ex);
            return null;
        }
    }

    public static InputStream getInputStreamFromWebPath(String path) {
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection conn = null;
        try {
            conn = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream instream = null;
        try {
            instream = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instream;
    }

    public static void CopyFile(InputStream fis, File out) throws Exception {
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[1024];
        int i = 0;
        while ((i = fis.read(buf)) != -1) {
            fos.write(buf, 0, i);
        }
        fis.close();
        fos.close();
    }

    public static void CopyFile(File in, File out) throws Exception {
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[1024];
        int i = 0;
        while ((i = fis.read(buf)) != -1) {
            fos.write(buf, 0, i);
        }
        fis.close();
        fos.close();
    }

    // 删除文件夹下所有内容，包括此文件夹
    public static void delAll(File f) throws IOException {
        if (!f.exists())// 文件夹不存在不存在
        {
            throw new IOException("指定目录不存在:" + f.getName());
        }

        boolean rslt = true;// 保存中间结果
        if (!(rslt = f.delete())) {// 先尝试直接删除
            // 若文件夹非空。枚举、递归删除里面内容
            File subs[] = f.listFiles();
            for (int i = 0; i <= subs.length - 1; i++) {
                if (subs[i].isDirectory()) {
                    delAll(subs[i]);// 递归删除子文件夹内容
                }
                rslt = subs[i].delete();// 删除子文件夹本身
            }
            rslt = f.delete();// 删除此文件夹本身
        }

        if (!rslt) {
            throw new IOException("无法删除:" + f.getName());
        }
        return;
    }

    //	 删除文件夹下所有内容，包括此文件夹
    public static void delAll2(File f) throws IOException {
        if (!f.exists())// 文件夹不存在不存在
        {
            throw new IOException("指定目录不存在:" + f.getName());
        }

        if (f.isDirectory()) {
            File subs[] = f.listFiles();
            for (int i = 0; i <= subs.length - 1; i++) {
                delAll2(subs[i]);// 递归删除子文件夹内容
            }
        }
        f.delete();
    }

    public static void deleteDir(String fileName) {
        File f = new File(fileName);
        try {
            delAll2(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         * if (!f.exists()) return; if (f.isFile()) { f.delete(); } else if
         * (f.isDirectory()) { if (f.list().length == 0) { f.delete(); } else {
         * for (int i = 0; i < f.list().length; i++) { deleteDir(f.getParent() +
         * "/" + f.list()[i]); } f.delete(); //deleteDir(fileName); } }
         */
    }

    public static void deleteFile(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            return;
        }
        if (f.isFile()) {
            f.delete();
            deleteFile(f.getParent());
        } else if (f.isDirectory() && f.list().length == 0) {
            // && (!f.getName().endsWith(utilPathName))) {
            f.delete();
            deleteFile(f.getParent());
        }
    }
    //根据源文件路径打开文件浏览器 

    public static void browsePath(String srcPath) {
        String[] execString = new String[2];
        String filePath = null;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("windows")) {
            // Window System;
            execString[0] = "explorer";
            filePath = srcPath.replace("/", "\\");
        } else {
            // Unix or Linux;
            execString[0] = "netscape";
            filePath = srcPath;
        }
        execString[1] = filePath;
        try {
            Runtime.getRuntime().exec(execString);
        } catch (Exception ex) {
            log.info(osName + " " + ex.toString());
        }
    }
    //打开文件浏览器 并选中定位的目标文件 有待完善

    public static void browseSelectFile(String targetDirectFileName) {
        String execString = new String();
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("windows")) {
            // Window System;
            execString = "rundll32 SHELL32.DLL,ShellExec_RunDLL explorer.exe /select,";
            //filePath = targetDirectFileName.replace("/", "\\");
            execString += targetDirectFileName;
        }
        try {
            Runtime.getRuntime().exec(execString);
        } catch (Exception ex) {
            log.info(osName + " " + ex.toString());
        }
    }

    public static String getDirectoryPath(File file) {
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory() && file.exists()) {
            return file.getPath();
        }
        File file1 = file;
        int len = 50;
        while (file1 != null && (file1.isFile() || !file1.exists()) && len > 0) {
            file1 = file1.getParentFile();
            len--;
        }
        return file1 == null ? null : file1.getPath();
    }

    public static boolean writeXML(Document doc, String path) {
        if (doc == null) {
            return false;
        }
        //生成xml
        XMLWriter writer = null;
        //设置输出格式
        OutputFormat format = new OutputFormat();
        format.setEncoding("utf-8");
        format.setIndent(true);
        format.setLineSeparator("\n");
        format.setNewlines(true);
        try {
            doc.getRootElement().asXML();
            writer = new XMLWriter(format);
            writer.setOutputStream(new FileOutputStream(path));
            writer.write(doc);
            writer.flush();
            return true;
        } catch (Exception e) {
            HrLog.error(FileUtil.class, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void getAllFilesBy(List<File> listFiles, File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            listFiles.add(file);
            return;
        }
        for (File f : file.listFiles()) {
            getAllFilesBy(listFiles, f);
        }
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * @param baseDir 指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");

        File ret = new File(baseDir);
        if (dirs.length > 1) {

            for (int i = 0; i < dirs.length - 1; i++) {
                ret = new File(ret, dirs[i]);
            }
            if (!ret.exists()) {
                ret.mkdirs();
            }
            ret = new File(ret, dirs[dirs.length - 1]);
            return ret;
        }
        return ret;
    }

    /**
     * 获取类的class文件位置的URL。
     */
    public static URL getClassLocationURL(final Class cls) {
        if (cls == null) {
            throw new IllegalArgumentException("class that input is null");
        }
        URL result = null;
        final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
        final ProtectionDomain pd = cls.getProtectionDomain();
        if (pd != null) {
            final CodeSource cs = pd.getCodeSource();
            if (cs != null) {
                result = cs.getLocation();

            }
            if (result != null) {
                if ("file".equals(result.getProtocol())) {
                    try {
                        if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip")) {
                            result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
                        } else if (new File(result.getFile()).isDirectory()) {
                            result = new URL(result, clsAsResource);
                        }
                    } catch (MalformedURLException ignore) {
                    }
                }
            }
        }

        if (result == null) {
            final ClassLoader clsLoader = cls.getClassLoader();
            result = clsLoader != null ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
        }
        return result;
    }

    public static void writeFile(String path, byte[] bytes) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            output.write(bytes);
            output.close();
        } catch (Exception ex) {
        }
    }

    public static boolean hasSamFile(File[] selectedFiles, JTree tree) {
        List<String> exists = new ArrayList();
        Enumeration enumt = ((CheckTreeNode) tree.getModel().getRoot()).breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            CheckTreeNode node = (CheckTreeNode) enumt.nextElement();
            if (node.isRoot()) {
                continue;
            }
            exists.add(node.getUserObject().toString());
        }
        for (File file : selectedFiles) {
            if (exists.contains(file.getName())) {
                return true;
            }
        }
        return false;
    }

    public static String getFileType(File file) {
        if (file == null || !file.getName().contains(".")) {
            return "";
        }
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    public static File mkdirFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        return file;
    }
}
