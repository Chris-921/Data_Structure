# Data_Structure
Welcome to the Data Structures and Algorithms Repository, a comprehensive resource for mastering fundamental concepts in computer science. This repository covers a wide range of topics, including:

* Linear Data Structures: Explore the principles of lists, queues, and other linked structures. Learn how to implement and manipulate these fundamental data structures efficiently.

* Arrays and Strings: Understand the intricacies of arrays and strings in programming. Discover methods for processing and managing these essential data types effectively.

* Trees: Dive into the world of trees, including binary trees and balanced trees. Learn the theory and practical implementations of these hierarchical structures.

* Hash Tables: Explore hash tables and their applications in storing and retrieving data. Understand how hash functions work and their significance in data management.

* Storage Management: Gain insights into memory management and storage allocation strategies used in modern software systems.

* Software Engineering Principles: Learn elementary principles of software engineering, including code organization, modularization, and best practices in software development.

* Abstract Data Types: Explore the concept of abstract data types (ADTs) and their role in building efficient and maintainable software systems.

* Sorting and Searching Algorithms: Study a variety of algorithms for sorting and searching data. Understand the efficiency and application scenarios for each algorithm.

This repository is designed to provide a structured learning path for computer science enthusiasts, software developers, and students. Whether you're a beginner looking to build a solid foundation or an experienced programmer seeking to sharpen your skills, you'll find valuable resources and code examples here.

---

## Project Description

#### Game 2048:
I build the core logic of the game "2048".  (see this on-line version of 2048: https://play2048.co/ ).

The game itself is quite simple. It’s played on a 4*4 grid of squares, each of which can either be empty or contain a tile bearing an integer–a power of 2 greater than or equal to 2. Before the first move, the application adds a tile containing either 2 or 4 to a random square on the initially empty board. The choice of 2 or 4 is random, with a 75% chance of choosing 2 and a 25% chance of choosing 4.

The player then chooses a direction via their arrow keys to tilt the board: north, south, east, or west. All tiles slide in that direction until there is no empty space left in the direction of motion (there might not be any to start with). A tile can possibly merge with another tile which earns the player points.
Here are the full rules for when merges occur that are shown in the image above.

1. Two tiles of the same value merge into one tile containing double the initial number.

2. A tile that is the result of a merge will not merge again on that tilt. For example, if we have [X, 2, 2, 4], where X represents an empty space, and we move the tiles to the left, we should end up with [4, 4, X, X], not [8, X, X, X]. This is because the leftmost 4 was already part of a merge so should not merge again.

3. When three adjacent tiles in the direction of motion have the same number, then the leading two tiles in the direction of motion merge, and the trailing tile does not. For example, if we have [X, 2, 2, 2] and move tiles left, we should end up with [4, 2, X, X] not [2, 4, X, X].

As a corollary of these rules, if there are four adjacent tiles with the same number in the direction of motion, they form two merged tiles. For example, if we have [4, 4, 4, 4], then if we move to the left, we end up with [8, 8, X, X]. This is because the leading two tiles will be merged as a result of rule 3, then the trailing two tiles will be merged, but because of rule 2 these merged tiles (8 in our example) will not merge themselves on that tilt.

If the tilt did not change the board state, then no new tiles will be randomly generated. Otherwise, a single randomly generated tile will be added to the board on an empty square. 

You might also notice that there is a field “Score” at the bottom of the screen that is being updated with each move. The score will not always change every move, but only when two tiles merge. 

Each time two tiles merge to form a larger tile, the player earns the number of points on the new tile. The game ends when the current player has no available moves (no tilt can change the board), or a move forms a square containing 2048. Your code will be responsible for detecting when the game is over.

The “Max Score” is the maximum score the user has achieved in that game session. It isn’t updated until the game is over.


---

Language: Java

Compiler: IntelliJ IDEA

Author: Zilong Guo

Date: Jun - Aug 2022

---

This repo only includes sample code and algorithms written by Zilong. The project ideas are from the UCB CS61B class.
