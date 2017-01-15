/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Administrator
 */
public class JhrNumberField extends javax.swing.JTextField{

    public JhrNumberField(){
        super();
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //如果要禁止键盘输入的话，把判断去掉，直接调用e.consume();，但是需要编写按钮来插入到文本区中
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

}
