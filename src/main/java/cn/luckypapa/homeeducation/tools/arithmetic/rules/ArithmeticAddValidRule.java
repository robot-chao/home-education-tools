package cn.luckypapa.homeeducation.tools.arithmetic.rules;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

public class ArithmeticAddValidRule extends ArithmeticValidRule {

    private ArithmeticOperand max;

    public ArithmeticAddValidRule(ArithmeticOperand max) {
        this.max = max;
    }

    @Override
    public String getType() {
        return "+";
    }

    @Override
    public ValidResult valid(ArithmeticOperand first, ArithmeticOperand second) {
        return !first.add(second).greaterThan(max)
                ? ValidResult.SUCCESS
                : ValidResult.fail("加法结果不能大于最大值" + max.intValue());
    }
}
