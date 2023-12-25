package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticFloat;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticFraction;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArithmeticFractionGenerator implements ArithmeticOperandGenerator {

    private int min;
    private int max;

    public ArithmeticFractionGenerator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public List<ArithmeticOperand> generate(int operandNum) {
        Random random = new Random(System.nanoTime());

        List<ArithmeticOperand> operands = new ArrayList<>(operandNum);

        for (int i = 0; i < operandNum; i++) {
            int denominator = random.nextInt(Math.max(1, min), max + 1);
            int numerator = random.nextInt(min, denominator + 1);
            operands.add(new ArithmeticFraction(numerator, denominator));
        }

        return operands;
    }
}
