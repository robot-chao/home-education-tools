package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;

import java.util.List;

public interface ArithmeticOperandGenerator {

    List<ArithmeticOperand> generate(int operandNum);
}
