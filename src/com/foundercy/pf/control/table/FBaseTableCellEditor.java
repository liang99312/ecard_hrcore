package com.foundercy.pf.control.table;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.foundercy.pf.control.AbstractDataField;
import com.foundercy.pf.control.FCheckBox;
import com.foundercy.pf.control.FTextField;
import com.foundercy.pf.control.ValueChangeEvent;
import com.foundercy.pf.control.ValueChangeListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */

public class FBaseTableCellEditor extends DefaultCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5687570288491726370L;

	private boolean isListenerAdded = false;
	
	private int align = -1;

	public FBaseTableCellEditor() {
		//很遗憾必须这样去构造
		super(new JTextField());
		this.editorComponent = new FTextField();
		this.setClickCountToStart(2);
	}

	public FBaseTableCellEditor(AbstractDataField dataField) {
		//很遗憾必须这样去构造
		super(new JTextField());
		this.editorComponent = dataField;
	}

	public void setDataField(AbstractDataField dataField) {
		this.editorComponent = dataField;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		((AbstractDataField) this.editorComponent).setTitleVisible(false);
		((AbstractDataField) this.editorComponent).setValue(value);

		if (isSelected) {
			((JComponent) editorComponent).setBackground(table
					.getSelectionBackground());
		}
		
		/**************************************文本显示***********************************/
		int alg = this.getTextAlignment();
		if (alg != SwingConstants.CENTER && alg != SwingConstants.LEFT
				&& alg != SwingConstants.RIGHT) {
			alg = this.getDefaultAlignment(value);
		}
		//设置文本对齐方式,及内容。
		if (this.editorComponent instanceof FTextField) {
			((JTextField)((FTextField)this.editorComponent).getEditor()).setHorizontalAlignment(alg);
		}
		
		if (!isListenerAdded) {
			if (this.editorComponent instanceof FCheckBox) {
				((AbstractDataField) this.editorComponent)
						.addValueChangeListener(new ValueChangeListener() {
							public void valueChanged(ValueChangeEvent vce) {
								FBaseTableCellEditor.this.fireEditingStopped();
							}
						});
			} else {

			}
			isListenerAdded = true;
		}
		
		if(this.editorComponent instanceof AbstractDataField) {
			JComponent editor = ((AbstractDataField)this.editorComponent).getEditor();
			editor.setBorder(null);
			return editor;
		}
		return this.editorComponent;
	}

	public Object getCellEditorValue() {
		Object v = ((AbstractDataField) this.editorComponent).getValue();
		return v;
	}
	
	/**
	 * 设置文本水平对齐方式
	 * @param align
	 */
	public void setTextAlignment(int align) {
		this.align = align;
	}
	
	/**
	 * 得到文本水平对齐方式
	 * @return
	 */
	public int getTextAlignment() {
		return this.align;
	}
	
	/**
	 * 根据值类型得到缺省的文本对齐方式
	 * @param value
	 * @return
	 */
	public int getDefaultAlignment(Object value) {
		if (value == null)
			return SwingConstants.LEFT;

		if (value instanceof Number) {
			return SwingConstants.RIGHT;
		}
		if (value instanceof Boolean) {
			return SwingConstants.CENTER;
		}
//		if(value.equals(FBaseTableSumRowModel.TOTAL_TITLE)) {
//			return SwingConstants.RIGHT;
//		}
		return SwingConstants.LEFT;
	}
	
}