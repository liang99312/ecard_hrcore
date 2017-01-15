/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class WizardDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private static boolean finished = false;
    private WizardModel model;
    private JButton btnNext;
    private JButton btnPriev;
    private JButton btnFinish;
    private JButton btnCancel;
    private int currentStep;
    private JDialog dlg;
    private WizardPanel currentPanel = null;

    public WizardDialog(WizardModel model) throws HeadlessException {
        super(new JFrame(), model.getWizardName());
        this.model = model;
        this.setModal(true);
        //this.setAlwaysOnTop(true);
        dlg = this;
        init();
        setupEventListeners();
        setCurrentStep(0);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void init() {
        setLocation(model.getLocation());
        setSize(model.getSize());
        btnNext = new JButton("下一步");
        btnPriev = new JButton("上一步");
        btnFinish = new JButton("完  成");
        btnCancel = new JButton("取  消");

        FormLayout layout = new FormLayout(
                "120dlu, 50dlu, 4dlu, 50dlu, 30dlu, 50dlu, 30dlu, 50dlu", // 3columns
                "b:p" // 1rows
                );
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();
        builder.add(btnPriev, cc.xy(2, 1));
        builder.add(btnNext, cc.xy(4, 1));
        builder.add(btnFinish, cc.xy(6, 1));
        builder.add(btnCancel, cc.xy(8, 1));
        add(builder.getPanel(), BorderLayout.SOUTH);
    }

    private void setupEventListeners() {
        btnPriev.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                setCurrentStep(currentStep - 1);
            }
        });
        btnNext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                setCurrentStep(currentStep + 1);
            }
        });
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                finished = false;
                dlg.dispose();
            }
        });
        btnFinish.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                currentPanel.beforeLeave();
                if (!currentPanel.isValidate()) {
                    return;
                }
                finished = true;
                currentPanel.setEnabled(false);
                model.afterFinished();
                dlg.dispose();
            }
        });
    }

    public void setCurrentStep(int currentStep) {
        if (currentPanel != null) {
            if (this.currentStep < currentStep && !currentPanel.isValidate()) {
                return;
            }
        }
        if (currentPanel != null&&this.currentStep < currentStep) {
            currentPanel.beforeLeave();
        }
        this.currentStep = currentStep;
        if (currentPanel != null) {
            dlg.remove(currentPanel);
        }
        currentPanel = model.getPanel(currentStep);
        dlg.add(currentPanel, BorderLayout.CENTER);
        this.setTitle(model.getWizardName()+"      "+currentPanel.getTitle());
        currentPanel.updateUI();
        btnPriev.setEnabled(currentStep > 0);
        btnNext.setEnabled(currentStep < model.getTotalStep() - 1);
        btnFinish.setEnabled(currentStep == model.getTotalStep() - 1);
    }

    public static boolean showWizard(WizardModel model) {
        WizardDialog fmWizard = new WizardDialog(model);
        ContextManager.locateOnScreenCenter(fmWizard);
        fmWizard.setVisible(true);
        return finished;
    }
    public static boolean showWizardResidebled(WizardModel model){
        WizardDialog fmWizard = new WizardDialog(model);
        ContextManager.locateOnScreenCenter(fmWizard);
        fmWizard.setResizable(true);
        fmWizard.setVisible(true);
        return finished;
    }
}

