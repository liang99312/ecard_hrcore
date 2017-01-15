package com.foundercy.pf.control.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.foundercy.pf.control.AbstractDataField;
import com.foundercy.pf.control.DataField;
import com.foundercy.pf.control.listener.IPickColumnLocalListener;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.util.PinYinMa;

/**
 * <p>
 * Title: FBaseTable
 * <p>
 * Description: ���ؼ�
 * <p>
 * &nbsp;&nbsp;&nbsp;FBaseTable��һ�����������һ�����һ����������������չ�Ŀؼ����ÿؼ�֧��#TableModel����ģ��
 * <p>
 * &nbsp;&nbsp;&nbsp;��ߵı��������������������Ĺ����������������в�����ק���෴�ұߵĻ��������������Ĺ���������
 * <p>
 * &nbsp;&nbsp;&nbsp;������ͷ������ק�����ұ��ʹ����ͬ��ѡ��ģ��ListSelectionModel���Կ���ͬʱѡ�У�FBaseTable
 * <p>
 * &nbsp;&nbsp;&nbsp;�߱����¹���:
 * <p>
 * &nbsp;&nbsp;&nbsp;1�������й��ܣ����Ե���FBaseTable.lockColumn(int column)����ָ����֮ǰ�������С�
 * <p>
 * &nbsp;&nbsp;&nbsp;2���ϼ��У������ܼơ�С�ƣ�����ʾ�Ĺ��ܣ�����С�����Զ����ݱ������ݽ��м���ģ�ͬʱ���Ե����û���
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ڽ��м���:
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;FBaseTableColumn.setSumRowCount(ISumRowCount)
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;�����ܼ�������ͨ�����������ý�ȥ�ģ�
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;FBaseTableColumn.setSumRowData(Number)
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;С�ƹ��ܿ���ͨ��setShowSumRow(boolean)��������
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;�ܼƹ��ܿ���ͨ��setShowSumRowAll(boolean)��������
 * <p>
 * &nbsp;&nbsp;&nbsp;3��˫����ͷ��������Ŀǰ��û��֧�ְ��պ���ƴ������Ҳ��֧�ְ�����ʾֵ��������,�����ܿ���ͨ��
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp; setSortable(boolean)�������Ρ�
 * <p>
 * &nbsp;&nbsp;&nbsp;4����Ԫ����ק���ƹ��ܣ���Excel���ܺ����ơ�
 * <p>
 * &nbsp;&nbsp;&nbsp;5��ͨ��addColumn(FBaseTableColumn)�������ӱ����
 * <p>
 * &nbsp;&nbsp;&nbsp;6���к�֧��,����ͨ��setShowRowNumber(boolean)�������Ρ�
 * <p>
 * &nbsp;&nbsp;&nbsp;7���Ե�Ԫ����в������ܣ��磺setValueAt(Object value��int rowIndex, String
 * columnId)
 * <p>
 * &nbsp;&nbsp;&nbsp;8�������У�����ͨ��setColumnVisible()����ָ���е���ʾ����
 * <p>
 * &nbsp;&nbsp;&nbsp;9���������õ�Ԫ��ѡ��ģʽ����ѡ��ģʽ��ȱʡ��ѡ��ģʽ
 * <p>
 * &nbsp;&nbsp;&nbsp;10������setIsCheckboxAffectedByClickRow()��������ѡ���еĸ�ѡ���Ƿ������е�����������ı�״̬��
 * <p>
 * &nbsp;&nbsp;&nbsp;11��������֧�ֱ༭״̬������ĳһ���Ƿ���Ա༭�����Ե���
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;FBaseTableColumn.setEditable(boolean),
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;�ڱ༭״̬��(�ؼ��Ѿ���ʾ�ڽ�����)
 * <p>
 * Copyright: ������Ԫ�Ƽ���չ���޹�˾(c) 2000~2007
 * <p>
 * Company: ������Ԫ�Ƽ���չ���޹�˾
 * 
 * @see com.foundercy.pf.control.table.FBaseTableColumn
 * @see com.foundercy.pf.control.table.FBaseTableCellRenderer
 * @see com.foundercy.pf.control.table.FBaseTableCellEditor
 * @see com.foundercy.pf.control.table.FBaseTableColumnModel
 * @see com.foundercy.pf.control.table.FBaseTableSorter
 * @see com.foundercy.pf.control.table.FBaseTableRowNoModel
 * @since jdk1.4.2
 * @author fangyi
 * @author jerry
 */
public class FBaseTable extends JPanel {

    /**
     * ���л��汾ID
     */
    private static final long serialVersionUID = 5911944755350293276L;
    /**
     * �к��е����ƣ��̶�ֵΪ"Row_No"��
     */
    public static final String ROWNUMBER_COLUMN_NAME = "Row_No";
    /**
     * �кŵĿ�ȣ��̶�ֵΪ40�����ء�
     */
    public static final int ROWNUMBER_COLUMN_WIDTH = 40;
    /**
     * �Ƿ����ǵ�һ�μ���
     */
    private static boolean isFirstLoad = true;
    /**
     * �Ƿ���ʾ�ϼ��У�С�ƣ�
     */
    private boolean showSumRow = false;
    /**
     * �Ƿ���ʾ�ϼ��У��ܼƣ�
     *
     * @author jerry
     */
    private boolean showSumRowAll = false;
    /**
     * С�����Ƿ������������ݶ����������ture, ���ڶ����������false�����ڵײ�
     */
    private boolean sumRowAtTop = true;
    /**
     * С������ϼ����Ƿ������������ݶ����������ture, ���ڶ����������false�����ڵײ�.
     */
    private boolean sumRowAllAtTop = true;
    /**
     * С������ϼ��е�λ�ù�ϵ��trueΪ�ϼ���С��֮�ϣ�falseΪС���ںϼ�֮��
     */
    private boolean isAllTopPart = true;
    /**
     * ��ѡ���Ƿ����ŵ���У�����ѡ�л��߲�ѡ�С�
     */
    private boolean isCheckBoxAffectedByClickRow = true;
    /**
     * �Ƿ���ʾ�кŵı�־
     */
    private boolean showRowNumber = true;
    /**
     * �Ƿ�߱�������
     */
    private boolean sortable = true;
    /**
     * �Ƿ���ݾ�����ģ����ָ�����У��Զ����������ʾ����
     */
    private boolean autoCreateColumnsFromModel = false;
    /**
     * �Ƿ�����������
     */
    protected boolean ascending = true;
    
    private int f_row_index = 0;//��������ǰ���У�˫����ͷ������
    
    public void setF_row_index(int index){
        this.f_row_index = index;
    }
    /**
     * �Ƿ���ʾѡ�����ϵĸ�ѡ��
     */
    boolean isShowHeaderCheckBox = true;
    /**
     * ���ı߿���ʱ���ù������ı߿��������ÿ��ܵ��·���л�ʱUI�����ʱ����
     */
    private Border border = null;//new LineBorder(Color.red);//new JScrollPane().getBorder();
    /**
     * ����������Ĺ�����
     */
    private JScrollPane lScrollPane = new JScrollPane();
    /**
     * �ұ߻���Ĺ�����
     */
    private JScrollPane rScrollPane = new JScrollPane();
    /**
     * ��ѡ�������
     */
    private List<ListSelectionListener> listeners = new ArrayList();

    // ���ڿ�������Ԫ����ɫ
    public Color getCellBackgroud(String fileName, Object cellValue, Object row_obj) {
        return null;
    }

    // ���ڿ�������Ԫ�������ɫ
    public Color getCellForegroud(String fileName, Object cellValue, Object row_obj) {
        return null;
    }

    // ���ڿ�������Ԫ����
    public Font getCellFont(String fileName, Object cellValue, Object row_obj) {
        return null;
    }

    // ���ڿ�������Ԫǰ׺
    public String getCellPrefix(String fileName, Object cellValue, Object row_obj) {
        return null;
    }
    /**
     * ��߱������ı�������ı�����϶���
     */
    private FExpandTable lockedTable = new FExpandTable(this) {

        @Override
        public String getToolTipText(MouseEvent event) {
            String tip = null;
            java.awt.Point p = event.getPoint();
            int rowIndex = rowAtPoint(p);
            int colIndex = columnAtPoint(p);
            int realColumnIndex = convertColumnIndexToModel(colIndex);
            TableModel model = getModel();
            Object obj = model.getValueAt(rowIndex, realColumnIndex);
            if (obj != null) {
                tip = obj.toString();
            }
            return tip;
        }

        public void paint(Graphics g) {
            super.paint(g);

            Graphics2D g2 = (Graphics2D) g;
            Stroke s = g2.getStroke();
            Color c = g2.getColor();

            g2.setStroke(new BasicStroke(2));
            if (lockedTable.getColumnCount() == 1) {
                g2.setColor(this.getGridColor());
            }

            g.drawLine(lockedTable.getWidth(), 0, lockedTable.getWidth(), lockedTable.getHeight());
            g2.setStroke(s);
            g2.setColor(c);
        }
        /*
        public TableCellRenderer getCellRenderer(int row, int col){            
        TableCellRenderer tcr = super.getCellRenderer(row, col);
        
        return tcr;
        }
         */
    };

    public FExpandTable getLockedTable() {
        return lockedTable;
    }
    /**
     * �ұ߻�ı�񣬻�����������������ģ������п����϶����ı�λ��
     */
    protected FExpandTable activeTable = new FExpandTable(this) {

        @Override
        public String getToolTipText(MouseEvent event) {
            String tip = null;
            java.awt.Point p = event.getPoint();
            int rowIndex = rowAtPoint(p);
            int colIndex = columnAtPoint(p);
            int realColumnIndex = convertColumnIndexToModel(colIndex);
            TableModel model = getModel();
            Object obj = model.getValueAt(rowIndex, realColumnIndex);
            if (obj != null) {
                tip = obj.toString();
            }
            return tip;
        }

        public void paint(Graphics g) {
            super.paint(g);

            Graphics2D g2 = (Graphics2D) g;
            Stroke s = g2.getStroke();
            Color c = g2.getColor();

            g2.setStroke(new BasicStroke(2));
            g2.setColor(this.getGridColor());

            g.drawLine(activeTable.getWidth(), 0, activeTable.getWidth(), activeTable.getHeight());
            g2.setStroke(s);
            g2.setColor(c);
        }
    };
    /**
     * ��ߵ�Panel
     */
    private JPanel leftPanel = new JPanel();
    /**
     * ���½ǵ�Panel���������ʱ�����½�һ���ɫ������
     */
    private JPanel leftBottomPanel = new JPanel();
    //lindx add 2008-06-10 ���Ԫ�����Զ���
    private FBaseTableCellAttribute cellAttribute;
    /**
     * �Զ������ģ��
     *
     * @see FBaseTableColumnModel
     */
    private FBaseTableColumnModel columModel;
    protected String enterTo = "Right";
    // ѡ����ͷ����
    protected int cur_column = -1;
    private boolean auto_insert = true;
    private JPanel pnlStat, southPanel;
    private LocalColumnPanel localColumnPanel;//��λ�в�ѯ��
    private List<String> fieldHeaders = new ArrayList<String>();//���ڶ�λ�е��ֶ���Ϣ
    private List<Integer> fieldColumnList = new ArrayList<Integer>();//��λ��:�ֶ���Ϣ��ѯ���Ľ������
    private String local_field_name = "";
    private int local_index = 0;

    public JPanel getPnlStat() {
        return pnlStat;
    }

    public boolean isAuto_insert() {
        return auto_insert;
    }

    public void setAuto_insert(boolean auto_insert) {
        this.auto_insert = auto_insert;
    }

    public Action getMyEnterAction() {
        return myEnterAction;
    }
    private Action myEnterAction = new AbstractAction() {

        public void actionPerformed(ActionEvent e) {
            int rowCount = activeTable.getRowCount();
            int colCount = activeTable.getColumnCount();
            if ((rowCount <= 0) || (colCount <= 0)) {
                return;
            }
            int row = activeTable.getSelectedRow();
            int col = activeTable.getSelectedColumn();
            activeTable.requestFocus();
            FTable tab = (FTable) FBaseTable.this;
            FTableModel model = (FTableModel) tab.getModel();
            if ((row < 0) || (col < 0)) {
                // if not in editing, start edit the selected row.
                row = activeTable.getSelectedRow();
                col = activeTable.getSelectedColumn();
                if ((row < 0) || (col < 0)) {
                    row = col = 0;
                }
            } else {
                // edit the "next" cell
                if (enterTo.equals("Right")) {
                    if (col + 1 < colCount) {
                        col++;
                    } else if (row + 1 < rowCount) {
                        row++;
                        col = 0;
                    } else {
                        row = col = 0;
                    }
                } else {
                    if (row + 1 < rowCount) {
                        row++;
                    } else if (col + 1 < colCount) {
                        col++;
                        row = 0;
                    } else {
                        row = col = 0;
                    }
                }
            }
            if (tab.isEditable()) {
                if (enterTo.equals("Right")) {
                    while (!model.isCellEditable2(row, col + lockedTable.getColumnCount() - 1)) {
                        if ((row < 0) || (col < 0)) {
                            // if not in editing, start edit the selected row.
                            row = activeTable.getSelectedRow();
                            col = activeTable.getSelectedColumn();
                            if ((row < 0) || (col < 0)) {
                                row = col = 0;
                            }
                        } else {
                            // edit the "next" cell
                            if (col + 1 < colCount) {
                                col++;
                            } else if (row + 1 < rowCount) {
                                row++;
                                col = 0;
                            } else {
                                row = col = 0;
                            }
                        }
                    }
                } else {
                    while (!model.isCellEditable2(row, col + lockedTable.getColumnCount() - 1)) {
                        if ((row < 0) || (col < 0)) {
                            // if not in editing, start edit the selected row.
                            row = activeTable.getSelectedRow();
                            col = activeTable.getSelectedColumn();
                            if ((row < 0) || (col < 0)) {
                                row = col = 0;
                            }
                        } else {
                            // edit the "next" cell
                            if (row + 1 < rowCount) {
                                row++;
                            } else if (col + 1 < colCount) {
                                col++;
                                row = 0;
                            } else {
                                row = col = 0;
                            }
                        }
                    }
                }
            }
            if (tab.isEditable()) {
                activeTable.editCellAt(row, col);
                Component c = activeTable.getEditorComponent();
                KeyStroke enter_key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
                if (c instanceof JTextField) {
                    ((JTextField) activeTable.getEditorComponent()).selectAll();
                } else if (c instanceof JPanel) {
                    JPanel pnl = (JPanel) c;
                    Component[] cs = pnl.getComponents();
                    for (Component cm : cs) {
                        if (cm instanceof JTextField) {
                            JTextField tf = (JTextField) cm;
                            tf.selectAll();
                            tf.getInputMap().put(enter_key, "ENTER");
                            tf.getActionMap().put("ENTER", myEnterAction);
                            tf.requestFocus();
                            break;
                        }
                    }
                } else if (c instanceof JComboBox) {
                    JComboBox cb = (JComboBox) c;
                    if (cb.isEnabled()) {
                        cb.getInputMap().put(enter_key, "ENTER");
                        cb.getActionMap().put("ENTER", myEnterAction);
                        cb.requestFocus();
                    }
                }
            }
            activeTable.setColumnSelectionInterval(col, col);
            activeTable.setRowSelectionInterval(row, row);
            int left = 0;
            for (int i = 0; i < col; i++) {
                left = left + activeTable.getColumnModel().getColumn(i).getWidth();
            }
            rScrollPane.getHorizontalScrollBar().setValue(left - rScrollPane.getViewport().getWidth() + activeTable.getColumnModel().getColumn(col).getWidth());
            if (activeTable.getRowHeight() * (row + 1) - rScrollPane.getViewport().getHeight() - rScrollPane.getVerticalScrollBar().getValue() > 0) {
                rScrollPane.getVerticalScrollBar().setValue(activeTable.getRowHeight() * (row + 1) - rScrollPane.getViewport().getHeight());
            }
        }
    };

    /**
     * ��λ��ĳһ��
     *
     * @param field_name
     *            ѡ������
     */
    public void LocalColumnFrames(String field_name) {
        if (field_name == null || "".equals(field_name.replace(" ", ""))) {
            return;
        } else {
            if (local_field_name.equalsIgnoreCase(field_name)) {
                this.LocalColumn(getIndexFromFieldColumnLis());
                return;
            }
            fieldColumnList.clear();
            local_field_name = field_name;
            field_name = field_name.toUpperCase();
            String field_pym = PinYinMa.ctoE(field_name);
            for (int i = 0; i < fieldHeaders.size(); i++) {
                String str = fieldHeaders.get(i);
                String[] strs = str.split(";");
                String f_caption = strs[0].toUpperCase();
                String f_name = strs[1].toUpperCase();
                String f_pym = PinYinMa.ctoE(f_caption).toUpperCase();
                if (f_caption.contains(field_name)) {
                    fieldColumnList.add(i);
                } else if (f_name.contains(field_name)) {
                    fieldColumnList.add(i);
                } else if (f_pym.contains(field_pym)) {
                    fieldColumnList.add(i);
                }
            }
        }
        local_index = -1;
        this.LocalColumn(getIndexFromFieldColumnLis());
    }

    private int getIndexFromFieldColumnLis() {
        int index = -1;
        if (fieldColumnList.isEmpty()) {
            return index;
        }
        local_index++;
        if (local_index == fieldColumnList.size() || local_index > fieldColumnList.size()) {
            local_index = 0;
        }
        index = fieldColumnList.get(local_index) == null ? -1 : fieldColumnList.get(local_index);
        return index;
    }

    /**
     * ����index��λ����ڼ��У���������֮����
     */
    public void LocalColumn(int index) {
        if (index < 0) {
            return;
        }
        if (index >= activeTable.getColumnCount()) {
            return;
        }
        activeTable.setColumnSelectionInterval(index, index);
        int left = 0;
        for (int i = 0; i < index; i++) {
            left = left + activeTable.getColumnModel().getColumn(i).getWidth();
        }
        rScrollPane.getHorizontalScrollBar().setValue(left - rScrollPane.getViewport().getWidth() + activeTable.getColumnModel().getColumn(index).getWidth());
        activeTable.repaint();
    }

    /**
     * ����ȱʡģ�͹���
     */
    public FBaseTable() {
        this(new DefaultTableModel());
    }

    /**
     * ����һ��ָ����ģ�͹���
     */
    public FBaseTable(TableModel model) {
        this.init();
        this.setModel(model);
    }

    public void enterToDown() {
        ConfigManager.getConfigManager().setProperty("table.enterTo", "Down");
        ConfigManager.getConfigManager().save2();
        enterTo = "Down";
    }

    public void enterToRight() {
        ConfigManager.getConfigManager().setProperty("table.enterTo", "Right");
        ConfigManager.getConfigManager().save2();
        enterTo = "Right";
    }

    /**
     * ��ʼ�����
     */
    private void init() {
        activeTable.setCellSelectionEnabled(true);

        enterTo = ConfigManager.getConfigManager().getProperty("table.enterTo");
        KeyStroke[] enter_keys = activeTable.getRegisteredKeyStrokes();
        for (KeyStroke key : enter_keys) {
            if (key.getKeyCode() == 10 && !key.toString().contains("shift")) {
                activeTable.registerKeyboardAction(myEnterAction, key, 1);
                break;
            }
        }

//        KeyStroke enter_key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
//        enterTo = ConfigManager.getConfigManager().getProperty("table.enterTo");
//        activeTable.getInputMap().put(enter_key, "ENTER");
//        activeTable.getActionMap().put("ENTER", myEnterAction);

        activeTable.getTableHeader().addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if ((e.getX() - rScrollPane.getHorizontalScrollBar().getValue()) < 100) {
                    rScrollPane.getHorizontalScrollBar().setValue(Math.max(0, rScrollPane.getHorizontalScrollBar().getValue() - 100));
                } else if ((rScrollPane.getHorizontalScrollBar().getValue() + rScrollPane.getViewport().getWidth() - e.getX()) < 100) {
                    rScrollPane.getHorizontalScrollBar().setValue(Math.max(0, rScrollPane.getHorizontalScrollBar().getValue() + 50));
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        //enter_key = KeyStroke.getKeyStroke(KeyEvent.VK_F3, /*Event.CTRL_MASK*/0, false);
        //activeTable.getInputMap().put(enter_key, "APPEND");
//        activeTable.getActionMap().put("APPEND", new AbstractAction() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                FTable tab = (FTable) FBaseTable.this;
//                if (!tab.isEditable() || !auto_insert) return;
//                FTableModel model = (FTableModel) tab.getModel();
//                model.addBlankRow();
//                int rowCount = activeTable.getRowCount();
//            int colCount = activeTable.getColumnCount();
//                int row = model.getObjects().size() - 1;
//                int col = 0;
//                while (!model.isCellEditable2(row, col)) {
//                        if ((row < 0) || (col < 0)) {
//                            // if not in editing, start edit the selected row.
//                            row = activeTable.getSelectedRow();
//                            col = activeTable.getSelectedColumn();
//                            if ((row < 0) || (col < 0)) {
//                                row = col = 0;
//                            }
//                        } else {
//                            // edit the "next" cell
//                            if (col + 1 < colCount) {
//                                col++;
//                            } else if (row + 1 < rowCount) {
//                                row++;
//                                col = 0;
//                            } else {
//                                row = col = 0;
//                            }
//                        }
//                    }
//                activeTable.setRowSelectionInterval(row, row);
//            activeTable.setColumnSelectionInterval(col, col);
//            if (tab.isEditable()) {
//                activeTable.editCellAt(row, col);
//                if (activeTable.getEditorComponent() instanceof JTextField) {
//                    ((JTextField) activeTable.getEditorComponent()).selectAll();
//                }
//                ;
//            }
//
//            int left = 0;
//            for (int i = 0; i < col; i++) {
//                left = left + activeTable.getColumnModel().getColumn(i).getWidth();
//            }
//
//            rScrollPane.getHorizontalScrollBar().setValue(left - rScrollPane.getViewport().getWidth() + activeTable.getColumnModel().getColumn(col).getWidth());
//            rScrollPane.getVerticalScrollBar().setValue(activeTable.getRowHeight() * (row + 1) - rScrollPane.getViewport().getHeight());
//            }
//        });

//        enter_key = KeyStroke.getKeyStroke(KeyEvent.VK_F5, /*Event.CTRL_MASK*/0, false);
//        activeTable.getInputMap().put(enter_key, "DELETE");
//        activeTable.getActionMap().put("DELETE", new AbstractAction() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                FTable tab = (FTable) FBaseTable.this;
//                if (!tab.isEditable() || !auto_insert) return;
//                FTableModel model = (FTableModel) tab.getModel();
//                int ind = tab.getSelectedRowModelIndex();
//                if (ind >= 0 && ind < model.getObjects().size())
//                    model.getObjects().remove(ind);
//                model.fireTableDataChanged();
//                ind = Math.min(ind, model.getObjects().size() - 1);
//                ind = Math.max(ind, -1);
//                if (ind >= 0 && ind < model.getObjects().size())
//                    tab.setRowSelectionInterval(ind, ind);
//            }
//        });
        /*
        if ("Down".equals(ConfigManager.getConfigManager().getProperty("table.enterTo"))) {
        activeTable.getActionMap().remove("ENTER");//enterToDown();
        } else {
        activeTable.getActionMap().put("ENTER", myEnterAction);
        }
        this.addFocusListener(new FocusListener() {
        
        @Override
        public void focusGained(FocusEvent e) {
        if ("Down".equals(ConfigManager.getConfigManager().getProperty("table.enterTo"))) {
        activeTable.getActionMap().remove("ENTER");//enterToDown();
        } else {
        activeTable.getActionMap().put("ENTER", myEnterAction);
        }
        }
        
        @Override
        public void focusLost(FocusEvent e) {
        }
        });*/
        //enterToRight();
        //lockedTable.setBorder(new LineBorder(Color.BLACK, 1));
        //lockedTable.setBackground(Color.ORANGE);
        rScrollPane.getHorizontalScrollBar().addMouseMotionListener(
                new MouseMotionAdapter() {

                    public void mouseDragged(MouseEvent e) {
                        if (isFirstLoad) {
                            isFirstLoad = false;
                            revalidate();
                            repaint();
                        }
                    }
                });

        rScrollPane.getHorizontalScrollBar().addComponentListener(
                new ComponentListener() {

                    public void componentShown(ComponentEvent ev) {
                        leftBottomPanel.setVisible(true);
                        leftBottomPanel.setPreferredSize(new Dimension(0,
                                rScrollPane.getHorizontalScrollBar().getHeight()));
                        validate();
                    }

                    public void componentHidden(ComponentEvent ev) {

                        leftBottomPanel.setVisible(false);
                    }

                    public void componentResized(ComponentEvent componentEvent) {
                        leftBottomPanel.setVisible(true);
                        leftBottomPanel.setPreferredSize(new Dimension(0,
                                rScrollPane.getHorizontalScrollBar().getHeight()));
                        validate();
                    }

                    public void componentMoved(ComponentEvent componentEvent) {
                    }
                });

        // ��񲼾�����
        this.setLayout(new BorderLayout());
        this.leftBottomPanel.setVisible(false);
        this.leftBottomPanel.setBorder(BorderFactory.createEmptyBorder());
        this.leftPanel.setLayout(new BorderLayout());
        this.leftPanel.add(lScrollPane, BorderLayout.CENTER);
        this.leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);
        this.lScrollPane.getViewport().add(lockedTable, null);
        this.rScrollPane.getViewport().add(activeTable, null);
        this.lScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.lScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBorder(null);

        pnl.add(leftPanel, BorderLayout.WEST);
        pnl.add(rScrollPane, BorderLayout.CENTER);
        final int height = this.getRowHeight();//15
        this.add(pnl, BorderLayout.CENTER);
        pnlStat = new JPanel() {

            public void paint(Graphics g) {
                super.paint(g);

                FTable ftable = (FTable) FBaseTable.this;
                FTableModel ftableModel = (FTableModel) ftable.getModel();

                Color old_color = g.getColor();

                int x = 0;
                for (int i = 0; i < FBaseTable.this.getLeftLockedTable().getColumnModel().getColumnCount(); i++) {

                    String f_name = FBaseTable.this.getLeftLockedTable().getColumnModel().getColumn(i).getIdentifier().toString();
                    String val = "";
                    if (f_name.equals("Row_No")) {
                        val = "ͳ��:";
                    } else {
                        FColumnStat fColumnStat = ftableModel.getHt_columnStat().get(f_name);
                        if (fColumnStat != null) {
                            val = fColumnStat.getDisplay();
                        }
                    }
                    if (!val.equals("")) {
                        g.setColor(Color.black);
                        g.setClip(x, 0, FBaseTable.this.getLeftLockedTable().getColumnModel().getColumn(i).getWidth(), FBaseTable.this.getRowHeight());
                        g.drawString(val, x, 12);
                        g.setClip(null);
                    }

                    x = x + FBaseTable.this.getLeftLockedTable().getColumnModel().getColumn(i).getWidth();
                    g.setColor(FBaseTable.this.getLeftLockedTable().getGridColor());
//                    g.drawLine(x, 0, x, 50);
                    g.drawLine(x, 0, x, height);
                }

                int hx = rScrollPane.getHorizontalScrollBar().getValue();

                int x0 = x;
                x = x - hx;
                for (int i = 0; i < FBaseTable.this.getRightActiveTable().getColumnModel().getColumnCount(); i++) {

                    String f_name = FBaseTable.this.getRightActiveTable().getColumnModel().getColumn(i).getIdentifier().toString();
                    String val = "";
                    FColumnStat fColumnStat = ftableModel.getHt_columnStat().get(f_name);
                    if (fColumnStat != null) {
                        val = fColumnStat.getDisplay();
                    }
                    g.setColor(Color.black);
                    int c_w = Math.min(pnlStat.getWidth() - x, FBaseTable.this.getRightActiveTable().getColumnModel().getColumn(i).getWidth());
                    g.setClip(Math.max(x, x0), 0, c_w, FBaseTable.this.getRowHeight());
                    g.drawString(val, x, 12);
                    g.setClip(null);

                    x = x + FBaseTable.this.getRightActiveTable().getColumnModel().getColumn(i).getWidth();
                    if (x > x0) {
                        g.setColor(FBaseTable.this.getLeftLockedTable().getGridColor());
//                        g.drawLine(x, 0, x, 50);
                        g.drawLine(x, 0, x, height);
                    }
                }
                g.setColor(old_color);
            }
        };
//        pnlStat.setBorder(new LineBorder(FBaseTable.this.getLeftLockedTable().getGridColor()));
        rScrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                pnlStat.updateUI();
            }
        });
        pnlStat.setPreferredSize(new Dimension(1, this.getRowHeight()));//15
//        this.add(pnlStat, BorderLayout.SOUTH);
        pnlStat.setVisible(false);

        localColumnPanel = new LocalColumnPanel();
        localColumnPanel.setVisible(false);
        localColumnPanel.addIPickColumnFindListener(new IPickColumnLocalListener() {

            @Override
            public void localColumn(String field_name) {
                LocalColumnFrames(field_name);
            }

            @Override
            public void closeLocal() {
                removeLocalColumnPanel();
            }
        });

        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(pnlStat, BorderLayout.NORTH);
        southPanel.add(localColumnPanel, BorderLayout.SOUTH);
        this.add(southPanel, BorderLayout.SOUTH);

        // ʹ�����ұ����ѡ��ͬ�������Բ�����ͬ����ѡ��ģ��
        this.lockedTable.setSelectionModel(activeTable.getSelectionModel());
        // ���������񣬲��ܵ����е�λ��
        this.lockedTable.getTableHeader().setReorderingAllowed(false);
        // ���������񣬲��ܵ����еĿ��
        this.lockedTable.getTableHeader().setResizingAllowed(false);
        // ������������У���������ģ���д����������ֹ�����ġ�
        this.lockedTable.setAutoCreateColumnsFromModel(false);
        // �Ҳ������У���������ģ���д����������ֹ�����ġ�
        this.activeTable.setAutoCreateColumnsFromModel(false);
        // ���λ����Զ����ձ���ε����еĿ��
        this.activeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // �������ұ��֧�ָ��ж�ѡ
        this.activeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.lockedTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // ���ñ����и�
        this.setRowHeight(19);

        // ���ñ����������ɫ
        this.activeTable.setGridColor(new Color(172, 168, 153));
        this.lockedTable.setGridColor(new Color(172, 168, 153));

        // ���ñ�ͷ�ı�����ɫ
        this.activeTable.getTableHeader().setBackground(Color.WHITE);
        this.activeTable.getTableHeader().setBackground(Color.WHITE);

        // ���ñ�������ͼ�ı�����ɫ
        this.lScrollPane.getViewport().setBackground(Color.WHITE);
        this.rScrollPane.getViewport().setBackground(Color.WHITE);

        // �������ҹ�����嶼û�б߿�
        this.lScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.rScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // �������ҹ���������
        this.lScrollPane.getVerticalScrollBar().setModel(
                rScrollPane.getVerticalScrollBar().getModel());

        // ���ñ��߿�
        this.setBorder(this.border);
        // ��ʼ�����ģ��
        this.initTableModel();
        // ��ʼ�������ģ��
        this.initColumnModel();
        // ��ʼ������¼�
        this.initEventListeners();
        // ���ñ����к�ȱʡ�ɼ�
        this.setShowRowNumber(true);
    }

    public void addColumnStat(FColumnStat fColumnStat) {
        FTableModel model = (FTableModel) ((FTable) this).getModel();
        model.getHt_columnStat().put(fColumnStat.getColumn_id(), fColumnStat);
        pnlStat.setVisible(true);
        pnlStat.updateUI();
    }

    public void removeAllColumnStat() {
        FTableModel model = (FTableModel) ((FTable) this).getModel();
        model.getHt_columnStat().clear();
        pnlStat.setVisible(false);
    }

    public void removeColumnStat(String column_id) {
        FTableModel model = (FTableModel) ((FTable) this).getModel();
        model.getHt_columnStat().remove(column_id);
        if (model.getHt_columnStat().size() == 0) {
            pnlStat.setVisible(false);
        }
        pnlStat.updateUI();
    }

    public void setFieldHeaders(List<String> fields) {
        fieldHeaders.clear();
        fieldColumnList.clear();
        local_field_name = "-1";
        this.fieldHeaders.addAll(fields);
    }

    /**
     * ��ʾ��λ�в�ѯ��
     */
    public void addLocalColumnPanel() {
        localColumnPanel.setVisible(true);
        localColumnPanel.updateUI();
    }

    /**
     * �رն�λ�в�ѯ��
     */
    public void removeLocalColumnPanel() {
        localColumnPanel.setVisible(false);
        localColumnPanel.updateUI();
    }

    /**
     * ��ʼ�����ģ��
     */
    private void initTableModel() {
        // ���ģ�ͳ�ʼ��
        FBaseTableSorter sorter = new FBaseTableSorter();
        FBaseTableSumRowModel sumModel = new FBaseTableSumRowModel(sorter);


        this.activeTable.setModel(sumModel);

        FBaseTableRowNoModel rm = new FBaseTableRowNoModel(sumModel);

        // ���úϼ����Ƿ�ɼ�
        sumModel.setShowSumRow(this.isShowSumRow());
        rm.setShowSumRow(this.isShowSumRow());
        // ���úϼ����Ƿ������������ݵĶ���
        sumModel.setSumRowAtTop(this.isSumRowAtTop());
        rm.setSumRowAtTop(this.isSumRowAtTop());
        /**
         * �����ܼ����Ƿ�ɼ����ܼ��е�λ�ã��ܼ�����С���е�λ�ù�ϵ
         */
        /** **************begin**************** */
        sumModel.setShowSumRowAll(this.isShowSumRowAll());
        rm.setShowSumRowAll(this.isShowSumRowAll());
        sumModel.setSumRowAllAtTop(this.isSumRowAllAtTop());
        rm.setSumRowAllAtTop(this.isSumRowAllAtTop());
        sumModel.setAllTopPart(this.isAllTopPart());
        rm.setAllTopPart(this.isAllTopPart());
        /** **************end****************** */
        this.lockedTable.setModel(rm);
    }

    /**
     * ��ʼ����ģ��
     */
    private void initColumnModel() {
        // ��ģ�ͳ�ʼ��
        this.columModel = new FBaseTableColumnModel();
        this.columModel.setLeftModel((DefaultTableColumnModel) lockedTable.getColumnModel());
        this.columModel.setRightModel((DefaultTableColumnModel) this.activeTable.getColumnModel());

        this.getSumRowModel().setColumnModel(this.columModel);
    }

    /**
     * Ϊ������ӱ�ͷ����¼�������
     *
     * @param l
     *            ����¼�������
     */
    public void addTableHeaderMouseListener(MouseListener l) {
        lockedTable.getTableHeader().addMouseListener(l);
        activeTable.getTableHeader().addMouseListener(l);
    }

    /**
     * Ϊ�������������¼�������
     *
     * @param l
     *            ����¼�������
     */
    public void addTableBodyMouseListener(MouseListener l) {
        lockedTable.addMouseListener(l);
        activeTable.addMouseListener(l);
        // �ڱ���·��Ŀյط����ڹ�����壬����Ҳ��Ҫ��������¼�������
        lScrollPane.addMouseListener(l);
        rScrollPane.addMouseListener(l);
    }

    /**
     * ��ʼ������¼�����
     */
    public void initEventListeners() {

        this.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (!(e.getSource() instanceof JTable)) {
                    return;
                }
                JTable tableView = ((JTable) e.getSource());
                TableColumnModel columnModel = tableView.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());

                if (tableView == activeTable) {
                    cur_column = viewColumn + lockedTable.getColumnCount() - 1;//tableView.convertColumnIndexToModel(viewColumn);
                } else {
                    cur_column = tableView.convertColumnIndexToModel(viewColumn) - 1;
                }
            }
        });

        // ��ͷ�������
        this.addTableHeaderMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                stopEditing();
//                JTable tableView = ((JTableHeader) e.getSource()).getTable();
//                TableColumnModel columnModel = tableView.getColumnModel();
//                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
//                if (tableView == activeTable) {
//                    cur_column = tableView.convertColumnIndexToModel(viewColumn);
//                } else {
//                    cur_column = tableView.convertColumnIndexToModel(viewColumn) - 1;
//                }
//
//                if (e.getButton() == MouseEvent.BUTTON1) {
//                    // �����ͷ��ѡ������ȫѡ��ȫ��ѡ
//                    try {
//                        int chkHdrIndex = columnModel.getColumnIndex("isCheck");
//                        TableColumn chkCol = columnModel.getColumn(chkHdrIndex);
//                        if (isShowHeaderCheckBox && chkHdrIndex == viewColumn) {
//                            FCheckBoxTableHeaderRenderer hdr = (FCheckBoxTableHeaderRenderer) ((FBaseTableColumn) chkCol).getHeaderRenderer();
//                            hdr.getCheckBox().setSelected(
//                                    !hdr.getCheckBox().isSelected());
//                            FTableModel model = (FTableModel) getModel();
//                            model.setAllCheckBoxsSelected(hdr.getCheckBox().isSelected());
//                            repaint();
//                        }
//                    } catch (IllegalArgumentException iae) {
//                        // ������ѡ�����
//                    }
//
//                    // ������ͷ����
//                    if (cur_column != -1 && sortable) {
//                        FBaseTableColumn sortCol = (FBaseTableColumn) columnModel.getColumn(viewColumn);
//                        FBaseTableColumn tempCol = null;
//                        int columns = FBaseTable.this.getColumnModel().getColumnCount();
//                        for (int i = 0; i < columns; i++) {
//                            tempCol = (FBaseTableColumn) FBaseTable.this.getColumnModel().getColumn(i);
//                            if (tempCol.getIdentifier().equals("isCheck")) {
//                                continue;
//                            }
//                            DefaultTableCellRenderer hdr = (DefaultTableCellRenderer) tempCol.getHeaderRenderer();
//
//                            Icon hdrIcon = null;
//
//                            // lgc add start
//                            if (tempCol == sortCol && sortCol.isSortable()) {
//                                //Lindx 2008-05-22 ����������sorter�н��д���
////								if (tempCol.getEditDataField() instanceof FDecimalField
////										|| tempCol.getEditDataField() instanceof FIntegerField) {
////									FBaseTable.this.getSorter().setSortType(
////											FBaseTableSorter.NUMBER_SORT);
////								} else {
////									FBaseTable.this.getSorter().setSortType(
////											FBaseTableSorter.ADAPTING_SORT);
////								}
//                                sortByColumn(sortCol, ascending);
//                                hdrIcon = ascending ? ASCENDING_ICON
//                                        : DESCENDING_ICON;
//                            }
//                            // lgc add end
//                            hdr.setIcon(hdrIcon);
//
//                            hdr.setIcon(hdrIcon);
//
//                        }
//                        ascending = !ascending;
//                        repaint();
//                    }
//                }
            }
        });

        // ��������ƶ���������
        MouseMotionAdapter moveListener = new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();
                JTable table = (JTable) e.getSource();
                if (table.equals(lockedTable)) {
                    if (p.getX() > table.getWidth()) {
                        MouseEvent ev = new MouseEvent(activeTable, e.getID(),
                                e.getWhen(), e.getModifiers(), e.getX() - table.getWidth(), e.getY(), e.getClickCount(), e.isPopupTrigger());
                        activeTable.dispatchEvent(ev);
                    }
                } else {
                    if (p.getX() < 0) {
                        MouseEvent ev = new MouseEvent(lockedTable, e.getID(),
                                e.getWhen(), e.getModifiers(), lockedTable.getWidth() + e.getX(), e.getY(),
                                e.getClickCount(), e.isPopupTrigger());
                        lockedTable.dispatchEvent(ev);
                    }
                }
            }
        };
        activeTable.addMouseMotionListener(moveListener);
        lockedTable.addMouseMotionListener(moveListener);
    }

    /**
     * ���ñ����ѡ��ʱ���Ƿ�ı临ѡ���ѡ��״̬,true:��Ӱ�죬false������Ӱ��
     *
     * @param isCheckBoxAffectedByClickRow
     */
    public void setIsCheckBoxAffectedByClickRow(
            boolean isCheckBoxAffectedByClickRow) {
        this.isCheckBoxAffectedByClickRow = isCheckBoxAffectedByClickRow;
    }

    /**
     * �����Ƿ���ʾ��ͷ�ϵĸ�ѡ��ѡ���У���
     *
     * @param isShowHeaderCheckBox
     */
    public void setIsShowHeaderCheckBox(boolean isShowHeaderCheckBox) {
        this.isShowHeaderCheckBox = isShowHeaderCheckBox;

        FBaseTableColumn chkBox = this.getColumnById("isCheck");
        if (chkBox != null) {
            FCheckBoxTableHeaderRenderer chkHdr = (FCheckBoxTableHeaderRenderer) chkBox.getHeaderRenderer();
            chkHdr.getCheckBox().setVisible(isShowHeaderCheckBox);

        }
    }

    /**
     * ��ʼ���к���
     */
    protected void updateRowNumberColumn() {
        FBaseTableColumn col = (FBaseTableColumn) lockedTable.getColumn(ROWNUMBER_COLUMN_NAME);
        if (this.isShowRowNumber()) {
            col.setVisible(true);
            col.setWidth(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
            col.setMaxWidth(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
            col.setMinWidth(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
            col.setPreferredWidth(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
        } else {
            col.setVisible(false);
        }
        this.adjustLeftLockedTableWidth();
    }

    /**
     * ��������ģ��,������Զ������У������ģ�ʹ��������� ����ʹ���û��Զ������
     *
     * @param model
     *            ��������ģ��
     */
    public void setModel(TableModel model) {
        this.getSorter().setModel(model);
        this.getRowNumberModel().fireTableStructureChanged();
        if (autoCreateColumnsFromModel) {
            this.createTableColumnsFromModel();
        }
    }

    public void cancelSort() {
        this.setModel(this.getModel());
    }

    /**
     * ��ñ��ģ��
     *
     * @return ���ģ��
     */
    public TableModel getModel() {
        return getSorter().getModel();
    }

    /**
     * �������ģ��
     *
     * @return ����ģ��
     */
    public FBaseTableSorter getSorter() {
        return (FBaseTableSorter) getSumRowModel().getModel();
    }

    /**
     * ����к�ģ��
     *
     * @return �к�ģ��
     */
    private FBaseTableRowNoModel getRowNumberModel() {
        if (this.lockedTable == null) {
            return null;
        }

        return (FBaseTableRowNoModel) lockedTable.getModel();

    }

    /**
     * ��úϼ���ģ��
     *
     * @return
     */
    public FBaseTableSumRowModel getSumRowModel() {
        if (getRowNumberModel() == null) {
            return null;
        }
        return (FBaseTableSumRowModel) getRowNumberModel().getModel();
    }

    /**
     * �ж��Ƿ���ںϼ���
     *
     * @return
     */
    public boolean isExistTotalRow() {
        for (int i = 0; i < this.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) getColumnModel().getColumn(i);
            /**
             * �����ܼ���
             *
             * @author jerry
             */
            /** **************begin*************** */
            if (col.isTotal() || col.isAllTotal()) /** **************end***************** */
            {
                return true;
            }
        }
        return false;
    }

    /**
     * ��ָ���н�������,����ʽ��ascendingָ��,true��ʾ��������,false��ʾ��������
     *
     * @param columnIndex
     *            �Ǳ���ϵ�ģ������
     * @param ascending
     *            ����ʽ
     */
    protected void sortByColumn(FBaseTableColumn column, boolean ascending) {
        int old_select = this.getSelectedRowModelIndex();

        getSorter().sortByColumn(column, ascending,f_row_index);
        if (this.getModel().getRowCount() > 0) {
            for (int i = 0; i < getSorter().getSortedIndex().length; i++) {
                if (getSorter().getSortedIndex()[i] == old_select) {
                    this.setRowSelectionInterval(i, i);
                    getVerticalScrollBar().setValue(i * getRowHeight());
                    break;
                }
            }
        }
    }

    /**
     * ��������������������
     *
     * @param identifier
     * @param ascending
     */
    protected void sortByColumn(String identifier, boolean ascending) {
        System.out.println("identifier:" + identifier);
        //int column = activeTable.getColumn(identifier).getModelIndex();
        sortByColumn((FBaseTableColumn) activeTable.getColumn(identifier), ascending);
    }

    /**
     * �����Ƿ���ʾ�к�
     *
     * @param showRowNumber
     */
    public void setShowRowNumber(boolean showRowNumber) {
        this.showRowNumber = showRowNumber;
        this.updateRowNumberColumn();
    }

    /**
     * �ж��к��Ƿ���ʾ
     *
     * @return
     */
    public boolean isShowRowNumber() {
        return this.showRowNumber;
    }

    /**
     * �����������ı��
     *
     * @return ������������ı��
     */
    public JTable getLeftLockedTable() {
        return lockedTable;
    }

    /**
     * ����Ҳ��ı��
     *
     * @return �����Ҳ��ı��
     */
    public JTable getRightActiveTable() {
        return activeTable;
    }

    /**
     * ������ǰ����ǰ����
     *
     * @param index
     *            �������ͼ��˳���
     */
    public void lockColumns(int index) {
        this.unLockColumns();
        if (index >= activeTable.getColumnCount()) {
            return;
        }

        Vector v = new Vector();
        for (int i = 0; i <= index; i++) {
            FBaseTableColumn col = (FBaseTableColumn) activeTable.getColumnModel().getColumn(i);
            v.add(col);
        }

        for (int i = 0; i < v.size(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) v.get(i);
            activeTable.removeColumn(col);
            col.setModelIndex(col.getModelIndex() + 1);
            lockedTable.addColumn(col);
            col.setLocked(true);
        }
        this.adjustLeftLockedTableWidth();
        if (this.getRightActiveTable().getTableHeader() != null) {
            this.rScrollPane.getViewport().repaint();
        }
    }

    /**
     * ����,ȫ���������б�����.
     */
    public void unLockColumns() {
        for (int i = lockedTable.getColumnCount() - 1; i > 0; i--) {
            FBaseTableColumn col = (FBaseTableColumn) lockedTable.getColumnModel().getColumn(i);
            lockedTable.removeColumn(col);
            col.setModelIndex(col.getModelIndex() - 1);
            activeTable.addColumn(col);
            col.setLocked(false);
            activeTable.getColumnModel().moveColumn(
                    activeTable.convertColumnIndexToView(col.getModelIndex()),
                    0);
        }
        this.adjustLeftLockedTableWidth();
    }

    /**
     * ��ñ���е�ѡ��ģ��
     *
     * @return ����е�ѡ��ģ��
     */
    public ListSelectionModel getSelectionModel() {
        return activeTable.getSelectionModel();
    }

    /**
     * ���ñ���е�ѡ��ģ��
     *
     * @param modelѡ��ģ��
     */
    public void setSelectionModel(ListSelectionModel model) {
        activeTable.setSelectionModel(model);
        lockedTable.setSelectionModel(model);
    }

    /**
     * ���ñ����и�
     *
     * @param height
     */
    public void setRowHeight(int height) {
        activeTable.setRowHeight(height);
        lockedTable.setRowHeight(height);
    }

    /**
     * ��ñ����и�
     *
     * @return �и�
     */
    public int getRowHeight() {
        return activeTable.getRowHeight();
    }

    /**
     * ��ñ����������������к���
     *
     * @return ��������
     */
    public int getColumnCount() {
        return lockedTable.getColumnCount() - 1 + activeTable.getColumnCount();
    }

    /**
     * ����������һ��
     *
     * @param col
     */
    public void addColumn(FBaseTableColumn col) {

        this.getColumnModel().addColumn(col);

    }

    /**
     * �ӱ����ɾ��һ��
     *
     * @param col
     */
    public void removeColumn(FBaseTableColumn col) {
        //if (col.isLocked())
        //return;
        //activeTable.removeColumn(activeTable.getColumn(col.getIdentifier()));
        //edit time 2008-07-18
        //���������
        if (col.getId().equals(FBaseTable.ROWNUMBER_COLUMN_NAME)) {
            return;
        }
        FBaseTableColumnModel columnModel = this.getColumnModel();
        columnModel.removeColumn(col);
    }

    /**
     * ����ָ���еı�ʶ����ж���
     *
     * @param identifier
     * @return �����
     */
    public FBaseTableColumn getColumn(Object identifier) {
        try {
            return (FBaseTableColumn) activeTable.getColumn(identifier);
        } catch (Exception ex) {
            return (FBaseTableColumn) lockedTable.getColumn(identifier);
        }
    }

    public int getCurrentColumnIndex() {
        int currentIndex = this.getLockedTable().getColumnCount() + this.activeTable.getSelectedColumn() - 1;
        return currentIndex;
    }

    /**
     * ��õ�ǰ����ѡ�е����ݣ��ڱ���е�˳��ţ���˳��Ŵ������п�ʼ���㡣
     *
     * @return ��ǰ��
     */
    public int getCurrentRowIndex() {

        /**
         * �Ժϼ��н���֧��
         *
         * @author jerry
         */
        /** ********************begin******************** */
        int currentIndex = this.activeTable.getSelectedRow();

        currentIndex = this.getSumRowModel().convertViewRowToModelRow(currentIndex);

        if (currentIndex <= -1) {
            currentIndex = -1;
        }

        return currentIndex;
        /** ********************end********************** */
    }

    /**
     * ���ظ���ѡ�еĵ�һ�����ݣ�������ģ����˳��ţ���0��ʼ���㡣
     *
     * @return ģ�͵�˳���
     */
    public int getSelectedRowModelIndex() {
        int index = this.getCurrentRowIndex();
        if (index < 0) {
            return -1;
        }
        return this.convertViewRowIndexToModel(index);
    }

    /**
     * ���ظ���ѡ�еĶ������ݣ�������ģ����˳��ţ���0��ʼ���㡣
     *
     * @return ˳�������
     */
    public int[] getSelectedRowModelIndexes() {
        int[] viewIndex = activeTable.getSelectedRows();
        Vector v = new Vector();

        // ����ϼ��еĲ�����

        for (int i = 0; i < viewIndex.length; i++) {

            int index = this.getSumRowModel().convertViewRowToModelRow(viewIndex[i]);

            if (index >= 0) {
                v.add(new Integer(convertViewRowIndexToModel(index)));
            }
        }
        int[] modelIndex = new int[v.size()];
        for (int i = 0; i < v.size(); i++) {
            modelIndex[i] = ((Integer) v.get(i)).intValue();
        }
        return modelIndex;
    }

    /**
     * ���ر�����ģ��,���Ҫ���е�ѡ�н��в���,���Ե��ô˷�����ȡ��ģ��
     *
     * @return
     */
    public FBaseTableColumnModel getColumnModel() {
        return this.columModel;
    }

    /**
     * ������ģ��
     *
     * @param model
     *            ��ģ��
     */
    public void setColumnModel(FBaseTableColumnModel model) {
        this.columModel = model;
        this.getSumRowModel().setColumnModel(this.columModel);
        lockedTable.setColumnModel(columModel.getLeftModel());
        activeTable.setColumnModel(columModel.getRightModel());

    }

    /**
     * ����ͼ�е�����ת��Ϊģ�͵�����
     *
     * @param viewIndex,�Ӻϼ����µĵ�һ�������п�ʼ����,��0��ʼ.
     * @return ����ģ��List�е�����.
     */
    public int convertViewRowIndexToModel(int viewIndex) {
        if (viewIndex < 0 || viewIndex >= this.getSortedIndex().length) {
            return -1;
        }
        return this.getSortedIndex()[viewIndex];
    }

    /**
     * ��ģ�͵�����ת��Ϊ��ͼ������
     *
     * @param modelIndex
     * @return
     */
    public int convertModelRowIndexToView(int modelIndex) {
        int[] indexes = this.getSortedIndex();
        for (int i = 0; indexes != null && i < indexes.length; i++) {
            if (indexes[i] == modelIndex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ���������ģ������
     *
     * @return ģ������������
     */
    public int[] getSortedIndex() {
        return this.getSorter().getSortedIndex();
    }

    /**
     * ���Ƶ�ǰѡ����
     */
    public void moveUpCurrentRow() {
        this.stopEditing();
        int selectIndex = this.activeTable.getSelectedRow();

        if (selectIndex > -1) {
            int rowIndex = this.getSumRowModel().convertViewRowToModelRow(selectIndex);

            if (rowIndex > 0 && rowIndex <= this.getRowCount() - 1) {
                this.getSorter().rowMoveUpOrDown(FBaseTableSorter.UP, rowIndex);
                activeTable.setRowSelectionInterval(selectIndex - 1, selectIndex - 1);
            }
        }

    }

    /**
     * ���Ƶ�ǰѡ����
     */
    public void moveDownCurrentRow() {
        this.stopEditing();
        int selectIndex = this.activeTable.getSelectedRow();

        if (selectIndex > -1) {
            int rowIndex = this.getSumRowModel().convertViewRowToModelRow(selectIndex);

            if (rowIndex >= 0 && rowIndex < this.getRowCount() - 1) {
                this.getSorter().rowMoveUpOrDown(FBaseTableSorter.DOWN, rowIndex);
                activeTable.setRowSelectionInterval(selectIndex + 1, selectIndex + 1);
            }
        }

    }

    /**
     * ���ø���ѡ�����з�Χ
     *
     * @param from
     *            ��ʼ��,�Ӻϼ��п�ʼ����
     * @param to
     *            ������,�Ӻϼ��п�ʼ����
     */
    public void setRowSelectionInterval(int from, int to) {

        activeTable.setRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
        lockedTable.setRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));

    }

    /**
     * ׷�Ӹ���ѡ�����з�Χ
     *
     * @param from
     *            ��ʼ��,�Ӻϼ��п�ʼ����
     * @param to
     *            ������,�Ӻϼ��п�ʼ����
     */
    public void addRowSelectionInterval(int from, int to) {

        activeTable.addRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
        lockedTable.addRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
    }

    /**
     * �������ѡ�����з�Χ
     *
     * @param from
     *            ��ʼ��,�Ӻϼ��п�ʼ����
     * @param to
     *            ������,�Ӻϼ��п�ʼ����
     */
    public void removeRowSelectionInterval(int from, int to) {

        activeTable.removeRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
        lockedTable.removeRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
    }

    /**
     * ����ϼ���λ���������ݶ������ڼ��������е����±�ʱ��Ҫ���ϼ��п۳����⡣ �����ӺϼƵ���һ�еĿ�ʼ���±�������0��1��2......��
     *
     * @param index
     * @return
     */
    private int convert2RealRowIndex(int index) {

        /**
         * ֧�ֺϼ���
         *
         * @author jerry
         */
        /** **********************begin********************* */
        if (this.getSumRowModel().getResult() != GetSumRowStute.noSumRow) {
            return index + this.getSumRowModel().resultRowChange(this.showSumRow,
                    this.showSumRowAll, this.isSumRowAtTop(),
                    this.isSumRowAllAtTop(), this.isAllTopPart);
        }
        /** **********************end*********************** */
        return index;
    }

    /**
     * ȫ������ѡ��
     */
    public void selectAll() {
        activeTable.selectAll();
        lockedTable.selectAll();
    }

    /**
     * ���ȫ������ѡ��
     */
    public void clearSelection() {
        activeTable.clearSelection();
        lockedTable.clearSelection();
    }

    /**
     * ����ָ����Χ���и�����ʾ�������临ѡ����Ϊѡ��״̬
     *
     * @param from
     *            ��ʼ��,�Ӻϼ��п�ʼ����
     * @param to
     *            ������,�Ӻϼ��п�ʼ����
     * @param isSelected
     *            ��ѡ���Ƿ�ѡ��
     */
    public void setCheckBoxSelectedInterval(int from, int to, boolean isSelected) {
        for (int i = convertViewRowIndexToModel(from); i <= convertViewRowIndexToModel(to); i++) {
            ((FTableModel) this.getModel()).setCheckBoxSelectedAtRow(i,
                    isSelected);
        }
//		int iRow =0;
//		for (int i = from; i <= to; i++) {
//			//���ݺϼ���ģ��ת��Ϊ������
//			iRow = this.getSumRowModel().convertModelRowToViewRow(i);
//			this.lockedTable.setRowSelectionInterval(iRow-1, iRow);
//			this.activeTable.setRowSelectionInterval(iRow-1, iRow);
//			
//			((FTableModel) this.getModel()).setCheckBoxSelectedAtRow(i,
//					isSelected);
//		}
    }

    /**
     * ��������¼�������,��������������������¼�������кͱ���·��հ״���
     *
     * @param l
     *            ����¼�������
     */
    public void addMouseListener(MouseListener l) {
        lockedTable.addMouseListener(l);
        activeTable.addMouseListener(l);
        lScrollPane.addMouseListener(l);
        rScrollPane.addMouseListener(l);
    }

    /**
     * ��������ƶ�����������������������������¼�������кͱ���·��հ״���
     *
     * @param l
     *            ����¼�������
     */
    public void addMouseMotionListener(MouseMotionListener l) {
        lockedTable.addMouseMotionListener(l);
        activeTable.addMouseMotionListener(l);
        lScrollPane.addMouseMotionListener(l);
        rScrollPane.addMouseMotionListener(l);
    }

    public void addKeyListener(KeyListener l) {
        lockedTable.addKeyListener(l);
        activeTable.addKeyListener(l);
    }

    public void addMouseWheelListener(MouseWheelListener l) {
        lockedTable.addMouseWheelListener(l);
        activeTable.addMouseWheelListener(l);
    }

    public void addListSelectionListener(ListSelectionListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
        this.getSelectionModel().addListSelectionListener(l);
    }

    public void removeMouseListener(MouseListener l) {
        lockedTable.removeMouseListener(l);
        activeTable.removeMouseListener(l);
    }

    public MouseListener[] getMouseListeners() {
        return activeTable.getMouseListeners();
    }

    public void removeMouseMotionListener(MouseMotionListener l) {
        lockedTable.removeMouseMotionListener(l);
        activeTable.removeMouseMotionListener(l);
    }

    public MouseMotionListener[] getMouseMotionListeners() {
        return activeTable.getMouseMotionListeners();
    }

    public void removeMouseWheelListener(MouseWheelListener l) {
        lockedTable.removeMouseWheelListener(l);
        activeTable.removeMouseWheelListener(l);
    }

    public MouseWheelListener[] getMouseWheelListeners() {
        return activeTable.getMouseWheelListeners();
    }

    public void removeKeyListener(KeyListener l) {
        lockedTable.removeKeyListener(l);
        activeTable.removeKeyListener(l);
    }

    public KeyListener[] getKeyListeners() {
        return activeTable.getKeyListeners();
    }

    public void removeListSelectionListener(ListSelectionListener l) {
        this.getSelectionModel().removeListSelectionListener(l);
    }

    public void removeAllListSelectionListener() {
        for (ListSelectionListener ls : this.getListeners()) {
            this.removeListSelectionListener(ls);
        }
    }

    public void refreshListSelectionListener() {
        for (ListSelectionListener ls : this.getListeners()) {
            this.addListSelectionListener(ls);
            ls.valueChanged(null);
        }
    }

    /**
     *
     * @param horizontalScrollBarPolicy
     */
    public void setHorizontalScrollBarPolicy(int horizontalScrollBarPolicy) {
        rScrollPane.setHorizontalScrollBarPolicy(horizontalScrollBarPolicy);
    }

    /**
     *
     * @param verticalScrollBarPolicy
     */
    public void setVerticalScrollBarPolicy(int verticalScrollBarPolicy) {
        rScrollPane.setVerticalScrollBarPolicy(verticalScrollBarPolicy);
    }

    /**
     *
     * @return
     */
    public JScrollBar getHorizontalScrollBar() {
        return rScrollPane.getHorizontalScrollBar();
    }

    /**
     *
     * @return
     */
    public JScrollBar getVerticalScrollBar() {
        return rScrollPane.getVerticalScrollBar();
    }

    /**
     * �ж�һ���Ƿ�ɼ�
     *
     * @param col
     * @return
     */
    public boolean isColumnVisible(Object identifier) {
        return this.getColumn(identifier).isVisible();
    }

    /**
     * ����һ�еı�ʶ�����ø����Ƿ�ɼ���
     *
     * @param
     */
    public void setColumnVisible(Object identifier, boolean visible) {
        // ������к��У�ֱ�ӷ���
        if (FBaseTable.ROWNUMBER_COLUMN_NAME.equals(identifier)) {
            return;
        }
        FBaseTableColumn col = this.getColumn(identifier);
        if (col.isLocked()) {
            return;
        }
        col.setVisible(visible);
    }

    /**
     * ��������Ѿ���ʾ����
     *
     * @return
     */
    public FBaseTableColumn[] getAllColumns() {
        Vector v = new Vector();
        for (int i = 1; i < lockedTable.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) lockedTable.getColumnModel().getColumn(i);
            if (!"isCheck".equals(col.getId())) {
                v.add(col);
            }
        }

        for (int i = 0; i < activeTable.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) activeTable.getColumnModel().getColumn(i);
            if (!"isCheck".equals(col.getId())) {
                v.add(col);
            }
        }

        if (v.size() == 0) {
            return null;
        }

        FBaseTableColumn[] cols = new FBaseTableColumn[v.size()];

        v.copyInto(cols);
        return cols;
    }

    /**
     * ��������Ѿ���ʾ����
     *
     * @return
     */
    public FBaseTableColumn[] getShowColumns() {
        Vector v = new Vector();
        for (int i = 1; i < lockedTable.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) lockedTable.getColumnModel().getColumn(i);
            if (col.isVisible() && !"isCheck".equals(col.getId())) {
                v.add(col);
            }
        }

        for (int i = 0; i < activeTable.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) activeTable.getColumnModel().getColumn(i);
            if (col.isVisible() && !"isCheck".equals(col.getId())) {
                v.add(col);
            }
        }

        if (v.size() == 0) {
            return null;
        }

        FBaseTableColumn[] cols = new FBaseTableColumn[v.size()];

        v.copyInto(cols);
        return cols;
    }

    /**
     * ��������Ѿ���ʾ����
     *
     * @return
     */
    public FBaseTableColumn[] getHideColumns() {

        Vector v = new Vector();
        for (int i = 1; i < lockedTable.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) lockedTable.getColumnModel().getColumn(i);
            if (!col.isVisible() && !"isCheck".equals(col.getId())) {
                v.add(col);
            }
        }
        for (int i = 0; i < activeTable.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) activeTable.getColumnModel().getColumn(i);
            if (!col.isVisible() && !"isCheck".equals(col.getId())) {
                v.add(col);
            }
        }
        if (v.size() == 0) {
            return null;
        }

        FBaseTableColumn[] cols = new FBaseTableColumn[v.size()];

        v.copyInto(cols);

        return cols;
    }

    /**
     * ���µ�������Ŀ��
     */
    public void adjustLeftLockedTableWidth() {
        int width = 0;
        for (int i = 0; i < lockedTable.getColumnCount(); i++) {
            width += lockedTable.getColumnModel().getColumn(i).getPreferredWidth();
        }
        lScrollPane.setPreferredSize(new Dimension(width, lScrollPane.getHeight()));
        lScrollPane.setSize(new Dimension(width, lScrollPane.getHeight()));
        validate();
    }

    /**
     *
     * @uml.property name="sortable"
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isSortable() {
        return this.sortable;
    }

    /**
     * Ϊ�����ȱʡ����ģ��
     *
     * @param table
     */
    private void createTableColumnsFromModel() {

        // ��������к��������
        int cols = lockedTable.getColumnCount();
        for (int i = 1; i < cols; i++) {
            lockedTable.removeColumn(lockedTable.getColumnModel().getColumn(1));
        }

        // ����ұ����е���
        int colnum = activeTable.getColumnCount();
        for (int i = 0; i < colnum; i++) {
            activeTable.removeColumn(activeTable.getColumnModel().getColumn(0));
        }

        TableModel model = this.getSorter();
        for (int i = 0; i < model.getColumnCount(); i++) {
            FBaseTableColumn col = new FBaseTableColumn();
            col.setId(model.getColumnName(i));
            col.setModelIndex(i);
            this.columModel.addColumn(col);
        }
    }

    /**
     *
     * @uml.property name="autoCreateColumnsFromModel"
     */
    public void setAutoCreateColumnsFromModel(boolean autoCreateColumnsFromModel) {
        this.autoCreateColumnsFromModel = autoCreateColumnsFromModel;
    }

    public boolean isAutoCreateColumnsFromModel() {
        return this.autoCreateColumnsFromModel;
    }

    public void setTableHeaderHidden() {
        activeTable.setTableHeader(null);
        lockedTable.setTableHeader(null);
    }

    public void setCellSelectionEnabled(boolean enable) {
        activeTable.setCellSelectionEnabled(enable);
        lockedTable.setCellSelectionEnabled(enable);
    }

    public void setRowSelectionAllowed(boolean rowSelectionAllowed) {
        activeTable.setRowSelectionAllowed(rowSelectionAllowed);
        lockedTable.setRowSelectionAllowed(rowSelectionAllowed);

    }

    public boolean isRowSelectionAllowed() {
        return activeTable.getRowSelectionAllowed();
    }

    public void setColumnSelectionAllowed(boolean columnSelectionAllowed) {
        activeTable.setColumnSelectionAllowed(columnSelectionAllowed);
        lockedTable.setColumnSelectionAllowed(columnSelectionAllowed);

    }

    public boolean isColumnSelectionAllowed() {
        return activeTable.getColumnSelectionAllowed();
    }

    public void setSurrendersFocusOnKeystroke(boolean surrendersFocusOnKeystroke) {
        activeTable.setSurrendersFocusOnKeystroke(surrendersFocusOnKeystroke);
        lockedTable.setSurrendersFocusOnKeystroke(surrendersFocusOnKeystroke);
    }

    /**
     * ���ñ�����Ƿ�ɼ�
     *
     * @param visible
     */
    public void setTableRowVisible(boolean visible) {
        if (!visible) {
            activeTable.setBackground(rScrollPane.getBackground());
            lockedTable.setBackground(lScrollPane.getBackground());
            activeTable.setGridColor(rScrollPane.getBackground());
            lockedTable.setGridColor(lScrollPane.getBackground());
            activeTable.setBorder(BorderFactory.createEmptyBorder());
            lockedTable.setBorder(BorderFactory.createEmptyBorder());
        } else {
            JTable table = new JTable();
            activeTable.setBackground(table.getBackground());
            lockedTable.setBackground(table.getBackground());
            activeTable.setGridColor(table.getGridColor());
            lockedTable.setGridColor(table.getGridColor());
            activeTable.setBorder(BorderFactory.createLineBorder(Color.gray));
            lockedTable.setBorder(BorderFactory.createLineBorder(Color.gray));
        }
        activeTable.setVisible(visible);
        lockedTable.setVisible(visible);
    }

    /**
     * ��ø��еı༭�ؼ�
     *
     * @param identifier
     * @return
     */
    public DataField getColumnEditor(Object identifier) {
        return this.getColumn(identifier).getEditDataField();
    }

    public int getRowCount() {
        return this.getModel().getRowCount();// this.getRightActiveTable().getRowCount();
    }

    public void setId(String id) {
        this.activeTable.setId(id);
        this.lockedTable.setId(id);
    }

    public String getId() {
        return this.activeTable.getId();
    }

    /**
     * ����id��ȡָ�����ж���,ֻ�ڿɼ����н��ж�λ
     *
     * @param id
     *            ��id
     * @return
     */
    public FBaseTableColumn getColumnById(String id) {
        FBaseTableColumn column = null;
        for (int i = 1; i < lockedTable.getColumnCount(); i++) {
            column = (FBaseTableColumn) lockedTable.getColumnModel().getColumn(
                    i);
            if (id == null || id.equalsIgnoreCase(column.getId())) {
                return column;
            }
            column = null;
        }
        for (int i = 0; i < activeTable.getColumnCount(); i++) {
            column = (FBaseTableColumn) activeTable.getColumnModel().getColumn(
                    i);
            if (id == null || id.equalsIgnoreCase(column.getId())) {
                return column;
            }
            column = null;
        }
        return column;
    }
    /**
     * �Զ��������ͷͼ��
     */
    protected static final Icon ASCENDING_ICON = new AscendingIcon();

    /**
     * ��������ͼ��
     *
     * @author fangyi
     *
     */
    protected static class AscendingIcon implements Icon {

        public AscendingIcon() {
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color saveColor = g.getColor();
            g.setColor(c.getBackground().darker().darker());
            g.drawLine(x + 4, y + 3, x + 4, y + 3);
            g.drawLine(x + 3, y + 4, x + 5, y + 4);
            g.drawLine(x + 2, y + 5, x + 6, y + 5);
            g.drawLine(x + 1, y + 6, x + 7, y + 6);
            g.setColor(saveColor);
        }

        public int getIconWidth() {
            return 16;
        }

        public int getIconHeight() {
            return 8;
        }
    };
    /**
     * �Զ��彵���ͷͼ��
     */
    protected static final Icon DESCENDING_ICON = new DescendingIcon();

    /**
     * ���������ͼ��
     *
     * @author fangyi
     */
    protected static class DescendingIcon implements Icon {

        public DescendingIcon() {
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color saveColor = g.getColor();
            g.setColor(c.getBackground().darker().darker());
            g.drawLine(x + 1, y + 3, x + 7, y + 3);
            g.drawLine(x + 2, y + 4, x + 6, y + 4);
            g.drawLine(x + 3, y + 5, x + 5, y + 5);
            g.drawLine(x + 4, y + 6, x + 4, y + 6);
            g.setColor(saveColor);
        }

        public int getIconWidth() {
            return 16;
        }

        public int getIconHeight() {
            return 8;
        }
    };

    /**
     * ֹͣ�������༭״̬��ͨ���������ڱ༭״̬���������뿪����
     * <p>
     * Ӧ�õ��ô˷���ֹͣ���ı༭״̬�����ֹͣ�༭״̬�����ݱ�
     * <p>
     * д��ģ���С�
     */
    public void stopEditing() {
        this.stopEditingForTable(this.getRightActiveTable());
        this.stopEditingForTable(this.getLeftLockedTable());
    }

    /**
     * ��ָ�����ֹͣ�༭
     *
     * @param table
     *            ���JTable��
     */
    private void stopEditingForTable(JTable table) {
        int cols = table.getColumnCount();
        for (int i = 0; i < cols; i++) {
            TableCellEditor ce = table.getColumnModel().getColumn(i).getCellEditor();
            if (ce != null) {
                ce.stopCellEditing();
            }
        }

    }

    /**
     * �ж��Ƿ���ʾС����
     *
     * @return
     */
    public boolean isShowSumRow() {
        return showSumRow;
    }

    /**
     * �����Ƿ���ʾС����
     *
     * @param showSumRow
     */
    public void setShowSumRow(boolean showSumRow) {
        this.showSumRow = showSumRow;
        this.getSumRowModel().setShowSumRow(showSumRow);
        this.getRowNumberModel().setShowSumRow(showSumRow);
    }

    /**
     * �ϼ����Ƿ�������
     *
     * �����֧�ֺϼ���������
     * @return �ϼ������ϱ�ʾtrue���ϼ������±�ʾfalse
     */
    public boolean isSumRowAtTop() {
        return sumRowAtTop;
    }

    /**
     * �ϼ����Ƿ������������ݶ�����
     *
     * @param sumRowAtTop
     *            �����ture, ���ڶ����������false�����ڵײ���
     */
    public void setSumRowAtTop(boolean sumRowAtTop) {
        this.sumRowAtTop = sumRowAtTop;
        this.getSumRowModel().setSumRowAtTop(sumRowAtTop);
        this.getRowNumberModel().setSumRowAtTop(sumRowAtTop);
    }

    /**
     * ���ظ���UI�ķ�������Ҫ��Ϊ���ػ��߿�
     */
    public void updateUI() {
        super.updateUI();
        this.setBorder(this.border);
    }

    /**
     * ����ָ���к��е�ֵ
     *
     * @param value
     *            �����õ�ֵ
     * @param row
     *            ��˳��ţ��Ӻϼ��п�ʼ����
     * @param col
     *            ��˳��ţ��ӵ�һ�п�ʼ����
     */
    public void setValueAt(Object value, int row, String col) {
        int colIndex = this.getColumnModelIndex(col);
        this.getSumRowModel().setValueAt(value, row, colIndex);
    }

    /**
     * ��ָ���к��е�ֵ
     *
     * @param row
     *            ��˳��ţ��Ӻϼ��п�ʼ����
     * @param col
     *            ��˳��ţ��ӵ�һ�п�ʼ����
     * @return
     */
    public Object getValueAt(int row, String col) {

        int colIndex = this.getColumnModelIndex(col);
        return this.getSumRowModel().getValueAt(row, colIndex);
    }

    /**
     * �����е�ID������е�ģ��˳���
     *
     * @param �е�ID
     * @return ���ظ��е�ģ��˳���
     */
    public int getColumnModelIndex(String col) {
        int index = -1;
        if (col == null || "".equals(col)) {
            return index;
        }
        try {
            index = this.getColumnModel().getRightModel().getColumnIndex(col);
            return this.activeTable.convertColumnIndexToModel(index);
        } catch (Exception ex) {
            if (col != null) {
                index = this.getColumnModel().getLeftModel().getColumnIndex(col);
                return this.lockedTable.convertColumnIndexToModel(index) - 1;
            }
        }
        return index;
    }

    /**
     * �ж�ָ���к��еĵ�Ԫ���Ƿ���Ա༭
     *
     * @param row
     *            ��˳��ţ��Ӻϼ��п�ʼ����
     * @param col
     *            ��˳��ţ��ӵ�һ�п�ʼ����
     * @return �Ƿ�ɱ༭
     */
    public boolean isCellEditable(int row, String col) {
        int colIndex = this.getColumnModelIndex(col);
        return this.getSumRowModel().isCellEditable(row, colIndex);

    }

    /**
     * �ж��Ƿ���ʾ�ܼ��С�
     *
     * @return
     */
    public boolean isShowSumRowAll() {
        return showSumRowAll;
    }

    /**
     * �����Ƿ���ʾ�ܼ���
     *
     * @param showSumRowAll
     *            �Ƿ���ʾ�ܼ���
     */
    public void setShowSumRowAll(boolean showSumRowAll) {
        this.showSumRowAll = showSumRowAll;
        this.getSumRowModel().setShowSumRowAll(showSumRowAll);
        this.getRowNumberModel().setShowSumRowAll(showSumRowAll);
    }

    /**
     * �ж��ܼ����Ƿ���С���е�����
     *
     * @return
     */
    public boolean isSumRowAllAtTop() {
        return sumRowAllAtTop;
    }

    /**
     * �����ܼ�����С���е�����
     *
     * @param sumRowAllAtTop
     */
    public void setSumRowAllAtTop(boolean sumRowAllAtTop) {
        this.sumRowAllAtTop = sumRowAllAtTop;
        this.getSumRowModel().setSumRowAllAtTop(sumRowAllAtTop);
        this.getRowNumberModel().setSumRowAllAtTop(sumRowAllAtTop);
    }

    public boolean isAllTopPart() {
        return isAllTopPart;
    }

    public void setAllTopPart(boolean isAllTopPart) {
        this.isAllTopPart = isAllTopPart;
        this.getSumRowModel().setAllTopPart(isAllTopPart);
        this.getRowNumberModel().setAllTopPart(isAllTopPart);
    }

    /**
     * �Ƿ񵥻���ʱ���ı�ѡ�����и�ѡ���ѡ��״̬
     *
     * @return
     */
    public boolean isCheckBoxAffectedByClickRow() {
        return isCheckBoxAffectedByClickRow;
    }

    /**
     * �����ڵ�����ʱ���ı�ѡ�����и�ѡ���ѡ��״̬
     *
     * @param isCheckBoxAffectedByClickRow
     */
    public void setCheckBoxAffectedByClickRow(
            boolean isCheckBoxAffectedByClickRow) {
        this.isCheckBoxAffectedByClickRow = isCheckBoxAffectedByClickRow;
    }

    /**
     * ������кϼ��е�ID
     * @return	�ϼ��е�List
     */
    public List getAllTotalId() {
        List arrList = new ArrayList();
        for (int i = 0; i < this.getColumnCount(); i++) {
            FBaseTableColumn colTemp = columModel.getColumn(i);
            if (colTemp.isAllTotal()) {
                arrList.add(colTemp.getId());
            }
        }
        return arrList;
    }

    //Add by Bigdog 080408
    //���غϼƵ�����
    public Map getAllSumRowData() {
        if (this.activeTable == null) {
            return null;
        }
        FBaseTableSumRowModel m = (FBaseTableSumRowModel) this.activeTable.getModel();
        if (m == null) {
            return null;
        }
        return m.getAllSumRowData();
    }

    /**
     * ȡ�ñ��ĵ�Ԫ������Զ���
     * @author lindx 2008-06-10
     * @return  FBaseTableCellAttribute ���Ԫ�����Զ������
     */
    protected FBaseTableCellAttribute getCellAttribute() {
        if (this.cellAttribute == null) {
            this.cellAttribute = new FBaseTableCellAttribute();
        }
        return this.cellAttribute;

    }

    /**
     * ����ָ����Ԫ��������Render
     * @param renderer  �����Render
     * @param row    	����ģ�͵��к�(��0�п�ʼ����)
     * @param column  	�ֶ�����
     * @author lindx 2008-06-10
     */
    public void setRenderAt(TableCellRenderer renderer, int row, String column) {

        //��������ȡ�ö�Ӧ�ı���ж���
        FBaseTableColumn tableColumn = this.getColumnById(column);

        if (tableColumn == null) {
            return;
        }
        tableColumn.setRenderAt(row, renderer);

    }

    /**
     * ����ָ����Ԫ��ı༭�ؼ�
     * @param editor  	�༭�ؼ�Editor
     * @param row		����ģ�͵��к�(��0�п�ʼ����)
     * @param column	�ֶ�����
     * @author lindx 2008-06-10
     */
    public void setEditorAt(TableCellEditor editor, int row, String column) {

        //��������ȡ�ö�Ӧ�ı���ж���
        FBaseTableColumn tableColumn = this.getColumnById(column);

        if (tableColumn == null) {
            return;
        }
        tableColumn.setEditorAt(row, editor);

    }

    /**
     * ����ָ����Ԫ��ı༭�ؼ�
     * @param dataField     �ؼ�
     * @param row			����ģ�͵��к�(��0�п�ʼ����)
     * @param column		�ֶ�����
     */
    public void setDataFieldAt(AbstractDataField dataField, int row, String column) {

//		��������ȡ�ö�Ӧ�ı���ж���
        FBaseTableColumn tableColumn = this.getColumnById(column);

        if (tableColumn == null) {
            return;
        }
        //FBaseTableCellEditor editor = new FBaseTableCellEditor(dataField);

        //tableColumn.setEditorAt(row, editor);
    }

    /**
     * ���ñ��������
     * @param font		����
     * @param row		����ģ�͵��к�
     */
    public void setCellFont(Font font, int row) {
        this.getCellAttribute().setFont(font, row);
    }

    /**
     * ���ñ��Ԫ������
     * @param font  	����
     * @param row   	����ģ�͵��к�
     * @param column	����ģ�͵�������
     */
    public void setCellFont(Font font, int row, String column) {
        this.getCellAttribute().setFont(font, row, column);
    }

    /**
     * ���ñ���еı�����ɫ
     * @param color		��ɫ
     * @param row		����ģ�͵��к�
     */
    public void setCellBackgroup(Color color, int row) {
        this.getCellAttribute().setBackgroup(color, row);

    }

    /**
     * ���ñ��Ԫ��ı�����ɫ
     * @param color			��ɫ
     * @param row			����ģ�͵��к�
     * @param column		����ģ�͵�������
     */
    public void setCellBackgroup(Color color, int row, String column) {

        this.getCellAttribute().setBackgroup(color, row, column);

    }

    /**
     * ���ñ���е�ǰ����ɫ
     * @param color			��ɫ
     * @param row			����ģ�͵��к�
     */
    public void setCellForegroup(Color color, int row) {
        this.getCellAttribute().setForegroup(color, row);
    }

    /**
     * ���ñ��Ԫ���ǰ����ɫ
     * @param color			��ɫ
     * @param row			����ģ�͵��к�
     * @param column		����ģ�͵�������
     */
    public void setCellForegroup(Color color, int row, String column) {
        this.getCellAttribute().setForegroup(color, row, column);
    }

    /**
     * ���ñ������ʾ
     * @param tips  ��ʾ���
     * @param row	����ģ�͵��к�
     */
    public void setCellToolTip(String tips, int row) {
        //this.getCellAttribute().setToolTipText(tips, row);
    }

    /**
     * ����������е�Ԫ���������ã���ɫ�����塢����ʾ��
     *
     */
    public void clearCellAttribute() {
        this.getCellAttribute().clearAllAttribute();
    }

    /**
     * �������ʾ����ת��Ϊ����ģ�Ͷ�Ӧ��������
     * @param viewRow  ����к�
     * @return  ����ģ���к�
     * @author lindx 2008-06-10
     */
    protected int convertTableRowToModelRow(int tableRow) {

        //����ϼ��еĲ�����

        int viewRow = this.getSumRowModel().convertViewRowToModelRow(tableRow);

        tableRow = this.convertViewRowIndexToModel(viewRow);

        return tableRow;
    }

    public List<ListSelectionListener> getListeners() {
        return listeners;
    }
}
