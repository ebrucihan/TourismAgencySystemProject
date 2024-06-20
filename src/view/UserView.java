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

    public UserView(User user) {
        this.user = user;
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(300,300);

        for (User.Role role : User.Role.values()) {
            this.cmb_user_type.addItem(role);
        }

        if (this.user.getId() != 0) {
            this.fld_username.setText(this.user.getUsername());
            this.fld_pass.setText(this.user.getPassword());
            this.cmb_user_type.setSelectedItem(this.user.getRole());
        }

        // Kaydet butonuna tıklandığında yapılacak işlemler
        this.btn_user_save.addActionListener(e -> {

            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_username, this.fld_pass})) {
                Helper.showMsg("fill");
            } else {
                boolean result;

                this.user.setUsername(this.fld_username.getText());
                this.user.setPassword(this.fld_pass.getText());
                this.user.setRole((User.Role) this.cmb_user_type.getSelectedItem());
                if (this.user.getId() != 0) {
                    result = this.userManager.update(this.user);
                } else {
                    result = this.userManager.save(this.user);
                }
                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

    }
}

