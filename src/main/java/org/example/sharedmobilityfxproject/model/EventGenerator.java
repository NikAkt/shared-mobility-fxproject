package org.example.sharedmobilityfxproject.model;

import java.util.Random;

public class EventGenerator {
    private Random random = new Random();

    // Enum to represent different types of events.
    public enum Event {
        BONUS_GEM, ROUTE_CLOSURE, NONE // Adding 'NONE' for turns when no event occurs.
    }

    public Event generateEvent() {
        int chance = random.nextInt(100); // Simple probability example.

        if (chance < 10) {
            return Event.BONUS_GEM;
        } else if (chance < 20) {
            return Event.ROUTE_CLOSURE;
        } else {
            return Event.NONE;
        }
    }

    // Add methods to handle the effects of different events.
}
