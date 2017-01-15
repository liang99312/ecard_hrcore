/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.queryanalysis;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.query.QueryPart;

/**
 *
 * @author mxliteboss
 */
public class AnalyseFieldModel extends DefaultTreeModel {

    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("所有方案");
    private DefaultMutableTreeNode fieldNode = new DefaultMutableTreeNode("当前表");
    private DefaultMutableTreeNode partNode = new DefaultMutableTreeNode("分段条件");

    public AnalyseFieldModel() {
        super(new DefaultMutableTreeNode());
        this.setRoot(rootNode);
        rootNode.add(fieldNode);
        rootNode.add(partNode);
    }

    public void addNode(QueryPart quertPart) {
        TempFieldInfo tfi = new TempFieldInfo();
        tfi.setEntity_name(quertPart.getQueryPart_key());
        tfi.setEntity_caption(quertPart.getEntity_caption());
        tfi.setField_name("@part_field");
        tfi.setField_type("String");
        tfi.setCaption_name(quertPart.getPart_name());
        partNode.add(new DefaultMutableTreeNode(tfi));
    }

    public void buildTree(List<TempFieldInfo> field_infos, List<QueryPart> parts) {
        fieldNode.removeAllChildren();
        partNode.removeAllChildren();
        for (TempFieldInfo tfi : field_infos) {
            fieldNode.add(new DefaultMutableTreeNode(tfi));
        }
        for (QueryPart query_part : parts) {
            TempFieldInfo tfi = new TempFieldInfo();
            tfi.setEntity_name(query_part.getQueryPart_key());
            tfi.setEntity_caption(query_part.getEntity_caption());
            tfi.setField_name("@part_field");
            tfi.setField_type("String");
            tfi.setCaption_name(query_part.getPart_name());
            partNode.add(new DefaultMutableTreeNode(tfi));
        }
    }
}

