package CheckpointSystem;

import Entity.Player;
import common.EventActionObject;
import common.EventManager;
import JavaLearn.GamePanel;
import java.awt.*;

public abstract class Checkpoint implements EventActionObject {
    public int worldX, worldY, width, height;

    public Checkpoint(int worldX, int worldY, int width, int height) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
    }

    public boolean isPlayerOnCheckpoint(Player player) {
        Rectangle playerRect = new Rectangle(player.worldX, player.worldY, player.solidArea.width, player.solidArea.height);
        Rectangle checkpointRect = new Rectangle(worldX, worldY, width, height);
        return playerRect.intersects(checkpointRect);
    }

    public abstract void onPlayerEnter(Player player, EventManager eventManager);

    // Optional: for event flow and UI
    public void onEventStep(int step, EventManager mgr) {}
    public void drawEventMessage(Graphics2D g2, GamePanel gp, int eventStep) {}
} 