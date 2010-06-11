package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.Room;

@Repository("roomRepository")
@Transactional
public class RoomRepositoryImpl implements RoomRepository {

    public void store(Room room) {
        if (em.contains(room) || (room.getId() != null)) {
            em.merge(room);
        } else {
            em.persist(room);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Room> getRooms() {
        return em.createNamedQuery(Room.GET_ALL_ROOMS_QUERY).getResultList();
    }

    public void delete(Room room) {
        //load(room.id);
        Room r = em.find(Room.class, room.getId());
        if (em.contains(r)) {
            em.remove(r);
        }
    }

    public Room load(Long id){
        return em.find(Room.class, id);
    }

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;
}
