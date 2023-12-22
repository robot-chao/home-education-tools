package cn.luckypapa.homeeducation.tools.arithmetic.operand;

public class ArithmeticInt extends ArithmeticOperand {
    private int num;

    public ArithmeticInt(int operand) {
        this.num = operand;
    }

    @Override
    public String getOperand() {
        return String.valueOf(this.num);
    }

    @Override
    public ArithmeticOperand add(ArithmeticOperand addon) {
        return new ArithmeticInt(this.num + addon.intValue());
    }

    @Override
    public ArithmeticOperand subtract(ArithmeticOperand subtrahend) {
        return new ArithmeticInt(this.num - subtrahend.intValue());
    }

    @Override
    public ArithmeticOperand multiple(ArithmeticOperand factor) {
        return new ArithmeticInt(this.num * factor.intValue());
    }

    @Override
    public ArithmeticOperand divide(ArithmeticOperand dividend) {
        return new ArithmeticInt(this.num / dividend.intValue());
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

    @Override
    public String toString() {
        return "ArithmeticInt{" +
                "num=" + num +
                '}';
    }
}
