package org.jorge.fightclub.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 11/11/14.
 */
public class MenuBar extends JMenuBar implements ActionListener,ChangeListener{
    private JMenuBar jmb;
    private JMenu file,save;
    private JMenuItem manualSave,saveAs,importJson,exportJson,changeLoad;
    private JCheckBoxMenuItem automaticSaved;
    private Window window;
    private String labelok;

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
        changeLoad = new JMenuItem("Cambiar ruta");
        automaticSaved = new JCheckBoxMenuItem("Manual");

        manualSave.addActionListener(this);
        saveAs.addActionListener(this);
        importJson.addActionListener(this);
        exportJson.addActionListener(this);
        changeLoad.addActionListener(this);
        automaticSaved.addActionListener(this);

        file.add(manualSave);
        file.add(saveAs);
        file.add(importJson);
        file.add(exportJson);
        save.add(automaticSaved);
        save.add(changeLoad);

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
            //window.getLoadLabel().setText(labelok);
            return;
        }
        if (actionEvent.getSource() == importJson) {
            return;
        }
        if (actionEvent.getSource() == exportJson) {
            return;
        }
        if (actionEvent.getSource() == changeLoad) {
            return;
        }
        if (actionEvent.getSource() == automaticSaved) {
            if (!automaticSaved.isSelected()){
                manualSave.setEnabled(false);
                window.setManual(false);
                window.getLoadLabel().setText("Guardado manual activado");
            }else{
                manualSave.setEnabled(true);
                window.setManual(true);
                window.getLoadLabel().setText("Guardado auntomatico activado");
            }
            return;
        }
        if (actionEvent.getSource() == save){

            return;
        }
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
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
    }
}
