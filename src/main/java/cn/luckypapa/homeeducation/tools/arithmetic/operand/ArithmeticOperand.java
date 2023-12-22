package cn.luckypapa.homeeducation.tools.arithmetic.operand;

import cn.luckypapa.homeeducation.tools.arithmetic.ArithmeticElement;

public abstract class ArithmeticOperand extends Number implements ArithmeticElement {

    public abstract String getOperand();

    @Override
    public boolean isOperand() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    public abstract ArithmeticOperand add(ArithmeticOperand addon);

    public abstract ArithmeticOperand subtract(ArithmeticOperand subtrahend);

    public abstract ArithmeticOperand multiple(ArithmeticOperand factor);

    public abstract ArithmeticOperand divide(ArithmeticOperand dividend);

    public abstract boolean greaterThan(ArithmeticOperand operand);

    public abstract boolean lessThan(ArithmeticOperand operand);

    public abstract boolean isZero();
}
