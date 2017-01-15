/*
 * filename:  FCoolButtonMouseListener.java
 *
 * Version: 1.0
 *
 * Date: 2006-3-10
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * <p>Title: 按钮鼠标事件 </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author 
 * @version 1.0
 */

public class FCoolButtonMouseListener extends MouseAdapter {
	public final static Border DEFAULT_BORDER = new EmptyBorder(2, 2, 2, 2);

	public final static Border ENTERED_BORDER = new FThinBevelBorder(
			FThinBevelBorder.RAISED);

	public final static Border PRESSED_BORDER = new FThinBevelBorder(
			FThinBevelBorder.LOWERED);

	private final static FCoolButtonMouseListener listener_ = new FCoolButtonMouseListener();

	private FCoolButtonMouseListener() {
	}

	public static FCoolButtonMouseListener getInstance() {
		return listener_;
	}

	public void mouseEntered(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		if ((!button.isEnabled())) {
			return;
		}
		if (button.isSelected()) {
			return;
		}
		if (button.getModel().isPressed()) {
			button.setBorder(PRESSED_BORDER);
		} else {
			if (e.getModifiers() != MouseEvent.BUTTON1_MASK) {
				button.setBorder(ENTERED_BORDER);
			}
		}
	}

	public void mouseExited(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		if (button.isSelected()) {
			return;
		} else {
			button.setBorder(DEFAULT_BORDER);
		}
	}

	public void mousePressed(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		if (button.isSelected()) {
			return;
		}
		if(button.isEnabled() == true)
		button.setBorder(PRESSED_BORDER);
	}

	public void mouseReleased(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		if ((!button.isEnabled())) {
			return;
		}
		if (button.isSelected()) {
			return;

		}
		button.setBorder(DEFAULT_BORDER);
	}
}
