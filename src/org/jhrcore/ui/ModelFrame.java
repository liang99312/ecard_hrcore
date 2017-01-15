/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.Cursor;
import org.jhrcore.ui.listener.IPickWindowCloseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.jhrcore.util.ImageUtil;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author DB2INST3
 */
public class ModelFrame extends JFrame implements WindowListener {

    private JFrame frame = null;
    private boolean modal = false;
    private static List<String> existKeys = new ArrayList<String>();
    private static Hashtable<String, ModelFrame> mfKeys = new Hashtable<String, ModelFrame>();
    private String moduleKey = "";
    private List<IPickWindowCloseListener> iPickWindowCloseListeners = new ArrayList<IPickWindowCloseListener>();

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public void addIPickWindowCloseListener(IPickWindowCloseListener listener) {
        iPickWindowCloseListeners.add(listener);
    }

    public void delIPickWindowCloseListener(IPickWindowCloseListener listener) {
        iPickWindowCloseListeners.remove(listener);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
        if (modal) {
            this.frame.setEnabled(false);
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    public ModelFrame() {
        this(null, false, "");
    }

    public ModelFrame(JFrame frame, boolean modal, Object title) {
        super(SysUtil.objToStr(title));
        this.frame = frame;
        this.modal = modal;
        this.setTitle(SysUtil.objToStr(title));
        this.init();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ImageUtil.getIconImage());
    }

    public static ModelFrame showModel(JFrame frame, JComponent com, boolean modal, Object title) {
        return showModel(frame, com, modal, title, 750, 650);
    }

    public static ModelFrame showModel(JFrame frame, JComponent com, boolean modal, Object title, boolean visible) {
        return showModel(frame, com, modal, title, null, 750, 650, visible);
    }

    public static ModelFrame showModel(JFrame frame, JComponent com, boolean modal, Object title, int width, int height) {
        return showModel(frame, com, modal, title, null, width, height);
    }

    public static ModelFrame showModel(JFrame frame, JComponent com, boolean modal, Object title, String moduleKey, int width, int height) {
        return showModel(frame, com, modal, title, moduleKey, width, height, true);
    }

    public static ModelFrame showModel(JFrame frame, JComponent com, boolean modal, Object title, int width, int height, boolean visible) {
        return showModel(frame, com, modal, title, null, width, height, visible);
    }

    public static ModelFrame showModel(JFrame frame, JComponent com, boolean modal, Object title, String moduleKey, int width, int height, boolean visible) {
        if (moduleKey == null || moduleKey.trim().equals("")) {
            moduleKey = com.getClass().getName();
        }
        if (existKeys.contains(moduleKey)) {
            return null;
        }
        ModelFrame mf = new ModelFrame(frame, modal, title);
        mf.moduleKey = moduleKey;
        existKeys.add(moduleKey);
        mfKeys.put(moduleKey, mf);
        mf.getContentPane().add(com);
        if (com instanceof IPickWindowCloseListener) {
            mf.iPickWindowCloseListeners.add((IPickWindowCloseListener) com);
        }
        mf.setSize(width, height);
        ContextManager.locateOnMainScreenCenter(mf);
        mf.setVisible(visible);
        return mf;
    }

    private void init() {
        if (modal) {
            frame.setEnabled(false);
        }
        this.addWindowListener(this);
    }

    public static void close() {
        String key = existKeys.get(existKeys.size() - 1);
        ModelFrame mf = mfKeys.get(key);
        if (mf == null) {
            return;
        }
        close(mf);
    }

    public static void close(ModelFrame mf) {
        if (mf != null) {
            JFrame frame = mf.getFrame();
            if (frame != null) {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                frame.setEnabled(true);
                frame.setVisible(true);
            }
            mf.dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
//        for (IPickWindowCloseListener listener : iPickWindowCloseListeners) {
//            listener.pickClose();
//        }
        if (modal) {
            frame.setEnabled(true);
            frame.setVisible(true);
        }
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        existKeys.remove(this.moduleKey);
        mfKeys.remove(this.moduleKey);
        for (IPickWindowCloseListener listener : iPickWindowCloseListeners) {
            listener.pickClose();
        }
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
        if (modal) {
            this.requestFocus();
        }
    }

    public static List<String> getExistKeys() {
        return existKeys;
    }

    public static Hashtable<String, ModelFrame> getMfKeys() {
        return mfKeys;
    }
}
