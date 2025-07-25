package ObjectInMap;

import Entity.Player;
import JavaLearn.GamePanel;
import common.EventManager;
import common.TextBoxUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class OBJ_StopCarWoodBoard extends SuperObject {
    public OBJ_StopCarWoodBoard() {
        name = "StopWoodBoard";
        isInteractive = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/wooden_board.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEventStep(int step, EventManager mgr) {
        if (step == 2) {
            Player player = mgr.getPlayer();
            player.gp.playSE(1);
            mgr.endEvent();
            this.removeThisObjectFromMap(player);
        }
    }

    @Override
    public void onActionEvent(Graphics2D g2, GamePanel gp, int eventStep) {
        String message = (eventStep == 0) ? "You found a Sign" : "ทางปิด ห้ามรถยนต์ผ่าน";
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