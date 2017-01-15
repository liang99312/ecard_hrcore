package com.foundercy.pf.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CoolButtonMouseListener extends MouseAdapter {
	public final static Border DEFAULT_BORDER = new EmptyBorder(2, 2, 2, 2);

	public final static Border ENTERED_BORDER = new ThinBevelBorder(
			ThinBevelBorder.RAISED);

	public final static Border PRESSED_BORDER = new ThinBevelBorder(
			ThinBevelBorder.LOWERED);

	private final static CoolButtonMouseListener listener_ = new CoolButtonMouseListener();

	private CoolButtonMouseListener() {
	}

	public static CoolButtonMouseListener getInstance() {
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
		if ((!button.isEnabled())) {
			return;
		}
		if (button.isSelected()) {
			return;
		} else {
			button.setBorder(DEFAULT_BORDER);
		}
	}

	public void mousePressed(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		if ((!button.isEnabled())) {
			return;
		}
		if (button.isSelected()) {
			return;
		}
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
