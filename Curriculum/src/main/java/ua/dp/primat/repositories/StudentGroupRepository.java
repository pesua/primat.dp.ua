package ua.dp.primat.repositories;

import java.util.List;
import ua.dp.primat.domain.StudentGroup;

public interface StudentGroupRepository {
    void store(StudentGroup studentGroup);
    void remove(StudentGroup studentGroup);
    List<StudentGroup> getGroups();
}
