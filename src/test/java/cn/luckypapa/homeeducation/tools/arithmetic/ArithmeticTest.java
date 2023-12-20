package cn.luckypapa.homeeducation.tools.arithmetic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArithmeticTest {
    @Test
    void testGenArithmetic() {
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            Arithmetic arithmetic = new Arithmetic(2, ArithmeticOperandType.INT, 1, true);
            // log.info("表达式: {}，是否有效：{}", arithmetic, arithmetic.isValid());
            if (arithmetic.isValid()) {
                log.info("有效的算式：{}", arithmetic);
                count ++;
            }
        }

        log.info("{} of 1000 is valid", count);
        count = 0;
        for (int i = 0; i < 10000; i++) {
            Arithmetic arithmetic = new Arithmetic(5, ArithmeticOperandType.INT, 2, true);
            // log.info("表达式: {}，是否有效：{}", arithmetic, arithmetic.isValid());
            if (arithmetic.isValid()) {
                log.info("有效的算式：{}", arithmetic);
                count ++;
            }
        }

        log.info("{} of 10000 is valid", count);
    }

    @Test
    void testToPostfixExpressionLoop() {
        for (int i = 0; i < 100; i++) {
            Arithmetic arithmetic = new Arithmetic(5, ArithmeticOperandType.INT, 2, true);
            log.info(arithmetic.toString());
            Deque<ArithmeticElement> postfixExpression = arithmetic.toPostfixExpression();
            int size = postfixExpression.size();
            String[] express = new String[size];

            while (!postfixExpression.isEmpty()) {
                ArithmeticElement element = postfixExpression.pop();
                if (element instanceof ArithmeticOperator) {
                    express[--size] = ((ArithmeticOperator) element).getOperator();
                } else {
                    express[--size] = ((ArithmeticOperand) element).getOperand().toString();
                }
            }

            log.info("postfix: {}", StringUtils.join(express, " "));
        }

    }

    @Test
    void testToPostfixExpression() {
        doTestToPostfixExpression("(11+10-16)-(9÷9-8)");
        doTestToPostfixExpression("19÷(13÷14÷7)-8-9");
    }

    private void doTestToPostfixExpression(String expression) {
        Arithmetic arithmetic = new Arithmetic(expression);
        log.info(arithmetic.toString());
        Deque<ArithmeticElement> postfixExpression = arithmetic.toPostfixExpression();
        int size = postfixExpression.size();
        String[] express = new String[size];
        int index = 0;
        while (!postfixExpression.isEmpty()) {
            ArithmeticElement element = postfixExpression.removeFirst();
            if (element instanceof ArithmeticOperator) {
                express[index ++] = ((ArithmeticOperator) element).getOperator();
            } else {
                express[index ++] = ((ArithmeticOperand) element).getOperand().toString();
            }
        }

        log.info("postfix: {}", StringUtils.join(express, " "));
    }
}