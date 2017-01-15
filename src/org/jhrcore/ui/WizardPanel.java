/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import javax.swing.*;
/**
 *
 * @author Administrator
 */
public abstract class WizardPanel extends JPanel{
    public abstract boolean isValidate();
    public abstract void beforeLeave();
    public abstract String getTitle();
}
