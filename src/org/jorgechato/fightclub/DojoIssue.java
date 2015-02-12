package org.jorgechato.fightclub;

import com.toedter.calendar.JDateChooser;
import org.hibernate.Session;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.util.HibernateUtil;

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
    private int idDojo;
    private Dojo query;

    public DojoIssue(Window window,int idDojo) {
        dateInnauguration.setPreferredSize(new Dimension(250, 29));
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);

        this.window = window;
        this.idDojo = idDojo;

        init();
    }

    private void init() {
        cancelarButton.addActionListener(this);
        aceptarButton.addActionListener(this);

        if (idDojo != -1){
            query = (Dojo) HibernateUtil.getCurrentSession().get(Dojo.class,idDojo);
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
            if (idDojo == -1){
                Dojo dojo = new Dojo();
                dojo.setName(textField1.getText());
                dojo.setStreet(textField2.getText());
                dojo.setInauguration(new Date(dateInnauguration.getDate().getTime()));
                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.save(dojo);
                session.getTransaction().commit();
                session.close();
            }else {
                query.setName(textField1.getText());
                query.setStreet(textField2.getText());
                query.setInauguration(new Date(dateInnauguration.getDate().getTime()));

                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.update(query);
                session.getTransaction().commit();
                session.close();
            }
            window.reloadDojoTable(HibernateUtil.getCurrentSession().createQuery("FROM Dojo "));
            setVisible(false);
            return;
        }
    }
}
