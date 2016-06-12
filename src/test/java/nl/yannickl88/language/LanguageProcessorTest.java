package nl.yannickl88.language;

import nl.yannickl88.language.intent.Intent;
import nl.yannickl88.language.matcher.buildins.Number;
import nl.yannickl88.language.matcher.buildins.StaticWord;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LanguageProcessorTest {
    @Test
    public void testGetIntent() {
        LanguageProcessor processor = new LanguageProcessor();

        IntentMatcher matcher1 = new IntentMatcher("Foo");
        matcher1.addMatcher(new StaticWord("foo"));
        IntentMatcher matcher2 = new IntentMatcher("Bar");
        matcher2.addMatcher(new StaticWord("bar"));
        matcher2.addMatcher(new Number());

        processor.addIntentMatcher(matcher1);
        processor.addIntentMatcher(matcher2);

        List<String> names;

        Intent iFoo = processor.getIntent("foo 12");
        names = iFoo.getEntityNames();

        assertEquals("Foo", iFoo.action);
        assertEquals(0, names.size());

        Intent iBar = processor.getIntent("bar 12");
        names = iBar.getEntityNames();

        assertEquals("Bar", iBar.action);
        assertEquals(1, names.size());
        assertEquals("Buildin.Number", names.get(0));
        assertEquals("12", iBar.getEntity("Buildin.Number").value);

        Intent iBarEmpty = processor.getIntent("bar");
        names = iBarEmpty.getEntityNames();

        assertEquals("Bar", iBarEmpty.action);
        assertEquals(0, names.size());
    }
}
