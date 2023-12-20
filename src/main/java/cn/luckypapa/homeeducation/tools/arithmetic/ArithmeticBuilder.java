package cn.luckypapa.homeeducation.tools.arithmetic;

import java.util.ArrayList;
import java.util.List;

public class ArithmeticBuilder {

    private int opNum = 1;
    private ArithmeticOperandType operandType = ArithmeticOperandType.INT;
    private int operatorType = 1;
    private boolean parentheses = false;
    private Number max = 20;
    private Number min = 5;

    public ArithmeticBuilder() {

    }

    public List<Arithmetic> build(int count) {
        List<Arithmetic> arithmetics = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            arithmetics.add(new Arithmetic(this.opNum, operandType, operatorType, parentheses, max, min));
        }

        return arithmetics;
    }

    public static ArithmeticBuilder newGradeOneBuilder(int opNum) {
        ArithmeticBuilder builder = new ArithmeticBuilder();
        builder.opNum = opNum;
        return builder;
    }
}
