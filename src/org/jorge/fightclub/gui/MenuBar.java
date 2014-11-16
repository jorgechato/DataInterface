package org.jorge.fightclub.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 11/11/14.
 */
public class MenuBar extends JMenuBar implements ActionListener,ChangeListener{
    private JMenuBar jmb;
    private JMenu file,save;
    private JMenuItem manualSave,saveAs,importJson,exportJson, changePath;
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
        jmb.add(file);
        jmb.add(save);

        manualSave = new JMenuItem("Guardar");
        saveAs = new JMenuItem("Guardar como..");
        importJson = new JMenuItem("Importar Json");
        exportJson = new JMenuItem("Exportar a Json");
        changePath = new JMenuItem("Cambiar ruta");
        automaticSaved = new JCheckBoxMenuItem("Manual");

        manualSave.addActionListener(this);
        saveAs.addActionListener(this);
        importJson.addActionListener(this);
        exportJson.addActionListener(this);
        changePath.addActionListener(this);
        automaticSaved.addActionListener(this);

        file.add(manualSave);
        file.add(saveAs);
        file.add(importJson);
        file.add(exportJson);
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
            return;
        }
        if (actionEvent.getSource() == exportJson) {
            return;
        }
        if (actionEvent.getSource() == changePath) {
            changePath();
            //window.changePath();
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
    }

    /**
     * change path where you save the files.
     */
    public void changePath(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cambiar la ruta de guardado");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(this) != JFileChooser.CANCEL_OPTION){
            window.setNewPath(fileChooser.getSelectedFile().getAbsolutePath());
            window.getLoadLabel().setText("Has cambiado la ruta a "+fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void newFilePath(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como...");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt, *.bat)", "bat"));
        if (fileChooser.showSaveDialog(this) != JFileChooser.CANCEL_OPTION){
            window.setFileNewPath(fileChooser.getSelectedFile().getAbsolutePath());
            window.getLoadLabel().setText("Has cambiado la ruta a "+fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * control tag events even if you not change between tags.
     * @param changeEvent
     */
    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        changeTag = true;
        tag = window.getTabbedPane1().getSelectedIndex();
        //changeTags();
    }
/*
    public Integer changeTags(){
        int tag = window.getTabbedPane1().getSelectedIndex();
        switch (tag){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
        return tag;
    }*/
}
