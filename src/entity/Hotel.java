package entity;

import java.util.StringJoiner;

public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String hotel_adress;
    private String hotel_city;
    private String hotel_region;
    private String hotel_mail;
    private String hotel_mpno;
    private String hotel_stars;
    private int hotel_facility_id;
    private int hotel_pension_type_id;
    private Facility facility;
    private Pension pension;

    // Yeni otel eklemek için uygun yapıcı
    public Hotel(String hotel_name, String hotel_adress, String hotel_city, String hotel_region, String hotel_mail, String hotel_mpno, String hotel_stars, int hotel_facility_id, int hotel_pension_type_id) {
        this.hotel_name = hotel_name;
        this.hotel_adress = hotel_adress;
        this.hotel_city = hotel_city;
        this.hotel_region = hotel_region;
        this.hotel_mail = hotel_mail;
        this.hotel_mpno = hotel_mpno;
        this.hotel_stars = hotel_stars;
        this.hotel_facility_id = hotel_facility_id;
        this.hotel_pension_type_id = hotel_pension_type_id;
    }

    // Mevcut oteli güncellemek için uygun yapıcı
    public Hotel(int hotel_id, String hotel_name, String hotel_adress, String hotel_city, String hotel_region, String hotel_mail,
                 String hotel_mpno, String hotel_stars, int hotel_facility_id, int hotel_pension_type_id, Facility facility, Pension pension) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_adress = hotel_adress;
        this.hotel_city = hotel_city;
        this.hotel_region = hotel_region;
        this.hotel_mail = hotel_mail;
        this.hotel_mpno = hotel_mpno;
        this.hotel_stars = hotel_stars;
        this.hotel_facility_id = hotel_facility_id;
        this.hotel_pension_type_id = hotel_pension_type_id;
        this.facility = facility;
        this.pension = pension;
    }

    // Boş yapıcı
    public Hotel() {
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_adress() {
        return hotel_adress;
    }

    public void setHotel_adress(String hotel_adress) {
        this.hotel_adress = hotel_adress;
    }

    public String getHotel_city() {
        return hotel_city;
    }

    public void setHotel_city(String hotel_city) {
        this.hotel_city = hotel_city;
    }

    public String getHotel_region() {
        return hotel_region;
    }

    public void setHotel_region(String hotel_region) {
        this.hotel_region = hotel_region;
    }

    public String getHotel_mail() {
        return hotel_mail;
    }

    public void setHotel_mail(String hotel_mail) {
        this.hotel_mail = hotel_mail;
    }

    public String getHotel_mpno() {
        return hotel_mpno;
    }

    public void setHotel_mpno(String hotel_mpno) {
        this.hotel_mpno = hotel_mpno;
    }

    public String getHotel_stars() {
        return hotel_stars;
    }

    public void setHotel_stars(String hotel_stars) {
        this.hotel_stars = hotel_stars;
    }

    public int getHotel_facility_id() {
        return hotel_facility_id;
    }

    public void setHotel_facility_id(int hotel_facility_id) {
        this.hotel_facility_id = hotel_facility_id;
    }

    public int getHotel_pension_type_id() {
        return hotel_pension_type_id;
    }

    public void setHotel_pension_type_id(int hotel_pension_type_id) {
        this.hotel_pension_type_id = hotel_pension_type_id;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Pension getPension() {
        return pension;
    }

    public void setPension(Pension pension) {
        this.pension = pension;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Hotel.class.getSimpleName() + "[", "]")
                .add("hotel_id=" + hotel_id)
                .add("hotel_name='" + hotel_name + "'")
                .add("hotel_adress='" + hotel_adress + "'")
                .add("hotel_city='" + hotel_city + "'")
                .add("hotel_region='" + hotel_region + "'")
                .add("hotel_mail='" + hotel_mail + "'")
                .add("hotel_mpno='" + hotel_mpno + "'")
                .add("hotel_stars='" + hotel_stars + "'")
                .add("hotel_facility_id=" + hotel_facility_id)
                .add("hotel_pension_type_id=" + hotel_pension_type_id)
                .add("facility=" + facility)
                .add("pension=" + pension)
                .toString();
    }
}
