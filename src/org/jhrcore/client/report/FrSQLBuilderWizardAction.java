/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import com.fr.base.BaseUtils;
import com.fr.base.Inter;
import com.fr.design.actions.UpdateAction;
import com.fr.design.data.source.DBTableDataPane;
import com.fr.report.core.ParameterHelper;
import com.fr.report.parameter.Parameter;
import java.awt.event.ActionEvent;
import org.jhrcore.client.CommUtil;
import org.jhrcore.ui.WizardDialog;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.query.QueryAnalysisScheme;

/**
 *
 * @author wangzhenhua
 */
public class FrSQLBuilderWizardAction extends UpdateAction {

    private DBTableDataPane dBTableDataPane;

    // 当前编辑报表所属于的模块
    public static String module_code = "Emp";
    
    public FrSQLBuilderWizardAction(DBTableDataPane dBTableDataPane) {
        this.dBTableDataPane = dBTableDataPane;
        setName(Inter.getLocText("Wizard"));
        setMnemonic('W');
        setSmallIcon(BaseUtils.readIcon("/com/fr/design/data/source/selectwizard/images/wizard.png"));
    }

    @Override
    public void actionPerformed(ActionEvent paramActionEvent) {
        QueryAnalysisScheme queryAnalysisScheme = new QueryAnalysisScheme();
        ModuleInfo cur_module = (ModuleInfo)CommUtil.fetchEntityBy("from ModuleInfo m where m.module_code='" + module_code + "'");
        queryAnalysisScheme.setModuleInfo(cur_module);
        queryAnalysisScheme.setQuery_code("sqlbuilder");
        final ReportWizardModel reportWizardModel = new ReportWizardModel(queryAnalysisScheme);
        //        queryAnalysisScheme.setQueryAnalysisScheme_type(reportDef1.getReport_class());

        if (WizardDialog.showWizard(reportWizardModel)) {
            String sql = queryAnalysisScheme.buildSQLforReport(reportWizardModel.getQueryScheme());
            //QueryAnalysisModulePanel.buildSQL(null, queryAnalysisScheme, reportWizardModel.getQueryScheme());
            dBTableDataPane.sqlTextPane.setText(sql);

            Parameter[] arrayOfParameter = ParameterHelper.analyze4Parameters(dBTableDataPane.sqlTextPane.getText());
            ReportPanel.analysisParam(arrayOfParameter, reportWizardModel);
            
            if (arrayOfParameter != null)
                dBTableDataPane.refreshParameters(arrayOfParameter);
            //add_report(reportDef1);
            //reportModel.buildTree();
            //jTree.updateUI();
            //处理参数
        }
    /*
    QueryBuilderDialog localQueryBuilderDialog = QueryBuilderDialog.showWindow(SwingUtilities.getWindowAncestor(DBTableDataPane.this));
    String str = DBTableDataPane.this.connectionComboBox.getSelectedItem();
    NameDatabaseConnection localNameDatabaseConnection = new NameDatabaseConnection(str);
    try
    {
    localQueryBuilderDialog.populate(localNameDatabaseConnection, DBTableDataPane.this.sqlTextPane.getText());
    localQueryBuilderDialog.setVisible(true);
    int i = localQueryBuilderDialog.getReturnValue();
    if (i == 0)
    {
    QueryModel localQueryModel = localQueryBuilderDialog.update();
    if (localQueryModel != null)
    {
    DBTableDataPane.this.sqlTextPane.setText(localQueryModel.toString(true));
    DBTableDataPane.this.sqlTextPane.requestFocus();
    }
    }
    }
    catch (Exception localException)
    {
    FRContext.getLogger().log(Level.WARNING, localException.getMessage());
    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(DBTableDataPane.this), localException.getMessage(), Inter.getLocText("Error"), 0);
    }
     */
    }
}