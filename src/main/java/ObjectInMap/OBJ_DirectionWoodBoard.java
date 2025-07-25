package ObjectInMap;

import Entity.Player;
import JavaLearn.GamePanel;
import common.EventManager;
import common.TextBoxUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class OBJ_DirectionWoodBoard extends SuperObject {
    public OBJ_DirectionWoodBoard() {
        name = "DirectionWoodBoard";
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
            player.gp.playSE(2);
            mgr.endEvent();
            this.removeThisObjectFromMap(player);
        }
    }

    @Override
    public void onActionEvent(Graphics2D g2, GamePanel gp, int eventStep) {
        String hint = "Press space or enter to proceed";

        if (eventStep == 0) { // แสดงข้อความชุดแรก
            TextBoxUtil.drawEventBox(
                    g2,
                    gp.screenWidth,
                    List.of(
                            "You found a Sign" // ข้อความบรรทัดเดียว
                    ),
                    hint
            );
        } else if (eventStep == 1) { // แสดงข้อความชุดที่สอง (เมื่อกด Space/Enter ครั้งแรก)
            TextBoxUtil.drawEventBox(
                    g2,
                    gp.screenWidth,
                    List.of(
                            "สถาบันบำบัดจิตเวช",     // บรรทัดที่ 1
                            "          -->          ", // บรรทัดที่ 2 (เว้นช่องไฟให้เท่ากัน)
                            "      สุสานเอเด็น      ", // บรรทัดที่ 3 (เว้นช่องไฟให้เท่ากัน)
                            "          <--          "            // บรรทัดที่ 4 (เว้นช่องไฟให้เท่ากัน)
                    ),
                    hint
            );
        }
    }

    @Override
    public void onInteract(Entity.Player player) {
        if (player.gp.eventManager != null) {
            player.gp.eventManager.startEvent(this);
        }
    }
}