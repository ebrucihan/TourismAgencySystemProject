package dao;

import core.Db;
import entity.Facility;
import entity.Hotel;
import entity.Pension;

import java.sql.*;
import java.util.ArrayList;

public class HotelDao {

    private Connection con;

    public HotelDao() {
        this.con = Db.getInstance(); // Veritabanı bağlantısı oluşturuluyor
    }

    // Tüm otelleri getirir
    public ArrayList<Hotel> findAll() {
        return this.selectByQuery("SELECT * FROM public.hotel ORDER BY hotel_id ASC");
    }

    // Belirli bir sorguya göre otelleri getirir
    public ArrayList<Hotel> selectByQuery(String query) {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query); // Sorgu çalıştırılıyor
            while (rs.next()) {
                hotels.add(this.match(rs)); // Oteller listesine eşleştirme yapılıyor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return hotels; // Oteller listesi döndürülüyor
    }

    // Belirli bir id'ye sahip oteli getirir
    public Hotel findById(int hotelId) {
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, hotelId); // Parametre ayarlanıyor
            ResultSet rs = pr.executeQuery(); // Sorgu çalıştırılıyor
            if (rs.next()) {
                return match(rs); // Oteli eşleştirerek döndürüyor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
        return null; // Bulunamazsa null döndürülüyor
    }

    // ResultSet'ten otel nesnesi oluşturur
    private Hotel match(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel(); // Otel nesnesi oluşturuluyor
        hotel.setHotel_id(rs.getInt("hotel_id")); // Otel özellikleri ayarlanıyor
        hotel.setHotel_name(rs.getString("hotel_name"));
        hotel.setHotel_adress(rs.getString("hotel_adress"));
        hotel.setHotel_city(rs.getString("hotel_city"));
        hotel.setHotel_region(rs.getString("hotel_region"));
        hotel.setHotel_mail(rs.getString("hotel_mail"));
        hotel.setHotel_mpno(rs.getString("hotel_mpno"));
        hotel.setHotel_stars(rs.getString("hotel_stars"));

        int facilityId = rs.getInt("hotel_facility_id");
        if (facilityId > 0) {
            Facility facility = getFacilityById(facilityId); // Tesis özellikleri alınıyor
            hotel.setFacility(facility); // Oteli tesis ile ilişkilendiriyor
        }

        int pensionTypeId = rs.getInt("hotel_pension_type_id");
        if (pensionTypeId > 0) {
            Pension pension = getPensionById(pensionTypeId); // Pansiyon özellikleri alınıyor
            hotel.setPension(pension); // Oteli pansiyon ile ilişkilendiriyor
        }

        return hotel; // Otel nesnesi döndürülüyor
    }

    // Yeni bir otel ekler
    public void insert(Hotel hotel) {
        String query = "INSERT INTO public.hotel (hotel_name, hotel_adress, hotel_city, hotel_region, hotel_mail, hotel_mpno, hotel_stars, hotel_facility_id, hotel_pension_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pr = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, hotel.getHotel_name()); // Parametreler ayarlanıyor
            pr.setString(2, hotel.getHotel_adress());
            pr.setString(3, hotel.getHotel_city());
            pr.setString(4, hotel.getHotel_region());
            pr.setString(5, hotel.getHotel_mail());
            pr.setString(6, hotel.getHotel_mpno());
            pr.setString(7, hotel.getHotel_stars());

            if (hotel.getFacility() != null) {
                pr.setInt(8, hotel.getFacility().getFacility_id());
            } else {
                pr.setNull(8, java.sql.Types.INTEGER);
            }

            if (hotel.getPension() != null) {
                pr.setInt(9, hotel.getPension().getPension_type_id());
            } else {
                pr.setNull(9, java.sql.Types.INTEGER);
            }

            int insertedRows = pr.executeUpdate(); // Sorgu çalıştırılıyor
            if (insertedRows > 0) {
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (generatedKeys.next()) {
                    hotel.setHotel_id(generatedKeys.getInt(1)); // Oluşturulan otel id'si ayarlanıyor
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
    }

    // Bir oteli günceller
    public boolean update(Hotel hotel) {
        String query = "UPDATE public.hotel SET hotel_name=?, hotel_adress=?, hotel_city=?, hotel_region=?, hotel_mail=?, hotel_mpno=?, hotel_stars=?, hotel_facility_id=?, hotel_pension_type_id=? WHERE hotel_id=?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setString(1, hotel.getHotel_name()); // Parametreler ayarlanıyor
            pr.setString(2, hotel.getHotel_adress());
            pr.setString(3, hotel.getHotel_city());
            pr.setString(4, hotel.getHotel_region());
            pr.setString(5, hotel.getHotel_mail());
            pr.setString(6, hotel.getHotel_mpno());
            pr.setString(7, hotel.getHotel_stars());

            if (hotel.getFacility() != null) {
                pr.setInt(8, hotel.getFacility().getFacility_id());
            } else {
                pr.setNull(8, java.sql.Types.INTEGER);
            }

            if (hotel.getPension() != null) {
                pr.setInt(9, hotel.getPension().getPension_type_id());
            } else {
                pr.setNull(9, java.sql.Types.INTEGER);
            }

            pr.setInt(10, hotel.getHotel_id());
            return pr.executeUpdate() > 0; // Sorgu çalıştırılıyor ve başarılı olup olmadığı döndürülüyor
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
            return false;
        }
    }

    // Bir oteli siler
    public void delete(int hotelId) {
        String query = "DELETE FROM public.hotel WHERE hotel_id=?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, hotelId); // Parametre ayarlanıyor
            pr.executeUpdate(); // Sorgu çalıştırılıyor
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
    }

    // Yeni bir tesis ekler
    public void insertFacility(Facility facility) {
        String query = "INSERT INTO public.facility (facility_hotel_id, facility_free_park, facility_free_wifi, facility_pool, facility_gym, " +
                "facility_concierge, facility_spa, facility_room_service) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pr = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pr.setInt(1, facility.getFacility_hotel_id()); // Parametreler ayarlanıyor
            pr.setBoolean(2, facility.isFacility_free_park());
            pr.setBoolean(3, facility.isFacility_free_wifi());
            pr.setBoolean(4, facility.isFacility_pool());
            pr.setBoolean(5, facility.isFacility_gym());
            pr.setBoolean(6, facility.isFacility_concierge());
            pr.setBoolean(7, facility.isFacility_SPA());
            pr.setBoolean(8, facility.isFacility_room_service());

            int insertedRows = pr.executeUpdate(); // Sorgu çalıştırılıyor
            if (insertedRows > 0) {
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (generatedKeys.next()) {
                    facility.setFacility_id(generatedKeys.getInt(1)); // Oluşturulan tesis id'si ayarlanıyor
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
    }

    // Yeni bir pansiyon ekler
    public void insertPension(Pension pension) {
        String query = "INSERT INTO public.pensiontype (pension_hotel_id, pension_type_ultra, pension_type_hsd, pension_type_breakfast, pension_type_tam, pension_type_yarim, pension_type_just_bed, pension_type_ahfc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pr = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pr.setInt(1, pension.getPension_hotel_id()); // Parametreler ayarlanıyor
            pr.setBoolean(2, pension.isPension_type_ultra());
            pr.setBoolean(3, pension.isPension_type_hsd());
            pr.setBoolean(4, pension.isPension_type_breakfast());
            pr.setBoolean(5, pension.isPension_type_tam());
            pr.setBoolean(6, pension.isPension_type_yarim());
            pr.setBoolean(7, pension.isPension_type_just_bed());
            pr.setBoolean(8, pension.isPension_type_ahfc());

            int insertedRows = pr.executeUpdate(); // Sorgu çalıştırılıyor
            if (insertedRows > 0) {
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (generatedKeys.next()) {
                    pension.setPension_type_id(generatedKeys.getInt(1)); // Oluşturulan pansiyon id'si ayarlanıyor
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatası durumunda hata mesajı yazdırılıyor
        }
    }

    // Belirli bir tesis id'ye sahip tesis nesnesi getirir
    private Facility getFacilityById(int facilityId) {
        FacilityDao facilityDao = new FacilityDao();
        return facilityDao.getById(facilityId); // Tesis dao üzerinden id'ye göre tesis getiriliyor
    }

    // Belirli bir pansiyon id'ye sahip pansiyon nesnesi getirir
    private Pension getPensionById(int pensionId) {
        PensionDao pensionDao = new PensionDao();
        return pensionDao.getById(pensionId); // Pansiyon dao üzerinden id'ye göre pansiyon getiriliyor
    }
}