import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by orenga on 7/1/17.
 */
public class Parser {

    static Pattern p = Pattern.compile("\\d{5}");
    static Map<String, Set<String>> facultiesAndDepartments = new HashMap<>();
    static Map<String, RefHelper> courseNumberToFacultyAndDep = new HashMap<>();

    public static Course rawCourseToCourse(CourseRaw rawCourse){
        try {
            String refPage = rawCourse.refPage;
            RefHelper refHelper = courseNumberToFacultyAndDep.get(refPage);
            String faculty = refHelper.getFaculty();
            String department = refHelper.getDepartment();
            return new Course(rawCourse, faculty, department);
        }
        catch(Exception e){
            e.printStackTrace();
            return new Course();
        }
    }

    public static void populateFacultyAndDepartmentsMaps(PDDocument document, PDFTextStripper pdfStripper, Set<String> refPages) {
        for (String ref : refPages){
            try {
                pdfStripper.setStartPage(Integer.valueOf(ref));
                pdfStripper.setEndPage(Integer.valueOf(ref));
                List<String> collect1 = Arrays.asList(pdfStripper.getText(document).split("\n")).stream()
                        .filter(line -> line.length() > 2)
                        .collect(Collectors.toList());
                String facultyName = collect1.get(0);
                String departmentName = collect1.get(1);
                courseNumberToFacultyAndDep.put(ref, new RefHelper(facultyName,departmentName));

                // populate catalog's faculties and departments
                if (facultiesAndDepartments.containsKey(facultyName)){
                    Set<String> departments = facultiesAndDepartments.get(facultyName);
                    departments.add(departmentName);
                }
                else {
                    facultiesAndDepartments.put(facultyName, new HashSet<>(Collections.singletonList(departmentName)));
                }


            }
            catch (Exception e){
                System.out.println("error with ref: " + ref);
            }
        }
    }

    public static CourseRaw parseLine(String line){
        String[] split = line.split("[)]");
        String ref = split[0].replaceAll("\\s",""); //232

        String courseNameAndNumber = split[1].replace("(","");
        String courseNumber = extractCourseNumber(courseNameAndNumber);
        String courseName =  courseNameAndNumber.replace(courseNumber,"");

        return new CourseRaw(ref, courseName, courseNumber);
    }

    public  static String extractCourseNumber(String line){
        Matcher matcher = p.matcher(line);
        return matcher.find() ? matcher.group() : "";
    }
}
