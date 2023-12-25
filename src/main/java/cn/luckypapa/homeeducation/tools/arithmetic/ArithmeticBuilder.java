package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticFloat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArithmeticBuilder {

    private int opNum = 1;
    private ArithmeticOperandType operandType = ArithmeticOperandType.INT;
    private int operatorType = 1;
    private boolean parentheses = false;
    private ArithmeticValidator arithmeticValidator;
    private ArithmeticOperandGenerator arithmeticOperandGenerator;

    public ArithmeticBuilder() {

    }

    public List<Arithmetic> build(int count) {
        Set<Arithmetic> arithmetics = new HashSet<>(count);
        for (int i = 0; i < count;) {
            Arithmetic arithmetic = new Arithmetic(this.opNum,
                    arithmeticOperandGenerator,
                    operatorType, parentheses, arithmeticValidator);
            if (!arithmetics.contains(arithmetic)) {
                arithmetics.add(arithmetic);
                i ++;
            }
        }

        return new ArrayList<>(arithmetics);
    }

    public static ArithmeticBuilder newGradeOneBuilder(int opNum) {
        ArithmeticBuilder builder = new ArithmeticBuilder();
        builder.opNum = opNum;
        builder.arithmeticOperandGenerator = new ArithmeticIntGenerator(20, 5);
        builder.arithmeticValidator = ArithmeticValidator.intValidator(20);
        return builder;
    }

    public static ArithmeticBuilder newGradeFourBuilder(int opNum, int floatCount) {
        ArithmeticBuilder builder = new ArithmeticBuilder();
        builder.opNum = opNum;
        builder.arithmeticOperandGenerator = new ArithmeticFloatGenerator(20, 0, floatCount);
        builder.arithmeticValidator = ArithmeticValidator.floatValidator(new ArithmeticFloat(2000, floatCount));
        return builder;
    }
}
