/**
 * Created by orenga on 7/1/17.
 */
public class RefHelper {

    private String faculty;
    private String department;

    public RefHelper(String faculty, String department) {
        this.faculty = faculty;
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDepartment() {
        return department;
    }
}
