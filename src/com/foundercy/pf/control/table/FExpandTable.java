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
 * <p>���ؼ�����չ�࣬�����JTable�̳У����Ӷ�����¼���֧�ֺͱ���ƹ��ܡ�
 * <p>���id������Ϣά����ǰ̨����
 * <p>����Ԫ��ѡ�еı߿�
 * @version 1.0
 * @author victor
 * @see com.foundercy.pf.control.table.FBaseTable
 * @since java 1.4.2
 */
public class FExpandTable extends JTable {

    public static final long serialVersionUID = -1L;
    private String id = "";
    //0:��ʾ��ͨѡ��״̬��1����ʾ������ק״̬
    private int state = 0;
    private final static float dash1[] = {1.0f};
    private final static BasicStroke dashed = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            1.0f, dash1, 0.0f);
    private int copyedRow = -1;//�����Ƶ���
    private int[] copyedCols = null;//�����Ƶ���
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
//                for (int j : this.getSelectedColumns()) {//ֻ�õ�ǰѡ��ĵ�Ԫ����ɫ�仯����ͻ��ѡ�У�ͬʱ���ѡ���б���ɫΪ����������
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
        //�����������������⣬�ڱ����ѡ��һ����Ԫ�񣬲����������뷨�����Դ�����Ԫ�����༭״̬
        this.addKeyListener(new KeyAdapter(){
        public void keyTyped(KeyEvent ev) {
        int row = getSelectedRow();
        int col =getSelectedColumn();
        if(col<0) return;
        //ȡ��ѡ���е�ID
        String colId = (String) getColumnModel().getColumn(col).getIdentifier();
        //���ڻس�����TAB����������Ԫ�����༭״̬
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
     * ��ȡ����Ӧid
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * ���ñ��id
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ����Ĭ�ϵ�ѡ��ģ��
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
     * ������������������������ƶ�ʱ����Ҫ�ػ�ѡ�е�Ԫ��
     * @param x ��Ҫ��������X����
     * @param y ��Ҫ��������Y����
     * @param w ��Ҫ�������Ŀ��
     * @param h ��Ҫ�������ĸ߶�
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
     * ��ѡ�еĵ�Ԫ��߿�
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
     * ����ʱˢ�½���
     */
    public void changeSelection(int rowIndex, int columnIndex,
            boolean toggle, boolean extend) {
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        //this.paintImmediately(this.getCellRect(rowIndex,columnIndex,true));
        repaint();
    }

    /**
     * ��������ƶ��¼����������ı�����״��
     * @param e ����ƶ��¼���
     */
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        //����ƶ�ʱ�����ѡ���к���
        if (e.getID() == MouseEvent.MOUSE_MOVED) {
            if (state == 0) {
                Rectangle rectRightBottom = this.getCrossAreaInSelectedCell();
                if (rectRightBottom != null && rectRightBottom.contains(e.getX(), e.getY())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    // ���ñ����Ƶĵ�Ԫ��
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
     * ��������¼�,�����Ԫ���϶����и�������.
     * @param e,����¼���
     */
    protected void processMouseEvent(MouseEvent e) {

        //������������קʮ�ּ�������ֱ�ӷ��أ��������"����"�¼����ݸ����
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            //���ѡ�е�Ԫ��ʮ�ּ�����
            Rectangle rectRightBottom = this.getCrossAreaInSelectedCell();
            if (rectRightBottom != null && rectRightBottom.contains(e.getX(), e.getY())) {
                return;
            }
        } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
            //�ڸ�����ק״̬���������ͷţ���ʼִ�и��Ʋ���
            if (state == 1) {
                //��ѡ�е��н������и���
                for (int col = 0; copyedCols != null && col < copyedCols.length; col++) {

                    //��ý�Ҫ������ʼ�ͽ�����
                    int rowStart = getSelectionModel().getMinSelectionIndex();
                    int rowEnd = getSelectionModel().getMaxSelectionIndex();

                    //��ñ����Ƶ�����
                    String colId = (String) getColumnModel().getColumn(copyedCols[col]).getIdentifier();
                    Object value = table.getValueAt(copyedRow, colId);
                    //����Ǻϼ��е����ݣ���Ӷ�����ȡ��ԭʼ���ݣ����и���
                    if (value instanceof SumRowValueTypes) {
                        value = ((SumRowValueTypes) value).getObject();
                    }
                    //���н��и�ֵ
                    for (int i = rowStart; i <= rowEnd; i++) {
                        //�����Ԫ���ǿ��Ա༭�ģ�����и��ƣ���������
                        if (table.isCellEditable(i, colId)) {
                            table.setValueAt(value, i, colId);
                        }
                    }
                }
                //������Ϻ����ñ���״̬��
                state = 0;
                //������Ϻ󣬶Ա�����ˢ����ʾ��
                repaint();
            }
        }
        super.processMouseEvent(e);
    }

    /**
     * ���ѡ�е�Ԫ�����½�ʮ�ּ�����.
     * @return ʮ�ּ�������ʮ�ּ��������ı�����״�����Ұ�����������϶�
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
