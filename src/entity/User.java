package entity;

public class User {
    private int id; // Kullanıcı ID'si
    private String username; // Kullanıcı adı
    private String password; // Şifre
    private Role role; // Kullanıcı rolü

    public User() {
    }

    public enum Role {
        ADMIN,
        WORKER
    }



    public User (int id,String username,String password,Role role){
        this.id = id;
        this.username = username;
        this.password =  password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}


