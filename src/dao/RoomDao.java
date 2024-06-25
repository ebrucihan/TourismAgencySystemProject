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
        this.con = Db.getInstance(); // Veritabanı bağlantısı oluşturuluyor
    }

    // Tüm odaları getirir
    public ArrayList<Room> findAll() {
        return this.selectByQuery("SELECT * FROM public.room ORDER BY room_id ASC");
    }

    // Belirli bir sorguya göre odaları getirir
    public ArrayList<Room> selectByQuery(String query) {
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                rooms.add(this.match(rs)); // Odalar eşleştiriliyor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return rooms;
    }

    // Belirli bir ID'ye sahip odayı getirir
    public Room findById(int roomId) {
        String query = "SELECT * FROM public.room WHERE room_id = ?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, roomId); // Parametre ayarlanıyor
            ResultSet rs = pr.executeQuery(); // Sorgu çalıştırılıyor
            if (rs.next()) {
                return match(rs); // Oda eşleştiriliyor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return null;
    }

    // Tüm otelleri getirir
    public ArrayList<Hotel> getAllHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        String query = "SELECT hotel_id, hotel_name FROM public.hotel";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int hotel_id = rs.getInt("hotel_id");
                String hotel_name = rs.getString("hotel_name");
                hotels.add(new Hotel(hotel_id, hotel_name)); // Otel nesnesi oluşturulup listeye ekleniyor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return hotels;
    }

    // Belirli bir otelin şehirlerini getirir
    public ArrayList<String> getHotelCities(int hotelId) {
        ArrayList<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT hotel_city FROM public.hotel WHERE hotel_id = ?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, hotelId); // Parametre ayarlanıyor
            ResultSet rs = pr.executeQuery(); // Sorgu çalıştırılıyor
            while (rs.next()) {
                cities.add(rs.getString("hotel_city")); // Şehirler listeye ekleniyor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return cities;
    }

    // ResultSet'ten oda nesnesi oluşturur
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
        return room; // Oluşturulan oda nesnesi döndürülüyor
    }

    // Tüm mevsimleri getirir
    public ArrayList<Room.Seasontype> getAllSeasons() {
        ArrayList<Room.Seasontype> seasons = new ArrayList<>();
        for (Room.Seasontype season : Room.Seasontype.values()) {
            seasons.add(season); // Mevsimler listeye ekleniyor
        }
        return seasons;
    }

    // Oda ekler
    public boolean addRoom(Room room) {
        String query = "INSERT INTO public.room (room_hotel_id, room_type, price_adult, price_child, room_stock, " +
                "room_bed_count, room_square_meters, room_tv, room_minibar, room_gameconsole, room_safe, room_projector, " +
                "room_season_type, room_pension_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pr = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pr.setInt(1, room.getRoom_hotel_id());

            // roomtype ve room_season_type null kontrolü

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

            int insertedRows = pr.executeUpdate(); // Sorgu çalıştırılıyor
            if (insertedRows > 0) {
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (generatedKeys.next()) {
                    room.setRoom_id(generatedKeys.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return false;
    }

    // Oda günceller
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
            return pr.executeUpdate() > 0; // Sorgu çalıştırılıyor ve güncelleme başarılıysa true döndürülüyor
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
            return false;
        }
    }

    // Oda siler
    public void delete(int roomId) {
        String query = "DELETE FROM public.room WHERE room_id=?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, roomId); // Parametre ayarlanıyor
            pr.executeUpdate(); // Sorgu çalıştırılıyor
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
    }

    // Odaları arar
    public ArrayList<Room> searchRooms(String startDate, String endDate, String city, String hotelName) {
        ArrayList<Room> rooms = new ArrayList<>();

        // Tarih aralığına göre uygun odaları bulan sorgu
        String query = "WITH AvailableRoomCounts AS ( " +
                "    SELECT r.room_id, r.room_stock + COUNT(res.reservation_id) AS available_stock " +
                "    FROM public.room r " +
                "    LEFT JOIN public.reservation res " +
                "    ON r.room_id = res.reservation_room_id " +
                "    AND (res.reservation_check_out_date < ?::DATE OR res.reservation_check_in_date > ?::DATE) " +
                "    GROUP BY r.room_id " +
                ") " +
                "SELECT r.*, ar.available_stock " +
                "FROM public.room r " +
                "JOIN public.hotel h ON r.room_hotel_id = h.hotel_id " +
                "JOIN AvailableRoomCounts ar ON r.room_id = ar.room_id " +
                "WHERE ar.available_stock > 0 ";

        // Şehir ve otel adı parametreleri ekleniyor (opsiyonel)
        if (city != null && !city.isEmpty()) {
            query += "AND h.hotel_city = ? ";
        }

        if (hotelName != null && !hotelName.isEmpty()) {
            query += "AND h.hotel_name = ? ";
        }

        query += "ORDER BY r.room_id ASC";

        try (PreparedStatement pr = con.prepareStatement(query)) {
            int paramIndex = 1;

            pr.setString(paramIndex++, startDate);
            pr.setString(paramIndex++, endDate);

            // Şehir parametresi varsa ekleniyor
            if (city != null && !city.isEmpty()) {
                pr.setString(paramIndex++, city);
            }

            // Otel adı parametresi varsa ekleniyor
            if (hotelName != null && !hotelName.isEmpty()) {
                pr.setString(paramIndex++, hotelName);
            }

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Room room = this.match(rs);
                room.setRoom_stock(rs.getInt("available_stock")); // Stok bilgisi ayarlanıyor
                rooms.add(room); // Oda listeye ekleniyor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return rooms;
    }
}









