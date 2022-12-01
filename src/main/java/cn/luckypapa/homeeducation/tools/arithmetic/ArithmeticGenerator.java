package cn.luckypapa.homeeducation.tools.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArithmeticGenerator {

    private static final int DEFAULT_NUM = 20;
    private static final int DEFAULT_OP_COUNT = 2;

    private static final int MAX_NUM = 100;
    private static final int MAX_OP_COUNT = 5;

    private static final Generator[] generators = new Generator[] {
            new DivisionGenerator(), new MinusGenerator(),
            new MultiGenerator(), new PlusGenerator()
    };

    public static ArithmeticPaper generateArithmeticPaper(int num, int opCount) {
        if (num <= 0) num = DEFAULT_NUM;
        if (opCount <= 0) opCount = DEFAULT_OP_COUNT;

        num = Math.min(num, MAX_NUM);
        opCount = Math.min(opCount, MAX_OP_COUNT);

        List<String> arithmetics = new ArrayList<>(num);

        for (int i = 0; i < num; i++) {
            arithmetics.add(generate(opCount) + "=");
        }

        return new ArithmeticPaper(opCount, arithmetics);
    }

    public static String generate(int opCount) {
        Arithmetic arithmetic = null;
        int tryCount = 0;
        while (arithmetic == null && tryCount < 1000) {
            tryCount ++;
            try {
                arithmetic = generate(opCount, -1);
            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }
        }

        // System.out.println("重试次数：" + tryCount);

        printArithmetic(arithmetic);
        return arithmetic.toString();
    }

    private static void printArithmetic(Arithmetic arithmetic) {
        System.out.println(arithmetic.toString() + ", stack:" + arithmetic.toStack() + ", eval:" + arithmetic.eval() );
    }

    private static Arithmetic generate(int opCount, int result) throws Exception {
        if (opCount == 0) {
            return new Arithmetic(result);
        }
        int opIndex = randomNumber(1, opCount);
        Generator generator = getGenerator();
        int[] opNums = -1 == result ? generator.generate() : generator.generate(result);
        if (!check(generator.getOperator(), opNums)) {
            throw new ArithmeticException(opNums[0] + generator.getOperator().getValue() + opNums[1]);
        }

        Arithmetic arithmetic = new Arithmetic(
                generate(opIndex - 1, opNums[0]), generator.getOperator(),
                generate(opCount - opIndex, opNums[1]));

        return arithmetic;
    }

    private static boolean check(ArithmeticOperatorEnum operator, int[] opNums) {
        if (opNums[0] <= 10 || opNums[1] <= 10) {
            return false;
        }

        if (operator.equals(ArithmeticOperatorEnum.DIVISION) && (opNums[1] > 200 || opNums[0] > 30000)) {
            return false;
        }

        return true;
    }

    private static Generator getGenerator() {
        Random random = new Random(System.nanoTime());
        return generators[random.nextInt(4)];
    }

    static int randomNumber(int from, int to) {
        if (from == to) return from;

        Random random = new Random(System.nanoTime());
        return random.nextInt(to - from) + from;
    }

    static int generatePlusNum() {
        return randomNumber(50, 1000);
    }

    static abstract class Generator {
        public abstract ArithmeticOperatorEnum getOperator();
        public abstract int[] generate();
        public abstract int[] generate(int result) throws ArithmeticException;
    }

    static class PlusGenerator extends Generator {

        @Override
        public ArithmeticOperatorEnum getOperator() {
            return ArithmeticOperatorEnum.PLUS;
        }

        public int[] generate() {
            return new int[] {generatePlusNum(), generatePlusNum()};
        }

        public int[] generate(int sum) throws ArithmeticException {
            if (sum >= 10000) throw new ArithmeticException("加法算式的和不能超过10000");
            int num = randomNumber(sum / 3, sum * 2 / 3);
            return new int[] {num, sum - num};
        }
    }

    static class MinusGenerator extends Generator {
        @Override
        public ArithmeticOperatorEnum getOperator() {
            return ArithmeticOperatorEnum.MINUS;
        }

        public int[] generate() {
            int num = randomNumber(80, 1000);
            return new int[] {num, randomNumber(num / 2, num * 4 / 5)};
        }

        public int[] generate(int result) throws ArithmeticException {
            if (result < 5) throw new ArithmeticException("加法的结果不能小于5");
            int num = randomNumber(80, 1000);
            return new int[] {num + result, num};
        }
    }

    static class MultiGenerator extends Generator {

        @Override
        public ArithmeticOperatorEnum getOperator() {
            return ArithmeticOperatorEnum.MULTI;
        }

        public int[] generate() {
            return new int[] {randomNumber(50, 300), randomNumber(50, 300)};
        }

        public int[] generate(int product) throws ArithmeticException {
            Integer[] factors = getFactors(product);

            if (factors.length == 1) throw new ArithmeticException("乘法的结果不能是质数");

            int num1 = 1;

            if (factors.length == 2) {
                num1 = factors[1];
            } else if (factors.length > 2) {
                int count = randomNumber(1, factors.length - 1);

                for (int i = 1; i <= count; i++) {
                    num1 *= factors[i];
                }
            }

            return new int[] {num1, product / num1};
        }

        private static Integer[] getFactors(int target) {
            List<Integer> res = new ArrayList<>();
            res.add(1);
            getFactors(target, res);

            return res.toArray(new Integer[0]);
        }

        private static void getFactors(int target, List<Integer> factors) {
            for (int i = 2; i < Math.sqrt(target); i++) {
                if (target % i == 0) {
                    factors.add(i);
                    getFactors(target / i, factors);
                    break;
                }
            }
        }

    }

    static class DivisionGenerator extends Generator {
        @Override
        public ArithmeticOperatorEnum getOperator() {
            return ArithmeticOperatorEnum.DIVISION;
        }

        public int[] generate() {
            int num1 = randomNumber(20, 100);
            int num2 = randomNumber(20, 100);
            return new int[] {num1 * num2, num1};
        }

        public int[] generate(int result) throws ArithmeticException {
            if (result > 1000) throw new ArithmeticException("除法的结果不能大于1000");
            int num = randomNumber(20, 100);
            return new int[] {num * result, num};
        }
    }

    public static void main(String[] args) {
        // ArithmeticGenerator.generateArithmeticPaper(20, 4);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            ArithmeticGenerator.generate(4);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}
