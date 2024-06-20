package business;

import dao.RoomDao;
import entity.Hotel;
import entity.Room;

import java.util.ArrayList;

public class RoomManager {
    private final RoomDao roomDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
    }

    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    public ArrayList<Object[]> getForTableRoom(int size, ArrayList<Room> rooms) {
        ArrayList<Object[]> roomList = new ArrayList<>();
        for (Room obj : rooms) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getRoom_id();
            rowObject[i++] = obj.getRoom_hotel_id();
            rowObject[i++] = obj.getRoom_season_id();
            rowObject[i++] = obj.getRoom_p_type_id();
            rowObject[i++] = obj.getRoomtype();
            rowObject[i++] = obj.getPrice_adult();
            rowObject[i++] = obj.getPrice_child();
            rowObject[i++] = obj.getRoom_stock();
            rowObject[i++] = obj.getRoom_bed_count();
            rowObject[i++] = obj.getRoom_square_meters();
            rowObject[i++] = obj.getRoom_tv();
            rowObject[i++] = obj.getRoom_minibar();
            rowObject[i++] = obj.getRoom_gameconsole();
            rowObject[i++] = obj.getRoom_safe();
            rowObject[i++] = obj.getRoom_projector();
            roomList.add(rowObject);

        }
        return roomList;
    }


}
