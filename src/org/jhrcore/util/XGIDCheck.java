/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class XGIDCheck {
    
    private static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
    // 每位加权因子  
    private static int power[] = {7, 6, 5, 4, 3, 2};

//    public static void main(String[] args) {
//        System.out.println(new XGIDCheck().checkId("Z000133(4)"));//B584693(0)"));
//    }
    /** 
     * 每个键盘对应的值
     *  
     * @param  
     * @return 
     * @throws 
     */
    private static void hastTableSwp() {
        if (numbers.keySet().size() > 0) {
            return;
        }
        numbers.put("", 58);
        numbers.put("I", 18);
        numbers.put("R", 27);
        numbers.put("A", 10);
        numbers.put("B", 11);
        numbers.put("C", 12);
        numbers.put("D", 13);
        numbers.put("E", 14);
        numbers.put("F", 15);
        numbers.put("G", 16);
        numbers.put("H", 17);
        numbers.put("J", 19);
        numbers.put("K", 20);
        numbers.put("L", 21);
        numbers.put("M", 22);
        numbers.put("N", 23);
        numbers.put("O", 24);
        numbers.put("P", 25);
        numbers.put("Q", 26);
        numbers.put("S", 28);
        numbers.put("T", 29);
        numbers.put("U", 30);
        numbers.put("V", 31);
        numbers.put("W", 32);
        numbers.put("X", 33);
        numbers.put("Y", 34);
        numbers.put("Z", 35);
    }
    
    public static boolean checkId(String text1) {
        hastTableSwp();
        String text = text1;
        int x = 58;
        int sum = 0;
        int idcrad18 = 0;
        int checkCode1 = 0;
        int number = 0;
        String checkCode = "";
        if (text.length() == 10) {
            String str = text.substring(0, 1);
            try {
                number = numbers.get(str);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            
            String idcard17 = text.substring(1, 7);
            String idcard18 = text.substring(8, 9);
            
            try {
                idcrad18 = Integer.parseInt(idcard18);
            } catch (Exception e) {
                e.printStackTrace();
                idcrad18 = 10;
            }
            char c[] = idcard17.toCharArray();
            if (c != null) {
                int bit[] = new int[idcard17.length()];
                bit = converCharToInt(c);
                int sum1 = 0;
                sum1 = getPowerSum(bit);
                sum = x * 9 + number * 8 + sum1;
                // 获取和值与11取摸得到余数进行校验码
                checkCode = getCheckCodeBySum(sum);
                checkCode1 = Integer.parseInt(checkCode);
                if (idcrad18 == checkCode1) {
                    return true;
                }
            }
            return false;
        } else if (text.length() == 11) {
            String str = text.substring(0, 1);
            String str1 = text.substring(1, 2);
            try {
                x = numbers.get(str);
                number = numbers.get(str1);
            } catch (Exception e) {
                return false;
            }
            String idcard17 = text.substring(1, 7);
            String idcard18 = text.substring(8, 9);
            
            try {
                idcrad18 = Integer.parseInt(idcard18);
            } catch (Exception e) {
                idcrad18 = 10;
            }
            char c[] = idcard17.toCharArray();
            if (c != null) {
                int bit[] = new int[idcard17.length()];
                bit = converCharToInt(c);
                int sum1 = 0;
                sum1 = getPowerSum(bit);
                sum = x * 9 + number * 8 + sum1;
                // 获取和值与11取摸得到余数进行校验码
                checkCode = getCheckCodeBySum(sum);
                checkCode1 = Integer.parseInt(checkCode);
                if (idcrad18 == checkCode1) {
                    return true;
                }
            }
            return false;
        }
        
        return false;
    }

    /** 
     * 将字符数组转成整形数组
     *  
     * @param c 
     * @return 
     * @throws NumberFormatException 
     */
    private static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    /** 
     * 将2-6位身份证的每位和对应位的加权因子相乘之后再得到和值
     *  
     * @param bit 
     * @return 
     */
    private static int getPowerSum(int[] bit) {
        int sum = 0;
        if (power.length != bit.length) {
            return sum;
        }
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    /** 
     * 将和值与11取模得到余数进行校验码判断
     *  
     * @param checkCode 
     * @param sum 
     * @return 校验位
     */
    private static String getCheckCodeBySum(int sum) {
        String checkCode = null;
        switch (sum % 11) {
            case 10:
                checkCode = "1";
                break;
            case 9:
                checkCode = "2";
                break;
            case 8:
                checkCode = "3";
                break;
            case 7:
                checkCode = "4";
                break;
            case 6:
                checkCode = "5";
                break;
            case 5:
                checkCode = "6";
                break;
            case 4:
                checkCode = "7";
                break;
            case 3:
                checkCode = "8";
                break;
            case 2:
                checkCode = "9";
                break;
            case 1:
                checkCode = "10";
                break;
            case 0:
                checkCode = "0";
                break;
        }
        return checkCode;
    }
}
