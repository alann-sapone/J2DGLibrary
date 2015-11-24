package J2DGLibrary.Utils;

import javax.swing.JOptionPane;

public class AsapError {
	
	Exception error;
	
	public AsapError (Exception e)
	{
		this.error = e;
		JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
	}
	
	public AsapError (Exception e, String info)
	{
		this.error = e;
		JOptionPane.showMessageDialog(null, "Error : " + e.getMessage() + " : " + info);
	}
}
