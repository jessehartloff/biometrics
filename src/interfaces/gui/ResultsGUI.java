package interfaces.gui;

import java.util.ArrayList;

import org.math.plot.*;

import javax.swing.*;

import system.allcommonclasses.commonstructures.*;
import system.biometricsystem.RatesPoint;

public class ResultsGUI {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Member variables
	 */
	private Results results;
	private JFrame frame;
	private ArrayList<JPanel> plots;
	private ArrayList<JPanel> variableHistograms;
	private ArrayList<JPanel> fieldHistograms;
	
	public ResultsGUI( Results results_) {
		
		frame = new JFrame("Experimental Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(new parent());
        frame.setSize(500, 500);
        
		results = results_;
		plots = new ArrayList<JPanel>();
		variableHistograms = new ArrayList<JPanel>();
		fieldHistograms = new ArrayList<JPanel>();
		
		makePlots(frame);
		
		frame.setVisible(true);
	}
	
	public void makePlots( JFrame frame) {
		/*** EER CURVE ***/
		System.out.print("I'm Makin your plots...");
		//jmathplot likes plain arrays so this is how we get them
		ArrayList<RatesPoint> rates = results.getRates();		
		double[] fars = new double[rates.size()];
		double[] frrs = new double[rates.size()];
		double[] thresholds = new double[rates.size()];
		
		double[] minAxisBounds = new double[2];//[0]:xmin, [1]:ymin
		double[] maxAxisBounds = new double[2];//same but for max
		minAxisBounds[0] = -1.; minAxisBounds[1] = 0.;
		maxAxisBounds[0] = 10.; maxAxisBounds[1] = 1.;
		
		for( int i = 0; i < rates.size(); i++) {
			fars[i] = rates.get(i).getFar().doubleValue();
			frrs[i] = rates.get(i).getFrr().doubleValue();
			thresholds[i] = rates.get(i).getThreshold().doubleValue();
		}
		
        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel eerPlot = new Plot2DPanel("SOUTH");
        eerPlot.addLinePlot("FAR", thresholds, fars);
        eerPlot.addLinePlot("FRR", thresholds, frrs);
        eerPlot.setFixedBounds(minAxisBounds, maxAxisBounds);

        // put the PlotPanel in a JFrame like a JPanel
        frame.setSize(600, 600);
        frame.add(eerPlot);
	}
	
//	public ArrayList<JPanel> makeFieldHistograms(JFrame frame) {
//		
//	}
//	
//	public ArrayList<JPanel> makeVariableHistograms() {
//		
//	}
	

}
