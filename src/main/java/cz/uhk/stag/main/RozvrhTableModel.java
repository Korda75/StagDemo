package cz.uhk.stag.main;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

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
            case 7 -> {
                RozvrhAkce.Ucitel ucitel = akce.ucitel;
                if (ucitel != null) {
                    yield (ucitel.titulPred != null ? ucitel.titulPred + " " : "") +
                            (ucitel.jmeno != null ? ucitel.jmeno + " " : "") +
                            (ucitel.prijmeni != null ? ucitel.prijmeni + " " : "") +
                            (ucitel.titulZa != null ? ucitel.titulZa : "");
                } else {
                    yield "neuveden";
                }
            }
            default -> null;
        };
    }
}
