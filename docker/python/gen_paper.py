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
    paperBook.save(path + '.xlsx')


path = sys.argv[1]
paperType = sys.argv[2]

if paperType == '1':
    path += "10以内连加连减"
    genPaper(path)
elif paperType == '2':
    path += "10以内连加连减V2"
    genPaper(path=path, onlyResult=False)
elif paperType == '3':
    path += "20以内连加连减"
    genPaper(path=path, max=20, min=5, onlyResult=True)

print(path)