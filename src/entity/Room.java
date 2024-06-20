package entity;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class Room {
    private int room_id;
    private int room_hotel_id;
    private Roomtype roomtype;
    private BigDecimal price_adult;
    private BigDecimal price_child;
    private int room_stock;
    private int room_bed_count;
    private int room_square_meters;
    private boolean room_tv;
    private boolean room_minibar;
    private boolean room_gameconsole;
    private boolean room_safe;
    private boolean room_projector;
    private Seasontype room_season_type;
    private Pensiontype room_pension_type;
    private Hotel hotel;

    public enum Roomtype {
        SINGLEROOM,
        DOUBLEROOM,
        JUNIORSUITEROOM,
        SUITEROOM
    }

    public enum Seasontype {
        SUMMER("'01/06/2021 - 01/12/2021'"),
        WINTER("'01/01/2021 - 31/05/2021'");

        private final String dateRange;

        Seasontype(String dateRange) {
            this.dateRange = dateRange;
        }

        public String getDateRange() {
            return dateRange;
        }

        public static Seasontype fromDateRange(String dateRange) {
            for (Seasontype season : Seasontype.values()) {
                if (season.dateRange.equals(dateRange)) {
                    return season;
                }
            }
            throw new IllegalArgumentException("No enum constant with date range " + dateRange);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Seasontype.class.getSimpleName() + "[", "]")
                    .add("dateRange='" + dateRange + "'")
                    .toString();
        }
    }



    public enum Pensiontype {
        ULTRA_HER_SEY_DAHIL,
        HER_SEY_DAHIL,
        KAHVALTI,
        TAM_PANSIYON,
        YARIM_PANSION,
        SADECE_YATAK,
        ALKOL_HARIC_FULL_CREDIT
    }

    public Room() {
    }

    public Room(int room_id, int room_hotel_id, int room_p_type_id, Roomtype roomtype, BigDecimal price_adult,
                BigDecimal price_child, int room_stock, int room_bed_count, int room_square_meters, boolean room_tv, boolean room_minibar,
                boolean room_gameconsole, boolean room_safe, boolean room_projector,Seasontype seasontype,Pensiontype pensiontype, Hotel hotel) {
        this.room_id = room_id;
        this.room_hotel_id = room_hotel_id;
        this.roomtype = roomtype;
        this.price_adult = price_adult;
        this.price_child = price_child;
        this.room_stock = room_stock;
        this.room_bed_count = room_bed_count;
        this.room_square_meters = room_square_meters;
        this.room_tv = room_tv;
        this.room_minibar = room_minibar;
        this.room_gameconsole = room_gameconsole;
        this.room_safe = room_safe;
        this.room_projector = room_projector;
        this.room_season_type = seasontype;
        this.room_pension_type = pensiontype;
        this.hotel = hotel;
    }

    public Pensiontype getRoom_pension_type() {
        return room_pension_type;
    }

    public void setRoom_pension_type(Pensiontype room_pension_type) {
        this.room_pension_type = room_pension_type;
    }

    public Seasontype getRoom_season_type() {
        return room_season_type;
    }

    public void setRoom_season_type(Seasontype room_season_type) {
        this.room_season_type = room_season_type;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_hotel_id() {
        return room_hotel_id;
    }

    public void setRoom_hotel_id(int room_hotel_id) {
        this.room_hotel_id = room_hotel_id;
    }

    public Roomtype getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(Roomtype roomtype) {
        this.roomtype = roomtype;
    }

    public BigDecimal getPrice_adult() {
        return price_adult;
    }

    public void setPrice_adult(BigDecimal price_adult) {
        this.price_adult = price_adult;
    }

    public BigDecimal getPrice_child() {
        return price_child;
    }

    public void setPrice_child(BigDecimal price_child) {
        this.price_child = price_child;
    }

    public int getRoom_stock() {
        return room_stock;
    }

    public void setRoom_stock(int room_stock) {
        this.room_stock = room_stock;
    }

    public int getRoom_bed_count() {
        return room_bed_count;
    }

    public void setRoom_bed_count(int room_bed_count) {
        this.room_bed_count = room_bed_count;
    }

    public int getRoom_square_meters() {
        return room_square_meters;
    }

    public void setRoom_square_meters(int room_square_meters) {
        this.room_square_meters = room_square_meters;
    }

    public boolean getRoom_tv() {
        return room_tv;
    }

    public void setRoom_tv(boolean room_tv) {
        this.room_tv = room_tv;
    }

    public boolean getRoom_minibar() {
        return room_minibar;
    }

    public void setRoom_minibar(boolean room_minibar) {
        this.room_minibar = room_minibar;
    }

    public boolean getRoom_gameconsole() {
        return room_gameconsole;
    }

    public void setRoom_gameconsole(boolean room_gameconsole) {
        this.room_gameconsole = room_gameconsole;
    }

    public boolean getRoom_safe() {
        return room_safe;
    }

    public void setRoom_safe(boolean room_safe) {
        this.room_safe = room_safe;
    }

    public boolean getRoom_projector() {
        return room_projector;
    }

    public void setRoom_projector(boolean room_projector) {
        this.room_projector = room_projector;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id=" + room_id +
                ", room_hotel_id=" + room_hotel_id +
                ", roomtype=" + roomtype +
                ", price_adult=" + price_adult +
                ", price_child=" + price_child +
                ", room_stock=" + room_stock +
                ", room_bed_count=" + room_bed_count +
                ", room_square_meters=" + room_square_meters +
                ", room_tv=" + room_tv +
                ", room_minibar=" + room_minibar +
                ", room_gameconsole=" + room_gameconsole +
                ", room_safe=" + room_safe +
                ", room_projector=" + room_projector +
                ", room_season_type=" + room_season_type +
                ", room_pension_type=" + room_pension_type +
                ", hotel=" + hotel +
                '}';
    }
}