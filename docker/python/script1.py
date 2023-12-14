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

print(sys.argv)

genPaper(path="/data/application/excel/10以内连加连减")
genPaper(path="/data/application/excel/10以内连加连减V2", onlyResult=False)
genPaper(path="/data/application/excel/20以内连加连减", max=20, min=5, onlyResult=True)