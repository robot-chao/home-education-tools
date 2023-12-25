package cn.luckypapa.homeeducation.tools.arithmetic.operand;

public class ArithmeticFraction extends ArithmeticOperand {

    private int numerator;
    private int denominator;

    public ArithmeticFraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public String getOperand() {
        if (this.numerator == 0) return "0";
        if (this.denominator == 1) return String.valueOf(this.numerator);

        return this.numerator + "/" + this.denominator;
    }

    @Override
    public ArithmeticOperand add(ArithmeticOperand addon) {
        ArithmeticFraction fraction = (ArithmeticFraction) addon;
        if (this.denominator == fraction.denominator)
            return new ArithmeticFraction(this.numerator + fraction.numerator, this.denominator);
        return new ArithmeticFraction(
                this.numerator * fraction.denominator + this.denominator * fraction.numerator,
                this.denominator * fraction.denominator);
    }

    @Override
    public ArithmeticOperand subtract(ArithmeticOperand subtrahend) {
        ArithmeticFraction fraction = (ArithmeticFraction) subtrahend;
        if (this.denominator == fraction.denominator)
            return new ArithmeticFraction(this.numerator - fraction.numerator, this.denominator);
        return new ArithmeticFraction(
                this.numerator * fraction.denominator - this.denominator * fraction.numerator,
                this.denominator * fraction.denominator);
    }

    @Override
    public ArithmeticOperand multiple(ArithmeticOperand factor) {
        ArithmeticFraction fraction = (ArithmeticFraction) factor;
        return new ArithmeticFraction(
                this.numerator * fraction.numerator,
                this.denominator * fraction.denominator);
    }

    @Override
    public ArithmeticOperand divide(ArithmeticOperand dividend) {
        ArithmeticFraction fraction = (ArithmeticFraction) dividend;
        return new ArithmeticFraction(
                this.numerator * fraction.denominator,
                this.denominator * fraction.numerator);
    }

    @Override
    public boolean greaterThan(ArithmeticOperand operand) {
        ArithmeticFraction fraction = (ArithmeticFraction) operand;
        if (this.denominator == fraction.denominator) return this.numerator > fraction.numerator;
        return this.numerator * fraction.denominator > fraction.numerator * this.denominator;
    }

    @Override
    public boolean lessThan(ArithmeticOperand operand) {
        ArithmeticFraction fraction = (ArithmeticFraction) operand;
        if (this.denominator == fraction.denominator) return this.numerator < fraction.numerator;
        return this.numerator * fraction.denominator < fraction.numerator * this.denominator;
    }

    @Override
    public boolean isZero() {
        return this.numerator == 0;
    }

    @Override
    public int intValue() {
        return this.numerator / this.denominator;
    }

    @Override
    public long longValue() {
        return this.numerator / this.denominator;
    }

    @Override
    public float floatValue() {
        return this.numerator / this.denominator;
    }

    @Override
    public double doubleValue() {
        return this.numerator / this.denominator;
    }

    @Override
    public String toString() {
        return this.numerator + "/" + this.denominator;
    }
}
