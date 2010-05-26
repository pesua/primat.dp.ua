package ua.dp.primat.curriculum.data;

import java.util.List;

public interface StudentGroupRepository {
    void store(StudentGroup studentGroup);
    public void remove(StudentGroup studentGroup);
    List<StudentGroup> getGroups();
    long getSemesterCount(StudentGroup group);
}
