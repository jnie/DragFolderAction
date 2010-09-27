package dk.jnie.dragunzip.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

public class MyMouseListener implements MouseListener {

	private Logger logger = Logger.getLogger("dk.jnie.dragunzip.control");

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		logger.info(source.toString());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
