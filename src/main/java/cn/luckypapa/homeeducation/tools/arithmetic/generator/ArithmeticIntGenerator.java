package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticInt;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArithmeticIntGenerator implements ArithmeticOperandGenerator{
    private int max;
    private int min;

    public ArithmeticIntGenerator(int max, int min) {
        this.max = max;
        this.min = min;
    }

    @Override
    public List<ArithmeticOperand> generate(int operandNum) {
        Random random = new Random(System.nanoTime());

        List<ArithmeticOperand> operands = new ArrayList<>(operandNum);

        for (int i = 0; i < operandNum; i++) {
            operands.add(new ArithmeticInt(random.nextInt(min, max + 1)));
        }

        return operands;
    }
}
