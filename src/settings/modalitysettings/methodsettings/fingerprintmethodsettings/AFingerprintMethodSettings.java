package settings.modalitysettings.methodsettings.fingerprintmethodsettings;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import settings.Settings;

public abstract class AFingerprintMethodSettings extends Settings{

	public abstract String getMethodString();
	@Override
	protected JPanel makeJPanel(){
		JPanel panel = super.makeJPanel();
//		panel.add(this.thisJPanel(), BorderLayout.WEST);
//		panel.add(this.makeChildrenJPanel(), BorderLayout.EAST);
		return panel;
	}
	

}
