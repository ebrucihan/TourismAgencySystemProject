package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class AdminView extends Layout {

    private User user;
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JScrollPane scrl_admin;
    private JTable tbl_admin;
    private JComboBox<User.Role> cmb_type;
    private JButton btn_admin_reset;
    private JButton btn_admin_search;
    private JButton btn_admin_logout;
    private DefaultTableModel tmdl_user = new DefaultTableModel();
    private JPopupMenu user_Menu;
    private Object[] col_user;
    private UserManager userManager;

    // Admin ekranı oluşturulur.
    public AdminView(User user) {
        this.userManager = new UserManager(); // Kullanıcı yönetim sınıfı oluşturulur.
        this.add(container); // Ana konteyner eklenir.
        this.guiInitilaze(1000, 500); // GUI başlatılır.
        this.user = user; // Giriş yapan kullanıcı atanır.
        if (this.user == null) {
            dispose(); // Eğer kullanıcı yoksa pencere kapatılır.
        }

        // Genel Kodlar
        loadCompenent(); // Component yüklenir.

        // Kullanıcı Sekmesi
        loadUserCompenent(); // Kullanıcı componentleri yüklenir.
        loadUserTable(null); // Kullanıcı tablosu yüklenir.
        loadUserFilter(); // Kullanıcı filtreleme yüklenir.

        this.tbl_admin.setComponentPopupMenu(user_Menu); // Tabloya sağ tıklama menüsü eklenir.
    }

    // Genel componentleri yükler.
    private void loadCompenent(){
        // Çıkış butonuna tıklandığında, mevcut pencereyi kapatıp yeni bir giriş ekranı penceresi oluşturulur.
        btn_admin_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Mevcut pencereyi kapatır.
                LoginView loginView = new LoginView(); // Yeni bir giriş ekranı penceresi oluşturur.
            }
        });
    }

    // Kullanıcı tablosunu yükler.
    public void loadUserTable(ArrayList<Object[]> userList) {
        this.col_user = new Object[]{"ID", " İsim ", "Şifre", "Rol"};  // Kullanıcı tablosu sütun başlıkları tanımlanır.
        if (userList == null) {
            userList = this.userManager.getForTable(this.col_user.length, this.userManager.findAll());
        }
        createTable(this.tmdl_user, this.tbl_admin, col_user, userList);
    }

    // Kullanıcı filtreleme seçeneklerini yükler.
    public void loadUserFilter() {
        this.cmb_type.removeAllItems(); // ComboBox'ı temizler.

        for (User.Role role : User.Role.values()) { // Tüm kullanıcı rollerini ComboBox'a ekler.
            this.cmb_type.addItem(role);
        }
        this.cmb_type.setSelectedItem(null); // Varsayılan olarak seçili olanı kaldırır.
    }

    // Kullanıcı componentleri yükler.
    public void loadUserCompenent() {
        // Kullanıcı tablosuna fare ile tıklanma olayını dinler.
        this.tbl_admin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_admin.rowAtPoint(e.getPoint());
                tbl_admin.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    user_Menu.show(tbl_admin, e.getX(), e.getY()); // Sağ tıklama menüsü gösterilir.
                }
            }
        });

        // Sağ tıklama menüsü için işlevler eklenir.
        this.user_Menu = new JPopupMenu();
        this.user_Menu.add("Yeni Kullanıcı Ekle").addActionListener(e -> {
            UserView userView = new UserView(new User());
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null); // Yeni kullanıcı ekranı kapatıldığında tablo yeniden yüklenir.
                }
            });
        });
        this.user_Menu.add("Kullanıcı Güncelle").addActionListener(e -> {
            int selectUserId = this.getTableSelectedRow(tbl_admin, 0);
            UserView userView = new UserView(this.userManager.getById(selectUserId));
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null); // Kullanıcı güncelleme ekranı kapatıldığında tablo yeniden yüklenir.
                }
            });
        });
        this.user_Menu.add("Kullanıcı Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) { // Silme işlemi onaylanırsa devam edilir.
                int selectUserId = this.getTableSelectedRow(tbl_admin, 0);
                if (this.userManager.delete(selectUserId)) {
                    Helper.showMsg("done"); // İşlem başarılı mesajı gösterilir.
                    loadUserTable(null);  // Tablo yeniden yüklenir.
                } else {
                    Helper.showMsg("error"); // İşlem hatası mesajı gösterilir.
                }
            }
        });

        this.tbl_admin.setComponentPopupMenu(user_Menu);  // Tabloya sağ tıklama menüsü eklenir.

        // Arama butonuna tıklandığında yapılacak işlemler belirlenir.
        this.btn_admin_search.addActionListener(e -> {
            User.Role selectedRole = (User.Role) cmb_type.getSelectedItem(); // Seçilen rol alınır.
            int userId = 0; // Kullanıcı ID'si sıfırlanır.
            if (selectedRole != null) {
            }
            // Arama sonuçlarını tabloya yükler.
            ArrayList<User> userListBySearch = this.userManager.searchForTable(
                    userId,
                    selectedRole
            );
            ArrayList<Object[]> userRowListBySearch = this.userManager.getForTable(this.col_user.length, userListBySearch);
            loadUserTable(userRowListBySearch);
        });

        // Sıfırlama butonuna tıklandığında yapılacak işlemler belirlenir.
        this.btn_admin_reset.addActionListener(e -> {
            this.cmb_type.setSelectedItem(null); // ComboBox seçimi sıfırlanır.
            loadUserTable(null); // Kullanıcı tablosu yeniden yüklenir.
        });
    }

}











