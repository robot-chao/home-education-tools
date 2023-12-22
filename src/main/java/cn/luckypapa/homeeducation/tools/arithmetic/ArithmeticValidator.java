package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticFloat;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticInt;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;
import cn.luckypapa.homeeducation.tools.arithmetic.rules.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ArithmeticValidator {
    private Map<String, List<ArithmeticValidRule>> ruleMap = new HashMap<>();

    public ArithmeticValidator(List<ArithmeticValidRule> rules) {
        for (ArithmeticValidRule rule : rules) {
            List<ArithmeticValidRule> ruleList = ruleMap.get(rule.getType());
            if (null == ruleList) {
                ruleList = new ArrayList<>();
                ruleMap.put(rule.getType(), ruleList);
            }
            ruleList.add(rule);
        }
    }

    public boolean validate(String type, ArithmeticOperand first, ArithmeticOperand second) {
        List<ArithmeticValidRule> ruleList = ruleMap.get(type);
        if (CollectionUtils.isEmpty(ruleList)) return true;
        for (ArithmeticValidRule rule : ruleList) {
            ValidResult validResult = rule.valid(first, second);
            if (!validResult.isPass()) {
                log.debug("表达式校验失败, {}", validResult.getMessage());
                return false;
            }
        }

        return true;
    }

    public static ArithmeticValidator intValidator(int max) {
        List<ArithmeticValidRule> rules = new ArrayList<>();
        rules.add(new ArithmeticAddValidRule(new ArithmeticInt(max)));
        rules.add(new ArithmeticSubtractValidRule());
        rules.add(new ArithmeticDivideValidRule());

        return new ArithmeticValidator(rules);
    }

    public static ArithmeticValidator floatValidator(ArithmeticFloat max) {
        List<ArithmeticValidRule> rules = new ArrayList<>();
        rules.add(new ArithmeticAddValidRule(max));
        rules.add(new ArithmeticSubtractValidRule(new ArithmeticFloat(0,0)));
        rules.add(new ArithmeticDivideValidRule());

        return new ArithmeticValidator(rules);
    }
}
