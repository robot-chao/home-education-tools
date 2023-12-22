package cn.luckypapa.homeeducation.tools.arithmetic.operand;

import lombok.ToString;

@ToString
public class ArithmeticFloat extends ArithmeticOperand {
    private int num;
    private int floatCount;
    private String operand = "0";

    public ArithmeticFloat(int num, int floatCount) {
        this.num = num;
        this.floatCount = floatCount;
        if (num != 0) {
            StringBuilder builder = new StringBuilder();
            while (floatCount -- > 0) {
                builder.append(num % 10);
                num /= 10;
            }
            this.operand = num + "." + builder.reverse().toString();
        }
    }

    @Override
    public String getOperand() {
        return this.operand;
    }

    @Override
    public ArithmeticOperand add(ArithmeticOperand addon) {
        return new ArithmeticFloat(this.num + addon.intValue(), this.floatCount);
    }

    @Override
    public ArithmeticOperand subtract(ArithmeticOperand subtrahend) {
        return new ArithmeticFloat(this.num - subtrahend.intValue(), this.floatCount);
    }

    @Override
    public ArithmeticOperand multiple(ArithmeticOperand factor) {
        return new ArithmeticFloat(this.num * factor.intValue(), this.floatCount);
    }

    @Override
    public ArithmeticOperand divide(ArithmeticOperand dividend) {
        return new ArithmeticFloat(this.num / dividend.intValue(), this.floatCount);
    }

    @Override
    public boolean greaterThan(ArithmeticOperand operand) {
        return this.num > operand.intValue();
    }

    @Override
    public boolean lessThan(ArithmeticOperand operand) {
        return this.num < operand.intValue();
    }

    @Override
    public boolean isZero() {
        return this.num == 0;
    }

    @Override
    public int intValue() {
        return this.num;
    }

    @Override
    public long longValue() {
        return this.num;
    }

    @Override
    public float floatValue() {
        return this.num;
    }

    @Override
    public double doubleValue() {
        return this.num;
    }
}
