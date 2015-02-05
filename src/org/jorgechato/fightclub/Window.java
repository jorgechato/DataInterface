package org.jorgechato.fightclub;

import org.hibernate.Query;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.util.HibernateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
public class Window {
    private JTabbedPane tabbedPane;
    private JPanel panel1;
    private JTable tableDojo;
    private JButton pushDojo;
    private JButton deleteDojo;
    private JButton changeDojo;
    private JPanel dojoPanel;
    private DefaultTableModel modelDojo;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Window");
        frame.setContentPane(new Window().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Window() {
        HibernateUtil.buildSessionFactory();
        init();
        reloadDojoTable();
    }

    private void init() {
        //dojo
        modelDojo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelDojo.addColumn("Nombre");
        modelDojo.addColumn("Dirección");
        modelDojo.addColumn("Inauguración");

        tableDojo.setModel(modelDojo);
    }

    private void reloadDojoTable() {
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Dojo ");
        List<Dojo> listDojo= (List<Dojo>) query.list();
        for (Dojo dojo : listDojo) {
            Object [] object = new Object[]{ dojo.getName(),dojo.getStreet(),dojo.getInauguration() };
            modelDojo.addRow(object);
        }
    }
}
