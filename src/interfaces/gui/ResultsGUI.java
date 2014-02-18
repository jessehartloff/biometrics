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
	private ArrayList<JPanel> plots;
	private ArrayList<JPanel> variableHistograms;
	private ArrayList<JPanel> fieldHistograms;
	
	public ResultsGUI( Results results_) {
		
		JFrame frame1 = new JFrame("HTER Curve");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(500, 500);
		JFrame frame2 = new JFrame("Variable Quantization Histogram");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(500, 500);
        
		results = results_;
		plots = new ArrayList<JPanel>();
		variableHistograms = new ArrayList<JPanel>();
		fieldHistograms = new ArrayList<JPanel>();
		
		makePlots(frame1);
//		makeVariableHistograms(frame2);
		
		frame1.setVisible(true);
		frame2.setVisible(true);
	}
	
	public void makePlots( JFrame frame) {
		/*** EER CURVE ***/
		System.out.print("I'm Makin your plots...");
		//jmathplot likes plain arrays so this is how we get them
		ArrayList<RatesPoint> rates = results.getRates();		
		double[] fars = new double[rates.size()];
		double[] frrs = new double[rates.size()];
		double[] thresholds = new double[rates.size()];
		
		boolean zeroFarFound = false; 
		int zeroFarIndex = 0;
		for( int i = 0; i < rates.size(); i++) {
			fars[i] = rates.get(i).getFar().doubleValue();
			if(!zeroFarFound && fars[i] == 0.) {
				zeroFarFound = true;
				zeroFarIndex = i;
			}
			frrs[i] = rates.get(i).getFrr().doubleValue();
			thresholds[i] = rates.get(i).getThreshold().doubleValue();
		}
		
		//setting up plot values
		double[] minAxisBounds = new double[2];//[0]:xmin, [1]:ymin
		double[] maxAxisBounds = new double[2];//same but for max
		minAxisBounds[0] = -1.; minAxisBounds[1] = 0.;
		maxAxisBounds[0] = zeroFarIndex+2; maxAxisBounds[1] = 1.; //+2 to show a little more
		
		//calculate secure error rate
		double HTER = (fars[zeroFarIndex] + frrs[zeroFarIndex]) /2.;
		double[] hterX = { thresholds[zeroFarIndex], thresholds[zeroFarIndex],  thresholds[zeroFarIndex] };
		double[] hterY = { 0., HTER,  1.};
		
        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel eerPlot = new Plot2DPanel("SOUTH");
        eerPlot.addLinePlot("FAR", thresholds, fars);
        eerPlot.addLinePlot("FRR", thresholds, frrs);
        eerPlot.addScatterPlot("HTER", hterX, hterY);
        eerPlot.setFixedBounds(minAxisBounds, maxAxisBounds);
        eerPlot.setAxisLabel(0, "Threshold");
        

        // put the PlotPanel in a JFrame like a JPanel
        frame.setSize(600, 600);
        frame.add(eerPlot);
	}

	public void makeFieldHistograms(JFrame frame) {
		
	}
	
	public void makeVariableHistograms(JFrame frame) {
		JPanel container = new JPanel();
		ArrayList<Histogram> histos = results.getVariableHistograms();
		ArrayList<double[]> variableHistograms = new ArrayList<double []>();
		for( Histogram h : histos ) {
			double[] varHisto = new double[h.histogram.size()];
			int i=0;
			for (long quantizedVal: h.histogram.values()){
				varHisto[i] = quantizedVal;
			}
			variableHistograms.add(varHisto);
		}
		Plot2DPanel histoPlot = new Plot2DPanel("SOUTH");
		for ( double[] h : variableHistograms) {
			System.out.print(h);
			histoPlot.addHistogramPlot("Variable Histograms", h, h.length);
		}
		frame.add(histoPlot);
	}

	

}