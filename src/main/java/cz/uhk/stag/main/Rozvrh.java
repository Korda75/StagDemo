package cz.uhk.stag.main;

import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

        String urlString = "https://stag-demo.uhk.cz/ws/services/rest2/rozvrhy/getRozvrhByMistnost?semestr=%25&budova=J&mistnost=J1&outputFormat=JSON";

        try {
            URL url = new URL(urlString);
            InputStreamReader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
            //ze vstupního proudu z url se převádí na čtecí proud s kódováním UTF
            Gson gson = new Gson();
            RozvrhRespons response = gson.fromJson(reader, RozvrhRespons.class);
            //převádí se Json data do objektu RovrhRespons


            List<RozvrhAkce> filtrovane = new ArrayList<>();
            for (RozvrhAkce a : response.rozvrhovaAkce) {
                if ("Přednáška".equals(a.typAkce) || "Cvičení".equals(a.typAkce)) {
                    filtrovane.add(a);
                }
            }

            tableModel.setData(filtrovane);


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Chyba při načítání dat: " + ex.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new Rozvrh().setVisible(true);
        });

    }


}

class RozvrhTableModel extends AbstractTableModel {

    private List<RozvrhAkce> data = new ArrayList<>();
    private String[] columns = {"Předmět", "Název", "Den", "Start", "Konec", "Katedra", "TypAkce", "Učitel"};

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
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RozvrhAkce akce = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> akce.predmet;
            case 1 -> akce.nazev;
            case 2 -> akce.den;
            case 3 -> akce.hodinaSkutOd.value;
            case 4 -> akce.hodinaSkutDo.value;
            case 5 -> akce.katedra;
            case 6 -> akce.typAkce;
            case 7 -> akce.ucitel.titulPred + " " + akce.ucitel.jmeno + " " + akce.ucitel.prijmeni + " " + akce.ucitel.titulZa;
            default -> null;
        };
    }
}

class RozvrhAkce {
    @SerializedName("predmet") String predmet;
    @SerializedName("nazev") String nazev;
    @SerializedName("katedra") String katedra;
    @SerializedName("typAkce") String typAkce;
    @SerializedName("den") String den;
    @SerializedName("hodinaSkutOd") HodinaSkutOd hodinaSkutOd;
    @SerializedName("hodinaSkutDo") HodinaSkutDo hodinaSkutDo;

    @SerializedName("ucitel") Ucitel ucitel;

    static class Ucitel {
        @SerializedName("jmeno") String jmeno;
        @SerializedName("prijmeni") String prijmeni;
        @SerializedName("titulPred") String titulPred;
        @SerializedName("titulZa") String titulZa;
    }

    static class HodinaSkutOd {
        @SerializedName("value") String value;
    }

    static class HodinaSkutDo {
        @SerializedName("value") String value;
    }

}

class RozvrhRespons {
    List<RozvrhAkce> rozvrhovaAkce;
}

