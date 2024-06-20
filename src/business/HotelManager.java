package business;

import dao.HotelDao;
import entity.Facility;
import entity.Hotel;
import entity.Pension;

import java.util.ArrayList;

public class HotelManager {

    private final HotelDao hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDao();
    }

    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> hotels) {
        ArrayList<Object[]> hotelList = new ArrayList<>();
        for (Hotel obj : hotels) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getHotel_name();
            rowObject[i++] = obj.getHotel_adress();
            rowObject[i++] = obj.getHotel_city();
            rowObject[i++] = obj.getHotel_region();
            rowObject[i++] = obj.getHotel_mail();
            rowObject[i++] = obj.getHotel_mpno();
            rowObject[i++] = obj.getHotel_stars();

            // Facilities
            Facility facility = obj.getFacility();
            rowObject[i++] = facility != null ? facility.toString() : "N/A";

            // Pension Types
            Pension pension = obj.getPension();
            rowObject[i++] = pension != null ? pension.toString() : "N/A";

            hotelList.add(rowObject);
        }
        return hotelList;
    }

    public void addHotel(Hotel hotel) {
        this.hotelDao.insert(hotel);
    }

    public boolean updateHotel(Hotel hotel) {
        return this.hotelDao.update(hotel);
    }

    public void deleteHotel(int hotelId) {
        this.hotelDao.delete(hotelId);
    }

    public Hotel getHotelById(int hotelId) {
        return hotelDao.findById(hotelId);
    }

    public void addFacility(Facility facility) {
        this.hotelDao.insertFacility(facility);
    }

    public void addPension(Pension pension) {
        this.hotelDao.insertPension(pension);
    }
}

