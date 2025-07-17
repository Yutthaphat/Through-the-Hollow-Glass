package ObjectInMap;

import Entity.Player;
import JavaLearn.GamePanel;
import common.EventManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Boots extends SuperObject {
	public OBJ_Boots() {
		name = "Boots";
		isInteractive = true;
		isEventTrigger = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEventStep(int step, EventManager mgr) {
		if (step == 2) {
			Player player = mgr.getPlayer();
			player.gp.playSE(2);
			mgr.getPlayer().speed *= 2;
			mgr.endEvent();
			this.removeThisObjectFromMap(player);
		}
	}

	@Override
	public void drawEventMessage(Graphics2D g2, GamePanel gp, int eventStep) {
		int boxWidth = 400;
		int boxHeight = 120;
		int x = (gp.screenWidth - boxWidth) / 2;
		int y = 40;
		g2.setColor(new Color(0, 0, 0, 180));
		g2.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.WHITE);
		g2.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 22));
		String message = (eventStep == 0) ? "You found a speed boot" : "You receive speed x2";
		int textWidth = g2.getFontMetrics().stringWidth(message);
		g2.drawString(message, x + (boxWidth - textWidth) / 2, y + 60);
		g2.setFont(new Font("Arial", Font.PLAIN, 14));
		String hint = "Press space or enter to proceed";
		int hintWidth = g2.getFontMetrics().stringWidth(hint);
		g2.drawString(hint, x + boxWidth - hintWidth - 10, y + boxHeight - 10);
	}

	@Override
	public void onInteract(Entity.Player player) {
		if (isEventTrigger && player.gp.eventManager != null) {
			player.gp.eventManager.startEvent(this);
		}
	}
}