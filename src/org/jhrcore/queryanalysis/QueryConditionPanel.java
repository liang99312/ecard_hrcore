/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.queryanalysis;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhrcore.ui.WizardPanel;
import org.jhrcore.entity.A01;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.query3.QuerySchemePanel;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author Administrator
 */
public class QueryConditionPanel extends WizardPanel {

    private QuerySchemePanel querySchemePanel;
    private QueryScheme queryScheme;

    public QueryConditionPanel(QueryScheme queryScheme) {
        super();
        this.queryScheme = queryScheme;
        this.setLayout(new BorderLayout());
        Class aclass = null;
        try {
            aclass = Class.forName(EntityBuilder.getHt_entity_classes().get(queryScheme.getQueryEntity()));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QueryConditionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (aclass == null) {
            aclass = A01.class;
        }
        querySchemePanel = new QuerySchemePanel(aclass);
        querySchemePanel.setQueryScheme(this.queryScheme);
        this.add(querySchemePanel, BorderLayout.CENTER);
    }

    @Override
    public boolean isValidate() {
        return true;
    }

    @Override
    public void beforeLeave() {
    }

    @Override
    public String getTitle() {
        return "最后一步：设置过滤条件";
    }
}
