package org.jorgechato.fightclub;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 12/02/15.
 */
public class MenuBar extends JMenuBar implements ActionListener {
    private JMenuBar jmb;
    private JMenu file;
    private JMenuItem importJson,exportJson;
    private Window window;
    private JFileChooser fileChooser;

    public MenuBar(){}

    public JMenuBar menuBar(){
        jmb = new JMenuBar();
        file = new JMenu("Archivo");
        jmb.add(file);
        importJson = new JMenuItem("Importar");
        exportJson = new JMenuItem("Exportar");

        importJson.addActionListener(this);
        exportJson.addActionListener(this);

        file.add(importJson);
        file.add(exportJson);

        return jmb;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == importJson) {
            importFromJson();
            window.importFromJson();
            return;
        }
        if (actionEvent.getSource() == exportJson) {
//            newFileJson();
//            window.exportToJson();
            return;
        }
    }

    public void importFromJson(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importar escuelas desde JSON");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        window.setJsonPath(fileChooser.getSelectedFile().getAbsolutePath());
    }

    public void newFileJson(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar como JSON...");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        window.setJsonPath(fileChooser.getSelectedFile().getAbsolutePath());
    }
}
