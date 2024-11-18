package ru.nsu.kuzminov;

public class Grade {
    private final int semester;
    private final String subjName;
    private final String type;
    private final int grade;

    public Grade(int semester, String subjName, String type, int grade) {
        this.semester = semester;
        this.subjName = subjName;
        this.type = type;
        this.grade = grade;
    }

    public int getSemester() {
        return semester;
    }

    public int getGrade() {
        return grade;
    }

    public String getType() {
        return type;
    }

    public String getSubjName() {
        return subjName;
    }
}
