package com.foundercy.pf.control;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 用于控制回车时，焦点在几个控件之间的切换
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 张能斌
 * @version 1.0
 */

public class FNextFocusManager {
	private Vector components = new Vector();

	Border border1 = BorderFactory.createMatteBorder(1, 1, 1, 1,
			SystemColor.desktop);

	public FNextFocusManager() {
	}

	public void add(Component component) {
		Iterator it = components.iterator();
		for (; it.hasNext();) {
			if (it.next().equals(component)) {
				return;
			}
		}
		component.addKeyListener(new FocusKeyAdapter(this));
		component.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				JComponent source = (JComponent) e.getComponent();
				source.setBorder(border1);
			}

			public void focusLost(FocusEvent e) {
				JComponent source = (JComponent) e.getComponent();
				source.setBorder(border1);
			}
		});

		components.add(component);
	}

	void focusKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Component src = (Component) e.getSource();
			Iterator it = components.iterator();
			for (; it.hasNext();) {
				if (it.next().equals(src) && it.hasNext()) {
					((Component) it.next()).requestFocus();
					break;
				}
			}
		}
	}
}

class FocusKeyAdapter extends java.awt.event.KeyAdapter {
	FNextFocusManager adaptee;

	FocusKeyAdapter(FNextFocusManager adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.focusKeyPress(e);
	}
}
