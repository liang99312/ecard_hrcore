/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;

/**
 *
 * @author hflj
 */
public class StringUtil {

    private static Logger log = Logger.getLogger(StringUtil.class);
    //按字节数截取字符串

    public static String bSubString(String s, int length) throws Exception {
        byte[] bytes = s.getBytes("GBK");
        int i = 0;
        int a = 1;
        if (bytes[length] > 0) {
            i = length;
        } else {
            for (int j = 0; j < length - 1; j++) {
                if (bytes[j] < 0) {
                    j++;
                    if (j == length - 1) {
                        a = 0;
                    }
                }
            }
            i = length - a;
        }

        return new String(bytes, 0, i, "GBK");
    }

    //字符串转十六进制
    public static String toHexString(String str) {
        byte[] b = null;
        try {
            b = str.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }
        String re = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            re += hex.toUpperCase();
        }
        return re;
    }

    //十六进制转字符串
    public static String toStringHex(String s) {
        if ("0x".equals(s.substring(0, 2))) {
            s = s.substring(2);
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                log.error(e);
            }
        }

        try {
            s = new String(baKeyword, "GBK");//UTF-16le:Not
        } catch (Exception e1) {
            log.error(e1);
        }
//        s = s.replaceAll(" ", "").replaceAll("  ", "");
        return s;
    }

    public static String stringToHexString(String strPart) {
        String hexString = "";
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString = hexString + strHex;
        }
        return hexString;
    }

    /** 
    　　* Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
    
    　　* @param src byte[] data
    
    　　* @return hex string
    
    　　*/
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
    
    　　* Convert hex string to byte[]
    
    　　* @param hexString the hex string
    
    　　* @return byte[]
    
    　　*/
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
    
    　　* Convert char to byte
    
    　　* @param c char
    
    　　* @return byte
    
    　　*/
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

//    public static void main(String[] args) {
//        String str = "26 115 28 -75 106 110 124 -84 100 -97 -126 112 -119 -14 -53 -87 28 -73 69 -17 -28 -40 -93 -46 -114 -40 12 -73 64 -91 102 -18";
//        String str1 = encode(str);
//        System.out.println("ss:" + str1);
//        System.out.println("ss:" + toHexString(str));
//        str1 = decode(str1);
//        System.out.println("ss1:" + str1);
//        byte[] bytes = HexString2Bytes(encode(str));
//        str1 = "";
//        for (byte b : bytes) {
//            str1 += b + " ";
//        }
//        System.out.println("ss2:" + str1);
//    }
}
