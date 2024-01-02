package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.ArithmeticOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomOperatorGenerator implements ArithmeticOperatorGenerator {

    private List<ArithmeticOperator> operators;

    public RandomOperatorGenerator(List<ArithmeticOperator> operators) {
        this.operators = operators;
    }

    @Override
    public List<ArithmeticOperator> generate(int operatorNum) {
        int max = this.operators.size();

        Random random = new Random(System.nanoTime());
        List<ArithmeticOperator> operators = new ArrayList<>(operatorNum);
        for (int i = 0; i < operatorNum; i++) {
            int index = random.nextInt(max);
            operators.add(this.operators.get(index));
        }

        return operators;
    }
}
