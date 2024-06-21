package dao;

import core.Db;
import entity.Hotel;
import entity.Pension;
import entity.Reservation;
import entity.Room;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomDao {
    private Connection con;

    public RoomDao() {
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

    public ArrayList<Hotel> getAllHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        String query = "SELECT hotel_id, hotel_name FROM public.hotel";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int hotel_id = rs.getInt("hotel_id");
                String hotel_name = rs.getString("hotel_name");
                hotels.add(new Hotel(hotel_id, hotel_name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public ArrayList<String> getHotelCities(int hotelId) {
        ArrayList<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT hotel_city FROM public.hotel WHERE hotel_id = ?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                cities.add(rs.getString("hotel_city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }


    public Room match(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoom_id(rs.getInt("room_id"));
        room.setRoom_hotel_id(rs.getInt("room_hotel_id"));
        room.setRoomtype(Room.Roomtype.valueOf(rs.getString("room_type")));
        room.setPrice_adult(rs.getDouble("price_adult"));
        room.setPrice_child(rs.getDouble("price_child"));
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

    public boolean addRoom(Room room) {
        String query = "INSERT INTO public.room (room_hotel_id, room_type, price_adult, price_child, room_stock, " +
                "room_bed_count, room_square_meters, room_tv, room_minibar, room_gameconsole, room_safe, room_projector, " +
                "room_season_type, room_pension_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pr = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pr.setInt(1, room.getRoom_hotel_id());

            // roomtype null kontrolü

            pr.setString(2, room.getRoomtype().name());

            pr.setDouble(3, room.getPrice_adult());
            pr.setDouble(4, room.getPrice_child());
            pr.setInt(5, room.getRoom_stock());
            pr.setInt(6, room.getRoom_bed_count());
            pr.setInt(7, room.getRoom_square_meters());
            pr.setBoolean(8, room.getRoom_tv());
            pr.setBoolean(9, room.getRoom_minibar());
            pr.setBoolean(10, room.getRoom_gameconsole());
            pr.setBoolean(11, room.getRoom_safe());
            pr.setBoolean(12, room.getRoom_projector());

            // room_season_type null kontrolü

            pr.setString(13, room.getRoom_season_type().name());

            pr.setString(14, room.getRoom_pension_type().name());

            int insertedRows = pr.executeUpdate();
            if (insertedRows > 0) {
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (generatedKeys.next()) {
                    room.setRoom_id(generatedKeys.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Room room) {
        String query = "UPDATE public.room SET room_hotel_id = ?, room_type = ?, price_adult = ?, price_child = ?, room_stock = ?, " +
                "room_bed_count = ?, room_square_meters = ?, room_tv = ?, room_minibar = ?, room_gameconsole = ?, room_safe = ?, room_projector = ?, " +
                "room_season_type = ?, room_pension_type = ? WHERE room_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, room.getRoom_hotel_id());
            pr.setString(2, room.getRoomtype().name());
            pr.setDouble(3, room.getPrice_adult());
            pr.setDouble(4, room.getPrice_child());
            pr.setInt(5, room.getRoom_stock());
            pr.setInt(6, room.getRoom_bed_count());
            pr.setInt(7, room.getRoom_square_meters());
            pr.setBoolean(8, room.getRoom_tv());
            pr.setBoolean(9, room.getRoom_minibar());
            pr.setBoolean(10, room.getRoom_gameconsole());
            pr.setBoolean(11, room.getRoom_safe());
            pr.setBoolean(12, room.getRoom_projector());
            pr.setString(13, room.getRoom_season_type().name());
            pr.setString(14, room.getRoom_pension_type().name());
            pr.setInt(15, room.getRoom_id());
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delete(int roomId) {
        String query = "DELETE FROM public.room WHERE room_id=?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, roomId);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Room> searchRooms(String startDate, String endDate, String city, String hotelName) {
        ArrayList<Room> rooms = new ArrayList<>();

        String query = "SELECT r.* FROM public.room r " +
                "JOIN public.hotel h ON r.room_hotel_id = h.hotel_id " +
                "LEFT JOIN public.reservation res ON r.room_id = res.reservation_room_id " +
                "WHERE r.room_stock > 0 ";

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            query += "AND NOT EXISTS (SELECT 1 FROM public.reservation res " +
                    "WHERE res.reservation_room_id = r.room_id " +
                    "AND (res.reservation_check_in_date <= '" + endDate + "' " +
                    "AND res.reservation_check_out_date >= '" + startDate + "')) ";
        }

        if (city != null && !city.isEmpty()) {
            query += "AND h.hotel_city = '" + city + "' ";
        }

        if (hotelName != null && !hotelName.isEmpty()) {
            query += "AND h.hotel_name = '" + hotelName + "' ";
        }

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
}












