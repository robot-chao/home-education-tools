package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.ArithmeticOperator;
import cn.luckypapa.homeeducation.tools.arithmetic.generator.ArithmeticOperatorGenerator;

import java.util.ArrayList;
import java.util.List;

public class SingleTypeOperatorGenerator implements ArithmeticOperatorGenerator {

    private ArithmeticOperator operator;

    public SingleTypeOperatorGenerator(ArithmeticOperator operator) {
        this.operator = operator;
    }

    @Override
    public List<ArithmeticOperator> generate(int operatorNum) {
        List<ArithmeticOperator> operators = new ArrayList<>(operatorNum);
        for (int i = 0; i < operatorNum; i++) {
            operators.add(operator);
        }
        return operators;
    }
}
