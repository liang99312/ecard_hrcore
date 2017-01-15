package com.foundercy.pf.control;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * ��JTextField�����ַ��ĳ��Ƚ�������,����ֵΪbyteֵ��Ϊ�˴������ݿ����Ӣ�����⣩ <br>
 * 
 * <pre>
 * ʹ�ã� textfield1 = new JTextField(15); textfield1.setDocument(new JTextFieldLimit(10));
 * </pre>
 * 
 * @author wuxianghui
 */
public class FTextFieldLimit extends PlainDocument {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7224482954486184976L;
	private int limit;
    // ��д��ĸת��ѡ��
    private boolean toUppercase = false;

    public FTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    public FTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
        toUppercase = upper;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;
        if ((getText(0, getLength()).getBytes().length + str.getBytes().length) <= limit) {
            if (toUppercase)
                str = str.toUpperCase();
            super.insertString(offset, str, attr);
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        JTextField t = new JTextField(20);
        FTextFieldLimit l = new FTextFieldLimit(10);
        t.setDocument(l);
        f.getContentPane().add(t);
        f.setVisible(true);

    }
}