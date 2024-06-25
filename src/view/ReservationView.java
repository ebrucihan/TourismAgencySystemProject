package view;

import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import dao.ReservationDao;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationView extends Layout {

    private User user;
    private JTextField txt_rzr_hotelname;
    private JTextField txt_rzr_hoteladres;
    private JTextField txt_rzr_hoteltel;
    private JTextField txt_rzr_hoteltype;
    private JTextField txt_rzr_roomtype;
    private JTextField txt_rzr_season;
    private JTextField txt_rzr_roomözellik;
    private JTextField txt_rzr_pensiontype;
    private JTextField txt_rzr_adultprice;
    private JTextField txt_rzr_pricechild;
    private JTextField txt_rzr_customername;
    private JTextField txt_rzr_customertel;
    private JTextField txt_rzr_customer_email;
    private JTextField txt_rzr_customer_tc;
    private JTextField txt_rzr_customer_note;
    private JTextField txt_rzr_totalprice;
    private JButton btn_rzr_calculate;
    private JButton btn_rzr_save;
    private JTextField txt_rzr_day;
    private JPanel container;
    private JTextField txt_rzr_adultcount;
    private ReservationManager reservationManager;
    private JTextField txt_rzr_childcount;
    private ReservationDao reservationDao;
    private JPopupMenu reservation_menu;
    private JTable tbl_reservation;
    private Reservation existingReservation;

    // Otel, oda, başlangıç ve bitiş tarihlerine göre rezervasyon görünümü oluşturulur.
    public ReservationView(Hotel selectedHotel, Room selectedRoom, LocalDate startDate, LocalDate endDate) {
        this(selectedHotel, selectedRoom, startDate, endDate, null); // Var olan rezervasyon için çağrı yapılır.
    }

    // Otel, oda, başlangıç, bitiş tarihleri ve var olan rezervasyona göre rezervasyon görünümü oluşturulur.
    public ReservationView(Hotel selectedHotel, Room selectedRoom, LocalDate startDate, LocalDate endDate, Reservation existingReservation) {
        this.existingReservation = existingReservation; // Var olan rezervasyon atanır.
        tbl_reservation = new JTable(); // Rezervasyon tablosu oluşturulur.
        this.reservationManager = new ReservationManager(); // Rezervasyon yöneticisi oluşturulur.
        this.reservationDao = new ReservationDao(); // Rezervasyon veri erişim nesnesi oluşturulur.
        this.add(container); // Ana konteyner eklenir.
        this.guiInitilaze(600, 650); // GUI başlatılır.

        if (existingReservation != null) { // Eğer var olan rezervasyon null değilse
            loadExistingReservationData(existingReservation); // Var olan rezervasyonun verilerini yükle
        }

        // Otel bilgilerini doldurma
        txt_rzr_hotelname.setText(selectedHotel.getHotel_name()); // Otel adını doldur
        txt_rzr_hoteladres.setText(selectedHotel.getHotel_adress()); // Otel adresini doldur
        txt_rzr_hoteltel.setText(selectedHotel.getHotel_mpno()); // Otel telefon numarasını doldur
        txt_rzr_hoteltype.setText(selectedHotel.getFacility().toString()); // Otel türünü doldur

        // Oda bilgilerini doldurma
        txt_rzr_roomtype.setText(selectedRoom.getRoomtype().name()); // Oda türünü doldur
        txt_rzr_season.setText(selectedRoom.getRoom_season_type().toString()); // Sezon tipini doldur
        txt_rzr_pensiontype.setText(selectedRoom.getRoom_pension_type().name()); // Pansiyon tipini doldur
        txt_rzr_adultprice.setText(String.valueOf(selectedRoom.getPrice_adult())); // Yetişkin fiyatını doldur
        txt_rzr_pricechild.setText(String.valueOf(selectedRoom.getPrice_child())); // Çocuk fiyatını doldur

        // Oda özelliklerini string olarak hazırlama
        StringBuilder roomFeatures = new StringBuilder();

        if (selectedRoom.getRoom_tv()) { // TV varsa
            roomFeatures.append("TV, ");
        }
        if (selectedRoom.getRoom_minibar()) { // Minibar varsa
            roomFeatures.append("Minibar, ");
        }
        if (selectedRoom.getRoom_gameconsole()) { // Oyun konsolu varsa
            roomFeatures.append("Console, ");
        }
        if (selectedRoom.getRoom_safe()) { // Kasa varsa
            roomFeatures.append("Safe, ");
        }
        if (selectedRoom.getRoom_projector()) { // Projektör varsa
            roomFeatures.append("Projector, ");
        }

        // Oda özelliklerinin sonundaki virgülü kaldırma
        if (roomFeatures.length() > 0) {
            roomFeatures.setLength(roomFeatures.length() - 2);
        }

        // Oda özelliklerini fld_reservation_room_type textfield'ına ekleme
        txt_rzr_roomözellik.setText(" (" + roomFeatures.toString() + ")");

        // Tarih farkını hesaplama ve gün bilgisini doldurma
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate); // Başlangıç ve bitiş tarihleri arasındaki gün sayısını hesapla
        txt_rzr_day.setText(String.valueOf(daysBetween)); // Gün sayısını doldur

        // Fiyatı hesapla butonuna tıklandığında yapılacak işlemler belirlenir.
        btn_rzr_calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Pansiyon tipi ve dönem tipi fiyatlarını al
                    String pensionType = txt_rzr_pensiontype.getText();
                    String seasonType = txt_rzr_season.getText();

                    // Yetişkin ve çocuk fiyatlarını al
                    double adultPrice = Double.parseDouble(txt_rzr_adultprice.getText());
                    double childPrice = Double.parseDouble(txt_rzr_pricechild.getText());

                    // Gün sayısını al
                    int days = Integer.parseInt(txt_rzr_day.getText());

                    // Yetişkin ve çocuk sayısını belirle (örneğin 2 yetişkin ve 1 çocuk)
                    int adultCount = Integer.parseInt(txt_rzr_adultcount.getText());
                    int childCount = Integer.parseInt(txt_rzr_childcount.getText());

                    // Gecelik fiyatı belirle
                    double nightlyRate = adultPrice * adultCount + childPrice * childCount;

                    // Toplam fiyatı hesapla
                    double totalPrice = nightlyRate * days;

                    // Toplam fiyatı fld_reservation_total_price textfield'ına yazdır
                    txt_rzr_totalprice.setText(String.format("%.2f", totalPrice));
                } catch (NumberFormatException ex) {
                    Helper.showMsg("error"); // Sayı formatı hatası durumunda uyarı göster
                }
            }
        });

        // Kaydet butonuna tıklandığında yapılacak işlemler belirlenir.
        btn_rzr_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Kullanıcı bilgilerinin alınması
                    String customerName = txt_rzr_customername.getText().trim();
                    String customerTel = txt_rzr_customertel.getText().trim();
                    String customerEmail = txt_rzr_customer_email.getText().trim();
                    String customerTc = txt_rzr_customer_tc.getText().trim();
                    String customerNote = txt_rzr_customer_note.getText().trim();
                    int adultCount = Integer.parseInt(txt_rzr_adultcount.getText().trim());
                    int childCount = Integer.parseInt(txt_rzr_childcount.getText().trim());

                    // Gerekli alanların boş olup olmadığını kontrol etme
                    JTextField[] fieldList = {txt_rzr_customername, txt_rzr_customertel, txt_rzr_customer_email, txt_rzr_customer_tc};
                    if (Helper.isFieldListEmpty(fieldList)) {
                        Helper.showMsg("fill"); // Boş alan uyarısı göster
                        return;
                    }

                    // Total price'ın alınması ve kontrolü
                    String totalPriceStr = txt_rzr_totalprice.getText().trim().replace(',', '.'); // Virgülü noktaya çeviriyoruz
                    double totalPrice = Double.parseDouble(totalPriceStr);

                    // Rezervasyon gün sayısının alınması ve kontrolü
                    int stayDuration;
                    try {
                        stayDuration = Integer.parseInt(txt_rzr_day.getText().trim());
                    } catch (NumberFormatException ex) {
                        Helper.showMsg("Lütfen geçerli bir gün sayısı giriniz."); // Geçersiz gün sayısı hatası durumunda uyarı göster
                        return;
                    }

                    Reservation reservation = new Reservation(); // Yeni rezervasyon nesnesi oluşturulur
                    reservation.setReservation_room_id(selectedRoom.getRoom_id()); // Oda ID'si atanır
                    reservation.setReservation_customer_name(customerName); // Müşteri adı atanır
                    reservation.setReservation_customer_contact(customerTel); // Müşteri telefonu atanır
                    reservation.setReservation_check_in_date(java.sql.Date.valueOf(startDate)); // Giriş tarihi atanır
                    reservation.setReservation_check_out_date(java.sql.Date.valueOf(endDate)); // Çıkış tarihi atanır
                    reservation.setReservation_total_price(totalPrice); // Toplam fiyat atanır
                    reservation.setReservation_guest_count_adult(adultCount); // Yetişkin sayısı atanır
                    reservation.setReservation_guest_count_child(childCount); // Çocuk sayısı atanır
                    reservation.setReservation_customer_email(customerEmail); // Müşteri e-posta atanır
                    reservation.setReservation_customer_tc(customerTc); // Müşteri TC kimlik numarası atanır
                    reservation.setReservation_customer_note(customerNote); // Müşteri notu atanır

                    if (existingReservation != null) { // Eğer var olan rezervasyon null değilse
                        // Mevcut rezervasyon, güncelle
                        reservation.setReservation_id(existingReservation.getReservation_id()); // Rezervasyon ID'si atanır
                        reservationDao.updateReservation(reservation); // Rezervasyon güncellenir

                        // Oda stok sayısını güncelle (eğer gerekliyse)
                        int newStock = selectedRoom.getRoom_stock(); // Yeni stok sayısı atanır
                        reservationManager.updateRoomStock(selectedRoom.getRoom_id(), newStock); // Oda stok güncellemesi yapılır

                        Helper.showMsg("done"); // İşlem tamamlandı mesajı gösterilir
                    } else {
                        // Yeni rezervasyon, ekle
                        int reservationId = reservationDao.saveReservation(reservation); // Rezervasyon kaydedilir ve ID'si alınır
                        reservation.setReservation_id(reservationId); // Rezervasyon ID'si atanır

                        // Oda stok sayısını güncelle
                        int newStock = selectedRoom.getRoom_stock() - 1; // Yeni stok sayısı belirlenir
                        reservationManager.updateRoomStock(selectedRoom.getRoom_id(), newStock); // Oda stok güncellemesi yapılır

                        // Tabloya eklemek için yeni bir satır oluşturma
                        Object[] newRow = {
                                reservation.getReservation_id(),
                                reservation.getReservation_room_id(),
                                reservation.getReservation_customer_name(),
                                reservation.getReservation_customer_contact(),
                                reservation.getReservation_check_in_date(),
                                reservation.getReservation_check_out_date(),
                                reservation.getReservation_total_price(),
                                reservation.getReservation_guest_count_adult(),
                                reservation.getReservation_guest_count_child(),
                                reservation.getReservation_customer_email(),
                                reservation.getReservation_customer_tc(),
                                reservation.getReservation_customer_note()
                        };

                        // Tabloya yeni satırı ekle
                        DefaultTableModel model = (DefaultTableModel) tbl_reservation.getModel();
                        model.addRow(newRow);

                        Helper.showMsg("done"); // İşlem tamamlandı mesajı gösterilir
                    }
                    dispose(); // Mevcut pencere kapatılır
                } catch (IllegalStateException ex) {
                    Helper.showMsg("error"); // Hata durumunda genel hata mesajı gösterilir
                    Helper.showMsg("Oda stoğu yetersiz!"); // Oda stoğu yetersiz uyarısı gösterilir
                } catch (NumberFormatException ex) {
                    Helper.showMsg("Lütfen geçerli bir sayı formatı giriniz."); // Geçersiz sayı formatı hatası durumunda uyarı gösterilir
                } catch (Exception ex) {
                    Helper.showMsg("error"); // Hata durumunda genel hata mesajı gösterilir
                    ex.printStackTrace(); // Hatanın konsolda detaylı görüntülenmesi için
                }
            }
        });
    }

    // Var olan rezervasyon verilerini yükler
    private void loadExistingReservationData(Reservation reservation) {
        txt_rzr_customername.setText(reservation.getReservation_customer_name()); // Müşteri adını doldur
        txt_rzr_customertel.setText(reservation.getReservation_customer_contact()); // Müşteri telefonunu doldur
        txt_rzr_customer_email.setText(reservation.getReservation_customer_email()); // Müşteri e-postasını doldur
        txt_rzr_customer_tc.setText(String.valueOf(reservation.getReservation_customer_tc())); // Müşteri TC kimlik numarasını doldur
        txt_rzr_customer_note.setText(reservation.getReservation_customer_note()); // Müşteri notunu doldur
        txt_rzr_totalprice.setText(String.valueOf(reservation.getReservation_total_price())); // Toplam fiyatı doldur
        txt_rzr_adultcount.setText(String.valueOf(reservation.getReservation_guest_count_adult())); // Yetişkin sayısını doldur
        txt_rzr_childcount.setText(String.valueOf(reservation.getReservation_guest_count_child())); // Çocuk sayısını doldur
    }

}