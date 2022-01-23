package com.techmaker.calcengine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter an operation and two numbers:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        String[] parts = userInput.split(" ");
        String keyword = parts[0];
        double leftVal = valueFromWord(parts[1]);
        double rightVal = valueFromWord(parts[2]);

        process(keyword, leftVal, rightVal);
    }

    private static void process(String keyword, double leftVal, double rightVal) {
        Optional<Object> optionalProcessor = retrieveProcessor(keyword);
        Consumer<Object> consumer = processor -> {
            double result = 0d;
            if(processor instanceof MathProcessing) {
                result = ((MathProcessing)processor).doCalculation(leftVal, rightVal);
            } else {
                result = handleCalculation(processor, leftVal, rightVal);
            }
            System.out.println("result = " + result);
        };
        optionalProcessor.ifPresentOrElse(consumer, ()-> System.out.println("Invalid Operation"));
     }

    private static double handleCalculation(Object processor, double leftVal, double rightVal) {
        double result = 0d;
        CommandKeyword commandKeyword = processor.getClass().getAnnotation(CommandKeyword.class);
        String methodName = commandKeyword.method();
        try {
            Method method = processor.getClass().getMethod(methodName, Double.class, Double.class);
            result = (double)method.invoke(processor, leftVal, rightVal);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Optional<Object> retrieveProcessor(String keyword) {
        Object[] processors = {new Adder(), new Subtracter(), new Multiplier(), new Divider(), new PowerOf()};
        for (Object processor : processors) {
            CommandKeyword commandKeyword = processor.getClass().getAnnotation(CommandKeyword.class);
            if(commandKeyword.value().equalsIgnoreCase(keyword)){
                return Optional.of(processor);
            }
        }
        return Optional.empty();
    }

    static double valueFromWord(String word) {
        String[] numberWords = {
                "zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine"
        };
        double value = -1d;
        for(int index = 0; index < numberWords.length; index++) {
            if(word.equals(numberWords[index])) {
                value = index;
                break;
            }
        }
        if(value == -1d)
            value = Double.parseDouble(word);

        return value;
    }

}









