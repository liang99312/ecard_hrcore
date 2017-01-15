package com.foundercy.pf.control.table;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.Format;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foundercy.pf.control.AbstractRefDataField;
import com.foundercy.pf.control.RefModel;


/**
 * 公式字段
 * @author Administrator
 *
 */
public class FFormulaField extends AbstractRefDataField{
	
	private static final long serialVersionUID = -8582025520099266921L;
	
	//公式编辑框
	private JTextField formulaText = null;
	
	//公式控件对应的数据业务模型
	private FFormulaRefModel refModel = new FFormulaRefModel();
	
	//可编辑性  默认为不可编辑
	private boolean editable = false;   
	/**
	 * 靠左排列
	 */
	public static final String LEFT = "left";

	/**
	 * 靠右排列
	 */
	public static final String RIGHT = "right";

	/**
	 * 居中排列
	 */
	public static final String CENTER = "center";
	
	/**
	 * 构造函数
	 *
	 */
	public FFormulaField() {
		this(null);
	}

	/**
	 * 构造函数
	 * @param strTitle 标题
	 */
	public FFormulaField(String strTitle) {
		super.setTitle(strTitle);
		allLayout();
	}
	
	/**
	 * 	取得编辑控件
	 */
	public JComponent getEditor() {
		if (formulaText == null) {
			
			formulaText = new JTextField();
			
			//默认靠右对齐
			formulaText.setHorizontalAlignment(JTextField.RIGHT);
			
			formulaText.setEditable(this.editable);
			
			//增加回车键响应事件
			formulaText.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						formulaText.transferFocus();
					}
				}

			});
		}
		return this.formulaText;
	}
	
	
	
	/**
	 * 将当前焦点设置到公式文本编辑框
	 */
	protected void setFocusNow() {
		formulaText.requestFocus();
		
	}

	
	
	/**
	 * 设置显示格式
	 * @param format
	 */
	public void setFormat(Format format){
		this.refModel.setFormat(format);
	}
	
	
	/**
	 * 取得对应的数据模型
	 */
	public RefModel getRefModel() {
		return this.refModel;
	}
	
	
	
	/**
	 * 设置公式表达式
	 * @param formula
	 */
	public void setRefModel(String formula){
		if(formula != null && !"".equals(formula)){
			this.refModel.setFormula(formula);
		}
		
	}
	

	/**
	 * 取得显示值
	 */
	public Object getValue() {
		
		String formula = formulaText.getText();
		//System.out.println("Excute FormulaField.getValue() method："+formula);
		if(formula == null)formula = "";
		formula = formula.trim();
		
		this.refModel.setFormula(formula);
		
		return this.refModel.getFormula();
		
	}
	
	
	/**
	 * 设置文本框内容水平对齐方式
	 * 
	 * @param alignment
	 *            FDecimalField.LEFT－左对齐；FDecimalField.RIGHT－右对齐；FDecimalField.CENTER－居中对齐
	 */
	public void setAlignment(String alignment) {
		if (alignment == null)
			return;
		if (alignment.equalsIgnoreCase(LEFT)) {
			this.formulaText.setHorizontalAlignment(JTextField.LEFT);
		}
		if (alignment.equalsIgnoreCase(RIGHT)) {
			this.formulaText.setHorizontalAlignment(JTextField.RIGHT);
		}
		if (alignment.equalsIgnoreCase(CENTER)) {
			this.formulaText.setHorizontalAlignment(JTextField.CENTER);
		}
	}
	
	/**
	 * 设置控件是否可编辑，子类应对此函数进行重载
	 * @param editable false为不可编辑，true为可编辑
	 */
	public void setEditable(boolean _editable)
	{
		this.editable = _editable;
		if(this.formulaText != null){
			this.formulaText.setEditable(_editable);
		}
		
	}
	
	/**
	 * 判断控件是否可以编辑
	 * @return false为不可编辑，true为可编辑
	 */
	public boolean isEditable()
	{
		return this.editable;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
