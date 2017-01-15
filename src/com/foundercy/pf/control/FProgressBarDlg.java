package com.foundercy.pf.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class FProgressBarDlg extends JDialog
{
	public static final long serialVersionUID = -1L;
	private JProgressBar progressbar = null;
	
	public FProgressBarDlg(JDialog dialog, String title, boolean model,int min,int max){
		super(dialog, title, model);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.progressbar = new JProgressBar();
		this.progressbar.setOrientation(JProgressBar.HORIZONTAL);		
		this.progressbar.setStringPainted(true);
		this.progressbar.setBackground(Color.WHITE);
		this.progressbar.setForeground(Color.BLACK);
		this.progressbar.setPreferredSize(new Dimension(400,20));
		this.progressbar.setBorderPainted(false);
		this.setUndecorated(false);
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		getContentPane().setBackground(Color.BLUE);
		getContentPane().add(progressbar,BorderLayout.CENTER);
        progressbar.setMinimum(min);
		progressbar.setMaximum(max);
		progressbar.setValue(min);
		Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
		int width = dimensions.width / 3, height = 50;
		int left = (dimensions.width - width) / 2;
		int top = (dimensions.height - height) / 2;		
		setLocation(left, top);
		setSize(width,height);
		setResizable(false);
	}
	public FProgressBarDlg(JFrame frame, String title, boolean model,int min,int max){
		super(frame, title, model);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.progressbar = new JProgressBar();
		this.progressbar.setOrientation(JProgressBar.HORIZONTAL);		
		this.progressbar.setStringPainted(true);
		this.progressbar.setBackground(Color.WHITE);
		this.progressbar.setForeground(Color.BLACK);
		this.progressbar.setPreferredSize(new Dimension(330,20));
		this.progressbar.setBorderPainted(false);
		this.setUndecorated(false);
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		getContentPane().setBackground(Color.BLUE);
		getContentPane().add(progressbar,BorderLayout.CENTER);
        progressbar.setMinimum(min);
		progressbar.setMaximum(max);
		progressbar.setValue(min);
		Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
		int width = dimensions.width / 3, height = 50;
		int left = (dimensions.width - width) / 2;
		int top = (dimensions.height - height) / 2;
		setLocation(left, top);
		setSize(width,height);
		setResizable(false);
		this.setFocusableWindowState(true);
		this.requestFocus();
	}

	public JProgressBar getProgressbar() {
		return progressbar;
	}

	public void setProgressbar(JProgressBar progressbar) {
		this.progressbar = progressbar;
	}
}
