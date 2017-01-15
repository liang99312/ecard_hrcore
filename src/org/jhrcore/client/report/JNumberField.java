
package org.jhrcore.client.report;

//只能输入数字的文本框,继承了JTextField,所以可以当做工具直接来用
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
     * 控制文本框的长度
     * 
     * @param maxLen
     * @param decLen
     */
    public JNumberField(int maxLen, int decLen) {
        this(maxLen + decLen, decLen, true);
    }

    public JNumberField(int maxLen, int decLen, boolean addAction) {
//  setPreferredSize(new Dimension(150, 25));// 设置组件的首选大小
        // this.setSize(150, 25);
        setDocument(new NumberDocument(maxLen, decLen));// 设置文本框关联一个文本
        super.setHorizontalAlignment(JTextField.LEFT);// 设置从左边开始输入
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

class NumberDocument extends PlainDocument {// 不包含字符的类

    int maxLength = 20;// 默认的是二十位
    int decLength = 0;
    double minRange = -Double.MAX_VALUE;
    double maxRange = Double.MAX_VALUE;

    public NumberDocument(int maxLen, int decLen) {
        maxLength = maxLen;
        decLength = decLen;
    }

    /**
     * @param decLen
     *            int 小数位长度
     * @param maxLen
     *            int 最大长度(含小数位)
     * @param minRange
     *            double 最小值
     * @param maxRange
     *            double 最大值
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
        if ( // 不能为f,F,d,D
                s.equals("F")
                || s.equals("f")
                || s.equals("D")
                || s.equals("d")
                // 第一位是0时,第二位只能为小数点
                || (str.trim().equals("0") && !s.substring(0, 1).equals(".") && offset != 0)
                // 整数模式不能输入小数点
                || (s.equals(".") && decLength == 0)) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String strIntPart = "";
        String strDecPart = "";
        String strNew = str.substring(0, offset) + s
                + str.substring(offset, getLength());
        strNew = strNew.replaceFirst("-", ""); // 控制能输入负数
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
            if (!strNew.equals("") && !strNew.equals("-")) {// 控制能输入负数
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
