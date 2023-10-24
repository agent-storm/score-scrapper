# score-scrapper
### NOTE: The project is still in progress.
&ensp;&ensp;The main goal of the project is to return a file(s) containing data about the contest held by codechef known as STARTERS. Basically by usuing selenium I can get the contest rankings site and choose my college as the filter, then by using Beautiful Soup (Python) I can get the raw HTML data and process it furhter for my needs, then finally by using xlsxwriter I can store the data in the form of xlsx sheets for each Division of the contest.
## Sample Output:
![image](https://github.com/agent-storm/score-scrapper/assets/117740222/7b5ffb45-e2f1-40f4-978b-775f3fce997b)


## Key Points:
----------------
- Scrapping of all four divisions is possible. [GOTO: Main Method]
-  Each division can be executed on a seperate Thread.
- Default filter options are:
    - By "Institution"
    - "CMR Institute of Technology"
- Data collection is done in java while processing is done in Python.
- Selenium with java is used for automating some steps to go to the desired webpage and apply some settings
- Beautifulsoup-Python is used for converting the raw HTML to useful data and putting it in a XLSX file.

Step by Step detailed explaination of the working can be found with in the code in the form of comments.
### TOOLS USED
- Selenium (Java)
- Beautiful Soup (Python)
- Xlsxwriter
- VS Code (IDE)

### Dependency
- Chrome Driver (Recommended)   ->[Downlaod](https://googlechromelabs.github.io/chrome-for-testing/)
- Selenium Server v4.0 or above ->[Download](https://www.selenium.dev/downloads/)
### Python Requirements
- Beautiful Soup              ->`pip install beautifulsoup4`
- xlsxwriter                  ->`pip install xlsxwriter`
## How to use:
&ensp;&ensp;For now there is no interactive method for using the program but in the future I will add a GUI Interface to make things simple and easy to use. To use the programm as is, you need to go to the main method in the ScoreScrapper.java file and edit the `link` variable to the desired link that only points any START contest ex: `https://codechef.com/START99`
## NOTICE
&ensp;&ensp;You must add the selenium server jar file to the "Referenced Libraries" folder of your Java project.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
