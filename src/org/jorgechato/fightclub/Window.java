package org.jorgechato.fightclub;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jorgechato.fightclub.base.Boxer;
import org.jorgechato.fightclub.base.Coach;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.base.Fight;
import org.jorgechato.fightclub.util.HibernateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
public class Window implements ActionListener{
    private JTabbedPane searchBoxer;
    private JPanel panel1;
    private JTable tableDojo;
    private JButton pushDojo;
    private JButton deleteDojo;
    private JButton changeDojo;
    private JPanel dojoPanel;
    private JPanel coachPanel;
    private JPanel boxerPanel;
    private JPanel fightPanel;
    private JTable tableCoach;
    private JTable tableBoxer;
    private JTable tableFight;
    private JButton pushCoach;
    private JButton deleteCoach;
    private JButton changeCoach;
    private JButton pushBoxer;
    private JButton deleteBoxer;
    private JButton changeBoxer;
    private JButton pushFight;
    private JButton deleteFight;
    private JButton changeFight;
    private JTextField searchDojo;
    private JTextField searchCoach;
    private JTextField searchFight;
    private DefaultTableModel modelDojo;
    private DefaultTableModel modelCoach;
    private DefaultTableModel modelBoxer;
    private DefaultTableModel modelFight;
    private List<Coach> listCoach;
    private List<Dojo> listDojo;
    private List<Boxer> listBoxer;
    private List<Fight> listFight;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Window");
        frame.setContentPane(new Window().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public Window() {
        HibernateUtil.buildSessionFactory();
        init();
        reloadDojoTable();
        reloadCoachTable();
        reloadBoxerTable();
        reloadFightTable();
    }

    public List<Coach> getListCoach() {
        return listCoach;
    }

    public void setListCoach(List<Coach> listCoach) {
        this.listCoach = listCoach;
    }

    public List<Dojo> getListDojo() {
        return listDojo;
    }

    public void setListDojo(List<Dojo> listDojo) {
        this.listDojo = listDojo;
    }

    public List<Boxer> getListBoxer() {
        return listBoxer;
    }

    public void setListBoxer(List<Boxer> listBoxer) {
        this.listBoxer = listBoxer;
    }

    public List<Fight> getListFight() {
        return listFight;
    }

    public void setListFight(List<Fight> listFight) {
        this.listFight = listFight;
    }

    private void init() {
        pushDojo.addActionListener(this);
        changeDojo.addActionListener(this);
        deleteDojo.addActionListener(this);
        pushCoach.addActionListener(this);
        changeCoach.addActionListener(this);
        deleteCoach.addActionListener(this);
        pushBoxer.addActionListener(this);
        changeBoxer.addActionListener(this);
        deleteBoxer.addActionListener(this);
        pushFight.addActionListener(this);
        changeFight.addActionListener(this);
        deleteFight.addActionListener(this);

        //dojo
        modelDojo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelDojo.addColumn("#");
        modelDojo.addColumn("Nombre");
        modelDojo.addColumn("Dirección");
        modelDojo.addColumn("Inauguración");
        //coach
        modelCoach = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelCoach.addColumn("#");
        modelCoach.addColumn("Nombre");
        modelCoach.addColumn("Cumpleaños");
        modelCoach.addColumn("Experiencia");
        modelCoach.addColumn("Escuela");
        //boxer
        modelBoxer = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelBoxer.addColumn("#");
        modelBoxer.addColumn("Nombre");
        modelBoxer.addColumn("Ganados");
        modelBoxer.addColumn("Perdidos");
        modelBoxer.addColumn("Peso");
        modelBoxer.addColumn("Escuela");
        modelBoxer.addColumn("Entrenador");
        //fight
        modelFight = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelFight.addColumn("#");
        modelFight.addColumn("Nombre");
        modelFight.addColumn("Dirección");
        modelFight.addColumn("Fecha");

        tableDojo.setModel(modelDojo);
        tableCoach.setModel(modelCoach);
        tableBoxer.setModel(modelBoxer);
        tableFight.setModel(modelFight);
    }

    public void reloadDojoTable() {
        modelDojo.setNumRows(0);
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Dojo ");
        listDojo = (List<Dojo>) query.list();
        for (Dojo dojo : listDojo) {
            Object [] object = new Object[]{dojo.getId() ,dojo.getName(),dojo.getStreet(),dojo.getInauguration() };
            modelDojo.addRow(object);
        }
    }

    public void reloadCoachTable() {
        modelCoach.setNumRows(0);
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Coach ");
        listCoach= (List<Coach>) query.list();
        for (Coach coach : listCoach) {
            Object [] object = new Object[]{ coach.getId(),coach.getName(),coach.getBirthday(),coach.getSperience(),coach.getDojo() };
            modelCoach.addRow(object);
        }
    }

    public void reloadBoxerTable() {
        modelBoxer.setNumRows(0);
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Boxer ");
        listBoxer= (List<Boxer>) query.list();
        for (Boxer boxer : listBoxer) {
            Object [] object = new Object[]{ boxer.getId(), boxer.getName(),boxer.getWins(),boxer.getLose(),boxer.getWeight(),
            boxer.getDojo(),boxer.getCoach()};
            modelBoxer.addRow(object);
        }
    }

    public void reloadFightTable() {
        modelFight.setNumRows(0);
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Fight ");
        listFight= (List<Fight>) query.list();
        for (Fight fight : listFight) {
            Object [] object = new Object[]{ fight.getId(), fight.getName(),fight.getStreet(),fight.getDay()};
            modelFight.addRow(object);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        actionDojo(actionEvent);
        actionCoach(actionEvent);
        actionBoxer(actionEvent);
        actionFight(actionEvent);
    }

    public void actionFight(ActionEvent actionEvent) {
        int fightRow;

        if ((fightRow = tableFight.getSelectedRow()) != -1){
            if (actionEvent.getSource() == changeFight){
                new FightIssue(this, (Integer) tableFight.getValueAt(fightRow,0)).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteFight){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                int id = (Integer) tableDojo.getValueAt(fightRow, 0);
                Fight fight =(Fight) HibernateUtil.getCurrentSession().get(Fight.class, id);
                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.delete(fight);
                session.getTransaction().commit();
                session.close();

                reloadFightTable();
                return;
            }
        }
        if (actionEvent.getSource() == pushFight){
            new FightIssue(this,-1).setVisible(true);
            return;
        }
    }

    private void actionBoxer(ActionEvent actionEvent) {
        int boxerRow;

        if ((boxerRow = tableBoxer.getSelectedRow()) != -1){
            if (actionEvent.getSource() == changeBoxer){
                new BoxerIssue(this, (Integer) tableBoxer.getValueAt(boxerRow,0)).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteBoxer){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                int id = (Integer) tableBoxer.getValueAt(boxerRow, 0);
                Boxer boxer =(Boxer) HibernateUtil.getCurrentSession().get(Boxer.class, id);
                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.delete(boxer);
                session.getTransaction().commit();
                session.close();

                reloadBoxerTable();
                return;
            }
        }
        if (actionEvent.getSource() == pushBoxer){
            new BoxerIssue(this,-1).setVisible(true);
            return;
        }
    }

    private void actionCoach(ActionEvent actionEvent) {
        int coachRow;

        if ((coachRow = tableCoach.getSelectedRow()) != -1){
            if (actionEvent.getSource() == changeCoach){
                new CoachIssue(this, (Integer) tableCoach.getValueAt(coachRow,0)).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteCoach){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                int id = (Integer) tableCoach.getValueAt(coachRow, 0);
                Coach coach =(Coach) HibernateUtil.getCurrentSession().get(Coach.class, id);
                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.delete(coach);
                session.getTransaction().commit();
                session.close();

                reloadCoachTable();
                return;
            }
        }
        if (actionEvent.getSource() == pushCoach){
            new CoachIssue(this,-1).setVisible(true);
            return;
        }
    }

    public void actionDojo(ActionEvent actionEvent) {
        int dojoRow;

        if ((dojoRow = tableDojo.getSelectedRow()) != -1){
            if (actionEvent.getSource() == changeDojo){
                new DojoIssue(this, (Integer) tableDojo.getValueAt(dojoRow,0)).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteDojo){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                int id = (Integer) tableDojo.getValueAt(dojoRow, 0);
                Dojo dojo =(Dojo) HibernateUtil.getCurrentSession().get(Dojo.class, id);
                Session session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.delete(dojo);
                session.getTransaction().commit();
                session.close();

                reloadDojoTable();
                return;
            }
        }
        if (actionEvent.getSource() == pushDojo){
            new DojoIssue(this,-1).setVisible(true);
            return;
        }
    }
}
