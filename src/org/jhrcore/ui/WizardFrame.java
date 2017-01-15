/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

/**
 *
 * @author mxliteboss
 */
public class WizardFrame extends ModelFrame {

    private static final long serialVersionUID = 1L;
    private static boolean finished = false;
    private WizardModel model;
    private JButton btnNext;
    private JButton btnPriev;
    private JButton btnFinish;
    private JButton btnCancel;
    private JCheckBox cb_continue;
    private int currentStep;
    private ModelFrame mf;
    private WizardPanel currentPanel = null;
    private boolean continue_flag = false; //是否添加cb_continue

    public WizardFrame(WizardModel model, JFrame modelFrame, boolean isModel, String title) throws HeadlessException {
        //super(new JFrame(), model.getWizardName());
        this.model = model;
        this.setTitle(title);
        this.setModal(isModel);
        this.setFrame(modelFrame);
        this.setModuleKey(model.getClass().getName());
        mf = this;
        init();
        setupEventListeners();
        setCurrentStep(0);
    }

    public WizardFrame(WizardModel model, JFrame modelFrame, boolean isModel, String title,boolean continue_flag) throws HeadlessException {
        //super(new JFrame(), model.getWizardName());
        this.continue_flag = continue_flag;
        this.model = model;
        this.setTitle(title);
        this.setModal(isModel);
        this.setFrame(modelFrame);
        this.setModuleKey(model.getClass().getName());
        mf = this;
        init();
        setupEventListeners();
        setCurrentStep(0);
    }

    private void init() {
        setLocation(model.getLocation());
        setSize(model.getSize());
        btnNext = new JButton("下一步");
        btnPriev = new JButton("上一步");
        btnFinish = new JButton("完  成");
        btnCancel = new JButton("取  消");

        cb_continue = new JCheckBox("完成后继续");

        FormLayout layout = new FormLayout(
                "2dlu, 80dlu,20dlu, 50dlu, 20dlu, 50dlu, 20dlu, 50dlu, 20dlu, 50dlu", // 3columns
                "b:p" // 1rows
                );
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();
        builder.add(cb_continue,cc.xy(2, 1));
        builder.add(btnPriev, cc.xy(4, 1));
        builder.add(btnNext, cc.xy(6, 1));
        builder.add(btnFinish, cc.xy(8, 1));
        builder.add(btnCancel, cc.xy(10, 1));
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
                ModelFrame.close();
            }
        });
        btnFinish.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!currentPanel.isValidate()) {
                    return;
                }
                currentPanel.beforeLeave();
                finished = true;
                model.afterFinished();
                if (cb_continue.isSelected()) {
                    setCurrentStep(0);
                } else {
                    ModelFrame.close();
                }
            }
        });
    }

    public void setCurrentStep(int currentStep) {
        if (currentPanel != null) {
            if (this.currentStep < currentStep && !currentPanel.isValidate()) {
                return;
            }
        }
        if (currentPanel != null) {
            currentPanel.beforeLeave();
        }
        this.currentStep = currentStep;
        if (currentPanel != null) {
            mf.remove(currentPanel);
        }
        currentPanel = model.getPanel(currentStep);
        mf.add(currentPanel, BorderLayout.CENTER);
        this.setTitle(model.getWizardName()+"      "+currentPanel.getTitle());
        currentPanel.updateUI();
        btnPriev.setEnabled(currentStep > 0);
        btnNext.setEnabled(currentStep < model.getTotalStep() - 1);
        btnFinish.setEnabled(currentStep == model.getTotalStep() - 1);
        cb_continue.setVisible(continue_flag && currentStep == model.getTotalStep() - 1);
    }

    public static boolean showWizard(WizardModel model, JFrame modelFrame, boolean isModel, String title) {
        if(ModelFrame.getExistKeys().contains(model.getClass().getName())){
            return false;
        }
        ModelFrame.getExistKeys().add(model.getClass().getName());
        WizardFrame fmWizard = new WizardFrame(model, modelFrame, isModel, title);
        ModelFrame.getMfKeys().put(model.getClass().getName(),fmWizard);
        ContextManager.locateOnScreenCenter(fmWizard);
        fmWizard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fmWizard.setVisible(true);
        return finished;
    }
    public static boolean showWizard(WizardModel model, JFrame modelFrame, boolean isModel, String title,boolean continue_flag) {
        if(ModelFrame.getExistKeys().contains(model.getClass().getName())){
            return false;
        }
        ModelFrame.getExistKeys().add(model.getClass().getName());
        WizardFrame fmWizard = new WizardFrame(model, modelFrame, isModel, title,continue_flag);
        ModelFrame.getMfKeys().put(model.getClass().getName(),fmWizard);
        ContextManager.locateOnScreenCenter(fmWizard);
        fmWizard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fmWizard.setVisible(true);
        return finished;
    }
}

