package ru.nsu.kuzminov;

/**
 * Класс для оценки.
 */
public class Grade {
    private final int semester;
    private final String subjName;
    private final String type;
    private final int grade;

    /**
     * Конструктор.
     *
     * @param semester семестр.
     * @param subjName название предмета.
     * @param type     тип сдачи.
     * @param grade    оценка.
     */
    public Grade(int semester, String subjName, String type, int grade) {
        this.semester = semester;
        this.subjName = subjName;
        this.type = type;
        this.grade = grade;
    }

    /**
     * Получить семестр.
     *
     * @return возвращает номер семестра.
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Получить оценку.
     *
     * @return возвращает оценку.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Получить тип сдачи.
     *
     * @return тип сдачи.
     */
    public String getType() {
        return type;
    }

    /**
     * Получить название предмета.
     *
     * @return название предмета.
     */
    public String getSubjName() {
        return subjName;
    }
}
