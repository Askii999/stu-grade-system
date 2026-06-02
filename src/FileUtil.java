import java.io.*;
import java.util.ArrayList;

/**
 * 文件读写工具类
 *
 * 设计原因：
 * - 将文件 IO 操作独立成工具类，StudentManager 和 GUI 不需要关心文件格式细节
 * - 使用 static 方法：读写不依赖对象状态，直接通过类名调用即可
 * - 数据格式：学号,姓名,年龄,Java成绩,数学成绩,英语成绩（CSV 逗号分隔）
 */
public class FileUtil {

   
    public static void saveStudents(ArrayList<Student> students, String fileName) {
        //BufferedWriter：包装流，批量写入，提升I/O，需包裹FileWriter
        //FileWriter：字符流，直接写入
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getJavaScore() + "," + s.getMathScore() + "," + s.getEnglishScore());
                writer.newLine();
            }
        } catch (IOException e) {//I/O异常
            throw new RuntimeException("保存文件失败：" + e.getMessage());
        }
    }

   
    public static ArrayList<Student> readStudents(String fileName) {
        ArrayList<Student> students = new ArrayList<>();

        File file = new File(fileName);
        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length != 6) continue;
                students.add(new Student(arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5])));
            }
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败：" + e.getMessage());
        }
        return students;
    }
    
}