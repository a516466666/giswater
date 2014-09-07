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
package org.giswater.gui.frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.giswater.controller.ConfigController;
import org.giswater.controller.DatabaseController;
import org.giswater.controller.HecRasController;
import org.giswater.controller.MainController;
import org.giswater.controller.MenuController;
import org.giswater.dao.MainDao;
import org.giswater.gui.panel.DatabasePanel;
import org.giswater.gui.panel.EpaPanel;
import org.giswater.gui.panel.GisPanel;
import org.giswater.gui.panel.HecRasPanel;
import org.giswater.util.Encryption;
import org.giswater.util.PropertiesMap;
import org.giswater.util.Utils;


public class MainFrame extends JFrame implements ActionListener{
	
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("form");

	private static final long serialVersionUID = -6630818426483107558L;
	private MenuController menuController;
	private PropertiesMap prop;
	private String versionCode;
	
    private JDesktopPane desktopPane;
    
    private JMenu mnProject;
	private JMenuItem mntmOpenProject;
	private JMenuItem mntmSaveProject;
	private JSeparator separator;
	private JMenuItem mntmSaveProjectAs;
	private JMenuItem mntmOpenPreferences;
	private JMenuItem mntmSavePreferences;
	private JMenuItem mntmSaveAsPreferences;

	private JMenu mnForms;
    private JMenuItem mntmSwmm;
	private JMenuItem mntmEpanet;
	private JMenuItem mntmHecras;
	
	private JMenu mnGisProject;	
	private JMenuItem mntmGisProject;	

	private JMenu mnData;
	private JMenuItem mntmProjectId;	
	private JMenuItem mntmArcCatalog;
	private JMenuItem mntmMaterials;
	private JMenuItem mntmTimeseries;
	private JMenuItem mntmCurves;
	private JMenuItem mntmPatterns;	
	
	private JMenu mnAnalysis;	
	private JMenuItem mntmCatalog;
	private JMenuItem mntmManagement;
	
	private JMenu mnConfiguration;
	private JMenuItem mntmSoftware;
	private JMenuItem mntmDatabase;
	private JMenuItem mntmSampleEpanet;
	private JMenuItem mntmSampleEpaswmm;
	private JMenuItem mntmSampleHecras;
	private JMenuItem mntmDatabaseAdministrator;	
	
	private JMenu mnAbout;
	private JMenuItem mntmWelcome;		
	private JMenuItem mntmLicense;
	private JMenuItem mntmAgreements;
	private JMenuItem mntmUserManual;	
	private JMenuItem mntmReferenceGuide;	
	private JMenuItem mntmWeb;
	private JMenuItem mntmCheckUpdates;
	
	private JMenu mnNewVersionAvailable;
	private JMenuItem mntmDownload;

	public EpaFrame swmmFrame;
	public EpaFrame epanetFrame;
	public HecRasFrame hecRasFrame;
	
	public DatabaseFrame dbFrame;
	public ConfigFrame configFrame;
	public GisFrame gisFrame;

	
	/**
	 * @wbp.parser.constructor
	 */
	public MainFrame(boolean isConnected, String versionCode) {
		this(isConnected, versionCode, false, "");
	}
	
	
	public MainFrame(boolean isConnected, String versionCode, boolean newVersion, String ftpVersion) {
		
		this.versionCode = versionCode;
		initConfig();
		setNewVersionVisible(newVersion, ftpVersion);
		try {
			initFrames();
			hecRasFrame.getPanel().enableButtons(isConnected);
		} catch (PropertyVetoException e) {
            Utils.logError(e.getMessage());
		}
		
	}

	
	public void setNewVersionVisible(boolean newVersion, String ftpVersion) {
		mnNewVersionAvailable.setVisible(newVersion);
		String msg = "Download version v"+ftpVersion;
		mntmDownload.setText(msg);
	}


	public void setControl(MenuController menuController) {
		this.menuController = menuController;
	}	
	
	
	private void initConfig(){

		ImageIcon image = new ImageIcon("images/imago.png");
		setIconImage(image.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		prop = MainDao.getPropertiesFile();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnProject = new JMenu(BUNDLE.getString("MainFrame.mnProject.text"));
		menuBar.add(mnProject);
		
		mntmOpenProject = new JMenuItem(BUNDLE.getString("MainFrame.mntmOpenProject.text"));
		mntmOpenProject.setActionCommand("openProject");
		mnProject.add(mntmOpenProject);
		
		mntmSaveProject = new JMenuItem(BUNDLE.getString("MainFrame.mntmSaveProject.text"));
		mntmSaveProject.setActionCommand("saveProject");
		mnProject.add(mntmSaveProject);
		
		mntmSaveProjectAs = new JMenuItem(BUNDLE.getString("MainFrame.mntmSaveProjectAs.text"));
		mntmSaveProjectAs.setVisible(false);
		mnProject.add(mntmSaveProjectAs);
		
		separator = new JSeparator();
		mnProject.add(separator);
		
		mntmOpenPreferences = new JMenuItem(BUNDLE.getString("MainFrame.mntmOpen.text"));
		mntmOpenPreferences.setActionCommand("gswOpen");
		mnProject.add(mntmOpenPreferences);
		
		mntmSavePreferences = new JMenuItem(BUNDLE.getString("MainFrame.mntmSave.text"));
		mntmSavePreferences.setActionCommand("gswSave");
		mnProject.add(mntmSavePreferences);
		
		mntmSaveAsPreferences = new JMenuItem(BUNDLE.getString("MainFrame.mntmSaveAs.text"));
		mntmSaveAsPreferences.setActionCommand("gswSaveAs");
		mnProject.add(mntmSaveAsPreferences);
		
		mnForms = new JMenu("Software");
		menuBar.add(mnForms);
		
		mntmEpanet = new JMenuItem("EPANET");
		mntmEpanet.setActionCommand("openEpanet");
		mnForms.add(mntmEpanet);
		
		mntmSwmm = new JMenuItem("EPA SWMM");
		mntmSwmm.setActionCommand("openSwmm");
		mnForms.add(mntmSwmm);
		
		mntmHecras = new JMenuItem(BUNDLE.getString("MainFrame.mntmHecras.text"));
		mntmHecras.setActionCommand("openHecras");
		mnForms.add(mntmHecras);
		
		mnGisProject = new JMenu(BUNDLE.getString("MainFrame.mnGisProject.text"));
		menuBar.add(mnGisProject);
		
		mntmGisProject = new JMenuItem(BUNDLE.getString("MainFrame.mntmGisProject.text"));
		mnGisProject.add(mntmGisProject);
		mntmGisProject.setActionCommand("showGisProject");
		
		mntmSampleEpanet = new JMenuItem(BUNDLE.getString("MainFrame.mntmNewMenuItem.text"));
		mnGisProject.add(mntmSampleEpanet);
		mntmSampleEpanet.setActionCommand("sampleEpanet");
		
		mntmSampleEpaswmm = new JMenuItem(BUNDLE.getString("MainFrame.mntmCreateEpaswmmSample.text"));
		mnGisProject.add(mntmSampleEpaswmm);
		mntmSampleEpaswmm.setActionCommand("sampleEpaswmm");
		
		mntmSampleHecras = new JMenuItem(BUNDLE.getString("MainFrame.mntmCreateHecrasSample.text"));
		mnGisProject.add(mntmSampleHecras);
		mntmSampleHecras.setActionCommand("sampleHecras");
		
		mntmDatabaseAdministrator = new JMenuItem(BUNDLE.getString("MainFrame.mntmDatabaseAdministrator.text"));
		mnGisProject.add(mntmDatabaseAdministrator);
		mntmDatabaseAdministrator.setActionCommand("openDatabaseAdmin");		
		
		mnData = new JMenu(BUNDLE.getString("MainFrame.mnManager.text"));
		mnData.setEnabled(false);
		menuBar.add(mnData);
		
		mntmProjectId = new JMenuItem(BUNDLE.getString("MainFrame.mntmProjectId.text"));
		mntmProjectId.setFocusCycleRoot(true);
		mntmProjectId.setActionCommand("showProjectId");
		mnData.add(mntmProjectId);
		
		mntmArcCatalog = new JMenuItem(BUNDLE.getString("MainFrame.mntmConduit.text"));
		mntmArcCatalog.setActionCommand("showArcCatalog");
		mnData.add(mntmArcCatalog);
		
		mntmMaterials = new JMenuItem(BUNDLE.getString("MainFrame.mntmMaterials.text"));
		mntmMaterials.setActionCommand("showMaterials");
		mnData.add(mntmMaterials);
		
		mntmTimeseries = new JMenuItem(BUNDLE.getString("MainFrame.mntmTimeseries.text"));
		mntmTimeseries.setActionCommand("showTimeseries");
		mnData.add(mntmTimeseries);
		
		mntmCurves = new JMenuItem(BUNDLE.getString("MainFrame.mntmCurves.text"));
		mntmCurves.setActionCommand("showCurves");
		mnData.add(mntmCurves);
		
		mntmPatterns = new JMenuItem(BUNDLE.getString("MainFrame.mntmPatterns.text"));
		mntmPatterns.setActionCommand("showPatterns");
		mnData.add(mntmPatterns);
		
		mnAnalysis = new JMenu(BUNDLE.getString("MainFrame.mnScenarios.text"));
		menuBar.add(mnAnalysis);
		
		mntmCatalog = new JMenuItem(BUNDLE.getString("MainFrame.mntmCatalog.text"));
		mntmCatalog.setActionCommand("scenarioCatalog");
		mnAnalysis.add(mntmCatalog);
		
		mntmManagement = new JMenuItem(BUNDLE.getString("MainFrame.mntmManagement.text"));
		mntmManagement.setActionCommand("scenarioManagement");
		mnAnalysis.add(mntmManagement);
		
		mnConfiguration = new JMenu(BUNDLE.getString("MainFrame.mnConfiguration.text"));
		menuBar.add(mnConfiguration);
		
		mntmDatabase = new JMenuItem(BUNDLE.getString("MainFrame.mntmDatabase.text"));
		mntmDatabase.setActionCommand("showDatabase");
		mnConfiguration.add(mntmDatabase);
		
		mntmSoftware = new JMenuItem(BUNDLE.getString("MainFrame.mntmSoftwareConfiguration.text"));
		mntmSoftware.setActionCommand("showSoftware");
		mnConfiguration.add(mntmSoftware);
		
		mnAbout = new JMenu(BUNDLE.getString("MainFrame.mnAbout.text"));
		menuBar.add(mnAbout);
		
		mntmWelcome = new JMenuItem(BUNDLE.getString("MainFrame.mntmWelcome.text"));
		mnAbout.add(mntmWelcome);
		mntmWelcome.setActionCommand("showWelcome");
		
		mntmLicense = new JMenuItem(BUNDLE.getString("MainFrame.mntmLicense.text"));
		mntmLicense.setActionCommand("showLicense");
		mnAbout.add(mntmLicense);
		
		mntmUserManual = new JMenuItem(BUNDLE.getString("MainFrame.mntmHelp.text"));
		mnAbout.add(mntmUserManual);
		mntmUserManual.setActionCommand("openUserManual");
		
		mntmReferenceGuide = new JMenuItem(BUNDLE.getString("MainFrame.mntmReferenceGuide.text"));
		mntmReferenceGuide.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmReferenceGuide.setActionCommand("openReferenceGuide");
		mnAbout.add(mntmReferenceGuide);
		
		mntmWeb = new JMenuItem(BUNDLE.getString("MainFrame.mntmWebPage.text"));
		mntmWeb.setActionCommand("openWeb");
		mnAbout.add(mntmWeb);
		
		mntmAgreements = new JMenuItem(BUNDLE.getString("MainFrame.mntmAgreements.text"));
		mntmAgreements.setActionCommand("showAcknowledgment");
		mnAbout.add(mntmAgreements);
		
		mntmCheckUpdates = new JMenuItem(BUNDLE.getString("MainFrame.mntmCheckUpdates.text"));
		mntmCheckUpdates.setActionCommand("checkUpdates");
		mnAbout.add(mntmCheckUpdates);
		
		String path = Utils.getAppPath() + "images/download_16.png";
		final ImageIcon iconImage = new ImageIcon(path);
		mnNewVersionAvailable = new JMenu(BUNDLE.getString("MainFrame.mnNewVersionAvailable.text"));
		mnNewVersionAvailable.setActionCommand("downloadNewVersion");
		mnNewVersionAvailable.setVisible(false);
		mnNewVersionAvailable.setIcon(iconImage);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(mnNewVersionAvailable);
		
		mntmDownload = new JMenuItem();
		mntmDownload.setActionCommand("downloadNewVersion");
		mnNewVersionAvailable.add(mntmDownload);
		
		desktopPane = new JDesktopPane();
		desktopPane.setVisible(true);
		desktopPane.setBackground(Color.LIGHT_GRAY);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
                    .addGap(1, 1, 1)
        ));
        
		setupListeners();
		
		this.addWindowListener(new WindowAdapter() {
			 @Override
			 public void windowClosing(WindowEvent e) {
			     closeApp();
			 }
		 });	
		
	}
	
	
	@SuppressWarnings("unused")
	private void initFrames() throws PropertyVetoException{

        // Create and Add frames to main Panel
        swmmFrame = new EpaFrame();
        epanetFrame = new EpaFrame();
        hecRasFrame = new HecRasFrame();
        dbFrame = new DatabaseFrame();
        configFrame = new ConfigFrame();
        gisFrame = new GisFrame();
        gisFrame.setLocation(175, 80);
        
        desktopPane.add(swmmFrame);
        desktopPane.add(epanetFrame);
        desktopPane.add(hecRasFrame);     
        desktopPane.add(dbFrame);        
        desktopPane.add(configFrame);
        desktopPane.add(gisFrame);
        
        // Set specific configuration
		swmmFrame.setTitle("EPA SWMM");
		swmmFrame.getPanel().setDesignButton("Raingage", "showRaingage");
		swmmFrame.getPanel().setOptionsButton("Options", "showInpOptions");
		swmmFrame.getPanel().setReportButton("Report options", "showReport");
		swmmFrame.getPanel().setSubcatchmentVisible(true);
		epanetFrame.setTitle("EPANET");
		epanetFrame.getPanel().setDesignButton("Times values", "showTimesValues");
		epanetFrame.getPanel().setOptionsButton("Options", "showInpOptionsEpanet");
		epanetFrame.getPanel().setReportButton("Report options", "showReportEpanet");
		epanetFrame.getPanel().setSubcatchmentVisible(false);

        // Get info from properties
		getMainParams("MAIN");

        // Define one controller per panel           
		new HecRasController(hecRasFrame.getPanel(), this);
		new DatabaseController(dbFrame.getPanel(), this);
		new ConfigController(configFrame.getPanel());
        MainController mcSwmm = new MainController(swmmFrame.getPanel(), this, "EPASWMM");   
        MainController mcEpanet = new MainController(epanetFrame.getPanel(), this, "EPANET");
        
        boolean overwrite = Boolean.parseBoolean(prop.get("IMPORT_OVERWRITE", "false"));
        mnAnalysis.setEnabled(!overwrite);
		
	}
	
	
	public void updateTitle(String path){
		String title = BUNDLE.getString("MainFrame.this.title");
		if (versionCode != null){
			title+= " v"+versionCode;
		}
		title+= " - " + path;
		setTitle(title);
	}
	
	
	public void updateFrames(){

		try {
	        getFrameParams(dbFrame, "DB");      
	        getFrameParams(configFrame, "CONFIG");			
			getFrameParams(swmmFrame, "SWMM");
	        getFrameParams(epanetFrame, "EPANET");
	        getFrameParams(hecRasFrame, "HECRAS");
			// Only one frame remains visible
			boolean selected;
	        selected = Boolean.parseBoolean(MainDao.getGswProperties().get("EPANET_SELECTED", "false"));
			if (selected){
				swmmFrame.setVisible(false);
				hecRasFrame.setVisible(false);
				epanetFrame.setSelected(true);
				epanetFrame.setMaximum(true);			
			}
			selected = Boolean.parseBoolean(MainDao.getGswProperties().get("SWMM_SELECTED", "false"));
			if (selected){
				hecRasFrame.setVisible(false);
				epanetFrame.setVisible(false);
				swmmFrame.setSelected(true);
				swmmFrame.setMaximum(true);						
			}
	        selected = Boolean.parseBoolean(MainDao.getGswProperties().get("HECRAS_SELECTED", "false"));
			if (selected){
				swmmFrame.setVisible(false);
				epanetFrame.setVisible(false);
				hecRasFrame.setSelected(true);
				hecRasFrame.setMaximum(true);						
			}
			} catch (PropertyVetoException e) {
            Utils.logError(e.getMessage());
		}		
		
	}
	
	
	private void getFrameParams (JInternalFrame frame, String prefix) throws PropertyVetoException{

        int x, y;
        boolean visible;
        x = Integer.parseInt(MainDao.getGswProperties().get(prefix + "_X", "0"));
        y = Integer.parseInt(MainDao.getGswProperties().get(prefix + "_Y", "0"));
        visible = Boolean.parseBoolean(MainDao.getGswProperties().get(prefix + "_VISIBLE", "false"));
        //selected = Boolean.parseBoolean(MainDao.getGswProperties().get(prefix + "_SELECTED", "false"));
        frame.setLocation(x, y);
        frame.setVisible(visible);
        //frame.setSelected(selected);
		
	}
	
	
	private void putFrameParams (JInternalFrame frame, String prefix) throws PropertyVetoException{
		MainDao.getGswProperties().put(prefix + "_X", frame.getX());
		MainDao.getGswProperties().put(prefix + "_Y", frame.getY());
		MainDao.getGswProperties().put(prefix + "_VISIBLE", frame.isVisible());
		MainDao.getGswProperties().put(prefix + "_SELECTED", frame.isSelected());
	}
	
	
	private void getMainParams (String prefix) throws PropertyVetoException{

        int x, y, width, height;
        boolean maximized;
        x = Integer.parseInt(prop.get(prefix + "_X", "200"));
        y = Integer.parseInt(prop.get(prefix + "_Y", "50"));
        width = Integer.parseInt(prop.get(prefix + "_WIDTH", "800"));
        height = Integer.parseInt(prop.get(prefix + "_HEIGHT", "600"));
        maximized = Boolean.parseBoolean(prop.get(prefix + "_MAXIMIZED", "false"));
        this.setLocation(x, y);
        this.setSize(width, height);
        
        if (maximized){
        	this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
                
	}
	
	
	private void putMainParams (String prefix) throws PropertyVetoException{
		boolean maximized = (this.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0;
		prop.put(prefix + "_MAXIMIZED", maximized);		
		prop.put(prefix + "_X", this.getX());
		prop.put(prefix + "_Y", this.getY());
		prop.put(prefix + "_WIDTH", this.getWidth());
		prop.put(prefix + "_HEIGHT", this.getHeight());
		MainDao.savePropertiesFile(); 
	}	
	
	
	public void putEpaParams(String software, EpaPanel epaPanel){
    	MainDao.getGswProperties().put(software+"_FOLDER_SHP", epaPanel.getFolderShp());    	
    	MainDao.getGswProperties().put(software+"_FILE_INP", epaPanel.getFileInp());
    	MainDao.getGswProperties().put(software+"_FILE_RPT", epaPanel.getFileRpt());
    	MainDao.getGswProperties().put(software+"_PROJECT_NAME", epaPanel.getProjectName());    	
    	MainDao.getGswProperties().put(software+"_SCHEMA", epaPanel.getSelectedSchema());
    	if (epaPanel.getOptDatabaseSelected()){
    		MainDao.getGswProperties().put(software+"_STORAGE", "DATABASE");
    	}
    	else if (epaPanel.getOptDbfSelected()){
    		MainDao.getGswProperties().put(software+"_STORAGE", "DBF");
    	}
    	else{
    		MainDao.getGswProperties().put(software+"_STORAGE", "");
    	}
	}    
    
    public void putHecrasParams(){
    	HecRasPanel hecRasPanel = hecRasFrame.getPanel();
    	MainDao.getGswProperties().put("HECRAS_FILE_ASC", hecRasPanel.getFileAsc());
    	MainDao.getGswProperties().put("HECRAS_FILE_SDF", hecRasPanel.getFileSdf());
    	MainDao.getGswProperties().put("HECRAS_SCHEMA", hecRasPanel.getSelectedSchema());
	}	
    
    public void putDatabaseParams(){
    	DatabasePanel dbPanel = dbFrame.getPanel();
    	MainDao.getGswProperties().put("POSTGIS_HOST", dbPanel.getHost());
    	MainDao.getGswProperties().put("POSTGIS_PORT", dbPanel.getPort());
    	MainDao.getGswProperties().put("POSTGIS_DATABASE", dbPanel.getDatabase());
    	MainDao.getGswProperties().put("POSTGIS_USER", dbPanel.getUser());
    	MainDao.getGswProperties().put("POSTGIS_PASSWORD", Encryption.encrypt(dbPanel.getPassword()));
    	MainDao.getGswProperties().put("POSTGIS_REMEMBER", dbPanel.getRemember().toString());
    	//MainDao.getGswProperties().put("POSTGIS_AUTOSTART", "true");    	
    	MainDao.getGswProperties().put("POSTGIS_DATA", "");
    	MainDao.getGswProperties().put("POSTGIS_BIN", "");
	}	   
    
    public void putGisParams(){
    	GisPanel gisPanel = gisFrame.getPanel();
    	MainDao.getGswProperties().put("GIS_FOLDER", gisPanel.getProjectFolder());
    	MainDao.getGswProperties().put("GIS_NAME", gisPanel.getProjectName());
    	MainDao.getGswProperties().put("GIS_SOFTWARE", gisPanel.getProjectSoftware());
    	MainDao.getGswProperties().put("GIS_TYPE", gisPanel.getDataStorage());
    	MainDao.getGswProperties().put("GIS_SCHEMA", gisPanel.getSelectedSchema());
	}	
    
	
	public void saveGswFile(){

		// Update FILE_GSW parameter 
		prop.put("FILE_GSW", MainDao.getGswPath());
    	
		// Get EPANET and SWMM parameters
    	EpaPanel epanetPanel = epanetFrame.getPanel();
    	putEpaParams("EPANET", epanetPanel);
    	EpaPanel swmmPanel = swmmFrame.getPanel();        	
    	putEpaParams("EPASWMM", swmmPanel);      
    	
		// Get HECRAS parameters
		putHecrasParams();		
		
		// Get Database parameters
		putDatabaseParams();		
    	
    	// Get GIS parameters
    	putGisParams();
    	
    	MainDao.saveGswPropertiesFile();
        
	}	
	
	
	public void closeApp(){
	
        try {
			putFrameParams(swmmFrame, "SWMM");
	        putFrameParams(epanetFrame, "EPANET");
	        putFrameParams(hecRasFrame, "HECRAS");
	        putFrameParams(dbFrame, "DB");      
	        putFrameParams(configFrame, "CONFIG");	
	        putMainParams("MAIN");
	        saveGswFile();  
	    	Utils.getLogger().info("Application closed");	        
		} catch (PropertyVetoException e) {
            Utils.logError(e.getMessage());			
		}
		
	}
	
	
	private void setupListeners(){
		
		mntmOpenProject.addActionListener(this);
		mntmSaveProject.addActionListener(this);
		mntmOpenPreferences.addActionListener(this);
		mntmSavePreferences.addActionListener(this);
		mntmSaveAsPreferences.addActionListener(this);
		
		mntmSwmm.addActionListener(this);
		mntmEpanet.addActionListener(this);
		mntmHecras.addActionListener(this);
		
		mntmProjectId.addActionListener(this);		
		mntmArcCatalog.addActionListener(this);
		mntmMaterials.addActionListener(this);
		mntmPatterns.addActionListener(this);		
		mntmTimeseries.addActionListener(this);
		mntmCurves.addActionListener(this);

		mntmCatalog.addActionListener(this);		
		mntmManagement.addActionListener(this);
		
		mntmDatabase.addActionListener(this);
		mntmSoftware.addActionListener(this);

		mntmGisProject.addActionListener(this);		
		mntmSampleEpanet.addActionListener(this);
		mntmSampleEpaswmm.addActionListener(this);
		mntmSampleHecras.addActionListener(this);	
		mntmDatabaseAdministrator.addActionListener(this);		
		
		mntmWelcome.addActionListener(this);
		mntmLicense.addActionListener(this);		
		mntmAgreements.addActionListener(this);		
		mntmUserManual.addActionListener(this);
		mntmReferenceGuide.addActionListener(this);		
		mntmWeb.addActionListener(this);
		mntmCheckUpdates.addActionListener(this);
		
		mntmDownload.addActionListener(this);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		menuController.action(e.getActionCommand());
	}


	public void openSwmm() {
		manageFrames(swmmFrame);
	}	
	
	public void openEpanet() {
		manageFrames(epanetFrame);
	}	

	public void openHecras() {
		manageFrames(hecRasFrame);
	}	

	public void openDatabase() {
		manageFrames(dbFrame);
	}		

	public void openSoftware() {
		manageFrames(configFrame);
	}
	
	public void openGisProject() {
		manageFrames(gisFrame);
		gisFrame.setGisExtension("qgs");
		gisFrame.setGisTitle(Utils.getBundleString("gis_panel_qgis"));
		try {
			gisFrame.setMaximum(false);
		} catch (PropertyVetoException e) {
            Utils.logError(e);
		}		
	}	
	
	public void updateEpaFrames(){
		epanetFrame.getPanel().selectSourceType();
		swmmFrame.getPanel().selectSourceType();
	}
	
	public void enableCatalog(boolean enable) {
		
		mntmOpenProject.setEnabled(enable);
		mntmSaveProject.setEnabled(enable);
		mnData.setEnabled(enable);
		mnAnalysis.setEnabled(enable);
		mntmSampleEpanet.setEnabled(enable);
		mntmSampleEpaswmm.setEnabled(enable);
		mntmSampleHecras.setEnabled(enable);
		
	}
	
	public void enableProjectId(boolean enable) {
		mntmProjectId.setEnabled(enable);
	}
	
	public void enableConduit(boolean enable) {
		mntmArcCatalog.setEnabled(enable);
	}

	public void enableCurves(boolean enable) {
		mntmCurves.setEnabled(enable);
	}

	public void enableMaterials(boolean enable) {
		mntmMaterials.setEnabled(enable);
	}

	public void enablePatterns(boolean enable) {
		mntmPatterns.setEnabled(enable);
	}
	
	public void enableTimeseries(boolean enable) {
		mntmTimeseries.setEnabled(enable);
	}
	
	
	public void enableResultCat(boolean enable) {
		mntmCatalog.setEnabled(enable);
	}
	
	public void enableResultSelection(boolean enable) {
		mntmManagement.setEnabled(enable);
	}	
	
	
    private void manageFrames(JInternalFrame frame) {
    	
        try {   	
            frame.setMaximum(true);
            frame.setVisible(true); 
            frame.setMaximum(true);            
        } catch (PropertyVetoException e) {
            Utils.logError(e);
        }
        
    }


	public void setCursorFrames(Cursor cursor) {
		swmmFrame.getPanel().setCursor(cursor);
		epanetFrame.getPanel().setCursor(cursor);
		hecRasFrame.getPanel().setCursor(cursor);
		dbFrame.getPanel().setCursor(cursor);
		configFrame.getPanel().setCursor(cursor);
		gisFrame.getPanel().setCursor(cursor);
		this.setCursor(cursor);
	}
}