/*
 * filename:  FPageRoller.java
 *
 * Version: 1.0
 *
 * Date: 2006-3-23
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * <p>
 * Title: ��ҳ�ؼ���
 * </p>
 * <p>
 * Description: ���ڶԿؼ�ʵ�ַ�ҳ�Ĺ���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: foundercy
 * </p>
 * 
 * @author yangbo
 * @version 1.0
 */
public class FPageRoller extends JPanel implements Control {
	public static final long serialVersionUID = -1L;

	private int pageRows = 10; // ÿҳ�ļ�¼����Ĭ��Ϊ10��

	private int currentPageIndex; // ��ǰ����ʾҳ
	
	//����ȫ����
	private int fromPage = 1;

	private String id;

	Control parent = null;

	private int totalRows; // �ܼ�¼��

	private Rollable rollable; // ȡ���߼��ӿ�

	private String unitName = "ҳ"; // ҳ�������ŵ����ƣ�Ĭ��Ϊҳ
	
	private boolean enableSelectRows = true;

	JButton btnFirst;

	JButton btnPrev;

	JButton btnNext;

	JButton btnLast;

	JButton btGoPage;
	
	JLabel lblPage;
	JSeparator sprLine;
	JLabel lblRow;
	JLabel lblGo ;
	JTextField txtPageNum;
	
	FComboBox pageRowsCombo;

	JLabel lblPageName;

	JPanel toPagePanel;
	
	JPanel toRowPanel;
	
	public FPageRoller() {
		try {
			jbInit();
			//this.setFloatable(false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		btnFirst = new JButton("|��" + unitName);
		btnFirst.setMargin(new Insets(0,0,0,0));
//		btnFirst.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnFirst.addMouseListener(CoolButtonMouseListener.getInstance());
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFirst();
			}
		});
		btnPrev = new JButton("<��" + unitName);
		btnPrev.setMargin(new Insets(0,0,0,0));
//		btnPrev.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnPrev.addMouseListener(CoolButtonMouseListener.getInstance());
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onPrev();
			}
		});
		btnNext = new JButton("��" + unitName + ">");
		btnNext.setMargin(new Insets(0,0,0,0));
//		btnNext.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnNext.addMouseListener(CoolButtonMouseListener.getInstance());
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onNext();
			}
		});
		btnLast = new JButton("ĩ" + unitName + "|");
		btnLast.setMargin(new Insets(0,0,0,0));
//		btnLast.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnLast.addMouseListener(CoolButtonMouseListener.getInstance());
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLast();
			}
		});
		lblPage = new JLabel("��" + currentPageIndex + unitName 
				+ "/��" + getTotalPage() + unitName);
		
		btGoPage = new JButton("ȷ��");
		btGoPage.setMargin(new Insets(0,0,0,0));
//		btGoPage.setIcon(Resource.getImage("go.gif"));
//		btGoPage.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btGoPage.addMouseListener(CoolButtonMouseListener.getInstance());
		btGoPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pageNum = Integer.parseInt(txtPageNum.getText());
					if (pageNum < 0){
						JOptionPane.showMessageDialog(FPageRoller.this, "��������ȷ��ҳ��!");
						txtPageNum.setText("");
						txtPageNum.requestFocus();
					} else {
						onJump(pageNum);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(FPageRoller.this, "��������ȷ��ҳ��!");
					txtPageNum.setText("");
					txtPageNum.requestFocus();
				}
			}
		});
		sprLine = new JSeparator();
		toPagePanel = new JPanel();
		toRowPanel = new JPanel();
		// toPagePanel.setLayout(new FlowLayout());
		toPagePanel.setLayout(null);
		toRowPanel.setLayout(null);
		lblRow = new JLabel("ÿ"+ unitName);
		lblGo = new JLabel("��");
		lblPageName = new JLabel(unitName);
		txtPageNum = new JTextField();
		pageRowsCombo = new FComboBox();
		lblGo.setBounds(0, 0, 16, 20);
		txtPageNum.setBounds(16, 2, 32, 17);
		
		pageRowsCombo.setRefModel("20#20+50#50+100#100+200#200+500#500+all#ȫ��");
		pageRowsCombo.setTitleVisible(false);
		this.setPreferredSize(new Dimension(32,24));
//		pageRowsCombo.setBounds(48, 2, 80, 19);
//		lblPageName.setBounds(48, 0, 18, 20);
//		lblRow.setBounds(48, 0, 60, 20);
//		btGoPage.setBounds(68, 0, 20, 20);
		//txtPageNum.setPreferredSize(new Dimension(32, 19));
//		comboRowNum.setPreferredSize(new Dimension(222, 220));
		txtPageNum.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btGoPage.doClick();
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		pageRowsCombo.addValueChangeListener(new ValueChangeListener(){
	      	public void valueChanged(ValueChangeEvent arg0){
	      		if(!arg0.getNewValue().toString().equals("all")){
	      			pageRows = Integer.parseInt( arg0.getNewValue().toString() );
	      		}else{
	      			pageRows = 1000000;
	      		}
	      		FPageRoller.this.onFirst();
	      	}
		});
//		toPagePanel.add(toRowPanel);
//		toPagePanel.add(lblGo);
//		toPagePanel.add(txtPageNum);
//		toPagePanel.add(lblRow);
//		toPagePanel.add(lblPageName);
//		toPagePanel.add(btGoPage);
//		if(enableSelectRows){
//			toRowPanel.add(lblRow);
//			toRowPanel.add(pageRowsCombo);
//		}
		
//		this.setLayout(new FlowLayout());
		this.setLayout(null);
		this.add(lblPage);
		this.add(btnFirst);
		this.add(btnPrev);
		this.add(btnNext);
		this.add(btnLast);
		this.add(lblRow);
		this.add(pageRowsCombo);
		this.add(lblGo);
		this.add(txtPageNum);
		this.add(lblPageName);
		this.add(btGoPage);
		this.add(sprLine);
	}

	/**
	 * ���캯��
	 * 
	 * @param r ��ҳ����ӿ�
	 */
	public FPageRoller(Rollable r) {
		this.setRollable(r);
	}

	/**
	 * ���ÿͻ��˷�ҳ����ӿ�
	 * 
	 * @param rollable �ӿ�
	 */
	public void addRollable(Rollable rollable) {
		this.rollable = rollable;
	}

	/**
	 * ���ÿͻ��˵ķ�ҳ����ӿ�
	 * 
	 * @param rollable ��ҳ����ӿ�
	 */
	public void setRollable(Rollable rollable) {
		this.rollable = rollable;
		onFirst();
	}

	/**
	 * ���ÿͻ��˵ķ�ҳ����ӿ�
	 * 
	 * @param rollable ��ҳ����ӿ�
	 */
	public void setRollable(Rollable rollable, int pageCode) {
		this.rollable = rollable;
		currentPageIndex = pageCode;
		totalRows = rollable.jump(pageCode, pageRows);
		refreshRollerState();
	}

	/**
	 * ����ҳ
	 * 
	 */
	public void onFirst() {
		//totalRows = rollable.jump(1, pageRows);
		totalRows = rollable.jump(this.getFromPage(), pageRows);
		if (totalRows == 0) {
			currentPageIndex = 0;
		} else {
			currentPageIndex = 1;
		}
		refreshRollerState();
	}
  
	
	
	/**
	 * ����һҳ
	 * 
	 */
	public void onNext() {
		if (currentPageIndex >= this.getTotalPage())
			return;
		totalRows = rollable.jump(currentPageIndex + 1, pageRows);
		currentPageIndex++;
		refreshRollerState();
	}

	/**
	 * ����һҳ
	 * 
	 */
	public void onPrev() {
		if (currentPageIndex <= 1)
			return;
		totalRows = rollable.jump(currentPageIndex - 1, pageRows);
		currentPageIndex--;
		refreshRollerState();
	}

	/**
	 * �����һҳ
	 * 
	 */
	public void onLast() {
		totalRows = rollable.jump(this.getTotalPage(), pageRows);
		currentPageIndex = this.getTotalPage();
		refreshRollerState();
	}

	/**
	 * ��ת��ָ��ҳ
	 * 
	 * @param pageIndex ҳ��
	 */
	public void onJump(int pageIndex) {
		totalRows = rollable.jump(pageIndex, pageRows);
		if (pageIndex > getTotalPage()) {
			pageIndex = getTotalPage();
			txtPageNum.setText(String.valueOf(pageIndex));
		} else if (getTotalPage()>0 && pageIndex < 1) {
			pageIndex = 1;
			txtPageNum.setText("1");
		}
		currentPageIndex = pageIndex;
		refreshRollerState();
	}

	/**
	 * ����ÿҳ�ļ�¼��
	 * 
	 * @param rows ��¼��
	 */
	public void setPageRows(int rows) {
		this.pageRows = rows;
		
		//lindx add 2008-06-13 ����UIˢ���¼�����
		refreshRollerState();
	}

	/**
	 * �õ�ÿҳ�ļ�¼��
	 * 
	 * @return ÿҳ��¼��
	 */
	public int getPageRows() {
		return pageRows;
	}
	

	/**
	 * ������ʾԪ������
	 * 
	 * @param name ҳ�������ŵ�����
	 */
	public void setUnitName(String name) {
		this.unitName = name;
		btnFirst.setText("|��" + unitName);
		btnPrev.setText("<��" + unitName);
		btnNext.setText("��" + unitName + ">");
		btnLast.setText("ĩ" + unitName + "|");
		lblPageName.setText(name);
		refreshRollerState();
	}

	/**
	 * �õ���ʾԪ������
	 * 
	 * @return ҳ�������ŵ�����
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * ˢ�·�ҳ������״̬
	 * 
	 */
	private void refreshRollerState() {
		if ((currentPageIndex == 1 || currentPageIndex == 0)
				&& currentPageIndex == this.getTotalPage()) {
			btnFirst.setEnabled(false);
			btnPrev.setEnabled(false);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
		} else if (currentPageIndex == this.getTotalPage()) {
			btnFirst.setEnabled(true);
			btnPrev.setEnabled(true);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
		} else if (currentPageIndex == 1) {
			btnFirst.setEnabled(false);
			btnPrev.setEnabled(false);
			btnNext.setEnabled(true);
			btnLast.setEnabled(true);
		} else {
			btnFirst.setEnabled(true);
			btnPrev.setEnabled(true);
			btnNext.setEnabled(true);
			btnLast.setEnabled(true);
		}
		
		//lindx add ���õ�ǰÿҳ��¼��
		if(!"all".equals(this.pageRowsCombo.getValue().toString())){
			this.pageRowsCombo.setValue(new Integer(this.pageRows));
		}
		
		lblPage.setText("��" + currentPageIndex + unitName + "/��"
				+ getTotalPage() + unitName);

	}

	/**
	 * �õ��ܵ�ҳ��
	 * 
	 * @return ҳ��ֵ
	 */
	public int getTotalPage() {
		if (totalRows % pageRows == 0) {
			return totalRows / pageRows;
		} else {
			return totalRows / pageRows + 1;
		}
	}

	public String getId() {
		return this.id;
	}

	public Control getParentControl() {
		return this.parent;
	}

	public void setId(String id) {
		this.id = id;

	}

	public void setParentControl(Control parent) {
		this.parent = parent;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		if (totalRows == 0) {
			this.currentPageIndex = 0;
		}
		refreshRollerState();
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void reshape(int x, int y, int w, int h) {
		super.reshape(x, y, w, h);
		
		int step = String.valueOf(getTotalPage()).length();
		int left = 390;
		int height = 20;
		int top = 0;
		if(h-height>0) {
			top = (h-height)/2;
		}
		if(this.enableSelectRows) {
			lblPage.setBounds((w - left) -35 - 7 * step, top, 75 + 7 * step,height);
			btnFirst.setBounds((w - left) + 30, top, 45, height);
			btnPrev.setBounds((w - left) + 76, top, 45, height);
			btnNext.setBounds((w - left) + 122, top, 45, height);
			btnLast.setBounds((w - left) + 168, top, 45, height);
			lblRow.setBounds((w - left) + 213, top, 25, height);
			pageRowsCombo.setBounds((w - left) + 238, top+1, 50, height-1);
			lblGo.setBounds((w - left) + 288, top, 15, height);
			txtPageNum.setBounds((w - left) + 303, top+1, 25, height-1);
			lblPageName.setBounds((w - left) + 328, top, 15, height);
			btGoPage.setBounds((w - left) + 343, top, 45, height);
		}else {
			left = 315;
			lblPage.setBounds((w - left) -35 - 7 * step, top, 75 + 7 * step, height);
			btnFirst.setBounds((w - left) + 30, top, 45, height);
			btnPrev.setBounds((w - left) + 76, top, 45, height);
			btnNext.setBounds((w - left) + 122, top, 45, height);
			btnLast.setBounds((w - left) + 168, top, 45, height);
			lblGo.setBounds((w - left) + 213, top, 15, height);
			txtPageNum.setBounds((w - left) + 228, top+1, 25, height-1);
			lblPageName.setBounds((w - left) + 253, top, 15, height);
			btGoPage.setBounds((w - left) + 268, top, 45, height);			
		}
	}

	public void enableSelectRows(boolean enableSelectRows) {
		this.enableSelectRows = enableSelectRows;
		try {
			this.jbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getFromPage() {
		return fromPage;
	}

	public void setFromPage(int fromPage) {
		this.fromPage = fromPage;
	}
	
	

}
