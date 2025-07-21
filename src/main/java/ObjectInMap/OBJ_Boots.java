package ObjectInMap;

import Entity.Player;
import JavaLearn.GamePanel;
import common.EventManager;
import common.TextBoxUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class OBJ_Boots extends SuperObject {
	public OBJ_Boots() {
		name = "Boots";
		isInteractive = true;
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
	public void onActionEvent(Graphics2D g2, GamePanel gp, int eventStep) {
		String message = (eventStep == 0) ? "You found a speed boot" : "You receive speed x2";
		String hint = "Press space or enter to proceed";
		TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message), hint);
	}

	@Override
	public void onInteract(Entity.Player player) {
		if (player.gp.eventManager != null) {
			player.gp.eventManager.startEvent(this);
		}
	}
}