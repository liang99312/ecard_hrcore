package org.jhrcore.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DB2INST3
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClientIpCheck {

    public static List getPhysicalAddress() {
        Process p = null;
        //物理网卡列表
        List address = new ArrayList();
        try {
            //执行ipconfig /all命令
            p = new ProcessBuilder("ipconfig", "/all").start();
        } catch (IOException e) {
            return address;
        }
        byte[] b = new byte[1024];
        StringBuffer sb = new StringBuffer();
        //读取进程输出值
        InputStream in = p.getInputStream();
        try {
            while (in.read(b) > 0) {
                sb.append(new String(b));
            }
        } catch (IOException e1) {
        } finally {
            try {
                in.close();
            } catch (IOException e2) {
            }
        }
//以下分析输出值，得到物理网卡
        String rtValue = sb.substring(0);
        String[] ss = rtValue.split("\n");
        for (String s : ss) {
            if (s.contains("Physical Address. . . . . . . . . :")) {
                address.add(s.replace("Physical Address. . . . . . . . . :", "").replace(" ", ""));
            }
        }
        return address;
    }
    // 返回一个字节的十六进制字符串   

    private static String hexByte(byte b) {
        String s = "000000" + Integer.toHexString(b);
        return s.substring(s.length() - 2);
    }

    public static List<String> getAllMac() {
        List<String> macs = new ArrayList<String>();
        String address = "";
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                NetworkInterface nif = el.nextElement();
                byte[] mac = nif.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : mac) {
                    builder.append(hexByte(b));
                    builder.append("-");
                }
                if (builder.length() > 1) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                address = builder.toString().toUpperCase();
                if (address.equals("")||macs.contains(address)) {
                    continue;
                }
                macs.add(address);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return macs;
    }

    /**  
     * JDK1.6新特性获取网卡MAC地址  
     */
    public static String getMac() {
        String ip = getIP();
        String address = "";
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                NetworkInterface nif = el.nextElement();
                byte[] mac = nif.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : mac) {
                    builder.append(hexByte(b));
                    builder.append("-");
                }
                if (builder.length() > 1) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                address = builder.toString().toUpperCase();
                if (address.equals("") || address.startsWith("00-") || address.length() != 17) {
                    continue;
                }
                Enumeration<InetAddress> addrs = nif.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    if (addr.getHostAddress().equals(ip)) {
                        return address;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return address;
    }

//    public static String getMac() {
//        String mac_address = "";
//        List list = getPhysicalAddress();
//        for (Object obj : list) {
//            mac_address += obj.toString() + " ";
//        }
//        return mac_address;
//    }
    public static String getIP() {
        InetAddress myIPaddress = null;
        try {
            myIPaddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        }
        return (myIPaddress).getHostAddress();
    }

    public static void main(String[] args) {
//        long time1 = System.currentTimeMillis();
//        List address = ClientIpCheck.getPhysicalAddress();
//        for (Object add : address) {
//            System.out.printf("物理网卡地址:%s%n", add.toString());
//        }
//        System.out.println(getIP());
//        long time2 = System.currentTimeMillis();
////        System.out.println("time2:" + (time2 - time1));
//        String mac = getMac();
//        System.out.println("mac:"+mac);
    }
}
