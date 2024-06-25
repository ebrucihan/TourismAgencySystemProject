package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JPanel w_bottom;
    private JPasswordField fld_pass;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_pass;
    private JTextField fld_username;
    private final UserManager userManager;


    // Giriş ekranı oluşturulur.
    public LoginView() {
        this.userManager = new UserManager(); // Kullanıcı yönetim sınıfı oluşturulur.
        this.add(container); // Ana konteyner eklenir.
        this.guiInitilaze(400, 400); // GUI başlatılır.

        // Giriş butonuna tıklandığında yapılacak işlemler belirlenir.
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_pass}; // Kontrol edilecek alanlar listesi oluşturulur.
            if (Helper.isFieldListEmpty(checkFieldList)) { // Alanların boş olup olmadığı kontrol edilir.
                Helper.showMsg("fill"); // Boş alan uyarısı gösterilir.
            } else {
                // Kullanıcı adı ve şifreyle giriş yapılır.
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), new String(this.fld_pass.getPassword()));
                if (loginUser == null) { // Kullanıcı bulunamazsa uyarı gösterilir.
                    Helper.showMsg("notFound");
                } else {
                    if (loginUser.getRole() == User.Role.ADMIN) {
                        new AdminView(loginUser).setVisible(true); // Admin girişi yapılırsa AdminView açılır.
                    } else {
                        new WorkerView(loginUser).setVisible(true); // Diğer durumda WorkerView açılır.
                    }
                    dispose(); // Mevcut pencere kapatılır.
                }
            }
        });
    }
}

