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
* Some placements may cause index out of bounds 
* If letters are placed all over the board (like adding to multiple words at once) the logic will break and might be counted as floating. 
* If multiple words are created and one of the words are invalid it might not remove all the newly placed letters. 
* If the word is invalid, it will remove the overlapping letter too.
* If the overlapping letter is not clicked, and the word is invalid, it will remove the wrong number of letters (it is in the rules that the overlapping letter should be clicked).
* Sometimes the error message contains the wrong word/missing letters if player chooses to overlap letters but doesn't click the overlapping letter. 
* if multiple wrong words are placed then only one gets removed (other letters are left).
* Sometimes we'll get an error saying we didn't place any letters even if we did.
* Skip turn doesn't ask if they want to discard any or all letters and provide new ones 
* Some word get an error saying it doesn't fit there even if it does.

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
