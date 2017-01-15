/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import org.jhrcore.ui.WizardModel;
import org.jhrcore.ui.WizardPanel;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.query.QueryAnalysisField;
import org.jhrcore.entity.query.QueryAnalysisScheme;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.queryanalysis.PickFieldSelectPanel;
import org.jhrcore.queryanalysis.QueryConditionPanel;

/**
 *
 * @author mxliteboss
 */
public class ReportWizardModel implements WizardModel {

    protected QueryAnalysisScheme queryAnalysisScheme;
    protected QueryScheme queryScheme;
    private PickFieldSelectPanel pickFieldSelectPanel;
    private Hashtable<String, Object> exist_keys = new Hashtable<String, Object>();

    public QueryScheme getQueryScheme() {
        return queryScheme;
    }

    @Override
    public int getTotalStep() {
        if ("sqlbuilder".equals(queryAnalysisScheme.getQuery_code())) {
            return 2;
        }
        return 3;
    }

    public ReportWizardModel(QueryAnalysisScheme queryAnalysisScheme) {
        this.queryAnalysisScheme = queryAnalysisScheme;
        if (queryAnalysisScheme.getNew_flag() != 1) {
            if (queryAnalysisScheme.getQueryAnalysisFields() != null && queryAnalysisScheme.getQueryAnalysisFields().size() != 0) {
                for (QueryAnalysisField queryAnalysisField : queryAnalysisScheme.getQueryAnalysisFields()) {
                    exist_keys.put(queryAnalysisField.getQueryAnalysisField_key(), queryAnalysisField);
                }
            }
        }
//        String scheme_type = "查询统计(" + queryAnalysisScheme.getQueryAnalysisScheme_key() + ")";
//        queryScheme = (QueryScheme) CommUtil.fetchEntityBy("from QueryScheme qs left join fetch qs.conditions where qs.scheme_type='" + scheme_type + "'");
//        if (queryScheme == null) {
            queryScheme = (QueryScheme) UtilTool.createUIDEntity(QueryScheme.class);
//        } else {
//            if (queryScheme.getConditions() != null && queryScheme.getConditions().size() != 0) {
//                for (Condition condition : queryScheme.getConditions()) {
//                    exist_keys.put(condition.getCondition_key(), condition);
//                }
//            }
//        }
    }

    @Override
    public WizardPanel getPanel(int step) {
        if ("sqlbuilder".equals(queryAnalysisScheme.getQuery_code())) {
            if (step == 0) {
                if (pickFieldSelectPanel == null) {
                    pickFieldSelectPanel = new PickFieldSelectPanel(queryAnalysisScheme);
                }
                pickFieldSelectPanel.setTitle("第一步：设置查询字段");
                return pickFieldSelectPanel;
            }
            if (step == 1) {
                String entityname = queryAnalysisScheme.getModuleInfo().getQuery_entity_name();
                entityname = entityname.substring(entityname.lastIndexOf(".") + 1);
                queryScheme.setQueryEntity(entityname);
                return new QueryConditionPanel(queryScheme);
            }
            return null;
        }
        return null;
    }

    @Override
    public Point getLocation() {
        return new Point(10, 10);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(610, 500);
    }

    @Override
    public void afterFinished() {
    }

    @Override
    public String getWizardName() {
        return "报表设计向导";
    }
}

