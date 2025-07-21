package CheckpointSystem;

import Entity.Player;
import JavaLearn.GamePanel;
import common.EventManager;
import common.TextBoxUtil;

import java.awt.*;
import java.util.List;

public class Checkpoint_Story extends Checkpoint {
    public Checkpoint_Story(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void onPlayerEnter(Player player, EventManager eventManager) {
        eventManager.startEvent(this);
    }

    @Override
    public void onEventStep(int step, EventManager mgr) {
        if (step == 1) {
            mgr.endEvent();
            //mgr.getPlayer().gp.map.checkpointManager.removeCheckpoint(this);
        }
    }

    @Override
    public void onActionEvent(Graphics2D g2, GamePanel gp, int eventStep) {
        String message = "You reached a story checkpoint!";
        String hint = "Press space or enter to continue";
        TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message), hint);
    }
} 