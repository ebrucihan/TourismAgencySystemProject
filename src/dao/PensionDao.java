package dao;

import core.Db;
import entity.Pension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PensionDao {

    private Connection con;

    public PensionDao() {
        this.con = Db.getInstance();
    }

    public Pension getById(int id) {
        Pension pension = null;
        String query = "SELECT * FROM public.pensiontype WHERE pension_type_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                pension = new Pension();
                pension.setPension_type_id(rs.getInt("pension_type_id"));
                pension.setPension_hotel_id(rs.getInt("pension_hotel_id"));
                pension.setPension_type_ultra(rs.getBoolean("pension_type_ultra"));
                pension.setPension_type_hsd(rs.getBoolean("pension_type_hsd"));
                pension.setPension_type_breakfast(rs.getBoolean("pension_type_breakfast"));
                pension.setPension_type_tam(rs.getBoolean("pension_type_tam"));
                pension.setPension_type_yarim(rs.getBoolean("pension_type_yarim"));
                pension.setPension_type_just_bed(rs.getBoolean("pension_type_just_bed"));
                pension.setPension_type_ahfc(rs.getBoolean("pension_type_ahfc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pension;
    }
}
