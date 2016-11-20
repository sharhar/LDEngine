package engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{
	
	volatile public static int posX, posY, dragPosX, dragPosY;
	volatile public static boolean mouseLeftPressed, mouseRightPressed;
	
	public static void init() {
		posX = -1;
		posY = -1;
		dragPosX = -1;
		dragPosY = -1;
		
		mouseLeftPressed = false;
		mouseRightPressed = false;
	}

	public void mouseDragged(MouseEvent e) {
		dragPosX = e.getX();
		dragPosY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		posX = e.getX();
		posY = e.getY();
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mouseLeftPressed = true;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			mouseRightPressed = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mouseLeftPressed = false;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			mouseRightPressed = false;
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
