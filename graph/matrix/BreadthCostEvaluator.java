package matrix;

import model.INode;


public class BreadthCostEvaluator extends AStarCostEvaluator {

	@Override
	public int evaluateHeuristic(INode node, INode start, INode end) {
		return 0;
	}

}
