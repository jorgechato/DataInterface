package org.jorgechato.fightclub;

import com.toedter.calendar.JDateChooser;
import org.jorgechato.fightclub.base.Boxer;
import org.jorgechato.fightclub.base.Coach;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 10/02/15.
 */
public class BoxerIssue extends JDialog implements ActionListener {
    private JPanel panel1;
    private JTextField textField1;
    private JDateChooser dateInnauguration;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBox2;
    private JTextField textField3;
    private JTextField textField4;
    private Window window;
    private String nameBoxer;
    private Boxer query;

    public BoxerIssue(Window window,String nameBoxer) {
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);

        this.window = window;
        this.nameBoxer = nameBoxer;

        init();
    }

    private void init() {
        cancelarButton.addActionListener(this);
        aceptarButton.addActionListener(this);

        for (Dojo dojo : window.getListDojo())
            comboBox1.addItem(dojo);
        for (Coach coach : window.getListCoach())
            comboBox2.addItem(coach);

        if (nameBoxer != null){
            query = window.searchBoxerByName(nameBoxer).get(0);
            textField1.setText(query.getName());
            textField2.setText(String.valueOf(query.getWins()));
            textField3.setText(String.valueOf(query.getLose()));
            textField4.setText(String.valueOf(query.getWeight()));
            comboBox1.setSelectedItem(query.getDojo());
            comboBox2.setSelectedItem(query.getCoach());
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelarButton){
            setVisible(false);
            return;
        }
        if(actionEvent.getSource() == aceptarButton){
            if (nameBoxer == null){
                Boxer boxer = new Boxer();
                boxer.setName(textField1.getText());
                boxer.setWins(Integer.parseInt(textField2.getText()));
                boxer.setLose(Integer.parseInt(textField3.getText()));
                boxer.setWeight(Double.parseDouble(textField4.getText()));
                boxer.setDojo((Dojo) comboBox1.getSelectedItem());
                boxer.setCoach((Coach) comboBox2.getSelectedItem());

                Util.db.store(boxer);
                Util.db.commit();
            }else {
                query.setName(textField1.getText());
                query.setWins(Integer.parseInt(textField2.getText()));
                query.setLose(Integer.parseInt(textField3.getText()));
                query.setWeight(Double.parseDouble(textField4.getText()));
                query.setDojo((Dojo) comboBox1.getSelectedItem());
                query.setCoach((Coach) comboBox2.getSelectedItem());

                Util.db.store(query);
                Util.db.commit();
            }
            window.reloadBoxerTable(Util.db.query(Boxer.class));
            setVisible(false);
            return;
        }
    }
}
