/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ShowImageComponent extends JComponent{
	private static final long serialVersionUID = 1L;

	private BufferedImage image = null;

	public ShowImageComponent(BufferedImage image) {
		super();
		this.image = image;
	}

	public void paint(Graphics g) {
		float mul = (float) Math.min(1.0 * this.getWidth() / image.getWidth(), 1.0 * this.getHeight()/ image.getHeight());
		int w = (int) (image.getWidth() * mul);
		int h = (int) (image.getHeight() * mul);
		g.drawImage(image, (this.getWidth() - w) / 2, (this.getHeight() - h) / 2, w, h, null);
	}

}
