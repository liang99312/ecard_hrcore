/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.ui.listener.IPickCodeSelectListener;

/**
 *
 * @author Administrator
 */
public class CodeEditor2 extends JPanel {

    public JTextField textfield = new JTextField();

    public CodeEditor2(Object bean, FieldAnnotation fieldAnnotation, final String hql, final String bindName, final List codes) {
        this(bean, fieldAnnotation, hql, bindName, codes, false);
    }

    public CodeEditor2(Object bean, FieldAnnotation fieldAnnotation, final String hql, final String bindName, final List codes, final boolean leafOnly) {
        super(new BorderLayout());
        final Object theBean = bean;
        textfield.setEditable(false);
        final JPanel jpanel = CodeEditor2.this;
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelelect = new JButton("...");
        btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelelect, BorderLayout.EAST);
        btnSelelect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                CodeSelectDialog csDialog = new CodeSelectDialog(codes, hql.substring(hql.indexOf("=") + 1), leafOnly ? TreeSelectMod.leafSelectMod : TreeSelectMod.nodeSelectMod);
                csDialog.addPickCodeSelectListener(new IPickCodeSelectListener() {

                    public void pickCode(DefaultMutableTreeNode node) {
                        if (node != null) {
                            textfield.setText(node.getUserObject().toString());
                            EditorFactory.setValueBy(theBean, bindName, node.getUserObject());
                        }
                    }
                });
                csDialog.setTitle("«Î—°‘Ò");
                csDialog.setLocationRelativeTo(jpanel);
                csDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                csDialog.setVisible(true);
            }
        });
    }
}
