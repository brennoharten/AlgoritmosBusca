package model;

import java.util.Collection;
import java.util.Comparator;
import util.BinaryHeap;

public class BreadthPathAlgorithm implements IPathAlgorithm {

	private ICostEvaluator evaluator;
	private final BinaryHeap<INode> listaNode = new BinaryHeap<INode>(new Comparator<INode>() {
		@Override
		public int compare(INode o1, INode o2) {
			return (o1.getCost() + o1.getHeuristic() - (o2.getCost() + o2.getHeuristic()));
		}
	});

	@Override
	public boolean searchPath(INode start, INode end) {
		try {
			return search(start, end);
		}
		finally {
			listaNode.clear();
		}
	}

	private boolean search(INode start, INode end) {
		INode node = start;
		node.setVisited(true);
		node.setCost(0);

		listaNode.add(node);
		while (listaNode.size() > 0) {
			node = listaNode.remove();
			if (node == null)
			node.setOpen(false);
			Collection<IEdge> edges = node.getEdges();
			for (IEdge edge : edges) {
				Node candidate = (Node) edge.getOpposite(node);
				if (!candidate.isOpen()) {
					continue;
				}
				int cost = evaluator.evaluateCost(candidate, edge, start, end);
				if (!candidate.isVisited()) {
					candidate.setVisited(true);
					candidate.setCost(cost);
					candidate.setPredecessor(node);
					listaNode.add(candidate);
				}
				else if (cost < candidate.getCost()) {
					candidate.setCost(cost);
					candidate.setPredecessor(node);
					listaNode.remove(candidate);
					listaNode.add(candidate);
				}
			}
			if (listaNode.contains(end)) {
				markPath(start, end);
				return true;
			}
		}
		return false;
	}

	private void markPath(INode start, INode end) {
		INode node = end;
		while (node != start) {
			node.setSelected(true);
			node = node.getPredecessor();
		}
		node.setSelected(true);
	}

	public ICostEvaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(ICostEvaluator evaluator) {
		this.evaluator = evaluator;
	}
}
