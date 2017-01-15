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
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//��ȡϵͳ������
        // ��ȡ���а��е�����
        Transferable clipT = clip.getContents(null);
        if (clipT != null) {
            // ��������Ƿ����ı�����
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
