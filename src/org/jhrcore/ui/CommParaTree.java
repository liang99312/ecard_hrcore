/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 *
 * @author hflj
 */
public class CommParaTree extends DefaultTreeModel {

    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("参数列表");
    private Hashtable<String, String> k_keywords = new Hashtable<String, String>();
    private Hashtable<String, List> lookups = new Hashtable<String, List>();
    private Hashtable<String, String> keyword_groups = new Hashtable<String, String>();

    public CommParaTree() {
        super(new DefaultMutableTreeNode());
        this.setRoot(rootNode);
    }

    public CommParaTree(List parents, Hashtable<Object, List> child_keys) {
        super(new DefaultMutableTreeNode());
        this.setRoot(rootNode);
        buildTree(parents, child_keys);
    }

    public void buildTree(List parents, Hashtable<Object, List> child_keys) {
        rootNode.removeAllChildren();
        k_keywords.clear();
        lookups.clear();
        keyword_groups.clear();
        for (Object parent_obj : parents) {
            DefaultMutableTreeNode parent_node = new DefaultMutableTreeNode(parent_obj);
            if (child_keys != null) {
                List childs = child_keys.get(parent_obj);
                if (childs != null) {
                    List tmp_list = lookups.get(parent_obj.toString());
                    if (tmp_list == null) {
                        tmp_list = new ArrayList();
                    }
                    for (Object child_obj : childs) {
                        parent_node.add(new DefaultMutableTreeNode(child_obj));
                        if (keyword_groups.get("[" + parent_obj.toString() + "." + child_obj.toString() + "]") != null) {
                            continue;
                        }
                        keyword_groups.put("[" + parent_obj.toString() + "." + child_obj.toString() + "]", parent_obj.toString());
                        if (child_obj instanceof TempFieldInfo) {
                            TempFieldInfo tfi = (TempFieldInfo) child_obj;
                            k_keywords.put("[" + parent_obj.toString() + "." + child_obj.toString() + "]", "[" + tfi.getField_name() + "]");
                        } else {
                            k_keywords.put("[" + parent_obj.toString() + "." + child_obj.toString() + "]", "[" + child_obj.toString() + "]");
                        }
                        tmp_list.add(child_obj);
                    }
                    lookups.put(parent_obj.toString(), tmp_list);
                }
            }
            rootNode.add(parent_node);
        }
    }

    public Hashtable<String, String> getK_keywords() {
        return k_keywords;
    }

    public Hashtable<String, String> getKeyword_groups() {
        return keyword_groups;
    }

    public Hashtable<String, List> getLookups() {
        return lookups;
    }
}
