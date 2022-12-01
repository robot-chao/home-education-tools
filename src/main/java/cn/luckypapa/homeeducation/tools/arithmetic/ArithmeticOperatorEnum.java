package cn.luckypapa.homeeducation.tools.arithmetic;

public enum ArithmeticOperatorEnum {
    PLUS("+"), MINUS("-"), MULTI("x"),DIVISION("÷");

    private String value;

    ArithmeticOperatorEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
