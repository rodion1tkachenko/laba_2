
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    private Evaluator evaluator;

    @BeforeEach
    void setup() {
        evaluator = new Evaluator();
    }

    @Test
    void testAdditionWithPositiveNumbers() throws Exception {
        assertEquals(15.0, evaluator.evaluate("7 + 8"), "Ошибка при сложении положительных чисел");
    }

    @Test
    void testSubtractionWithNegativeResult() throws Exception {
        assertEquals(-5.0, evaluator.evaluate("5 - 10"), "Ошибка при вычитании с отрицательным результатом");
    }


    @Test
    void testDivisionWithPositiveResult() throws Exception {
        assertEquals(2.0, evaluator.evaluate("8 / 4"), "Ошибка при делении с положительным результатом");
    }


    @Test
    void testOperatorPrecedenceWithMixedOperations() throws Exception {
        assertEquals(25.0, evaluator.evaluate("5 + 10 * 2"), "Ошибка при проверке приоритета операторов с разными операциями");
    }

    @Test
    void testDivisionByZeroShouldThrowException() {
        Exception exception = assertThrows(Exception.class, () -> evaluator.evaluate("10 / 0"));
        assertTrue(exception.getMessage().contains("Деление на ноль"), "Не выброшено исключение при делении на ноль");
    }


    @Test
    void testUsingVariablesInExpression() throws Exception {
        evaluator.assignVariable("a", 15.0);
        evaluator.assignVariable("b", 25.0);
        assertEquals(40.0, evaluator.evaluate("a + b"), "Ошибка при использовании переменных в выражении");
    }

    @Test
    void testEvaluatingUnknownVariableShouldThrowException() {
        Exception exception = assertThrows(Exception.class, () -> evaluator.evaluate("w"));
        assertTrue(exception.getMessage().contains("Неизвестная переменная"), "Не выброшено исключение для неизвестной переменной");
    }
}