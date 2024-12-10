package entity;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int reservation_id; // Rezervasyon ID'si
    private int reservation_room_id; // Rezervasyon yapılan oda ID'si
    private String reservation_customer_name; // Rezervasyon yapan müşteri adı
    private String reservation_customer_contact; // Rezervasyon yapan müşteri iletişim bilgisi
    private Date reservation_check_in_date; // Giriş tarihi
    private Date reservation_check_out_date; // Çıkış tarihi
    private double reservation_total_price; // Toplam fiyat
    private int reservation_guest_count_adult; // Yetişkin misafir sayısı
    private int reservation_guest_count_child; // Çocuk misafir sayısı
    private String reservation_customer_email; // Rezervasyon yapan müşteri e-posta adresi
    private String reservation_customer_tc; // Rezervasyon yapan müşteri TC kimlik numarası
    private String reservation_customer_note; // Rezervasyon notu

    public Reservation() {
    }

    public Reservation(int reservation_id, int reservation_room_id, String reservation_customer_name,
                       String reservation_customer_contact, Date reservation_check_in_date, Date reservation_check_out_date,
                       double reservation_total_price, int reservation_guest_count_adult, int reservation_guest_count_child,
                       String reservation_customer_email, String reservation_customer_tc, String reservation_customer_note) {
        this.reservation_id = reservation_id;
        this.reservation_room_id = reservation_room_id;
        this.reservation_customer_name = reservation_customer_name;
        this.reservation_customer_contact = reservation_customer_contact;
        this.reservation_check_in_date = reservation_check_in_date;
        this.reservation_check_out_date = reservation_check_out_date;
        this.reservation_total_price = reservation_total_price;
        this.reservation_guest_count_adult = reservation_guest_count_adult;
        this.reservation_guest_count_child = reservation_guest_count_child;
        this.reservation_customer_email = reservation_customer_email;
        this.reservation_customer_tc = reservation_customer_tc;
        this.reservation_customer_note = reservation_customer_note;
    }

    public Reservation(String customerName, String customerTel, String customerEmail, String customerTc, String customerNote, Room selectedRoom, Hotel selectedHotel, LocalDate startDate, LocalDate endDate, int adultCount, int childCount, double totalPrice) {
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getReservation_room_id() {
        return reservation_room_id;
    }

    public void setReservation_room_id(int reservation_room_id) {
        this.reservation_room_id = reservation_room_id;
    }

    public String getReservation_customer_name() {
        return reservation_customer_name;
    }

    public void setReservation_customer_name(String reservation_customer_name) {
        this.reservation_customer_name = reservation_customer_name;
    }

    public String getReservation_customer_contact() {
        return reservation_customer_contact;
    }

    public void setReservation_customer_contact(String reservation_customer_contact) {
        this.reservation_customer_contact = reservation_customer_contact;
    }

    public Date getReservation_check_in_date() {
        return  reservation_check_in_date;
    }

    public void setReservation_check_in_date(Date reservation_check_in_date) {
        this.reservation_check_in_date = reservation_check_in_date;
    }

    public  Date getReservation_check_out_date() {
        return  reservation_check_out_date;
    }

    public void setReservation_check_out_date(Date reservation_check_out_date) {
        this.reservation_check_out_date = reservation_check_out_date;
    }

    public double getReservation_total_price() {
        return reservation_total_price;
    }

    public void setReservation_total_price(double reservation_total_price) {
        this.reservation_total_price = reservation_total_price;
    }

    public int getReservation_guest_count_adult() {
        return reservation_guest_count_adult;
    }

    public void setReservation_guest_count_adult(int reservation_guest_count_adult) {
        this.reservation_guest_count_adult = reservation_guest_count_adult;
    }

    public int getReservation_guest_count_child() {
        return reservation_guest_count_child;
    }

    public void setReservation_guest_count_child(int reservation_guest_count_child) {
        this.reservation_guest_count_child = reservation_guest_count_child;
    }

    public String getReservation_customer_email() {
        return reservation_customer_email;
    }

    public void setReservation_customer_email(String reservation_customer_email) {
        this.reservation_customer_email = reservation_customer_email;
    }

    public String getReservation_customer_tc() {
        return reservation_customer_tc;
    }

    public void setReservation_customer_tc(String reservation_customer_tc) {
        this.reservation_customer_tc = reservation_customer_tc;
    }

    public String getReservation_customer_note() {
        return reservation_customer_note;
    }

    public void setReservation_customer_note(String reservation_customer_note) {
        this.reservation_customer_note = reservation_customer_note;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", reservation_room_id=" + reservation_room_id +
                ", reservation_customer_name='" + reservation_customer_name + '\'' +
                ", reservation_customer_contact='" + reservation_customer_contact + '\'' +
                ", reservation_check_in_date=" + reservation_check_in_date +
                ", reservation_check_out_date=" + reservation_check_out_date +
                ", reservation_total_price=" + reservation_total_price +
                ", reservation_guest_count_adult=" + reservation_guest_count_adult +
                ", reservation_guest_count_child=" + reservation_guest_count_child +
                ", reservation_customer_email='" + reservation_customer_email + '\'' +
                ", reservation_customer_tc='" + reservation_customer_tc + '\'' +
                ", reservation_customer_note='" + reservation_customer_note + '\'' +
                '}';
    }
}