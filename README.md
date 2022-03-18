# Tree-Structure

Instructions:

The first goal of this assignment is to read the tree structure from a file and display the tree structure on the console as described next. Each line in the file will hold a node's ID and its children's IDs. For example, if the file contains the following

1 2 3 4

3 6

4 7 8

the first ID in each line represents the parent node and the remaining IDs in that line are its children. In the tree structure above, 1 has three children 2,3,4; node 3's child is 6, and 7 and 8 are children of node 4. Node 2 does not have any child and that's why it is not at the start of any line in the file. Node 1 is the root of the tree always and it will be in the first column of the first line in the file.

Your first goal is to read the data from a file that you can assume will be stored in Dr. Dutta's computer. The file name will be "tree.txt". DO NOT use any specific file address location in the code. Once you read the file, the next goal would be to print the tree structure on the console in the following ways:

1. Pre-order traversal

2. The way the tree structure was stored in the file itself (i.e., like the above tree)

Demark your printouts so that the two types of printouts are easily separable on the console.

Solve the following problem on This Tree
Assume that you have a robot that wants to visit all the nodes in this tree using a modified Depth-First Search (DFS) Algorithm. However, it has limited battery capacity and can cross a maximum of B edges in the tree before it runs out of energy.
Think about an iRobot Roomba vacuum cleaning robot that wants to clean all the points in your house.
There is a charging station located in the root that charges the robot whenever the robot reaches the root.
Note that if the robot has some budget left before it moves to the root, it can not have more than B edge-crossing energy capacity, i.e., the battery capacity is capped by B.
If the robot has B edge crossing capacity, then it can cover B/2 edges going away from the charging station and the remaining half to get back to the charging station before the battery runs out.
If B/2 is lower than the tree height, then some nodes in the tree will not be visited as there will never be enough energy.
The logic of exploration: Similar to DFS, it will follow the nodes in the tree in the depth-first order. However, when the robot has exhausted the B/2 budget in going away from the root, it will follow the same path back (i.e., following the parents of the current node) to reach the root again before the complete budget is exhausted. The DFS search is PAUSED in this phase. Next, after getting fully recharged at the root, it will RESUME the DFS traversal, i.e., from the next node in the DFS traversal order.
The program will terminate when all the nodes are visited at least once by the robot or no new node can be visited anymore because of the limited budget capacity.
B will be a user input -- do not hardcode that in your program. You can assume that B will always be an even number.
Given this, your goal is the following:
Print the path that the robot has taken during the exploration.
What is the percentage of nodes that have been visited by the robots at least once?
The solution for the tree shown above with B=4 will be
Path: 1-2-1-3-6-3-1-4-7-4-1-4-8-4-1
Percentage of exploration: 100%
