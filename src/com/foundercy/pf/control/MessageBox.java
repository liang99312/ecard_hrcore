package com.foundercy.pf.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.foundercy.pf.util.Resource;
/** 公用消息提示组件
 * 	使用示例:
 * 			MessageBox msg=new MessageBox("测试消息","测试帮助",3,1|2);
 *  		msg.setVisible(true);		
 * 	构造函数说明:
 * 				MessageBox(String message,String helpMessager,int messagerBoxType,int buttonTpye|buttonType);
 * 				message:提示消息内容
 * 				helpMessager:帮助消息内容
 * 				messagerBoxType:messageBox类型 
 *                          值:|  0  |  1  |  2  |  3
 * 						   类型:| 警告 | 错误 | 消息 | 确认
 * 				buttonTpye:按钮类型
 * 			  	              值:|  1  |  2  |
 * 						   类型:| 确定 | 取消 |
 * 				MessageBox("测试消息","测试帮助",1,1|2) 确定按钮和取消按钮
 * 				MessageBox("测试消息","测试帮助",1,1)   只有确定按钮
 * 				MessageBox("测试消息","测试帮助",1,2)   只有取消按钮
 */
public class MessageBox extends JDialog 
{
	public static final long serialVersionUID = -1L;
    public final static int WARNING = 0;
    public final static int ERROR = 1;
    public final static int MESSAGE = 2;
    public final static int INFOMATION=3;

    public final static int BUTTON_OK = 1;
    public final static int BUTTON_CANCEL = 2;

    public final static int OK = 1;
    public final static int CANCEL = 2;

    private String TITLE_WARNING = "警告";
    private String TITLE_ERROR = "错误";
    private String TITLE_MESSAGE = "消息";
    private String TITLE_INFOMATION = "确认";

    private JPanel jUpPanel = new JPanel();
    private JPanel jDownPanel = null;
    // message
    private JPanel jAreaMsg = new JPanel();
    // option
    private JPanel jAreaOption = new JPanel();
    private FButton jButtonHelp = null;
    // icon
    private JPanel jAreaIcon = new JPanel();
    private ImageIcon icon = null;
    // help
    private JTextArea jHelp; // = new JTextArea("这是帮助文件");
    private boolean isExpand = false;
    private boolean hasHelp = false;
    // input info
    private String msg;
    private String help;
    private int buttonType;

    private int height;
    public int result = MessageBox.CANCEL;

    public static void msgBox(Object parentComponent, String message, int messageType) {
        //处理message显示的换行问题。按照段落格式显示。
        char[] p = message.toCharArray();
        String pmessage = "        ";
        for (int i = 0; i < p.length; i++) {
            pmessage += p[i];
            if ((i + 1) % 30 == 0)
                pmessage += "\n";
        }
        if (messageType == 0) {
            (new MessageBox(message, MessageBox.WARNING, null)).setVisible(true);
        } else if (messageType == 1) {
            (new MessageBox(message, MessageBox.ERROR, null)).setVisible(true);
        } else {
            (new MessageBox(message, MessageBox.MESSAGE, null)).setVisible(true);
        }
    }

    public MessageBox(String msg) throws HeadlessException {
    	super((JFrame)null, true);
        construct(msg, null, MESSAGE, BUTTON_OK);
    }
    
    public MessageBox(JFrame owner, String msg) throws HeadlessException {
        super(owner, true);
    	construct(msg, null, MESSAGE, BUTTON_OK);
    }

    public MessageBox(String msg, int type) throws HeadlessException {
    	super((JFrame)null, true);
        construct(msg, null, type, BUTTON_OK);
    }
    
    public MessageBox(JFrame owner, String msg, int type) throws HeadlessException {
        super(owner, true);
    	construct(msg, null, type, BUTTON_OK);
    }

    public MessageBox(JDialog owner, String msg, int type) throws HeadlessException {
        super(owner, true);
    	construct(msg, null, type, BUTTON_OK);
    }
    
    public MessageBox(String msg, int type, int buttonType) throws HeadlessException {
    	super((JFrame)null, true);
        construct(msg, null, type, buttonType);
    }
    
    public MessageBox(JFrame owner, String msg, int type, int buttonType) throws HeadlessException {
        super(owner, true);
    	construct(msg, null, type, buttonType);
    }
    
    public MessageBox(JDialog owner,String msg,int type,int buttonType)throws HeadlessException {
    	super(owner,true);
    	construct(msg, null, type, buttonType);
    }
    public MessageBox(String msg, int type, String help) throws HeadlessException {
    	super((JFrame)null, true);
        construct(msg, help, type, BUTTON_OK);
    }
    
    public MessageBox(JFrame owner,String msg, int type, String help) throws HeadlessException {
        super(owner, true);
    	construct(msg, help, type, BUTTON_OK);
    }

    public MessageBox(JDialog owner,String msg, int type, String help) throws HeadlessException {
        super(owner, true);
    	construct(msg, help, type, BUTTON_OK);
    }
    
    public MessageBox(String msg, String help, int type, int buttonType) throws HeadlessException {
    	super((JFrame)null, true);
        construct(msg, help, type, buttonType);
    }    
    
    public MessageBox(JFrame owner, String msg, String help, int type, int buttonType) throws HeadlessException {
        super(owner, true);
    	construct(msg, help, type, buttonType);
    }

    public MessageBox(JDialog owner, String msg, String help, int type, int buttonType) throws HeadlessException {
        super(owner, true);
    	construct(msg, help, type, buttonType);
    }
    
    public int getResult() {
        return this.result;
    }

    private void construct(String msg, String help, int type, int buttonType) throws HeadlessException {
        try {
            setTitle(type);
            this.msg = msg;
            this.buttonType = buttonType;
            if (help != null && help.length() > 0) {
                hasHelp = true;
                this.help = help;
            }
            jbInit();
            pack();
            height = getHeight();
            setPositonInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setPositonInfo() {
        this.setResizable(false);
        this.setModal(true);
        Dimension size = new Dimension(340, height);
        Dimension parentSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((parentSize.width - size.width) / 2, (parentSize.height - size.height) / 3);
        this.setSize(size);
    }

    private void setTitle(int type) {
        String title = null;
        switch (type) {
        case WARNING:
            title = TITLE_WARNING;
            icon = Resource.getImage("warning.gif");
            break;
        case ERROR:
            title = TITLE_ERROR;
            icon = Resource.getImage("error.gif");
            break;
        case INFOMATION:
        	title = TITLE_INFOMATION;
        	icon = Resource.getImage("information.gif");
        	break;
        case MESSAGE:
        default:
            title = TITLE_MESSAGE;
            icon = Resource.getImage("tips.gif");
            break;
        }
        this.setTitle(title);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(new BorderLayout());
        initUpArea();
        initDownArea();
        this.getContentPane().add(jUpPanel, BorderLayout.NORTH);
    }

    private void initUpArea() throws Exception {
        jUpPanel.setLayout(new BorderLayout());
        jUpPanel.add(jAreaOption, BorderLayout.SOUTH);
        jUpPanel.add(jAreaIcon, BorderLayout.WEST);
        jUpPanel.add(jAreaMsg, BorderLayout.CENTER);
        initOption();
        initIcon();
        initMessage();
    }

    private void initOption() throws Exception {
        if ((buttonType & BUTTON_OK) != 0) {
            FButton jButtonOK = new FButton();
//          设置快捷键 add by chenli 2007-12-4
            jButtonOK.setText("确定(O)");            
            jButtonOK.setMnemonic(KeyEvent.VK_O);
            
            //jButtonOK.setIcon("ok.gif");
            jButtonOK.setFont(new java.awt.Font("DialogInput", 0, 12));
//            jButtonOK.setMargin(new Insets(0,0,0,0));
            jButtonOK.setPreferredSize(new Dimension(80,22));
            jButtonOK.addActionListener(new MessageBox_jButtonOK_actionAdapter(this));
            jButtonOK.registerKeyboardAction(new RefActionListener(this)
            		{
            	public void actionPerformed(ActionEvent e)
            	{
            		actionPerformedOk();
            	}
            		},KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false),JComponent.WHEN_IN_FOCUSED_WINDOW);
            jAreaOption.add(jButtonOK);
        }
        if ((buttonType & BUTTON_CANCEL) != 0) {
            FButton jButtonCancel = new FButton();
            //设置快捷键 add by chenli 2007-12-4
            jButtonCancel.setText("取消(C)");
            jButtonCancel.setMnemonic(KeyEvent.VK_C);
            
            jButtonCancel.setPreferredSize(new Dimension(80,22));
            //jButtonCancel.setIcon("uncancel.gif");
            //jButtonCancel.setFont(new java.awt.Font("DialogInput", 0, 22));
//            jButtonCancel.setMargin(new Insets(0,0,0,0));
            jButtonCancel.addActionListener(new MessageBox_jButtonCancel_actionAdapter(this));
            jButtonCancel.registerKeyboardAction(new RefActionListener(this)
            		{
            	public void actionPerformed(ActionEvent e)
            	{
            		actionPerformedCancle();
            	}
            		},KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false),JComponent.WHEN_IN_FOCUSED_WINDOW);
            jAreaOption.add(jButtonCancel);
        }

        if (help != null && help.length() > 0) {
            jButtonHelp = new FButton();
            jButtonHelp.setPreferredSize(new Dimension(110,22));
            jButtonHelp.setFont(new java.awt.Font("DialogInput", 0, 12));
//            jButtonHelp.setMargin(new Insets(0,0,0,0));
            jButtonHelp.addActionListener(new MessageBox_jButtonHelp_actionAdapter(this));
            //添加快捷键 add　by chenli 2007-12-4
            jButtonHelp.setText("详细信息(H)");
            jButtonHelp.setMnemonic(KeyEvent.VK_H);
            
            //jButtonHelp.setIcon("help2.gif");
            jAreaOption.add(jButtonHelp);
        }
    }

    private void initIcon() throws Exception {
        jAreaIcon.setLayout(new BoxLayout(jAreaIcon, BoxLayout.X_AXIS));
        jAreaIcon.add(Box.createHorizontalStrut(10));
        JLabel jLabelIcon = new JLabel(null, icon, JLabel.CENTER);
        jAreaIcon.add(jLabelIcon);
        jAreaIcon.add(Box.createHorizontalStrut(10));
    }

    private void initMessage() throws Exception {
        jAreaMsg.setLayout(new BoxLayout(jAreaMsg, BoxLayout.Y_AXIS));
        jAreaMsg.add(Box.createVerticalStrut(30));
        // 分行
        final int maxLength = 300;
        msg = "   " + msg;
        String line = msg;
        while (line.length() > 0) {
            String show = null;
            show = subStringLength(line, maxLength);
            JLabel jMsg = new JLabel(" " + show);
            jMsg.setFont(new java.awt.Font("DialogInput", 0, 12));
            jAreaMsg.add(jMsg);
            line = line.substring(show.length());
        }
        jAreaMsg.add(Box.createVerticalStrut(30));
    }

    boolean charInString(char ch, String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }

    private String subStringLength(String str, int maxWidth) {
        int length = 0;
        int width = 0;
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" + "~!@#$%^&*()-_+=|\\{}[]:;\"'<>,.?/";
        String noPre = "，,.。？?;；:：!！";
        for (int i = 0; i < str.length(); i++) {
            length++;
            if (charInString(str.charAt(i), letter)) {
                width += 8;
            } else {
                width += 15;
            }
            if (width >= maxWidth && i+1<str.length()) 
            {
                if (charInString(str.charAt(i + 1), noPre)) {
                    length++;
                }
                break;
            }
        }
        return str.substring(0, length);
    }

    private void initDownArea() throws Exception {
        if (hasHelp) {
            jDownPanel = new JPanel();
            jDownPanel.setLayout(new BoxLayout(jDownPanel, BoxLayout.Y_AXIS));
            jHelp = new JTextArea(help);
            JScrollPane jScrollPane = new JScrollPane(jHelp);
            jHelp.setEditable(false);
            jDownPanel.add(Box.createVerticalStrut(10));
            jDownPanel.add(jScrollPane);
        }
    }

    public void expand() {
        final int expandHeight = 100;
        if (isExpand) {
            this.getContentPane().remove(jDownPanel);
            //添加快捷键 add by chenli 2007-12-4  start
            this.jButtonHelp.setText("详细信息(H)");
            this.jButtonHelp.setMnemonic(KeyEvent.VK_H);
//          添加快捷键 add by chenli 2007-12-4  end
            //this.jButtonHelp.setIcon("help2.gif");
            this.setSize(this.getWidth(), height);
        } else {
            this.getContentPane().add(jDownPanel, BorderLayout.CENTER);
//          添加快捷键 add by chenli 2007-12-4  start
            this.jButtonHelp.setText("隐藏信息(H)");
            this.jButtonHelp.setMnemonic(KeyEvent.VK_H);
//          添加快捷键 add by chenli 2007-12-4  end
            //this.jButtonHelp.setIcon("help1.gif");
            this.setSize(this.getWidth(), height + expandHeight);
        }
        isExpand = !isExpand;
        this.validate();
        this.repaint();
    }

    void jButtonOK_actionPerformed(ActionEvent e) {
        this.result = MessageBox.OK;
        this.dispose();
    }

    void jButtonHelp_actionPerformed(ActionEvent e) {
        expand();
    }

    void jButtonCancel_actionPerformed(ActionEvent e) {
        this.result = MessageBox.CANCEL;
        this.dispose();
    }
    public void actionPerformedOk()
    {
    	 this.result = MessageBox.OK;
         this.dispose();
    }
    public void actionPerformedCancle()
    {
    	this.result = MessageBox.CANCEL;
        this.dispose();
    }    
   
    public static void main(String[] args) {
        MessageBox msg = new MessageBox("测试!", "帮助内容",3,3);
        msg.setVisible(true);
        System.exit(0);
    }
    
//    public static void main(String[] args) {
//        MessageBox msg = new MessageBox("当前选择的数据中存在未下!", "nihao大家你好我们都好大家你好我们都\n好大家你好我们都好大家你好我们都好", MessageBox.WARNING,
//                MessageBox.BUTTON_OK | MessageBox.BUTTON_CANCEL);
//        msg.setVisible(true);
//        System.exit(0);
//    }
}

class MessageBox_jButtonOK_actionAdapter implements java.awt.event.ActionListener {
    MessageBox adaptee;

    MessageBox_jButtonOK_actionAdapter(MessageBox adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonOK_actionPerformed(e);
    }
}

class MessageBox_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
    MessageBox adaptee;

    MessageBox_jButtonCancel_actionAdapter(MessageBox adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonCancel_actionPerformed(e);
    }
}

class MessageBox_jButtonHelp_actionAdapter implements java.awt.event.ActionListener {
    MessageBox adaptee;

    MessageBox_jButtonHelp_actionAdapter(MessageBox adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonHelp_actionPerformed(e);
    }
}