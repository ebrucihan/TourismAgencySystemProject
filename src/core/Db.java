package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    //Db sınıfı veri bağlantıları için kullanılacaktır.
    //Singleton Design Pattern özelliği
    private static Db instance = null;
    private Connection connection = null;

    // Veri tabanı bağlantı bilgileri
    private final String DB_URL = "jdbc:postgresql://localhost:5432/tourismagencysystem";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASS = "2108";

    // Özel yapıcı metod, sadece sınıf içinden erişilebilir.
    private Db() {
        try{
            this.connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASS);
        }catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }
    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()){
                instance = new Db();
        }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return instance.getConnection();
    }
}
