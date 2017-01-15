
package org.jhrcore.client.report;

//ֻ���������ֵ��ı���,�̳���JTextField,���Կ��Ե�������ֱ������
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Serializable;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JNumberField extends JTextField implements ActionListener,
        FocusListener, Serializable {

    public JNumberField() {
        this(true);
    }

    public JNumberField(boolean addAction) {
        this(20, 0, addAction);
    }

    public JNumberField(int intPartLen) {
        this(intPartLen, true);
    }

    public JNumberField(int intPartLen, boolean addAction) {
        this(intPartLen, 0, addAction);
    }

    /**
     * �����ı���ĳ���
     * 
     * @param maxLen
     * @param decLen
     */
    public JNumberField(int maxLen, int decLen) {
        this(maxLen + decLen, decLen, true);
    }

    public JNumberField(int maxLen, int decLen, boolean addAction) {
//  setPreferredSize(new Dimension(150, 25));// �����������ѡ��С
        // this.setSize(150, 25);
        setDocument(new NumberDocument(maxLen, decLen));// �����ı������һ���ı�
        super.setHorizontalAlignment(JTextField.LEFT);// ���ô���߿�ʼ����
        if (addAction) {
            addActionListener(this);
        }
        addFocusListener(this);
    }

    public JNumberField(int maxLen, int decLen, double minRange,
            double maxRange, boolean addAction) {
        setPreferredSize(new Dimension(150, 25));
        setDocument(new NumberDocument(maxLen, decLen, minRange, maxRange));
        super.setHorizontalAlignment(JTextField.RIGHT);
        if (addAction) {
            addActionListener(this);
        }
        addFocusListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        transferFocus();
    }

    @Override
    public void focusGained(FocusEvent e) {
        selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
}

class NumberDocument extends PlainDocument {// �������ַ�����

    int maxLength = 20;// Ĭ�ϵ��Ƕ�ʮλ
    int decLength = 0;
    double minRange = -Double.MAX_VALUE;
    double maxRange = Double.MAX_VALUE;

    public NumberDocument(int maxLen, int decLen) {
        maxLength = maxLen;
        decLength = decLen;
    }

    /**
     * @param decLen
     *            int С��λ����
     * @param maxLen
     *            int ��󳤶�(��С��λ)
     * @param minRange
     *            double ��Сֵ
     * @param maxRange
     *            double ���ֵ
     */
    public NumberDocument(int maxLen, int decLen, double minRange,
            double maxRange) {
        this(maxLen, decLen);
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public NumberDocument(int decLen) {
        decLength = decLen;
    }

    public NumberDocument() {
    }

    @Override
    public void insertString(int offset, String s, AttributeSet a)
            throws BadLocationException {
        String str = getText(0, getLength());
        if ( // ����Ϊf,F,d,D
                s.equals("F")
                || s.equals("f")
                || s.equals("D")
                || s.equals("d")
                // ��һλ��0ʱ,�ڶ�λֻ��ΪС����
                || (str.trim().equals("0") && !s.substring(0, 1).equals(".") && offset != 0)
                // ����ģʽ��������С����
                || (s.equals(".") && decLength == 0)) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String strIntPart = "";
        String strDecPart = "";
        String strNew = str.substring(0, offset) + s
                + str.substring(offset, getLength());
        strNew = strNew.replaceFirst("-", ""); // ���������븺��
        int decPos = strNew.indexOf(".");
        if (decPos > -1) {
            strIntPart = strNew.substring(0, decPos);
            strDecPart = strNew.substring(decPos + 1);
        } else {
            strIntPart = strNew;
        }
        if (strIntPart.length() > (maxLength - decLength)
                || strDecPart.length() > decLength
                || (strNew.length() > 1 && strNew.substring(0, 1).equals("0") && !strNew.substring(1, 2).equals("."))) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        try {
            if (!strNew.equals("") && !strNew.equals("-")) {// ���������븺��
                double d = Double.parseDouble(strNew);
                if (d < minRange || d > maxRange) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        super.insertString(offset, s, a);
    }
}
