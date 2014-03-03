package interfaces.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

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
		results = results_;
		
		/*** HTER CURVE ***/
		JFrame HTERframe = new JFrame("HTER Curve");
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HTERframe.setSize(500, 500);
        makeHTER(HTERframe);
        //HTERframe.setVisible(true);
		
        /*** Variable Histograms ***/
        JFrame varFrame = new JFrame("Variable Quantization Histograms");
//        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        varFrame.setSize(1000, 600);
//        variableHistograms = new ArrayList<JPanel>();
	    makeVariableHistograms(varFrame);
        //varFrame.setVisible(true);
        
		/*** Field Histograms ***/
        //fieldHistograms = new ArrayList<JPanel>();
        JFrame fieldFrame = new JFrame("Field Histogram");
//      frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fieldFrame.setSize(600, 300);
//      variableHistograms = new ArrayList<JPanel>();
	    makeFieldHistogram(fieldFrame);
        //fieldFrame.setVisible(true);
        
        //plots = new ArrayList<JPanel>();
		
		
		
		

		
		
	}
	
	public void makeHTER( JFrame frame) {
		/*** HALF TOTAL ERROR RATE (HTER)  CURVE ***/
		//System.out.print("I'm Makin your plots...");
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
		double[] hterY = { 0., HTER,  frrs[zeroFarIndex]};
		
        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel eerPlot = new Plot2DPanel("SOUTH");
        //HTER Label
        String HTERlabel = new Double(HTER).toString();
        int HTERround = 5;
        if (HTERlabel.length() < 5) {HTERround = HTERlabel.length()-1;}
        eerPlot.addLabel("HTER: "+HTERlabel.substring(0, HTERround), Color.BLACK, 
        		thresholds[zeroFarIndex]+ .5, HTER); //next to dot   
        
        //ZeroFAR FRR
        String ZFARlabel = new Double(frrs[zeroFarIndex]).toString();
        int ZFARround = 5;
        if (ZFARlabel.length() < 5) {ZFARround = ZFARlabel.length()-1;}
        eerPlot.addLabel("FRR at ZeroFAR: "+ZFARlabel.substring(0, ZFARround), Color.RED, 
        		thresholds[zeroFarIndex]+ .55, frrs[zeroFarIndex] - .05 ); //next to dot
        eerPlot.addLinePlot("FAR", thresholds, fars);
        eerPlot.addLinePlot("FRR", thresholds, frrs);
        eerPlot.addScatterPlot("HTER", Color.BLACK, hterX, hterY);
        eerPlot.setFixedBounds(minAxisBounds, maxAxisBounds);
        eerPlot.setAxisLabel(0, "Threshold");
        

        // put the PlotPanel in a JFrame like a JPanel
        frame.setSize(600, 600);
        frame.add(eerPlot);
        frame.setVisible(true);
	}

	public void makeFieldHistogram(JFrame frame) {
		Histogram histo = results.getFieldHistogram();
		//failsafe
		if (histo.histogram.isEmpty()) {
			System.out.println("We didn't calculate field histogram.  Turn 'Histogram' ON next time");
			return;
		}
		
		//do the work
		double[] fieldHisto = new double[histo.histogram.size()];
		int i=0;
		for (Long fieldVal: histo.histogram.values() ){
			fieldHisto[i] = (double) fieldVal;
			i++;
		}
		
		String name = histo.getVariableName();
		Plot2DPanel histoPlot = new Plot2DPanel("SOUTH");
		//named after variable name
		histoPlot.addBarPlot(name, fieldHisto);
		histoPlot.setFixedBounds(0, 0, histo.histogram.size());
		
        //histoPlot.setFixedBounds(1, 0, max);
		//add this to grid
		frame.add(histoPlot);
		frame.setVisible(true);
	}
	
	public void makeVariableHistograms(JFrame frame) {
		//get result histos and format as arrays like jmath likes
		ArrayList<Histogram> histos = results.getVariableHistograms();
		//fail safe
		if (histos.isEmpty()) {
			System.out.println("We didn't calculate variable histograms.  Turn 'Histogram' ON next time");
			return;
		}
		//do the work
		
		ArrayList<double[]> variableHistograms = new ArrayList<double []>();
		ArrayList<String> variableNames = new ArrayList<String>(); //for histo titles
		for( Histogram h : histos ) {
			double[] varHisto = new double[h.histogram.size()];
			int i=0;
			for (long quantizedVal: h.histogram.values()){
				varHisto[i] = quantizedVal;
				i++;
			}
			variableHistograms.add(varHisto);
			variableNames.add(h.getVariableName());
		}
		//make container and set layout
		JPanel gridContainer = new JPanel();
		GridLayout experimentLayout = new GridLayout(0,3);
		gridContainer.setLayout(experimentLayout);
		
		//form histogram plots and add them to the container
		Iterator<double[]> histoIt = variableHistograms.iterator();
		Iterator<String> nameIt = variableNames.iterator();
		while ( nameIt.hasNext() && histoIt.hasNext()){
			double[] h = histoIt.next();
			String name = nameIt.next();
			//find max value of double array
			double maxVal = 0;
			for (int i=0; i<h.length; i++){ if (h[i] > maxVal) { maxVal = h[i]; } }
			
			//to fit our histogram to the way jmathplot does it
			// we need to make a bar graph with labels as indices... I know it seems convoluted
			double[] bins = new double[h.length];
			
			for (double i=0; i < h.length; i++){ bins[(int) i] = i; }
			//System.out.print(h);
			//new plotpanel with legend at bottom
			Plot2DPanel histoPlot = new Plot2DPanel("SOUTH");
			//named after variable name
			histoPlot.addBarPlot(name, bins, h);
			histoPlot.setFixedBounds(1, 0, maxVal);
			//add this to grid
			gridContainer.add(histoPlot);
		}
		
		frame.add(gridContainer);
		frame.setVisible(true);
	}

	

}
