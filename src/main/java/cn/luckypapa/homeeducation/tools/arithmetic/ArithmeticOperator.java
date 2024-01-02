package cn.luckypapa.homeeducation.tools.arithmetic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class ArithmeticOperator implements ArithmeticElement {
    public static final ArithmeticOperator PLUS = new ArithmeticOperator("+", 0);
    public static final ArithmeticOperator MINUS = new ArithmeticOperator("-", 0);
    public static final ArithmeticOperator MULTI = new ArithmeticOperator("x", 1);
    public static final ArithmeticOperator DIVISION = new ArithmeticOperator("÷", 1);

    public static final ArithmeticOperator LEFT_PARENTHESIS = new ArithmeticOperator("(", -1);

    public static final ArithmeticOperator RIGHT_PARENTHESIS = new ArithmeticOperator(")", -1);

    private static final List<ArithmeticOperator> CALC_OPERATORS = List.of(PLUS, MINUS, MULTI, DIVISION);

    private String operator;

    private int priority;

    private ArithmeticOperator(String operator, int priority) {
        this.operator = operator;
        this.priority = priority;
    }

    public static List<ArithmeticOperator> random(int opNum, int operatorType) {
        int max = CALC_OPERATORS.size();
        if (1 == operatorType) {
            max = 2;
        }

        Random random = new Random(System.nanoTime());
        List<ArithmeticOperator> operators = new ArrayList<>(opNum);
        for (int i = 0; i < opNum; i++) {
            int index = random.nextInt(max);
            operators.add(CALC_OPERATORS.get(index));
        }

        return operators;
    }

    @Override
    public boolean isOperand() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return true;
    }
}
