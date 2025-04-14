package cz.uhk.stag.main;

import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


/**
 * Hlavní okno
 */
public class Rozvrh extends JFrame{
    private JComboBox<String> budovaComboBox;
    private JComboBox<String> mistnostComboBox;
    private JButton btNacti;
    private JTable table;
    private RozvrhTableModel tableModel;



    public Rozvrh() {
        setTitle("Stag");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout()); //FlowLayout = rozmisťuje komponenty vedle sebe z leva do prava

        topPanel.add(new JLabel("Budova"));
        budovaComboBox = new JComboBox<>(new String[]{"J"});
        topPanel.add(budovaComboBox);

        topPanel.add(new JLabel("Mistnost"));
        mistnostComboBox = new JComboBox<>(new String[]{"J1"});
        topPanel.add(mistnostComboBox);

        btNacti = new JButton("Nacist");
        topPanel.add(btNacti);
        btNacti.addActionListener(e -> nactiData());

        add(topPanel, BorderLayout.NORTH);

        tableModel = new RozvrhTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);



    }

    private void nactiData() {
        String budova = (String) budovaComboBox.getSelectedItem();
        String mistnost = (String) mistnostComboBox.getSelectedItem();
        //vrací aktualni vybranou položku
        //když uživatel změní možnost, tato část to zjistí



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new Rozvrh().setVisible(true);
        });

    }


}

class RozvrhTableModel extends AbstractTableModel {

    private List<RozvrhAkce> data = new ArrayList<>();
    private String[] columns = {"Předmět", "Název"};

    public void setData(List<RozvrhAkce> data) {
        this.data = data;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RozvrhAkce akce = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> akce.predmet;
            case 1 -> akce.nazev;
            default -> null;
        };
    }
}

class RozvrhAkce {
    @SerializedName("Předmět") String predmet;
    @SerializedName("Název") String nazev;
}

