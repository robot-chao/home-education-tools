def genAddition(sum, min=0):
    arithmetics = []
    for addend in range(min, sum):
        # arithmetics.append(((addend, sum - addend),'+'))
        arithmetics.append(Arithmetic.arithmetic(addend, sum - addend, sum, '+'))
            
    return arithmetics

def genSubtraction(diff, max=10):
    arithmetics = []
    for sum in range(diff, max + 1):
        # arithmetics.append(((sum, sum - diff), '-'))
        arithmetics.append(Arithmetic.arithmetic(sum, sum - diff, diff, '-'))
    return arithmetics

def genArithmeticByResult(result, max=10, min=0, type='BOTH', opNum=1):
    arithmetics = []
    if 'BOTH' == type or 'ADD' == type:
        arithmetics += genAddition(result, min)

    if 'BOTH' == type or 'SUB' == type:
        arithmetics += genSubtraction(result, max)
    
    if opNum <= 1:
        return arithmetics
    
    opNum -= 1
    allArithmetics = []
    for arithmetic in arithmetics:
        # print(arithmetic)
        for ar in genArithmeticByResult(arithmetic.getFirst().getNum(), max, min, type, opNum):
            allArithmetics.append(arithmetic.updateFirst(ar))

        # 只有加法才能根据第二个数继续差分，否则需要括号
        if arithmetic.getOp() == '+':
            for ar in genArithmeticByResult(arithmetic.getSecond().getNum(), max, min, type, opNum):
                allArithmetics.append(arithmetic.updateSecond(ar))

    return allArithmetics


class Arithmetic():
    def __init__(self, isNumberic):
        self.__isNumberic = isNumberic

    @classmethod
    def numberic(cls, num):
        arithmetic = cls(True)
        arithmetic.__num = num

        return arithmetic
    
    @classmethod
    def wrap(cls, ar):
        if isinstance(ar, Arithmetic):
            return ar
        return Arithmetic.numberic(ar)

    @classmethod
    def arithmetic(cls, first, second, result, op):
        arithmetic = cls(False)
        arithmetic.__first = Arithmetic.wrap(first)
        arithmetic.__second = Arithmetic.wrap(second)
        arithmetic.__result = Arithmetic.wrap(result)
        arithmetic.__op = op

        return arithmetic

    def updateFirst(self, first):
        if self.__isNumberic:
            return self
        
        return Arithmetic.arithmetic(first, self.__second, self.__result, self.__op)
    
    def updateSecond(self, second):
        if self.__isNumberic:
            return self
        
        return Arithmetic.arithmetic(self.__first, second, self.__result, self.__op)

    def getFirst(self):
        return self.__first
    
    def getSecond(self):
        return self.__second
    
    def getOp(self):
        return self.__op
    
    def getResult(self):
        return self.__result
    
    def getNum(self):
        return self.__num
    
    def getAllNums(self):
        nums = []
        if self.__isNumberic:
            nums.append(self.__num)
        else:
            nums += self.__first.getAllNums()
            nums += self.__second.getAllNums()
        
        return nums
    
    def getAllOps(self):
        ops = []
        if not self.__isNumberic:
            ops += self.__first.getAllOps()
            ops.append(self.__op)
            ops += self.__second.getAllOps()
        
        return ops
    
    def format(self, printResult=False):
        if self.__isNumberic:
            return str(self.__num)
        
        if printResult:
            return '{} {} {} = {}'.format(self.__first.format(), self.__op, self.__second.format(), self.__result.format())
        return '{} {} {}'.format(self.__first.format(), self.__op, self.__second.format())
    
    def __str__(self):
        if self.__isNumberic:
            return str(self.__num)
        return self.format(True)

# 生成所有的算术式
def genArithmetics(max=10, min=0, type='BOTH', opNum=1):
    allArithmetics = []
    for sum in range(min, max + 1):
        allArithmetics += genArithmeticByResult(sum, max, min, type, opNum)
    return allArithmetics

# 将给定的算术式格式为包含空白的字符串
def formatArithmetics(arithmetics, onlyResult=True):
    formatArithmetics = []
    for arithmetic in arithmetics:
        if onlyResult:
         formatArithmetics.append('{} = (    )'.format(arithmetic.format()))
        else:
            nums = arithmetic.getAllNums()
            nums += arithmetic.getResult().getAllNums()
            ops = arithmetic.getAllOps()
            ops.append('=')
            ops += arithmetic.getResult().getAllOps()

            for i in range(0, len(nums)):
                s = ''
                for j in range(0, len(nums)):
                    if i == j:
                        s += '(    )'
                    else:
                        s += str(nums[j])
                    if j < len(ops):
                        s += ' ' + ops[j] + ' '
                formatArithmetics.append(s)

    return formatArithmetics