package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.tools.arithmetic.generator.*;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticFloat;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticFraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArithmeticBuilder {

    private int opNum = 1;
    private boolean parentheses = false;
    private ArithmeticValidator arithmeticValidator;
    private ArithmeticOperandGenerator arithmeticOperandGenerator;
    private ArithmeticOperatorGenerator arithmeticOperatorGenerator = ArithmeticOperatorGenerator.gradeOneOperatorGenerator();

    public ArithmeticBuilder() {

    }

    public List<Arithmetic> build(int count) {
        Set<Arithmetic> arithmetics = new HashSet<>(count);
        for (int i = 0; i < count;) {
            Arithmetic arithmetic = new Arithmetic(this.opNum,
                    arithmeticOperandGenerator,
                    arithmeticOperatorGenerator, parentheses, arithmeticValidator);
            if (!arithmetics.contains(arithmetic)) {
                arithmetics.add(arithmetic);
                i ++;
            }
        }

        return new ArrayList<>(arithmetics);
    }

    public static ArithmeticBuilder newGradeOneBuilder(int opNum) {
        return newGradeOneBuilder(opNum, 20, 2);
    }

    public static ArithmeticBuilder newGradeOneBuilder(int opNum, int max, int min) {
        ArithmeticBuilder builder = new ArithmeticBuilder();
        builder.opNum = opNum;
        builder.arithmeticOperandGenerator = new ArithmeticIntGenerator(max, min);
        builder.arithmeticValidator = ArithmeticValidator.intValidator(max);
        return builder;
    }

    public static ArithmeticBuilder newGradeOneSingleTypeBuilder(int opNum, ArithmeticOperator operator) {
        return newGradeOneSingleTypeBuilder(opNum, operator, 20, 2);
    }

    public static ArithmeticBuilder newGradeOneSingleTypeBuilder(int opNum, ArithmeticOperator operator,int max, int min) {
        ArithmeticBuilder builder = new ArithmeticBuilder();
        builder.opNum = opNum;
        builder.arithmeticOperandGenerator = new ArithmeticIntGenerator(max, min);
        builder.arithmeticValidator = ArithmeticValidator.intValidator(max);
        builder.arithmeticOperatorGenerator = new SingleTypeOperatorGenerator(operator);
        return builder;
    }
    public static ArithmeticBuilder newGradeFourBuilder(int opNum, int floatCount) {
        ArithmeticBuilder builder = new ArithmeticBuilder();
        builder.opNum = opNum;
        builder.arithmeticOperandGenerator = new ArithmeticFloatGenerator(20, 0, floatCount);
        builder.arithmeticValidator = ArithmeticValidator.floatValidator(new ArithmeticFloat(2000, floatCount));
        return builder;
    }

    public static ArithmeticBuilder newGradeThreeBuilder(int opNum) {
        ArithmeticBuilder builder = new ArithmeticBuilder();
        builder.opNum = opNum;
        builder.arithmeticOperandGenerator = new ArithmeticFractionGenerator(1, 10);
        builder.arithmeticValidator = ArithmeticValidator.fractionValidator(new ArithmeticFraction(2, 1));
        return builder;
    }
}
