package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import matrix.AStarCostEvaluator;
import matrix.DijkstraCostEvaluator;
import model.ICostEvaluator;
import model.XAStarPathAlgorithm;
import model.XMatrix;
import model.XNode;




public class PathFinder {

	@SuppressWarnings("serial")
	static class CanvasPanel extends JPanel {

		JLabel statusLabel = new JLabel(" ", JLabel.CENTER);
		
		public void setStatus(String text) {
			if (text != null && text.length() > 0 ) {
				statusLabel.setText(text);
			}
			else {
				statusLabel.setText(" ");
			}
		}
		
		public CanvasPanel(String name, Canvas canvas) {
			this.setLayout(new BorderLayout());
			JLabel titleLabel = new JLabel(name, JLabel.CENTER);
			add(BorderLayout.NORTH, titleLabel);
			add(BorderLayout.CENTER, canvas);
			add(BorderLayout.SOUTH, statusLabel);
		}

	}

	Canvas createCanvas(final Parameters parameters, final ICostEvaluator evaluator) {
		final XMatrix matrix = new XMatrix();
		matrix.setEvaluator(evaluator);
		final Canvas canvas = new Canvas();
		canvas.setParameters(parameters);
		canvas.setMatrix(matrix);
		matrix.setNodeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (parameters.getAnimationMs() == 0) {
					return;
				}
				try {
					canvas.repaint();
					Thread.sleep(parameters.getAnimationMs());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		return canvas;
	}

	public void demo() {
		final Parameters parameters = new Parameters();
		final ControlPanel controlPanel = new ControlPanel(parameters);

		final Coordinator coordinator = new Coordinator();
		coordinator.setControlPanel(controlPanel);

		final DijkstraCostEvaluator evaluator1 = new DijkstraCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm1 = new XAStarPathAlgorithm();
		pathAlgorithm1.setEvaluator(evaluator1);
		final Canvas canvas1 = createCanvas(parameters, evaluator1);
		final CanvasPanel canvasPanel1 = new CanvasPanel("A*", canvas1);

		final DijkstraCostEvaluator evaluator2 = new DijkstraCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm2 = new XAStarPathAlgorithm();
		pathAlgorithm2.setEvaluator(evaluator2);
		final Canvas canvas2 = createCanvas(parameters, evaluator2);
		final CanvasPanel canvasPanel2 = new CanvasPanel("Dijkstra", canvas2);
		
		final DijkstraCostEvaluator evaluator3 = new DijkstraCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm3 = new XAStarPathAlgorithm();
		pathAlgorithm3.setEvaluator(evaluator3);
		final Canvas canvas3 = createCanvas(parameters, evaluator3);
		final CanvasPanel canvasPanel3 = new CanvasPanel("Teste1", canvas3);

		final DijkstraCostEvaluator evaluator4 = new DijkstraCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm4 = new XAStarPathAlgorithm();
		pathAlgorithm4.setEvaluator(evaluator4);
		final Canvas canvas4 = createCanvas(parameters, evaluator4);
		final CanvasPanel canvasPanel4 = new CanvasPanel("Teste2", canvas4);

		final DijkstraCostEvaluator evaluator5 = new DijkstraCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm5 = new XAStarPathAlgorithm();
		pathAlgorithm5.setEvaluator(evaluator5);
		final Canvas canvas5 = createCanvas(parameters, evaluator5);
		final CanvasPanel canvasPanel5 = new CanvasPanel("Teste3", canvas5);

		final DijkstraCostEvaluator evaluator6 = new DijkstraCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm6 = new XAStarPathAlgorithm();
		pathAlgorithm6.setEvaluator(evaluator6);
		final Canvas canvas6 = createCanvas(parameters, evaluator6);
		final CanvasPanel canvasPanel6 = new CanvasPanel("Teste4", canvas6);
		
		coordinator.add(canvas1, pathAlgorithm1, evaluator1);
		coordinator.add(canvas2, pathAlgorithm2, evaluator2);
		coordinator.add(canvas3, pathAlgorithm3, evaluator3);
		coordinator.add(canvas4, pathAlgorithm4, evaluator4);
		coordinator.add(canvas5, pathAlgorithm5, evaluator5);
		coordinator.add(canvas6, pathAlgorithm6, evaluator6);
		PropertyChangeListener propertyListener = new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Object source = evt.getSource();
				String propertyName = evt.getPropertyName();
				if (propertyName.equals(AppConstant.SearchStarted)) {
					if (source == canvas1) {
						canvasPanel1.setStatus(null);
					}
					else if (source == canvas2) {
						canvasPanel2.setStatus(null);
					}
					else if (source == canvas3) {
						canvasPanel3.setStatus(null);
					}
					else if (source == canvas4) {
						canvasPanel4.setStatus(null);
					}
					else if (source == canvas5) {
						canvasPanel5.setStatus(null);
					}
					else if (source == canvas6) {
						canvasPanel6.setStatus(null);
					}
				}
				else if (propertyName.equals(AppConstant.SearchCompleted)) {
					if (source == canvas1) {
						canvasPanel1.setStatus(format(canvas1.getMatrix()));
					}
					else if (source == canvas2) {
						canvasPanel2.setStatus(format(canvas2.getMatrix()));
					}
					else if (source == canvas3) {
						canvasPanel3.setStatus(format(canvas3.getMatrix()));
					}
					else if (source == canvas4) {
						canvasPanel4.setStatus(format(canvas4.getMatrix()));
					}
					else if (source == canvas5) {
						canvasPanel5.setStatus(format(canvas5.getMatrix()));
					}
					else if (source == canvas6) {
						canvasPanel6.setStatus(format(canvas6.getMatrix()));
					}
				}
			}
			
			String format(XMatrix matrix) {
				StringBuilder sb = new StringBuilder();
				sb.append("cost: ");
				sb.append(matrix.getEnd().getCost());
				sb.append(", visited: ");
				sb.append(countVisistedNodes(matrix));
				return sb.toString();
			}
			
			int countVisistedNodes(XMatrix matrix) {
				int count = 0;
				for (int row = 0; row < matrix.getRows(); row++) {
					for (int col = 0; col < matrix.getColumns(); col++) {
						XNode node = matrix.getValue(row, col);
						if (node.isVisited()) {
							count++;
						}
					}
				}
				return count;
			}
			
		};
		coordinator.addPropertyChangeListener(canvas1, propertyListener);
		coordinator.addPropertyChangeListener(canvas2, propertyListener);
		coordinator.addPropertyChangeListener(canvas3, propertyListener);
		coordinator.addPropertyChangeListener(canvas4, propertyListener);
		coordinator.addPropertyChangeListener(canvas5, propertyListener);
		coordinator.addPropertyChangeListener(canvas6, propertyListener);

		JPanel overallPanel = new JPanel(new BorderLayout());
		overallPanel.add(controlPanel, BorderLayout.WEST);
		JPanel mainPanel = new JPanel(new GridLayout(2,0,10,10));
		mainPanel.add(canvasPanel1);
		mainPanel.add(canvasPanel2);
		mainPanel.add(canvasPanel3);
		mainPanel.add(canvasPanel4);
		mainPanel.add(canvasPanel5);
		mainPanel.add(canvasPanel6);
		overallPanel.add(mainPanel, BorderLayout.CENTER);
		/* JPanel mainPanel2 = new JPanel(new GridLayout(1,0,10,10));
		mainPanel2.add(canvasPanel1);
		mainPanel2.add(canvasPanel2);
		mainPanel2.add(canvasPanel3);
		overallPanel.add(mainPanel2, BorderLayout.SOUTH); */

		JFrame frame = new JFrame("Path Finder");
		frame.getContentPane().add(overallPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(840, 420));
		frame.setSize(1200, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new PathFinder().demo();
	}

}


