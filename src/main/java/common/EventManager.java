package common;

import Entity.Player;
import ObjectInMap.SuperObject;

public class EventManager {
    private SuperObject currentEventObject;
    private int eventStep = 0;
    private boolean eventActive = false;
    private Player player;

    public EventManager(Player player) {
        this.player = player;
    }

    public void startEvent(SuperObject obj) {
        currentEventObject = obj;
        eventStep = 0;
        eventActive = true;
        player.lock();
        obj.onEventStep(eventStep, this);
    }

    public void advanceEventStep() {
        if (!eventActive || currentEventObject == null) return;
        eventStep++;
        currentEventObject.onEventStep(eventStep, this);
    }

    public void endEvent() {
        eventActive = false;
        player.unlock();
        currentEventObject = null;
    }

    public boolean isEventActive() { return eventActive; }
    public int getEventStep() { return eventStep; }
    public SuperObject getCurrentEventObject() { return currentEventObject; }
    public Player getPlayer() { return player; }
} 