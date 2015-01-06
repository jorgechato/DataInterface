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
import java.util.Date;

/**
 * Class with Coach's own listeners
 * Created by jorge on 11/11/14.
 */
public class ActionCoach extends FatherAction{

    private Window window;
    private String fileName;
    private boolean canLoadData;

    public ActionCoach(Window window){
        this.window = window;
        connection = window.getConnection();
        fileName = "Coach";
        canLoadData = true;
        setBtnew(window.getBtNewCoach());
        setBtsave(window.getBtSaveCoach());
        setBtchange(window.getBtChangeCoach());
        setBtdelete(window.getBtDeleteCoach());
        setBtcancel(window.getBtCancelCoach());
        setModel(window.getModelCoach());
        setList(window.getListCoach());
        setBefore(window.getBtBeforeCoach());
        setAfter(window.getBtAfterCoach());
        setFirst(window.getBtFirstCoach());
        setLast(window.getBtLastCoach());
        setLoadLabel(window.getLoadLabel());
        setFileName(fileName);
        setFullPath(fileName);
        loadDataBase();
        setArray((ArrayList<Coach>) window.getArrayListCoach());
    }

    /**
     * method to control the events on Coach tag.
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == window.getBtNewCoach()) {
            canLoadData = false;
            newData();
            activateDeactivateButton(false);
            activateDeactivateEdition(true);
            return;
        }
        if (actionEvent.getSource() == window.getBtCancelCoach()) {
            canLoadData = true;
            setNew(true);
            navigate();
            activateDeactivateEdition(false);
            activateDeactivateButton(true);
            return;
        }
        if (actionEvent.getSource() == window.getBtChangeCoach()) {
            canLoadData = false;
            setNew(false);
            disableNavigation();

            activateDeactivateEdition(true);
            activateDeactivateButton(false);
            return;
        }
        if (actionEvent.getSource() == window.getBtDeleteCoach()){
            setManualSave(window.isManual());
            deleteData();
            return;
        }
        if (actionEvent.getSource() == window.getBtFirstCoach()) {
            setPos(0);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtLastCoach()){
            setPos(window.getArrayListCoach().size()-1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtAfterCoach()){
            setPos(getPos()+1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtBeforeCoach()){
            setPos(getPos()-1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtSaveCoach()){
            canLoadData = true;
            setManualSave(window.isManual());
            saveData();
            return;
        }
    }

    /**
     *  method to control the click events on Coach tag.
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == window.getListCoach()){
            if (navigateToJlist()){
                if (window.getModelCoach().isEmpty())
                    return;
                getPosDataSearch();
                navigate();
                loadData();
            }
            return;
        }
    }
    /**
     *  method to control the key events on Coach tag.
     * @param keyEvent
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getSource() == window.getSearchCoach()){
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
    public void loadInFile() {
        String consult = " SELECT * FROM coach ";
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(consult);
            result = statement.executeQuery();

            window.setArrayListCoach(new ArrayList<Coach>());
            while (result.next()){
                Date newDate = result.getTimestamp(3);
                Coach coach = new Coach(result.getInt(1),result.getString(2), newDate, result.getInt(4),
                        getDojoId(result.getInt(5)));
                window.getArrayListCoach().add(coach);
            }
            setArray((ArrayList<Coach>) window.getArrayListCoach());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                    result.close();
                    window.getLoadLabel().setText("Datos de coach cargados automaticamente");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void reloadModelData() {
        window.getModelCoach().clear();

        for (Coach Coach : window.getArrayListCoach()){
            window.getModelCoach().addElement(Coach);
        }
    }

    @Override
    public void newData() {
        disableNavigation();
        setNew(true);
        window.getTxtNameCoach().setText("");
        window.getTxtYearCoach().setText("");
        window.getDateCoach().setDate(null);

        loadComboBox();
        window.getModelBoxerInCoach().removeAllElements();
    }

    public void loadComboBox(){
        window.getCbDojoInCoach().removeAllItems();
        if (window.getArrayListDojo().isEmpty()) {
            window.getCbDojoInCoach().addItem(null);
            return;
        }
        if (window.getArrayListCoach().size() != 0 && null == window.getArrayListCoach().get(getPos()).getDojo())
            window.getCbDojoInCoach().addItem(null);
        for (Dojo dojo : window.getArrayListDojo())
            window.getCbDojoInCoach().addItem(dojo.toString());

    }

    @Override
    public void activateDeactivateEdition(boolean activate) {
        window.getTxtNameCoach().setEditable(activate);
        window.getTxtYearCoach().setEditable(activate);
        window.getDateCoach().setEnabled(activate);
        window.getListBoxerInCoach().setEnabled(activate);
        window.getCbDojoInCoach().setEnabled(activate);

        window.getSearchCoach().setEnabled(!activate);
        window.getListCoach().setEnabled(!activate);
    }

    @Override
    public void loadData() {
        if (!canLoadData)
            return;

        if (window.getArrayListCoach().size() == 0)
            return;

        window.getTxtNameCoach().setText(window.getArrayListCoach().get(getPos()).getName());
        window.getTxtYearCoach().setText(String.valueOf(window.getArrayListCoach().get(getPos()).getYears()));
        window.getDateCoach().setDate(window.getArrayListCoach().get(getPos()).getBirthday());
        loadListBoxer();
        loadComboBox();
        if (null == window.getArrayListCoach().get(getPos()).getDojo())
            return;
        window.getCbDojoInCoach().setSelectedItem(window.getArrayListCoach().get(getPos()).getDojo().toString());

    }

    private void loadListBoxer(){
        window.getModelBoxerInCoach().removeAllElements();

        if (window.getArrayListBoxer().size() == 0)
            return;
        for (Boxer boxer : window.getArrayListBoxer())
            if (boxer.getCoach()!=null)
                if (boxer.getCoach().toString().equals(window.getArrayListCoach().get(getPos()).toString()))
                    window.getModelBoxerInCoach().addElement(boxer);
    }

    @Override
    public void saveData() {
        Coach coach = new Coach();

        if (window.getTxtNameCoach().getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "ERROR :: Nombre obligatorio", "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (window.getTxtYearCoach().getText().equalsIgnoreCase("")){
            window.getTxtYearCoach().setText("0");
        }

        if (isNew()) {
            coach.setName(window.getTxtNameCoach().getText());
            try {
                coach.setYears(Integer.parseInt(window.getTxtYearCoach().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Experiencia en años tiene que ser un entero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            coach.setBirthday(window.getDateCoach().getDate());

            Dojo selectedDojo = null;
            for (Dojo dojo : window.getArrayListDojo())
                if(window.getCbDojoInCoach().getSelectedItem().equals(dojo.toString()))
                    selectedDojo = dojo;
            coach.setDojo(selectedDojo);

            window.getArrayListCoach().add(coach);

            setPos(window.getArrayListCoach().size()-1);
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
            }
            window.getArrayListCoach().get(getPos()).setName(window.getTxtNameCoach().getText());
            try {
                window.getArrayListCoach().get(getPos()).setYears(Integer.parseInt(window.getTxtYearCoach().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Experiencia en años tiene que ser un entero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            window.getArrayListCoach().get(getPos()).setBirthday(window.getDateCoach().getDate());

            Dojo selectedDojo = null;
            for (Dojo dojo : window.getArrayListDojo())
                if(window.getCbDojoInCoach().getSelectedItem().equals(dojo.toString()))
                    selectedDojo = dojo;

            window.getArrayListCoach().get(getPos()).setDojo(selectedDojo);
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
        window.getModelCoach().removeAllElements();

        for (Coach coach : window.getArrayListCoach())
            if (coach.getName().toLowerCase().contains(window.getSearchCoach().getText().toLowerCase()) ||
                    String.valueOf(coach.getYears()).contains(window.getSearchCoach().getText()))
                window.getModelCoach().addElement(coach);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        loadData();
    }
}
