# score-scrapper
### NOTE: The project is still in progress, just completed a demo of selenium automation, still need to start on Beautiful Soup.
&ensp;&ensp;The main goal of the project is to return a file containing data about the contest held by codechef known as STARTERS. Basically by usuing selenium I can get the contest rankings site and choose my college as the filter, then by using Beautiful Soup (Python) I can get the raw HTML data and process it furhter for my needs.

### TOOLS USED
- Selenium (Java)
- Beautiful Soup (Python)
- VS Code (IDE)

### Dependency
- Chrome Driver (Recommended)   ->[Downlaod](https://googlechromelabs.github.io/chrome-for-testing/)
- Selenium Server v4.0 or above ->[Download](https://www.selenium.dev/downloads/)
## NOTICE
  You must add the selenium server jar file to the "Referenced Libraries" folder of your Java project.
  
## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
