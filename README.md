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
I built the core logic of the game "2048".  (see this on-line version of 2048: https://play2048.co/ ).

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

#### Deques:

I built implementations of a “Double Ended Queue” using both lists and arrays in a package that other classes can use. The project is roughly split into two halves: the data structure portion and the application portion.
In the data structure part: ArrayDeque sample code.java and LinkedListDeque sample code.java
In the application part: MaxArrayDeque sample code.java and 

#### Percolation:
I wrote a program to estimate the value of the percolation threshold via Monte Carlo simulation.(https://en.wikipedia.org/wiki/Monte_Carlo_method)

Percolation. Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

The model. We model a percolation system using an N-by-N grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

In the diagrams below, you can see that in the system on the left, the water is able to start in a site on the top row and trickle down through empty sites until it reaches an empty site on the bottom row.

Whereas on the right, the water in the site on the top row has no way of trickling down to an open site on the bottom row.

![Screenshot 2023-10-27 at 2 25 30 AM](https://github.com/Chris-921/Data_Structure/assets/112040075/ac644ffa-9291-4da9-8be4-76483b5fff43)

Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:

 * Initialize all sites to be blocked.
 * Repeat the following until the system percolates:
    Choose a site uniformly at random among all blocked sites.
    Open the site.
 * The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
   
For example, if sites are opened in a 20-by-20 grid according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened. The images correspond to the 50, 100, 150, and 204 sites being open, respectively.

![Screenshot 2023-10-27 at 2 30 17 AM](https://github.com/Chris-921/Data_Structure/assets/112040075/3ef6d346-da23-4085-8d8f-8f00b681947b)

By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let xt be the fraction of open sites in computational experiment t. The sample mean μ provides an estimate of the percolation threshold; the sample standard deviation σ measures the sharpness of the threshold.

![Screenshot 2023-10-27 at 2 36 03 AM](https://github.com/Chris-921/Data_Structure/assets/112040075/4a7e4366-5deb-4b35-b992-a3207975f7f0)

Assuming T is sufficiently large (say, at least 30), the following provides a 95% confidence interval for the percolation threshold:

![Screenshot 2023-10-27 at 2 36 46 AM](https://github.com/Chris-921/Data_Structure/assets/112040075/d1ce07b8-7590-450c-ae77-27a61433cabb)

#### NGordnet (NGrams):

I built a browser based tool for exploring the history of word usage in English texts. The front-end code is provided (in Javascript and HTML) that collects user inputs and displays outputs. This Java code will be the back end for this tool, accepting input and generating appropriate output for display.

The string is a base 64 encoding of an image file. To visualize it, Google “base64 image decode”. At the time of this writing, the top search result was for a site called codebeautify.org. Copy and paste this entire string into the website and you should see a plot similar to the one shown below:

![Screenshot 2023-10-27 at 3 03 33 AM](https://github.com/Chris-921/Data_Structure/assets/112040075/6806588b-2bd1-4927-8feb-e9dd9793b0f9)

The string your code printed IS THE IMAGE. Keep in mind that any data can be represented as a string of bits. This website knows how to decode this string into the corresponding image, using a predefined standard.

In your web browser, again open up the ngordnet_2a.html file in the static folder. With your ngordnet.main.Main class running, enter “cat, dog” into the “words” box, then click “history”. You’ll see the strange image below:

![Screenshot 2023-10-27 at 3 04 32 AM](https://github.com/Chris-921/Data_Structure/assets/112040075/348d67ed-9fa0-4df4-b144-06fedf45c740)

WordNet is a “semantic lexicon for the English language” that is used extensively by computational linguists and cognitive scientists; for example, it was a key component in IBM’s Watson. WordNet groups words into sets of synonyms called synsets and describes semantic relationships between them. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, “change” is a hypernym of “demotion”, since “demotion” is-a (type of) “change”. “ change” is in turn a hyponym of “action”, since “change” is-a (type of) “action”. A visual depiction of some hyponym relationships in English is given below:

![Screenshot 2023-10-27 at 3 10 04 AM](https://github.com/Chris-921/Data_Structure/assets/112040075/ddc5e30a-d8c1-41ef-af90-2310a292f1f8)

Each node in the graph above is a synset. Synsets consist of one or more words in English that all have the same meaning. For example, one synset is “jump, parachuting” , which represents the act of descending to the ground with a parachute. “jump, parachuting” is a hyponym of “descent”, since “jump, parachuting” is-a “descent”.

Words in English may belong to multiple synsets. This is just another way of saying words may have multiple meanings. For example, the word “jump” also belongs to the synset “jump, leap” , which represents the more figurative notion of jumping (e.g. a jump in attendance) rather the literal meaning of jump from the other synset (e.g. a jump over a puddle). The hypernym of the synset “jump, leap” is “increase”, since “jump, leap” is-an “increase”. Of course, there are other ways to “increase” something: for example, we can increase something through “augmentation,” and thus it is no surprise that we have an arrow pointing downwards from “increase” to “augmentation” in the diagram above.

Synsets may include not just words, but also what are known as collocations. You can think of these as single words that occur next to each other so often that they are considered a single word, e.g. nasal_decongestant . To avoid ambiguity, we will represent the constituent words of collocations as being separated with an underscore _ instead of the usual convention in English of separating them with spaces. For simplicity, we will refer to collocations as simply “words” throughout this document.

A synset may be a hyponym of multiple synsets. For example, “actifed” is a hyponym of both “antihistamine” and “ nasal_decongestant”, since “actifed” is both of these things.

---

Language: Java

Compiler: IntelliJ IDEA

Author: Zilong Guo

Date: Jun - Aug 2022

---

This repo only includes sample code and algorithms written by Zilong. The project ideas are from the UCB CS61B class.
