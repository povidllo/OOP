package ru.nsu.kuzminov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс, реализующий зачетную книжку ученика ФИТ.
 */
public class GradeBook {
    private ArrayList<Grade> grades;
    int[][] countCan;
    int[][] countCur;
    HashMap<String, Integer> associationIdType;
    boolean freeEducation;
    int lastSemester;

    /**
     * Заполняет массивы countCan, countCur, считывая из файла.
     *
     * @throws FileNotFoundException ошибка, если файла нет.
     */
    private void fillCountCan() throws FileNotFoundException {
        Scanner reader = new Scanner(new File("countSubj.txt"));
        countCan = new int[8][8];
        countCur = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int count = reader.nextInt();
                countCan[i][j] = count;
                countCur[i][j] = 0;
            }
        }

    }

    /**
     * Конструктор.
     *
     * @param freeStatus true - если на бюджете, false если нет.
     * @throws FileNotFoundException исключение для fillCountCan.
     */
    public GradeBook(boolean freeStatus) throws FileNotFoundException {
        grades = new ArrayList<>();
        fillCountCan();
        freeEducation = freeStatus;
        lastSemester = 0;
        associationIdType = new HashMap<>();
        associationIdType.put("Задание", 0);
        associationIdType.put("Контрольная", 1);
        associationIdType.put("Коллоквиум", 2);
        associationIdType.put("Экзамен", 3);
        associationIdType.put("Диф зачет", 4);
        associationIdType.put("Зачет", 5);
        associationIdType.put("Защита отчета", 6);
        associationIdType.put("ВКР", 7);
    }

    /**
     * Добавляет оценку в зачетку.
     *
     * @param semester номер семестра 1-8.
     * @param subj     имя предмета.
     * @param type     тип оценки.
     * @param grade    оценка.
     * @return true если добавилось, иначе false.
     */
    public boolean addGrade(int semester, String subj, String type, int grade) {
        if (semester > 8 || semester < 1 || grade < 2 || grade > 5
                || !associationIdType.containsKey(type)) {
            throw new IllegalArgumentException("Не правильные входные данные");
        }
        semester -= 1;
        int idCount = associationIdType.get(type);
        if (countCur[idCount][semester] < countCan[idCount][semester]) {
            if (lastSemester < semester) {
                lastSemester = semester;
            }
            Grade newGrade = new Grade(semester, subj, type, grade);
            grades.add(newGrade);
            countCur[idCount][semester]++;
            return true;
        }
        return false;
    }

    /**
     * Средняя оценка за все семестры.
     *
     * @return среднюю оценку.
     */
    public double averageScore() {
        int all = 0;
        for (var grade : grades) {
            all += grade.getGrade();
        }
        return grades.isEmpty() ? 0 : (double) all / grades.size();
    }

    /**
     * Оценивает возможность перевестись на бесплатное обучение.
     *
     * @return true если может, иначе false.
     */
    public boolean canGetFreeEducation() {
        if (freeEducation) {
            return true;
        }
        if (lastSemester == 0) {
            return false;
        }
        for (var grade : grades) {
            if (grade.getSemester() == lastSemester || grade.getSemester() == lastSemester - 1) {
                if (grade.getType().equals("Экзамен") && grade.getGrade() <= 3) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Оценивает возможность получить красный диплом(75% оценок за 8 семестр 5,
     *                              ВКР = 5, нет 3 по диф зачету и экзам).
     *
     * @return true если может, иначе false.
     */
    public boolean canGetRedDiplom() {
        if (lastSemester != 7) {
            return false;
        }

        double count = 0;
        double count5 = 0;
        for (var grade : grades) {
            if (grade.getSemester() != lastSemester) {
                continue;
            }
            count++;
            if (grade.getGrade() == 5) {
                count5++;
            }
            if ((grade.getType().equals("Диф зачет") || grade.getType().equals("Экзамен")) && grade.getGrade() <= 3) {
                return false;
            } else if (grade.getType().equals("ВКР") && grade.getGrade() != 5) {
                return false;
            }
        }
        return count5 / count > 0.75;
    }

    /**
     * Оценивает возможность получить повышенную стипендию.
     *
     * @return true если может, иначе false.
     */
    public boolean canGetBetterStipendia() {
        for (var grade : grades) {
            if (grade.getSemester() == lastSemester) {
                if (grade.getGrade() < 5) {
                    return false;
                }
            }
        }
        return true;
    }
}
