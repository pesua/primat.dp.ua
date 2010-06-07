package ua.dp.primat.curriculum.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoomRepositoryImpl implements RoomRepository {

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    public void store(Room room) {
        if (em.contains(em)) {
            em.merge(em);
        } else {
            em.persist(em);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Room> getRooms() {
        return em.createNamedQuery(Room.GET_ALL_ROOMS_QUERY).getResultList();
    }

    public void delete(Room room) {
        if (em.contains(em)) {
            em.remove(em);
        }
    }
}
