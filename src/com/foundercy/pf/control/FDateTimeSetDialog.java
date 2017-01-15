package com.foundercy.pf.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.foundercy.pf.util.Tools;

/**
 * This code was generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a
 * for-profit company or business) then you should purchase
 * a license - please visit www.cloudgarden.com for details.
 */
public class FDateTimeSetDialog
    extends JDialog {

  /**
	 * 
	 */
  private static final long serialVersionUID = 5993795047856049433L;
  JButton btnOperate = new JButton("确定"),
          btnClose = new JButton("取消");
  JTextField txtYear = new JTextField();
  JTextField txtMonth = new JTextField();
  JTextField txtDay = new JTextField();
  JTextField txtHour = new JTextField();
  JTextField txtMin = new JTextField();
  JTextField txtSecond = new JTextField();

  JPanel ReportParInfoPanel = new JPanel();
  JPanel operatePanel = new JPanel();
  GridBagConstraints c;
  GridBagLayout gridBag;
  JLabel label = new JLabel();
  String date = "";
  String strSysDate = "";
  String year = "";
  String month = "";
  String day = "";
  String second = "";
  String min = "";
  String hour = "";

  public void setNextDate(String date){
    this.date = date;
  }
  public String getNextDate(){
    return date;
  }

  public FDateTimeSetDialog(JFrame owner, String title, boolean model, Object time) throws HeadlessException {
    super(owner, title, model);
    
    this.setResizable(true);
    initGUI();
  }

  /**
   * Initializes the GUI.
   * Auto-generated code - any changes you make will disappear.
   */
  public void initGUI() {
    try {
      Dimension d = new Dimension(45, 20);
      txtYear.setPreferredSize(d);
      Dimension dd = new Dimension(25, 20);
      txtMonth.setPreferredSize(dd);
      txtDay.setPreferredSize(dd);
      txtHour.setPreferredSize(dd);
      txtMin.setPreferredSize(dd);
      txtSecond.setPreferredSize(dd);

      txtYear.setDocument(new FTextFieldLimit(4));
      txtDay.setDocument(new FTextFieldLimit(2));
      txtHour.setDocument(new FTextFieldLimit(2));
      txtMonth.setDocument(new FTextFieldLimit(2));
      txtMin.setDocument(new FTextFieldLimit(2));
      txtSecond.setDocument(new FTextFieldLimit(2));
      operatePanel.add(btnOperate);
      operatePanel.add(btnClose);
      btnOperate.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          btnAddActionPerformed(evt);
        }
      });

      btnClose.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          btnExitActionPerformed(evt);
        }
      });

      gridBag = new GridBagLayout();
      c = new GridBagConstraints();
      ReportParInfoPanel.setLayout(gridBag);
      c.gridx = 0;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1.0;
      label = new JLabel("年");
      addReportInfoComponent(c, gridBag, label, txtYear);

      c.gridx = 1;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1.0;
      label = new JLabel("月");
      addReportInfoComponent(c, gridBag, label, txtMonth);

      c.gridx = 2;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1.0;
      label = new JLabel("日");
      addReportInfoComponent(c, gridBag, label, txtDay);

      c.gridx = 3;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1.0;
      label = new JLabel("时");
      addReportInfoComponent(c, gridBag, label, txtHour);

      c.gridx = 4;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1.0;
      label = new JLabel("分");
      addReportInfoComponent(c, gridBag, label, txtMin);

      c.gridx = 5;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1.0;
      label = new JLabel("秒");
      addReportInfoComponent(c, gridBag, label, txtSecond);

      postInitGUI();
//      this.getContentPane().setLayout(new FlowLayout());
      this.getContentPane().add(ReportParInfoPanel, BorderLayout.CENTER);
      this.getContentPane().add(operatePanel, BorderLayout.SOUTH);
      this.setSize(5000, 3000);
      this.pack();
      this.setCentered();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

	public void setCentered(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation(
			(screenSize.width - frameSize.width) / 2,
			(screenSize.height - frameSize.height) / 2);
		
	}
	
  public void addReportInfoComponent(GridBagConstraints c,
                                     GridBagLayout gridBag,
                                     JComponent label, JComponent comp) {
    JPanel jpanel = new JPanel();
    if(comp != null)
    jpanel.add(comp);
    jpanel.add(label);
    gridBag.setConstraints(jpanel, c);
    ReportParInfoPanel.add(jpanel);
  }

  public void decorateButton(JButton button) {
    button.setVerticalTextPosition(SwingConstants.BOTTOM);
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    int lenth = button.getText().length();
    button.setPreferredSize(new Dimension(24 + lenth * 10, 42));
    button.setMinimumSize(new Dimension(24 + lenth * 10, 42));
    button.setMaximumSize(new Dimension(24 + lenth * 10, 42));
    button.addMouseListener(new
                            MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        JButton b = (JButton) e.getSource();
        b.setBorder(new ThinBevelBorder(ThinBevelBorder.RAISED));
      }

      public void mouseExited(MouseEvent e) {
        if (e.getSource()instanceof JButton) {
          JButton b = (JButton) e.getSource();
          b.setBorder(new EmptyBorder(2, 2, 2, 2));
        }
      }

      public void mousePressed(MouseEvent e) {
        if (e.getSource()instanceof JButton) {
          JButton b = (JButton) e.getSource();
          b.setBorder(new ThinBevelBorder(ThinBevelBorder.LOWERED));
        }
      }

      public void mouseReleased(MouseEvent e) {
        if (e.getSource()instanceof JButton) {
          JButton b = (JButton) e.getSource();
          String text = b.getText();
          b.setBorder(new EmptyBorder(2, 2, 2, 2));
          b.setText(text);
        }
      }
    });

  }

  protected void btnExitActionPerformed(ActionEvent evt) {
    this.dispose();
  }

  protected void btnAddActionPerformed(ActionEvent evt) {
    //添加显示
    if (txtYear.getText() == null || txtYear.getText().equals("")) {
      MessageBox.msgBox(this, "年度不能为空", 1);
      txtYear.requestFocus();
      return;
    }
/*
    if((CheckNumber.isNumber(txtYear.getText()) == false)||
       (txtYear.getText().trim().length() != 4)){
      MessageBox.msgBox(this,"时间间隔只能输入数字或者请输入四位的年度如2005！",0);
      txtYear.requestFocus(true);
      return;
    }

    if((CheckNumber.isNumber(txtMonth.getText()) == false)||
       (Integer.parseInt(txtMonth.getText().trim())) > 12){
      MessageBox.msgBox(this,"月份只能输入数字或者最大月份不能超过12！",0);
      txtMonth.requestFocus(true);
      return;
    }

    if((CheckNumber.isNumber(txtDay.getText()) == false)||
       (Integer.parseInt(txtDay.getText().trim())) > 31){
      MessageBox.msgBox(this,"日期只能输入数字或者最大日期不能超过31！",0);
      txtDay.requestFocus(true);
      return;
    }

    if((CheckNumber.isNumber(txtHour.getText()) == false)||
       (Integer.parseInt(txtHour.getText().trim())) > 24){
      MessageBox.msgBox(this,"小时只能输入数字或者最大小时不能超过24！",0);
      txtHour.requestFocus(true);
      return;
    }

    if((CheckNumber.isNumber(txtMin.getText()) == false)||
       (Integer.parseInt(txtMin.getText().trim())) > 60){
      MessageBox.msgBox(this,"分钟只能输入数字或者最大分钟不能超过60！",0);
      txtMin.requestFocus(true);
      return;
    }
    if((CheckNumber.isNumber(txtSecond.getText()) == false)||
       (Integer.parseInt(txtSecond.getText().trim())) > 60){
      MessageBox.msgBox(this,"秒只能输入数字或者最大秒数不能超过60！",0);
      txtSecond.requestFocus(true);
      return;
    }
*/
    if (txtDay.getText() == null || txtDay.getText().equals("")) {
      txtDay.setText("1");
    }

    if (txtMonth.getText() == null || txtMonth.getText().equals("")) {
      txtMonth.setText("1");
    }

    if (txtHour.getText() == null || txtHour.getText().equals("")) {
      txtHour.setText("0");
    }

    if (txtMin.getText() == null || txtMin.getText().equals("")) {
      txtMin.setText("0");
    }

    if (txtSecond.getText() == null || txtSecond.getText().equals("")) {
      txtSecond.setText("0");
    }

    ///拼nextDate格式yyyy-MM-dd HH:mm:ss
    StringBuffer finalDate = new StringBuffer();
    finalDate.append(txtYear.getText().trim() + "-");
    int imon = Integer.parseInt(txtMonth.getText().trim());
    int iday = Integer.parseInt(txtDay.getText().trim());
    int ihour = Integer.parseInt(txtHour.getText().trim());
    int imin = Integer.parseInt(txtMin.getText().trim());
    int isec = Integer.parseInt(txtSecond.getText().trim());
    if(imon < 10) finalDate.append("0" + String.valueOf(imon));
      else finalDate.append( String.valueOf(imon));
    if(iday < 10) finalDate.append("-0" + String.valueOf(iday));
      else finalDate.append("-" + String.valueOf(iday));
    if(ihour < 10) finalDate.append(" 0" + String.valueOf(ihour));
      else finalDate.append(" " + String.valueOf(ihour));
    if(imin < 10) finalDate.append(":0" + String.valueOf(imin));
      else finalDate.append(":" + String.valueOf(imin));
    if(isec < 10) finalDate.append(":0" + String.valueOf(isec));
      else finalDate.append(":" + String.valueOf(isec));
    setNextDate(finalDate.toString());
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//    try{
//      Date sysDate = sdf.parse(strSysDate);
//      Date userDate = sdf.parse(finalDate.toString());
//      if((userDate.getTime() - sysDate.getTime())/60000 < 1){
//        MessageBox.msgBox(this,"您输入的时间可能小于数据库系统时间，任务可能将无法执行！",0);
//        return;
//      }
      this.dispose();
//    }catch(Exception e){
//      e.printStackTrace();
//    }
  }

  /** Add your post-init code in here 	*/
  public void postInitGUI() {
    String curTime = date;
    String year = curTime.substring(0,4);
    String month = curTime.substring(5,7);
    String day = curTime.substring(8,10);
    int index = curTime.lastIndexOf(":");
    String second = curTime.substring(index + 1,index + 3);
    String min = curTime.substring(index-2,index);
    String hour = curTime.substring(index-5,index-3);
    txtDay.setText(day);
    txtHour.setText(hour);
    txtMin.setText(min);
    txtMonth.setText(month);
    txtSecond.setText(second);
    txtYear.setText(year);
  }

  protected void btnCloseActionPerformed(ActionEvent evt) {
    this.dispose();
  }

}