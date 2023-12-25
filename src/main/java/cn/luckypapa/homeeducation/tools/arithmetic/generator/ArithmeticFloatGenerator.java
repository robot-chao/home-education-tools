package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticFloat;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArithmeticFloatGenerator implements ArithmeticOperandGenerator {
    private int max;
    private int min;
    private int floatCount;

    public ArithmeticFloatGenerator(int max, int min, int floatCount) {
        this.max = multiple10(max, floatCount);
        this.min = multiple10(min, floatCount);
        this.floatCount = floatCount;
    }

    private int multiple10(int num, int count) {
        while (count -- > 0) {
            num *= 10;
        }

        return num;
    }

    @Override
    public List<ArithmeticOperand> generate(int operandNum) {
        Random random = new Random(System.nanoTime());

        List<ArithmeticOperand> operands = new ArrayList<>(operandNum);

        for (int i = 0; i < operandNum; i++) {
            operands.add(new ArithmeticFloat(random.nextInt(min, max + 1), floatCount));
        }

        return operands;
    }
}
