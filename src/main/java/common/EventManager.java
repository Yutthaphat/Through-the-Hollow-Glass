package common;

import Entity.Player;

public class EventManager {
    private EventActionObject currentEventObject;
    private int eventStep = 0;
    private boolean eventActive = false;
    private Player player;

    public EventManager(Player player) {
        this.player = player;
    }

    public void startEvent(EventActionObject obj) {
        if (currentEventObject == obj) return;

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
    public EventActionObject getCurrentEventObject() { return currentEventObject; }
    public Player getPlayer() { return player; }
} 