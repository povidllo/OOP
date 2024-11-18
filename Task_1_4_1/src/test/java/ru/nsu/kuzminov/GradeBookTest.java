package ru.nsu.kuzminov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GradeBookTest {

    private GradeBook gradesFree;
    private GradeBook gradesNotFree;

    @BeforeEach
    public void init() throws FileNotFoundException {
        gradesFree = new GradeBook(true);
        gradesNotFree = new GradeBook(false);
    }

    @Test
    public void addGradeOne() {
        assertTrue(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
    }

    @Test
    public void addGradeFull() {
        //их может быть 3 в первом семестре
        assertTrue(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
        assertTrue(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
        assertTrue(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
    }

    @Test
    public void addGradeOutFull() {
        //их может быть 3 в первом семестре
        assertTrue(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
        assertTrue(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
        assertTrue(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
        assertFalse(gradesFree.addGrade(1, "Оси", "Экзамен", 5));
    }

    @Test
    public void addGradeIllegal() {

        assertThrows(IllegalArgumentException.class, () ->
                gradesFree.addGrade(0, "Оси", "Экзамен", 5));
        assertThrows(IllegalArgumentException.class, () ->
                gradesFree.addGrade(9, "Оси", "Экзамен", 5));
        assertThrows(IllegalArgumentException.class, () ->
                gradesFree.addGrade(1, "Оси", "Экзамен", 1));
        assertThrows(IllegalArgumentException.class, () ->
                gradesFree.addGrade(1, "Оси", "Экзамен", 6));
        assertThrows(IllegalArgumentException.class, () ->
                gradesFree.addGrade(1, "Оси", "ауауа", 6));
    }

    @Test
    public void avgTestOK() {
        gradesFree.addGrade(2, "Оси", "Экзамен", 5);
        gradesFree.addGrade(5, "Оси", "Диф зачет", 5);
        gradesFree.addGrade(2, "Оси", "Коллоквиум", 2);
        assertEquals(gradesFree.averageScore(), 4);
    }

    @Test
    public void avgEmpty() {
        assertEquals(gradesFree.averageScore(), 0);
    }

    @Test
    public void canGetFreeButAlreadyFree() {
        assertTrue(gradesFree.canGetFreeEducation());
    }

    @Test
    public void canGetFreeButItsFirstSemester() {
        assertFalse(gradesNotFree.canGetFreeEducation());
    }

    @Test
    public void cantGetFreeEducation() {
        gradesNotFree.addGrade(2, "Оси", "Экзамен", 3);
        gradesNotFree.addGrade(1, "Оси", "Экзамен", 3);
        assertFalse(gradesNotFree.canGetFreeEducation());
    }

    @Test
    public void cantGetFreeEducation2() {
        gradesNotFree.addGrade(1, "Оси", "Экзамен", 3);
        gradesNotFree.addGrade(2, "Оси", "Экзамен", 3);
        assertFalse(gradesNotFree.canGetFreeEducation());
    }

    @Test
    public void caGetFreeEducation() {
        gradesNotFree.addGrade(2, "Оси", "Экзамен", 4);
        gradesNotFree.addGrade(1, "Оси", "Экзамен", 5);
        assertTrue(gradesNotFree.canGetFreeEducation());

    }

    @Test
    public void cantGetRedDiplomeNotEightSemester() {
        assertFalse(gradesNotFree.canGetRedDiplom());
    }

    @Test
    public void cantGetRedDiplomeBecause3() {
        gradesFree.addGrade(8, "Оси", "Экзамен", 3);
        assertFalse(gradesFree.canGetRedDiplom());

    }

    @Test
    public void cantGetRedDiplomeBecause3_2() {
        gradesFree.addGrade(8, "Оси", "Диф зачет", 3);
        assertFalse(gradesFree.canGetRedDiplom());
    }

    @Test
    public void cantGetRedDiplomeBecauseVKR() {
        gradesFree.addGrade(8, "ВКР", "ВКР", 3);
        assertFalse(gradesFree.canGetRedDiplom());
    }

    @Test
    public void canGetRedDiplome() {
        gradesFree.addGrade(8, "ВКР", "ВКР", 5);
        gradesFree.addGrade(8, "Оси", "Экзамен", 5);
        assertTrue(gradesFree.canGetRedDiplom());
    }

    @Test
    public void cantGetBetterStip() {
        gradesFree.addGrade(1, "Оси", "Диф зачет", 4);
        gradesFree.addGrade(1, "Оси", "Экзамен", 5);
        assertFalse(gradesFree.canGetBetterStipendia());
    }

    @Test
    public void canGetBetterStip() {
        gradesFree.addGrade(1, "Оси", "Диф зачет", 5);
        gradesFree.addGrade(1, "Оси", "Экзамен", 5);
        assertTrue(gradesFree.canGetBetterStipendia());
    }

}