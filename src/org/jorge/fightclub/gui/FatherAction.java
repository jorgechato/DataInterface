package org.jorge.fightclub.gui;

import org.jorge.fightclub.base.Coach;
import org.jorge.fightclub.base.Dojo;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Father class to every single action tag. with abstract method and parameters method.
 * Created by jorge on 11/11/14.
 */
public abstract class FatherAction implements ActionListener,MouseListener,KeyListener,ChangeListener {

    private int pos;
    private boolean isNew;
    private JList list;
    private ArrayList<?> array;
    private JButton before, after, last, first, btnew, btsave, btchange, btdelete, btcancel;
    private DefaultListModel<?> model;
    private JLabel loadLabel;
    private String fileName,newPath,newFilePath,fullPath;
    private boolean manualSave;
    public static Connection connection;

    public FatherAction(){
        pos = 0;
        isNew = true;
        newPath = "";
        newFilePath = "";
    }

    public ArrayList<?> getArray() {
        return array;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath+".bat";
    }

    public void setNewFilePath(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public void setManualSave(boolean manualSave) {
        this.manualSave = manualSave;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLoadLabel(JLabel loadLabel) {
        this.loadLabel = loadLabel;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setList(JList list) {
        this.list = list;
    }

    public void setArray(ArrayList<?> array) {
        this.array = array;
    }

    public void setBefore(JButton before) {
        this.before = before;
    }

    public void setAfter(JButton after) {
        this.after = after;
    }

    public void setLast(JButton last) {
        this.last = last;
    }

    public void setFirst(JButton first) {
        this.first = first;
    }

    public void setBtnew(JButton btnew) {
        this.btnew = btnew;
    }

    public void setBtsave(JButton btsave) {
        this.btsave = btsave;
    }

    public void setBtchange(JButton btchange) {
        this.btchange = btchange;
    }

    public void setBtdelete(JButton btdelete) {
        this.btdelete = btdelete;
    }

    public void setBtcancel(JButton btcancel) {
        this.btcancel = btcancel;
    }

    public void setModel(DefaultListModel<?> model) {
        this.model = model;
    }

    public void disableNavigation(){
        before.setEnabled(false);
        last.setEnabled(false);
        first.setEnabled(false);
        after.setEnabled(false);
    }

    /**
     * method to navigate in the list of the different tags. You had to cast the List parameter
     * to Arraylist<¿> in setters.
     */
    public void navigate(){
        list.setSelectedIndex(pos);

        if (array.isEmpty() || array.size() == 1) {
            before.setEnabled(false);
            last.setEnabled(false);
            first.setEnabled(false);
            after.setEnabled(false);

            return;
        }

        if (array.size() >= 2){
            before.setEnabled(true);
            first.setEnabled(true);
            last.setEnabled(true);
            after.setEnabled(true);

            if (pos == array.size() - 1) {
                last.setEnabled(false);
                after.setEnabled(false);
            }

            if (pos == 0){
                before.setEnabled(false);
                first.setEnabled(false);
            }
        }
    }

    /**
     * activate or deactivate button by a boolean. Depend on buttons of the tag.
     * @param activate
     */
    public void activateDeactivateButton(boolean activate){
        btnew.setEnabled(activate);
        btsave.setEnabled(!activate);
        btchange.setEnabled(activate);
        btdelete.setEnabled(activate);
        btcancel.setEnabled(!activate);

        if (array.size() == 0){
            btdelete.setEnabled(false);
            btchange.setEnabled(false);
            newData();
        }
    }

    /**
     * Delete the selected item to the arrayList and then save the changes.
     */
    public void deleteData(){
        if(array.size() == 0){
            newData();
            model.clear();
            return;
        }
        if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
            return;
        }
        deleteDatabase(array.get(pos));
        loadInFile();

        if (pos > array.size()-1){
            pos = array.size()-1;
        }

        loadData();
        reloadModelData();
        navigate();

        activateDeactivateButton(true);
        activateDeactivateEdition(false);
        if (!manualSave)
            saveInFile();
        else
            loadLabel.setText("**Archivo no guardado, recuerda ir a Archivo>Guardar");

        if (array.size() == 0){
            btdelete.setEnabled(false);
            btchange.setEnabled(false);
            newData();
        }
    }

    protected abstract void deleteDatabase(Object object);

    /**
     * method to navigate in JList to each tag.
     * @return
     */
    public boolean navigateToJlist(){
        pos = list.getSelectedIndex();
        if (array.size() == 0)
            return false;

        return true;
    }

    /**
     * When you navigate to JList and you are searching some data, you need to check the real position of
     * data in array not the position on JList.
     */
    public void getPosDataSearch(){
        pos = array.indexOf(list.getSelectedValue());
        loadData();
    }

    /**
     * Generate a full path from change path to save the files
     */
    public void parseNewPath(){
        fullPath = fileName+".bat";
        if (!newPath.equals(""))
            fullPath = newPath+File.separator+fileName+".bat";
    }
    /**
     * Generate a full path from save as to save the files
     */
    public void parseNewFilePath(){
        fullPath = fileName+".bat";
        if (!newFilePath.equals(""))
            fullPath = newFilePath+".bat";
    }
    /**
     * save the array of each tag in file.
     */
    public void saveInFile(){
        try {
            ObjectOutputStream printInFile = new ObjectOutputStream(new FileOutputStream(fullPath));
            printInFile.writeObject(array);
            if (!manualSave)
                loadLabel.setText("Datos guardados automaticamente en "+fullPath);
            else
                loadLabel.setText("Datos gardados correctamente en directorio "+newPath);
        } catch (IOException e) {
            loadLabel.setText(e.getMessage());
        }
    }

    /**
     * next step to load data from file. part 2/2
     */
    public void loadDataBase(){
        loadInFile();
        if (array == null)
            return;
        navigate();
        loadData();
        reloadModelData();
        activateDeactivateButton(true);
        activateDeactivateEdition(false);
    }

    public Dojo getDojoId(int id){
        Dojo dojo = null;
        String consult = " SELECT * FROM dojo WHERE id = " + id;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(consult);
            result = statement.executeQuery();
            result.last();

            if (dojo != null){
                Date newDate = result.getTimestamp(4);
                dojo = new Dojo(result.getInt(1),result.getString(2),result.getString(3),
                        newDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return dojo;
    }

    public Coach getCoachId(int id){
        String consult = " SELECT * FROM coach WHERE id = " + id;
        PreparedStatement statement = null;
        ResultSet result = null;
        Coach coach = null;
        try {
            statement = connection.prepareStatement(consult);
            result = statement.executeQuery();
            result.last();
            if (coach != null) {
                Date newDate = result.getTimestamp(3);
                coach = new Coach(result.getInt(1), result.getString(2), newDate, result.getInt(4),
                        getDojoId(result.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return coach;
    }

    /**
     * load the data from a file .bat in each arrayList. part 1/2
     */
    public abstract void loadInFile();
    /**
     * Load data in JList of each tag
     */
    public abstract void reloadModelData();
    /**
     * Initialize every box to empty.
     */
    public abstract void newData();
    /**
     * activate or deactivate edition mode.
     * @param activate
     */
    public abstract void activateDeactivateEdition(boolean activate);
    /**
     * load data in each box.
     */
    public abstract void loadData();
    /**
     * Save the data in the arrayList.
     */
    public abstract void saveData();

    /**
     * find data with the name and other element in every tag.
     */
    public abstract void findData();

    public abstract void insert();

    /**
     * load the data when you change between tag.
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {}
}
