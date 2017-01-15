/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.queryanalysis;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.log4j.Logger;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author mxliteboss
 */
public class AnalyseParaModel extends DefaultTreeModel {

    private Class<?> cur_class;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("参数列表");
    private Hashtable<String, String> k_keywords = new Hashtable<String, String>();
    private Hashtable<String, List> lookups = new Hashtable<String, List>();
    private Hashtable<String, String> keyword_groups = new Hashtable<String, String>();
    private Logger log = Logger.getLogger(AnalyseParaModel.class.getName());

    public Hashtable<String, String> getK_keywords() {
        return k_keywords;
    }

    public Hashtable<String, String> getKeyword_groups() {
        return keyword_groups;
    }

    public void setKeyword_groups(Hashtable<String, String> keyword_groups) {
        this.keyword_groups = keyword_groups;
    }

    public Hashtable<String, List> getLookups() {
        return lookups;
    }

    public void setLookups(Hashtable<String, List> lookups) {
        this.lookups = lookups;
    }

    public AnalyseParaModel(List entity_list) {
        super(new DefaultMutableTreeNode());
        this.setRoot(rootNode);
        buildTree(entity_list);
    }

    public void buildTree(List entity_list) {
        rootNode.removeAllChildren();
        lookups.clear();
        k_keywords.clear();
        keyword_groups.clear();
        if (entity_list != null) {
            for (Object obj : entity_list) {
                EntityDef ed = (EntityDef) obj;
                String entityCaption = ed.getEntityCaption();
                String packageName = EntityBuilder.getPackage(ed);
                try {
                    cur_class = Class.forName(packageName + ed.getEntityName());
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    log.error(ex);
                }
                DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(ed);
                List tmp_list = lookups.get(entityCaption);
                if (tmp_list == null) {
                    tmp_list = new ArrayList();
                }
                List<TempFieldInfo> field_list = EntityBuilder.getCommFieldInfoListOf(cur_class, EntityBuilder.COMM_FIELD_VISIBLE);
                if (field_list != null) {
                    for (TempFieldInfo tfi : field_list) {
                        if (tfi.getField_type().equals("String") || tfi.getField_type().equals("Code") || tfi.getField_type().equals("Date") || tfi.getField_type().toLowerCase().equals("boolean")) {
                            continue;
                        }
                        DefaultMutableTreeNode tmpNode1 = new DefaultMutableTreeNode(tfi);
                        tmpNode.add(tmpNode1);
                        String entity_name = tfi.getEntity_name();
                        if (keyword_groups.get("[" + entityCaption + "." + tfi.getCaption_name() + "]") != null) {
                            continue;
                        }
                        keyword_groups.put("[" + entityCaption + "." + tfi.getCaption_name() + "]", entityCaption);
                        k_keywords.put("[" + entityCaption + "." + tfi.getCaption_name() + "]", entity_name + "." + tfi.getField_name().replace("_code_", ""));
                        tmp_list.add(tfi);
                    }
                }
                lookups.put(entityCaption, tmp_list);
                rootNode.add(tmpNode);
            }
        }
    }
}

