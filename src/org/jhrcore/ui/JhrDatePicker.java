/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import org.jhrcore.ui.listener.IDatePickerListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import org.apache.log4j.Logger;
import org.jhrcore.util.DateUtil;

/**
 *
 * @author mxliteboss
 */
public class JhrDatePicker extends JPanel {

    private Logger log = Logger.getLogger(JhrDatePicker.class.getName());
    private boolean editable = true;
    private JFormattedTextField picker;
    private Date date;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormatter df = new DateFormatter(format);
    private String date_str;
    private String format_str = "####-##-##";
    private IDatePickerListener datePickerListener;
    private String format_str_y = "yyyy-MM-dd";

    public JhrDatePicker() {
        this("yyyy-MM-dd");
    }

    public JhrDatePicker(String format_str) {
        super(new BorderLayout());
        this.format_str_y = format_str;
        format = new SimpleDateFormat(format_str);
        format_str = format_str.replace("y", "#");
        format_str = format_str.replace("m", "#");
        format_str = format_str.replace("M", "#");
        format_str = format_str.replace("d", "#");
        format_str = format_str.replace("H", "#");
        format_str = format_str.replace("s", "#");
        format_str = format_str.replace("S", "#");
        format_str = format_str.replace("h", "#");
        this.format_str = format_str;
        init();

    }

    public JhrDatePicker(Date date) {
        this(date,"yyyy-MM-dd");
    }

    public JhrDatePicker(Date date,String format_str) {
        this(format_str);
        this.setDate(date);
    }

    public Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
        date_str = format.format(date);
        this.picker.setValue(date_str);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.picker.setEnabled(enabled);
    }

    public void setEditable(boolean editable) {
        this.picker.setEditable(editable);
    }

    public String getDate_str() {
        return date_str;
    }

    public void setDate_str(String date_str) {
        this.date_str = date_str;
    }

    public void setBack_color(Color c) {
        this.picker.setBackground(c);
    }

    public void addDatePickListener(IDatePickerListener datePickerListener) {
        this.datePickerListener = datePickerListener;
    }

    private void init() {
        setBorder(null);
        setFocusTraversalKeysEnabled(false);
        this.setPreferredSize(new Dimension(80, 24));
        this.setMaximumSize(new Dimension(80, 24));
        picker = new JFormattedTextField(df);
        MaskFormatter mf1;
        try {
            mf1 = new MaskFormatter(format_str);
            mf1.setPlaceholderCharacter('_');
            picker = new JFormattedTextField(mf1);
        } catch (ParseException ex) {
            log.error(ex);
        }
        new EnterToTab(picker);
        picker.setEditable(editable);
        date = new Date();
        picker.setText(format.format(date));
        add(picker, BorderLayout.CENTER);
        picker.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) {
                    return;
                }
                if (!picker.isEnabled()) {
                    return;
                }
                if(!format_str_y.toLowerCase().contains("d")){
                    return;
                }
                DateChooser dateChooser = new DateChooser(((SimpleDateFormat)format).toPattern());
                dateChooser.setDate2(DateUtil.StrToDate((String) picker.getValue()));
                JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(picker), "日期时间选择", true);
                dateChooser.setDialog(dialog);
                dialog.setLocationRelativeTo(picker);
                dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                dialog.getContentPane().add(dateChooser, BorderLayout.CENTER);
                dialog.pack();
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                if (dateChooser.isPicked()) {
                    date = dateChooser.getDate();
                    date_str = format.format(date);
                    picker.setValue(date_str);
                    if (datePickerListener != null) {
                        datePickerListener.actionPerformed();
                    }
                }
            }
        });
        picker.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    date = format.parse(picker.getText());
                    date_str = format.format(date);
                    picker.setValue(date_str);
                    if (datePickerListener != null) {
                        datePickerListener.actionPerformed();
                    }
                } catch (Exception e1) {
                    log.error(e1);
                    //JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(picker), "输入的时间格式不正确", "提示", JOptionPane.OK_CANCEL_OPTION);
                }

            }
        });


    }

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        JhrDatePicker jdp = new JhrDatePicker("yyyy-MM-dd");
        jdp.setPreferredSize(new Dimension(100, 22));
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(jdp, BorderLayout.NORTH);
        jf.add(pnl);
        jf.setSize(600, 400);
        jf.setVisible(true);
        System.out.println("date_:" + jdp.getDate().toString() + ";date_str:" + jdp.getDate_str());
    }
}
