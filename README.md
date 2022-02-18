# MultiAgent Systems (Pappous Agent)
 Agent participating to local ANAC competition in Technical University of Chania 

# Introduction
The project concerns participation of the agent in a Final Internal Competition ANAC. Genius is a negotiation environment that implements an open architecture for heterogeneous negotiating parties. Genius can be used to implement, or simulate, real life negotiations. 



# Description of Agent
General Idea. A heuristic agent that follows 4 basic rules for accepting bids.
1) Upper & Lower Bounds for expected utility. <br />
2) Differentiation of bounds in time. <br />
3) Own  & Opponent Bid History. <br /> 
4) Send Bid with Reservation Value at the end of the deal. <br />


# Decide Offer
1)First move of the player is chosen as the best possible <br />

2)Select Bid that have utility in a specific range <br />

3)The selected Bid depends on the evaluation of the previous move <br />

4)At the end of the negotiation the minimum offer touches the Reservation Value <br />

5)A bid history is used when the transaction is in the final stretch <br />


# Bounds
Upper Bound differ from 1 to 0.8 <br />

Lower Bound differ from 0.65 to 0.52  <br />


The implementation is a union of existed agents worked in ANAC agent. 

# How to run
To build agent you need to download anac project from 
(http://ii.tudelft.nl/genius/?q=article/releases) and import agent
in project. You can build agent in a java platform. For a succesfull building genius, you can follow genius's user manual.



# AI_application_of_algorithms_on_probs
> Solving the problem of n queens using AI algorithms studying their performance.

## Table of contents
* [General Info](#general-information)
* [Implementation](#implementation)
* [Setup](#setup)
* [Screenshot](#screenshot)
* [Acknowledgements](#acknowledgements)

## General Information
On *NxN* chessboard, queens placed at random cells. The problem is solved after queens move to the desired state of the chessboard (no threatened queen). <br>For more information, check https://www.geeksforgeeks.org/n-queen-problem-backtracking-3/. <br>
In our approach, we have 
1. Local search 
2. Constraint satisfaction

## Implementation
We use `Grid.Java` to represent queens in a board ( __NxN__ grid, queen as binary values)
Grid is randomly initialized with queens in any columns.


### Hill Climbing
* Available move is a position in which another queen can't intervene (inside the grid).
* Queens exposed to attacks reflect the conflicts.

For more information, see https://www.javatpoint.com/hill-climbing-algorithm-in-ai

### Constraint Satisfaction 
In our view, queen can threat diagonally, vertical and horizontaly (due to mutual collisions between two queens).

* Non-threatened queen by a predecessor (from left-sided queen), stays where she is.
* Otherwise, finds a row in which he is not bothered by the previous ones. 
    * No such row, then the algorithm will return with the position of the queen who has a problem.

For more info, see https://en.wikipedia.org/wiki/Constraint_satisfaction

### Performance Measures
* Average resolution time for N number of repetitions per number
queens
* Average initialization number of the grid to establish the solution.
* Reliability of the solution.
* Collision minimization

## Setup
Java Integrated Development Environment (Eclipse IDE)

## Screenshot
https://github.com/z1skgr/Artificial-Intelligence/issues/1#issue-1143769173





## Acknowledgements
- This project was created for the requirements of the lesson Multiagent Systems

