/*
 * This file is part of Giswater
 * Copyright (C) 2013 Tecnics Associats
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Author:
 *   David Erill <derill@giswater.org>
 */
package org.giswater.gui.panel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.giswater.controller.EpaSoftController;
import org.giswater.gui.frame.EpaSoftFrame;
import org.giswater.util.MaxLengthTextDocument;
import org.giswater.util.Utils;


public class EpaSoftPanel extends JPanel implements ActionListener {

	private EpaSoftController controller;
	private EpaSoftFrame epaSoftFrame;	
	
	private JPanel panelPreprocess;
	private JButton btnDesign;
	private JButton btnReport;
	private JButton btnOptions;
	private JButton btnPatternSelection;
	private JButton btnSectorSelection;
	private JButton btnPlanSelection;	
	
	private JPanel panelFileManager;
	private JLabel lblFileRpt;
	private JLabel lblResultName;
	private JCheckBox chkSubcatchments;	
	private JTextField txtResultName;
	private JTextArea txtFileRpt;
	private JTextArea txtFileInp;
	private JButton btnFileInp;
	private JButton btnFileRpt;
	private JButton btnAccept;
	private JCheckBox chkExec;
	private JCheckBox chkImport;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JCheckBox chkExport;
	
	private JPanel panelPostprocess;
	private JButton btnResultCatalog;
	private JButton btnResultSelector;

	private JButton btnProjectPreferences;
	private JButton btnProjectManager;
	
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("form"); 	
	private static final Font FONT_PANEL_TITLE = new Font("Tahoma", Font.PLAIN, 11);

	
	public EpaSoftPanel() {
		try {
			initConfig();
		} catch (MissingResourceException e) {
			Utils.showError(e);
			System.exit(ERROR);
		}
	}
	

	private void initConfig() throws MissingResourceException {

		setLayout(new MigLayout("", "[575.00px][::8px]", "[][3px:n][][3px:n][][]"));
		
		panelPreprocess = new JPanel();
		panelPreprocess.setBorder(new TitledBorder(null, BUNDLE.getString("EpaSoftPanel.panelPreprocess.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, FONT_PANEL_TITLE, null));
		add(panelPreprocess, "cell 0 0 2 1,grow");
		panelPreprocess.setLayout(new MigLayout("", "[150px][150px][150px][5px:n]", "[][]"));
		
		btnOptions = new JButton(BUNDLE.getString("EpaSoftPanel.btnDesign.text")); //$NON-NLS-1$
		btnOptions.setEnabled(false);
		btnOptions.setActionCommand("showOptions");
		panelPreprocess.add(btnOptions, "flowx,cell 0 0,growx");
		
		btnDesign = new JButton(BUNDLE.getString("EpaSoftPanel.btnRaingage.text")); //$NON-NLS-1$
		btnDesign.setEnabled(false);
		btnDesign.setActionCommand("showRaingage");
		panelPreprocess.add(btnDesign, "cell 1 0,growx");
		
		btnReport = new JButton(BUNDLE.getString("EpaSoftPanel.btnOptions.text")); //$NON-NLS-1$
		btnReport.setEnabled(false);
		btnReport.setActionCommand("showReport");
		panelPreprocess.add(btnReport, "cell 2 0,growx");
		
		btnPatternSelection = new JButton("Pattern selection");
		btnPatternSelection.setEnabled(false);
		btnPatternSelection.setActionCommand("showPatternSelection");
		panelPreprocess.add(btnPatternSelection, "cell 0 1,growx");
		
		btnSectorSelection = new JButton(BUNDLE.getString("EpaSoftPanel.btnSectorSelection.text")); //$NON-NLS-1$
		btnSectorSelection.setEnabled(false);
		btnSectorSelection.setActionCommand("showSectorSelection");
		panelPreprocess.add(btnSectorSelection, "cell 1 1,growx");
		
		btnPlanSelection = new JButton("Plan selection");
		btnPlanSelection.setEnabled(false);
		btnPlanSelection.setActionCommand("showPlanSelection");
		panelPreprocess.add(btnPlanSelection, "cell 2 1,growx");
		
		panelFileManager = new JPanel();
		panelFileManager.setBorder(new TitledBorder(null, BUNDLE.getString("EpaSoftPanel.panelFileManager.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, FONT_PANEL_TITLE, null));
		add(panelFileManager, "cell 0 2 2 1,grow");
		panelFileManager.setLayout(new MigLayout("", "[][120.00][::5px][:355px:355px][:65px:65px]", "[::20px][34px:n][20][34px:n][20][][]"));
		
		chkExport = new JCheckBox();
		chkExport.setToolTipText(BUNDLE.getString("EpaSoftPanel.chkExport.toolTipText")); //$NON-NLS-1$
		chkExport.setActionCommand("exportSelected");
		chkExport.setText(BUNDLE.getString("EpaSoftPanel.chkExport.text")); 
		panelFileManager.add(chkExport, "cell 0 0 2 1,aligny bottom");
		
		chkSubcatchments = new JCheckBox();
		chkSubcatchments.setToolTipText(BUNDLE.getString("EpaSoftPanel.chkSubcatchments.toolTipText")); //$NON-NLS-1$
		chkSubcatchments.setVisible(false);
		chkSubcatchments.setText(BUNDLE.getString("EpaSoftPanel.chkSubcatchments.text")); //$NON-NLS-1$
		panelFileManager.add(chkSubcatchments, "cell 3 0");

		JLabel label = new JLabel();
		label.setText(BUNDLE.getString("Form.label.text")); 
		panelFileManager.add(label, "cell 1 1,alignx right");
		
		scrollPane_2 = new JScrollPane();
		panelFileManager.add(scrollPane_2, "cell 3 1,grow");

		txtFileInp = new JTextArea();
		scrollPane_2.setViewportView(txtFileInp);
		txtFileInp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtFileInp.setLineWrap(true);

		btnFileInp = new JButton();
		btnFileInp.setMinimumSize(new Dimension(65, 9));
		btnFileInp.setActionCommand("chooseFileInp");
		btnFileInp.setText("...");
		btnFileInp.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelFileManager.add(btnFileInp, "cell 4 1,growx");

		chkExec = new JCheckBox();
		chkExec.setToolTipText(BUNDLE.getString("EpaSoftPanel.chkExec.toolTipText")); //$NON-NLS-1$
		chkExec.setText(BUNDLE.getString("EpaSoftPanel.chkExec.text"));  //$NON-NLS-1$
		chkExec.setName("chk_exec");
		panelFileManager.add(chkExec, "cell 0 2 3 1,alignx left,aligny bottom");

		lblFileRpt = new JLabel();
		lblFileRpt.setText(BUNDLE.getString("Form.label_1.text")); 
		panelFileManager.add(lblFileRpt, "cell 1 3,alignx right");
		
		scrollPane_3 = new JScrollPane();
		panelFileManager.add(scrollPane_3, "cell 3 3,grow");

		txtFileRpt = new JTextArea();
		scrollPane_3.setViewportView(txtFileRpt);
		txtFileRpt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtFileRpt.setLineWrap(true);

		btnFileRpt = new JButton();
		btnFileRpt.setMinimumSize(new Dimension(65, 9));
		btnFileRpt.setActionCommand("chooseFileRpt");
		btnFileRpt.setText("...");
		btnFileRpt.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelFileManager.add(btnFileRpt, "cell 4 3,growx");

		chkImport = new JCheckBox();
		chkImport.setToolTipText(BUNDLE.getString("EpaSoftPanel.chkImport.toolTipText")); //$NON-NLS-1$
		chkImport.setText(BUNDLE.getString("EpaSoftPanel.chkImport.text")); 
		chkImport.setName("chk_import");
		panelFileManager.add(chkImport, "cell 0 4 2 1,aligny bottom");

		lblResultName = new JLabel();
		lblResultName.setText(BUNDLE.getString("Form.label_2.text")); 
		lblResultName.setName("lbl_project");
		panelFileManager.add(lblResultName, "cell 1 5,alignx right");

		txtResultName = new JTextField();
		txtResultName.setName("txt_project");
		MaxLengthTextDocument maxLength = new MaxLengthTextDocument(16);		
		txtResultName.setDocument(maxLength);				
		panelFileManager.add(txtResultName, "cell 3 5,growx,aligny top");
		
		btnAccept = new JButton();
		btnAccept.setMinimumSize(new Dimension(65, 23));
		btnAccept.setEnabled(false);
		btnAccept.setText(BUNDLE.getString("Generic.btnAccept.text")); 
		btnAccept.setName("btn_accept_postgis");
		btnAccept.setActionCommand("execute");
		panelFileManager.add(btnAccept, "flowx,cell 4 6,alignx right");
		
		panelPostprocess = new JPanel();
		panelPostprocess.setBorder(new TitledBorder(null, BUNDLE.getString("EpaSoftPanel.panelPostprocess.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, FONT_PANEL_TITLE, null));
		add(panelPostprocess, "cell 0 4 2 1,grow");
		panelPostprocess.setLayout(new MigLayout("", "[120px:n][120px:n]", "[]"));
		
		btnResultCatalog = new JButton(BUNDLE.getString("EpaSoftPanel.btnResultCatalog.text")); 
		btnResultCatalog.setPreferredSize(new Dimension(121, 23));
		btnResultCatalog.setEnabled(false);
		btnResultCatalog.setActionCommand("scenarioCatalog");
		panelPostprocess.add(btnResultCatalog, "cell 0 0");
		
		btnResultSelector = new JButton(BUNDLE.getString("EpaSoftPanel.btnResultSelector.text")); 
		btnResultSelector.setPreferredSize(new Dimension(121, 23));
		btnResultSelector.setEnabled(false);
		btnResultSelector.setActionCommand("scenarioManagement");
		panelPostprocess.add(btnResultSelector, "cell 1 0");
		
		btnProjectPreferences = new JButton("Project Preferences");
		btnProjectPreferences.setMinimumSize(new Dimension(120, 23));
		btnProjectPreferences.setActionCommand("openProjectPreferences");
		add(btnProjectPreferences, "flowx,cell 0 5,alignx right");
		
		btnProjectManager = new JButton(BUNDLE.getString("EpaSoftPanel.btnEditProjectPreferences.text")); //$NON-NLS-1$
		btnProjectManager.setActionCommand("openProjectManager");
		btnProjectManager.setMinimumSize(new Dimension(120, 23));
		add(btnProjectManager, "cell 0 5,alignx right");

		setupListeners();

	}

	
	// Setup component's listener
	private void setupListeners() {
		
		// Preprocess options		
		btnDesign.addActionListener(this);
		btnReport.addActionListener(this);
		btnOptions.addActionListener(this);
		btnSectorSelection.addActionListener(this);		
		
		// File manager
		btnFileInp.addActionListener(this);
		btnFileRpt.addActionListener(this);
		chkExport.addActionListener(this);
		btnAccept.addActionListener(this);
		
		// Postprocess options
		btnResultCatalog.addActionListener(this);
		btnResultSelector.addActionListener(this);
		
		btnProjectPreferences.addActionListener(this);
		btnProjectManager.addActionListener(this);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.action(e.getActionCommand());
	}

	
	public void setFrame(EpaSoftFrame epaSoftFrame) {
		this.epaSoftFrame = epaSoftFrame;
	}	
	
	public EpaSoftFrame getFrame(){
		return epaSoftFrame;
	}	

	public void setController(EpaSoftController controller) {
		this.controller = controller;
	}

	public EpaSoftController getController() {
		return controller;
	}
	
	public String getFileRpt() {
		String fileRpt = txtFileRpt.getText().trim();
		return fileRpt;
	}
	
	public void setFileRpt(String path) {
		txtFileRpt.setText(path);
	}
	
	public String getFileInp() {
		String fileInp = txtFileInp.getText().trim();
		return fileInp;
	}

	public void setFileInp(String path) {
		txtFileInp.setText(path);
	}

	public void setProjectName(String projectName) {
		txtResultName.setText(projectName);
	}

	public String getProjectName() {
		return txtResultName.getText().trim();
	}
	
	public boolean isSubcatchmentsSelected() {
		return chkSubcatchments.isSelected();
	}
	
	public boolean isExportSelected() {
		return chkExport.isSelected();
	}

	public boolean isExecSelected() {
		return chkExec.isSelected();
	}

	public boolean isImportSelected() {
		return chkImport.isSelected();
	}	
	
	public void enableRunAndImport(boolean enable) {
		chkExec.setEnabled(enable);
		lblFileRpt.setEnabled(enable);
		txtFileRpt.setEnabled(enable);
		btnFileRpt.setEnabled(enable);
		chkImport.setEnabled(enable);
		lblResultName.setEnabled(enable);
		txtResultName.setEnabled(enable);		
	}
	
	public void enableAccept(boolean enable) {
		btnAccept.setEnabled(enable);
	}	
	
	
	public void setOptionsButton(String text, String actionCommand) {
		btnOptions.setText(text);
		btnOptions.setActionCommand(actionCommand);
	}	
	
	public void setDesignButton(String text, String actionCommand) {
		btnDesign.setText(text);
		btnDesign.setActionCommand(actionCommand);
	}
	
	public void setReportButton(String text, String actionCommand) {
		btnReport.setText(text);
		btnReport.setActionCommand(actionCommand);
	}	
	
    
	public void enableDatabaseButtons(boolean enable) {
    	enablePreprocess(enable);
    	enableResultCatalog(enable);
    	enableResultSelector(enable);
	}
	
	
	public void enablePreprocess(boolean enabled){
		btnOptions.setEnabled(enabled);
		btnDesign.setEnabled(enabled);
		btnReport.setEnabled(enabled);
		btnSectorSelection.setEnabled(enabled);
	}
    
    public void enableResultCatalog(boolean enable) {
    	btnResultCatalog.setEnabled(enable);
    }
    
    public void enableResultSelector(boolean enable) {
    	btnResultSelector.setEnabled(enable);
    }    
    
	public void setTitle(String title) {
		getFrame().setTitle(title);		
	}
	
	public void setSubcatchmentVisible(boolean visible) {
		chkSubcatchments.setVisible(visible);	
	}
	
	public void setSubcatchmentEnabled(boolean enabled) {
		chkSubcatchments.setEnabled(enabled);	
	}
	
	public void setSubcatchmentSelected(boolean selected) {
		chkSubcatchments.setSelected(selected);	
	}
	
	public void exportSelected() {
		controller.exportSelected();
	}

	
	
}