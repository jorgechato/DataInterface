package org.jorgechato.fightclub;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jorgechato.fightclub.base.Boxer;
import org.jorgechato.fightclub.base.Coach;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.base.Fight;
import org.jorgechato.fightclub.util.HibernateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
public class Window implements ActionListener{
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
    private JTextField searchBoxer;
    private JLabel logLabel;
    private JScrollPane dojoTab;
    private JTabbedPane tabPanel;
    private DefaultTableModel modelDojo;
    private DefaultTableModel modelCoach;
    private DefaultTableModel modelBoxer;
    private DefaultTableModel modelFight;
    private List<Coach> listCoach;
    private ArrayList<Dojo> listDojo;
    private List<Boxer> listBoxer;
    private List<Fight> listFight;
    private static MenuBar menuBar;
    private String jsonPath;

    private java.lang.reflect.Type type;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Window");
        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar.menuBar());
        Window window =new Window();
        menuBar.setWindow(window);
        frame.setContentPane(window.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public Window() {
        HibernateUtil.buildSessionFactory();
        init();
        search();
        reloadDojoTable(HibernateUtil.getCurrentSession().createQuery("FROM Dojo "));
        reloadCoachTable(HibernateUtil.getCurrentSession().createQuery("FROM Coach "));
        reloadBoxerTable(HibernateUtil.getCurrentSession().createQuery("FROM Boxer "));
        reloadFightTable(HibernateUtil.getCurrentSession().createQuery("FROM Fight "));
        timer();

        tabPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                switch (tabPanel.getSelectedIndex()){
                    case 0:
                        logLabel.setText("Tabla de escuela");
                        break;
                    case 1:
                        logLabel.setText("Tabla de entrenador");
                        break;
                    case 2:
                        logLabel.setText("Tabla de boxeador");
                        break;
                    case 3:
                        logLabel.setText("Tabla de combate");
                        break;
                    default:
                        break;
                }
            }
        });

        tableDojo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                logLabel.setText("Fila seleccionada: "+tableDojo.getSelectedRow());
            }
        });

        tableCoach.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                logLabel.setText("Fila seleccionada: "+tableCoach.getSelectedRow());
            }
        });

        tableBoxer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                logLabel.setText("Fila seleccionada: "+tableBoxer.getSelectedRow());
            }
        });

        tableFight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                logLabel.setText("Fila seleccionada: " + tableFight.getSelectedRow());
            }
        });
    }

    private void timer() {
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reloadDojoTable(HibernateUtil.getCurrentSession().createQuery("FROM Dojo "));
                reloadCoachTable(HibernateUtil.getCurrentSession().createQuery("FROM Coach "));
                reloadBoxerTable(HibernateUtil.getCurrentSession().createQuery("FROM Boxer "));
                reloadFightTable(HibernateUtil.getCurrentSession().createQuery("FROM Fight "));
            }
        });timer.start();
    }

    public void search() {
        searchDojo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                searchDojoTable();
            }
        });
        searchCoach.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                searchCoachTable();
            }
        });
        searchFight.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                searchFightTable();
            }
        });
        searchBoxer.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                searchBoxerTable();
            }
        });
    }

    public void searchDojoTable() {
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Dojo WHERE name like '%" +
                searchDojo.getText() +"%' or street like '%"+searchDojo.getText()+"%'");
        reloadDojoTable(query);
    }
    public void searchCoachTable(){
        int num;
        try{
            num = Integer.parseInt(searchCoach.getText());
        }catch (NumberFormatException e){
            num = 0;
        }
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Coach WHERE name like '%" +
                searchCoach.getText() +"%' or sperience = "+num+" or dojo.name like '%"+searchCoach.getText()+"%'");
        reloadCoachTable(query);

    }
    public void searchFightTable(){
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Fight WHERE name like '%" +
                searchFight.getText() +"%' or street like '%"+searchFight.getText()+"%'");
        reloadFightTable(query);

    }
    public void searchBoxerTable(){
        int win;
        try{
            win = Integer.parseInt(searchBoxer.getText());
        }catch (NumberFormatException e){
            win = 0;
        }

        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Boxer WHERE name like '%" +
                searchBoxer.getText() +"%' or wins = "+win+ " or dojo.name like '%"+searchBoxer.getText()+"%' or coach.name like '%"+searchBoxer.getText()+"%'");
        reloadBoxerTable(query);

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

    public void setListDojo(ArrayList<Dojo> listDojo) {
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

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
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

    public void reloadDojoTable(Query query) {
        modelDojo.setNumRows(0);
        listDojo = (ArrayList<Dojo>) query.list();
        for (Dojo dojo : listDojo) {
            Object [] object = new Object[]{dojo.getId() ,dojo.getName(),dojo.getStreet(),dojo.getInauguration() };
            modelDojo.addRow(object);
        }
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
        logLabel.setText("Tabla de escuela actualizada \t Filas: "+modelDojo.getRowCount());
    }

    public void reloadCoachTable(Query query) {
        modelCoach.setNumRows(0);
        listCoach= (List<Coach>) query.list();
        for (Coach coach : listCoach) {
            Object [] object = new Object[]{ coach.getId(),coach.getName(),coach.getBirthday(),coach.getSperience(),coach.getDojo() };
            modelCoach.addRow(object);
        }
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
        logLabel.setText("Tabla de entrenador actualizada \t Filas: "+modelCoach.getRowCount());
    }

    public void reloadBoxerTable(Query query) {
        modelBoxer.setNumRows(0);
        listBoxer= (List<Boxer>) query.list();
        for (Boxer boxer : listBoxer) {
            Object [] object = new Object[]{ boxer.getId(), boxer.getName(),boxer.getWins(),boxer.getLose(),boxer.getWeight(),
            boxer.getDojo(),boxer.getCoach()};
            modelBoxer.addRow(object);
        }
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
        logLabel.setText("Tabla de boxeador actualizada \t Filas: "+modelBoxer.getRowCount());
    }

    public void reloadFightTable(Query query) {
        modelFight.setNumRows(0);
        listFight= (List<Fight>) query.list();
        for (Fight fight : listFight) {
            Object [] object = new Object[]{ fight.getId(), fight.getName(),fight.getStreet(),fight.getDay()};
            modelFight.addRow(object);
        }
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
        logLabel.setText("Tabla de combate actualizada \t Filas: "+modelFight.getRowCount());
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

                reloadFightTable(HibernateUtil.getCurrentSession().createQuery("FROM Fight "));
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

                reloadBoxerTable(HibernateUtil.getCurrentSession().createQuery("FROM Boxer "));
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

                reloadCoachTable(HibernateUtil.getCurrentSession().createQuery("FROM Coach "));
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

                reloadDojoTable(HibernateUtil.getCurrentSession().createQuery("FROM Dojo "));
                return;
            }
        }
        if (actionEvent.getSource() == pushDojo){
            new DojoIssue(this,-1).setVisible(true);
            return;
        }
    }

    public void importFromJson(){
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader(jsonPath);

            type = new TypeToken<ArrayList<Dojo>>() {
            }.getType();
            List<Dojo> newListDojo= gson.fromJson(fileReader,type);

            fileReader.close();

            Session session = HibernateUtil.getCurrentSession();
            for (Dojo dojo : newListDojo){
                session.beginTransaction();
                session.save(dojo);
                session.getTransaction().commit();
            }
            session.close();

            reloadDojoTable(HibernateUtil.getCurrentSession().createQuery("FROM Dojo "));

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void exportToJson(){
        Gson gson = new Gson();
//        String json = gson.toJson(listDojo);
        System.out.println(gson.toJson(listDojo));
//        try {
//            FileWriter fileWriter = new FileWriter(jsonPath+".json");
//            fileWriter.write(json);
//            fileWriter.close();
//        } catch (IOException e) {
//            System.out.println(e.fillInStackTrace());
//        }
    }
}
