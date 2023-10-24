# Still not considered perfect, have some work to do.
import os
import xlsxwriter
from bs4 import BeautifulSoup
class Scrapper:
    def __init__(self):
        self.list_of_files = os.listdir("output-files/temp_files")
        self.FileCreator()
        
    def FileCreator(self):
        os.chdir("output-files")
        for fileName in self.list_of_files:
            newName = self.FileNameGetter(fileName)
            workbook = xlsxwriter.Workbook(f"{newName}.xlsx")
            worksheet = workbook.add_worksheet()
            worksheet.write("A1","RANK")
            worksheet.write("B1","USER-NAME")
            worksheet.write("C1","SCORE")
            raw_file = open(f"temp_files/{fileName}","r")
            soup = BeautifulSoup(raw_file,"lxml")
            ranks_list = soup.find_all("tr")
            lineNum = 2
            for rank in ranks_list:
                td_lis = rank.find_all("td")
                lis = []
                for td in td_lis[:3]:
                    td.div.unwrap()
                    if "Rank" in td.text:
                        lis.append(td.text[4:])
                    elif "Username" in td.text:
                        lis.append(td.text[10:-38])
                    elif "Score" in td.text:
                        lis.append(td.text[11:])
                worksheet.write(f"A{lineNum}",lis[0])
                worksheet.write(f"B{lineNum}",lis[1])
                worksheet.write(f"C{lineNum}",lis[2])
                lineNum+=1

            workbook.close()
                
    def FileNameGetter(self,file):
        if("1" in file):
            return "Div-1"
        elif("2" in file):
            return "Div-2"
        elif("3" in file):
            return "Div-3"
        elif("4" in file):
            return "Div-4"

Obj = Scrapper()


