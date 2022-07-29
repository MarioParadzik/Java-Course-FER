package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

    @Test
    public void lessTest() {
        assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jasna"));
    }

    @Test
    public void lessWrongTest() {
        assertFalse(ComparisonOperators.LESS.satisfied("Jasna", "Ana"));
    }

    @Test
    public void lessOrEqualsTest() {
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Mario", "Mario"));
    }

    @Test
    public void lessOrEqualsWrongTest() {
        assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Mario", "Ana"));
    }

    @Test
    public void greaterTest() {
        assertTrue(ComparisonOperators.GREATER.satisfied("Mario", "Ana"));
    }

    @Test
    public void greaterWrongTest() {
        assertFalse(ComparisonOperators.GREATER.satisfied("Ana", "Mario"));
    }

    @Test
    public void greaterEqualsTest() {
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Mario", "Mario"));
    }

    @Test
    public void greaterEqualsWrongTest() {
        assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Ana", "Mario"));
    }

    @Test
    public void equalsTest() {
        assertTrue(ComparisonOperators.EQUALS.satisfied("Mario", "Mario"));
    }

    @Test
    public void equalsWrongTest() {
        assertFalse(ComparisonOperators.EQUALS.satisfied("Mario", "Ana"));
    }

    @Test
    public void notEqualsTest() {
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Mario"));
    }

    @Test
    public void notEqualsWrongTest() {
        assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Mario", "Mario"));
    }

    @Test
    public void likeTest() {
        assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
        assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
        assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
    }
}