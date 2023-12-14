import sys
from openpyxl import Workbook
import random
from uitls import write2Excel
from arithmetic_utils import genArithmetics, formatArithmetics

def genPaper(itemCountPerPaper=30, paperCount=2, path="", opNum=2, max=10, min=5, onlyResult=True):
    # 30道10以内连加连减
    paperBook = Workbook()
    paperSheet = paperBook.active
    paperSheet.title = '四则运算'
    allItems = formatArithmetics(genArithmetics(max=max, min=min, opNum=opNum), onlyResult=onlyResult)
    for i in range(paperCount):
        random.shuffle(allItems)
        write2Excel(paperSheet, allItems[:itemCountPerPaper], 3)

    paperSheet.page_margins.bottom=0.8
    paperBook.save(path)

argSize = len(sys.argv)
path = sys.argv[1] + "计算题.xlsx"

paperType = sys.argv[2]
onlyResult = True
if paperType == '2':
    onlyResult = False
opNum = 2
if argSize > 3:
    opNum = int(sys.argv[3])
max=10
if argSize > 4:
    max = int(sys.argv[4])
min=5
if argSize > 5:
    min = int(sys.argv[5])
itemCountPerPaper=30
if argSize > 6:
    itemCountPerPaper = int(sys.argv[6])
paperCount=2
if argSize > 7:
    paperCount = int(sys.argv[7])

genPaper(itemCountPerPaper, paperCount, path, opNum, max, min, onlyResult)

print(path)