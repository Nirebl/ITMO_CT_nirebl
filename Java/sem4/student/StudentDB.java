package info.kgeorgiy.ja.tarasevich.student;

import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentQuery;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {

    public static final Comparator<Student> STUDENT_COMPARATOR = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getGroup);

    private <R> List<R> mapStudents(List<Student> students, Function<Student, R> mapper) {
        return students.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    private Stream<Student> filterStudents(Collection<Student> students, Predicate<Student> predicate) {
        return students.stream()
                .filter(predicate);
    }

    private Stream<Student> findBy(Collection<Student> students, Predicate<Student> predicate) {
        return filterStudents(students, predicate);
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return mapStudents(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return mapStudents(students, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(List<Student> students) {
        return mapStudents(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return mapStudents(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students.stream()
                .max(Comparator.naturalOrder())
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream()
                // :NOTE: naturalOrder
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Student> constantSort(Collection<Student> data, Predicate<Student> filter) {
        return data.stream()
                .filter(filter)
                .sorted(STUDENT_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        // :NOTE: extract CONST

        return constantSort(students, student -> true);
    }

    // :NOTE: unify findBy
    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findBy(students, student -> student.getFirstName().equals(name))
                .sorted(Comparator.comparing(Student::getLastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findBy(students, student -> student.getLastName().equals(name)).
                collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return filterStudents(students, student -> student.getGroup().equals(group))
                .sorted(Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getFirstName))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName,
                        (existing, replacement) -> existing.compareTo(replacement) < 0 ? existing : replacement));
    }

}
