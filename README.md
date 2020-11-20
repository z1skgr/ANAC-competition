# MultiAgent Systems (Pappous Agent)
 ANAC competition 

Introduction
The project concerns participation of the agent in a Final Internal Competition ANAC. Genius is a negotiation environment that implements an open architecture for heterogeneous negotiating parties. Genius can be used to implement, or simulate, real life negotiations. 



# Description of Agent
General Idea. A heuristic agent that follows 4 basic rules for accepting bids.
1) Upper & Lower Bounds for expected utility. <br />
2) Differentiation of bounds in time. <br />
3) Own  & Opponent Bid History. <br /> 
4) Send Bid with Reservation Value at the end of the deal. <br />


# Decide Offer
1)First move of the player is chosen as the best possible
2)Select Bid that have utility in a specific range
3)The selected Bid depends on the evaluation of the previous move
4)At the end of the negotiation the minimum offer touches the Reservation Value
5)A bid history is used when the transaction is in the final stretch

# Bounds
Upper Bound differ from 1 to 0.8
Lower Bound differ from 0.65 to 0.52 

The implementation is a union of existed agents worked in ANAC agent. 

How to run
To build agent you need to download anac project from 
(http://ii.tudelft.nl/genius/?q=article/releases and import agent)
in project. You can build agent in a java platform. For a succesfull building genius, you can follow genius's user manual.