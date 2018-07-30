package com.VortixDev.PostfixParser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostfixParser {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String postfix = scan.nextLine();

        scan.close();

        postfix = calculate(postfix);

        System.out.println(postfix);
    }

    public static String calculate(String postfix) {
        String result = postfix;
        String initialPostfix;

        do {
            initialPostfix = result;

            do {
                postfix = result;

                result = processPostfix(result, '/');
            } while (result != postfix);

            do {
                postfix = result;

                result = processPostfix(result, '*');
            } while (result != postfix);

            do {
                postfix = result;

                result = processPostfix(result, '+');
            } while (result != postfix);

            do {
                postfix = result;

                result = processPostfix(result, '-');
            } while (result != postfix);
        } while (initialPostfix != result);

        return result;
    }

    private static String processPostfix(String postfix, char operator) {
        String numberMatch = "(\\-?\\d+(?:\\.\\d+)?)";

        Matcher matcher = Pattern.compile(numberMatch + " " + numberMatch + " \\" + operator).matcher(postfix);

        boolean findMatch = matcher.find();

        while (findMatch) {
            String match = matcher.group(0);

            double firstValue = Double.parseDouble(matcher.group(1));
            double secondValue = Double.parseDouble(matcher.group(2));
            double resultValue;

            switch (operator) {
                case '/':
                    resultValue = firstValue / secondValue;

                    break;
                case '*':
                    resultValue = firstValue * secondValue;

                    break;
                case '+':
                    resultValue = firstValue + secondValue;

                    break;
                case '-':
                    resultValue = firstValue - secondValue;

                    break;
                default:
                    return postfix;
            }

            String result = String.valueOf(resultValue);

            postfix = postfix.replace(match, result);

            findMatch = matcher.find();
        }

        return postfix;
    }
}