/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.util.Random;

/**
 *
 * @author Administrator
 */
public class RandomUtil {
    public static String getRandomString(int length) {
        if (length < 1) {
            return "";
        }
        Random randGen = new Random();
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
}
