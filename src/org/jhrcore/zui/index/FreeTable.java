package org.jhrcore.zui.index;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.jhrcore.zui.FreeUtil;

public class FreeTable extends JTable {

    private Color verticalGridColor = Color.blue;
    private FreeTableCellRenderer renderer = new FreeTableCellRenderer();
    private FreeTableHeaderRenderer headerRenderer = new FreeTableHeaderRenderer();

    public FreeTable() {
        init();
    }

    private void init() {
        //this.setFont(FreeUtil.FONT_12_PLAIN);
        //this.getTableHeader().setFont(FreeUtil.FONT_12_BOLD);
        this.getTableHeader().setDefaultRenderer(headerRenderer);
        this.setBorder(null);
        this.setShowGrid(false);
        this.setRowSelectionAllowed(true);
        this.setGridColor(verticalGridColor);
        this.setRowMargin(0);
        this.setRowHeight(25);
//        this.setShowHorizontalLines(false);
//        this.setShowVerticalLines(false);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return renderer;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
