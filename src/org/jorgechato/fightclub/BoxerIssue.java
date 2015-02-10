package org.jorgechato.fightclub;

import com.toedter.calendar.JDateChooser;
import org.hibernate.Session;
import org.jorgechato.fightclub.base.Boxer;
import org.jorgechato.fightclub.base.Coach;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.util.HibernateUtil;

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
    private int idBoxer;
    private Boxer query;

    public BoxerIssue(Window window,int idBoxer) {
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);

        this.window = window;
        this.idBoxer = idBoxer;

        init();
    }

    private void init() {
        cancelarButton.addActionListener(this);
        aceptarButton.addActionListener(this);

        for (Dojo dojo : window.getListDojo())
            comboBox1.addItem(dojo);
        for (Coach coach : window.getListCoach())
            comboBox2.addItem(coach);

        if (idBoxer != -1){
            query = (Boxer) HibernateUtil.getCurrentSession().get(Boxer.class,idBoxer);
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
            if (idBoxer == -1){
                Boxer boxer = new Boxer();
                boxer.setName(textField1.getText());
                boxer.setWins(Integer.parseInt(textField2.getText()));
                boxer.setLose(Integer.parseInt(textField3.getText()));
                boxer.setWeight(Double.parseDouble(textField4.getText()));
                boxer.setDojo((Dojo) comboBox1.getSelectedItem());
                boxer.setCoach((Coach) comboBox2.getSelectedItem());

                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.save(boxer);
                session.getTransaction().commit();
                session.close();
            }else {
                query.setName(textField1.getText());
                query.setWins(Integer.parseInt(textField2.getText()));
                query.setLose(Integer.parseInt(textField3.getText()));
                query.setWeight(Double.parseDouble(textField4.getText()));
                query.setDojo((Dojo) comboBox1.getSelectedItem());
                query.setCoach((Coach) comboBox2.getSelectedItem());

                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.update(query);
                session.getTransaction().commit();
                session.close();
            }
            window.reloadBoxerTable();
            setVisible(false);
            return;
        }
    }
}
