/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author 2013-03-12jiang
 */
/**
 * 对密码进行加密和验证的类
 */
public class CipherUtil {

    //十六进制下数字到字符的映射数组
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};//md5加密
    private final static String type = "AES";//aes加密
//    /**
//     * 验证输入的密码是否正确
//     *
//     * @param password 加密后的密码
//     * @param inputString 输入的字符串
//     * @return 验证结果，TRUE:正确 FALSE:错误
//     */
//    public static boolean validatePassword(String password, String inputString) {
//        if (password.equals(encodeByMD5(inputString))) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * 对字符串进行MD5加密
     */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                //创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                //将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * MD5转换字节数组为十六进制字符串
     *
     * @param 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * MD5将一个字节转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 使用AES对文件进行加密
     * @param srcFile：源文件路径
     * @param destFile：加密后文件
     * @param privateKey：加密密码
     * @throws GeneralSecurityException
     * @throws IOException 
     */
    public static void encodeByAES(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
        Key key = getAESKey(privateKey);
        Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(FileUtil.mkdirFiles(destFile));
            cryptAES(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 使用AES对文件进行解密
     * @param srcFile：源文件
     * @param destFile：解密后文件
     * @param privateKey：解密密码
     * @throws GeneralSecurityException
     * @throws IOException 
     */
    public static void decodeByAES(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
        Key key = getAESKey(privateKey);
        Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(FileUtil.mkdirFiles(destFile));
            cryptAES(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static Key getAESKey(String secret) throws GeneralSecurityException {
        KeyGenerator kgen = KeyGenerator.getInstance(type);
        kgen.init(128, new SecureRandom(secret.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        return secretKey;
    }

    private static void cryptAES(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1000;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0) {
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        } else {
            outBytes = cipher.doFinal();
        }
        out.write(outBytes);
    }
}
