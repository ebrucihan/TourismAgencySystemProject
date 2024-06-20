package business;

import core.Helper;
import dao.UserDao;
import entity.User;

import java.util.ArrayList;
import java.util.Objects;

public class UserManager {
    private UserDao userDao;

    public UserManager(){
        this.userDao = new UserDao();
    }

    public User getById(int id) {
        return this.userDao.getById(id);
    }


    public User findByLogin(String username, String password){
        return this.userDao.findByLogin(username,password);


    }

    public ArrayList<User> findAll(){
        return this.userDao.findAll();
    }

    // Tablo için kullanıcıları formatlamak için kullanılan metod
    public ArrayList<Object []> getForTable (int size, ArrayList <User> users) {
        ArrayList<Object []> userRowList = new ArrayList<>();
        for (User obj : users ) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getUsername();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();
            userRowList.add(rowObject);

        }

        return userRowList;
    }

    // Yeni kullanıcı ekleme işlemi
    public boolean save(User user){
        if (this.getById(user.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }

        return this.userDao.save(user);
    }


    // Kullanıcı bilgilerini güncelleme işlemi
    public boolean update(User user) {
        if (this.getById(user.getId()) == null) {
            Helper.showMsg(user.getUsername() + "ID Kayıtlı kullanıcı bulunamadı");
            return false;
        }
        return this.userDao.update(user);
    }

    // Kullanıcı silme işlemi
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + "ID kayıtlı kullanıcı bulunamadı");
            return false;
        }
        return this.userDao.delete(id);
    }

    // Belirli bir kullanıcıya ait kullanıcıları getirme işlemi
    public ArrayList<User> getByListUserId (int userId) {
        return this.userDao.getByUserListId(userId);
    }


    // Tablo için arama yapma işlemi
    public ArrayList<User> searchForTable (int userId, User.Role role){
        String select = "SELECT * FROM public.user";
        ArrayList<String> whereList = new ArrayList<>();
        if (userId != 0) {
            whereList.add("user_id = " + userId);

        }
        if (role != null){
            whereList.add("user_role ='" + role.toString() + "'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (whereStr.length() > 0){
            query += " WHERE " + whereStr;
        }
        return this.userDao.selectByQuery(query);


    }
}




