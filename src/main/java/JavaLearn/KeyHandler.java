package JavaLearn;

import common.EventManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{  // รับค่าคีย์บอร์ด
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean interactPressed;
    private EventManager eventManager;
    private GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
        if (gp.eventManager.isEventActive()) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.eventManager.advanceEventStep();
            }
            return;
        }
		if (code == KeyEvent.VK_W){  // W เดินขึ้น 
			upPressed = true;
		}
		if (code == KeyEvent.VK_S){    // S เดินลง
			downPressed = true;
		}	
		if (code == KeyEvent.VK_A){   // A เดินซ้าย
			leftPressed = true;
		}		
		if (code == KeyEvent.VK_D){   // D เดินขวา
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P){
			interactPressed = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W){  // ถ้าปล่อย  W ไม่เดินขึ้น 
			upPressed = false;
		}
		if (code == KeyEvent.VK_S){    // ถ้าปล่อย  S ไม่เดินลง
			downPressed = false;
		}	
		if (code == KeyEvent.VK_A){   // ถ้าปล่อย A ไม่เดินซ้าย
			leftPressed = false;
		}		
		if (code == KeyEvent.VK_D){   // ถ้าปล่อย D ไม่เดินขวา
				rightPressed = false;
			}
		if (code == KeyEvent.VK_P){
			interactPressed = false;
		}
	}
}


