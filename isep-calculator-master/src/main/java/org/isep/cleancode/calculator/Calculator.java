package org.isep.cleancode.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    public double evaluateMathExpression(String expression) {
        List<String> tokens = tokenize(expression);
        List<String> afterMul = handleMultiplication(tokens);
        return evaluateAdditionSubtraction(afterMul);
    }

    private List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        char[] chars = expression.replaceAll("\\s+", "").toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else if (c == '-' && (i == 0 || "+-*".contains("" + chars[i - 1]))) {
                // It's a unary minus
                number.append(c);
            } else {
                // Finish number if present
                if (!number.isEmpty()) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }
                tokens.add("" + c); // Operator
            }
        }

        if (!number.isEmpty()) {
            tokens.add(number.toString());
        }

        return tokens;
    }

    private List<String> handleMultiplication(List<String> tokens) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals("*")) {
                double left = Double.parseDouble(result.removeLast());
                double right = Double.parseDouble(tokens.get(++i));
                result.add(String.valueOf(left * right));
            } else {
                result.add(token);
            }
        }
        return result;
    }

    private double evaluateAdditionSubtraction(List<String> tokens) {
        double result = Double.parseDouble(tokens.getFirst());
        for (int i = 1; i < tokens.size(); i += 2) {
            String op = tokens.get(i);
            double value = Double.parseDouble(tokens.get(i + 1));
            if (op.equals("+")) result += value;
            else result -= value;
        }
        return result;
    }
}