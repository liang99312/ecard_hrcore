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
                //���Ҫ��ֹ��������Ļ������ж�ȥ����ֱ�ӵ���e.consume();��������Ҫ��д��ť�����뵽�ı�����
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
