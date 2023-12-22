package cn.luckypapa.homeeducation.tools.arithmetic.rules;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

public abstract class ArithmeticValidRule {

    public abstract String getType();

    public abstract ValidResult valid(ArithmeticOperand first, ArithmeticOperand second);
}
