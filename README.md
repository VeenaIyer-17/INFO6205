# INFO6205

## Team Information
| Name | NEU ID | Email Address |
| --- | --- | --- |
| Ravi Kiran    | 001439467 | lnu.ra@husky.neu.edu |
| Veena Iyer    | 001447061 | iyer.v@husky.neu.edu|

##Pre-requisites
You need the Java installed in your system

## Description
The project implements Ranking System for the English Premier League.
The EPL is contested by 20 clubs which runs from August to May with each team playing 38 matches 
playing all 19 teams home and away. 
The Ranking System is based on the Wins, Loss and draws between each team. Probability of any two 
teams given as input is also calculated.

## Run Instructions
ReadExcel file is the entry point for execution.
* User will be displayed the teams with a pre defined number attached to each team
* User will input the number for the two teams they wish to check Win probability
* If a valid number from the list is selected and the two numbers are separated by space:
* Output will be displayed which will contain the below -
    * List of home and away team with their wins and losses
    * Rankings of all these teams
    * Win probability
 * The dataset being used if from the playoffs of 2018-2019.
 * To consider the COVID-19 scenario mentioned we have one more excel called coviddataset.xlsx
 * Replacing this file with the earlier dataset will show the Rankings for the teams presently.
 * Replacement can be done in the ReadExcel file at line 193. Screenshot of the data and ranking is
 attached in the Report
 
## Output Verification
As professor has mentioned - Although Liverpool must end the season at the top of
                             the table (it is mathematically impossible for any other team to pass them), 
                             our ranking system draws the same conclusion.