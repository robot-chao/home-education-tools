package cn.luckypapa.homeeducation.tools.arithmetic;

import lombok.Getter;

@Getter
public class Arithmetic {
    public static final String OP_PLUS = "+";

    public static final String OP_MINUS = "-";

    public static final String OP_MULTI = "x";

    public static final String OP_DIVISION = "÷";

    private boolean isNum = false;

    private int value;

    private ArithmeticOperatorEnum operator;

    private Arithmetic first;

    private Arithmetic second;

    public Arithmetic(Arithmetic first, ArithmeticOperatorEnum operator, Arithmetic second) {
        this.first = first;
        this.operator = operator;
        this.second = second;
    }

    public Arithmetic(int value) {
        this.isNum = true;
        this.value = value;
    }

    @Override
    public String toString() {
        if (isNum) {
            return value + "";
        }

        String firstStr = first.toString();
        if (!first.isNum &&
                (first.operator.equals(ArithmeticOperatorEnum.PLUS)
                        || first.operator.equals(ArithmeticOperatorEnum.MINUS))) {
            firstStr = "(" + firstStr + ")";
        }
        String secondStr = second.toString();
        if (!second.isNum && !second.operator.equals(ArithmeticOperatorEnum.MULTI)) {
            secondStr = "(" + secondStr + ")";
        }

        return firstStr + operator.getValue() + secondStr;
    }

    public StringBuilder toStack() {
        if (isNum) {
            return new StringBuilder().append(this.value);
        }

        return new StringBuilder(operator.getValue())
                .append("[")
                .append(first.toStack())
                .append(",")
                .append(second.toStack())
                .append("]");
    }

    public int eval() {
        if (isNum) return this.value;

        switch (operator) {
            case PLUS -> {return first.eval() + second.eval();}
            case MINUS -> {return first.eval() - second.eval();}
            case MULTI -> {return first.eval() * second.eval();}
            default -> {
                if (first.eval() % second.eval() != 0) {
                    System.out.println("值计算出错");
                }
                return first.eval() / second.eval();
            }
        }
    }
}
