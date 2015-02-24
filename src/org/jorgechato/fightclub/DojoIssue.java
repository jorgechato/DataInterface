package org.jorgechato.fightclub;

import com.toedter.calendar.JDateChooser;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * Created by jorge on 9/02/15.
 */
public class DojoIssue extends JDialog implements ActionListener{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton cancelarButton;
    private JDateChooser dateInnauguration;
    private JButton aceptarButton;
    private Window window;
    private String nameDojo;
    private Dojo query;

    public DojoIssue(Window window,String nameDojo) {
        dateInnauguration.setPreferredSize(new Dimension(250, 29));
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);

        this.window = window;
        this.nameDojo = nameDojo;

        init();
    }

    private void init() {
        cancelarButton.addActionListener(this);
        aceptarButton.addActionListener(this);

        if (nameDojo != null){
            query = window.searchDojoByName(nameDojo).get(0);
            textField1.setText(query.getName());
            textField2.setText(query.getStreet());
            dateInnauguration.setDate(new Date(query.getInauguration().getTime()));
        }
    }

    public JPanel getPanel1() {
        return panel1;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelarButton){
            setVisible(false);
            return;
        }
        if(actionEvent.getSource() == aceptarButton){
            if (nameDojo == null){
                Dojo dojo = new Dojo();
                dojo.setName(textField1.getText());
                dojo.setStreet(textField2.getText());
                dojo.setInauguration(new Date(dateInnauguration.getDate().getTime()));

                Util.db.store(dojo);
                Util.db.commit();
            }else {
                query.setName(textField1.getText());
                query.setStreet(textField2.getText());
                query.setInauguration(new Date(dateInnauguration.getDate().getTime()));

                Util.db.store(query);
                Util.db.commit();
            }
            window.reloadDojoTable(Util.db.query(Dojo.class));
            setVisible(false);
            return;
        }
    }
}
