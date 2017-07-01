import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Set;

/**
 * Created by orenga on 7/1/17.
 */
public class JsonUtil {

    public static void outoutCourses(Set<Course> finalCourses) throws IOException {
        try(Writer writer = new OutputStreamWriter(new FileOutputStream("Courses.json") , "UTF-8")){
            Gson gson = new GsonBuilder().create();
            gson.toJson(finalCourses, writer);
        }
    }
}
