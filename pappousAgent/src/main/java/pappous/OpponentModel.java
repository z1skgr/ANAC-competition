package pappous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import negotiator.Bid;
import negotiator.Domain;
import negotiator.issue.Issue;
import negotiator.issue.IssueDiscrete;
import negotiator.issue.Objective;
import negotiator.issue.Value;
import negotiator.issue.ValueDiscrete;
import negotiator.utility.Evaluator;
import negotiator.utility.EvaluatorDiscrete;
import negotiator.utility.AdditiveUtilitySpace;

/**
 * This class represents a model of the issue and value weights of an Opponent.
 * This model is built using the Frequency analysis method.
 */
public class OpponentModel {

	/**
	 * The history of bids this opponent has made
	 */
	public List<Bid> bidHistory;

	/**
	 * The domain we are negotiating in.
	 */
	private Domain domain;

	/**
	 * Map containing the modeled issue weight for each issue.
	 */
	public Map<IssueDiscrete, Double> issueModel;

	/**
	 * n used in the Frequency Analysis.
	 */
	private final double n = 0.1;

	/**
	 * Map containing the observed count every value has been bid.
	 */
	public Map<IssueDiscrete, Map<ValueDiscrete, Integer>> valueCountModel;

	/**
	 * Map containing the modeled value weight for each value in each issue.
	 */
	public Map<IssueDiscrete, Map<ValueDiscrete, Double>> valueWeightModel;

	/**
	 * Constructs an new OpponentModel based on the first bid.
	 * 
	 * @param domain
	 *            The domain we are negotiating in.
	 * @param bid
	 *            The opponent's first bid.
	 * @throws Exception
	 */
	public OpponentModel(Domain domain, Bid bid) throws Exception {
		issueModel = new HashMap<IssueDiscrete, Double>();
		valueCountModel = new HashMap<IssueDiscrete, Map<ValueDiscrete, Integer>>();
		valueWeightModel = new HashMap<IssueDiscrete, Map<ValueDiscrete, Double>>();

		bidHistory = new ArrayList<Bid>();
		bidHistory.add(bid);
		this.domain = domain;

		List<Issue> issues = bid.getIssues();
		for (Issue issue : issues) {
			// Initialize weights evenly
			IssueDiscrete issueDiscrete = (IssueDiscrete) issue;
			issueModel.put(issueDiscrete, 1. / issues.size());

			List<ValueDiscrete> values = issueDiscrete.getValues();
			HashMap<ValueDiscrete, Integer> valueCountMap = new HashMap<ValueDiscrete, Integer>();
			HashMap<ValueDiscrete, Double> valueWeightMap = new HashMap<ValueDiscrete, Double>();
			ValueDiscrete bidValue = (ValueDiscrete) bid.getValue(issueDiscrete
					.getNumber());
			for (ValueDiscrete value : values) {
				if (value.equals(bidValue)) {
					valueCountMap.put(value, 1);
					valueWeightMap.put(value, 1.);
				} else {
					valueCountMap.put(value, 0);
					valueWeightMap.put(value, 0.);
				}
			}
			valueCountModel.put(issueDiscrete, valueCountMap);
			valueWeightModel.put(issueDiscrete, valueWeightMap);
		}
	}

	/**
	 * @param percentage
	 *            The percentage (between 0 and 1) of the list, starting from
	 *            the end, that should be returned.
	 * @return Returns the last few bids from this opponent, based on the
	 *         percentage.
	 */
	public List<Bid> getBidHistory(double percentage) {
		int historySize = bidHistory.size();
		int fromIndex = (int) Math.floor(historySize
				- (historySize * percentage));
		List<Bid> newList = new ArrayList<Bid>(historySize - fromIndex);
		for (int i = fromIndex - 1; i < historySize; i++) { // Make sure to
															// always return one
			newList.add(bidHistory.get(fromIndex));
		}
		return newList;
	}

	/**
	 * Generates an UtilitySpace that represents the modeled UtilitySpace of
	 * this opponent.
	 * 
	 * @return An UtilitySpace that represents the modeled UtilitySpace of this
	 *         opponent.
	 * @throws Exception
	 */
	public AdditiveUtilitySpace getUtilitySpace() throws Exception {
		Map<Objective, Evaluator> evaluatorMap = new HashMap<Objective, Evaluator>();
		for (IssueDiscrete issue : issueModel.keySet()) {
			EvaluatorDiscrete evaluator = new EvaluatorDiscrete();
			evaluator.setWeight(issueModel.get(issue));
			Map<ValueDiscrete, Double> valueWeightMap = valueWeightModel
					.get(issue);
			for (ValueDiscrete value : valueWeightMap.keySet()) {
				evaluator.setEvaluationDouble(value, valueWeightMap.get(value));
			}
			evaluatorMap.put(issue, evaluator);
		}
		return new AdditiveUtilitySpace(domain, evaluatorMap);
	}

	/**
	 * Normalizes the issue weights in the issueModel.
	 */
	private void normalizeWeights() {
		double sum = 0;
		for (IssueDiscrete v : issueModel.keySet()) {
			sum += issueModel.get(v);
		}

		// Divide every issue weight with the sum of the weights to obtain a sum
		// of 1.
		for (IssueDiscrete v : issueModel.keySet()) {
			double issueWeight = issueModel.get(v);
			issueModel.put(v, issueWeight / sum);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ret = "";
		for (Issue issue : valueWeightModel.keySet()) {
			ret += "Issue: " + issue.getName() + " - weight: "
					+ issueModel.get(issue) + "\n";
			for (Value value : valueWeightModel.get(issue).keySet()) {
				ret += "Value: " + value.toString();
				ret += " - weight: " + valueWeightModel.get(issue).get(value)
						+ " (" + valueCountModel.get(issue).get(value) + ")\n";
			}
		}

		return ret;
	}

	/**
	 * Updates the model with a new bid that has been received.
	 * 
	 * @param bid
	 *            The newest bid of our opponent.
	 * @throws Exception
	 */
	public void updateModel(Bid bid) throws Exception {
		Bid lastBid = bidHistory.get(bidHistory.size() - 1);
		for (Issue issue : issueModel.keySet()) {
			// Increase the issue weight if its value didn't change.
			ValueDiscrete newValue = (ValueDiscrete) bid.getValue(issue
					.getNumber());
			ValueDiscrete oldValue = (ValueDiscrete) lastBid.getValue(issue
					.getNumber());
			if (newValue.equals(oldValue)) {
				double issueWeight = issueModel.get(issue);
				issueModel.put((IssueDiscrete) issue, issueWeight + n);
			}

			// Increase the count for the values that have been bid.
			Map<ValueDiscrete, Integer> valueCountMap = valueCountModel
					.get(issue);
			int valueCount = valueCountMap.get(newValue);
			valueCountMap.put(newValue, valueCount + 1);

			// Recalculate the weights based on the counts.
			updateValueWeights((IssueDiscrete) issue);
		}

		// Normalize the weights again so the sum is again 1.
		normalizeWeights();
		bidHistory.add(bid);
	}

	/**
	 * Updates the valueWeightModel based on the valueCountModel for the given
	 * issue.
	 * 
	 * @param issue
	 *            The issue for which the value weights should be updated.
	 */
	private void updateValueWeights(IssueDiscrete issue) {
		Map<ValueDiscrete, Integer> valueCountMap = valueCountModel.get(issue);
		Map<ValueDiscrete, Double> valueWeightMap = valueWeightModel.get(issue);

		int max = 0;
		for (ValueDiscrete v : valueCountMap.keySet()) {
			int valueCount = valueCountMap.get(v);
			if (valueCount > max)
				max = valueCount;
		}

		// Divide every value count with the maximum count to obtain values
		// between 0 and 1.
		for (ValueDiscrete v : valueCountMap.keySet()) {
			int valueCount = valueCountMap.get(v);
			double valueWeight = valueCount / (double) max;
			valueWeightMap.put(v, valueWeight);
		}
	}
}
