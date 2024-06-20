package dao;

import core.Db;
import entity.Hotel;
import entity.Pension;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao {
    private Connection con;

    public RoomDao(){
        this.con = Db.getInstance();
    }

    public ArrayList<Room> findAll() {
        return this.selectByQuery("SELECT * FROM public.room ORDER BY room_id ASC");
    }

    public ArrayList<Room> selectByQuery(String query) {
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                rooms.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Room findById(int roomId) {
        String query = "SELECT * FROM public.room WHERE room_id = ?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, roomId);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Room match(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoom_id(rs.getInt("room_id"));
        room.setRoom_hotel_id(rs.getInt("room_hotel_id"));
        room.setRoomtype(Room.Roomtype.valueOf(rs.getString("room_type")));
        room.setPrice_adult(rs.getBigDecimal("price_adult"));
        room.setPrice_child(rs.getBigDecimal("price_child"));
        room.setRoom_stock(rs.getInt("room_stock"));
        room.setRoom_bed_count(rs.getInt("room_bed_count"));
        room.setRoom_square_meters(rs.getInt("room_square_meters"));
        room.setRoom_tv(rs.getBoolean("room_tv"));
        room.setRoom_minibar(rs.getBoolean("room_minibar"));
        room.setRoom_gameconsole(rs.getBoolean("room_gameconsole"));
        room.setRoom_safe(rs.getBoolean("room_safe"));
        room.setRoom_projector(rs.getBoolean("room_projector"));
        room.setRoom_season_type(Room.Seasontype.valueOf(rs.getString("room_season_type")));
        room.setRoom_pension_type(Room.Pensiontype.valueOf(rs.getString("room_pension_type")));
        return room;

    }

    public ArrayList<Room.Seasontype> getAllSeasons() {
        ArrayList<Room.Seasontype> seasons = new ArrayList<>();
        for (Room.Seasontype season : Room.Seasontype.values()) {
            seasons.add(season);
        }
        return seasons;
    }

}











