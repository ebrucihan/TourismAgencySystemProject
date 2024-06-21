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

    public ReservationView(Hotel selectedHotel, Room selectedRoom, LocalDate startDate, LocalDate endDate) {
        super();
        this.reservationManager = new ReservationManager();
        this.reservationDao = new ReservationDao();

        this.add(container);
        this.guiInitilaze(600, 650);

        // Otel bilgilerini doldurma
        txt_rzr_hotelname.setText(selectedHotel.getHotel_name());
        txt_rzr_hoteladres.setText(selectedHotel.getHotel_adress());
        txt_rzr_hoteltel.setText(selectedHotel.getHotel_mpno());
        txt_rzr_hoteltype.setText(selectedHotel.getFacility().toString());

        // Oda bilgilerini doldurma
        txt_rzr_roomtype.setText(selectedRoom.getRoomtype().name());
        txt_rzr_season.setText(selectedRoom.getRoom_season_type().toString());
        txt_rzr_pensiontype.setText(selectedRoom.getRoom_pension_type().name());
        txt_rzr_adultprice.setText(String.valueOf(selectedRoom.getPrice_adult()));
        txt_rzr_pricechild.setText(String.valueOf(selectedRoom.getPrice_child()));

        // Oda özelliklerini string olarak hazırlama
        StringBuilder roomFeatures = new StringBuilder();

        if (selectedRoom.getRoom_tv()) {
            roomFeatures.append("TV, ");
        }
        if (selectedRoom.getRoom_minibar()) {
            roomFeatures.append("Minibar, ");
        }
        if (selectedRoom.getRoom_gameconsole()) {
            roomFeatures.append("Console, ");
        }
        if (selectedRoom.getRoom_safe()) {
            roomFeatures.append("Safe, ");
        }
        if (selectedRoom.getRoom_projector()) {
            roomFeatures.append("Projector, ");
        }

        // Oda özelliklerinin sonundaki virgülü kaldırma
        if (roomFeatures.length() > 0) {
            roomFeatures.setLength(roomFeatures.length() - 2);
        }

        // Oda özelliklerini fld_reservation_room_type textfield'ına ekleme
        txt_rzr_roomözellik.setText(" (" + roomFeatures.toString() + ")");

        // Tarih farkını hesaplama ve gün bilgisini doldurma
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        txt_rzr_day.setText(String.valueOf(daysBetween));


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
                    Helper.showMsg("error");
                }
            }

        });


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
                        Helper.showMsg("fill");
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
                        Helper.showMsg("Lütfen geçerli bir gün sayısı giriniz.");
                        return;
                    }

                    Reservation reservation = new Reservation();
                    reservation.setReservation_room_id(selectedRoom.getRoom_id());
                    reservation.setReservation_customer_name(customerName);
                    reservation.setReservation_customer_contact(customerTel);
                    reservation.setReservation_check_in_date(java.sql.Date.valueOf(startDate));
                    reservation.setReservation_check_out_date(java.sql.Date.valueOf(endDate));
                    reservation.setReservation_total_price(totalPrice);
                    reservation.setReservation_guest_count_adult(adultCount);
                    reservation.setReservation_guest_count_child(childCount);
                    reservation.setReservation_customer_email(customerEmail);
                    reservation.setReservation_customer_tc(customerTc);
                    reservation.setReservation_customer_note(customerNote);

                    // Rezervasyonu kaydetme işlemi (Gerçek kaydetme işlemi)
                    int reservationId = reservationDao.saveReservation(reservation); // Örnek olarak rezervasyonu kaydediyoruz ve ID'sini alıyoruz

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

                    // Oda stok sayısını güncelle
                    int newStock = selectedRoom.getRoom_stock() - 1;
                    reservationManager.updateRoomStock(selectedRoom.getRoom_id(), newStock);


                    // Başarılı mesajı
                    Helper.showMsg("done");
                    dispose();
                } catch (IllegalStateException ex) {
                    // Oda stoğu hatası
                    Helper.showMsg("error");
                    Helper.showMsg("Oda stoğu yetersiz!");
                } catch (NumberFormatException ex) {
                    // Sayı formatı hatası
                    Helper.showMsg("error");
                    Helper.showMsg("Lütfen geçerli bir sayı formatı giriniz.");
                } catch (Exception ex) {
                    // Diğer hatalar
                    Helper.showMsg("error");
                    ex.printStackTrace(); // Hatanın konsolda detaylı görüntülenmesi için
                }
            }
        });


    }
}