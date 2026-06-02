/**
 * 学生实体类
 * 
 * 设计原因：
 * 1. 封装学号、姓名、年龄、三门成绩等属性，对外通过 getter/setter 访问
 * 2. 实现 ScoreCalculator 接口，让 Student 既"是"学生对象，又"能"计算成绩
 */
public class Student implements ScoreCalculator {

    private String id;
    private String name;
    private int age;
    private double javaScore;
    private double mathScore;
    private double englishScore;

    
    //构造方法：创建学生时必须提供全部基本信息
    public Student(String id, String name, int age,double javaScore, 
                    double mathScore, double englishScore) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.javaScore = javaScore;
        this.mathScore = mathScore;
        this.englishScore = englishScore;
    }

    // ==================== ScoreCalculator 接口实现 ====================

    @Override
    public double getTotalScore() {

        return javaScore + mathScore + englishScore;
    }

    @Override
    public double getAverageScore() {

        return getTotalScore() / 3.0;
    }

    @Override
    public String getLevel() {

        double avg = getAverageScore();
        if(avg >= 90)
            return "优秀";
        else if(avg >= 80)
            return "良好";
        else if(avg >= 70)
            return "中等";
        else if(avg >= 60)
            return "及格";
        else
            return "不及格";
    }

    // ==================== Getter / Setter ====================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getJavaScore() {
        return javaScore;
    }

    public void setJavaScore(double javaScore) {
        this.javaScore = javaScore;
    }

    public double getMathScore() {
        return mathScore;
    }

    public void setMathScore(double mathScore) {
        this.mathScore = mathScore;
    }

    public double getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(double englishScore) {
        this.englishScore = englishScore;
    }
}
