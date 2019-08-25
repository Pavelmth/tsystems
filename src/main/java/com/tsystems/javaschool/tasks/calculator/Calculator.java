package com.tsystems.javaschool.tasks.calculator;

import java.util.NoSuchElementException;
import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here

        if (Validator(statement)) {
            return null;
        } else {
            statement = infixToPostfix(statement);
            double result;
            try {
                result = calculate(statement);
            } catch (ArithmeticException | NoSuchElementException e) {
                return null;
            }
            if (result % 1 > 0.0) {
                double n = Math.round(result * 10_000);
                double result2 = n / 10_000;
                return Double.toString(n / 10_000);
            } else {
                return Integer.toString((int) result);
            }
        }
    }

    private boolean Validator (String statement) {
        boolean checking = false;

        //проверка на null
        if (statement == null || statement.equals("")) {
            checking = true;
        } else {
            //проверка символов и порядка скобок
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < statement.length(); i++) {
                char ch = statement.charAt(i);

                if (!Character.isDigit(ch) && !Character.isSpaceChar(ch) && ch != '+' && ch != '-' && ch != '*' && ch != '/' && ch != '(' && ch != ')' && ch != '.') {
                    checking = true;
                    break;
                }
                if (ch == '(') {
                    stack.push(ch);
                }
                else if (ch == ')') {
                    if (stack.isEmpty()) {
                        checking = true;
                        break;
                    } else {
                        stack.pop();
                    }
                }
            }
            if (!stack.empty()) {
                checking = true;
            }

            //проверка последовательности знаков
            for (int i = 0; i < statement.length(); i++) {
                char ch = statement.charAt(i);
                if (ch == ' ') {
                    continue;
                }
                if (stack.empty()) {
                    stack.push(ch);
                    continue;
                } else {
                    if (ch == '.' && '.' == stack.peek()) {
                        checking = true;
                        break;
                    }
                    if ((ch == '+' || ch == '-' || ch == '*' || ch == '/') &&
                            ('+' == stack.peek()|| '-' == stack.peek() || '*' == stack.peek() || '/' == stack.peek())) {
                        checking = true;
                        break;
                    }
                }
                stack.push(ch);
            }
        }

        return checking;
    }

    private static int Prior(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    private String infixToPostfix(String infixExp)
    {
        StringBuilder postfixExp = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i<infixExp.length(); ++i)
        {
            char c = infixExp.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                postfixExp.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                postfixExp.append(" ");
                while (!stack.isEmpty() && stack.peek() != '(')
                    postfixExp.append(stack.pop());
                stack.pop();
            } else {
                while (!stack.isEmpty() && Prior(c) <= Prior(stack.peek())){
                    postfixExp.append(" ");
                    postfixExp.append(stack.pop());
                }
                postfixExp.append(" ");
                stack.push(c);
            }
        }

        while (!stack.isEmpty()){
            postfixExp.append(" ");
            postfixExp.append(stack.pop());
        }
        return postfixExp.toString();
    }

    private double calculate(String exp) throws ArithmeticException, NoSuchElementException {
        Stack<Double> stack = new Stack<>();
        String[] s = exp.split(" ");
        Double result;
        for (String value : s) {
            if (value.equals("+") || value.equals("*") || value.equals("/") || value.equals("-")) {
                if (stack.size() < 2) {
                    throw new NoSuchElementException();
                }
                double a = stack.pop();
                double b = stack.pop();
                switch (value) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(b - a);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    default:
                        if (a == 0) {
                            throw new ArithmeticException();
                        } else {
                            stack.push(b / a);
                        }
                        break;
                }
            } else {
                stack.push(Double.parseDouble(value));
            }
        }
        if (stack.size() != 1) {
            throw new NoSuchElementException();
        }

        result = stack.pop();
        return result;
    }
}
