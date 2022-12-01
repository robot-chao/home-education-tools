package cn.luckypapa.homeeducation.tools.arithmetic;

import org.springframework.stereotype.Service;

@Service
public class ArithmeticServiceImpl implements IArithmeticService {

    @Override
    public ArithmeticPaper generate(int opCount, int itemCount) {
        return ArithmeticGenerator.generateArithmeticPaper(itemCount, opCount);
    }
    
}
