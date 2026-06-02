/**
 * 成绩计算接口
 *
 * 设计原因：
 * - 将"如何计算成绩"从 Student 的具体字段中抽象出来，体现面向接口编程
 * - Student 负责存数据，ScoreCalculator 负责定义计算行为，职责分离
 * - 后续若有其他类型学生（如 ExcellentStudent），可复用同一套计算契约
 */
public interface ScoreCalculator {
   
    double getTotalScore();
 
    double getAverageScore();
  
    String getLevel();
}
