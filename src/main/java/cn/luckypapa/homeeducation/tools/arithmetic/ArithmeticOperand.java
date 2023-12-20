package cn.luckypapa.homeeducation.tools.arithmetic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class ArithmeticOperand implements ArithmeticElement {

    private Number operand;

    public ArithmeticOperand(Number operand) {
        this.operand = operand;
    }

    public static List<ArithmeticOperand> random(
            int operandNum, ArithmeticOperandType operandType, Number max, Number min) {
        Random random = new Random(System.nanoTime());
        List<ArithmeticOperand> operands = new ArrayList<>(operandNum);
        if (ArithmeticOperandType.INT.equals(operandType)) {
            for (int i = 0; i < operandNum; i++) {
                operands.add(new ArithmeticOperand(random.nextInt((int) min, (int) max + 1)));
            }
        }
        return operands;
    }

    @Override
    public boolean isOperand() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }
}
