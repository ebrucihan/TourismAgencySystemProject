package dao;

import core.Db;
import entity.Reservation;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDao {
    private Connection con;

    public ReservationDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<Reservation> findAll() {
        return this.selectByQuery("SELECT * FROM public.reservation ORDER BY reservation_id ASC");
    }

    public ArrayList<Reservation> selectByQuery(String query) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                reservations.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public Reservation findById(int roomId) {
        String query = "SELECT * FROM public.reservation WHERE reservation_id = ?";
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

    public Reservation getById(int id) {
        Reservation obj = null;
        String query = "SELECT * FROM public.reservation WHERE reservation_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) obj = this.match(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public boolean updateRoomStock(int roomId, int newStock) {
        String query = "UPDATE public.room SET room_stock = ? WHERE room_id = ?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, newStock);
            pr.setInt(2, roomId);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Reservation match(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservation_id(rs.getInt("reservation_id"));
        reservation.setReservation_room_id(rs.getInt("reservation_room_id"));
        reservation.setReservation_customer_name(rs.getString("reservation_customer_name"));
        reservation.setReservation_customer_contact(rs.getString("reservation_customer_contact"));
        reservation.setReservation_check_in_date(rs.getDate("reservation_check_in_date"));
        reservation.setReservation_check_out_date(rs.getDate("reservation_check_out_date"));
        reservation.setReservation_total_price(rs.getDouble("reservation_total_price"));
        reservation.setReservation_guest_count_adult(rs.getInt("reservation_guest_count_adult"));
        reservation.setReservation_guest_count_child(rs.getInt("reservation_guest_count_child"));
        reservation.setReservation_customer_email(rs.getString("reservation_customer_email"));
        reservation.setReservation_customer_tc(rs.getString("reservation_customer_tc"));
        reservation.setReservation_customer_note(rs.getString("reservation_customer_note"));

        return reservation;
    }

    public int saveReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO public.reservation (reservation_room_id, reservation_customer_name, " +
                "reservation_customer_contact, reservation_check_in_date, reservation_check_out_date, reservation_total_price, " +
                "reservation_guest_count_adult, reservation_guest_count_child, reservation_customer_email, " +
                "reservation_customer_tc, reservation_customer_note) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int reservationId = -1;
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reservation.getReservation_room_id());
            stmt.setString(2, reservation.getReservation_customer_name());
            stmt.setString(3, reservation.getReservation_customer_contact());
            stmt.setDate(4, new java.sql.Date(reservation.getReservation_check_in_date().getTime()));
            stmt.setDate(5, new java.sql.Date(reservation.getReservation_check_out_date().getTime()));
            stmt.setDouble(6, reservation.getReservation_total_price());
            stmt.setInt(7, reservation.getReservation_guest_count_adult());
            stmt.setInt(8, reservation.getReservation_guest_count_child());
            stmt.setString(9, reservation.getReservation_customer_email());
            stmt.setString(10, reservation.getReservation_customer_tc());
            stmt.setString(11, reservation.getReservation_customer_note());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                // Yeni eklenen rezervasyonun ID'sini alın
                java.sql.ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    reservationId = generatedKeys.getInt(1);
                    reservation.setReservation_id(reservationId); // Rezervasyon nesnesine ID'yi ayarlayın
                } else {
                    throw new SQLException("Rezervasyon ID alınamadı, kayıt başarısız.");
                }
            } else {
                throw new SQLException("Rezervasyon kaydedilemedi, hiçbir satır etkilenmedi.");
            }
        }
        return reservationId;
    }

    public boolean updateReservation(Reservation reservation) {
        String query = "UPDATE public.reservation SET reservation_room_id = ?, reservation_customer_name = ?, " +
                "reservation_customer_contact = ?, reservation_check_in_date = ?, reservation_check_out_date = ?, " +
                "reservation_total_price = ?, reservation_guest_count_adult = ?, reservation_guest_count_child = ?, " +
                "reservation_customer_email = ?, reservation_customer_tc = ?, reservation_customer_note = ? " +
                "WHERE reservation_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, reservation.getReservation_room_id());
            pr.setString(2, reservation.getReservation_customer_name());
            pr.setString(3, reservation.getReservation_customer_contact());
            pr.setDate(4, new java.sql.Date(reservation.getReservation_check_in_date().getTime()));
            pr.setDate(5, new java.sql.Date(reservation.getReservation_check_out_date().getTime()));
            pr.setDouble(6, reservation.getReservation_total_price());
            pr.setInt(7, reservation.getReservation_guest_count_adult());
            pr.setInt(8, reservation.getReservation_guest_count_child());
            pr.setString(9, reservation.getReservation_customer_email());
            pr.setString(10, reservation.getReservation_customer_tc());
            pr.setString(11, reservation.getReservation_customer_note());
            pr.setInt(12, reservation.getReservation_id());

            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteReservation(int reservationId) {
        String query = "DELETE FROM public.reservation WHERE reservation_id=?";
        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, reservationId);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}