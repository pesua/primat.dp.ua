package ua.dp.primat.curriculum.data;

import java.util.List;

public interface StudentGroupRepository {
    List<StudentGroup> getGroups();
    long getSemesterCount(StudentGroup group);
}
