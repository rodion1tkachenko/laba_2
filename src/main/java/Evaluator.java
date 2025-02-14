

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Класс Evaluator предназначен для оценки математических выражений,
 * поддерживающих переменные и операции с различным приоритетом.
 */
public class Evaluator {

    private static final Map<Character, Integer> operatorPrecedence = new HashMap<>();
    private final Map<String, Double> variableStore = new HashMap<>();

    static {
        operatorPrecedence.put('+', 1);
        operatorPrecedence.put('-', 1);
        operatorPrecedence.put('*', 2);
        operatorPrecedence.put('/', 2);
    }

    /**
     * Присваивает значение переменной.
     *
     * @param name  имя переменной
     * @param value значение, которое будет присвоено переменной
     */
    public void assignVariable(String name, double value) {
        variableStore.put(name, value);
    }

    /**
     * Выполняет вычисление двух операндов с заданным оператором.
     *
     * @param operator      оператор, который будет применен к операндам
     * @param secondOperand второй операнд
     * @param firstOperand  первый операнд
     * @return результат вычисления
     * @throws Exception если происходит ошибка (например, деление на ноль или неизвестный оператор)
     */
    private double calculate(char operator, double secondOperand, double firstOperand) throws Exception {
        switch (operator) {
            case '+':
                return firstOperand + secondOperand;
            case '-':
                return firstOperand - secondOperand;
            case '*':
                return firstOperand * secondOperand;
            case '/':
                if (secondOperand == 0) throw new Exception("Ошибка: Деление на ноль");
                return firstOperand / secondOperand;
            default:
                throw new Exception("Ошибка: Неизвестный оператор " + operator);
        }
    }

    /**
     * Оценивает математическое выражение.
     *
     * @param expr строка, представляющая математическое выражение
     * @return результат вычисления выражения
     * @throws Exception если происходит ошибка (например, неизвестная переменная)
     */
    double evaluate(String expr) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int index = 0; index < expr.length(); index++) {
            char currentChar = expr.charAt(index);

            // Игнорируем пробелы
            if (currentChar == ' ') {
                continue;
            }

            // Обработка чисел
            if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder numberBuilder = new StringBuilder();
                while (index < expr.length() && (Character.isDigit(expr.charAt(index)) || expr.charAt(index) == '.')) {
                    numberBuilder.append(expr.charAt(index++));
                }
                values.push(Double.parseDouble(numberBuilder.toString()));
                index--;
            }
            // Обработка переменных
            else if (Character.isLetter(currentChar)) {
                String variableName = String.valueOf(currentChar);
                if (!variableStore.containsKey(variableName)) {
                    throw new Exception("Ошибка: Неизвестная переменная " + variableName);
                }
                values.push(variableStore.get(variableName));
            }
            // Обработка операторов
            else if (operatorPrecedence.containsKey(currentChar)) {
                while (!operators.isEmpty() && operatorPrecedence.get(operators.peek()) >= operatorPrecedence.get(currentChar)) {
                    values.push(calculate(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(currentChar);
            }
        }

        while (!operators.isEmpty()) {            values.push(calculate(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    /**
     * Оценивает математическое выражение с учетом заданных переменных.
     *
     * @param expression строка, представляющая математическое выражение
     * @return результат вычисления выражения с переменными
     * @throws Exception если происходит ошибка (например, неизвестная переменная)
     */
    public double evaluateExpression(String expression) throws Exception {
        for (Map.Entry<String, Double> entry : variableStore.entrySet()) {
            expression = expression.replace(entry.getKey(), entry.getValue().toString());
        }
        return evaluate(expression);
    }
}