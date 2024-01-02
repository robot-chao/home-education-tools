package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.ArithmeticOperator;

import java.util.List;

public interface ArithmeticOperatorGenerator {


    List<ArithmeticOperator> generate(int operatorNum);

    static ArithmeticOperatorGenerator plusOperatorGenerator() {
        return new SingleTypeOperatorGenerator(ArithmeticOperator.PLUS);
    }

    static ArithmeticOperatorGenerator minusOperatorGenerator() {
        return new SingleTypeOperatorGenerator(ArithmeticOperator.MINUS);
    }

    static ArithmeticOperatorGenerator gradeOneOperatorGenerator() {
        return new RandomOperatorGenerator(List.of(ArithmeticOperator.PLUS, ArithmeticOperator.MINUS));
    }

    static ArithmeticOperatorGenerator gradeThreeOperatorGenerator() {
        return new RandomOperatorGenerator(List.of(
                ArithmeticOperator.PLUS, ArithmeticOperator.MINUS,
                ArithmeticOperator.MULTI, ArithmeticOperator.DIVISION));
    }
}
