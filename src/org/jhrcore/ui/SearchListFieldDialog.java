/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchFieldDialog.java
 *
 * Created on 2011-6-25, 12:29:32
 */
package org.jhrcore.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import org.jhrcore.util.PinYinMa;

/**
 *
 * @author hflj
 */
public class SearchListFieldDialog extends javax.swing.JDialog {

    private JList jList = null;
    private static SearchListFieldDialog searchFieldDialog = null;
    private Hashtable<JList, String> treeKeys = new Hashtable();

    /** Creates new form SearchFieldDialog */
    public SearchListFieldDialog() {
        initComponents();
        setupEvents();
    }

    private void setupEvents() {
        searchText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    locate(1, searchText.getText());
                }
            }
        });
        btnLastPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                locate(-1, searchText.getText());
            }
        });
        btnAfterPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                locate(1, searchText.getText());
            }
        });
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                cancelQuickSearch();
            }
        });
    }

    private static void showSearchPanel(final String title) {
        if (searchFieldDialog.isVisible()) {
            return;
        }
        searchFieldDialog.setMinimumSize(new Dimension(250, 70));
        searchFieldDialog.setPreferredSize(new Dimension(250, 70));
        searchFieldDialog.setTitle(title + "À—À˜£∫");
        searchFieldDialog.setModal(true);
        searchFieldDialog.setLocationRelativeTo(searchFieldDialog.jList.getRootPane());
        searchFieldDialog.setVisible(true);

    }

    public static void doQuickSearch(final String title, final JList jlist) {
        if (searchFieldDialog == null) {
            searchFieldDialog = new SearchListFieldDialog();
        }
        searchFieldDialog.jList = jlist;
        jlist.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() || searchFieldDialog.isVisible()) {
                    return;
                }
                int kCode = e.getKeyCode();
                if ((kCode >= 97 && kCode <= 122) || (kCode >= 65 && kCode <= 90)) {
                    String val = KeyEvent.getKeyText(kCode).toUpperCase();
                    searchFieldDialog.locate(1, val);
                }
            }
        });
        final JPopupMenu popupMenu = new JPopupMenu();
        jlist.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                searchFieldDialog.jList = jlist;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 3) {
                    popupMenu.show(searchFieldDialog.jList, e.getX(), e.getY());
                }
            }
        });
        Action alSearch = new AbstractAction("≤È’“") {

            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchPanel(title);
            }
        };
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.Event.CTRL_MASK, false);
        jlist.getInputMap().put(stroke, "Search");
        jlist.getActionMap().put("Search", alSearch);
        JMenuItem mi = new JMenuItem(alSearch);
        mi.setMnemonic(KeyEvent.VK_F);
        mi.setAccelerator(stroke);
        popupMenu.add(mi);
        searchFieldDialog.treeKeys.put(jlist, title);
    }

    private void cancelQuickSearch() {
        searchFieldDialog.treeKeys.clear();
        searchText.setText(null);
    }

    private void locate(int step, String val) {
        if (val == null || val.trim().equals("")) {
            return;
        }
        val = val.toUpperCase();
        ListModel model = jList.getModel();
        int max = model.getSize();
        if (max <= 0) {
            return;
        }
        int index = jList.getSelectedIndex() == -1 ? 0 : jList.getSelectedIndex();
        int startIndex = index;
        do {
            startIndex = (startIndex + step) % max;
            Object obj = model.getElementAt(startIndex);
            if (obj != null) {
                String str = obj.toString();
                String string = PinYinMa.ctoE(str).toUpperCase();
                if (string.startsWith(val) || str.startsWith(val)) {
                    index = startIndex;
                    jList.setSelectedIndex(index);
                    jList.ensureIndexIsVisible(index);
                    break;
                }
            }
        } while (step != 0 && index != startIndex);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchText = new javax.swing.JTextField();
        btnLastPage = new javax.swing.JButton();
        btnAfterPage = new javax.swing.JButton();

        searchText.setToolTipText("«Î ‰»ÎÀ—À˜ƒ⁄»›£∫");

        btnLastPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/remove_one.png"))); // NOI18N

        btnAfterPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/select_one.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLastPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAfterPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAfterPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLastPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfterPage;
    private javax.swing.JButton btnLastPage;
    private javax.swing.JTextField searchText;
    // End of variables declaration//GEN-END:variables
}
