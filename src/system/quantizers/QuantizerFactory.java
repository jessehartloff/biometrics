package system.quantizers;

import settings.quantizersettings.AllQuantizerSettings;

public class QuantizerFactory {
	
	public static void makeQuantizer(){
		switch(QuantizerEnumerator.valueOf(AllQuantizerSettings.getQuantizer())){
			case BINNING:
				Quantizer.setQuantizer(new Binning());
				break;
			case PCA:
				Quantizer.setQuantizer(new PCA());
				break;
			default:
				System.out.println("Hey, you didn't choose a quantizer!");
				Quantizer.setQuantizer(new Binning());
				break;
		}
	}
	
	public enum QuantizerEnumerator{
		BINNING, PCA; 
	}
}
