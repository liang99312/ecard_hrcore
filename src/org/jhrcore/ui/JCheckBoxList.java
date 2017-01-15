package org.jhrcore.ui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;
import org.jhrcore.util.ComponentUtil;

public class JCheckBoxList extends JList {

    //这个boolean数组装载所有item是否被check的信息。
    private boolean[] checkedItems = null;
    private boolean singleFlag = false;//单选
    private JPopupMenu pp = new JPopupMenu();
    private JMenuItem miAll = new JMenuItem("全选");
    private JMenuItem miNull = new JMenuItem("全消");
    private JMenuItem miReverse = new JMenuItem("反选");
    private boolean allowRightItem = false;//允许弹出右键

    public boolean isSingleFlag() {
        return singleFlag;
    }

    public void setSingleFlag(boolean singleFlag) {
        this.singleFlag = singleFlag;
    }

    public void setAllowRightItem(boolean allowRightItem) {
        this.allowRightItem = allowRightItem;
    }

    public boolean[] getCheckedItems() {
        return checkedItems;
    }

    public Object[] getObjects() {
        if (this.getModel() == null) {
            return null;
        }
        return ((CheckListBoxModel) this.getModel()).items;
    }

    /**
     * 定义一个简单的ListModel,它可以发送check变化事件。
     */
    class CheckListBoxModel extends AbstractListModel {

        private Object[] items = null;

        CheckListBoxModel(Object[] items) {
            this.items = items;
        }

        @Override
        public int getSize() {
            return items.length;
        }

        @Override
        public Object getElementAt(int i) {
            return items[i];
        }

        protected void fireCheckChanged(Object source, int index) {
            fireContentsChanged(source, index, index);
        }

        public Object getItem(int index) {
            return items[index];
        }
    }

    /**
     * 这里就覆盖了一个构造函数。其他JList你自己覆盖吧，反正super一下再init就OK了。
     * @param items Object[]
     */
    public JCheckBoxList(Object[] items) {
        this(items, false);
    }

    public JCheckBoxList(List list) {
        this(list.toArray());
    }

    public JCheckBoxList(List list, boolean single) {
        this(list.toArray(), single);
    }

    public JCheckBoxList(Object[] items, boolean single) {
        setModel(new CheckListBoxModel(items));
        this.singleFlag = single;
        init();
    }

    public void rebuild(List list) {
        setModel(new CheckListBoxModel(list.toArray()));
        checkedItems = new boolean[this.getModel().getSize()];
    }

    public void moveIndex(int index, int step) {
        if (index < 0) {
            return;
        }
        Object[] data = getObjects();
        int len = data.length;
        if (data.length <= index) {
            return;
        }
        Object[] copy_data = new Object[len - 1];
        Object obj = data[index];
        int j = 0;
        for (int i = 0; i < len; i++) {
            if (i == index) {
                continue;
            }
            copy_data[j] = data[i];
            j++;
        }
        j = 0;
        int newindex = index + step;
        if (newindex == -1) {
            newindex = len - 1;
        } else {
            if (newindex >= len) {
                newindex = 0;
            }
        }
        for (int i = 0; i < len; i++) {
            if (i == newindex) {
                data[i] = obj;
                continue;
            }
            data[i] = copy_data[j];
            j++;
        }
        this.setSelectedIndex(newindex);
        this.updateUI();
    }

    /**
     * 初始化控件。包括初始化boolean数组、安装一个渲染器、安装一个鼠标监听器。
     */
    private void init() {
        checkedItems = new boolean[this.getModel().getSize()];
        this.setCellRenderer(new MyCellRenderer());
        CheckListener lst = new CheckListener(this);
        this.addMouseListener(lst);
        this.addKeyListener(lst);
        pp.add(miAll);
        pp.add(miNull);
        pp.add(miReverse);
        ComponentUtil.setIcon(miAll, "blank");
        ComponentUtil.setIcon(miNull, "blank");
        ComponentUtil.setIcon(miReverse, "blank");
        miAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SelectAll();
            }
        });
        miNull.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ClearSelectAll();
            }
        });
        miReverse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ReverseCheckAll();
            }
        });
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && allowRightItem) {
                    Point p = e.getPoint();
                    pp.show(JCheckBoxList.this, p.x, p.y);
                }
            }
        ;
    }

    );
    }

    class MyCellRenderer extends JCheckBox implements ListCellRenderer {

        public MyCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            setComponentOrientation(list.getComponentOrientation());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            if (value instanceof Icon) {
                setIcon((Icon) value);
                setText("");
            } else {
                setIcon(null);
                setText((value == null) ? "" : value.toString());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            this.setSelected(isChecked(index));
            return this;
        }
    }

    class CheckListener
            implements MouseListener, KeyListener {

        protected JList m_list;

        public CheckListener(JCheckBoxList parent) {
            m_list = parent;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Point p1 = e.getPoint();
            int index = locationToIndex(e.getPoint());
            Point p = m_list.indexToLocation(index);
            if (p == null || p1 == null) {
                return;
            }
            int x = (int) (p1.getX() - p.getX());
            if (x > 0 && x < 25) {
                int y = (int) (p1.getY() - p.getY());
                if (y > 0 && y < 30) {
                    invertChecked(index);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    /**
     * 翻转指定item的check状态。
     * @param index int
     */
    public void invertChecked(int index) {
        checkedItems[index] = !checkedItems[index];
        if (singleFlag && checkedItems[index]) {
            int len = checkedItems.length;
            for (int i = 0; i < len; i++) {
                checkedItems[i] = false;
            }
            checkedItems[index] = true;
        }
        CheckListBoxModel model = (CheckListBoxModel) getModel();
        model.fireCheckChanged(this, index);
        this.repaint();
    }

    //选择指定item
    public void CheckedItem(int index) {
        checkedItems[index] = true;
        CheckListBoxModel model = (CheckListBoxModel) getModel();
        model.fireCheckChanged(this, index);
        this.repaint();
    }

    /**
     * 是否指定item被check。
     * @param index int
     * @return boolean
     */
    public boolean isChecked(int index) {
        return checkedItems[index];
    }

    /**
     * 选中所有项
     */
    public void SelectAll() {
        for (int i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = true;
        }
        this.updateUI();
    }

    /**
     * 清空全部选择项
     */
    public void ClearSelectAll() {
        for (int i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = false;
        }
        this.updateUI();
    }

    /**
     * 翻转所有选择项
     */
    public void ReverseCheckAll() {
        int len = checkedItems.length;
        for (int i = 0; i < len; i++) {
            invertChecked(i);
        }
        this.updateUI();
    }

    /**
     * 获得选中的item个数
     */
    public int getCheckedCount() {
        int result = 0;
        for (int i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i]) {
                result++;
            }
        }
        return result;
    }

    /**
     * 所有选中item索引的数组。
     */
    public int[] getCheckedIndices() {
        int[] result = new int[getCheckedCount()];
        int index = 0;
        for (int i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i]) {
                result[index] = i;
                index++;
            }
        }
        return result;
    }

    public List getCheckedObjects() {
        List list = new ArrayList();
        int[] indices = getCheckedIndices();
        for (int i = 0; i < indices.length; i++) {
            list.add(this.getModel().getElementAt(indices[i]));
        }
        return list;
    }
}
