'''
        This is just a quick and easy way to delete all the files in the output-files folder, really use ful when you are doing multiple 
    execution tests.
'''

import os
class Cleaner:
    def __init__(self):
        os.chdir("../")
        self.output_path = "output-files"
        self.temp_path = "temp_files"
        self.Delete()
    def Delete(self):
        os.chdir(self.output_path)
        lis = os.listdir()
        for file in lis:
            if "Div" in file:
                os.system(f"del {file}")
        os.chdir(self.temp_path)
        lis = os.listdir()
        for file in lis:
            if "div" in file:
                os.system(f"del {file}")
        print("------------CLEANING FINISHED------------")

obj = Cleaner()