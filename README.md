# MineSweeper-Game
Using Java and Test Driven Development to create a Minesweeper game

The object of the game is for a player to find and seal all ten mines hidden among hundred cells (10 x 10 cells). The mines may be in any random location.

There are three types of cells: mined cell, adjacent cell, and empty cell.

A mined cell has a hidden mine.

An adjacent cell is next to one or more mined cells and knows the number of mines next to it. This number is not revealed to the player initially.

An empty cell has no mine and is not next to a mined cell.

At the start of the game all cells are displayed grayed out, the player has no initial clue which cells are mined, adjacent, or empty. The player may either expose a cell or seal it.

The player may seal a cell if they suspect that cell to have a mine. Only an unexposed cell can be sealed. A sealed cell is shown with a seal symbol. A player may unseal a sealed cell and it will be displayed gray again.

Only an unexposed and unsealed cell can be exposed.

The behavior of a cell when exposed depends on the type of the cell.

Exposing a mined cell ends the game.

If an empty cell is exposed, the player can see that it's empty. Furthermore, when an empty cell is exposed, it will trigger an expose action on all its unsealed neighbors.

When an adjacent cell is exposed (either due to users direct action or due to the successive action from exposing of a neighboring cell) it's count is shown and no further action happens.

There are two outcomes of the game. The player wins after sealing all the mines and exposing all the other cells. The player loses if a mined cell is exposed.
