package CheckpointSystem;

import Entity.Player;
import common.EventManager;
import java.util.List;
import java.util.ArrayList;

public class CheckpointManager {
    private List<Checkpoint> checkpoints = new ArrayList<>();
    private Checkpoint lastTriggered = null;
    private int delayCount = 0;

    public void addCheckpoint(Checkpoint cp) {
        checkpoints.add(cp);
    }

    public void update(Player player, EventManager eventManager) {
        if (lastTriggered != null && delayCount++ >= 300){
            lastTriggered = null;
            delayCount = 0;
        }
        for (Checkpoint cp : checkpoints) {
            if (cp.isPlayerOnCheckpoint(player) && cp != lastTriggered) {
                cp.onPlayerEnter(player, eventManager);
                lastTriggered = cp;
                break;
            }
        }
    }

    public void removeCheckpoint(Checkpoint cp){
        this.checkpoints.remove(cp);
    }
} 