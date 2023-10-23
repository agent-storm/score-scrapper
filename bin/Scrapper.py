import sys
class Scrapper:
    def __init__(self,argv):
        self.URL = argv[1]
        self.Test()
    def Test(self):
        print("You are in Python Test method right now")
        print(self.URL)
        fi = open(r"score-scrapper\output-files\test.txt","w+")
        fi.write(self.URL)
        fi.close()

Obj = Scrapper(sys.argv)

