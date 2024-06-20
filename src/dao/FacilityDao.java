package dao;

import core.Db;
import entity.Facility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacilityDao {

    private Connection con;

    public FacilityDao() {
        this.con = Db.getInstance();
    }

    public Facility getById(int id) {
        Facility facility = null;
        String query = "SELECT * FROM public.facility WHERE facility_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                facility = new Facility();
                facility.setFacility_id(rs.getInt("facility_id"));
                facility.setFacility_hotel_id(rs.getInt("facility_hotel_id"));
                facility.setFacility_free_park(rs.getBoolean("facility_free_park"));
                facility.setFacility_free_wifi(rs.getBoolean("facility_free_wifi"));
                facility.setFacility_pool(rs.getBoolean("facility_pool"));
                facility.setFacility_gym(rs.getBoolean("facility_gym"));
                facility.setFacility_concierge(rs.getBoolean("facility_concierge"));
                facility.setFacility_SPA(rs.getBoolean("facility_spa"));
                facility.setFacility_room_service(rs.getBoolean("facility_room_service"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facility;
    }
}