/*
 * $Id: FTableCustomizerDialog.java,v 1.1.1.1 2009/04/07 08:12:34 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control.table;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.foundercy.pf.control.FEtchedLine;
//import com.foundercy.pf.control.FIntegerField;
import com.foundercy.pf.control.util.LocalMemoUtil;
/**
 * 鼠标右击表头对话框类，用于定制表格列信息
 * 
 * @author a
 *
 */
public class FTableCustomizerDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private FTable sourceTable = null;
	//
	private JPanel panel1 = new JPanel();
	
	private FTable localTable = new FTable(true);
	
	private JButton btnUp = new JButton("上移 ( U )");
	private JButton btnDown = new JButton("下移 ( D )");
	private JButton btnShow = new JButton("显示 ( S )");
	private JButton btnHide = new JButton("隐藏 ( H )");
	private JButton btnRevert = new JButton("恢复默认设置 ( R )");
	
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	
	private JLabel jLabel1 = new JLabel("请选择您在此表格中要显示的列：");
	private JLabel jLabel2 = new JLabel("所选列的宽度(像素)：");
	
	//private FIntegerField widthText = new FIntegerField();
	
	private JLabel jLabel3 = new JLabel("设置锁定列数：");
	//private FIntegerField lockedNumText = new FIntegerField();
	
	private String defaultXML = null;//用于恢复表格列的默认显示格式
	
	/**
	 * 构造函数
	 * @param sourceTable 源表，即被操作的表格
	 */
	public FTableCustomizerDialog (FTable sourceTable, String defaultXML) {
		super((JFrame) null, "定制表格列", true);
		this.sourceTable = sourceTable;
		this.defaultXML = defaultXML;
		try {
			jbInit();
			//loadSourceTableColumns();
			showThisDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 界面初始化
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		//
		jLabel1.setBounds(6, 4, 200, 16);
		//设置本对话框中的表格
		FBaseTableColumn colChk = localTable.getColumnById("isCheck");
		colChk.sizeWidthToFit();
		FBaseTableColumn colId = new FBaseTableColumn();
		colId.setId("column_id");
		colId.setVisible(false);
		FBaseTableColumn colTitle = new FBaseTableColumn();
		colTitle.setId("column_title");
		colTitle.setWidth(130);
		FBaseTableColumn colWidth = new FBaseTableColumn();
		colWidth.setId("column_width");
		colWidth.setVisible(false);
		FBaseTableColumn colIsLocked = new FBaseTableColumn();
		colIsLocked.setId("column_isLocked");
		colIsLocked.setVisible(false);
		
		localTable.addControl(colId);
		localTable.addControl(colTitle);
		localTable.addControl(colWidth);
		localTable.addControl(colIsLocked);
		
		localTable.setIsCheckBoxAffectedByClickRow(false);
		localTable.setShowRowNumber(false);
		localTable.setTableHeaderHidden();
		localTable.getRightActiveTable().setShowVerticalLines(false);
		localTable.getRightActiveTable().setShowHorizontalLines(false);
		localTable.getRightActiveTable().setIntercellSpacing(new Dimension(0, 0));
		localTable.getRightActiveTable().setShowGrid(false);
		localTable.setBounds(4, 30, 180, 260);
		localTable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				localTable_mouseClicked();
			}
		});
		localTable.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					localTable_mouseClicked();
				}
			}
		});
		//设置操作按钮
		//上移
		btnUp.setBounds(195, 40, 70, 20);
		btnUp.setMargin(new Insets(0, 0, 0, 0));
		btnUp.setMnemonic('U');
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUp_actionPerformed();
			}
		});
		//下移
		btnDown.setBounds(195, 65, 70, 20);
		btnDown.setMargin(new Insets(0, 0, 0, 0));
		btnDown.setMnemonic('D');
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDown_actionPerformed();
			}
		});
		//显示
		btnShow.setBounds(195, 90, 70, 20);
		btnShow.setMargin(new Insets(0, 0, 0, 0));
		btnShow.setMnemonic('S');
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnShow_actionPerformed();
			}
		});
		//隐藏
		btnHide.setBounds(195, 115, 70, 20);
		btnHide.setMargin(new Insets(0, 0, 0, 0));
		btnHide.setMnemonic('H');
		btnHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnHide_actionPerformed();
			}
		});
		//
		jLabel3.setBounds(195, 190, 100, 16);
		//锁定列数文本框
		/*
		lockedNumText.setBounds(280, 188, 25, 20);
		lockedNumText.setTitleVisible(false);
		lockedNumText.setMinValue(0);
		lockedNumText.setMaxValue(99);
		lockedNumText.setValue(new Integer(getShowLockedColumnsCount()));
		*/
		//恢复默认设置
		btnRevert.setBounds(195, 240, 110, 20);
		btnRevert.setMargin(new Insets(0, 0, 0, 0));
		btnRevert.setMnemonic('R');
		btnRevert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRevert_actionPerformed();
			}
		});
		//
		jLabel2.setBounds(6, 310, 200, 16);
		//列框文本框
		/*
		widthText.setBounds(150, 308, 35, 20);
		widthText.setTitleVisible(false);
		widthText.setMinValue(0);
		widthText.setMaxValue(1000);
		widthText.getEditor().addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e) {
				widthText_keyReleased();
			}
		});
		*/
		//
		FEtchedLine line = new FEtchedLine();
		line.setBounds(4, 340, 263, 4);
		//确定
		btnOk.setBounds(115, 360, 70, 20);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk_actionPerformed();
			}
		});
		//取消
		btnCancel.setBounds(195, 360, 70, 20);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel_actionPerformed();
			}
		});
		
		//
		panel1.setLayout(null);
		panel1.setBounds(4, 4, 320, 400);
		
		panel1.add(jLabel1);
		panel1.add(localTable);
		panel1.add(btnUp);
		panel1.add(btnDown);
		panel1.add(btnShow);
		panel1.add(btnHide);
		panel1.add(jLabel3);
		//panel1.add(lockedNumText);
		panel1.add(btnRevert);
		panel1.add(jLabel2);
		//panel1.add(widthText);
		panel1.add(line);
		panel1.add(btnOk);
		panel1.add(btnCancel);
		
		getContentPane().add(panel1);
		
	}
	
	/**
	 * 显示此对话框
	 *
	 */
	private void showThisDialog() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-325)/2,
				(screenSize.height-425)/2, 325, 425);
		show();
	}
	
	/**
	 * 加载本对话框表格数据
	 *
	 */
	/*
	private void loadSourceTableColumns() {
		List allColList = new ArrayList();
		FBaseTableColumn[] allCols = this.sourceTable.getAllColumns();
		FBaseTableColumn[] showCols = this.sourceTable.getShowColumns();
		//显示所有源表格列信息
		for (int i=0; allCols!=null && i<allCols.length; i++) {
			XMLData ltData = new XMLData();
			ltData.put("column_id", allCols[i].getId());
			ltData.put("column_title", allCols[i].getTitle());
			if (allCols[i].isVisible()) {
				ltData.put("column_width", new Integer(allCols[i].getWidth()));
			} else {
				ltData.put("column_width", new Integer(FBaseTableColumn.DEFAULT_COLWIDTH));
			}
			ltData.put("column_isLocked", new Boolean(allCols[i].isLocked()));
			allColList.add(ltData);
		}
		localTable.setData(allColList);
		//勾选源表格可显示的列
		for (int i=0; i<allColList.size(); i++) {
			XMLData colData = (XMLData)allColList.get(i);
			for (int j=0; showCols!=null && j<showCols.length; j++) {
				if (colData.get("column_id").equals(showCols[j].getId())) {
					this.localTable.setCheckBoxSelectedAtRow(i, true);
					break;
				}
			}
		}
	}
	*/
	/**
	 * 恢复默认设置
	 *
	 */
	private void btnRevert_actionPerformed() {
		
	}
	
	/**
	 * 上移按钮操作
	 *
	 */
	private void btnUp_actionPerformed() {
		int curLocIndex = this.localTable.getCurrentRowIndex();
		//如果当前选中的列不第一列，并且它的前一列不是锁定列，则此列可以上移；
		//否则，此列不能上移。
		if (curLocIndex > 0) {
			this.localTable.moveUpCurrentRow();
			if (!this.btnDown.isEnabled()) {
				this.btnDown.setEnabled(true);
			}
			//
//			XMLData prePreLocCol = null;
//			if (curLocIndex - 2 >= 0) {
//				prePreLocCol = (XMLData) ((FTableModel) this.localTable
//						.getModel()).getData().get(
//						localTable.convertViewRowIndexToModel(curLocIndex - 2));
//			}
			if (curLocIndex-1 == 0
//					|| (prePreLocCol!=null &&
//							((Boolean)prePreLocCol.get("column_isLocked")).booleanValue())
					) {
				this.btnUp.setEnabled(false);
			}
		} 
	}
	
	/**
	 * 下移按钮操作 
	 *
	 */
	private void btnDown_actionPerformed() {
		this.localTable.moveDownCurrentRow();
		if (!this.btnUp.isEnabled()) {
			this.btnUp.setEnabled(true);
		}
		int curLocIndex = this.localTable.getCurrentRowIndex();
		if (curLocIndex+1 == this.localTable.getRowCount()) {
			this.btnDown.setEnabled(false);
		}
	}
	
	/**
	 * 显示按钮操作
	 *
	 */
	private void btnShow_actionPerformed() {
		if (this.localTable.getCurrentRowIndex() != -1) {
			this.localTable.setCheckBoxSelectedAtRow(
					this.localTable.getCurrentRowIndex(), true);
			this.btnShow.setEnabled(false);
			this.btnHide.setEnabled(true);
		}
	}
	
	/**
	 * 隐藏按钮操作
	 *
	 */
	private void btnHide_actionPerformed() {
		if (this.localTable.getCurrentRowIndex() != -1) {
			this.localTable.setCheckBoxSelectedAtRow(
					this.localTable.getCurrentRowIndex(), false);
			this.btnShow.setEnabled(true);
			this.btnHide.setEnabled(false);
		}
	}
	
	/**
	 * 确定按钮操作
	 *
	 */
	private void btnOk_actionPerformed() {

		
	}
	
	/**
	 * 取消按钮操作
	 *
	 */
	private void btnCancel_actionPerformed() {
		this.dispose();
	}
	
	/**
	 * 本地对话框中表格的鼠标点击事件
	 * @param e
	 */
	private void localTable_mouseClicked() {

	}
	
	/**
	 * 列框文本框键盘事件
	 *
	 */
	private void widthText_keyReleased() {
		
	}
	
	/**
	 * 得到可见的锁定列的列数
	 * @return
	 */
	private int getShowLockedColumnsCount() {
		int count = 0;
		JTable lockedTable = this.sourceTable.getLeftLockedTable();
		for (int i = 1; i < lockedTable.getColumnCount(); i++) {
			FBaseTableColumn col = (FBaseTableColumn) lockedTable
					.getColumnModel().getColumn(i);
			if (col.isVisible() && !"isCheck".equals(col.getId())) {
				++count;
			}
		}
		return count;
	}


}
