/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.data;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface RoomRepository {

    void delete(Room room);

    @SuppressWarnings(value = "unchecked")
    List<Room> getRooms();

    void store(Room room);

}
