package ua.dp.primat.curriculum.data;

import java.util.List;

public interface StudentGroupRepository {
    void store(StudentGroup studentGroup);
    void remove(StudentGroup studentGroup);
    List<StudentGroup> getGroups();
}
