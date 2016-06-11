package nl.yannickl88.language;

import nl.yannickl88.language.intent.Entity;
import nl.yannickl88.language.intent.Intent;

import java.util.HashMap;

class IntentBuilder {
    private String action;
    private HashMap<String, Entity> entities;

    IntentBuilder() {
        entities = new HashMap<>();
    }

    void setAction(String action) {
        this.action = action;
    }

    void addEntity(String type, Entity entity) {
        entities.put(type, entity);
    }

    Intent createIntent() {
        return new Intent(action, entities);
    }
}
