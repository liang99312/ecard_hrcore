/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import java.awt.Dimension;
import java.awt.Point;

public interface WizardModel {
	public int getTotalStep();
	public String getWizardName();
	public WizardPanel getPanel(int step);
	public Point getLocation();	
	public Dimension getSize();
	public void afterFinished();
}
