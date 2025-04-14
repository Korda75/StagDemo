package cz.uhk.stag.main;

import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.awt.*;
import com.google.gson.Gson;


/**
 * Hlavní okno
 */
public class Rozvrh extends JFrame{
    private JComboBox<String> budovaComboBox;
    private JComboBox<String> mistnostComboBox;
    private JButton btNacti;



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
        mistnostComboBox = new JComboBox<>(new String[]{"J1", "J2", "J3"});
        topPanel.add(mistnostComboBox);

        btNacti = new JButton("Nacist");
        topPanel.add(btNacti);

        add(topPanel, BorderLayout.NORTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new Rozvrh().setVisible(true);
        });

    }


}

