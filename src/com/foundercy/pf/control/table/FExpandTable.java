/*
 * @(#)FExpandTable	1.0 11/04/06
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control.table;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.*;

import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

/**
 * <p>表格控件的扩展类，该类从JTable继承，增加对中文录入的支持和表格复制功能。
 * <p>添加id方便消息维护和前台处理。
 * <p>画单元格选中的边框。
 * @version 1.0
 * @author victor
 * @see com.foundercy.pf.control.table.FBaseTable
 * @since java 1.4.2
 */
public class FExpandTable extends JTable {

    public static final long serialVersionUID = -1L;
    private String id = "";
    //0:表示普通选中状态，1：表示复制拖拽状态
    private int state = 0;
    private final static float dash1[] = {1.0f};
    private final static BasicStroke dashed = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            1.0f, dash1, 0.0f);
    private int copyedRow = -1;//被复制的行
    private int[] copyedCols = null;//被复制的列
    private FBaseTable table = null;

    protected FBaseTable getBaseTable() {
        return this.table;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component com = super.prepareRenderer(renderer, row, col);
        boolean isSelected = false;
        for (int i : this.getSelectedRows()) {
            if (i == row) {
//                for (int j : this.getSelectedColumns()) {//只让当前选择的单元格颜色变化，以突出选中，同时解决选择行背景色为高亮的问题
//                    if (j == col) {
                isSelected = true;
                break;
//                    }
//                }
            }
        }

        Color temp_forecolor = table.getForeground();
        Font font = null;
        FTableModel tmp_model = (FTableModel) table.getModel();
        Object cell_value = this.getValueAt(row, col);
        FBaseTableColumn column = ((FBaseTableColumn) this.getColumnModel().getColumn(col));
        String colId = column.getId();

        if (tmp_model != null) {
            Object row_obj = tmp_model.lazyFetch(row);
            temp_forecolor = table.getCellForegroud(colId, cell_value, row_obj);
            font = table.getCellFont(colId, cell_value, row_obj);
            String prefixStr = table.getCellPrefix(colId, cell_value, row_obj);
            if (com instanceof JTextComponent && prefixStr != null) {
                JTextComponent jtc = (JTextComponent) com;
                jtc.setText(prefixStr + jtc.getText());
            }
            if (font != null) {
                com.setFont(font);
            }
        }
        if (temp_forecolor == null) {
            temp_forecolor = Color.BLACK;
        }
        com.setForeground(temp_forecolor);
        if (isSelected) {
            com.setBackground(this.getSelectionBackground());
            if (com instanceof JPanel) {
                Component[] cs = ((JPanel) com).getComponents();
                for (Component c : cs) {
                    c.setBackground(this.getSelectionBackground());
                }
            }
            return com;
        }
        Color tmp_color = table.getBackground();
        if (tmp_model != null) {
            Object row_obj = tmp_model.lazyFetch(row);
            tmp_color = table.getCellBackgroud(colId, cell_value, row_obj);
        }
        /*if (((FTableModel) table.getModel()).getEntityClass() == null) {
        tmp_color = table.getCellBackgroud(((FTable)table).getFields().get(col), cell_value);
        } else*/
//            tmp_color = table.getCellBackgroud(((FBaseTableColumn) this.getColumnModel().getColumn(col)).getId(), cell_value);

        if (tmp_color == null) {
            boolean editable = false;
            List<String> disable_fields = tmp_model.getDisable_fields();
            if (disable_fields != null && disable_fields.contains(colId)) {
                editable = false;
            } else if (colId.contains(".") || colId.contains("#")) {
                editable = false;
            } else {
                boolean b_new_record = row >= tmp_model.getObjects().size();//.get(rowIndex) == black_object;
                if (b_new_record) {
                    editable = column.isEditable_when_new();
                } else {
                    editable = column.isEditable();
                }
            }
//            if(editable&&tmp_model.getEntityClass() != null){
//                editable = tmp_model.getObjects().size() > 0 && (UserContext.getObjRoleRight(tmp_model.getObjects().get(row), colId) >=0) ;
//            }
            if (!editable) {
                tmp_color = table.getBackground();
            } else {
                tmp_color = Color.WHITE;
            }
        }
        com.setBackground(tmp_color);
        return com;
    }

    ;

    public FExpandTable(final FBaseTable table) {
        this.table = table;
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!((FTableModel) table.getModel()).isEditable()) {
                    return;
                }
                if (FExpandTable.this.getEditorComponent() != null) {
                    if (FExpandTable.this.getEditorComponent() instanceof JTextComponent) {
                        ((JTextComponent) FExpandTable.this.getEditorComponent()).selectAll();
                        ((JTextComponent) FExpandTable.this.getEditorComponent()).requestFocus();
                    }
                }
//                FExpandTable.this.requestFocus();
            }
        });
        /*
        //解决表格中文输入问题，在表格中选中一个单元格，采用中文输入法，可以触发单元格进入编辑状态
        this.addKeyListener(new KeyAdapter(){
        public void keyTyped(KeyEvent ev) {
        int row = getSelectedRow();
        int col =getSelectedColumn();
        if(col<0) return;
        //取得选中列的ID
        String colId = (String) getColumnModel().getColumn(col).getIdentifier();
        //对于回车键和TAB键不触发单元格进入编辑状态
        if (ev.getID() == KeyEvent.KEY_TYPED && ev.getKeyChar() != '\n'
        && ev.getKeyChar() != '\t' && ev.getKeyCode() != KeyEvent.VK_UP
        && ev.getKeyCode() != KeyEvent.VK_DOWN
        && ev.getKeyCode() != KeyEvent.VK_LEFT
        && ev.getKeyCode() != KeyEvent.VK_RIGHT && !isEditing()
        && table.isCellEditable(row, colId)) {
        editCellAt(row, col);
        }
        if ((ev.getKeyChar() != '\n' && ev.getKeyChar() != '\t')
        && !ev.isControlDown() && table.isCellEditable(row, colId)) {
        JComponent editor = table.getColumnById(colId).getEditDataField()
        .getEditor();
        if (editor instanceof JComboBox) {
        if (((JComboBox) editor).isEditable()) {
        JTextField field = (JTextField) ((JComboBox) editor)
        .getEditor().getEditorComponent();
        field.dispatchEvent(ev);
        //field.setCaretPosition(field.getText().length());
        }
        }
        
        }
        }
        });
         */

    }

    /**
     * 获取表格对应id
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置表格id
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 设置默认的选择模型
     */
    protected ListSelectionModel createDefaultSelectionModel() {
        return new DefaultListSelectionModel() {

            private static final long serialVersionUID = 1L;

            protected void fireValueChanged(
                    int firstIndex,
                    int lastIndex,
                    boolean isAdjusting) {
                super.fireValueChanged(firstIndex, lastIndex, isAdjusting);
            }
        };
    }

    /**
     * 重载立即画函数，解决焦点移动时，需要重画选中单元格。
     * @param x 需要立即画的X坐标
     * @param y 需要立即画的Y坐标
     * @param w 需要立即画的宽度
     * @param h 需要立即画的高度
     */
    public void paintImmediately(int x, int y, int w, int h) {
        super.paintImmediately(x - 4, y - 4, w + 8, h + 8);
        //paintSelection(this.getGraphics());
    }

    public void paint(Graphics g) {
        super.paint(g);
        paintSelection(g);
    }

    /**
     * 画选中的单元格边框
     * @param g
     */
    private void paintSelection(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int minRow = this.getSelectionModel().getMinSelectionIndex();
        int maxRow = this.getSelectionModel().getMaxSelectionIndex();
        int minCol = this.getColumnModel().getSelectionModel().getMinSelectionIndex();
        int maxCol = this.getColumnModel().getSelectionModel().getMaxSelectionIndex();

        if (minRow < 0 || maxRow < 0 || minCol < 0 || maxCol < 0) {
            return;
        }

        Rectangle rectLeftTop = this.getCellRect(minRow, minCol, true);
        Rectangle rectRightBottom = this.getCellRect(maxRow, maxCol, true);

        Color oldColor = g2.getColor();
        g2.setColor(Color.BLACK);
        int x = (int) rectLeftTop.getX();
        int y = (int) rectLeftTop.getY();
        int w = (int) rectRightBottom.getWidth() + (int) rectRightBottom.getX() - (int) rectLeftTop.getX();
        int h = (int) rectRightBottom.getHeight() + (int) rectRightBottom.getY() - (int) rectLeftTop.getY();
        if (state == 0) {
            g2.drawLine(x - 2, y - 2, x + w, y - 2);
            g2.drawLine(x - 2, y - 2, x - 2, y + h);
            g2.drawLine(x - 2, y + h, x + w - 5, y + h);
            g2.drawLine(x + w, y - 2, x + w, y + h - 5);

            if (!this.isEditing()) {
                g2.drawLine(x, y, x + w - 2, y);
                g2.drawLine(x, y, x, y + h - 2);
                g2.drawLine(x, y + h - 2, x + w - 5, y + h - 2);
                g2.drawLine(x + w - 2, y, x + w - 2, y + h - 5);
            }
            g.fillRect(x + w - 3, y + h - 3, 5, 5);
            g2.setColor(this.getGridColor().darker().darker());

            g2.drawRect(x - 1, y - 1, w, h);
            g2.drawLine(x - 1 + w, y - 1 + h, x + w + 1, y - 1 + h);
            g2.drawLine(x - 1 + w, y - 1 + h, x - 1 + w, y + h + 1);
        } else {
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(dashed);
            g2.drawLine(x - 2, y - 2, x + w, y - 2);
            g2.drawLine(x - 2, y - 2, x - 2, y + h);
            g2.drawLine(x - 2, y + h, x + w, y + h);
            g2.drawLine(x + w, y - 2, x + w, y + h);

            g2.drawLine(x, y, x + w - 2, y);
            g2.drawLine(x, y, x, y + h - 2);
            g2.drawLine(x, y + h - 2, x + w, y + h - 2);
            g2.drawLine(x + w - 2, y, x + w - 2, y + h);

            g2.drawRect(x - 1, y - 1, w, h);
            g2.setStroke(oldStroke);
        }

        g2.setColor(oldColor);
    }

    /**
     * 换行时刷新界面
     */
    public void changeSelection(int rowIndex, int columnIndex,
            boolean toggle, boolean extend) {
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        //this.paintImmediately(this.getCellRect(rowIndex,columnIndex,true));
        repaint();
    }

    /**
     * 重载鼠标移动事件处理函数，改变光标形状。
     * @param e 鼠标移动事件。
     */
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        //鼠标移动时，获得选择行和列
        if (e.getID() == MouseEvent.MOUSE_MOVED) {
            if (state == 0) {
                Rectangle rectRightBottom = this.getCrossAreaInSelectedCell();
                if (rectRightBottom != null && rectRightBottom.contains(e.getX(), e.getY())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    // 设置被复制的单元格
                    copyedRow = getSelectionModel().getMaxSelectionIndex();
                    copyedCols = getSelectedColumns();
                } else {
                    setCursor(Cursor.getDefaultCursor());
                    copyedRow = -1;
                    copyedCols = null;
                }
            }
        } else if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
            if (getCursor() != Cursor.getDefaultCursor()) {
                state = 1;
            }
        }
    }

    /**
     * 重载鼠标事件,解决单元格拖动进行复制问题.
     * @param e,鼠标事件。
     */
    protected void processMouseEvent(MouseEvent e) {

        //如果鼠标点击在拖拽十字架区域，则直接返回，并不鼠标"按下"事件传递给表格。
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            //获得选中单元格十字架区域
            Rectangle rectRightBottom = this.getCrossAreaInSelectedCell();
            if (rectRightBottom != null && rectRightBottom.contains(e.getX(), e.getY())) {
                return;
            }
        } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
            //在复制拖拽状态，如果鼠标释放，则开始执行复制操作
            if (state == 1) {
                //对选中的列进行逐列复制
                for (int col = 0; copyedCols != null && col < copyedCols.length; col++) {

                    //获得将要复制起始和结束行
                    int rowStart = getSelectionModel().getMinSelectionIndex();
                    int rowEnd = getSelectionModel().getMaxSelectionIndex();

                    //获得被复制的数据
                    String colId = (String) getColumnModel().getColumn(copyedCols[col]).getIdentifier();
                    Object value = table.getValueAt(copyedRow, colId);
                    //如果是合计行的数据，则从对象中取得原始数据，进行复制
                    if (value instanceof SumRowValueTypes) {
                        value = ((SumRowValueTypes) value).getObject();
                    }
                    //逐行进行赋值
                    for (int i = rowStart; i <= rowEnd; i++) {
                        //如果单元格是可以编辑的，则进行复制，否则跳过
                        if (table.isCellEditable(i, colId)) {
                            table.setValueAt(value, i, colId);
                        }
                    }
                }
                //复制完毕后，设置表格的状态。
                state = 0;
                //复制完毕后，对表格进行刷新显示。
                repaint();
            }
        }
        super.processMouseEvent(e);
    }

    /**
     * 获得选中单元格右下角十字架区域.
     * @return 十字架区域，在十字架区域，鼠标改变光标形状，并且按下鼠标后可以拖动
     */
    private Rectangle getCrossAreaInSelectedCell() {
        int maxRow = getSelectionModel().getMaxSelectionIndex();
        int maxCol = getColumnModel().getSelectionModel().getMaxSelectionIndex();
        if (maxRow < 0 || maxCol < 0) {
            return null;
        }

        Rectangle rect = getCellRect(maxRow, maxCol, true);
        Rectangle rectRightBottom = new Rectangle(rect.x + rect.width - 5, rect.y + rect.height - 5, 10, 10);
        return rectRightBottom;
    }
}
