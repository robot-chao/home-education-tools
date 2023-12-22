package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticInt;
import cn.luckypapa.homeeducation.tools.arithmetic.operand.ArithmeticOperand;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Getter
@Slf4j
public class Arithmetic {
    public static final String OP_PLUS = "+";

    public static final String OP_MINUS = "-";

    public static final String OP_MULTI = "x";

    public static final String OP_DIVISION = "÷";

    public static final int MAX_RETRY_COUNT = 10000;

    private List<ArithmeticElement> elements = new ArrayList<>();

    private List<ArithmeticOperand> operands;

    private List<ArithmeticOperator> operators;

    private int opNum;

    private boolean valid;

    private ArithmeticOperand result;

    private Number max = 20;

    private Number min = 5;

    public Arithmetic(int opNum, ArithmeticOperandGenerator operandGenerator,
                      int operatorType, boolean parentheses, ArithmeticValidator arithmeticValidator) {
        int retry = 0;
        while (!this.valid && retry ++ < MAX_RETRY_COUNT) {
            this.operands = operandGenerator.generate(opNum + 1);
            this.operators = ArithmeticOperator.random(opNum, operatorType);
            if (parentheses)    this.insertParentheses();
            log.debug("expression: {}", this);
            this.calcAndCheckArithmetic(arithmeticValidator);
        }

        if (retry >= MAX_RETRY_COUNT) {
            log.error("超过最大重试次数");
            throw new RuntimeException("超过最大重试次数");
        }
    }

    public Arithmetic(String arithmetic) {
        this.operators = new ArrayList<>();
        this.operands = new ArrayList<>();
        int len = arithmetic.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char ch = arithmetic.charAt(i);
            switch (ch) {
                case '+':
                    this.operators.add(ArithmeticOperator.PLUS);
                    if (!builder.isEmpty()) {
                        this.operands.add(new ArithmeticInt(Integer.parseInt(builder.toString())));
                        builder.delete(0, builder.length());
                    }
                    break;
                case '-':
                    this.operators.add(ArithmeticOperator.MINUS);
                    if (!builder.isEmpty()) {
                        this.operands.add(new ArithmeticInt(Integer.parseInt(builder.toString())));
                        builder.delete(0, builder.length());
                    }
                    break;
                case 'x':
                    this.operators.add(ArithmeticOperator.MULTI);
                    if (!builder.isEmpty()) {
                        this.operands.add(new ArithmeticInt(Integer.parseInt(builder.toString())));
                        builder.delete(0, builder.length());
                    }
                    break;
                case '÷':
                    this.operators.add(ArithmeticOperator.DIVISION);
                    if (!builder.isEmpty()) {
                        this.operands.add(new ArithmeticInt(Integer.parseInt(builder.toString())));
                        builder.delete(0, builder.length());
                    }
                    break;
                case '(':
                    this.operators.add(ArithmeticOperator.LEFT_PARENTHESIS);
                    if (!builder.isEmpty()) {
                        this.operands.add(new ArithmeticInt(Integer.parseInt(builder.toString())));
                        builder.delete(0, builder.length());
                    }
                    break;
                case ')':
                    this.operators.add(ArithmeticOperator.RIGHT_PARENTHESIS);
                    if (!builder.isEmpty()) {
                        this.operands.add(new ArithmeticInt(Integer.parseInt(builder.toString())));
                        builder.delete(0, builder.length());
                    }
                    break;
                default:
                    builder.append(ch);
                    break;
            }
        }
        if (!builder.isEmpty()) {
            this.operands.add(new ArithmeticInt(Integer.parseInt(builder.toString())));
            builder.delete(0, builder.length());
        }
        this.calcAndCheckArithmetic(ArithmeticValidator.intValidator(20));
    }

    private void calcAndCheckArithmetic(ArithmeticValidator arithmeticValidator) {
        Deque<ArithmeticElement> postfixExpression = toPostfixExpression();
        Stack<ArithmeticOperand> stack = new Stack<>();
        while (!postfixExpression.isEmpty()) {
            ArithmeticElement curElement = postfixExpression.removeFirst();

            if (curElement instanceof ArithmeticOperand) {
                stack.push(((ArithmeticOperand) curElement));
            } else {
                char ch = ((ArithmeticOperator) curElement).getOperator().charAt(0);
                ArithmeticOperand second = stack.pop();
                ArithmeticOperand first = stack.pop();
                if (!arithmeticValidator.validate(String.valueOf(ch), first, second)) {
                    return;
                }

                switch (ch) {
                    case '+':
                        stack.push(first.add(second));
                        break;
                    case '-':
                        stack.push(first.subtract(second));
                        break;
                    case 'x':
                        stack.push(first.multiple(second));
                        break;
                    case '÷':
                        stack.push(first.divide(second));
                        break;
                }
            }
        }
        this.result = new ArithmeticInt(stack.pop().intValue());
        this.valid = true;
        log.debug("result: {}", this.result.getOperand());
    }

    private void insertParentheses() {
        // (1 + 2 + 3 + 4) 不可以
        // 1 + (2) 不可以
        // 1 + ((2 + 3)) + 4 不可以
        
        Random random = new Random(System.nanoTime());
        int parenthesesNum = random.nextInt(this.operators.size());

        for (int i = 0; i < parenthesesNum; i++) {
            List<Integer> availableLeftParenthesisPos = getAvailableLeftParenthesisPos();
            int leftSize = availableLeftParenthesisPos.size();
            if (leftSize == 0) {
                continue;
            }
            int leftPos = availableLeftParenthesisPos.get(random.nextInt(leftSize));

            List<Integer> availableRightParenthesisPos = getAvailableRightParenthesisPos(leftPos);
            int rightSize = availableRightParenthesisPos.size();
            if (rightSize == 0) {
                continue;
            }

            int rightPos = availableRightParenthesisPos.get(random.nextInt(rightSize));

            this.operators.add(rightPos, ArithmeticOperator.RIGHT_PARENTHESIS);
            this.operators.add(leftPos, ArithmeticOperator.LEFT_PARENTHESIS);
            log.debug("{}, left: {}, right: {}, avl:{}, avr:{}", toString(), leftPos, rightPos,
                    Arrays.toString(availableLeftParenthesisPos.toArray(new Integer[0])),
                    Arrays.toString(availableRightParenthesisPos.toArray(new Integer[0])));
        }
    }

    private List<Integer> getAvailableLeftParenthesisPos() {
        List<Integer> poses = new ArrayList<>();
        for (int i = 0; i < this.operators.size() - 1; i++) {
            // 当前位置为右括号，则它的前后均不能放左括号
            if (!isRightParenthesis(i) && !isRightParenthesis(i - 1)) {
                poses.add(i);
            }
        }

        return poses;
    }

    private List<Integer> getAvailableRightParenthesisPos(int start) {
        List<Integer> poses = new ArrayList<>();
        boolean isStartAtLeftParenthesis = isLeftParenthesis(start);
        int leftParenthesisNum = isStartAtLeftParenthesis ? 1 : 0;
        int size = this.operators.size();
        for (int i = start + 1; i < size; i++) {
            boolean isRightParenthesis = isRightParenthesis(i);
            if (isRightParenthesis) {
                leftParenthesisNum --;
            } else if (isLeftParenthesis(i)) {
                leftParenthesisNum ++;
            }

            if (leftParenthesisNum < 0) {
                break;
            }

            if (0 == leftParenthesisNum &&
                    // 插入的位置不能有左括号
                    !isLeftParenthesis(i + 1)
                    && !isUselessParenthesis(start, i + 1)) {
                poses.add(i + 1);
            }
        }

        return poses;
    }

    private boolean isUselessParenthesis(int left, int right) {
        int size = this.operators.size();

        // 插入的左括号在开头的时候，右括号不能在最后
        if (0 == left && size == right) {
            return true;
        }

        // 插入的左括号位于左括号之前，则新的右括号不能位于右括号之后
        if (isLeftParenthesis(left)
                && isRightParenthesis(right - 1)) {
            return true;
        }

        // 插入的左括号位于左括号之后，则新的右括号不能位于右括号之前
        if (isLeftParenthesis(left - 1)
                && isRightParenthesis(right)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        List<ArithmeticElement> elementList = mergeElements();

        for (int i = 0; i < elementList.size(); i++) {
            ArithmeticElement element = elementList.get(i);
            if (element instanceof ArithmeticOperand) {
                builder.append(((ArithmeticOperand) element).getOperand());
            } else {
                builder.append(((ArithmeticOperator) element).getOperator());
            }
        }

        builder.append("=");
        return builder.toString();
    }

    private List<ArithmeticElement> mergeElements() {
        int operand = 0, operator = 0;
        List<ArithmeticElement> elementList =
                new ArrayList<>(this.operands.size() + this.operators.size());
        boolean isNextOperand = !isLeftParenthesis(0);

        int total = this.operands.size() + this.operators.size();
        for (int i = 0; i < total; i++) {
            if (isNextOperand) {
                elementList.add(this.operands.get(operand));
                operand ++;
                isNextOperand = false;
            } else {
                ArithmeticOperator curOperator = this.operators.get(operator);
                elementList.add(this.operators.get(operator));
                operator ++;
                // 下一个操作符是左括号，继续拼接操作符
                // 当前操作符是右括号，继续拼接操作符
                isNextOperand = !isLeftParenthesis(operator) && !isRightParenthesis(curOperator);
            }
        }

        return elementList;
    }
    private boolean isLeftParenthesis(int index) {
        return index >= 0 && index < this.operators.size() && ArithmeticOperator.LEFT_PARENTHESIS == this.operators.get(index);
    }

    private boolean isRightParenthesis(ArithmeticOperator operator) {
        return ArithmeticOperator.RIGHT_PARENTHESIS == operator;
    }

    private boolean isRightParenthesis(int index) {
        return index >= 0 && index < this.operators.size() && ArithmeticOperator.RIGHT_PARENTHESIS == this.operators.get(index);
    }

    public Deque<ArithmeticElement> toPostfixExpression() {
        Deque<ArithmeticOperator> stack1 = new LinkedList<>();
        Deque<ArithmeticElement> stack2 = new LinkedList<>();

        List<ArithmeticElement> elementList = mergeElements();

        for (int i = 0; i < elementList.size(); i++) {
            ArithmeticElement element = elementList.get(i);
            if (element instanceof ArithmeticOperand) {
                stack2.addLast(element);
            } else {
                ArithmeticOperator curOperator = (ArithmeticOperator) element;
                // stack1为空 或者 遇到左括号
                if (stack1.isEmpty() || stack1.peekLast().equals(ArithmeticOperator.LEFT_PARENTHESIS)) {
                    stack1.addLast(curOperator);
                } else if (curOperator.equals(ArithmeticOperator.RIGHT_PARENTHESIS)) {
                    while (!stack1.isEmpty()) {
                        ArithmeticOperator topOperator = stack1.removeLast();
                        if (topOperator.equals(ArithmeticOperator.LEFT_PARENTHESIS)) {
                            break;
                        }
                        stack2.addLast(topOperator);
                    }
                } else if (curOperator.equals(ArithmeticOperator.LEFT_PARENTHESIS)) {
                    stack1.addLast(curOperator);
                } else {
                    ArithmeticOperator topOperator = stack1.peekLast();
                    if (curOperator.getPriority() > topOperator.getPriority()) {
                        stack1.addLast(curOperator);
                    } else {
                        stack2.addLast(stack1.removeLast());
                        i --;
                    }
                }
            }
        }

        while (!stack1.isEmpty()) {
            stack2.addLast(stack1.removeLast());
        }

        return stack2;
    }
}
