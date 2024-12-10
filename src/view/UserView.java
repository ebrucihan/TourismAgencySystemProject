package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class UserView extends Layout {
    private JPanel container;
    private JLabel lbl_heading;
    private JTextField fld_username;
    private JTextField fld_pass;
    private JComboBox<User.Role> cmb_user_type;
    private JButton btn_user_save;
    private User user;
    private UserManager userManager;

    // Kullanıcı görünümü oluşturucu
    public UserView(User user) {
        this.user = user; // Kullanıcı atanır
        this.userManager = new UserManager(); // Kullanıcı yöneticisi oluşturulur
        this.add(container); // Konteyner eklenir
        this.guiInitilaze(300,300); // GUI başlatılır

        // Kullanıcı tipi seçim kutusuna kullanıcı rolleri eklenir
        for (User.Role role : User.Role.values()) {
            this.cmb_user_type.addItem(role);
        }

        // Eğer kullanıcı ID'si 0 değilse (var olan kullanıcı ise)
        if (this.user.getId() != 0) {
            // Kullanıcı adı, şifre ve kullanıcı tipi alanları doldurulur
            this.fld_username.setText(this.user.getUsername());
            this.fld_pass.setText(this.user.getPassword());
            this.cmb_user_type.setSelectedItem(this.user.getRole());
        }

        // Kaydet butonuna tıklandığında yapılacak işlemler
        this.btn_user_save.addActionListener(e -> {
            // Gerekli alanların boş olup olmadığı kontrol edilir
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_username, this.fld_pass})) {
                Helper.showMsg("fill"); // Boş alan uyarısı gösterilir
            } else {
                boolean result;

                // Kullanıcı adı, şifre ve kullanıcı tipi güncellenir
                this.user.setUsername(this.fld_username.getText());
                this.user.setPassword(this.fld_pass.getText());
                this.user.setRole((User.Role) this.cmb_user_type.getSelectedItem());

                // Eğer kullanıcı ID'si 0 değilse (var olan kullanıcı ise)
                if (this.user.getId() != 0) {
                    result = this.userManager.update(this.user); // Kullanıcı güncellenir
                } else {
                    result = this.userManager.save(this.user); // Yeni kullanıcı kaydedilir
                }

                // İşlem sonucuna göre mesaj gösterilir
                if (result) {
                    Helper.showMsg("done"); // İşlem tamamlandı mesajı gösterilir
                    dispose(); // Pencere kapatılır
                } else {
                    Helper.showMsg("error"); // Hata mesajı gösterilir
                }
            }
        });
    }
}