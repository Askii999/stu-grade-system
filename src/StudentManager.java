import java.util.ArrayList;
import java.util.Comparator;

/**
 * 学生数据管理类
 *
 * 设计原因：
 * - 将"数据的增删改查和排序"从 Student 实体中分离出来，Student 只负责表示单个学生
 * - 使用 ArrayList<Student> 在内存中统一管理所有学生，便于 GUI 层调用
 * - GUI 不直接操作 ArrayList，而是通过 StudentManager 提供的方法，降低耦合
 */
public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();
    //ArrayList是Java中的一个类，用于存储一组有序的元素，可以动态地添加、删除和访问元素。

    public boolean addStudent(Student student) {

        if(findStudentById(student.getId()) != null)
            return false ;
        else
            students.add(student);
        return true;
    }


    public Student findStudentById(String id) {

        for (Student s : students)
            if (s.getId().equals(id))
                return s;
        return null;
    }

   
    public boolean deleteStudentById(String id) {
        Student student = findStudentById(id);
        if (student == null) {
            return false;
        }
        students.remove(student);
        return true;
    }


    public boolean updateStudent(Student newStudent) {

        for(Student s : students)
            if (s.getId().equals(newStudent.getId())) {
                s.setName(newStudent.getName());
                s.setAge(newStudent.getAge());
                s.setJavaScore(newStudent.getJavaScore());
                s.setMathScore(newStudent.getMathScore());
                s.setEnglishScore(newStudent.getEnglishScore());
                return true;
            }
        return false;
    }

    public void sortByAverageScore() {
        //sort是Java中的一个方法，用于对列表中的元素进行排序。
        students.sort(new Comparator<Student>() {   

            @Override   //降序写法
            public int compare(Student s1, Student s2) {
                return Double.compare(s2.getAverageScore(), s1.getAverageScore());
            }
        });
    }


    public ArrayList<Student> getStudents() {
        return students;
    }

 
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
 
}
