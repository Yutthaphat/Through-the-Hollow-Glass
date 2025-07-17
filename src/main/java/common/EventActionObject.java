package common;

import JavaLearn.GamePanel;

import java.awt.*;

public interface EventActionObject {
    // Called by EventManager on each step
    void onEventStep(int step, EventManager mgr);
    // Called by EventManager to trigger action
    void onActionEvent(Graphics2D g2, GamePanel gp, int eventStep);
}
