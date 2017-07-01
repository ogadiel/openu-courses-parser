/**
 * Created by orenga on 7/1/17.
 */
public class Course {

    String number;
    String name;
    String faculty;
    String department;

    public Course(CourseRaw rawCourse, String faculty, String department) {
        this.number = rawCourse.courseNumber;
        this.name = rawCourse.courseName;
        this.faculty = faculty;
        this.department = department;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "Course{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", faculty='" + faculty + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
