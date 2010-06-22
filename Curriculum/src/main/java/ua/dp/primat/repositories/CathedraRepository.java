package ua.dp.primat.repositories;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.Cathedra;

/**
 *
 * @author EniSh
 */
public interface CathedraRepository {

    @Transactional(readOnly = true)
    List<Cathedra> getCathedras();

}
