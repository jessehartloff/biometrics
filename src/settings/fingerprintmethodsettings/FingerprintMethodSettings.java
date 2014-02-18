package settings.fingerprintmethodsettings;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import settings.MethodSettings;
import settings.Settings;

public abstract class FingerprintMethodSettings extends MethodSettings{

	public abstract String getMethodString();
	
	@Override
	protected JPanel makeJPanel(){
		JPanel panel = super.makeJPanel();
//		panel.add(this.thisJPanel(), BorderLayout.WEST);
//		panel.add(this.makeChildrenJPanel(), BorderLayout.EAST);
		return panel;
	}
	

}
