package com.foundercy.pf.control.table;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.foundercy.pf.control.AbstractDataField;
import com.foundercy.pf.control.AbstractRefDataField;
import com.foundercy.pf.control.Compound;
import com.foundercy.pf.control.Control;
import com.foundercy.pf.control.FCheckBox;
import com.foundercy.pf.control.FComboBox;
//import com.foundercy.pf.control.FDecimalField;
//import com.foundercy.pf.control.FIntegerField;
import com.foundercy.pf.control.FRadioGroup;
import com.foundercy.pf.control.FormatManager;
import com.foundercy.pf.control.RefModel;
import com.foundercy.pf.control.RefModelFactory;
//import com.foundercy.pf.control.TreeAssistRefModel;
import com.foundercy.pf.control.UIControl;

/**
 * <p>
 * Title: 表格列，实现了复合列
 * </p>
 * <p>
 * Description:表格列，实现了复合列
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company:北京方正春元科技发展有限公司
 * </p>
 * 
 * @author fangyi
 * @author 黄节 2008年4月10日整理
 * @version 1.0
 */
public class FBaseTableColumn extends TableColumn implements UIControl,
        Compound {

    private static final long serialVersionUID = -2073957592742841133L;
    /**
     * 父控件
     */
    private Control parentControl;
    /**
     * 排列方式，靠左
     */
    public static final String LEFT = "left";
    /**
     * 排列方式，靠右
     */
    public static final String RIGHT = "right";
    /**
     * 排列方式，居中
     */
    public static final String CENTER = "center";
    /**
     * 默认最小列宽度
     */
    public static final int DEFAULT_MIN_COLWIDTH = 20;
    /**
     * 默认列宽度
     */
    public static final int DEFAULT_COLWIDTH = 100;
    // public static final int DEFAULT_SHOW_COLWIDTH = 100;
    /**
     * 默认最大列宽度
     */
    public static final int DEFAULT_MAX_COLWIDTH = Integer.MAX_VALUE;
    /**
     *
     * @uml.property name="locked" multiplicity="(0 1)"
     */
    // 是否是锁定的，初始化时设置为true不起作用
    private boolean locked = false;
    /**
     *
     * @uml.property name="editable" multiplicity="(0 1)"
     */
    // 是否可编辑
    private boolean editable = false;
    private boolean is_must_input = false;
    
    /*
     * 是否大文本
     */
    private boolean isLargeText = false;
    
    /**
     *
     * @uml.property name="sortable" multiplicity="(0 1)"
     */
    // 是否可排序
    private boolean sortable = true;
    /**
     *
     * @uml.property name="subColumns"
     * @uml.associationEnd elementType="xingx.smartbiz.control.table.NLTableColumn"
     *                     multiplicity= "(0 -1)"
     */
    private Vector subColumns = new Vector();
    /**
     * 是否显示小计值
     */
    private boolean isTotal = false;
    /**
     * 是否显示总计行值
     *
     * @author jerry
     */
    private boolean isAllTotal = false;
    /**
     * 合计行合计算法接口对象，用于自制合计算法
     *
     * @author jerry
     */
    private ISumRowCount sumRowCount = null;
    /**
     * 支持列对象编辑属性切换
     *
     * @author jerry
     */
    private FTableModel tableModel = null;
    /**
     * 总计初始值，当列需要显示总计时，对其进行覆值，默认为0
     *
     * @author jerry
     */
    private Number sumRowData = null;
    /**
     * 用于计算总计行总数
     *
     * @author jerry
     */
    private Number sumRowDataOld = null;
    private boolean editable_when_new; //如果是新记录是否可编辑

    public boolean isEditable_when_new() {
        return editable_when_new;
    }

    public void setEditable_when_new(boolean editable_when_new) {
        this.editable_when_new = editable_when_new;
    }

    /**
     * 获取统计数据
     *
     * @return 统计的数据
     *
     */
    public Number getSumRowData() {
        return sumRowData;
    }

    /**
     * 设置统计数据
     *
     * @param sumRowData
     *            数据
     *
     */
    public void setSumRowData(Number sumRowData) {
        this.sumRowData = sumRowData;
    }

    /**
     * 获取合计行合计算法接口对象
     *
     * @return 合计行合计算法接口
     *
     */
    public ISumRowCount getSumRowCount() {
        return sumRowCount;
    }

    /**
     * 设置合计行合计算法接口对象
     *
     * @param sumRowCount
     *            合计行合计算法接口
     *
     */
    public void setSumRowCount(ISumRowCount sumRowCount) {
        this.sumRowCount = sumRowCount;
    }

    /**
     * 构造函数
     */
    public FBaseTableColumn() {
        init();
    }

    private void init() {
        // 设置缺省的标识
        this.setIdentifier("");
        this.setHeaderValue(this.getIdentifier());
        DefaultTableCellRenderer headcr = new FBaseTableHeaderRenderer();
        headcr.setHorizontalTextPosition(JLabel.LEFT);
        this.setHeaderRenderer(headcr);

//        this.setCellRenderer(new FBaseTableCellRenderer());
        //this.setCellRenderer(new FBaseTableRowCellRenderer(new FBaseTableCellRenderer()));
        //this.setCellEditor(new FBaseTableRowCellEditor(new FBaseTableCellEditor()));
        //this.setCellEditor(new FBaseTableCellEditor());

        this.setMaxWidth(FBaseTableColumn.DEFAULT_MAX_COLWIDTH);
        this.setMinWidth(FBaseTableColumn.DEFAULT_MIN_COLWIDTH);
        // 缺省宽度
        this.setPreferredWidth(this.width);
        this.width = FBaseTableColumn.DEFAULT_COLWIDTH;
    }

    /**
     * 获取所有列下的控件
     *
     * @return 控件列表
     *
     */
    public List getSubControls() {
        ArrayList list = new ArrayList();
        list.add(this.getBodyEditor().getComponent());
        return list;
        // return new Control[] {
        // (Control)this.getBodyEditor().getComponent()};
    }

    /**
     * 增加子控件
     *
     * @param control
     *            FPanel中的子控件
     */
    public void addControl(Control control) {

        // 加入子列
        if (control instanceof FBaseTableColumn) {
            this.subColumns.add(control);
        } else // 增加一个输入控件时，更新CellRenderer,CellEditor;
        if (control instanceof AbstractDataField) {

            FBaseTableCellEditor ce = this.getBodyEditor();
            ce.setDataField((AbstractDataField) control);

            FBaseTableCellRenderer cr = this.getBodyRenderer();

            if (control instanceof FRadioGroup || control instanceof FCheckBox) {
                cr.setDataField((AbstractDataField) control);
                return;
            }

            // 设置缺省的参照模型。
            if (cr.getRefModel() == null) {
                RefModel model = null;
                if (control instanceof FComboBox) {
                    model = ((FComboBox) control).getRefModel();
                }
                if (control instanceof AbstractRefDataField) {
                    model = ((AbstractRefDataField) control).getRefModel();
                    //if (model instanceof TreeAssistRefModel) {
                    // ((TreeAssistRefModel) model).excuteQuery();
                    //}
                }
                cr.setRefModel(model);
            }
        }
        control.setParentControl(this);
    }

    /**
     * 获取排布类型（暂时不用）
     *
     * @return null
     *
     */
    public String getLayoutType() {
        return null;
    }

    /**
     * 获取父控件
     *
     * @return 父控件
     *
     */
    public AbstractDataField getEditDataField() {
        return (AbstractDataField) this.getBodyEditor().getComponent();
    }

    /**
     * 增加子控件
     *
     * @param field
     *            子控件
     *
     */
    public void setEditDataField(AbstractDataField field) {
        this.addControl(field);
    }

    /**
     * 判断控件是否可以编辑
     *
     * @return false为不可编辑，true为可编辑
     */
    public boolean isEditable() {
        return this.editable;
    }

    /**
     * 设置控件是否可编辑，由于父类JComponent无此方法，因此进行重载
     *
     * @param editable
     *            false为不可编辑，true为可编辑
     */
    public void setEditable(boolean editable) {
        
        this.editable = editable;
        /**
         * 当列对象初始化后，编辑属性可切换，改动此处
         *
         * @author jerry
         */
        /** ******************begin******************* */
        if (this.tableModel != null) {
            if (this.tableModel.getColumnList().size() != 0 && this.getTableModel().getColumnList() != null) {
                for (int i = 0; i < this.tableModel.getColumnList().size(); i++) {
                    if (this.getId().equals(
                            ((FTableColumnItem) this.tableModel.getColumnList().get(i)).getId())) {
                        ((FTableColumnItem) this.tableModel.getColumnList().get(i)).setEditable(editable);                        
                    }
                }
            }
        }
        /** ******************end********************* */
    }
      
    /**
     * 查询是否可排序
     *
     * @return true－可排序，false－不可排序
     *
     */
    public boolean isSortable() {
        return this.sortable;
    }

    /**
     * 设置是否可排序
     *
     * @param sortable
     *            true－可排序，false－不可排序
     *
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * 查询是否可见
     *
     * @return true-可见，false－不可见
     *
     */
    public boolean isVisible() {
        if (this.getWidth() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 设置是否可见
     *
     * @param visible
     *            true-可见，false－不可见
     *
     */
    public void setVisible(boolean visible) {
        if (visible) {
            this.setMaxWidth(FBaseTableColumn.DEFAULT_MAX_COLWIDTH);
            this.setMinWidth(FBaseTableColumn.DEFAULT_MIN_COLWIDTH);
            int width = getWidth() == 0 ? FBaseTableColumn.DEFAULT_COLWIDTH
                    : getWidth();
            this.setPreferredWidth(width);
            this.setWidth(width);
            this.setResizable(true);
        } else {
            this.setMaxWidth(0);
            this.setMinWidth(0);
            this.setPreferredWidth(0);
            this.setWidth(0);
            this.setResizable(false);
        }
    }

    /**
     * 获取文字水平排序方式
     *
     * @return "left"－靠左，“right”－靠右，“center”－居中
     *
     */
    public String getAlignment() {
        int align = this.getBodyRenderer().getTextAlignment();
        if (align == JLabel.CENTER) {
            return FBaseTableColumn.CENTER;
        }
        if (align == JLabel.LEFT) {
            return FBaseTableColumn.LEFT;
        }
        if (align == JLabel.RIGHT) {
            return FBaseTableColumn.RIGHT;
        }
        return null;
    }

    /**
     * 设置文本内容水平对齐方式
     *
     * @param alignment
     *            FBaseTableColumn.LEFT－左对齐；FBaseTableColumn.RIGHT－右对齐；FBaseTableColumn.CENTER－居中对齐
     */
    public void setAlignment(String alignment) {
        if (alignment == null) {
            return;
        }
        if (alignment.equalsIgnoreCase(LEFT)) {
            this.getBodyRenderer().setTextAlignment(JLabel.LEFT);
            this.getBodyEditor().setTextAlignment(JTextField.LEFT);
        }
        if (alignment.equalsIgnoreCase(RIGHT)) {
            this.getBodyRenderer().setTextAlignment(JLabel.RIGHT);
            this.getBodyEditor().setTextAlignment(JTextField.RIGHT);
        }
        if (alignment.equalsIgnoreCase(CENTER)) {
            this.getBodyRenderer().setTextAlignment(JLabel.CENTER);
            this.getBodyEditor().setTextAlignment(JTextField.CENTER);
        }
    }

    /**
     * 获取表头水平排序方式
     *
     * @return "left"－靠左，“right”－靠右，“center”－居中
     *
     */
    public String getHeadAlignment() {
        int align = ((DefaultTableCellRenderer) this.getHeaderRenderer()).getHorizontalAlignment();
        if (align == JLabel.CENTER) {
            return FBaseTableColumn.CENTER;
        }
        if (align == JLabel.LEFT) {
            return FBaseTableColumn.LEFT;
        }
        if (align == JLabel.RIGHT) {
            return FBaseTableColumn.RIGHT;
        }
        return null;
    }

    /**
     * 设置表头水平对齐方式
     *
     * @param alignment
     *            FBaseTableColumn.LEFT－左对齐；FBaseTableColumn.RIGHT－右对齐；FBaseTableColumn.CENTER－居中对齐
     */
    public void setHeadAlignment(String alignment) {
        if (alignment == null) {
            return;
        }

        if (alignment.equalsIgnoreCase(LEFT)) {
            this.getHeadRenderer().setHorizontalAlignment(JLabel.LEFT);
        }
        if (alignment.equalsIgnoreCase(RIGHT)) {
            this.getHeadRenderer().setHorizontalAlignment(JLabel.RIGHT);
        }
        if (alignment.equalsIgnoreCase(CENTER)) {
            this.getHeadRenderer().setHorizontalAlignment(JLabel.CENTER);
        }

    }

    /**
     * 获取背景色
     *
     * @return 颜色对象
     *
     */
    public java.awt.Color getBackground() {
        return this.getBodyRenderer().getBackColor();
    }

    /**
     * 设置背景色对象
     *
     * @param color
     *            color是一个十六进制数据如"#FF00EE".
     */
    public void setBackground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getBodyRenderer().setBackColor(c);
        } catch (Exception ex) {
        }
    }

    /**
     * 获取前景色对象
     *
     * @return 颜色对象
     *
     */
    public java.awt.Color getForeground() {
        return this.getBodyRenderer().getForeColor();
    }

    /**
     * 设置背景色对象
     *
     * @param color
     *            color是一个十六进制数据如"#FF00EE".
     */
    public void setForeground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getBodyRenderer().setForeColor(c);
        } catch (Exception ex) {
        }
    }

    /**
     * 获取表头颜色对象
     *
     * @return 颜色对象
     *
     */
    public java.awt.Color getHeadBackground() {
        return ((DefaultTableCellRenderer) this.getHeaderRenderer()).getBackground();
    }

    /**
     * 设置表头颜色
     *
     * @param color
     *            颜色字符串
     *
     */
    public void setHeadBackground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getHeadRenderer().setBackground(c);
        } catch (Exception ex) {
        }
    }

    /**
     * 获取表头前景色对象
     *
     * @return 颜色对象
     *
     */
    public java.awt.Color getHeadForeground() {
        return ((DefaultTableCellRenderer) this.getHeaderRenderer()).getForeground();
    }

    /**
     * 设置表头前景色
     *
     * @param color
     *            颜色字符串
     *
     */
    public void setHeadForeground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getHeadRenderer().setForeground(c);
        } catch (Exception ex) {
        }
    }

    /**
     * 查询表格边框是否是粗体描绘
     *
     * @return true－是，false－否
     *
     */
    public boolean isBold() {
        return this.getBodyRenderer().isBold();
    }

    /**
     * 设置表格边框是否是粗体描绘
     *
     * @param bold
     *            true－是，false－否
     *
     */
    public void setBold(boolean bold) {
        this.getBodyRenderer().setBold(bold);
    }

    /**
     * 查询表头是否是粗体
     *
     * @return true－是，false－否
     *
     */
    public boolean isHeadBold() {
        return this.getHeadRenderer().isBold();

    }

    /**
     * 设置表头是否是粗体
     *
     * @param bold
     *            true－是，false－否
     *
     */
    public void setHeadBold(boolean bold) {
        this.getHeadRenderer().setBold(bold);
    }

    /**
     * 设置是否显示小计值
     *
     * @param isTotal
     *            true－是，false－否
     *
     */
    public void setIsTotal(boolean isTotal) {
        this.isTotal = isTotal;
    }

    /**
     * 查询是否显示小计值
     *
     * @return true－是，false－否
     *
     */
    public boolean isTotal() {
        return this.isTotal;
    }

    /**
     * 设置id
     *
     * @param id
     *            设置的id
     */
    public void setId(String id) {
        this.setIdentifier(id);
        // this.setHeaderValue(id);
    }

    /**
     * 获取id
     *
     * @return id
     */
    public String getId() {
        return this.getIdentifier().toString();
    }

    /**
     * 设置标题
     *
     * @param title
     *            标题
     */
    public void setTitle(String title) {
        this.getHeadRenderer().setTitle(title);
        // this.updateHeader();
        this.setHeaderValue(title);
    }

    /**
     * 获取标题
     *
     * @return 标题
     *
     */
    public String getTitle() {
        return ((FBaseTableHeaderRenderer) this.getHeaderRenderer()).getTitle();
    }

    /**
     * 设置标题是否可见
     *
     * @param titleVisible
     *            true－是，false－否
     */
    public void setTitleVisible(boolean titleVisible) {
//        ((FBaseTableHeaderRenderer) this.getHeaderRenderer()).setTitleVisible(titleVisible);
    }

    /**
     * 判断标题是否可见
     *
     * @return true－是，false－否
     */
    public boolean isTitleVisible() {
        return true;
//        return this.getHeadRenderer().isTitleVisible();
    }

    /**
     * 设置数据模型
     *
     * @param model
     *            数据模型
     */
    public void setRefModel(RefModel model) {
        this.getBodyRenderer().setRefModel(model);
    }

    /**
     * 设置数据模型
     *
     * @param modelClass
     *            数据模型类
     */
    public void setRefModel(String modelClass) {
        if (modelClass != null) {
            try {
                this.getBodyRenderer().setRefModel(
                        RefModelFactory.getRefModel(modelClass));
            } catch (Exception ex) {
                System.out.println("表格列数据参照模型配置不正确。");
            }
        }

    }

    /**
     * 获取数据模型
     *
     * @return 数据模型对象
     *
     */
    public String getRefModel() {
        if (this.getBodyRenderer().getRefModel() != null) {
            return this.getBodyRenderer().getRefModel().getClass().getName();
        }
        return null;
    }

    protected FBaseTableHeaderRenderer getHeadRenderer() {
        return (FBaseTableHeaderRenderer) this.getHeaderRenderer();
    }

    protected FBaseTableCellRenderer getBodyRenderer() {

        if (super.getCellRenderer() instanceof FBaseTableRowCellRenderer) {
            FBaseTableRowCellRenderer renderer = (FBaseTableRowCellRenderer) super.getCellRenderer();
            return (FBaseTableCellRenderer) renderer.defaultRenderer;
        } else {
            return (FBaseTableCellRenderer) super.getCellRenderer();
        }
    }

    protected FBaseTableCellEditor getBodyEditor() {

        if (super.getCellEditor() instanceof FBaseTableRowCellEditor) {
            FBaseTableRowCellEditor editor = (FBaseTableRowCellEditor) super.getCellEditor();
            return (FBaseTableCellEditor) editor.defaultEditor;
        } else {
            return (FBaseTableCellEditor) super.getCellEditor();
        }

    }

    /**
     * 设置是否是锁定的
     *
     * @param locked
     *            true－是，false－否
     *
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * 查询是否是锁定的
     *
     * @return true－是，false－否
     *
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * 设置数据显示格式
     *
     * @param format
     *            local_currency,assist_currency,integer,decimal,percent,permill
     */
    public void setFormat(String format) {
        // 设置数据格式。
        // 系统预定义的数据格式，如百分比/日期格式/数字的分割符号/币种等，有待进一步开发。
        if (FormatManager.LOCAL_CURRENCY.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getLocalCurrencyFormat());
        } else if (FormatManager.ASSIST_CURRENCY.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getAssistCurrencyFormat());
        } else if (FormatManager.INTEGER_NUMBER.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getIntegerNumberFormat());
            //this.getBodyEditor().setDataField(new FIntegerField());
        } else if (FormatManager.DECIMAL_NUMBER.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getDecimalNumberFormat());
            //this.getBodyEditor().setDataField(new FDecimalField());
        } else if (FormatManager.PERCENT.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(FormatManager.getPercentFormat());
        } else if (FormatManager.PERMILL.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(FormatManager.getPermillFormat());
        }
    }

    /**
     * 根据控件id获取控件
     *
     * @param controlID
     *            控件id
     * @return 控件
     *
     */
    public Control getSubControlByID(String controlID) {
        if (this.getEditDataField().getId().equalsIgnoreCase(controlID)) {
            return this.getEditDataField();
        }
        return null;
    }

    /**
     * 获得子表格列
     *
     * @return 子表格列
     *
     */
    public FBaseTableColumn[] getSubTableColumns() {
        if (this.subColumns.size() == 0) {
            return null;
        }
        FBaseTableColumn[] columns = new FBaseTableColumn[this.subColumns.size()];

        this.subColumns.copyInto(columns);
        return columns;
    }

    /**
     * 获得所有的子表格列
     *
     * @return 子表格列
     *
     */
    public FBaseTableColumn[] getAllSubTableColumns() {

        if (subColumns.size() == 0) {
            return new FBaseTableColumn[]{this};
        }

        Vector v = new Vector();
        for (int i = 0; i < subColumns.size(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) subColumns.get(i);
            FBaseTableColumn[] subs = col.getAllSubTableColumns();
            for (int j = 0; j < subs.length; j++) {
                v.add(subs[j]);
            }
        }

        FBaseTableColumn[] cols = new FBaseTableColumn[v.size()];
        v.copyInto(cols);
        return cols;
    }

    /**
     * 获取列的层次
     *
     * @return 列的层次
     *
     */
    public int getColumnLayers() {
        FBaseTableColumn[] cols = this.getSubTableColumns();
        int max = 1;
        for (int i = 0; cols != null && i < cols.length; i++) {
            int layers = cols[i].getColumnLayers() + 1;
            if (layers > max) {
                max = layers;
            }
        }
        return max;
    }

    /**
     * 获取父控件
     *
     * @return 父控件
     *
     */
    public Control getParentControl() {
        return parentControl;
    }

    /**
     * 设置父控件
     *
     * @param parentControl
     *            父控件
     */
    public void setParentControl(Control parentControl) {
        this.parentControl = parentControl;
    }

    /**
     * 根据id获取所有子控件
     *
     * @param id
     *            控件id
     *
     * @return 控件
     *
     */
    public Control getSubControl(String id) {

        if (id == null) {
            return null;
        }

        for (int i = 0; i < this.subColumns.size(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) this.subColumns.get(i);
            if (id.equalsIgnoreCase((String) col.getIdentifier())) {
                return col;
            }
        }
        return null;
    }

    /**
     * 增加子控件,同时增加约束
     *
     * @param control
     *            子控件
     * @param contraint
     *            控件承载容器
     */
    public void addControl(Control control, Object contraint) {
    }

    /**
     * 获取表格的数据模型
     *
     * @return 表格的数据模型
     *
     */
    public FTableModel getTableModel() {
        return tableModel;
    }

    /**
     * 设置表格的数据模型
     *
     * @param tableModel
     *            表格的数据模型
     *
     */
    public void setTableModel(FTableModel tableModel) {
        this.tableModel = tableModel;
    }

    /**
     * 获取计算总计的行总数
     *
     * @return 计算总计的行总数
     *
     */
    public Number getSumRowDataOld() {
        return sumRowDataOld;
    }

    /**
     * 设置计算总计的行总数
     *
     * @param sumRowDataOld
     *            计算总计的行总数
     *
     */
    public void setSumRowDataOld(Number sumRowDataOld) {
        this.sumRowDataOld = sumRowDataOld;
    }

    /**
     * 查询是否显示总计行值
     *
     * @return true－显示 ，false－不显示
     *
     */
    public boolean isAllTotal() {
        return isAllTotal;
    }

    /**
     * 设置是否显示总计行值
     *
     * @param isAllTotal
     *            true－显示 ，false－不显示
     *
     */
    public void setAllTotal(boolean isAllTotal) {
        this.isAllTotal = isAllTotal;
    }

    /**
     * 取得表头颜色
     *
     * @return Color 表头颜色
     * @author jerry
     */
    public Color getTitleColor() {
        return this.getHeadForeground();
    }

    /**
     * 设置表头颜色
     *
     * @param color
     *            表头颜色
     *
     * @author jerry
     */
    public void setTitleColor(String color) {
        this.setHeadForeground(color);

    }

    /**
     * 判断是否必须录入
     * @return
     */
    public boolean is_must_input() {
        return this.is_must_input;
    }

    /**
     * 设置是否必须录入
     * @param is_must_input
     */
    public void setIs_must_input(boolean is_must_input) {
        this.is_must_input = is_must_input;
    }

    /**
     * 设置行单元格描绘器
     * @param row 数据模型的行号
     * @param _renderer  描绘器
     * @author lindx 2008-06-10
     */
    protected void setRenderAt(int row, TableCellRenderer _renderer) {

        FBaseTableRowCellRenderer renderer = (FBaseTableRowCellRenderer) super.getCellRenderer();

        renderer.setRendererAt(row, _renderer);

    }

    /**
     * 设置行单元格编辑控件
     * @param row 数据模型的行号
     * @param _editor  编辑控件
     * @author lindx 2008-06-10
     */
    protected void setEditorAt(int row, TableCellEditor _editor) {
        FBaseTableRowCellEditor editor = (FBaseTableRowCellEditor) super.getCellEditor();
        editor.setEditorAt(row, _editor);

    }

    /**
     * 重写该方法
     */
    public TableCellRenderer getCellRenderer() {
        if (super.getCellRenderer() instanceof FBaseTableRowCellRenderer) {
            FBaseTableRowCellRenderer renderer = (FBaseTableRowCellRenderer) super.getCellRenderer();
            return renderer.defaultRenderer;
        } else {
            return super.getCellRenderer();
        }
    }

    public void setLargeText(boolean isLargeText) {
        this.isLargeText = isLargeText;
        
        if (this.tableModel != null) {
            if (this.tableModel.getColumnList().size() != 0 && this.getTableModel().getColumnList() != null) {
                for (int i = 0; i < this.tableModel.getColumnList().size(); i++) {
                    if (this.getId().equals(
                            ((FTableColumnItem) this.tableModel.getColumnList().get(i)).getId())) {                        
                        ((FTableColumnItem) this.tableModel.getColumnList().get(i)).setLargeText(isLargeText);
                    }
                }
            }
        }
    }
    
    public boolean isLargeText() {
        return isLargeText;
    }
    
}