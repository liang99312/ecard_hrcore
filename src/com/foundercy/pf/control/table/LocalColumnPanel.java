/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import com.foundercy.pf.control.listener.IPickColumnLocalListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class LocalColumnPanel extends javax.swing.JPanel {
    
    private List<IPickColumnLocalListener> listeners = new ArrayList<IPickColumnLocalListener>();

    public void addIPickColumnFindListener(IPickColumnLocalListener listener) {
        listeners.add(listener);
    }

    public void removeIPickColumnFindListener(IPickColumnLocalListener listener) {
        listeners.remove(listener);
    }

    /**
     * Creates new form LocalColumnPanel
     */
    public LocalColumnPanel() {
        initComponents();
        setEvents();
    }
    
    private void setEvents() {
        ActionListener search_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ss = jTextField1.getText().trim().toUpperCase();
                if (ss == null || ss.equals("")) {
                    return;
                }
                for (IPickColumnLocalListener listener : listeners) {
                    listener.localColumn(ss);
                }
            }
        };
        this.jTextField1.addActionListener(search_listener);
        this.btnFind.addActionListener(search_listener);
        this.btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (IPickColumnLocalListener listener : listeners) {
                    listener.closeLocal();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jLabel3.setText("����: ");
        jToolBar1.add(jLabel3);

        jTextField1.setMaximumSize(new java.awt.Dimension(140, 21));
        jTextField1.setMinimumSize(new java.awt.Dimension(140, 21));
        jTextField1.setPreferredSize(new java.awt.Dimension(140, 21));
        jToolBar1.add(jTextField1);

        jLabel1.setText(" ");
        jToolBar1.add(jLabel1);

        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/search.png"))); // NOI18N
        btnFind.setMaximumSize(new java.awt.Dimension(21, 21));
        btnFind.setMinimumSize(new java.awt.Dimension(21, 21));
        btnFind.setPreferredSize(new java.awt.Dimension(21, 21));
        jToolBar1.add(btnFind);

        jLabel2.setText(" ");
        jToolBar1.add(jLabel2);

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/del1.png"))); // NOI18N
        btnClose.setFocusable(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setMaximumSize(new java.awt.Dimension(21, 21));
        btnClose.setMinimumSize(new java.awt.Dimension(21, 21));
        btnClose.setPreferredSize(new java.awt.Dimension(21, 21));
        btnClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnClose);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
