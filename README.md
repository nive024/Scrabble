# Scrabble Game

## Table of Contents

1. [Project's Objectives](#Project's-Objectives)
2. [Known Issues](#Known-Issues)
3. [Roadmap Ahead](#Roadmap-Ahead)
4. [Authors](#Authors)

<!-- Project's Objectives -->
## Project's Objectives

The current project has finished Milestone 2. Throughout the project, the objective of the first milestone is to create a text-based playable version of Scrabble. The
second milestone's objective is to make a GUI-based version of the game. The third milestone's
objective is to add additional features. Finally, the fourth milestone's objective is to add more
features.


<!-- Known Issues -->
## Known Issues
These are the known issues that will be fixed in Milestone 3:
* There are no multiplier tiles on the board.
* Placing words is sometimes seen as floating (should be fixed)
* Sometimes we get the not in center square error even though it is (fixed)
* Clicking the player tile and then multiple grid tiles changes the tile to that letter (fixed)
* We need to disable other players tile when its not their turn
* need to add a skip turn button
* some placements cause index out of bounds (should be fixed)
* need to check to select a letter before placing on the grid (fixed)
* need to check if a letter already exists on the board when player tries to click it (fixed)
* JOptionPane if invalid play (fixed)
* catch exception if end game is clicked before placing letters 
* Still issues accessing the rows 10 and above (for example placing the first word in a row greater than 10 will throw an outof bounds exception) (fixed)
* update the score on the GUI
* if overlapping letter is the first letter of the new word, it causes an overlapping error 
* can place letters all over the board, no check to make sure its vertical or horizontal

<!-- Roadmap Ahead -->
## Roadmap Ahead
For the next milestone, more features will be added. To begin, all test cases will pass. Thus, 
the scoring as well as placements of the letters work correctly. Furthermore,
blank tiles, premium squares and an AI will be implemented. Finally, any issues in Milestone 2
will be addressed and fixed. 

<!-- Authors -->
## Authors
* Nivetha Sivasaravanan
* Isaiah Hunte
* Rimsha Atif
* Nicole Lim
