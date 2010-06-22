package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.Cathedra;

/**
 *
 * @author EniSh
 */
@Repository
public class CathedraRepositoryImpl implements CathedraRepository {
    @Transactional(readOnly=true)
    public List<Cathedra> getCathedras() {
        return em.createQuery("from Cathedra").getResultList();
    }

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;
}
