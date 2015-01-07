package org.jorge.fightclub.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menu bar Class
 * Created by jorge on 11/11/14.
 */
public class MenuBar extends JMenuBar implements ActionListener,ChangeListener{
    private JMenuBar jmb;
    private JMenu file,save,importE;
    private JMenuItem manualSave,saveAs,importJson,exportJson, changePath,connect;
    private JCheckBoxMenuItem automaticSaved;
    private Window window;
    private JFileChooser fileChooser;
    private boolean changeTag;
    private int tag;

    public MenuBar(){}

    public void setWindow(Window window) {
        this.window = window;
    }

    /**
     * Create menuBar with 2 menus. One with 3 items and another with 2 items.
     */
    public JMenuBar menuBar(){
        jmb = new JMenuBar();
        file = new JMenu("Archivo");
        save = new JMenu("Guardado");
        importE = new JMenu("Importar Json");
        jmb.add(file);
        jmb.add(save);

        manualSave = new JMenuItem("Guardar");
        saveAs = new JMenuItem("Guardar como..");
        importJson = new JMenuItem("Escuela");
        exportJson = new JMenuItem("Exportar a Json");
        changePath = new JMenuItem("Cambiar ruta");
        automaticSaved = new JCheckBoxMenuItem("Manual");
        connect = new JMenuItem("Conectar");

        manualSave.addActionListener(this);
        saveAs.addActionListener(this);
        importJson.addActionListener(this);
        exportJson.addActionListener(this);
        changePath.addActionListener(this);
        automaticSaved.addActionListener(this);
        connect.addActionListener(this);

        file.add(manualSave);
        file.add(saveAs);
        file.add(importE);
        importE.add(importJson);
        file.add(exportJson);
        file.add(connect);
        save.add(automaticSaved);
        save.add(changePath);

        tag = 0;
        manualSave.setEnabled(false);
        return jmb;
    }

    /**
     * Listening click events in menu bar button, then call a method to each button.
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == saveAs) {
            newFilePath();
            window.saveAsFile(tag);
            return;
        }
        if (actionEvent.getSource() == importJson) {
            importFromJson();
            window.importFromJson();
            return;
        }
        if (actionEvent.getSource() == exportJson) {
            newFileJson();
            window.exportToJson(tag);
            return;
        }
        if (actionEvent.getSource() == changePath) {
            changePath();
            return;
        }
        if (actionEvent.getSource() == automaticSaved) {
            if (!automaticSaved.isSelected()){
                manualSave.setEnabled(false);
                window.setManual(false);
                window.getLoadLabel().setText("Guardado automatico activado");
            }else{
                manualSave.setEnabled(true);
                window.setManual(true);
                window.getLoadLabel().setText("Guardado manual activado");
            }
            return;
        }
        if (actionEvent.getSource() == manualSave){
            window.manualSave(tag);
            return;
        }
        if (actionEvent.getSource() == connect){
            userPass();
            return;
        }
    }

    private void userPass() {
        window.setUsr("root");
        window.setPass("2015**Luz");
        String usr = "", pass = "";
        if ((usr = JOptionPane.showInputDialog(null,"usuario","usuario",JOptionPane.PLAIN_MESSAGE))==null)
            return;
        if (!usr.equals(""))
            window.setUsr(usr);
        if ((pass = JOptionPane.showInputDialog(null,"pass","pass",JOptionPane.PLAIN_MESSAGE))==null)
            return;
        if (!pass.equals(""))
            window.setPass(pass);

        window.connect();
//        window.loadSqlData();
    }
    /**
     * change path where you save the files.
     */
    public void changePath(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cambiar la ruta de guardado");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        window.setNewPath(fileChooser.getSelectedFile().getAbsolutePath());
        window.getLoadLabel().setText("Has cambiado la ruta a "+fileChooser.getSelectedFile().getAbsolutePath());

    }

    /**
     * Set new file path; save as the file.
     */
    public void newFilePath(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como...");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt, *.bat)", "bat"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        window.setFileNewPath(fileChooser.getSelectedFile().getAbsolutePath());
        window.getLoadLabel().setText("Has cambiado la ruta a "+fileChooser.getSelectedFile().getAbsolutePath());
    }

    /**
     * chose path to export to JSON.
     */
    public void newFileJson(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar como JSON...");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        window.setJsonPath(fileChooser.getSelectedFile().getAbsolutePath());
    }

    /**
     * Select JSON path to import in dojo's array.
     */
    public void importFromJson(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importar escuelas desde JSON");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        window.setJsonPath(fileChooser.getSelectedFile().getAbsolutePath());
    }
    /**
     * control tag events even if you not change between tags.
     * @param changeEvent
     */
    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        changeTag = true;
        tag = window.getTabbedPane1().getSelectedIndex();
    }
}
