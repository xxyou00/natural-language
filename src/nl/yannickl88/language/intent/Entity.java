package nl.yannickl88.language.intent;

/**
 * An Entity represents a specialized data element in an intent. This can be items
 * like Locations, Numbers or Dates.
 */
public class Entity {
    /**
     * The value of the Entity.
     */
    public final String value;

    public Entity(String value) {
        this.value = value;
    }
}
