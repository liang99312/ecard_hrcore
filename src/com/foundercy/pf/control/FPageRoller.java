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
 * Title: 翻页控件类
 * </p>
 * <p>
 * Description: 用于对控件实现翻页的功能
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

	private int pageRows = 10; // 每页的记录数，默认为10条

	private int currentPageIndex; // 当前的显示页
	
	//李文全增加
	private int fromPage = 1;

	private String id;

	Control parent = null;

	private int totalRows; // 总记录数

	private Rollable rollable; // 取数逻辑接口

	private String unitName = "页"; // 页、条、张等名称，默认为页
	
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
		btnFirst = new JButton("|首" + unitName);
		btnFirst.setMargin(new Insets(0,0,0,0));
//		btnFirst.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnFirst.addMouseListener(CoolButtonMouseListener.getInstance());
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFirst();
			}
		});
		btnPrev = new JButton("<上" + unitName);
		btnPrev.setMargin(new Insets(0,0,0,0));
//		btnPrev.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnPrev.addMouseListener(CoolButtonMouseListener.getInstance());
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onPrev();
			}
		});
		btnNext = new JButton("下" + unitName + ">");
		btnNext.setMargin(new Insets(0,0,0,0));
//		btnNext.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnNext.addMouseListener(CoolButtonMouseListener.getInstance());
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onNext();
			}
		});
		btnLast = new JButton("末" + unitName + "|");
		btnLast.setMargin(new Insets(0,0,0,0));
//		btnLast.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btnLast.addMouseListener(CoolButtonMouseListener.getInstance());
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLast();
			}
		});
		lblPage = new JLabel("第" + currentPageIndex + unitName 
				+ "/共" + getTotalPage() + unitName);
		
		btGoPage = new JButton("确定");
		btGoPage.setMargin(new Insets(0,0,0,0));
//		btGoPage.setIcon(Resource.getImage("go.gif"));
//		btGoPage.setBorder(new EmptyBorder(0, 2, 0, 2));
//		btGoPage.addMouseListener(CoolButtonMouseListener.getInstance());
		btGoPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pageNum = Integer.parseInt(txtPageNum.getText());
					if (pageNum < 0){
						JOptionPane.showMessageDialog(FPageRoller.this, "请输入正确的页码!");
						txtPageNum.setText("");
						txtPageNum.requestFocus();
					} else {
						onJump(pageNum);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(FPageRoller.this, "请输入正确的页码!");
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
		lblRow = new JLabel("每"+ unitName);
		lblGo = new JLabel("到");
		lblPageName = new JLabel(unitName);
		txtPageNum = new JTextField();
		pageRowsCombo = new FComboBox();
		lblGo.setBounds(0, 0, 16, 20);
		txtPageNum.setBounds(16, 2, 32, 17);
		
		pageRowsCombo.setRefModel("20#20+50#50+100#100+200#200+500#500+all#全部");
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
	 * 构造函数
	 * 
	 * @param r 翻页处理接口
	 */
	public FPageRoller(Rollable r) {
		this.setRollable(r);
	}

	/**
	 * 设置客户端翻页处理接口
	 * 
	 * @param rollable 接口
	 */
	public void addRollable(Rollable rollable) {
		this.rollable = rollable;
	}

	/**
	 * 设置客户端的翻页处理接口
	 * 
	 * @param rollable 翻页处理接口
	 */
	public void setRollable(Rollable rollable) {
		this.rollable = rollable;
		onFirst();
	}

	/**
	 * 设置客户端的翻页处理接口
	 * 
	 * @param rollable 翻页处理接口
	 */
	public void setRollable(Rollable rollable, int pageCode) {
		this.rollable = rollable;
		currentPageIndex = pageCode;
		totalRows = rollable.jump(pageCode, pageRows);
		refreshRollerState();
	}

	/**
	 * 到首页
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
	 * 到下一页
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
	 * 到上一页
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
	 * 到最后一页
	 * 
	 */
	public void onLast() {
		totalRows = rollable.jump(this.getTotalPage(), pageRows);
		currentPageIndex = this.getTotalPage();
		refreshRollerState();
	}

	/**
	 * 跳转到指定页
	 * 
	 * @param pageIndex 页号
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
	 * 设置每页的记录数
	 * 
	 * @param rows 记录数
	 */
	public void setPageRows(int rows) {
		this.pageRows = rows;
		
		//lindx add 2008-06-13 增加UI刷新事件处理
		refreshRollerState();
	}

	/**
	 * 得到每页的记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageRows() {
		return pageRows;
	}
	

	/**
	 * 设置显示元的名称
	 * 
	 * @param name 页、条、张等名称
	 */
	public void setUnitName(String name) {
		this.unitName = name;
		btnFirst.setText("|首" + unitName);
		btnPrev.setText("<上" + unitName);
		btnNext.setText("下" + unitName + ">");
		btnLast.setText("末" + unitName + "|");
		lblPageName.setText(name);
		refreshRollerState();
	}

	/**
	 * 得到显示元的名称
	 * 
	 * @return 页、条、张等名称
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 刷新翻页工具条状态
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
		
		//lindx add 设置当前每页记录数
		if(!"all".equals(this.pageRowsCombo.getValue().toString())){
			this.pageRowsCombo.setValue(new Integer(this.pageRows));
		}
		
		lblPage.setText("第" + currentPageIndex + unitName + "/共"
				+ getTotalPage() + unitName);

	}

	/**
	 * 得到总的页数
	 * 
	 * @return 页数值
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
