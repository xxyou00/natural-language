package nl.yannickl88.language;

import nl.yannickl88.language.intent.Intent;

/**
 * Implementation of this interface are able to process a message and result
 * the intent of a user.
 */
public interface LanguageProcessable {
    /**
     * Return the intent of a message. This is based all configured intents in
     * the processor and selects the best matching Intent. If no match can be
     * found, an Intent with action "None" is returned.
     */
    Intent getIntent(String message);
}
