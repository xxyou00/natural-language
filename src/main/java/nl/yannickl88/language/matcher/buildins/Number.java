package nl.yannickl88.language.matcher.buildins;

import nl.yannickl88.language.EntityMatchable;
import nl.yannickl88.language.intent.Entity;
import nl.yannickl88.language.matcher.EntityMatch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The number EntityMatcher allows for extracting numbers from messages. These
 * can be in the format 42 or 1032.
 */
public class Number implements EntityMatchable {
    private Pattern numberPattern;

    public Number() {
        numberPattern = Pattern.compile("([1-9][0-9]*)");
    }

    @Override
    public EntityMatch match(String message) {
        Matcher m = numberPattern.matcher(message);

        if (m.find()) {
            return new EntityMatch(1, "Buildin.Number", new Entity(m.group()));
        }

        return new EntityMatch();
    }
}
