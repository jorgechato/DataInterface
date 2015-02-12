package org.jorgechato.fightclub;

import com.toedter.calendar.JDateChooser;
import org.hibernate.Session;
import org.jorgechato.fightclub.base.Coach;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.util.HibernateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * Created by jorge on 10/02/15.
 */
public class CoachIssue extends JDialog implements ActionListener {
    private JTextField textField1;
    private JTextField textField2;
    private JDateChooser dateInnauguration;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JPanel panel1;
    private JComboBox comboBox1;
    private Window window;
    private int idCoach;
    private Coach query;

    public CoachIssue(Window window,int idCoach) {
        dateInnauguration.setPreferredSize(new Dimension(250, 29));
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);

        this.window = window;
        this.idCoach = idCoach;

        init();
    }

    private void init() {
        cancelarButton.addActionListener(this);
        aceptarButton.addActionListener(this);

        for (Dojo dojo : window.getListDojo())
            comboBox1.addItem(dojo);

        if (idCoach != -1){
            query = (Coach) HibernateUtil.getCurrentSession().get(Coach.class,idCoach);
            textField1.setText(query.getName());
            textField2.setText(query.getSperience().toString());
            dateInnauguration.setDate(new Date(query.getBirthday().getTime()));
            comboBox1.setSelectedItem(query.getDojo());
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelarButton){
            setVisible(false);
            return;
        }
        if(actionEvent.getSource() == aceptarButton){
            if (idCoach == -1){
                Coach coach = new Coach();
                coach.setName(textField1.getText());
                coach.setSperience(Integer.parseInt(textField2.getText()));
                coach.setBirthday(new Date(dateInnauguration.getDate().getTime()));
                coach.setDojo((Dojo) comboBox1.getSelectedItem());

                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.save(coach);
                session.getTransaction().commit();
                session.close();
            }else {
                query.setName(textField1.getText());
                query.setSperience(Integer.parseInt(textField2.getText()));
                query.setBirthday(new Date(dateInnauguration.getDate().getTime()));
                query.setDojo((Dojo) comboBox1.getSelectedItem());

                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.update(query);
                session.getTransaction().commit();
                session.close();
            }
            window.reloadCoachTable(HibernateUtil.getCurrentSession().createQuery("FROM Coach "));
            setVisible(false);
            return;
        }
    }
}
