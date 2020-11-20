package pappous;

import java.util.*;

import negotiator.*;
import negotiator.actions.Accept;
import negotiator.actions.Action;
import negotiator.actions.Offer;
import negotiator.bidding.BidDetails;
import negotiator.boaframework.SortedOutcomeSpace;
import negotiator.issue.Issue;
import negotiator.issue.IssueDiscrete;
import negotiator.issue.Value;
import negotiator.issue.ValueDiscrete;
import negotiator.parties.AbstractNegotiationParty;
import negotiator.parties.NegotiationInfo;
import negotiator.utility.AdditiveUtilitySpace;


/**
 * This is your negotiation party.
 */
public class pappousAgent extends AbstractNegotiationParty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bid lastReceivedBid = null;
	private Bid max_bid=null;
	private Bid my_bid=null;
	private Bid first_bid=null;
	private final double UPPER_B=1;
	private final double LOWER_B=0.65;
	private final double RES_B=0.5;
	private BidHistory MyBidHistory;
	private static Double upper_b;
	private static Double lower_b;
	private BidDetails MyBidDetails=null;
	private boolean goodplay;//Metavliti an pezw kalytera h xeirotera apo prohgoymenh m kinhsh
	private boolean change1,change2,change3,change4;
	private ArrayList<Bid> allBids=null;
	private ArrayList<Double> allBidsUtil=null;
	private Bid otherBid=null;
//Improvements//----------------
	private int indexofmin; 
	private double min=-1;
	private double perc=0.01;
	private ArrayList<Bid> bids=null;
	private List<BidDetails> allOutcomes;
	/**
	 * The last bid that has been made by any agent (including us).
	 */
	private Bid lastBid;

	/**
	 * The models that we build for every opponent.
	 */
	private Map<Object, OpponentModel> opponentModels;

	/**
	 * The SortedOutcomeSpace of our UtilitySpace.
	 */
	private SortedOutcomeSpace sos;

	
	public void init(NegotiationInfo info) {
		super.init(info);
		goodplay=false;
		MyBidHistory=new BidHistory();
		upper_b=UPPER_B;
		lower_b=LOWER_B;
		change1=false;
		change2=false;
		change3=false;
		change4=false;
		allBids=new ArrayList<Bid>();
		allBidsUtil=new ArrayList<Double>();
//------------Improvements-----------
		bids=new ArrayList<Bid>();
		bids=buildlist();
		sos = new SortedOutcomeSpace(utilitySpace);
		opponentModels = new HashMap<Object, OpponentModel>();
		allOutcomes = sos.getAllOutcomes();
	}
	
	
	
	public Action chooseAction(List<Class<? extends Action>> validActions) {
		if (bids.isEmpty())//Gia na dw an leitourgei h func buildlist
			return new Accept(getPartyId(),generateRandomBid());
		//-----------------PRWTH KINHSH----------------------------------
		if(lastReceivedBid==null) {//Se auto to if pezw prwto prin ton allo
			max_bid=getMaxBid();//best
			goodplay=true; //Epeksa kala
			first_bid=max_bid; //To prwto bid to max
			MyBidDetails=new BidDetails(first_bid,getUtility(first_bid));
			MyBidHistory.add(MyBidDetails);
			return new Offer(getPartyId(), first_bid);
		}
		if(first_bid==null) {//pezw deuteros kai einai h prwth m kinhsh best
			max_bid=getMaxBid();//best
			goodplay=true; //Epeksa kala
			first_bid=max_bid; //To prwto bid to max
			MyBidDetails=new BidDetails(first_bid,getUtility(first_bid));
			MyBidHistory.add(MyBidDetails);
			return new Offer(getPartyId(), first_bid);
		}
		if (aggrement(getUtility(lastReceivedBid))) {
			return new Accept(getPartyId(),lastReceivedBid);
		}
		//------------------------Deuterh Kinhsh------------------------------
		//H prwth kinhsh egine eite pexw prwtos eite deuteros
		//An to my bid einai null dn exw peksei deuterh fora
		//Kanoume th deuterh kinhsh
		if(my_bid==null) {
			do {
				my_bid=getLRandBid(lower_b);
			}while(getUtility(my_bid)>getUtility(first_bid));
			goodplay=false;
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			return new Offer(getPartyId(), my_bid);
		}
		
		//-Thelw allagh diasthmatos --Allagh orion kai pezw best thn prwth fora ths allaghs
		if(timeline.getTime()>=0.8 && !change4) {
			upper_b=UPPER_B*0.8;//Allazw oria
			lower_b=LOWER_B*0.8;
			change4=true;
			my_bid=selectMinDistBid(upper_b);
			goodplay=true;
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			perc=+0.01;
			return new Offer(getPartyId(), my_bid);
		}
		else if(timeline.getTime()>=0.6 && !change3) {
			upper_b=UPPER_B*0.85;//Allazw oria
			lower_b=LOWER_B*0.85;
			change3=true;
			my_bid=selectMinDistBid(upper_b);
			goodplay=true;
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			perc=+0.01;
			return new Offer(getPartyId(), my_bid);
		}else if(timeline.getTime()>=0.4 && !change2) {
			upper_b=UPPER_B*0.9;//Allazw oria
			lower_b=LOWER_B*0.9;
			change2=true;
			my_bid=selectMinDistBid(upper_b);
			goodplay=true;
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			perc=+0.01;
			return new Offer(getPartyId(), my_bid);
		}else if(timeline.getTime()>=0.2 && !change1)  {
			upper_b=UPPER_B*0.95;
			lower_b=LOWER_B*0.95;
			change1=true;
			my_bid=selectMinDistBid(upper_b);
			goodplay=true;
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			perc=+0.01;
			return new Offer(getPartyId(), my_bid);
		}
			

		if(timeline.getTime()>0.95) {//Stelnoume prwta Reservation 
			do {
				my_bid=getURandBid(RES_B);
			}while(!((RES_B)==getUtility(my_bid)));
			goodplay=true;
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			return new Offer(getPartyId(), my_bid);
		}
		if(timeline.getTime()>0.9) {
			if(allBids.isEmpty()) {
				do {
					otherBid=getURandBid(RES_B);
				}while(!((RES_B)==getUtility(otherBid)));
				goodplay=true;
				MyBidDetails=new BidDetails(otherBid,getUtility(otherBid));
				MyBidHistory.add(MyBidDetails);
				return new Offer(getPartyId(), otherBid);
			}
			else{
				otherBid=getRandomList(allBids);
				return new Offer(getPartyId(), otherBid);
			}
				
		}
		if(goodplay) {
			do {
				my_bid=getLRandBid(lower_b);
			}while(getUtility(my_bid)>getUtility(MyBidHistory.getLastBid()));
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			goodplay=false;
			return new Offer(getPartyId(), my_bid);
		}else {
			do {
				my_bid=getURandBid(upper_b);
			}while(getUtility(my_bid)<getUtility(MyBidHistory.getLastBid()));
			try {
				Bid bid=generateBid();
				if(getUtility(bid)>lower_b) {
					MyBidDetails=new BidDetails(bid,getUtility(bid));
					MyBidHistory.add(MyBidDetails);
					goodplay=true;
				}else {
					MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
					MyBidHistory.add(MyBidDetails);
					goodplay=true;
					return new Offer(getPartyId(), my_bid);
				}
			}catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			goodplay=true;
			MyBidDetails=new BidDetails(my_bid,getUtility(my_bid));
			MyBidHistory.add(MyBidDetails);
			return new Offer(getPartyId(), my_bid);
		}
		
					
	}

	
	
	public void receiveMessage(AgentID sender, Action action) {
		super.receiveMessage(sender, action);
		
		
		if (action instanceof Offer) {
			
			lastReceivedBid = ((Offer) action).getBid();
			try {
					lastBid = ((Offer) action).getBid();
					updateModel(sender, lastBid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(allBids.size()<10) {
				if(getUtility(lastReceivedBid)>RES_B) {
					allBids.add(lastReceivedBid);
					allBidsUtil.add(getUtility(lastReceivedBid));
				}
				
			}
			if(allBids.size()==10) {
				for(int x=0;x<allBidsUtil.size();x++) {
					if(allBidsUtil.get(x)<min) {
						min=allBidsUtil.get(x);
						indexofmin=x;
					}
				}
				if(getUtility(lastReceivedBid)>min) {
					allBidsUtil.add(indexofmin, getUtility(lastReceivedBid));
					allBids.add(indexofmin,lastReceivedBid);
				}
				min=-1;
				indexofmin=0;
			}
			
		}
		if (action instanceof Accept) {
			try {
				updateModel(sender, lastBid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		
		

	}
	
	public Bid getRandomList(ArrayList<Bid> list) {
	    //Math.random() = greater than or equal to 0.0 and less than 1
            //0-4
	    int index = (int)(Math.random()*list.size());
	    return list.get(index);
	    
}
	
	private Bid generateBid() throws Exception {
		Double time = timeline.getTime();
		double ownFactor = 1 - (time * time); // 1 - x^2
		double nashFactor = 1 - ownFactor;

		// Get modeled utility spaces for opponents
		Set<Object> opponents = opponentModels.keySet();
		Map<Object, AdditiveUtilitySpace> opponentUtilSpaces = new HashMap<Object, AdditiveUtilitySpace>();
		for (Object opponent : opponents) {
			opponentUtilSpaces.put(opponent, opponentModels.get(opponent).getUtilitySpace());
		}

		// weightedAverages contains the top 5 weighted averages found
		Map<BidDetails, Double> weightedAverages = new HashMap<BidDetails, Double>();
		weightedAverages.put(null, 0.);
		for (BidDetails bid : allOutcomes) {
			// First find the minimum in order to know which one we should
			// replace if we find a better one.
			double min = Double.MAX_VALUE;
			BidDetails minBid = null;
			for (BidDetails mapBid : weightedAverages.keySet()) {
				double mapAvg = weightedAverages.get(mapBid);
				if (mapAvg <= min) {
					min = mapAvg;
					minBid = mapBid;
				}
			}

			// If based on our utility alone we won't make it, break because:
			// - Adding the opponents utilities to the nash will only decrease
			// the value (because U <= 1)
			// - The list is sorted in decreasing order so all next bids will be
			// worse.
			double ourUtil = bid.getMyUndiscountedUtil();
			if (ownFactor * ourUtil + nashFactor * ourUtil < min)
				break;

			double nash = ourUtil;
			for (Object opponent : opponents) {
				AdditiveUtilitySpace opponentUtilSpace = opponentUtilSpaces.get(opponent);
				nash *= opponentUtilSpace.getUtility(bid.getBid());
			}

			double weightedAverage = ownFactor * ourUtil + nashFactor * nash;
			if (weightedAverage > min) {
				if (weightedAverages.size() >= 5) {
					weightedAverages.remove(minBid);
				}

				weightedAverages.put(bid, weightedAverage);
			}
		}

		// Find the one that gives the highest utility of the top 5.
		double max = 0;
		BidDetails maxBid = null;
		for (BidDetails mapBid : weightedAverages.keySet()) {
			double mapUtil = mapBid.getMyUndiscountedUtil();
			if (mapUtil > max) {
				max = mapUtil;
				maxBid = mapBid;
			}
		}

		return maxBid.getBid();
	}
	
	
	
	//Apo yxagent nomizw
	public Bid getMaxBid() {
		Bid max_bid=null;
			try {
				max_bid = utilitySpace.getMaxUtilityBid();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return max_bid;
	}
	
	public Bid selectMinDistBid(double upperb) {
		ArrayList<Bid> temp1=new ArrayList<Bid>();
		ArrayList<Double> temp2=new ArrayList<Double>();
		Bid temp=null;
		for(int k=0;k<bids.size();k++) {
			if(getUtility(bids.get(k))<=upperb) {
				temp1.add(bids.get(k));
				temp2.add(getUtility(bids.get(k)));
			}
		}
		double maxvalue=Collections.max(temp2);
		int maxindex=temp2.indexOf(maxvalue);
		temp=temp1.get(maxindex);
		return temp;
	}
	
	public ArrayList<Bid> buildlist() {
		List<Issue> issueList = utilitySpace.getDomain().getIssues();
		ArrayList<ArrayList<ValueDiscrete>> listValueList = new ArrayList<ArrayList<ValueDiscrete>>();
		ArrayList<Bid> bidList = new ArrayList<Bid>();
		int bidListsize = 1;
		int nIssues = issueList.size();
		int[] nValues = new int[nIssues];
		for (int i = 0; i < issueList.size(); i++) {
			listValueList
					.add((ArrayList<ValueDiscrete>) ((IssueDiscrete) issueList
							.get(i)).getValues());
			nValues[i] = listValueList.get(i).size();
			bidListsize = bidListsize * nValues[i];
		}
		ValueDiscrete[][] valueMatrix = new ValueDiscrete[bidListsize][nIssues];
		for (int i = 0; i < nIssues; i++) {
			int before = 1, actual = nValues[i], after = 1;
			for (int k = 0; k < i; k++) {
				before = before * nValues[k];
			}
			for (int k = nIssues - 1; k > i; k--) {
				after = after * nValues[k];
			}
			for (int j = 0; j < before; j++) {
				for (int k = 0; k < actual; k++) {
					for (int l = 0; l < after; l++) {
						valueMatrix[l + actual * after * j + k * after][i] = listValueList
								.get(i).get(k);
					}
				}
			}

		}
		
		for (int i = 0; i < bidListsize; i++) {
			HashMap<Integer, Value> bidMap = new HashMap<Integer, Value>();
			for (int j = 0; j < nIssues; j++) {
				bidMap.put(issueList.get(j).getNumber(), valueMatrix[i][j]);
			}
			Bid currentBid;
			try {
				currentBid = new Bid(utilitySpace.getDomain(), bidMap);
				bidList.add(currentBid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bidList;
	}
	
	

	
	/* private function to get a new valid Bid */
	//Apo grandma me tropopoihsh
	private Bid getLRandBid(double minUtil) {
		Bid newbid;
		do {
			newbid = generateRandomBid();
		}while (getUtility(newbid) < minUtil );
		return newbid;
	}
	
	private Bid getURandBid(double maxUtil) {
		Bid newbid;
		do {
			newbid = generateRandomBid();
		}while (getUtility(newbid) > maxUtil );
		return newbid;
	}
	
	
	
	
	
	
	//Apo grandma kalo(to offer tou antipalou) gia n kanw aggrement
/*	 * Checks if the utility of the offered bid is higher than the minimum
	 * accepted and if it is lower than our last bid
	 * Utility
	 */
	private boolean aggrement(double offered) {
		double time=timeline.getTime();
		if (time >= 0.9) {
			List<Bid> opponentBids = new ArrayList<Bid>();
			for (Object opponent : opponentModels.keySet()) {
				opponentBids.addAll(opponentModels.get(opponent).getBidHistory(1 - time));
			}
			double maxUtility = 0;
			for (Bid oppBid : opponentBids) {
				double utility = utilitySpace.getUtility(oppBid);
				if (utility > maxUtility) {
					maxUtility = utility;
				}
			}
			if (maxUtility>RES_B && offered>=maxUtility)
				return true;
		}
		if(MyBidHistory!=null) {
			if (offered>=getUtility(MyBidHistory.getLastBid())) {
				return true;
			}else if(goodplay) {
				double value=getUtility(MyBidHistory.getLastBid())*(1-perc);
				if(offered>=value) {
					return true;
				}
			}else {
				return false;
			}
		}
		return false;
		
	}
	
	private void updateModel(AgentID opponent, Bid newBid) throws Exception {
		OpponentModel opponentModel = opponentModels.get(opponent);
		if (opponentModel == null) {
			opponentModel = new OpponentModel(utilitySpace.getDomain(), newBid);
			opponentModels.put(opponent, opponentModel);
		} else {
			opponentModel.updateModel(newBid);
		}
	}
	
	
//-----------------------------------------------------------//
	public String getDescription() {
		return "party PappousAgentOP gg 2019";
	}
}
	

