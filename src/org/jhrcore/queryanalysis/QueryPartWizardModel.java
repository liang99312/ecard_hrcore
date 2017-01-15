/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.queryanalysis;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JOptionPane;
import org.jhrcore.ui.WizardModel;
import org.jhrcore.ui.WizardPanel;
import org.jhrcore.entity.query.QueryPart;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class QueryPartWizardModel implements WizardModel {

    private QueryPartNamePanel queryPartNamePanel;
    private QueryParaPanel queryParaPanel;
    private QueryPart queryPart;

    public QueryPartWizardModel(QueryPart queryPart) {
        this.queryPart = queryPart;
    }

    @Override
    public int getTotalStep() {
        return 2;
    }

    @Override
    public String getWizardName() {
        return "分段设置向导";
    }

    @Override
    public WizardPanel getPanel(int step) {
        if (step == 0) {
            if (queryPartNamePanel == null) {
                queryPartNamePanel = new QueryPartNamePanel(queryPart);
            }
            return queryPartNamePanel;
        }
        if (queryParaPanel == null) {
            queryParaPanel = new QueryParaPanel(queryPart);
        } else {
            queryParaPanel.rebuildForEntity(queryPart);
        }
        return queryParaPanel;
    }

    @Override
    public Point getLocation() {
        return new Point(10, 10);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(750, 550);
    }

    @Override
    public void afterFinished() {
        ValidateSQLResult result = CommImpl.saveQueryPart(queryPart);
        if (result.getResult() == 0) {
            queryPart.setNew_flag(0);
            MsgUtil.showHRSaveSuccessMsg(null);
        } else {
            JOptionPane.showMessageDialog(null, "保存失败");
        }
    }

    public QueryPart getQueryPart() {
        return queryPart;
    }
}
