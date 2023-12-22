package cn.luckypapa.homeeducation.tools.arithmetic.rules;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

public class ArithmeticDivideValidRule extends ArithmeticValidRule {

    @Override
    public String getType() {
        return "÷";
    }

    @Override
    public ValidResult valid(ArithmeticOperand first, ArithmeticOperand second) {
        if (second.isZero()) return ValidResult.fail("除数不能为0");
        if (first.intValue() % second.intValue() != 0) return ValidResult.fail("除法不能整除");
        return ValidResult.SUCCESS;
    }
}
