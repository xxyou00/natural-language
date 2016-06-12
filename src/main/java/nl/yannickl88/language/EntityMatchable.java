package nl.yannickl88.language;

import nl.yannickl88.language.matcher.EntityMatch;

/**
 * Implementation of this interface allow for custom Entity Matches in intents.
 * This allows for specialized data fields in messages.
 */
public interface EntityMatchable {
    /**
     * Try to match an entity from a message and return a EntityMatch result.
     */
    EntityMatch match(String message);
}
