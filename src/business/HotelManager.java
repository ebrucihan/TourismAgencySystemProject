package business;

import dao.HotelDao;
import entity.Facility;
import entity.Hotel;
import entity.Pension;

import java.util.ArrayList;

public class HotelManager {

    // HotelDao nesnesi, veri erişim işlemlerini gerçekleştirmek için kullanılır.
    private final HotelDao hotelDao;

    // HotelManager sınıfının yapıcı metodu. HotelDao nesnesi örneklenir.
    public HotelManager() {
        this.hotelDao = new HotelDao();
    }

    // Tüm otelleri döner.
    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    // Otelleri tablo formatında döner.
    // size: satır boyutunu belirtir.
    // hotels: otellerin listesini içerir.
    public ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> hotels) {
        ArrayList<Object[]> hotelList = new ArrayList<>();
        for (Hotel obj : hotels) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getHotel_id();  // Otel ID
            rowObject[i++] = obj.getHotel_name();  // Otel Adı
            rowObject[i++] = obj.getHotel_adress();  // Otel Adresi
            rowObject[i++] = obj.getHotel_city();  // Otel Şehri
            rowObject[i++] = obj.getHotel_region();  // Otel Bölgesi
            rowObject[i++] = obj.getHotel_mail();  // Otel Maili
            rowObject[i++] = obj.getHotel_mpno();  // Otel Telefon Numarası
            rowObject[i++] = obj.getHotel_stars();  // Otel Yıldız Sayısı

            // Tesis Özellikleri
            Facility facility = obj.getFacility();
            rowObject[i++] = facility != null ? facility.toString() : "N/A";

            // Pansiyon Tipleri
            Pension pension = obj.getPension();
            rowObject[i++] = pension != null ? pension.toString() : "N/A";

            // Satır nesnesini listeye ekle
            hotelList.add(rowObject);
        }
        return hotelList;
    }

    // Yeni otel ekler.
    public void addHotel(Hotel hotel) {
        this.hotelDao.insert(hotel);
    }

    // Mevcut oteli günceller.
    public boolean updateHotel(Hotel hotel) {
        return this.hotelDao.update(hotel);
    }

    // Belirtilen ID'ye sahip oteli siler.
    public void deleteHotel(int hotelId) {
        this.hotelDao.delete(hotelId);
    }

    // Belirtilen ID'ye sahip oteli döner.
    public Hotel getHotelById(int hotelId) {
        return hotelDao.findById(hotelId);
    }

    // Yeni tesis özelliği ekler.
    public void addFacility(Facility facility) {
        this.hotelDao.insertFacility(facility);
    }

    // Yeni pansiyon tipi ekler.
    public void addPension(Pension pension) {
        this.hotelDao.insertPension(pension);
    }
}
