package org.jorgechato.fightclub.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 12/02/15.
 */
public class Root extends JDialog{
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton conectarButton;
    public static String NAME,PASS;

    public Root() {
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);
        textField1.setText("root");
        passwordField1.setText("2015**Luz");
        conectarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NAME = textField1.getText();
                char pass[] = passwordField1.getPassword();
                PASS = String.valueOf(pass);
                setVisible(false);
            }
        });
    }
}
