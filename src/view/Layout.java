package view;

import core.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Layout extends JFrame {

    public void guiInitilaze(int width, int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Pencere kapatıldığında sadece bu pencerenin kapanmasını sağlar.
        this.setTitle("Turizm Acente Sistemi"); // Pencere başlığı
        this.setSize(width, height); // Pencere boyutu
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize())); // Pencerenin konumu
        this.setVisible(true); // Pencereyi görünür yapar.
    }

    public void createTable(DefaultTableModel model, JTable table, Object[] columns, ArrayList<Object[]> rows) {
        model.setColumnIdentifiers(columns); // Tablo başlıklarını ayarlar
        table.setModel(model); // Tabloya modeli set eder
        table.getTableHeader().setReorderingAllowed(false); // Sütunların sırasının değiştirilmesine izin vermez
        table.getColumnModel().getColumn(0).setMaxWidth(75); // İlk sütunun maksimum genişliğini ayarlar
        table.setEnabled(false); // Tabloyu etkisiz hale getirir


        DefaultTableModel clearmodel = (DefaultTableModel) table.getModel();
        clearmodel.setRowCount(0);

        if (rows == null) {
            rows = new ArrayList<>();

        }

        for (Object[] row : rows){
            model.addRow(row);
        }
    }

    public int getTableSelectedRow(JTable table, int index){
        return Integer.parseInt(table.getValueAt(table.getSelectedRow(),index).toString());
    }

    public void tableHotelRowSelected(JTable table, Runnable action) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selectedRow, selectedRow); // Fare tıklamasına göre satırı seçer
                action.run(); // Belirtilen aksiyonu çalıştırır
            }
        });
    }
}
