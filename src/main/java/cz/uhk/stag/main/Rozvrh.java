package cz.uhk.stag.main;

import javax.swing.*;
import java.awt.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;


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
        budovaComboBox = new JComboBox<>(new String[]{"J", "A"});
        topPanel.add(budovaComboBox);

        topPanel.add(new JLabel("Mistnost"));
        mistnostComboBox = new JComboBox<>(new String[]{"J1", "J2", "J3", "A1", "A2"});
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

        String urlString = "https://stag-demo.uhk.cz/ws/services/rest2/rozvrhy/getRozvrhByMistnost?budova=%s&mistnost=%s&outputFormat=JSON";

        try {
            urlString = String.format(urlString, budova, mistnost);
            URL url = new URL(urlString);
            InputStreamReader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
            //ze vstupního proudu z url se převádí na čtecí proud s kódováním UTF
            Gson gson = new Gson();
            RozvrhRespons response = gson.fromJson(reader, RozvrhRespons.class);
            //převádí se Json data do objektu RovrhRespons


            tableModel.setData(response.rozvrhovaAkce);


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


