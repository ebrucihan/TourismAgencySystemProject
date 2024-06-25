package business;

import core.Helper;
import dao.ReservationDao;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationManager {
    private final ReservationDao reservationDao;


    public ReservationManager() {
        this.reservationDao = new ReservationDao();

    }

    public Reservation getById(int id) {
        return this.reservationDao.getById(id);
    }

    public Reservation getReservationById(int roomId) {
        return reservationDao.findById(roomId);
    }

    public boolean updateRoomStock(int roomId, int newStock) {
        return reservationDao.updateRoomStock(roomId, newStock);
    }

    public void deleteReservation(int reservationId) {
        reservationDao.deleteReservation(reservationId);
    }



    public Reservation getRoomById(int roomId) {
        return reservationDao.findById(roomId);
    }


    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservations) {
        ArrayList<Object[]> reservationList = new ArrayList<>();
        for (Reservation obj : reservations) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getReservation_id();
            rowObject[i++] = obj.getReservation_room_id();
            rowObject[i++] = obj.getReservation_customer_name();
            rowObject[i++] = obj.getReservation_customer_contact();
            rowObject[i++] = obj.getReservation_check_in_date();
            rowObject[i++] = obj.getReservation_check_out_date();
            rowObject[i++] = obj.getReservation_total_price();
            rowObject[i++] = obj.getReservation_guest_count_adult();
            rowObject[i++] = obj.getReservation_guest_count_child();
            rowObject[i++] = obj.getReservation_customer_email();
            rowObject[i++] = obj.getReservation_customer_tc();
            rowObject[i++] = obj.getReservation_customer_note();

            reservationList.add(rowObject);
        }
        return reservationList;
    }

    public int save(Reservation reservation) throws SQLException {
        if (this.getById(reservation.getReservation_id()) != null) {
            Helper.showMsg("error");
            return save(null);
        }

        return this.reservationDao.saveReservation(reservation);

    }
}


