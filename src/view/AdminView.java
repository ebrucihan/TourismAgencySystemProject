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

    public AdminView(User user) {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }

        //General Code
        loadCompenent();


        //User Tab Menu
        loadUserCompenent();
        loadUserTable(null);
        loadUserFilter();

        this.tbl_admin.setComponentPopupMenu(user_Menu);
    }

    private void loadCompenent(){
        // Çıkış butonuna tıklandığında, mevcut pencereyi kapatıp yeni bir giriş ekranı penceresi oluşturulur.
        btn_admin_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView(); // Yeni bir giriş ekranı penceresi oluşturur.

            }
        });
    }

    //User Tablo
    public void loadUserTable(ArrayList<Object[]> userList) {
        this.col_user = new Object[]{"ID", " İsim ", "Şifre", "Rol"};  // Kullanıcı tablosu sütun başlıkları tanımlanır.
        if (userList == null) {
            userList = this.userManager.getForTable(this.col_user.length, this.userManager.findAll());
        }
        createTable(this.tmdl_user, this.tbl_admin, col_user, userList);
    }


    // User filtreleme
    public void loadUserFilter() {
        this.cmb_type.removeAllItems(); // ComboBox'ı temizler.

        for (User.Role role : User.Role.values()) { // Tüm kullanıcı rollerini ComboBox'a ekler.
            this.cmb_type.addItem(role);
        }
        this.cmb_type.setSelectedItem(null);

    }


    public void loadUserCompenent() {

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
                    Helper.showMsg("done");
                    loadUserTable(null);  // Tablo yeniden yüklenir.
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_admin.setComponentPopupMenu(user_Menu);  // Tabloya sağ tıklama menüsü eklenir.

        // Arama butonuna tıklanınca yapılacak işlemleri belirler.
        this.btn_admin_search.addActionListener(e -> {
            User.Role selectedRole = (User.Role) cmb_type.getSelectedItem(); // Seçilen rolü alır.
            int userId = 0;
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
        // Sıfırlama butonuna tıklanınca yapılacak işlemleri belirler.
        this.btn_admin_reset.addActionListener(e -> {
            this.cmb_type.setSelectedItem(null);
            loadUserTable(null);

        });
    }

}












