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
 * Description: 表格控件
 * <p>
 * &nbsp;&nbsp;&nbsp;FBaseTable是一个在两个表格（一个活动，一个锁定）基础上扩展的控件，该控件支持#TableModel数据模型
 * <p>
 * &nbsp;&nbsp;&nbsp;左边的表格锁定，不能随滚动条的滚动而滚动，并且列不能拖拽，相反右边的活动表格可以随滚动条的滚动而滚动
 * <p>
 * &nbsp;&nbsp;&nbsp;并且列头可以拖拽。左右表格使用相同的选择模型ListSelectionModel所以可以同时选中，FBaseTable
 * <p>
 * &nbsp;&nbsp;&nbsp;具备以下功能:
 * <p>
 * &nbsp;&nbsp;&nbsp;1、锁定列功能，可以调用FBaseTable.lockColumn(int column)锁定指定列之前的所有列。
 * <p>
 * &nbsp;&nbsp;&nbsp;2、合计行（包括总计、小计）等显示的功能，其中小计是自动根据表格的数据进行计算的，同时可以调用用户接
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;口进行计算:
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;FBaseTableColumn.setSumRowCount(ISumRowCount)
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;其中总计数可以通过表格的列设置进去的：
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;FBaseTableColumn.setSumRowData(Number)
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;小计功能可以通过setShowSumRow(boolean)进行屏蔽
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;总计功能可以通过setShowSumRowAll(boolean)进行屏蔽
 * <p>
 * &nbsp;&nbsp;&nbsp;3、双击表头可以排序，目前还没有支持按照汉语拼音排序，也不支持按照显示值进行排序,排序功能可以通过
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp; setSortable(boolean)进行屏蔽。
 * <p>
 * &nbsp;&nbsp;&nbsp;4、单元格拖拽复制功能，和Excel功能很相似。
 * <p>
 * &nbsp;&nbsp;&nbsp;5、通过addColumn(FBaseTableColumn)方法增加表格列
 * <p>
 * &nbsp;&nbsp;&nbsp;6、行号支持,可用通过setShowRowNumber(boolean)进行屏蔽。
 * <p>
 * &nbsp;&nbsp;&nbsp;7、对单元格进行操作功能，如：setValueAt(Object value，int rowIndex, String
 * columnId)
 * <p>
 * &nbsp;&nbsp;&nbsp;8、隐藏列，可以通过setColumnVisible()设置指定列的显示属性
 * <p>
 * &nbsp;&nbsp;&nbsp;9、可以设置单元格选择模式和行选择模式，缺省行选择模式
 * <p>
 * &nbsp;&nbsp;&nbsp;10、调用setIsCheckboxAffectedByClickRow()可以设置选择列的复选框是否随着行的鼠标点击，而改变状态。
 * <p>
 * &nbsp;&nbsp;&nbsp;11、表格可以支持编辑状态，设置某一列是否可以编辑，可以调用
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;FBaseTableColumn.setEditable(boolean),
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;在编辑状态下(控件已经显示在界面上)
 * <p>
 * Copyright: 方正春元科技发展有限公司(c) 2000~2007
 * <p>
 * Company: 方正春元科技发展有限公司
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
     * 序列化版本ID
     */
    private static final long serialVersionUID = 5911944755350293276L;
    /**
     * 行号列的名称，固定值为"Row_No"。
     */
    public static final String ROWNUMBER_COLUMN_NAME = "Row_No";
    /**
     * 行号的宽度，固定值为40个像素。
     */
    public static final int ROWNUMBER_COLUMN_WIDTH = 40;
    /**
     * 是否表格是第一次加载
     */
    private static boolean isFirstLoad = true;
    /**
     * 是否显示合计行（小计）
     */
    private boolean showSumRow = false;
    /**
     * 是否显示合计行（总计）
     *
     * @author jerry
     */
    private boolean showSumRowAll = false;
    /**
     * 小计行是否置于所有数据顶部。如果是ture, 则在顶部；如果是false，则在底部
     */
    private boolean sumRowAtTop = true;
    /**
     * 小计行与合计行是否置于所有数据顶部。如果是ture, 则在顶部；如果是false，则在底部.
     */
    private boolean sumRowAllAtTop = true;
    /**
     * 小计行与合计行的位置关系，true为合计在小计之上，false为小计在合计之上
     */
    private boolean isAllTopPart = true;
    /**
     * 复选框是否随着点击行，进行选中或者不选中。
     */
    private boolean isCheckBoxAffectedByClickRow = true;
    /**
     * 是否显示行号的标志
     */
    private boolean showRowNumber = true;
    /**
     * 是否具备排序功能
     */
    private boolean sortable = true;
    /**
     * 是否根据据数据模型中指定的列，自动创建表格显示的列
     */
    private boolean autoCreateColumnsFromModel = false;
    /**
     * 是否是升序排序
     */
    protected boolean ascending = true;
    
    private int f_row_index = 0;//锁定网格前几行，双击表头不排序
    
    public void setF_row_index(int index){
        this.f_row_index = index;
    }
    /**
     * 是否显示选择列上的复选框
     */
    boolean isShowHeaderCheckBox = true;
    /**
     * 表格的边框，暂时采用滚动条的边框，这种设置可能导致风格切换时UI类加载时出错
     */
    private Border border = null;//new LineBorder(Color.red);//new JScrollPane().getBorder();
    /**
     * 左边锁定表格的滚动条
     */
    private JScrollPane lScrollPane = new JScrollPane();
    /**
     * 右边活动表格的滚动条
     */
    private JScrollPane rScrollPane = new JScrollPane();
    /**
     * 行选择监听器
     */
    private List<ListSelectionListener> listeners = new ArrayList();

    // 用于控制网格单元的颜色
    public Color getCellBackgroud(String fileName, Object cellValue, Object row_obj) {
        return null;
    }

    // 用于控制网格单元字体的颜色
    public Color getCellForegroud(String fileName, Object cellValue, Object row_obj) {
        return null;
    }

    // 用于控制网格单元字体
    public Font getCellFont(String fileName, Object cellValue, Object row_obj) {
        return null;
    }

    // 用于控制网格单元前缀
    public String getCellPrefix(String fileName, Object cellValue, Object row_obj) {
        return null;
    }
    /**
     * 左边被锁定的表格，锁定的表格不能拖动列
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
     * 右边活动的表格，活动表格是随滚动条滚动的，并且列可以拖动而改变位置
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
     * 左边的Panel
     */
    private JPanel leftPanel = new JPanel();
    /**
     * 左下角的Panel，表格锁定时，左下角一快灰色的区域
     */
    private JPanel leftBottomPanel = new JPanel();
    //lindx add 2008-06-10 表格单元格属性对象
    private FBaseTableCellAttribute cellAttribute;
    /**
     * 自定义的列模型
     *
     * @see FBaseTableColumnModel
     */
    private FBaseTableColumnModel columModel;
    protected String enterTo = "Right";
    // 选中题头的列
    protected int cur_column = -1;
    private boolean auto_insert = true;
    private JPanel pnlStat, southPanel;
    private LocalColumnPanel localColumnPanel;//定位列查询框
    private List<String> fieldHeaders = new ArrayList<String>();//用于定位列的字段信息
    private List<Integer> fieldColumnList = new ArrayList<Integer>();//定位列:字段信息查询到的结果集合
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
     * 定位到某一列
     *
     * @param field_name
     *            选择列名
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
     * 根据index定位网格第几列，滚动条随之滚动
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
     * 根据缺省模型构造
     */
    public FBaseTable() {
        this(new DefaultTableModel());
    }

    /**
     * 根据一个指定的模型构造
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
     * 初始化表格
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

        // 表格布局设置
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
                        val = "统计:";
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

        // 使得左右表格行选择同步，所以采用相同的列选择模型
        this.lockedTable.setSelectionModel(activeTable.getSelectionModel());
        // 左侧锁定表格，不能调整列的位置
        this.lockedTable.getTableHeader().setReorderingAllowed(false);
        // 左侧锁定表格，不能调整列的宽度
        this.lockedTable.getTableHeader().setResizingAllowed(false);
        // 左侧锁定表格的列，不从数据模型中创建，而是手工定义的。
        this.lockedTable.setAutoCreateColumnsFromModel(false);
        // 右侧活动表格的列，不从数据模型中创建，而是手工定义的。
        this.activeTable.setAutoCreateColumnsFromModel(false);
        // 屏蔽活动表格自动按照表格款段调整列的宽度
        this.activeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 设置左右表格都支持隔行多选
        this.activeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.lockedTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // 设置表格的行高
        this.setRowHeight(19);

        // 设置表格线条的颜色
        this.activeTable.setGridColor(new Color(172, 168, 153));
        this.lockedTable.setGridColor(new Color(172, 168, 153));

        // 设置表头的背景颜色
        this.activeTable.getTableHeader().setBackground(Color.WHITE);
        this.activeTable.getTableHeader().setBackground(Color.WHITE);

        // 设置表格滚动视图的背景颜色
        this.lScrollPane.getViewport().setBackground(Color.WHITE);
        this.rScrollPane.getViewport().setBackground(Color.WHITE);

        // 设置左右滚动面板都没有边框
        this.lScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.rScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // 设置左右滚动条连动
        this.lScrollPane.getVerticalScrollBar().setModel(
                rScrollPane.getVerticalScrollBar().getModel());

        // 设置表格边框
        this.setBorder(this.border);
        // 初始化表格模型
        this.initTableModel();
        // 初始化表格列模型
        this.initColumnModel();
        // 初始化表格事件
        this.initEventListeners();
        // 设置表格的行号缺省可见
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
     * 显示定位列查询框
     */
    public void addLocalColumnPanel() {
        localColumnPanel.setVisible(true);
        localColumnPanel.updateUI();
    }

    /**
     * 关闭定位列查询框
     */
    public void removeLocalColumnPanel() {
        localColumnPanel.setVisible(false);
        localColumnPanel.updateUI();
    }

    /**
     * 初始化表格模型
     */
    private void initTableModel() {
        // 表格模型初始化
        FBaseTableSorter sorter = new FBaseTableSorter();
        FBaseTableSumRowModel sumModel = new FBaseTableSumRowModel(sorter);


        this.activeTable.setModel(sumModel);

        FBaseTableRowNoModel rm = new FBaseTableRowNoModel(sumModel);

        // 设置合计行是否可见
        sumModel.setShowSumRow(this.isShowSumRow());
        rm.setShowSumRow(this.isShowSumRow());
        // 设置合计行是否置于所能数据的顶部
        sumModel.setSumRowAtTop(this.isSumRowAtTop());
        rm.setSumRowAtTop(this.isSumRowAtTop());
        /**
         * 设置总计行是否可见，总计行的位置，总计行与小计行的位置关系
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
     * 初始化列模型
     */
    private void initColumnModel() {
        // 列模型初始化
        this.columModel = new FBaseTableColumnModel();
        this.columModel.setLeftModel((DefaultTableColumnModel) lockedTable.getColumnModel());
        this.columModel.setRightModel((DefaultTableColumnModel) this.activeTable.getColumnModel());

        this.getSumRowModel().setColumnModel(this.columModel);
    }

    /**
     * 为表格增加表头鼠标事件监听器
     *
     * @param l
     *            鼠标事件监听器
     */
    public void addTableHeaderMouseListener(MouseListener l) {
        lockedTable.getTableHeader().addMouseListener(l);
        activeTable.getTableHeader().addMouseListener(l);
    }

    /**
     * 为表格体增加鼠标事件监听器
     *
     * @param l
     *            鼠标事件监听器
     */
    public void addTableBodyMouseListener(MouseListener l) {
        lockedTable.addMouseListener(l);
        activeTable.addMouseListener(l);
        // 在表格下方的空地方属于滚动面板，其上也需要增加鼠标事件监听器
        lScrollPane.addMouseListener(l);
        rScrollPane.addMouseListener(l);
    }

    /**
     * 初始化表格事件监听
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

        // 表头鼠标侦听
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
//                    // 点击表头复选框设置全选或全不选
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
//                        // 不存在选择框列
//                    }
//
//                    // 单击表头排序
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
//                                //Lindx 2008-05-22 排序处理集中在sorter中进行处理
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

        // 表体鼠标移动侦听器。
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
     * 设置表格行选中时，是否改变复选框的选中状态,true:受影响，false：不受影响
     *
     * @param isCheckBoxAffectedByClickRow
     */
    public void setIsCheckBoxAffectedByClickRow(
            boolean isCheckBoxAffectedByClickRow) {
        this.isCheckBoxAffectedByClickRow = isCheckBoxAffectedByClickRow;
    }

    /**
     * 设置是否显示表头上的复选框（选择列）。
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
     * 初始化行号列
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
     * 设置数据模型,如果是自动创建列，则根据模型创建表格的列 否则，使用用户自定义的列
     *
     * @param model
     *            表格的数据模型
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
     * 获得表格模型
     *
     * @return 表格模型
     */
    public TableModel getModel() {
        return getSorter().getModel();
    }

    /**
     * 获得排序模型
     *
     * @return 排序模型
     */
    public FBaseTableSorter getSorter() {
        return (FBaseTableSorter) getSumRowModel().getModel();
    }

    /**
     * 获得行号模型
     *
     * @return 行号模型
     */
    private FBaseTableRowNoModel getRowNumberModel() {
        if (this.lockedTable == null) {
            return null;
        }

        return (FBaseTableRowNoModel) lockedTable.getModel();

    }

    /**
     * 获得合计行模型
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
     * 判断是否存在合计行
     *
     * @return
     */
    public boolean isExistTotalRow() {
        for (int i = 0; i < this.getColumnCount(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) getColumnModel().getColumn(i);
            /**
             * 增加总计行
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
     * 对指定列进行排序,排序方式由ascending指定,true表示升序排序,false表示降序排序
     *
     * @param columnIndex
     *            是表格上的模型索引
     * @param ascending
     *            排序方式
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
     * 根据列名称来进行排序
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
     * 设置是否显示行号
     *
     * @param showRowNumber
     */
    public void setShowRowNumber(boolean showRowNumber) {
        this.showRowNumber = showRowNumber;
        this.updateRowNumberColumn();
    }

    /**
     * 判断行号是否显示
     *
     * @return
     */
    public boolean isShowRowNumber() {
        return this.showRowNumber;
    }

    /**
     * 获得左侧锁定的表格
     *
     * @return 返回左侧锁定的表格
     */
    public JTable getLeftLockedTable() {
        return lockedTable;
    }

    /**
     * 获得右侧活动的表格
     *
     * @return 返回右侧活动的表格
     */
    public JTable getRightActiveTable() {
        return activeTable;
    }

    /**
     * 锁定当前列以前的列
     *
     * @param index
     *            表格上视图的顺序号
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
     * 解锁,全部锁定的列被解锁.
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
     * 获得表格行的选择模型
     *
     * @return 表格行的选择模型
     */
    public ListSelectionModel getSelectionModel() {
        return activeTable.getSelectionModel();
    }

    /**
     * 设置表格行的选择模型
     *
     * @param model选择模型
     */
    public void setSelectionModel(ListSelectionModel model) {
        activeTable.setSelectionModel(model);
        lockedTable.setSelectionModel(model);
    }

    /**
     * 设置表格的行高
     *
     * @param height
     */
    public void setRowHeight(int height) {
        activeTable.setRowHeight(height);
        lockedTable.setRowHeight(height);
    }

    /**
     * 获得表格的行高
     *
     * @return 行高
     */
    public int getRowHeight() {
        return activeTable.getRowHeight();
    }

    /**
     * 获得表格的列数，不包括行号列
     *
     * @return 表格的列数
     */
    public int getColumnCount() {
        return lockedTable.getColumnCount() - 1 + activeTable.getColumnCount();
    }

    /**
     * 向表格中增加一列
     *
     * @param col
     */
    public void addColumn(FBaseTableColumn col) {

        this.getColumnModel().addColumn(col);

    }

    /**
     * 从表格中删除一列
     *
     * @param col
     */
    public void removeColumn(FBaseTableColumn col) {
        //if (col.isLocked())
        //return;
        //activeTable.removeColumn(activeTable.getColumn(col.getIdentifier()));
        //edit time 2008-07-18
        //保存序号列
        if (col.getId().equals(FBaseTable.ROWNUMBER_COLUMN_NAME)) {
            return;
        }
        FBaseTableColumnModel columnModel = this.getColumnModel();
        columnModel.removeColumn(col);
    }

    /**
     * 根据指定列的标识获得列对象
     *
     * @param identifier
     * @return 表格列
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
     * 获得当前高亮选中的数据，在表格中的顺序号，本顺序号从数据行开始计算。
     *
     * @return 当前行
     */
    public int getCurrentRowIndex() {

        /**
         * 对合计行进行支持
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
     * 返回高亮选中的第一行数据，在数据模型中顺序号，从0开始计算。
     *
     * @return 模型的顺序号
     */
    public int getSelectedRowModelIndex() {
        int index = this.getCurrentRowIndex();
        if (index < 0) {
            return -1;
        }
        return this.convertViewRowIndexToModel(index);
    }

    /**
     * 返回高亮选中的多行数据，在数据模型中顺序号，从0开始计算。
     *
     * @return 顺序号数组
     */
    public int[] getSelectedRowModelIndexes() {
        int[] viewIndex = activeTable.getSelectedRows();
        Vector v = new Vector();

        // 计算合计行的差行数

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
     * 返回表格的列模型,如果要对列的选中进行操作,可以调用此方法获取列模型
     *
     * @return
     */
    public FBaseTableColumnModel getColumnModel() {
        return this.columModel;
    }

    /**
     * 设置列模型
     *
     * @param model
     *            列模型
     */
    public void setColumnModel(FBaseTableColumnModel model) {
        this.columModel = model;
        this.getSumRowModel().setColumnModel(this.columModel);
        lockedTable.setColumnModel(columModel.getLeftModel());
        activeTable.setColumnModel(columModel.getRightModel());

    }

    /**
     * 将视图中的索引转化为模型的索引
     *
     * @param viewIndex,从合计行下的第一个数据行开始计算,从0开始.
     * @return 数据模型List中的索引.
     */
    public int convertViewRowIndexToModel(int viewIndex) {
        if (viewIndex < 0 || viewIndex >= this.getSortedIndex().length) {
            return -1;
        }
        return this.getSortedIndex()[viewIndex];
    }

    /**
     * 将模型的索引转化为视图的索引
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
     * 获得排序后的模型索引
     *
     * @return 模型索引的数组
     */
    public int[] getSortedIndex() {
        return this.getSorter().getSortedIndex();
    }

    /**
     * 上移当前选中行
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
     * 下移当前选中行
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
     * 设置高亮选定的行范围
     *
     * @param from
     *            开始行,从合计行开始计算
     * @param to
     *            结束行,从合计行开始计算
     */
    public void setRowSelectionInterval(int from, int to) {

        activeTable.setRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
        lockedTable.setRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));

    }

    /**
     * 追加高亮选定的行范围
     *
     * @param from
     *            开始行,从合计行开始计算
     * @param to
     *            结束行,从合计行开始计算
     */
    public void addRowSelectionInterval(int from, int to) {

        activeTable.addRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
        lockedTable.addRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
    }

    /**
     * 清除高亮选定的行范围
     *
     * @param from
     *            开始行,从合计行开始计算
     * @param to
     *            结束行,从合计行开始计算
     */
    public void removeRowSelectionInterval(int from, int to) {

        activeTable.removeRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
        lockedTable.removeRowSelectionInterval(convert2RealRowIndex(from),
                convert2RealRowIndex(to));
    }

    /**
     * 如果合计行位于所有数据顶部，在计算数据行的行下标时，要将合计行扣除在外。 即：从合计的下一行的开始，下标依次是0、1、2......。
     *
     * @param index
     * @return
     */
    private int convert2RealRowIndex(int index) {

        /**
         * 支持合计行
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
     * 全部高亮选定
     */
    public void selectAll() {
        activeTable.selectAll();
        lockedTable.selectAll();
    }

    /**
     * 清除全部高亮选定
     */
    public void clearSelection() {
        activeTable.clearSelection();
        lockedTable.clearSelection();
    }

    /**
     * 设置指定范围的行高亮显示，并将其复选框置为选中状态
     *
     * @param from
     *            开始行,从合计行开始计算
     * @param to
     *            结束行,从合计行开始计算
     * @param isSelected
     *            复选框是否选中
     */
    public void setCheckBoxSelectedInterval(int from, int to, boolean isSelected) {
        for (int i = convertViewRowIndexToModel(from); i <= convertViewRowIndexToModel(to); i++) {
            ((FTableModel) this.getModel()).setCheckBoxSelectedAtRow(i,
                    isSelected);
        }
//		int iRow =0;
//		for (int i = from; i <= to; i++) {
//			//根据合计行模型转换为界面行
//			iRow = this.getSumRowModel().convertModelRowToViewRow(i);
//			this.lockedTable.setRowSelectionInterval(iRow-1, iRow);
//			this.activeTable.setRowSelectionInterval(iRow-1, iRow);
//			
//			((FTableModel) this.getModel()).setCheckBoxSelectedAtRow(i,
//					isSelected);
//		}
    }

    /**
     * 增加鼠标事件侦听器,可以侦听整个表格的鼠标事件（表格行和表格下方空白处）
     *
     * @param l
     *            鼠标事件侦听器
     */
    public void addMouseListener(MouseListener l) {
        lockedTable.addMouseListener(l);
        activeTable.addMouseListener(l);
        lScrollPane.addMouseListener(l);
        rScrollPane.addMouseListener(l);
    }

    /**
     * 增加鼠标移动侦听器，可以侦听整个表格的鼠标事件（表格行和表格下方空白处）
     *
     * @param l
     *            鼠标事件侦听器
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
     * 判断一列是否可见
     *
     * @param col
     * @return
     */
    public boolean isColumnVisible(Object identifier) {
        return this.getColumn(identifier).isVisible();
    }

    /**
     * 根据一列的标识，设置该列是否可见。
     *
     * @param
     */
    public void setColumnVisible(Object identifier, boolean visible) {
        // 如果是行号列，直接返回
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
     * 获得所有已经显示的列
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
     * 获得所有已经显示的列
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
     * 获得所有已经显示的列
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
     * 重新调整左侧表的宽度
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
     * 为活动表创建缺省的列模型
     *
     * @param table
     */
    private void createTableColumnsFromModel() {

        // 清空左表除行号以外的列
        int cols = lockedTable.getColumnCount();
        for (int i = 1; i < cols; i++) {
            lockedTable.removeColumn(lockedTable.getColumnModel().getColumn(1));
        }

        // 清空右表所有的列
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
     * 设置表格行是否可见
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
     * 获得该列的编辑控件
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
     * 根据id获取指定的列对象,只在可见列中进行定位
     *
     * @param id
     *            列id
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
     * 自定义升序表头图标
     */
    protected static final Icon ASCENDING_ICON = new AscendingIcon();

    /**
     * 升序排序图标
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
     * 自定义降序表头图标
     */
    protected static final Icon DESCENDING_ICON = new DescendingIcon();

    /**
     * 降序排序的图标
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
     * 停止整个表格编辑状态，通常如果表格处在编辑状态，当焦点离开表格后，
     * <p>
     * 应该调用此方法停止表格的编辑状态，表格停止编辑状态后，数据被
     * <p>
     * 写入模型中。
     */
    public void stopEditing() {
        this.stopEditingForTable(this.getRightActiveTable());
        this.stopEditingForTable(this.getLeftLockedTable());
    }

    /**
     * 对指定表格停止编辑
     *
     * @param table
     *            表格（JTable）
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
     * 判断是否显示小计行
     *
     * @return
     */
    public boolean isShowSumRow() {
        return showSumRow;
    }

    /**
     * 设置是否显示小计行
     *
     * @param showSumRow
     */
    public void setShowSumRow(boolean showSumRow) {
        this.showSumRow = showSumRow;
        this.getSumRowModel().setShowSumRow(showSumRow);
        this.getRowNumberModel().setShowSumRow(showSumRow);
    }

    /**
     * 合计行是否在上面
     *
     * 表格不再支持合计行在下面
     * @return 合计行在上表示true，合计行在下表示false
     */
    public boolean isSumRowAtTop() {
        return sumRowAtTop;
    }

    /**
     * 合计行是否置于所有数据顶部。
     *
     * @param sumRowAtTop
     *            如果是ture, 则在顶部；如果是false，则在底部。
     */
    public void setSumRowAtTop(boolean sumRowAtTop) {
        this.sumRowAtTop = sumRowAtTop;
        this.getSumRowModel().setSumRowAtTop(sumRowAtTop);
        this.getRowNumberModel().setSumRowAtTop(sumRowAtTop);
    }

    /**
     * 重载更新UI的方法，主要是为了重画边框
     */
    public void updateUI() {
        super.updateUI();
        this.setBorder(this.border);
    }

    /**
     * 设置指定行和列的值
     *
     * @param value
     *            被设置的值
     * @param row
     *            行顺序号，从合计行开始计算
     * @param col
     *            列顺序号，从第一列开始计算
     */
    public void setValueAt(Object value, int row, String col) {
        int colIndex = this.getColumnModelIndex(col);
        this.getSumRowModel().setValueAt(value, row, colIndex);
    }

    /**
     * 获指定行和列的值
     *
     * @param row
     *            行顺序号，从合计行开始计算
     * @param col
     *            列顺序号，从第一列开始计算
     * @return
     */
    public Object getValueAt(int row, String col) {

        int colIndex = this.getColumnModelIndex(col);
        return this.getSumRowModel().getValueAt(row, colIndex);
    }

    /**
     * 根据列的ID，获得列的模型顺序号
     *
     * @param 列的ID
     * @return 返回该列的模型顺序号
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
     * 判断指定行和列的单元格是否可以编辑
     *
     * @param row
     *            行顺序号，从合计行开始计算
     * @param col
     *            列顺序号，从第一列开始计算
     * @return 是否可编辑
     */
    public boolean isCellEditable(int row, String col) {
        int colIndex = this.getColumnModelIndex(col);
        return this.getSumRowModel().isCellEditable(row, colIndex);

    }

    /**
     * 判断是否显示总计行。
     *
     * @return
     */
    public boolean isShowSumRowAll() {
        return showSumRowAll;
    }

    /**
     * 设置是否显示总计行
     *
     * @param showSumRowAll
     *            是否显示总计行
     */
    public void setShowSumRowAll(boolean showSumRowAll) {
        this.showSumRowAll = showSumRowAll;
        this.getSumRowModel().setShowSumRowAll(showSumRowAll);
        this.getRowNumberModel().setShowSumRowAll(showSumRowAll);
    }

    /**
     * 判断总计行是否在小计行的上面
     *
     * @return
     */
    public boolean isSumRowAllAtTop() {
        return sumRowAllAtTop;
    }

    /**
     * 设置总计行在小计行的上面
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
     * 是否单击行时，改变选择列中复选框的选中状态
     *
     * @return
     */
    public boolean isCheckBoxAffectedByClickRow() {
        return isCheckBoxAffectedByClickRow;
    }

    /**
     * 设置在单击行时，改变选择列中复选框的选中状态
     *
     * @param isCheckBoxAffectedByClickRow
     */
    public void setCheckBoxAffectedByClickRow(
            boolean isCheckBoxAffectedByClickRow) {
        this.isCheckBoxAffectedByClickRow = isCheckBoxAffectedByClickRow;
    }

    /**
     * 获得所有合计列的ID
     * @return	合计列的List
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
    //返回合计的数据
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
     * 取得表格的单元格的属性对象
     * @author lindx 2008-06-10
     * @return  FBaseTableCellAttribute 表格单元格属性定义对象
     */
    protected FBaseTableCellAttribute getCellAttribute() {
        if (this.cellAttribute == null) {
            this.cellAttribute = new FBaseTableCellAttribute();
        }
        return this.cellAttribute;

    }

    /**
     * 设置指定单元格的描绘器Render
     * @param renderer  描绘器Render
     * @param row    	数据模型的行号(从0行开始计算)
     * @param column  	字段列名
     * @author lindx 2008-06-10
     */
    public void setRenderAt(TableCellRenderer renderer, int row, String column) {

        //根据列名取得对应的表格列对象
        FBaseTableColumn tableColumn = this.getColumnById(column);

        if (tableColumn == null) {
            return;
        }
        tableColumn.setRenderAt(row, renderer);

    }

    /**
     * 设置指定单元格的编辑控件
     * @param editor  	编辑控件Editor
     * @param row		数据模型的行号(从0行开始计算)
     * @param column	字段列名
     * @author lindx 2008-06-10
     */
    public void setEditorAt(TableCellEditor editor, int row, String column) {

        //根据列名取得对应的表格列对象
        FBaseTableColumn tableColumn = this.getColumnById(column);

        if (tableColumn == null) {
            return;
        }
        tableColumn.setEditorAt(row, editor);

    }

    /**
     * 设置指定单元格的编辑控件
     * @param dataField     控件
     * @param row			数据模型的行号(从0行开始计算)
     * @param column		字段列名
     */
    public void setDataFieldAt(AbstractDataField dataField, int row, String column) {

//		根据列名取得对应的表格列对象
        FBaseTableColumn tableColumn = this.getColumnById(column);

        if (tableColumn == null) {
            return;
        }
        //FBaseTableCellEditor editor = new FBaseTableCellEditor(dataField);

        //tableColumn.setEditorAt(row, editor);
    }

    /**
     * 设置表格行字体
     * @param font		字体
     * @param row		数据模型的行号
     */
    public void setCellFont(Font font, int row) {
        this.getCellAttribute().setFont(font, row);
    }

    /**
     * 设置表格单元格字体
     * @param font  	字体
     * @param row   	数据模型的行号
     * @param column	数据模型的列名称
     */
    public void setCellFont(Font font, int row, String column) {
        this.getCellAttribute().setFont(font, row, column);
    }

    /**
     * 设置表格行的背景颜色
     * @param color		颜色
     * @param row		数据模型的行号
     */
    public void setCellBackgroup(Color color, int row) {
        this.getCellAttribute().setBackgroup(color, row);

    }

    /**
     * 设置表格单元格的背景颜色
     * @param color			颜色
     * @param row			数据模型的行号
     * @param column		数据模型的列名称
     */
    public void setCellBackgroup(Color color, int row, String column) {

        this.getCellAttribute().setBackgroup(color, row, column);

    }

    /**
     * 设置表格行的前景颜色
     * @param color			颜色
     * @param row			数据模型的行号
     */
    public void setCellForegroup(Color color, int row) {
        this.getCellAttribute().setForegroup(color, row);
    }

    /**
     * 设置表格单元格的前景颜色
     * @param color			颜色
     * @param row			数据模型的行号
     * @param column		数据模型的列名称
     */
    public void setCellForegroup(Color color, int row, String column) {
        this.getCellAttribute().setForegroup(color, row, column);
    }

    /**
     * 设置表格行提示
     * @param tips  提示语句
     * @param row	数据模型的行号
     */
    public void setCellToolTip(String tips, int row) {
        //this.getCellAttribute().setToolTipText(tips, row);
    }

    /**
     * 清除表格的所有单元格属性设置（颜色、字体、行提示）
     *
     */
    public void clearCellAttribute() {
        this.getCellAttribute().clearAllAttribute();
    }

    /**
     * 将表格显示的行转换为数据模型对应的数据行
     * @param viewRow  表格行号
     * @return  数据模型行号
     * @author lindx 2008-06-10
     */
    protected int convertTableRowToModelRow(int tableRow) {

        //计算合计行的差行数

        int viewRow = this.getSumRowModel().convertViewRowToModelRow(tableRow);

        tableRow = this.convertViewRowIndexToModel(viewRow);

        return tableRow;
    }

    public List<ListSelectionListener> getListeners() {
        return listeners;
    }
}
