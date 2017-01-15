package org.jhrcore.ui;

import javax.swing.tree.DefaultMutableTreeNode;

public class CheckTreeNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 1L;
    private boolean selected = false;

    public CheckTreeNode(Object obj) {
        super(obj);
    }

    public CheckTreeNode() {
        super();
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
