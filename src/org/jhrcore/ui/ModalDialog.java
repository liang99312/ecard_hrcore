package org.jhrcore.ui;

import java.awt.Component;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jhrcore.util.SysUtil;

public class ModalDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnOK = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private boolean modalResult = false;
    private ValidateEntity ve = null;
    private DefaultKeyboardFocusManager keyListener = new DefaultKeyboardFocusManager() {

        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                doCancel();
                return true;
            }
            return super.dispatchKeyEvent(e);
        }
    };

    private ModalDialog(Frame parentFrame, JPanel content) {
        super(parentFrame, true);
        this.contentPane = content;
        add(content, "Center");
        btnOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (contentPane instanceof ModalDialogCheckInput) {
                    if (!((ModalDialogCheckInput) contentPane).check()) {
                        return;
                    }
                }
                doOK();
            }
        });
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                doCancel();
            }
        });
        int h = btnOK.getPreferredSize().height;
        int w = Math.max(btnOK.getPreferredSize().width, btnCancel.getPreferredSize().width);
        btnOK.setPreferredSize(new Dimension(w + 10, h));
        btnCancel.setPreferredSize(new Dimension(w + 10, h));
        btnOK.setMargin(new Insets(0, 0, 0, 0));
        btnCancel.setMargin(new Insets(0, 0, 0, 0));
        JPanel bottomButtonPane = new JPanel(new FlowLayout());
        bottomButtonPane.add(btnOK);
        bottomButtonPane.add(btnCancel);
        add(bottomButtonPane, "South");
        KeyboardFocusManager kbfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kbfm.addKeyEventDispatcher(keyListener);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void doOK() {
        if (ve != null) {
            boolean result = ve.isEntityValidate(null);
            if (!result) {
                return;
            }
        }
        modalResult = true;
        dispose();
    }

    private void doCancel() {
        modalResult = false;
        dispose();
    }

    public static boolean doModal(Component parent, JPanel contentPane, Object title) {
        return doModal(parent, contentPane, title, null, null);
    }

    public static boolean doModal(Component parent, JPanel contentPane, Object title, JComponent c) {
        return doModal(parent, contentPane, title, c, null);
    }

    public static boolean doModal(Component parent, JPanel contentPane, Object title, ValidateEntity ve) {
        return doModal(parent, contentPane, title, null, ve);
    }

    /**
     * 根据指定参数生成模式DLG显示，并实现对应的OK/CANCEL事件
     * @param parent：父窗体
     * @param contentPane：目标显示界面PANEL
     * @param title：标题
     * @param c：该控件将会支持双击关闭窗口事件
     * @param ve：验证器，用于验证界面逻辑
     * @return ：用户是否点击确认操作
     */
    public static boolean doModal(Component parent, JPanel contentPane, Object title, JComponent c, ValidateEntity ve) {
        final ModalDialog dlg;
        if (parent == null) {
            dlg = new ModalDialog(null, contentPane);
        } else {
            dlg = new ModalDialog(JOptionPane.getFrameForComponent(parent), contentPane);
        }
        dlg.setTitle(SysUtil.objToStr(title));
        Dimension d = contentPane.getPreferredSize();
        if (d == null || d.width < 60 || d.height < 60) {
            dlg.setSize(300, 300);
        } else {
            dlg.setSize(Math.min(Toolkit.getDefaultToolkit().getScreenSize().width, d.width),
                    Math.min(Toolkit.getDefaultToolkit().getScreenSize().height - 50, d.height + 100));
        }
        dlg.setLocationRelativeTo(null);
        dlg.setResizable(false);
        dlg.modalResult = false;
        dlg.ve = ve;
        dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (c != null) {
            c.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getClickCount() >= 2) {
                        dlg.doOK();
                    }
                }
            });
        }
        dlg.setVisible(true);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dlg.keyListener);
        return dlg.modalResult;
    }
//    public static boolean doModalSticky(Component parent, JPanel contentPane, String title, Rectangle bounds) {
//        ModalDialog dlg;
//        if (parent == null) {
//            dlg = new ModalDialog(null, contentPane);
//        } else {
//            dlg = new ModalDialog(JOptionPane.getFrameForComponent(parent), contentPane);
//        }
//        dlg.setTitle(title);
//        Dimension d = contentPane.getPreferredSize();
//        if (d == null || d.width < 60 || d.height < 60) {
//            dlg.setSize(300, 300);
//        } else {
//            dlg.setSize(Math.min(Toolkit.getDefaultToolkit().getScreenSize().width, d.width),
//                    Math.min(Toolkit.getDefaultToolkit().getScreenSize().height - 50, d.height + 30));
//        }
//        dlg.setBounds(bounds);
//        dlg.setUndecorated(true);
//        dlg.setResizable(false);
//        modalResult = false;
//        dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        dlg.setVisible(true);
//        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dlg.keyListener);
//        return modalResult;
//    }
}
