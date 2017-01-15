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

    //���boolean����װ������item�Ƿ�check����Ϣ��
    private boolean[] checkedItems = null;
    private boolean singleFlag = false;//��ѡ
    private JPopupMenu pp = new JPopupMenu();
    private JMenuItem miAll = new JMenuItem("ȫѡ");
    private JMenuItem miNull = new JMenuItem("ȫ��");
    private JMenuItem miReverse = new JMenuItem("��ѡ");
    private boolean allowRightItem = false;//�������Ҽ�

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
     * ����һ���򵥵�ListModel,�����Է���check�仯�¼���
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
     * ����͸�����һ�����캯��������JList���Լ����ǰɣ�����superһ����init��OK�ˡ�
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
     * ��ʼ���ؼ���������ʼ��boolean���顢��װһ����Ⱦ������װһ������������
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
     * ��תָ��item��check״̬��
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

    //ѡ��ָ��item
    public void CheckedItem(int index) {
        checkedItems[index] = true;
        CheckListBoxModel model = (CheckListBoxModel) getModel();
        model.fireCheckChanged(this, index);
        this.repaint();
    }

    /**
     * �Ƿ�ָ��item��check��
     * @param index int
     * @return boolean
     */
    public boolean isChecked(int index) {
        return checkedItems[index];
    }

    /**
     * ѡ��������
     */
    public void SelectAll() {
        for (int i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = true;
        }
        this.updateUI();
    }

    /**
     * ���ȫ��ѡ����
     */
    public void ClearSelectAll() {
        for (int i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = false;
        }
        this.updateUI();
    }

    /**
     * ��ת����ѡ����
     */
    public void ReverseCheckAll() {
        int len = checkedItems.length;
        for (int i = 0; i < len; i++) {
            invertChecked(i);
        }
        this.updateUI();
    }

    /**
     * ���ѡ�е�item����
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
     * ����ѡ��item���������顣
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
