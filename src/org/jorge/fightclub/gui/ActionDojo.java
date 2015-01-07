package org.jorge.fightclub.gui;

import org.jorge.fightclub.base.Boxer;
import org.jorge.fightclub.base.Coach;
import org.jorge.fightclub.base.Dojo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class with Dojo's own listeners
 * Created by jorge on 11/11/14.
 */
public class ActionDojo extends FatherAction{

    private Window window;
    private String fileName;
    private boolean canLoadData;
    private String consult;

    public ActionDojo(Window window){
        this.window = window;
        connection = window.getConnection();
        this.consult = " SELECT * FROM dojo ";
        fileName = "Dojo";
        canLoadData = true;
        setBtnew(window.getBtNewDojo());
        setBtsave(window.getBtSaveDojo());
        setBtchange(window.getBtChangeDojo());
        setBtdelete(window.getBtDeleteDojo());
        setBtcancel(window.getBtCancelDojo());
        setModel(window.getModelDojo());
        setList(window.getListDojo());
        setBefore(window.getBtBeforeDojo());
        setAfter(window.getBtAfterDojo());
        setFirst(window.getBtFirstDojo());
        setLast(window.getBtLastDojo());
        setLoadLabel(window.getLoadLabel());
        setFileName(fileName);
        setFullPath(fileName);
        loadDataBase();
        setArray((ArrayList<Dojo>) window.getArrayListDojo());
    }

    /**
     * method to control the events on dojo tag.
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == window.getBtNewDojo()) {
            canLoadData = false;
            newData();
            activateDeactivateButton(false);
            activateDeactivateEdition(true);
            return;
        }
        if (actionEvent.getSource() == window.getBtCancelDojo()) {
            canLoadData = true;
            setNew(true);
            navigate();
            activateDeactivateEdition(false);
            activateDeactivateButton(true);
            return;
        }
        if (actionEvent.getSource() == window.getBtChangeDojo()) {
            canLoadData = false;
            setNew(false);
            disableNavigation();

            activateDeactivateEdition(true);
            activateDeactivateButton(false);
            return;
        }
        if (actionEvent.getSource() == window.getBtDeleteDojo()){
            setManualSave(window.isManual());
            deleteData();
            return;
        }
        if (actionEvent.getSource() == window.getBtFirstDojo()) {
            setPos(0);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtLastDojo()){
            setPos(window.getArrayListDojo().size()-1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtAfterDojo()){
            setPos(getPos()+1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtBeforeDojo()){
            setPos(getPos()-1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtSaveDojo()){
            canLoadData = true;
            setManualSave(window.isManual());
            saveData();
            return;
        }
    }

    /**
     *  method to control the click events on dojo tag.
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == window.getListDojo()){
            if (navigateToJlist()){
                if (window.getModelDojo().isEmpty())
                    return;
                getPosDataSearch();
                navigate();
                loadData();
            }
            return;
        }
    }
    /**
     *  method to control the key events on dojo tag.
     * @param keyEvent
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getSource() == window.getSearchDojo()){
            findData();
            navigate();
            return;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        return;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        return;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        return;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        return;
    }

    @Override
    public void reloadModelData() {
        window.getModelDojo().clear();

        for (Dojo dojo : window.getArrayListDojo()){
            window.getModelDojo().addElement(dojo);
        }
    }

    @Override
    public void newData() {
        disableNavigation();
        setNew(true);
        window.getTxtNameDojo().setText("");
        window.getTxtStreetDojo().setText("");
        window.getDateDojo().setDate(null);

        window.getModelBoxerInDojo().removeAllElements();
        window.getModelCoachInDojo().removeAllElements();
    }

    @Override
    public void activateDeactivateEdition(boolean activate) {
        window.getTxtNameDojo().setEditable(activate);
        window.getTxtStreetDojo().setEditable(activate);
        window.getDateDojo().setEnabled(activate);
        window.getListBoxerInDojo().setEnabled(activate);
        window.getListCoachInDojo().setEnabled(activate);

        window.getSearchDojo().setEnabled(!activate);
        window.getListDojo().setEnabled(!activate);
    }

    @Override
    public void loadData() {
        if (!canLoadData)
            return;

        if (window.getArrayListDojo().size() == 0)
            return;

        loadListCoach();
        loadListBoxer();
        window.getTxtNameDojo().setText(window.getArrayListDojo().get(getPos()).getName());
        window.getTxtStreetDojo().setText(window.getArrayListDojo().get(getPos()).getStreet());
        window.getDateDojo().setDate(window.getArrayListDojo().get(getPos()).getInauguration());
    }

    private void loadListBoxer(){
        window.getModelBoxerInDojo().removeAllElements();

        if (window.getArrayListBoxer().size() == 0)
            return;

        for (Boxer boxer : window.getArrayListBoxer())
            if (boxer.getDojo()!=null)
                if (boxer.getDojo().toString().equals(window.getArrayListDojo().get(getPos()).toString()))
                    window.getModelBoxerInDojo().addElement(boxer);
    }

    private void loadListCoach(){
        window.getModelCoachInDojo().removeAllElements();

        if (window.getArrayListCoach().size() == 0) {
            return;
        }

        for (Coach coach : window.getArrayListCoach())
            if (coach.getDojo()!=null)
                if (coach.getDojo().toString().equals(window.getArrayListDojo().get(getPos()).toString()))
                    window.getModelCoachInDojo().addElement(coach);
    }

    @Override
    public void saveData() {
        Dojo dojo = new Dojo();

        if (window.getTxtNameDojo().getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "ERROR :: Nombre obligatorio", "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (isNew()) {
            insert();

            setPos(window.getArrayListDojo().size()-1);
        }else{
            if(JOptionPane.showConfirmDialog(null,"¿Estas seguro?",
                    "Modificar",JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
                navigate();
                reloadModelData();
                loadData();

                activateDeactivateButton(true);
                activateDeactivateEdition(false);
                return;
            }/*
            window.getArrayListDojo().get(getPos()).setName(window.getTxtNameDojo().getText());
            window.getArrayListDojo().get(getPos()).setStreet(window.getTxtStreetDojo().getText());
            window.getArrayListDojo().get(getPos()).setInauguration(window.getDateDojo().getDate());*/
            update();
        }

        navigate();
        reloadModelData();

        activateDeactivateButton(true);
        activateDeactivateEdition(false);

        if (!window.isManual())
            saveInFile();
        else
            window.getLoadLabel().setText("**Archivo no guardado, recuerda ir a Archivo>Guardar");
    }

    @Override
    public void findData() {
        window.getModelDojo().removeAllElements();
        this.consult = " SELECT * FROM dojo WHERE name LIKE '%"+window.getSearchDojo().getText()+"%' ";

        loadInFile();
        reloadModelData();
    }

    @Override
    public void insert() {
        String consult = " INSERT INTO dojo(name, street, inauguration) VALUES (?,?,?) ";
        PreparedStatement statement = null;
        Date date = window.getDateDojo().getDate();
        long current = Calendar.getInstance().getTimeInMillis();

        try {
            statement = connection.prepareStatement(consult);

            statement.setString(1, window.getTxtNameDojo().getText());
            statement.setString(2, window.getTxtStreetDojo().getText());
            if (window.getDateDojo().getDate() == null){
                statement.setDate(3, new java.sql.Date(current));
            }else {
                statement.setDate(3, new java.sql.Date(date.getTime()));
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        loadInFile();
    }

    @Override
    protected void deleteDatabase(Object object) {
        Dojo dojo = (Dojo) object;

        String consult = " DELETE FROM dojo WHERE id = ? ";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(consult);
            statement.setInt(1, dojo.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        window.loadSqlData();
    }

    @Override
    public void loadInFile() {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(consult);
            result = statement.executeQuery();

            window.setArrayListDojo(new ArrayList<Dojo>());
            while (result.next()){
                Date newDate = result.getTimestamp(4);
                Dojo dojo = new Dojo(result.getInt(1),result.getString(2),result.getString(3),
                        newDate);
                window.getArrayListDojo().add(dojo);
            }
            setArray((ArrayList<Dojo>) window.getArrayListDojo());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                    result.close();
                    window.getLoadLabel().setText("Datos de escuelas cargados automaticamente");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        this.consult = " SELECT * FROM dojo ";
        loadData();
    }

    @Override
    public void update(){
        String consultSql = "UPDATE dojo SET name = ? , street = ? , inauguration = ? WHERE id = ?";
        PreparedStatement sentencia = null ;
        Dojo dojo = (Dojo) getArray().get(getPos());
        Date date = window.getDateDojo().getDate();

        try {
            sentencia = connection.prepareStatement(consultSql);
            sentencia.setString(1,window.getTxtNameDojo().getText());
            sentencia.setString(2,window.getTxtStreetDojo().getText());
            sentencia.setDate(3, new java.sql.Date(date.getTime()));
            sentencia.setInt(4,dojo.getId());
            sentencia.executeUpdate();
        } catch ( SQLException e ) {e.getErrorCode();}
        finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException e) {e.getErrorCode();}
        }
        loadInFile();
    }


    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        loadData();
    }
}
