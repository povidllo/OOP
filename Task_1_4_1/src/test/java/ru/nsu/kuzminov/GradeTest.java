package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GradeTest {
    static Grade grade;

    @BeforeAll
    public static void init() {
        grade = new Grade(1, "subj", "type", 5);
    }

    @Test
    void getSemester() {
        assertEquals(grade.getSemester(), 1);
    }

    @Test
    void getGrade() {
        assertEquals(grade.getGrade(), 5);

    }

    @Test
    void getType() {
        assertEquals(grade.getType(), "type");

    }

    @Test
    void getSubjName() {
        assertEquals(grade.getSubjName(),"subj");

    }
}
