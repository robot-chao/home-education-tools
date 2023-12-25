package cn.luckypapa.homeeducation.tools.arithmetic.generator;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArithmeticFractionGeneratorTest {

    @Test
    void generate() {
        ArithmeticFractionGenerator fractionGenerator = new ArithmeticFractionGenerator(1, 20);
        List<ArithmeticOperand> operands = fractionGenerator.generate(10);
        log.info(Arrays.toString(operands.toArray(new Object[0])));
    }
}