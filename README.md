# Scrabble Game

## Table of Contents

1. [Project's Objectives](#Project's-Objectives)
2. [Known Issues](#Known-Issues)
3. [Roadmap Ahead](#Roadmap-Ahead)
4. [Authors](#Authors)

<!-- Project's Objectives -->
## Project's Objectives

The current project has finished Milestone 3. Throughout the project, the objective of the first milestone is to create a text-based playable version of Scrabble. The
second milestone's objective is to make a GUI-based version of the game. The third milestone's
objective is to add additional features. The additional features that have been added on this milestone is an AI, and tile multipliers. Finally, the fourth milestone's objective is to add more
features.


<!-- Known Issues -->
## Known Issues
These are the known issues that will be fixed in Milestone 4:
* There are no multiplier tiles on the board.
* Some placements may cause index out of bounds (i think fixed)
* If letters are placed all over the board (like adding to multiple words at once) the logic will break and might be counted as floating. (should be fixed)
* If multiple words are created and one of the words are invalid it might not remove all the newly placed letters. (fixed)
* If the word is invalid, it will remove the overlapping letter too. (fixed)
* If the overlapping letter is not clicked, and the word is invalid, it will remove the wrong number of letters (it is in the rules that the overlapping letter should be clicked). (fixed)
* Sometimes the error message contains the wrong word/missing letters if player chooses to overlap letters but doesn't click the overlapping letter. (fixed)
* if multiple wrong words are placed then only one gets removed (other letters are left). (fixed)
* Sometimes we'll get an error saying we didn't place any letters even if we did.
* Skip turn doesn't ask if they want to discard any or all letters and provide new ones 
* Some word get an error saying it doesn't fit there even if it does.

NOTE: These issues are mainly regarding the GUI so there will not be any tests that fail, since our tests are only for the model. 

<!-- Roadmap Ahead -->
## Roadmap Ahead
For the next milestone, more features will be added. To begin, a multiple level redo/undo will be available
to allow players to convert back and forth to and from different versions of the board throughout the game. 
Furthermore, the players will be allowed to save and reload their game.
Finally, the board will be customizable meaning that the special tiles can be placed according to the players preference.


<!-- Authors -->
## Authors
* Nivetha Sivasaravanan
* Isaiah Hunte
* Rimsha Atif
* Nicole Lim
