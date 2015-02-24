package org.jorgechato.fightclub;

import com.db4o.Db4oEmbedded;
import com.db4o.query.Predicate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jorgechato.fightclub.base.Boxer;
import org.jorgechato.fightclub.base.Coach;
import org.jorgechato.fightclub.base.Dojo;
import org.jorgechato.fightclub.base.Fight;
import org.jorgechato.fightclub.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
    private List<Dojo> listDojo;
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
        Util.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "FightClub.db4o") ;

        init();
        search();
        if (Util.db.query(Dojo.class).size()>0)
            reloadDojoTable(Util.db.query(Dojo.class));
        if (Util.db.query(Coach.class).size()>0)
            reloadCoachTable(Util.db.query(Coach.class));
        if (Util.db.query(Boxer.class).size()>0)
            reloadBoxerTable(Util.db.query(Boxer.class));
        if (Util.db.query(Fight.class).size()>0)
            reloadFightTable(Util.db.query(Fight.class));
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
            if (Util.db.query(Dojo.class).size()>0)
                reloadDojoTable(Util.db.query(Dojo.class));
            if (Util.db.query(Coach.class).size()>0)
                reloadCoachTable(Util.db.query(Coach.class));
            if (Util.db.query(Boxer.class).size()>0)
                reloadBoxerTable(Util.db.query(Boxer.class));
            if (Util.db.query(Fight.class).size()>0)
                reloadFightTable(Util.db.query(Fight.class));
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
        List<Dojo> list = Util.db.query(new Predicate<Dojo>() {
            @Override
            public boolean match(Dojo dojo) {
                if (dojo.getName().contains(searchDojo.getText()))
                    return true;
                if (dojo.getStreet().contains(searchDojo.getText()))
                    return true;
                return false;
            }
        });
        reloadDojoTable(list);
    }
    public void searchCoachTable(){
        List<Coach> list = Util.db.query(new Predicate<Coach>() {
            @Override
            public boolean match(Coach coach) {
                if (coach.getName().contains(searchCoach.getText()))
                    return true;
                if (coach.getDojo().getName().contains(searchCoach.getText()))
                    return true;
                if (String.valueOf(coach.getSperience()).contains(searchCoach.getText()))
                    return true;
                return false;
            }
        });
        reloadCoachTable(list);
    }
    public void searchFightTable(){
        List<Fight> list = Util.db.query(new Predicate<Fight>() {
            @Override
            public boolean match(Fight fight) {
                if (fight.getName().contains(searchFight.getText()))
                    return true;
                if (fight.getStreet().contains(searchFight.getText()))
                    return true;
                return false;
            }
        });
        reloadFightTable(list);
    }
    public void searchBoxerTable(){
        List<Boxer> list = Util.db.query(new Predicate<Boxer>() {
            @Override
            public boolean match(Boxer boxer) {
                if (boxer.getName().contains(searchBoxer.getText()))
                    return true;
                if (boxer.getDojo().getName().contains(searchBoxer.getText()))
                    return true;
                if (boxer.getCoach().getName().contains(searchBoxer.getText()))
                    return true;
                if (String.valueOf(boxer.getWins()).contains(searchBoxer.getText()))
                    return true;
                return false;
            }
        });
        reloadBoxerTable(list);
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
        modelFight.addColumn("Nombre");
        modelFight.addColumn("Dirección");
        modelFight.addColumn("Fecha");

        tableDojo.setModel(modelDojo);
        tableCoach.setModel(modelCoach);
        tableBoxer.setModel(modelBoxer);
        tableFight.setModel(modelFight);
    }

    public void reloadDojoTable(List<Dojo> list) {
        modelDojo.setNumRows(0);
        for (Dojo dojo : list) {
            Object [] object = new Object[]{dojo.getName(),dojo.getStreet(),dojo.getInauguration() };
            modelDojo.addRow(object);
        }
        listDojo = list;

        logLabel.setText("Tabla de escuela actualizada \t Filas: "+modelDojo.getRowCount());
    }

    public void reloadCoachTable(List<Coach> list) {
        modelCoach.setNumRows(0);
        for (Coach coach : list) {
            Object [] object = new Object[]{ coach.getName(),coach.getBirthday(),coach.getSperience(),coach.getDojo() };
            modelCoach.addRow(object);
        }
        listCoach = list;

        logLabel.setText("Tabla de entrenador actualizada \t Filas: "+modelCoach.getRowCount());
    }

    public void reloadBoxerTable(List<Boxer> list) {
        modelBoxer.setNumRows(0);
        for (Boxer boxer : list) {
            Object [] object = new Object[]{boxer.getName(),boxer.getWins(),boxer.getLose(),boxer.getWeight(),
            boxer.getDojo(),boxer.getCoach()};
            modelBoxer.addRow(object);
        }
        listBoxer = list;

        logLabel.setText("Tabla de boxeador actualizada \t Filas: "+modelBoxer.getRowCount());
    }

    public void reloadFightTable(List<Fight> list) {
        modelFight.setNumRows(0);
        for (Fight fight : list) {
            Object [] object = new Object[]{ fight.getName(),fight.getStreet(),fight.getDay()};
            modelFight.addRow(object);
        }
        listFight = list;

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
                new FightIssue(this, tableFight.getValueAt(fightRow,0).toString()).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteFight){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                Util.db.delete(searchFightByName(tableFight.getValueAt(fightRow,0).toString()).get(0));
                Util.db.commit();

                reloadFightTable(Util.db.query(Fight.class));
                return;
            }
        }
        if (actionEvent.getSource() == pushFight){
            new FightIssue(this,null).setVisible(true);
            return;
        }
    }

    public List<Fight> searchFightByName(String fightRow){
        Fight fight = new Fight() ;
        fight.setName(fightRow);
        return Util.db.queryByExample(fight);
    }

    public List<Boxer> searchBoxerByName(String boxerRow){
        Boxer boxer = new Boxer() ;
        boxer.setName(boxerRow);
        return Util.db.queryByExample(boxer);
    }

    public List<Coach> searchCoachByName(String coachRow){
        Coach coach = new Coach() ;
        coach.setName(coachRow);
        return Util.db.queryByExample(coach);
    }

    public List<Dojo> searchDojoByName(String dojoRow){
        Dojo dojo = new Dojo() ;
        dojo.setName(dojoRow);
        return Util.db.queryByExample(dojo);
    }

    private void actionBoxer(ActionEvent actionEvent) {
        int boxerRow;

        if ((boxerRow = tableBoxer.getSelectedRow()) != -1){
            if (actionEvent.getSource() == changeBoxer){
                new BoxerIssue(this, tableBoxer.getValueAt(boxerRow,0).toString()).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteBoxer){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                Util.db.delete(searchBoxerByName(tableBoxer.getValueAt(boxerRow,0).toString()).get(0));
                Util.db.commit();

                reloadBoxerTable(Util.db.query(Boxer.class));
                return;
            }
        }
        if (actionEvent.getSource() == pushBoxer){
            new BoxerIssue(this,null).setVisible(true);
            return;
        }
    }

    private void actionCoach(ActionEvent actionEvent) {
        int coachRow;

        if ((coachRow = tableCoach.getSelectedRow()) != -1){
            if (actionEvent.getSource() == changeCoach){
                new CoachIssue(this, tableCoach.getValueAt(coachRow,0).toString()).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteCoach){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                Util.db.delete(searchCoachByName(tableCoach.getValueAt(coachRow,0).toString()).get(0));
                Util.db.commit();

                reloadCoachTable(Util.db.query(Coach.class));
                return;
            }
        }
        if (actionEvent.getSource() == pushCoach){
            new CoachIssue(this,null).setVisible(true);
            return;
        }
    }

    public void actionDojo(ActionEvent actionEvent) {
        int dojoRow;

        if ((dojoRow = tableDojo.getSelectedRow()) != -1){
            if (actionEvent.getSource() == changeDojo){
                new DojoIssue(this,tableDojo.getValueAt(dojoRow,0).toString()).setVisible(true);
                return;
            }
            if (actionEvent.getSource() == deleteDojo){
                if(JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminarlo?", "Eliminar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
                Util.db.delete(searchDojoByName(tableDojo.getValueAt(dojoRow,0).toString()).get(0));
                Util.db.commit();

                reloadDojoTable(Util.db.query(Dojo.class));
                return;
            }
        }
        if (actionEvent.getSource() == pushDojo){
            new DojoIssue(this,null).setVisible(true);
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

            for (Dojo dojo : newListDojo){
                Util.db.store(dojo) ;
                Util.db.commit() ;
            }

            reloadDojoTable(Util.db.query(Dojo.class));
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void exportToJson(){
        Gson gson = new Gson();
        String json = gson.toJson(listDojo);
        System.out.println(gson.toJson(listDojo));
        try {
            FileWriter fileWriter = new FileWriter(jsonPath+".json");
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
