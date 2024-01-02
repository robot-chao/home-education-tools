package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.tools.arithmetic.generator.ArithmeticIntGenerator;
import cn.luckypapa.homeeducation.tools.arithmetic.generator.ArithmeticOperatorGenerator;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Deque;

@Slf4j
class ArithmeticTest {
    @Test
    void testGenArithmetic() {
        ArithmeticIntGenerator intGenerator = new ArithmeticIntGenerator(20, 5);
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            Arithmetic arithmetic = new Arithmetic(2, intGenerator,
                    ArithmeticOperatorGenerator.gradeOneOperatorGenerator(), true,
                    ArithmeticValidator.intValidator(20));
            // log.info("表达式: {}，是否有效：{}", arithmetic, arithmetic.isValid());
            if (arithmetic.isValid()) {
                log.info("有效的算式：{}", arithmetic);
                count ++;
            }
        }

        log.info("{} of 1000 is valid", count);
        count = 0;
        for (int i = 0; i < 10000; i++) {
            Arithmetic arithmetic = new Arithmetic(5, intGenerator,
                    ArithmeticOperatorGenerator.gradeThreeOperatorGenerator(), true,
                    ArithmeticValidator.intValidator(20));
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
        ArithmeticIntGenerator intGenerator = new ArithmeticIntGenerator(20, 5);
        for (int i = 0; i < 100; i++) {
            Arithmetic arithmetic = new Arithmetic(5, intGenerator,
                    ArithmeticOperatorGenerator.gradeThreeOperatorGenerator(), true,
                    ArithmeticValidator.intValidator(20));
            log.debug(arithmetic.toString());
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

            log.debug("postfix: {}", StringUtils.join(express, " "));
        }

    }

    @Test
    void testToPostfixExpression() {
        doTestToPostfixExpression("18-20+6");
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