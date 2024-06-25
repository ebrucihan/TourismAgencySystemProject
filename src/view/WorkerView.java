package view;

import business.HotelManager;
import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WorkerView extends Layout {

    private User user;
    private JPanel container;
    private JTabbedPane tab_menu;
    private JScrollPane scrl_hotel;
    private JTable tbl_hotel;
    private JTextField txt_hotel_name;
    private JTextField txt_hotel_adres;
    private JTextField txt_hotel_city;
    private JTextField txt_hotel_mail;
    private JTextField txt_hotel_region;
    private JTextField txt_hotel_mpno;
    private JTextField txt_hotel_star;
    private JButton btn_exit;
    private JButton btn_delete;
    private JButton btn_update;
    private JButton btn_add;
    private JButton btn_reset;
    private JCheckBox chk_tp;
    private JCheckBox chk_yp;
    private JCheckBox chk_just_bed;
    private JCheckBox chk_ahfc;
    private JCheckBox chk_hsd;
    private JCheckBox chk_breakfast;
    private JCheckBox chk_facility_park;
    private JCheckBox chk_facility_wifi;
    private JCheckBox chk_facility_pool;
    private JCheckBox chk_facility_gym;
    private JCheckBox chk_facility_hc;
    private JCheckBox chk_facility_spa;
    private JCheckBox chk_facility_room_service;
    private JCheckBox chk_uhsd;
    private JScrollPane scrl_room;
    private JTable tbl_room;
    private JTextField txt_room_stock;
    private JTextField txt_room_price_adult;
    private JTextField txt_room_price_child;
    private JTextField txt_room_bed_count;
    private JTextField txt_room_metre;
    private JCheckBox chk_room_tv_yes;
    private JCheckBox chk_room_tv_no;
    private JCheckBox chk_room_minibar_yes;
    private JCheckBox chk_room_minibar_no;
    private JCheckBox chk_room_game_yes;
    private JCheckBox chk_room_game_no;
    private JCheckBox chk_room_safe_yes;
    private JCheckBox chk_room_safe_no;
    private JCheckBox chk_room_projeks_yes;
    private JCheckBox chk_room_projeks_no;
    private JComboBox<Room.Roomtype> cmb_room_type;
    private JButton btn_room_reset;
    private JButton btn_room_update;
    private JButton btn_room_delete;
    private JButton btn_room_add;
    private JFormattedTextField room_strt_date;
    private JFormattedTextField rıoom_fnsh_date;
    private JButton btn_room_search;
    private JComboBox<Room.Seasontype> cmb_season_type;
    private JComboBox<Hotel> cmb_room_hoteladd;
    private JComboBox<Room.Pensiontype> cmb_room_pension_type;
    private JComboBox cmb_search_hotel_name;
    private JComboBox cmb_search_city;
    private JButton btn_search_reset;
    private JTable tbl_reservation;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private DefaultTableModel tmdl_reservation = new DefaultTableModel();
    private Object[] col_hotel;
    private Object[] col_room;
    private Object[] col_reservation;
    private JPopupMenu hotel_menu;
    private JPopupMenu room_menu;
    private JPopupMenu reservation_menu;
    private HotelManager hotelManager;
    private RoomManager roomManager;
    private ReservationManager reservationManager;
    private Pension pension;
    private Facility facility;
    private int facilityType;
    private int hotelPensionType;


    public WorkerView(User user) {
        this.user = user;
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        this.reservationManager = new ReservationManager();
        this.add(container);
        this.guiInitilaze(1200, 720);
        this.setTitle("Turizm Acentesi");

        loadCompenent();
        loadHotelTable(null);
        // Add ListSelectionListener to the table
        tableHotelRowSelected(tbl_hotel, this::loadHotelCompenent);

        //Room
        loadRoomCompenent();
        initializeComboBoxes();
        loadRoomButton();
        loadRoomTable(null);
        tableHotelRowSelected(tbl_room, this::loadRoomCompenent);
        resetRoom();
        roomSearchReset();

        //Rezervasyon
        loadReservationComponent();
        loadReservationRightClick();
        loadReservationTable(null);

        this.tbl_hotel.setComponentPopupMenu(hotel_menu);
        this.tbl_room.setComponentPopupMenu(room_menu);
        this.tbl_reservation.setComponentPopupMenu(reservation_menu);


    }


    public void loadHotelCompenent() {
        int selectedRow = tbl_hotel.getSelectedRow();
        if (selectedRow != -1) {
            txt_hotel_name.setText(tbl_hotel.getValueAt(selectedRow, 1).toString());
            txt_hotel_adres.setText(tbl_hotel.getValueAt(selectedRow, 2).toString());
            txt_hotel_city.setText(tbl_hotel.getValueAt(selectedRow, 3).toString());
            txt_hotel_region.setText(tbl_hotel.getValueAt(selectedRow, 4).toString());
            txt_hotel_mail.setText(tbl_hotel.getValueAt(selectedRow, 5).toString());
            txt_hotel_mpno.setText(tbl_hotel.getValueAt(selectedRow, 6).toString());
            txt_hotel_star.setText(tbl_hotel.getValueAt(selectedRow, 7).toString());

            // İlgili otelin ID'sini alarak özellikleri ve pansiyonu yükleyin
            int hotelId = Integer.parseInt(tbl_hotel.getValueAt(selectedRow, 0).toString());
            loadFacilityAndPension(hotelId);
        }
    }

    public void loadRoomCompenent() {
        int selectedRow = tbl_room.getSelectedRow();
        if (selectedRow != -1) {
            // Room type
            String roomTypeStr = tbl_room.getValueAt(selectedRow, 2).toString().toUpperCase().replaceAll(" ", "");
            Room.Roomtype roomType = Room.Roomtype.valueOf(roomTypeStr);
            cmb_room_type.setSelectedItem(roomType);

            // Prices and other fields
            txt_room_price_adult.setText(tbl_room.getValueAt(selectedRow, 3).toString());
            txt_room_price_child.setText(tbl_room.getValueAt(selectedRow, 4).toString());
            txt_room_stock.setText(tbl_room.getValueAt(selectedRow, 5).toString());
            txt_room_bed_count.setText(tbl_room.getValueAt(selectedRow, 6).toString());
            txt_room_metre.setText(tbl_room.getValueAt(selectedRow, 7).toString());

            // Checkbox values
            chk_room_tv_yes.setSelected((Boolean) tbl_room.getValueAt(selectedRow, 8));
            chk_room_tv_no.setSelected(!(Boolean) tbl_room.getValueAt(selectedRow, 8));

            chk_room_minibar_yes.setSelected((Boolean) tbl_room.getValueAt(selectedRow, 9));
            chk_room_minibar_no.setSelected(!(Boolean) tbl_room.getValueAt(selectedRow, 9));

            chk_room_game_yes.setSelected((Boolean) tbl_room.getValueAt(selectedRow, 10));
            chk_room_game_no.setSelected(!(Boolean) tbl_room.getValueAt(selectedRow, 10));

            chk_room_safe_yes.setSelected((Boolean) tbl_room.getValueAt(selectedRow, 11));
            chk_room_safe_no.setSelected(!(Boolean) tbl_room.getValueAt(selectedRow, 11));

            chk_room_projeks_yes.setSelected((Boolean) tbl_room.getValueAt(selectedRow, 12));
            chk_room_projeks_no.setSelected(!(Boolean) tbl_room.getValueAt(selectedRow, 12));


            int hotelId = (int) tbl_room.getValueAt(selectedRow, 1);
            for (int i = 0; i < cmb_room_hoteladd.getItemCount(); i++) {
                Hotel hotel = (Hotel) cmb_room_hoteladd.getItemAt(i);
                if (hotel.getHotel_id() == hotelId) {
                    cmb_room_hoteladd.setSelectedItem(hotel);
                    break;
                }
            }

            // Season type
            cmb_season_type.removeAllItems();
            ArrayList<Room.Seasontype> seasons = roomManager.getAllSeasons();
            for (Room.Seasontype season : seasons) {
                cmb_season_type.addItem(season);
            }

            // Pension type
            String pensionTypeStr = tbl_room.getValueAt(selectedRow, 14).toString().toUpperCase().replaceAll(" ", "");
            Room.Pensiontype pensionType = Room.Pensiontype.valueOf(pensionTypeStr);
            cmb_room_pension_type.setSelectedItem(pensionType);


            // Set room ID
            int roomId = Integer.parseInt(tbl_room.getValueAt(selectedRow, 0).toString());


        }

        // Checkbox action listeners
        chk_room_tv_yes.addActionListener(e -> {
            if (chk_room_tv_yes.isSelected()) {
                chk_room_tv_no.setSelected(false);
            }
        });

        chk_room_tv_no.addActionListener(e -> {
            if (chk_room_tv_no.isSelected()) {
                chk_room_tv_yes.setSelected(false);
            }
        });

        chk_room_minibar_yes.addActionListener(e -> {
            if (chk_room_minibar_yes.isSelected()) {
                chk_room_minibar_no.setSelected(false);
            }
        });

        chk_room_minibar_no.addActionListener(e -> {
            if (chk_room_minibar_no.isSelected()) {
                chk_room_minibar_yes.setSelected(false);
            }
        });

        chk_room_game_yes.addActionListener(e -> {
            if (chk_room_game_yes.isSelected()) {
                chk_room_game_no.setSelected(false);
            }
        });

        chk_room_game_no.addActionListener(e -> {
            if (chk_room_game_no.isSelected()) {
                chk_room_game_yes.setSelected(false);
            }
        });

        chk_room_safe_yes.addActionListener(e -> {
            if (chk_room_safe_yes.isSelected()) {
                chk_room_safe_no.setSelected(false);
            }
        });

        chk_room_safe_no.addActionListener(e -> {
            if (chk_room_safe_no.isSelected()) {
                chk_room_safe_yes.setSelected(false);
            }
        });

        chk_room_projeks_yes.addActionListener(e -> {
            if (chk_room_projeks_yes.isSelected()) {
                chk_room_projeks_no.setSelected(false);
            }


        });

        chk_room_projeks_no.addActionListener(e -> {
            if (chk_room_projeks_no.isSelected()) {
                chk_room_projeks_yes.setSelected(false);
            }
        });


    }

    private void initializeComboBoxes() {
        cmb_room_hoteladd.removeAllItems();
        ArrayList<Hotel> hotels = roomManager.getAllHotels();
        for (Hotel hotel : hotels) {
            cmb_room_hoteladd.addItem(hotel);
            cmb_search_hotel_name.addItem(hotel);
        }

        cmb_search_hotel_name.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Hotel selectedHotel = (Hotel) cmb_search_hotel_name.getSelectedItem();
                    if (selectedHotel != null) {
                        initializeHotelCityComboBox(selectedHotel.getHotel_id());
                    }
                }
            }
        });


        // Room Type ComboBox
        for (Room.Roomtype roomType : Room.Roomtype.values()) {
            cmb_room_type.addItem(roomType);
        }

        // Season Type ComboBox
        for (Room.Seasontype seasonType : Room.Seasontype.values()) {
            cmb_season_type.addItem(seasonType);
        }

        // Pension Type ComboBox
        for (Room.Pensiontype pensionType : Room.Pensiontype.values()) {
            cmb_room_pension_type.addItem(pensionType);
        }


    }

    private void initializeHotelCityComboBox(int hotelId) {
        cmb_search_city.removeAllItems();
        ArrayList<String> cities = roomManager.getHotelCities(hotelId);
        for (String city : cities) {
            cmb_search_city.addItem(city);
        }
    }

    public void loadReservationRightClick() {
        this.reservation_menu = new JPopupMenu(); // Burada reservation_menu nesnesini oluşturuyoruz.

        this.tbl_reservation.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_reservation.rowAtPoint(e.getPoint());
                tbl_reservation.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    reservation_menu.show(tbl_reservation, e.getX(), e.getY());
                }
            }
        });

        this.reservation_menu.add("Rezervasyon Güncelle").addActionListener(e -> {
            int selectReservationId = getTableSelectedRow(tbl_reservation, 0);
            Reservation selectedReservation = reservationManager.getReservationById(selectReservationId);

            if (selectedReservation == null) {
                Helper.showMsg("error");
                return;
            }

            Room selectedRoom = roomManager.getRoomById(selectedReservation.getReservation_room_id());
            if (selectedRoom == null) {
                Helper.showMsg("error");
                return;
            }

            Hotel selectedHotel = hotelManager.getHotelById(selectedRoom.getRoom_hotel_id());
            if (selectedHotel == null) {
                Helper.showMsg("error");
                return;
            }

            Date checkInDate = selectedReservation.getReservation_check_in_date();
            Date checkOutDate = selectedReservation.getReservation_check_out_date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            LocalDate startDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

            calendar.setTime(checkOutDate);
            LocalDate endDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

            ReservationView reservationView = new ReservationView(selectedHotel, selectedRoom, startDate, endDate,selectedReservation);
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable(null);
                }
            });
            reservationView.setVisible(true);
        });

        this.reservation_menu.add("Rezervasyon Sil").addActionListener(e -> {
            int selectReservationId = getTableSelectedRow(tbl_reservation, 0);
            Reservation selectedReservation = reservationManager.getReservationById(selectReservationId);

            if (selectedReservation == null) {
                Helper.showMsg("error");
                return;
            }

            Room selectedRoom = roomManager.getRoomById(selectedReservation.getReservation_room_id());
            if (selectedRoom == null) {
                Helper.showMsg("error");
                return;
            }

            if (Helper.confirm("sure")) {
                reservationManager.deleteReservation(selectReservationId);

                // Oda stok sayısını güncelle
                int newStock = selectedRoom.getRoom_stock() + 1;
                reservationManager.updateRoomStock(selectedRoom.getRoom_id(), newStock);

                loadReservationTable(null);
                loadRoomTable(null);
            }
        });

        this.tbl_reservation.setComponentPopupMenu(reservation_menu);
        tableRowSelected(this.tbl_reservation, reservation_menu);
    }

    private void tableRowSelected(JTable tblReservation, JPopupMenu reservationMenu) {
        // Implement this method if needed
    }

    private LocalDate convertToLocalDateViaSqlDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }



    private void loadRoomButton() {
        this.btn_room_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("Emin misiniz?")) {
                    Room.Seasontype room_season = (Room.Seasontype) cmb_season_type.getSelectedItem();
                    Room.Roomtype room_type = (Room.Roomtype) cmb_room_type.getSelectedItem();
                    Room.Pensiontype room_pension = (Room.Pensiontype) cmb_room_pension_type.getSelectedItem();

                    double room_price_adult = Double.parseDouble(txt_room_price_adult.getText());
                    double room_price_child = Double.parseDouble(txt_room_price_child.getText());
                    int room_stock = Integer.parseInt(txt_room_stock.getText());
                    int room_bed_count = Integer.parseInt(txt_room_bed_count.getText());
                    int room_size = Integer.parseInt(txt_room_metre.getText());
                    boolean room_has_tv = chk_room_tv_yes.isSelected();
                    boolean room_has_minibar = chk_room_minibar_yes.isSelected();
                    boolean room_has_console = chk_room_game_yes.isSelected();
                    boolean room_has_safe = chk_room_safe_yes.isSelected();
                    boolean room_has_projector = chk_room_projeks_yes.isSelected();

                    Hotel selectedHotel = (Hotel) cmb_room_hoteladd.getSelectedItem();
                    int hotel_id = selectedHotel.getHotel_id();

                    Room room = new Room(0, hotel_id, room_type, room_season, room_pension,
                            room_price_adult, room_price_child, room_stock, room_bed_count,
                            room_size, room_has_tv, room_has_minibar, room_has_console,
                            room_has_safe, room_has_projector);

                    boolean success = roomManager.addRoom(room);

                    if (success) {
                        loadRoomTable(null);
                        Helper.showMsg("done");
                    } else {
                        Helper.showMsg("error");
                    }

                }
            }
        });
        btn_room_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetRoom();
            }
        });
        btn_room_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbl_room.getSelectedRow();
                if (selectedRow != -1) {
                    int roomId = Integer.parseInt(tbl_room.getValueAt(selectedRow, 0).toString());
                    Room room = roomManager.getRoomById(roomId);
                    room.setRoom_season_type((Room.Seasontype) cmb_season_type.getSelectedItem());
                    room.setRoomtype((Room.Roomtype) cmb_room_type.getSelectedItem());
                    room.setRoom_pension_type((Room.Pensiontype) cmb_room_pension_type.getSelectedItem());
                    room.setPrice_adult(Double.parseDouble(txt_room_price_adult.getText()));
                    room.setPrice_child(Double.parseDouble(txt_room_price_child.getText()));
                    room.setRoom_stock(Integer.parseInt(txt_room_stock.getText()));
                    room.setRoom_bed_count(Integer.parseInt(txt_room_bed_count.getText()));
                    room.setRoom_square_meters(Integer.parseInt(txt_room_metre.getText()));
                    room.setRoom_tv(chk_room_tv_yes.isSelected());
                    room.setRoom_minibar(chk_room_minibar_yes.isSelected());
                    room.setRoom_gameconsole(chk_room_game_yes.isSelected());
                    room.setRoom_safe(chk_room_safe_yes.isSelected());
                    room.setRoom_projector(chk_room_projeks_yes.isSelected());

                    Hotel selectedHotel = (Hotel) cmb_room_hoteladd.getSelectedItem();
                    int hotel_id = selectedHotel.getHotel_id();
                    room.setRoom_hotel_id(hotel_id);

                    boolean success = roomManager.updateRoom(room);

                    if (success) {
                        loadRoomTable(null);
                        Helper.showMsg("done");
                    } else {
                        Helper.showMsg("error");
                    }
                }


            }
        });
        btn_room_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbl_room.getSelectedRow();
                if (selectedRow != -1 && Helper.confirm("Emin misiniz?")) {
                    int roomId = (int) tbl_room.getValueAt(selectedRow, 0);
                    roomManager.deleteRoom(roomId);
                    loadRoomTable(null);
                    Helper.showMsg("done");
                }

            }


        });


        btn_room_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = room_strt_date.getText();
                String endDate = rıoom_fnsh_date.getText();

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate startLocalDate = LocalDate.parse(startDate, inputFormatter);
                    LocalDate endLocalDate = LocalDate.parse(endDate, inputFormatter);

                    String formattedStartDate = startLocalDate.format(outputFormatter);
                    String formattedEndDate = endLocalDate.format(outputFormatter);

                    // cmb_room_search_otel_name combobox'ından seçilen Hotel nesnesini al
                    Hotel selectedHotel = (Hotel) cmb_search_hotel_name.getSelectedItem();
                    String hotelName = selectedHotel != null ? selectedHotel.getHotel_name() : null;

                    // Şehir combobox'ından seçilen şehir adını al
                    String city = (String) cmb_search_city.getSelectedItem();

                    ArrayList<Room> rooms = roomManager.searchRooms(formattedStartDate, formattedEndDate, city, hotelName);
                    ArrayList<Object[]> roomData = roomManager.getForTableRoom(col_room.length, rooms);
                    loadRoomTable(roomData);
                } catch (DateTimeParseException dtpe) {
                    dtpe.printStackTrace();
                    Helper.showMsg("error");

                }
            }
        });

        btn_search_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roomSearchReset();
            }
        });


        btn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFormFields();

            }

            private void resetFormFields() {
                // Text alanlarını temizle
                txt_hotel_name.setText("");
                txt_hotel_adres.setText("");
                txt_hotel_city.setText("");
                txt_hotel_mail.setText("");
                txt_hotel_region.setText("");
                txt_hotel_mpno.setText("");
                txt_hotel_star.setText("");

                resetPensionCheckboxes();

                // Tesis özelliklerini temizle
                chk_facility_park.setSelected(false);
                chk_facility_wifi.setSelected(false);
                chk_facility_pool.setSelected(false);
                chk_facility_gym.setSelected(false);
                chk_facility_hc.setSelected(false);
                chk_facility_spa.setSelected(false);
                chk_facility_room_service.setSelected(false);

                loadHotelTable(null);
            }

            private void resetPensionCheckboxes() {
                chk_tp.setSelected(false);
                chk_yp.setSelected(false);
                chk_just_bed.setSelected(false);
                chk_ahfc.setSelected(false);
                chk_hsd.setSelected(false);
                chk_breakfast.setSelected(false);
                chk_uhsd.setSelected(false);
            }
        });


    }

    private void resetRoom() {
        txt_room_bed_count.setText("");
        txt_room_metre.setText("");
        txt_room_price_adult.setText("");
        txt_room_price_child.setText("");
        txt_room_stock.setText("");
        chk_room_tv_yes.setSelected(false);
        chk_room_tv_no.setSelected(false);
        chk_room_minibar_yes.setSelected(false);
        chk_room_minibar_no.setSelected(false);
        chk_room_game_yes.setSelected(false);
        chk_room_game_no.setSelected(false);
        chk_room_safe_yes.setSelected(false);
        chk_room_safe_no.setSelected(false);
        chk_room_projeks_yes.setSelected(false);
        chk_room_projeks_no.setSelected(false);
        cmb_room_type.setSelectedItem(null);
        cmb_season_type.setSelectedItem(null);
        cmb_room_hoteladd.setSelectedItem(null);
        cmb_room_pension_type.setSelectedItem(null);

    }

    private void roomSearchReset() {
        cmb_search_hotel_name.setSelectedItem(null);
        cmb_search_city.setSelectedItem(null);
        loadRoomTable(null);

    }

    private void loadReservationComponent() {
        this.tbl_room.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_room.rowAtPoint(e.getPoint());
                tbl_room.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    room_menu.show(tbl_room, e.getX(), e.getY());
                }
            }
        });

        this.room_menu = new JPopupMenu();
        this.room_menu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectRoomId = this.getTableSelectedRow(this.tbl_room, 0);
            Room selectedRoom = roomManager.getRoomById(selectRoomId);

            Hotel selectedHotel = hotelManager.getHotelById(selectedRoom.getRoom_hotel_id());

            String startDateStr = room_strt_date.getText();
            String endDateStr = rıoom_fnsh_date.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            ReservationView reservationView = new ReservationView(selectedHotel, selectedRoom, startDate, endDate);
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                    loadReservationTable(null);
                }
            });
            reservationView.setVisible(true);
        });


    }



    private void loadFacilityAndPension(int hotelId) {
        Hotel hotel = hotelManager.getHotelById(hotelId);
        if (hotel != null) {
            // Pansiyon Tipi
            Pension pension = hotel.getPension();
            if (pension != null) {
                resetPensionCheckboxes();
                chk_tp.setSelected(pension.isPension_type_tam());
                chk_yp.setSelected(pension.isPension_type_yarim());
                chk_just_bed.setSelected(pension.isPension_type_just_bed());
                chk_ahfc.setSelected(pension.isPension_type_ahfc());
                chk_hsd.setSelected(pension.isPension_type_hsd());
                chk_breakfast.setSelected(pension.isPension_type_breakfast());
                chk_uhsd.setSelected(pension.isPension_type_ultra());
            }

            // Tesis Özellikleri
            Facility facility = hotel.getFacility();
            if (facility != null) {
                chk_facility_park.setSelected(facility.isFacility_free_park());
                chk_facility_wifi.setSelected(facility.isFacility_free_wifi());
                chk_facility_pool.setSelected(facility.isFacility_pool());
                chk_facility_gym.setSelected(facility.isFacility_gym());
                chk_facility_hc.setSelected(facility.isFacility_concierge());
                chk_facility_spa.setSelected(facility.isFacility_SPA());
                chk_facility_room_service.setSelected(facility.isFacility_room_service());
            }
        }
    }

    private void resetPensionCheckboxes() {
        chk_tp.setSelected(false);
        chk_yp.setSelected(false);
        chk_just_bed.setSelected(false);
        chk_ahfc.setSelected(false);
        chk_hsd.setSelected(false);
        chk_breakfast.setSelected(false);
        chk_uhsd.setSelected(false);
    }


    public void loadRoomTable(ArrayList<Object[]> roomList){
        this.col_room = new Object[]{"Room ID", "Room Hotel ID", "Room Type", "Price Adult", "Price Child", "Room Stock", "Room Bed Count", "Room Square Meters", "Room Tv", "Room Minibar", "Room Gameconsole", "Room Safe", "Room Projector","Room Season Type","Room Pension Type"};
        if (roomList == null) {
            roomList = this.roomManager.getForTableRoom(this.col_room.length,this.roomManager.findAll());
        }
        this.createTable(this.tmdl_room,this.tbl_room,col_room,roomList);
    }




    public void loadHotelTable(ArrayList<Object[]> hotelList) {
        this.col_hotel = new Object[]{"Hotel ID", "Hotel Name", "Address", "City", "Region", "Email", "Phone", "Stars", "Facilities", "Pension Types"};
        if (hotelList == null) {
            hotelList = this.hotelManager.getForTable(this.col_hotel.length, this.hotelManager.findAll());
        }

        this.createTable(this.tmdl_hotel, this.tbl_hotel, col_hotel, hotelList);
    }


    public void loadReservationTable(ArrayList<Object[]> reservationList) {
        this.col_reservation = new Object[]{"Reservation ID", "Reservation Room ID", "Reservation Customer Name", "Reservation Customer Contact", "Reservation Check In Date",
                "Reservation Check Out Date", "Reservation Total Price", "Reservation Adult Count", "Reservation Child Count", "Reservation Customer Email", "Reservation Customer T.C.", "Reservation Customer Note"};

        if (reservationList == null) {
            reservationList = this.reservationManager.getForTable(this.col_reservation.length, this.reservationManager.findAll());
        }

        this.createTable(this.tmdl_reservation, this.tbl_reservation, col_reservation, reservationList);
    }

    private void loadCompenent() {
        this.btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")) {
                    dispose();
                    new LoginView().setVisible(true);
                }
            }
        });

        this.btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("Emin misiniz?")) {

                    String hotelName = txt_hotel_name.getText();
                    String hotelAddress = txt_hotel_adres.getText();
                    String hotelCity = txt_hotel_city.getText();
                    String hotelMail = txt_hotel_mail.getText();
                    String hotelRegion = txt_hotel_region.getText();
                    String hotelMpno = txt_hotel_mpno.getText();
                    String hotelStar = txt_hotel_star.getText();
                    Facility facility = getSelectedFacilityType();
                    Pension pension = getSelectedPensionType();

                    Hotel newHotel = new Hotel(0, hotelName, hotelAddress, hotelCity, hotelRegion,
                            hotelMail, hotelMpno, hotelStar, facility.getFacility_id(), pension.getPension_type_id(),
                            facility, pension);

                    hotelManager.addHotel(newHotel);

                    loadHotelTable(null);

                    cmb_room_hoteladd.removeAllItems();
                    ArrayList<Hotel> hotels = roomManager.getAllHotels();
                    for (Hotel hotel : hotels) {
                        cmb_room_hoteladd.addItem(hotel);
                        cmb_search_hotel_name.addItem(hotel);
                    }
                    cmb_search_hotel_name.removeAllItems();

                    cmb_search_hotel_name.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                Hotel selectedHotel = (Hotel) cmb_search_hotel_name.getSelectedItem();
                                if (selectedHotel != null) {
                                    initializeHotelCityComboBox(selectedHotel.getHotel_id());
                                }
                            }
                        }
                    });


                    Helper.showMsg("done");
                }
            }
        });

        this.btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbl_hotel.getSelectedRow();
                if (selectedRow != -1 && Helper.confirm("Emin misiniz?")) {
                    int hotelId = (int) tbl_hotel.getValueAt(selectedRow, 0);
                    hotelManager.deleteHotel(hotelId);
                    loadHotelTable(null);
                    Helper.showMsg("done");
                }
            }
        });

        this.btn_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbl_hotel.getSelectedRow();
                if (selectedRow != -1) {
                    Hotel hotel = new Hotel();
                    hotel.setHotel_id(Integer.parseInt(tbl_hotel.getValueAt(selectedRow, 0).toString()));
                    hotel.setHotel_name(txt_hotel_name.getText());
                    hotel.setHotel_adress(txt_hotel_adres.getText());
                    hotel.setHotel_city(txt_hotel_city.getText());
                    hotel.setHotel_region(txt_hotel_region.getText());
                    hotel.setHotel_mail(txt_hotel_mail.getText());
                    hotel.setHotel_mpno(txt_hotel_mpno.getText());
                    hotel.setHotel_stars(txt_hotel_star.getText());
                    hotel.setFacility(getSelectedFacilityType());
                    hotel.setPension(getSelectedPensionType());

                    boolean success = hotelManager.updateHotel(hotel);
                    if (success) {
                        Helper.showMsg("done");
                        loadHotelTable(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });

    }

    private Facility getSelectedFacilityType() {
        Facility facility = new Facility();
        facility.setFacility_free_park(chk_facility_park.isSelected());
        facility.setFacility_free_wifi(chk_facility_wifi.isSelected());
        facility.setFacility_pool(chk_facility_pool.isSelected());
        facility.setFacility_gym(chk_facility_gym.isSelected());
        facility.setFacility_concierge(chk_facility_hc.isSelected());
        facility.setFacility_SPA(chk_facility_spa.isSelected());
        facility.setFacility_room_service(chk_facility_room_service.isSelected());
        hotelManager.addFacility(facility);
        return facility;
    }

    private Pension getSelectedPensionType() {
        Pension pension = new Pension();
        pension.setPension_type_ultra(chk_uhsd.isSelected());
        pension.setPension_type_hsd(chk_hsd.isSelected());
        pension.setPension_type_breakfast(chk_breakfast.isSelected());
        pension.setPension_type_tam(chk_tp.isSelected());
        pension.setPension_type_yarim(chk_yp.isSelected());
        pension.setPension_type_just_bed(chk_just_bed.isSelected());
        pension.setPension_type_ahfc(chk_ahfc.isSelected());
        hotelManager.addPension(pension);
        return pension;
    }


    private void createUIComponents() throws ParseException {
        this.room_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.room_strt_date.setText("10/10/2023");
        this.rıoom_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.rıoom_fnsh_date.setText("16/10/2023");
    }


}