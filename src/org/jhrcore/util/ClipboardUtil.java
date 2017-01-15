/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 *
 * @author mxliteboss
 */
public class ClipboardUtil {
    
    public static String getClipboardText() throws Exception {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
        // 获取剪切板中的内容
        Transferable clipT = clip.getContents(null);
        if (clipT != null) {
            // 检查内容是否是文本类型
            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) clipT.getTransferData(DataFlavor.stringFlavor);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            String text = getClipboardText();
            String[] texts = text.split("\n");
            for(String t:texts){
                System.out.println("s:"+t);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
