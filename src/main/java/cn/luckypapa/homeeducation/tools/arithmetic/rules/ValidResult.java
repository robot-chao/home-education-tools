package cn.luckypapa.homeeducation.tools.arithmetic.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidResult {
    public static final ValidResult SUCCESS = new ValidResult(true, "success");

    private boolean pass;
    private String message;

    public static ValidResult fail(String message) {
        return new ValidResult(false, message);
    }
}
