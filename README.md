# Automated Negotiating Agents Competition (ANAC) (Pappous Agent)
> Agent participating to local ANAC competition in Technical University of Chania 

## Table of contents
* [General Info](#general-information)
* [Setup](#setup)
* [How to run](#how-to-run)
* [Screenshot](#screenshot)
* 
* [Acknowledgements](#acknowledgements)

## General Information
_*ANAC*_ dedicates to develop successful automated negotiators for scenarios where there is incomplete information about the opponent. The design of generic negotiating agents aims to operate in a variety of scenarios providing benchmarks of performance.

*_GENIUS (General Environment for Negotiation with Intelligent multi-purpose Usage Simulation)_* is a negotiation environment that implements an open architecture for heterogeneous negotiating parties. In addition, Genius can be used to implement, or simulate, real life negotiations.  

## Setup
Java Integrated Development Environment (Eclipse IDE)

## How to run
Genius can run on any machine running Java 8. Java 9 is not yet supported.

1. Download the file `genius-XXX.zip` from
```
http://ii.tudelft.nl/genius/?q=article/releases
```
and unzip it to your machine.

1. Install the environment, the file `genius-XXX.zip`. 
  * `genius-XXX.jar` => GENIUS negotiation simulator

2. Start a console and find the directory of `genius-XXX.jar`

  1. On windows, open the `.jar` using
  ```java -jar genius-XXX.jar```
  2. On mac, open the `.jar` using
     1.`Systemdisk/Applications/Utilities/Console.app`
     2. Double click on the app on the path above
  
  For, more information, check `readme` in the genius folder.
  
## Execution
There are two modes to run a negotiation:
* Session
* Tournament

In each mode, parameters need to be specified for running.

### Log files
Log files get the filename tournament- followed by day and time followed by the domain name and
an extension. <br>
There are 3 log files created: 
* log.csv file 
* log.xml file
* logStats.xml file


![alt-text-1](image1.png "title-1") ![alt-text-2](image2.png "title-2")

For more info about parameter description/selection, see `readme`

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



# Screenshot
https://github.com/z1skgr/ANAC-competition/issues/2#issue-1144973956




## Acknowledgements
- This project was created for the requirements of the lesson Multiagent Systems
- You can find every annual competition in https://web.tuat.ac.jp/~katfuji/ANAC2020/#:~:text=The%20Automated%20Negotiating%20Agent%20Competition%20%28ANAC%29%20is%20an,to%20bring%20together%20researchers%20from%20the%20negotiation%20community.

