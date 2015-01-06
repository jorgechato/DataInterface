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

/**
 * Class with Boxer's own listeners
 * Created by jorge on 11/11/14.
 */
public class ActionBoxer extends FatherAction{

    private Window window;
    private String fileName;
    private boolean canLoadData;

    public ActionBoxer(Window window){
        this.window = window;
        connection = window.getConnection();
        fileName = "Boxer";
        canLoadData = true;
        setBtnew(window.getBtNewBoxer());
        setBtsave(window.getBtSaveBoxer());
        setBtchange(window.getBtChangeBoxer());
        setBtdelete(window.getBtDeleteBoxer());
        setBtcancel(window.getBtCancelBoxer());
        setModel(window.getModelBoxer());
        setList(window.getListBoxer());
        setBefore(window.getBtBeforeBoxer());
        setAfter(window.getBtAfterBoxer());
        setFirst(window.getBtFirstBoxer());
        setLast(window.getBtLastBoxer());
        setLoadLabel(window.getLoadLabel());
        setFileName(fileName);
        setFullPath(fileName);
        loadDataBase();
        setArray((ArrayList<Boxer>) window.getArrayListBoxer());
    }

    /**
     * method to control the events on Boxer tag.
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == window.getBtNewBoxer()) {
            canLoadData = false;
            newData();
            activateDeactivateButton(false);
            activateDeactivateEdition(true);
            return;
        }
        if (actionEvent.getSource() == window.getBtCancelBoxer()) {
            canLoadData = true;
            setNew(true);
            navigate();
            activateDeactivateEdition(false);
            activateDeactivateButton(true);
            return;
        }
        if (actionEvent.getSource() == window.getBtChangeBoxer()) {
            canLoadData = false;
            setNew(false);
            disableNavigation();

            activateDeactivateEdition(true);
            activateDeactivateButton(false);
            return;
        }
        if (actionEvent.getSource() == window.getBtDeleteBoxer()){
            setManualSave(window.isManual());
            deleteData();
            return;
        }
        if (actionEvent.getSource() == window.getBtFirstBoxer()) {
            setPos(0);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtLastBoxer()){
            setPos(window.getArrayListBoxer().size()-1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtAfterBoxer()){
            setPos(getPos()+1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtBeforeBoxer()){
            setPos(getPos()-1);
            navigate();
            loadData();
            return;
        }
        if (actionEvent.getSource() == window.getBtSaveBoxer()){
            canLoadData = true;
            setManualSave(window.isManual());
            saveData();
            return;
        }
    }

    /**
     *  method to control the click events on Boxer tag.
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == window.getListBoxer()){
            if (navigateToJlist()){
                if (window.getModelBoxer().isEmpty())
                    return;
                getPosDataSearch();
                navigate();
                loadData();
            }
            return;
        }
    }
    /**
     *  method to control the key events on Boxer tag.
     * @param keyEvent
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getSource() == window.getSearchBoxer()){
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
    protected void deleteDatabase(Object object) {
        Boxer boxer = (Boxer) object;
/*
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        String consult = " DELETE FROM boxer WHERE id = ? ";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(consult);
            statement.setInt(1, boxer.getId());
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
    }

    @Override
    public void loadInFile() {
        String consult = " SELECT * FROM boxer ";
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(consult);
            result = statement.executeQuery();

            window.setArrayListBoxer(new ArrayList<Boxer>());
            while (result.next()){
                Boxer boxer = new Boxer(result.getInt(1),result.getString(2),result.getInt(3), result.getInt(4),
                        result.getFloat(5),getCoachId(result.getInt(7)),getDojoId(result.getInt(6)));
                window.getArrayListBoxer().add(boxer);
            }
            setArray((ArrayList<Boxer>) window.getArrayListBoxer());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                    result.close();
                    window.getLoadLabel().setText("Datos de boxeadores cargados automaticamente");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void reloadModelData() {
        window.getModelBoxer().clear();

        for (Boxer boxer : window.getArrayListBoxer()){
            window.getModelBoxer().addElement(boxer);
        }
    }

    @Override
    public void newData() {
        disableNavigation();
        setNew(true);
        window.getTxtNameBoxer().setText("");
        window.getTxtWinBoxer().setText("");
        window.getTxtLoseBoxer().setText("");
        window.getTxtWeightBoxer().setText("");

        loadComboBoxCoach();
        loadComboBoxDojo();
    }

    public void loadComboBoxDojo(){
        window.getCbDojoInBoxer().removeAllItems();
        if (window.getArrayListDojo().isEmpty()) {
            window.getCbDojoInBoxer().addItem(null);
            return;
        }
        if (window.getArrayListBoxer().size() != 0 && null == window.getArrayListBoxer().get(getPos()).getDojo())
            window.getCbDojoInBoxer().addItem(null);
        for (Dojo dojo : window.getArrayListDojo())
            window.getCbDojoInBoxer().addItem(dojo.toString());
    }

    public void loadComboBoxCoach(){
        window.getCbCoachInBoxer().removeAllItems();
        if (window.getArrayListCoach().isEmpty()) {
            window.getCbCoachInBoxer().addItem(null);
            return;
        }
        if (window.getArrayListBoxer().size() != 0 && null == window.getArrayListBoxer().get(getPos()).getCoach())
            window.getCbCoachInBoxer().addItem(null);
        for (Coach coach : window.getArrayListCoach())
            window.getCbCoachInBoxer().addItem(coach.toString());
    }

    @Override
    public void activateDeactivateEdition(boolean activate) {
        window.getTxtNameBoxer().setEditable(activate);
        window.getTxtWinBoxer().setEditable(activate);
        window.getTxtLoseBoxer().setEditable(activate);
        window.getTxtWeightBoxer().setEditable(activate);
        window.getCbDojoInBoxer().setEnabled(activate);
        window.getCbCoachInBoxer().setEnabled(activate);

        window.getSearchBoxer().setEnabled(!activate);
        window.getListBoxer().setEnabled(!activate);
    }

    @Override
    public void loadData() {
        if (!canLoadData)
            return;

        if (window.getArrayListBoxer().size() == 0)
            return;

        window.getTxtNameBoxer().setText(window.getArrayListBoxer().get(getPos()).getName());
        window.getTxtWinBoxer().setText(String.valueOf(window.getArrayListBoxer().get(getPos()).getWin()));
        window.getTxtLoseBoxer().setText(String.valueOf(window.getArrayListBoxer().get(getPos()).getLose()));
        window.getTxtWeightBoxer().setText(String.valueOf(window.getArrayListBoxer().get(getPos()).getWeight()));
        loadComboBoxDojo();
        loadComboBoxCoach();
        if (null != window.getArrayListBoxer().get(getPos()).getDojo())
            window.getCbDojoInBoxer().setSelectedItem(window.getArrayListBoxer().get(getPos()).getDojo().toString());
        if (null != window.getArrayListBoxer().get(getPos()).getCoach())
            window.getCbCoachInBoxer().setSelectedItem(window.getArrayListBoxer().get(getPos()).getCoach().toString());
    }

    @Override
    public void saveData() {
        Boxer boxer = new Boxer();

        if (window.getTxtNameBoxer().getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "ERROR :: Nombre obligatorio", "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (window.getTxtWinBoxer().getText().equalsIgnoreCase("")){
            window.getTxtWinBoxer().setText("0");
        }
        if (window.getTxtLoseBoxer().getText().equalsIgnoreCase("")){
            window.getTxtLoseBoxer().setText("0");
        }
        if (window.getTxtWeightBoxer().getText().equalsIgnoreCase("")){
            window.getTxtWeightBoxer().setText("0.0");
        }

        if (isNew()) {
            boxer.setName(window.getTxtNameBoxer().getText());
            try {
                boxer.setWin(Integer.parseInt(window.getTxtWinBoxer().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Victorias tiene que ser un entero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                boxer.setLose(Integer.parseInt(window.getTxtLoseBoxer().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Derrotas tiene que ser un entero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                boxer.setWeight(Float.parseFloat(window.getTxtWeightBoxer().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Peso tiene que ser un numero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Dojo selectedDojo = null;
            for (Dojo dojo : window.getArrayListDojo())
                if(window.getCbDojoInBoxer().getSelectedItem().equals(dojo.toString()))
                    selectedDojo = dojo;

            boxer.setDojo(selectedDojo);

            Coach selectedCoach = null;
            for (Coach coach : window.getArrayListCoach())
                if(window.getCbCoachInBoxer().getSelectedItem().equals(coach.toString()))
                    selectedCoach = coach;

            boxer.setCoach(selectedCoach);

            window.getArrayListBoxer().add(boxer);

            setPos(window.getArrayListBoxer().size()-1);
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
            window.getArrayListBoxer().get(getPos()).setName(window.getTxtNameBoxer().getText());
            try {
                window.getArrayListBoxer().get(getPos()).setWin(Integer.parseInt(window.getTxtWinBoxer().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Victorias tiene que ser un entero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                window.getArrayListBoxer().get(getPos()).setLose(Integer.parseInt(window.getTxtLoseBoxer().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Derrotas tiene que ser un entero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                window.getArrayListBoxer().get(getPos()).setWeight(Float.parseFloat(window.getTxtWeightBoxer().getText()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERROR :: El campo Peso tiene que ser un numero",
                        "Formato no aceptado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Dojo selectedDojo = null;
            for (Dojo dojo : window.getArrayListDojo())
                if(window.getCbDojoInBoxer().getSelectedItem().equals(dojo.toString()))
                    selectedDojo = dojo;

            window.getArrayListBoxer().get(getPos()).setDojo(selectedDojo);

            Coach selectedCoach = null;
            for (Coach coach : window.getArrayListCoach())
                if(window.getCbCoachInBoxer().getSelectedItem().equals(coach.toString()))
                    selectedCoach = coach;

            window.getArrayListBoxer().get(getPos()).setCoach(selectedCoach);
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
        window.getModelBoxer().removeAllElements();

        for (Boxer boxer : window.getArrayListBoxer())
            if (boxer.getName().toLowerCase().contains(window.getSearchBoxer().getText().toLowerCase()) ||
                    String.valueOf(boxer.getWin()).contains(window.getSearchBoxer().getText()))
                window.getModelBoxer().addElement(boxer);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        loadData();
    }
}
