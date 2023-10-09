import sys
class Scrapper:
    def __init__(self,argv):
        self.URL = argv[1]
    def Test(self):
        print("You are in Python right now")
        print(self.URL)
        fi = open(r"score-scrapper\output-files\test.txt")
        fi.write("Hello world, this workd.")
        fi.close()

Obj = Scrapper(sys.argv)
