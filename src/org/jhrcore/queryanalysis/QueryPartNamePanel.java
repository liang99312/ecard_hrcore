/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * QueryPartNamePanel.java
 *
 * Created on 2009-8-30, 10:44:00
 */

package org.jhrcore.queryanalysis;

import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.ui.WizardPanel;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.query.QueryPart;

/**
 *
 * @author mxliteboss
 */
public class QueryPartNamePanel  extends WizardPanel {
    private JComboBoxBinding entity_binding;
    private List entitys;
    private QueryPart queryPart;
    /** Creates new form QueryPartNamePanel */
    public QueryPartNamePanel(QueryPart queryPart) {
        this.queryPart = queryPart;
        this.entitys = queryPart.getEntitys();
        initComponents();
        initOthers();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        jLabel1.setText("分段名称：");

        jLabel2.setText("表选择：");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(199, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    private void initOthers(){        
        entity_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, entitys, jComboBox1);
        entity_binding.bind();
        if(queryPart.getNew_flag() == 0){
            jTextField1.setText(queryPart.getPart_name());
            String entity_name = queryPart.getEntity_name();
            for(Object obj:entitys){
                EntityDef ed = (EntityDef)obj;
                if(ed.getEntityName().equals(entity_name)){
                    jComboBox1.setSelectedItem(obj);
                    break;
                }
            }
        }
    }
    @Override
    public boolean isValidate() {
        String text = jTextField1.getText();
        if(text == null||text.trim().equals("")){
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(jComboBox1), "分段名不可为空", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(CommUtil.exists("select 1 from QueryPart qp where qp.user_code='"+UserContext.person_code+"' and qp.part_name='"+text+"' and qp.queryPart_key<>'"+queryPart.getQueryPart_key()+"'")){
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(jComboBox1), "该分段名已存在", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void beforeLeave() {
        queryPart.setPart_name(jTextField1.getText());
        queryPart.setEntity_name(((EntityDef)jComboBox1.getSelectedItem()).getEntityName());
        queryPart.setEntity_caption(jComboBox1.getSelectedItem().toString());
        queryPart.setCur_entity((EntityDef)jComboBox1.getSelectedItem());
    }

    @Override
    public String getTitle() {
        return "第一步：设置分段名称";
    }

}
