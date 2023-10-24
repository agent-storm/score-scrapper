# Still not considered perfect, have some work to do.
'''
    We use BeautifulSoup for processing the raw HTML data collected by the ScoreScrapper.java file, the processed data
is then stored in a xlsx file using xlsxwriter in python.

'''
import os
import xlsxwriter
from bs4 import BeautifulSoup
class Scrapper:
    # Initialsing the path of raw_html files stored in temp_files folder
    def __init__(self):
        self.list_of_files = os.listdir("output-files/temp_files")
        self.FileCreator()
    # The main method that creates a new excel sheet for each division.    
    def FileCreator(self):
        # change the current working dir to output-files folder
        os.chdir("output-files")
        # for every raw_html file we do the following actions.
        for fileName in self.list_of_files:
            #Get the respective name of the file by calling FileNameGetter() method
            newName = self.FileNameGetter(fileName)
            # Create a new xlsx file
            workbook = xlsxwriter.Workbook(f"{newName}.xlsx")
            worksheet = workbook.add_worksheet()
            # Add headers for the new file.
            worksheet.write("A1","COLLEGE-RANK")
            worksheet.write("B1","CONTEST-RANK")
            worksheet.write("C1","USER-NAME")
            worksheet.write("D1","SCORE")
            #Open the HTML file.
            raw_file = open(f"temp_files/{fileName}","r")
            # Using Beautiful soup we can process the data.
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
                # Add the collected data into the worksheet/xlsx file.
                worksheet.write(f"A{lineNum}",str(lineNum-1))
                worksheet.write(f"B{lineNum}",lis[0])
                worksheet.write(f"C{lineNum}",lis[1])
                worksheet.write(f"D{lineNum}",lis[2])
                lineNum+=1
            # After finishing one file close the xlsx file to save it.
            workbook.close()
    # This method returns the respectve name of the new Folder.         
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


