package business;

import dao.RoomDao;
import entity.Hotel;
import entity.Room;

import java.util.ArrayList;

public class RoomManager {
    // RoomDao nesnesi, veri erişim işlemlerini gerçekleştirmek için kullanılır.
    private final RoomDao roomDao;

    // RoomManager sınıfının yapıcı metodu. RoomDao nesnesi örneklenir.
    public RoomManager() {
        this.roomDao = new RoomDao();
    }

    // Tüm odaları döner.
    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    // Yeni oda ekler.
    public boolean addRoom(Room room) {
        return roomDao.addRoom(room);
    }

    // Belirtilen oda ID'sine göre odayı döner.
    public Room getRoomById(int roomId) {
        return roomDao.findById(roomId);
    }

    // Oda bilgilerini günceller.
    public boolean updateRoom(Room room) {
        return roomDao.update(room);
    }

    // Belirtilen oda ID'sine sahip odayı siler.
    public void deleteRoom(int roomId) {
        roomDao.delete(roomId);
    }

    // Belirtilen otel ID'sine göre otelin bulunduğu şehirleri döner.
    public ArrayList<String> getHotelCities(int hotelId) {
        return roomDao.getHotelCities(hotelId);
    }

    // Belirtilen kriterlere göre odaları arar.
    public ArrayList<Room> searchRooms(String startDate, String endDate, String city, String hotelName) {
        return roomDao.searchRooms(startDate, endDate, city, hotelName);
    }

    // Odaları tablo formatında döner.
    // size: satır boyutunu belirtir.
    // rooms: odaların listesini içerir.
    public ArrayList<Object[]> getForTableRoom(int size, ArrayList<Room> rooms) {
        ArrayList<Object[]> roomList = new ArrayList<>();
        for (Room obj : rooms) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getRoom_id();  // Oda ID
            rowObject[i++] = obj.getRoom_hotel_id();  // Oda Otel ID
            rowObject[i++] = obj.getRoomtype();  // Oda Tipi
            rowObject[i++] = obj.getPrice_adult();  // Yetişkin Fiyatı
            rowObject[i++] = obj.getPrice_child();  // Çocuk Fiyatı
            rowObject[i++] = obj.getRoom_stock();  // Oda Stoku
            rowObject[i++] = obj.getRoom_bed_count();  // Oda Yatak Sayısı
            rowObject[i++] = obj.getRoom_square_meters();  // Oda Metrekare
            rowObject[i++] = obj.getRoom_tv();  // TV Var mı?
            rowObject[i++] = obj.getRoom_minibar();  // Minibar Var mı?
            rowObject[i++] = obj.getRoom_gameconsole();  // Oyun Konsolu Var mı?
            rowObject[i++] = obj.getRoom_safe();  // Kasa Var mı?
            rowObject[i++] = obj.getRoom_projector();  // Projeksiyon Var mı?
            rowObject[i++] = obj.getRoom_season_type();  // Dönem Tipi
            rowObject[i++] = obj.getRoom_pension_type();  // Pansiyon Tipi
            // Satır nesnesini listeye ekle
            roomList.add(rowObject);
        }
        return roomList;
    }

    // Tüm sezonları döner.
    public ArrayList<Room.Seasontype> getAllSeasons() {
        return roomDao.getAllSeasons();
    }

    // Tüm otelleri döner.
    public ArrayList<Hotel> getAllHotels() {
        return roomDao.getAllHotels();
    }
}