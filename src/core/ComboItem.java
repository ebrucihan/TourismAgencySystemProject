package core;

public class ComboItem {

    // Kullanıcı adı, şifre ve rol bilgilerini tutan sınıf.
    private String username;
    private String password;
    private String role;

    // Yapıcı metot, kullanıcı adı, şifre ve rol bilgilerini alır.
    public ComboItem(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return role; // ComboBox'da rolü göstermek için
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

