package dao;

import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection con;

    public UserDao() {
        this.con = Db.getInstance(); // Veritabanı bağlantısı oluşturuluyor
    }

    // Belirli bir ID'ye sahip kullanıcıyı getirme işlemi
    public User getById(int id) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id); // Parametre ayarlanıyor
            ResultSet rs = pr.executeQuery(); // Sorgu çalıştırılıyor
            if (rs.next()) obj = this.match(rs); // Sonuç eşleştiriliyor
        } catch (SQLException throwables) {
            throwables.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return obj;
    }


    // Tüm kullanıcıları getirme işlemi
    public ArrayList<User> findAll(){
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()){
                userList.add(this.match(rs)); // Kullanıcılar listeye ekleniyor
            }
        } catch (SQLException e){
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return userList;
    }

    // Belirli bir kullanıcıya ait bilgileri getirme işlemi
    public ArrayList <User> getByUserListId(int userId) {
        return this.selectByQuery("SELECT * FROM public.user WHERE user_id = " + userId);
    }

    // Belirli bir sorguya göre kullanıcıları getirme işlemi
    public ArrayList<User> selectByQuery(String query) {
        ArrayList<User> userList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                userList.add(this.match(rs)); // Kullanıcılar listeye ekleniyor
            }
        }catch (SQLException throwable) {
            throwable.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return userList;
    }

    // Kullanıcı girişi için belirli bir kullanıcı adı ve şifreye göre kullanıcıyı getirme işlemi
    public User findByLogin(String username, String password) {
        User user = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_pass = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username); // Parametreler ayarlanıyor
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_pass"),
                            User.Role.valueOf(rs.getString("user_role").toUpperCase())
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return user;
    }

    // Belirli bir kullanıcıyı silme işlemi
    public boolean delete(int user_id) {
        String query = "DELETE FROM public.user WHERE user_id = ?";
        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,user_id); // Parametre ayarlanıyor
            return pr.executeUpdate() != -1; // Sorgu çalıştırılıyor ve başarılıysa true döndürülüyor
        }catch (SQLException throwables) {
            throwables.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return true;

    }


    // Yeni bir kullanıcı kaydetme işlemi
    public boolean save(User user) {
        String query = "INSERT INTO public.user " +
                "(user_name, user_pass, user_role) " +
                "VALUES (?,?,?)";

        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1,user.getUsername()); // Parametreler ayarlanıyor
            pr.setString(2,user.getPassword());
            pr.setString(3,user.getRole().name());
            pr.executeUpdate(); // Sorgu çalıştırılıyor
            return true;
        }catch (SQLException throwables) {
            throwables.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return true;
    }

    // Kullanıcı bilgilerini güncelleme işlemi
    public boolean update(User user) {
        String query = "UPDATE public.user SET " +
                "user_name = ?, " +
                "user_pass = ?, " +
                "user_role = ? " +
                "WHERE user_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1, user.getUsername()); // Parametreler ayarlanıyor
            pr.setString(2, user.getPassword());
            pr.setString(3, user.getRole().name());
            pr.setInt(4, user.getId());
            return pr.executeUpdate() != -1; // Sorgu çalıştırılıyor ve başarılıysa true döndürülüyor

        } catch (SQLException throwables) {
            throwables.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return false;

    }

    // ResultSet'ten dönen sonucu bir User nesnesine eşleme işlemi
    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword(rs.getString("user_pass"));
        String role = rs.getString("user_role").toUpperCase();
        obj.setRole(User.Role.valueOf(role));
        return obj;
    }


}

