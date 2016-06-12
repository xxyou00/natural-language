package nl.yannickl88.language.intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An intent represents an action the user wants to perform. This can be
 * something like ordering an item of querying for information.
 *
 * Intents can have entities attached to them. These contain specialized
 * information such as Locations, Numbers or Dates.
 */
public class Intent {
    /**
     * The action the Intent represents.
     */
    public final String action;
    private Map<String, Entity> entities;

    public Intent(String action) {
        this(action, new HashMap<>());
    }

    public Intent(String action, Map<String, Entity> entities) {
        this.action = action;
        this.entities = entities;
    }

    /**
     * Return an entity based on the name. If no entity with the name is found,
     * NULL is returned.
     */
    public Entity getEntity(String s) {
        return entities.get(s);
    }

    /**
     * Return all entity names that are associated with this intent.
     */
    public List<String> getEntityNames() {
        return new ArrayList<>(entities.keySet());
    }
}
