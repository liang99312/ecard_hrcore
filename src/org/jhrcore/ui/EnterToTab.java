/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class EnterToTab {

    private JComponent component;
    private static List<JComponent> list_bean_component = new ArrayList<JComponent>();

    public static List<JComponent> getList_bean_component() {
        return list_bean_component;
    }

    public EnterToTab(JComponent component) {
        super();
        this.component = component;
        setUpEvent();
        if (!list_bean_component.contains(component)) {
            list_bean_component.add(component);
        }
    }

    private void setUpEvent() {
        component.addKeyListener(new KeyAdapter() {

            @SuppressWarnings("deprecation")
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    KeyEvent ke = new KeyEvent(component, KeyEvent.KEY_PRESSED,
                            50, 0, KeyEvent.VK_TAB);
                    component.dispatchEvent(ke);
                }
            }
        });
    }
}
