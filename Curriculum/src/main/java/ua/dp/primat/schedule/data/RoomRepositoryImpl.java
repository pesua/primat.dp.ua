package ua.dp.primat.schedule.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("roomRepository")
@Transactional
public class RoomRepositoryImpl implements RoomRepository {

    public void store(Room room) {
        if (em.contains(room)) {
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
        if (em.contains(room)) {
            em.remove(room);
        }
    }

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;
}
