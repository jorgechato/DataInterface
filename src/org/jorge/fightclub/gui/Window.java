package org.jorge.fightclub.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toedter.calendar.JDateChooser;
import org.jorge.fightclub.base.Boxer;
import org.jorge.fightclub.base.Coach;
import org.jorge.fightclub.base.Dojo;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.*;

/**
 * Main Class
 * Created by jorge on 10/11/14.
 */
public class Window extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JLabel loadLabel;
    private JTextField txtNameDojo;
    private JTextField txtStreetDojo;
    private JList listCoachInDojo;
    private JList listBoxerInDojo;
    private JButton btNewDojo;
    private JButton btSaveDojo;
    private JButton btChangeDojo;
    private JButton btCancelDojo;
    private JButton btDeleteDojo;
    private JButton btFirstDojo;
    private JButton btBeforeDojo;
    private JButton btAfterDojo;
    private JButton btLastDojo;
    private JTextField txtNameCoach;
    private JTextField txtYearCoach;
    private JComboBox cbDojoInCoach;
    private JList listBoxerInCoach;
    private JTextField searchDojo;
    private JList listDojo;
    private JDateChooser dateCoach;
    private JList listCoach;
    private JTextField searchCoach;
    private JButton btNewCoach;
    private JButton btSaveCoach;
    private JButton btChangeCoach;
    private JButton btDeleteCoach;
    private JButton btCancelCoach;
    private JButton btFirstCoach;
    private JButton btBeforeCoach;
    private JButton btAfterCoach;
    private JButton btLastCoach;
    private JTextField txtNameBoxer;
    private JTextField txtWinBoxer;
    private JTextField txtLoseBoxer;
    private JTextField txtWeightBoxer;
    private JComboBox cbCoachInBoxer;
    private JComboBox cbDojoInBoxer;
    private JButton btNewBoxer;
    private JButton btSaveBoxer;
    private JButton btChangeBoxer;
    private JButton btDeleteBoxer;
    private JButton btCancelBoxer;
    private JButton btFirstBoxer;
    private JButton btBeforeBoxer;
    private JButton btAfterBoxer;
    private JButton btLastBoxer;
    private JList listBoxer;
    private JTextField searchBoxer;
    private JDateChooser dateDojo;

    private List<Dojo> arrayListDojo;
    private List<Coach> arrayListCoach;
    private List<Boxer> arrayListBoxer;

    private DefaultListModel<Dojo> modelDojo;
    private DefaultListModel<Coach> modelCoach,modelCoachInDojo;
    private DefaultListModel<Boxer> modelBoxer,modelBoxerInCoach,modelBoxerInDojo;

    private static MenuBar menuBar;

    private boolean manual;

    private String newPath,fileNewPath,jsonPath;

    private ActionBoxer actionBoxer;
    private ActionCoach actionCoach;
    private ActionDojo actionDojo;
    private Connection connection;

    private java.lang.reflect.Type type;

    private String usr,pass;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Window");
        menuBar = new MenuBar();
        Window window = new Window();
        frame.setJMenuBar(menuBar.menuBar());
        menuBar.setWindow(window);
        frame.setContentPane(window.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * method to initialize all variable and set default values.
     */
    public void initialize(){
        dateDojo.setEnabled(false);
        dateCoach.setEnabled(false);

        arrayListBoxer = new ArrayList<Boxer>();
        arrayListDojo = new ArrayList<Dojo>();
        arrayListCoach = new ArrayList<Coach>();

        modelBoxer = new DefaultListModel<Boxer>();
        modelBoxerInCoach = new DefaultListModel<Boxer>();
        modelBoxerInDojo = new DefaultListModel<Boxer>();
        modelCoach = new DefaultListModel<Coach>();
        modelCoachInDojo = new DefaultListModel<Coach>();
        modelDojo = new DefaultListModel<Dojo>();

        listBoxer.setModel(modelBoxer);
        listBoxerInCoach.setModel(modelBoxerInCoach);
        listBoxerInDojo.setModel(modelBoxerInDojo);
        listCoach.setModel(modelCoach);
        listCoachInDojo.setModel(modelCoachInDojo);
        listDojo.setModel(modelDojo);

        manual = false;
        newPath = "";
        fileNewPath = "";
        jsonPath = "";

        usr = "root";
        pass = "2015**Luz";
    }

    public Window(){
        initialize();
        connect();
        configureListeners();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fightclub", this.usr, this.pass);
            loadLabel.setText("Conexion establecida");
        } catch (ClassNotFoundException  e) {
            loadLabel.setText(e.getMessage());
        } catch (SQLException e) {
            loadLabel.setText(e.getMessage());
        }
    }

    /**
     * create my own listeners
     */
    public void configureListeners(){
        actionBoxer = new ActionBoxer(this);
        actionCoach = new ActionCoach(this);
        actionDojo = new ActionDojo(this);

        //action button
        btNewBoxer.addActionListener(actionBoxer);
        btCancelBoxer.addActionListener(actionBoxer);
        btSaveBoxer.addActionListener(actionBoxer);
        btChangeBoxer.addActionListener(actionBoxer);
        btDeleteBoxer.addActionListener(actionBoxer);

        btNewDojo.addActionListener(actionDojo);
        btCancelDojo.addActionListener(actionDojo);
        btSaveDojo.addActionListener(actionDojo);
        btChangeDojo.addActionListener(actionDojo);
        btDeleteDojo.addActionListener(actionDojo);

        btNewCoach.addActionListener(actionCoach);
        btCancelCoach.addActionListener(actionCoach);
        btSaveCoach.addActionListener(actionCoach);
        btChangeCoach.addActionListener(actionCoach);
        btDeleteCoach.addActionListener(actionCoach);
        //navigate button
        btFirstBoxer.addActionListener(actionBoxer);
        btLastBoxer.addActionListener(actionBoxer);
        btAfterBoxer.addActionListener(actionBoxer);
        btBeforeBoxer.addActionListener(actionBoxer);

        btFirstDojo.addActionListener(actionDojo);
        btLastDojo.addActionListener(actionDojo);
        btAfterDojo.addActionListener(actionDojo);
        btBeforeDojo.addActionListener(actionDojo);

        btFirstCoach.addActionListener(actionCoach);
        btLastCoach.addActionListener(actionCoach);
        btBeforeCoach.addActionListener(actionCoach);
        btAfterCoach.addActionListener(actionCoach);
        //JList navigate
        listDojo.addMouseListener(actionDojo);
        listCoach.addMouseListener(actionCoach);
        listBoxer.addMouseListener(actionBoxer);
        //TextArea search
        searchDojo.addKeyListener(actionDojo);
        searchCoach.addKeyListener(actionCoach);
        searchBoxer.addKeyListener(actionBoxer);
        //menuBar
        tabbedPane1.addChangeListener(menuBar);
        tabbedPane1.addChangeListener(actionDojo);
        tabbedPane1.addChangeListener(actionCoach);
        tabbedPane1.addChangeListener(actionBoxer);
    }

    /**
     * save manually tags. it saved in local directory
     * @param tag
     */
    public void manualSave(int tag){
        switch (tag){
            case 0:
                actionDojo.setNewPath(newPath);
                actionDojo.parseNewPath();
                actionDojo.saveInFile();
                break;
            case 1:
                actionCoach.setNewPath(newPath);
                actionCoach.parseNewPath();
                actionCoach.saveInFile();
                break;
            case 2:
                actionBoxer.setNewPath(newPath);
                actionBoxer.parseNewPath();
                actionBoxer.saveInFile();
                break;
            default:
                break;
        }
    }

    /**
     * save as file. Save the file where ever you like.
     * @param tag
     */
    public void saveAsFile(int tag){
        switch (tag){
            case 0:
                actionDojo.setNewFilePath(fileNewPath);
                actionDojo.parseNewFilePath();
                actionDojo.saveInFile();
                break;
            case 1:
                actionCoach.setNewFilePath(fileNewPath);
                actionCoach.parseNewFilePath();
                actionCoach.saveInFile();
                break;
            case 2:
                actionBoxer.setNewFilePath(fileNewPath);
                actionBoxer.parseNewFilePath();
                actionBoxer.saveInFile();
                break;
            default:
                break;
        }
    }

    public void loadSqlData(){
        actionDojo.setPos(0);
        actionDojo.loadInFile();
        actionCoach.setPos(0);
        actionCoach.loadInFile();
        actionBoxer.setPos(0);
        actionBoxer.loadInFile();
    }

    public void procedureLoad(){
        actionDojo.setNew(true);
        actionDojo.navigate();
        actionDojo.activateDeactivateEdition(false);
        actionDojo.activateDeactivateButton(true);

        actionCoach.setNew(true);
        actionCoach.navigate();
        actionCoach.activateDeactivateEdition(false);
        actionCoach.activateDeactivateButton(true);

        actionBoxer.setNew(true);
        actionBoxer.navigate();
        actionBoxer.activateDeactivateEdition(false);
        actionBoxer.activateDeactivateButton(true);
    }

    /**
     * export data to JSON file, it depends on which tag you are.
     * @param tag
     */
    public void exportToJson(int tag){
        String name = "", json="";
        Gson gson = new Gson();

        switch (tag){
            case 0:
                name = "las escuelas";
                json = gson.toJson(arrayListDojo);
                break;
            case 1:
                name = "los coach";
                json = gson.toJson(arrayListCoach);
                break;
            case 2:
                name = "los boxeadores";
                json = gson.toJson(arrayListBoxer);
                break;
            default:
                break;
        }
        try {
            FileWriter fileWriter = new FileWriter(jsonPath+".json");
            fileWriter.write(json);
            fileWriter.close();
            loadLabel.setText("Has exportado "+name+" a Json");
        } catch (IOException e) {
            loadLabel.setText(e.getMessage());
        }
    }

    /**
     * Import JSON file. Only import Dojo's data.
     */
    public void importFromJson(){
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader(jsonPath);

            type = new TypeToken<ArrayList<Dojo>>(){}.getType();
            ArrayList<Dojo> newListDojo= gson.fromJson(fileReader,type);

            fileReader.close();

            getArrayListDojo().addAll(newListDojo);
            actionDojo.loadData();
            actionDojo.navigate();
            actionDojo.reloadModelData();

            loadLabel.setText("Datos de escuelas importados correctamente");
        } catch (FileNotFoundException e) {
            loadLabel.setText(e.getMessage());
        } catch (IOException e) {
            loadLabel.setText(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public void setFileNewPath(String fileNewPath) {
        this.fileNewPath = fileNewPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JLabel getLoadLabel() {
        return loadLabel;
    }

    public JTextField getTxtNameDojo() {
        return txtNameDojo;
    }

    public JTextField getTxtStreetDojo() {
        return txtStreetDojo;
    }

    public JList getListCoachInDojo() {
        return listCoachInDojo;
    }

    public JList getListBoxerInDojo() {
        return listBoxerInDojo;
    }

    public JButton getBtNewDojo() {
        return btNewDojo;
    }

    public JButton getBtSaveDojo() {
        return btSaveDojo;
    }

    public JButton getBtChangeDojo() {
        return btChangeDojo;
    }

    public JButton getBtCancelDojo() {
        return btCancelDojo;
    }

    public JButton getBtDeleteDojo() {
        return btDeleteDojo;
    }

    public JButton getBtFirstDojo() {
        return btFirstDojo;
    }

    public JButton getBtBeforeDojo() {
        return btBeforeDojo;
    }
    public JButton getBtAfterDojo() {
        return btAfterDojo;
    }

    public JButton getBtLastDojo() {
        return btLastDojo;
    }

    public JTextField getTxtNameCoach() {
        return txtNameCoach;
    }

    public JTextField getTxtYearCoach() {
        return txtYearCoach;
    }

    public JComboBox getCbDojoInCoach() {
        return cbDojoInCoach;
    }

    public JList getListBoxerInCoach() {
        return listBoxerInCoach;
    }

    public JTextField getSearchDojo() {
        return searchDojo;
    }

    public JList getListDojo() {
        return listDojo;
    }

    public JDateChooser getDateCoach() {
        return dateCoach;
    }

    public JList getListCoach() {
        return listCoach;
    }

    public JTextField getSearchCoach() {
        return searchCoach;
    }

    public JButton getBtNewCoach() {
        return btNewCoach;
    }

    public JButton getBtSaveCoach() {
        return btSaveCoach;
    }

    public JButton getBtChangeCoach() {
        return btChangeCoach;
    }

    public JButton getBtDeleteCoach() {
        return btDeleteCoach;
    }

    public JButton getBtCancelCoach() {
        return btCancelCoach;
    }

    public JButton getBtFirstCoach() {
        return btFirstCoach;
    }

    public JButton getBtBeforeCoach() {
        return btBeforeCoach;
    }

    public JButton getBtAfterCoach() {
        return btAfterCoach;
    }

    public JButton getBtLastCoach() {
        return btLastCoach;
    }

    public JTextField getTxtNameBoxer() {
        return txtNameBoxer;
    }

    public JTextField getTxtWinBoxer() {
        return txtWinBoxer;
    }

    public JTextField getTxtLoseBoxer() {
        return txtLoseBoxer;
    }

    public JTextField getTxtWeightBoxer() {
        return txtWeightBoxer;
    }

    public JComboBox getCbCoachInBoxer() {
        return cbCoachInBoxer;
    }

    public JComboBox getCbDojoInBoxer() {
        return cbDojoInBoxer;
    }

    public JButton getBtNewBoxer() {
        return btNewBoxer;
    }

    public JButton getBtSaveBoxer() {
        return btSaveBoxer;
    }

    public JButton getBtChangeBoxer() {
        return btChangeBoxer;
    }

    public JButton getBtDeleteBoxer() {
        return btDeleteBoxer;
    }

    public JButton getBtCancelBoxer() {
        return btCancelBoxer;
    }

    public JButton getBtFirstBoxer() {
        return btFirstBoxer;
    }

    public JButton getBtBeforeBoxer() {
        return btBeforeBoxer;
    }

    public JButton getBtAfterBoxer() {
        return btAfterBoxer;
    }

    public JButton getBtLastBoxer() {
        return btLastBoxer;
    }

    public JList getListBoxer() {
        return listBoxer;
    }

    public JTextField getSearchBoxer() {
        return searchBoxer;
    }

    public JDateChooser getDateDojo() {
        return dateDojo;
    }

    public List<Dojo> getArrayListDojo() {
        return arrayListDojo;
    }

    public void setArrayListDojo(List<Dojo> arrayListDojo) {
        this.arrayListDojo = arrayListDojo;
    }

    public List<Coach> getArrayListCoach() {
        return arrayListCoach;
    }

    public void setArrayListCoach(List<Coach> arrayListCoach) {
        this.arrayListCoach = arrayListCoach;
    }

    public List<Boxer> getArrayListBoxer() {
        return arrayListBoxer;
    }

    public void setArrayListBoxer(List<Boxer> arrayListBoxer) {
        this.arrayListBoxer = arrayListBoxer;
    }

    public DefaultListModel<Dojo> getModelDojo() {
        return modelDojo;
    }

    public DefaultListModel<Coach> getModelCoach() {
        return modelCoach;
    }

    public DefaultListModel<Coach> getModelCoachInDojo() {
        return modelCoachInDojo;
    }

    public DefaultListModel<Boxer> getModelBoxer() {
        return modelBoxer;
    }

    public DefaultListModel<Boxer> getModelBoxerInCoach() {
        return modelBoxerInCoach;
    }

    public DefaultListModel<Boxer> getModelBoxerInDojo() {
        return modelBoxerInDojo;
    }
}
