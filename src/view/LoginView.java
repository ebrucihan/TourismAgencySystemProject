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


    public LoginView() {

        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(400, 400);

        // Giriş butonuna tıklanınca yapılacak işlemler belirlenir.
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_pass};
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
            } else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), new String(this.fld_pass.getPassword()));
                if (loginUser == null) {
                    Helper.showMsg("notFound");
                } else {
                    if (loginUser.getRole() == User.Role.ADMIN) {
                        new AdminView(loginUser).setVisible(true);
                    } else {
                        new WorkerView(loginUser).setVisible(true);
                    }
                    dispose();
                }
            }
        });

    }
}



