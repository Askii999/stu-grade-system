/**
 * 优秀学生子类
 *
 * 设计原因：
 * - 通过 extends Student 体现继承：优秀学生"是一种"普通学生，拥有其全部属性
 * - 额外增加 reward（奖励）字段，体现子类对父类的扩展
 * - 文档要求：平均分 >= 90 可视为优秀学生（后续在添加学生时使用）
 */
public class ExcellentStudent extends Student {

    private String reward;
    
    public ExcellentStudent(String id, String name, int age,double javaScore,
                            double mathScore, double englishScore,String reward){
        super(id,name,age,javaScore,mathScore,englishScore);
        this.reward = reward;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
