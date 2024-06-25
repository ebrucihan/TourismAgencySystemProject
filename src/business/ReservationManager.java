package business;

import core.Helper;
import dao.ReservationDao;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationManager {
    // ReservationDao nesnesi, veri erişim işlemlerini gerçekleştirmek için kullanılır.
    private final ReservationDao reservationDao;

    // ReservationManager sınıfının yapıcı metodu. ReservationDao nesnesi örneklenir.
    public ReservationManager() {
        this.reservationDao = new ReservationDao();
    }

    // Belirtilen ID'ye sahip rezervasyonu döner.
    public Reservation getById(int id) {
        return this.reservationDao.getById(id);
    }

    // Oda ID'sine göre rezervasyonu döner.
    public Reservation getReservationById(int roomId) {
        return reservationDao.findById(roomId);
    }

    // Belirtilen oda ID'si için yeni stok miktarını günceller.
    public boolean updateRoomStock(int roomId, int newStock) {
        return reservationDao.updateRoomStock(roomId, newStock);
    }

    // Belirtilen ID'ye sahip rezervasyonu siler.
    public void deleteReservation(int reservationId) {
        reservationDao.deleteReservation(reservationId);
    }

    // Belirtilen oda ID'sine göre oda rezervasyonunu döner.
    public Reservation getRoomById(int roomId) {
        return reservationDao.findById(roomId);
    }

    // Tüm rezervasyonları döner.
    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    // Rezervasyonları tablo formatında döner.
    // size: satır boyutunu belirtir.
    // reservations: rezervasyonların listesini içerir.
    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservations) {
        ArrayList<Object[]> reservationList = new ArrayList<>();
        for (Reservation obj : reservations) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getReservation_id();  // Rezervasyon ID
            rowObject[i++] = obj.getReservation_room_id();  // Rezervasyon Oda ID
            rowObject[i++] = obj.getReservation_customer_name();  // Müşteri Adı
            rowObject[i++] = obj.getReservation_customer_contact();  // Müşteri İletişim
            rowObject[i++] = obj.getReservation_check_in_date();  // Giriş Tarihi
            rowObject[i++] = obj.getReservation_check_out_date();  // Çıkış Tarihi
            rowObject[i++] = obj.getReservation_total_price();  // Toplam Fiyat
            rowObject[i++] = obj.getReservation_guest_count_adult();  // Yetişkin Misafir Sayısı
            rowObject[i++] = obj.getReservation_guest_count_child();  // Çocuk Misafir Sayısı
            rowObject[i++] = obj.getReservation_customer_email();  // Müşteri Email
            rowObject[i++] = obj.getReservation_customer_tc();  // Müşteri TC Kimlik Numarası
            rowObject[i++] = obj.getReservation_customer_note();  // Müşteri Notu

            // Satır nesnesini listeye ekle
            reservationList.add(rowObject);
        }
        return reservationList;
    }

    // Yeni rezervasyon kaydeder.
    // reservation: kaydedilecek rezervasyon nesnesi
    public int save(Reservation reservation) throws SQLException {
        // Eğer rezervasyon ID'sine sahip bir rezervasyon varsa hata mesajı gösterir ve null döner.
        if (this.getById(reservation.getReservation_id()) != null) {
            Helper.showMsg("error");
            return save(null);
        }

        // Rezervasyonu veritabanına kaydeder ve rezervasyon ID'sini döner.
        return this.reservationDao.saveReservation(reservation);
    }
}
