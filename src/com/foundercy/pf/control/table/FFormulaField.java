package com.foundercy.pf.control.table;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.Format;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foundercy.pf.control.AbstractRefDataField;
import com.foundercy.pf.control.RefModel;


/**
 * ��ʽ�ֶ�
 * @author Administrator
 *
 */
public class FFormulaField extends AbstractRefDataField{
	
	private static final long serialVersionUID = -8582025520099266921L;
	
	//��ʽ�༭��
	private JTextField formulaText = null;
	
	//��ʽ�ؼ���Ӧ������ҵ��ģ��
	private FFormulaRefModel refModel = new FFormulaRefModel();
	
	//�ɱ༭��  Ĭ��Ϊ���ɱ༭
	private boolean editable = false;   
	/**
	 * ��������
	 */
	public static final String LEFT = "left";

	/**
	 * ��������
	 */
	public static final String RIGHT = "right";

	/**
	 * ��������
	 */
	public static final String CENTER = "center";
	
	/**
	 * ���캯��
	 *
	 */
	public FFormulaField() {
		this(null);
	}

	/**
	 * ���캯��
	 * @param strTitle ����
	 */
	public FFormulaField(String strTitle) {
		super.setTitle(strTitle);
		allLayout();
	}
	
	/**
	 * 	ȡ�ñ༭�ؼ�
	 */
	public JComponent getEditor() {
		if (formulaText == null) {
			
			formulaText = new JTextField();
			
			//Ĭ�Ͽ��Ҷ���
			formulaText.setHorizontalAlignment(JTextField.RIGHT);
			
			formulaText.setEditable(this.editable);
			
			//���ӻس�����Ӧ�¼�
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
	 * ����ǰ�������õ���ʽ�ı��༭��
	 */
	protected void setFocusNow() {
		formulaText.requestFocus();
		
	}

	
	
	/**
	 * ������ʾ��ʽ
	 * @param format
	 */
	public void setFormat(Format format){
		this.refModel.setFormat(format);
	}
	
	
	/**
	 * ȡ�ö�Ӧ������ģ��
	 */
	public RefModel getRefModel() {
		return this.refModel;
	}
	
	
	
	/**
	 * ���ù�ʽ���ʽ
	 * @param formula
	 */
	public void setRefModel(String formula){
		if(formula != null && !"".equals(formula)){
			this.refModel.setFormula(formula);
		}
		
	}
	

	/**
	 * ȡ����ʾֵ
	 */
	public Object getValue() {
		
		String formula = formulaText.getText();
		//System.out.println("Excute FormulaField.getValue() method��"+formula);
		if(formula == null)formula = "";
		formula = formula.trim();
		
		this.refModel.setFormula(formula);
		
		return this.refModel.getFormula();
		
	}
	
	
	/**
	 * �����ı�������ˮƽ���뷽ʽ
	 * 
	 * @param alignment
	 *            FDecimalField.LEFT������룻FDecimalField.RIGHT���Ҷ��룻FDecimalField.CENTER�����ж���
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
	 * ���ÿؼ��Ƿ�ɱ༭������Ӧ�Դ˺�����������
	 * @param editable falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public void setEditable(boolean _editable)
	{
		this.editable = _editable;
		if(this.formulaText != null){
			this.formulaText.setEditable(_editable);
		}
		
	}
	
	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * @return falseΪ���ɱ༭��trueΪ�ɱ༭
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
