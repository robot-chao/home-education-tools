package cn.luckypapa.homeeducation.tools.arithmetic.rules;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticInt;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

public class ArithmeticSubtractValidRule extends ArithmeticValidRule {
    private ArithmeticOperand min = new ArithmeticInt(0);

    public ArithmeticSubtractValidRule() {}

    public ArithmeticSubtractValidRule(ArithmeticOperand operand) {
        this.min = operand;
    }

    @Override
    public String getType() {
        return "-";
    }

    @Override
    public ValidResult valid(ArithmeticOperand first, ArithmeticOperand second) {
        return !first.subtract(second).lessThan(min)
                ? ValidResult.SUCCESS
                : ValidResult.fail("加法结果不能小于" + min.intValue());
    }
}
