package nl.yannickl88.language;

import nl.yannickl88.language.matcher.EntityMatch;

/**
 * Implementation of this interface allow for custom Entity Matches in intents.
 * This allows for specialized data fields in messages.
 */
public interface EntityMatchable {
    /**
     * Try to match an entity from a message and return a EntityMatch result.
     *
     * @param message Message to match
     * @return Return any match information for the given message
     */
    EntityMatch match(String message);
}
