# Automated Negotiating Agents Competition (ANAC) (Pappous Agent)
> Agent participating to local ANAC competition in Technical University of Chania 

## Table of contents
* [General Info](#general-information)
* [Setup](#setup)
* [How to run](#how-to-run)
* [Execution](#execution)
* [Agent Description](#agent-description)
* [Acknowledgements](#acknowledgements)

## General Information
_*ANAC*_ dedicates to develop successful automated negotiators for scenarios where there is incomplete information about the opponent. The design of generic negotiating agents aims to operate in a variety of scenarios providing benchmarks of performance.

*_GENIUS (General Environment for Negotiation with Intelligent multi-purpose Usage Simulation)_* is a negotiation environment that implements an open architecture for heterogeneous negotiating parties. In addition, Genius can be used to implement, or simulate, real life negotiations.  

## Setup
* Java Integrated Development Environment (Eclipse IDE)
* Java Version 8
* Genius.XXX version


## How to run
Genius can run on any machine running Java 8. Java 9 is not yet supported.

1. Download the file `genius-XXX.zip` from ` http://ii.tudelft.nl/genius/?q=article/releases`
and unzip it to your machine.

2. Install the environment, the file `genius-XXX.zip`. 
  * `genius-XXX.jar` => GENIUS negotiation simulator

3. Start a console and find the directory of `genius-XXX.jar`
   - On windows, open the `.jar` using
     ```java -jar genius-XXX.jar```
   - On mac, open the `.jar` using <br>
       - Systemdisk/Applications/Utilities/Console.app
       - Double click on the app on the path above

  
  For more information, check `readme` in the genius folder.
  
  


### Connect IDE & Genius
1. Open Eclipse

2. Select File->New Java Project
   * Make sure you select `JavaSE-1.8`. If you missed `JavaSE`, you can download it from
   ```https://www.oracle.com/java/technologies/downloads/#java17```
   
3. Import (or drag) `genius-XXX.jar` into the project in the navigator area

4. Connect genius Jar
       - Right click on the `<your_project_name>` icon and select ”Properties”.
       - Select the Java Build Path.
       - Select the Libraries Tab.
       - Select ”Add JARs”, in the JAR Selection window.  
       - Open the `<your_project_name>` folder, scroll down to select genius.jar and  run as java application .
       
       
### Create An Agent
* Drag an example folder from your unzipped genius
* Drop it on the `src` folder
* Copy files and rename folder 
* Work using imagination and check out other agents policy to enrich yours. :octocat:


## Execution
There are two modes to run a negotiation:
* Session


<div id="image">
  <img src="https://user-images.githubusercontent.com/22920222/154848764-0d781bd4-a994-4197-9244-1f2ad093c16a.png" id="imageone" width="325"/>

* Tournament


  <img src="https://user-images.githubusercontent.com/22920222/154848762-76663eb1-07c0-4cb6-89b2-2c83ed244d10.png" id="imagetwo" width="325"/>
<div>

In each mode, parameters need to be specified for running.

### Log files
Log files get the filename tournament- followed by day and time followed by the domain name and
an extension. <br>
There are 3 log files: 
* log.csv  
* log.xml 
* logStats.xml

For more info about parameter description/selection, see `readme`

## Agent Description
> Agent's name: PappousAgent 
 
General Idea. Heuristic agent[^1] with four basic rules for accepting bids. 
 
1. Expected utility = [Lower Bound  Upper Bound] 
 
2. Bound differentiation in time (percentage reduction of bounds[^2]). 
 
3. Bid History. 
 
4. Final Offer with Reserved Value. 


### Decide Offer

* First move as the best possible.

* Bid with utility in a range.

* Bid dependance from the previous (or bid history).
 
* Reservation Bid in the end of the negotiation.








## Acknowledgements
- This project was created for the requirements of the lesson Multiagent Systems
- You can find every annual competition in [ANAC](https://web.tuat.ac.jp/~katfuji/ANAC2020/#:~:text=The%20Automated%20Negotiating%20Agent%20Competition%20%28ANAC%29%20is%20an,to%20bring%20together%20researchers%20from%20the%20negotiation%20community.)

[^1]: The implementation is a union of existed agents worked in ANAC agent. 
