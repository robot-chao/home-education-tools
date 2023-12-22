package cn.luckypapa.homeeducation.tools.arithmetic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArithmeticBuilderTest {

    @Test
    void build() {
        List<Arithmetic> arithmetics = ArithmeticBuilder.newGradeOneBuilder(1).build(30);
        for (Arithmetic arithmetic : arithmetics) {
            log.info(arithmetic.toString());
        }

        arithmetics = ArithmeticBuilder.newGradeOneBuilder(2).build(30);
        for (Arithmetic arithmetic : arithmetics) {
            log.info(arithmetic.toString());
        }
    }

    @Test
    void testFloatBuilder() {
        List<Arithmetic> arithmetics = ArithmeticBuilder.newGradeFourBuilder(2, 2).build(30);
        for (Arithmetic arithmetic : arithmetics) {
            log.info(arithmetic.toString());
        }
    }
}