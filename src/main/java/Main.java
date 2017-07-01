import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by orenga on 6/30/17.
 */
public class Main {

    public static void main(String[] args){

        try {

            //Loading an existing document
            File file = new File("src/main/resources/catalogkorsim2017.pdf");
            PDDocument document = PDDocument.load(file);

            //Instantiate PDFTextStripper class
            PDFTextStripper pdfStripper = new PDFTextStripper();

            //set start page to pdf courses index
            pdfStripper.setStartPage(339);
            String text = pdfStripper.getText(document);

            //create raw courses list
            List<String> rawCatalog = Arrays.asList(text.split("\n"));
            List<CourseRaw> courses = rawCatalog.stream()
                    .filter(line -> line.length() > 3 && line.contains(")"))
                    .map(Parser::parseLine)
                    .collect(Collectors.toList());

            //create references to pdf page with faculty and department names
            Set<String> refPages = courses.stream()
                    .map(course -> course.refPage)
                    .collect(Collectors.toSet());

            //other helper maps creation
            Parser.populateFacultyAndDepartmentsMaps(document, pdfStripper, refPages);

            Set<Course> finalCourses = courses.stream()
                    .map(Parser::rawCourseToCourse)
                    .collect(Collectors.toSet());


            JsonUtil.outoutCourses(finalCourses);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
