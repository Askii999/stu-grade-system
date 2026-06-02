import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * 学生成绩管理系统 — 主界面
 *
 * 设计原因：
 * - 继承 JFrame，作为整个应用的窗口容器
 * - 界面层只负责：收集用户输入、显示数据、弹出提示；业务逻辑委托给 StudentManager
 * - 使用 DefaultTableModel + JTable 展示学生列表，模型与视图分离
 */
public class StudentFrame extends JFrame {

    // ==================== 输入组件 ====================
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField javaScoreField;
    private JTextField mathScoreField;
    private JTextField englishScoreField;

    // ==================== 表格组件 ====================
    private JTable table;
    private DefaultTableModel tableModel;

    // ==================== 按钮组件（initListeners 中绑定事件） ====================
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnQuery;
    private JButton btnShowAll;
    private JButton btnSort;
    private JButton btnSave;
    private JButton btnLoad;
    private JButton btnClear;

    // ==================== 业务管理 ====================
    private StudentManager manager = new StudentManager();

    private static final String DATA_FILE = "students.txt";

    public StudentFrame() {
        setTitle("学生成绩管理系统");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        initListeners();
    }

   
    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // ---------- 1. 创建输入框 ----------
        idField = new JTextField(12);
        nameField = new JTextField(12);
        ageField = new JTextField(6);
        javaScoreField = new JTextField(6);
        mathScoreField = new JTextField(6);
        englishScoreField = new JTextField(6);

        // ---------- 2. 创建按钮 ----------
        btnAdd = new JButton("添加");
        btnUpdate = new JButton("修改");
        btnDelete = new JButton("删除");
        btnQuery = new JButton("查询");
        btnShowAll = new JButton("显示全部");
        btnSort = new JButton("按平均分排序");
        btnSave = new JButton("保存到文件");
        btnLoad = new JButton("从文件读取");
        btnClear = new JButton("清空输入框");

        // ---------- 3. 输入区布局 ----------
        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("学号："));
        row1.add(idField);
        row1.add(new JLabel("姓名："));
        row1.add(nameField);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("年龄："));
        row2.add(ageField);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Java成绩："));
        row3.add(javaScoreField);
        row3.add(new JLabel("数学成绩："));
        row3.add(mathScoreField);
        row3.add(new JLabel("英语成绩："));
        row3.add(englishScoreField);

        inputPanel.add(row1);
        inputPanel.add(row2);
        inputPanel.add(row3);

        // ---------- 4. 按钮区布局 ----------
        JPanel btnPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        btnPanel1.add(btnAdd);
        btnPanel1.add(btnUpdate);
        btnPanel1.add(btnDelete);
        btnPanel1.add(btnQuery);
        btnPanel1.add(btnShowAll);
        btnPanel1.add(btnSort);

        JPanel btnPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        btnPanel2.add(btnSave);
        btnPanel2.add(btnLoad);
        btnPanel2.add(btnClear);

        // ---------- 5. 北部面板：标题 + 输入 + 按钮 ----------
        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel titleLabel = new JLabel("学生成绩管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));

        JPanel topContent = new JPanel(new BorderLayout(5, 5));
        topContent.add(inputPanel, BorderLayout.CENTER);

        JPanel btnWrapper = new JPanel(new GridLayout(2, 1, 5, 5));
        btnWrapper.add(btnPanel1);
        btnWrapper.add(btnPanel2);
        topContent.add(btnWrapper, BorderLayout.SOUTH);

        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(topContent, BorderLayout.CENTER);

        // ---------- 6. 表格 ----------
        String[] columnNames = {
            "学号", "姓名", "年龄", "Java成绩", "数学成绩", "英语成绩", "总分", "平均分", "等级"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        // ---------- 7. 组装到窗口 ----------
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initListeners() {
        btnAdd.addActionListener(e -> onAddClick());
        btnUpdate.addActionListener(e -> onUpdateClick());
        btnDelete.addActionListener(e -> onDeleteClick());
        btnQuery.addActionListener(e -> onQueryClick());
        btnShowAll.addActionListener(e -> onShowAllClick());
        btnSort.addActionListener(e -> onSortClick());
        btnSave.addActionListener(e -> onSaveClick());
        btnLoad.addActionListener(e -> onLoadClick());
        btnClear.addActionListener(e -> clearInput());
    }

    // ==================== 表格与输入辅助方法 ====================

   
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : manager.getStudents()) {
            tableModel.addRow(new Object[] {
                s.getId(),
                s.getName(),
                s.getAge(),
                s.getJavaScore(),
                s.getMathScore(),
                s.getEnglishScore(),
                s.getTotalScore(),
                String.format("%.2f", s.getAverageScore()),
                s.getLevel()
            });
        }
    }

 
    private Student getStudentFromInput() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        double javaScore = Double.parseDouble(javaScoreField.getText().trim());
        double mathScore = Double.parseDouble(mathScoreField.getText().trim());
        double englishScore = Double.parseDouble(englishScoreField.getText().trim());
        return new Student(id, name, age, javaScore, mathScore, englishScore);
    }

  
    private void clearInput() {
        
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        javaScoreField.setText("");
        mathScoreField.setText("");
        englishScoreField.setText("");
    }

    private boolean validateInput() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String javaText = javaScoreField.getText().trim();
        String mathText = mathScoreField.getText().trim();
        String englishText = englishScoreField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ageText.isEmpty()
                || javaText.isEmpty() || mathText.isEmpty() || englishText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入完整信息", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            int age = Integer.parseInt(ageText);
            double javaScore = Double.parseDouble(javaText);
            double mathScore = Double.parseDouble(mathText);
            double englishScore = Double.parseDouble(englishText);

            if (age <= 0) {
                JOptionPane.showMessageDialog(this, "年龄必须为正整数", "提示", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (javaScore < 0 || mathScore < 0 || englishScore < 0
                    || javaScore > 100 || mathScore > 100 || englishScore > 100) {
                JOptionPane.showMessageDialog(this, "成绩必须在 0~100 之间", "提示", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "年龄必须为整数，成绩必须为数字", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateIdOnly() {
        if (idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入学号", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

  
    private void fillInputFromStudent(Student s) {

        idField.setText(s.getId());
        nameField.setText(s.getName());
        ageField.setText(String.valueOf(s.getAge()));
        javaScoreField.setText(String.valueOf(s.getJavaScore()));
        mathScoreField.setText(String.valueOf(s.getMathScore()));
        englishScoreField.setText(String.valueOf(s.getEnglishScore()));
    }

    // ==================== 按钮事件处理 ====================

    private void onAddClick() {
        if (!validateInput()) {
            return;
        }
        Student student = getStudentFromInput();
        if (manager.addStudent(student)) {
            refreshTable();
            clearInput();
            JOptionPane.showMessageDialog(this, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "学号已存在", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onUpdateClick() {
        if (!validateInput()) {
            return;
        }
        Student student = getStudentFromInput();
        if (manager.updateStudent(student)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "未找到该学生", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onDeleteClick() {
        if (!validateIdOnly()) {
            return;
        }

        String id = idField.getText().trim();
        Student found = manager.findStudentById(id);
        if (found == null) {
            JOptionPane.showMessageDialog(this, "未找到该学生", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "确定删除该学生吗？", "确认",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            manager.deleteStudentById(id);
            refreshTable();
            clearInput();
            JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onQueryClick() {
        if (!validateIdOnly()) {
            return;
        }

        String id = idField.getText().trim();
        Student found = manager.findStudentById(id);
        if (found != null) {
            fillInputFromStudent(found);
            JOptionPane.showMessageDialog(this, "查询成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "未找到该学生", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onShowAllClick() {
     
        refreshTable(); 
    }

    private void onSortClick() {
      
        manager.sortByAverageScore();
        refreshTable();
    }

    private void onSaveClick() {
        try {
            FileUtil.saveStudents(manager.getStudents(), DATA_FILE);
            JOptionPane.showMessageDialog(this, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "保存失败：" + e.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onLoadClick() {
        try {
            ArrayList<Student> list = FileUtil.readStudents(DATA_FILE);
            manager.setStudents(list);
            refreshTable();
            JOptionPane.showMessageDialog(this, "读取成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "读取失败：" + e.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
        }
    }
}