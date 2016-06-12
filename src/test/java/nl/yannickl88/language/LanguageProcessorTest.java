package nl.yannickl88.language;

import nl.yannickl88.language.intent.Intent;
import nl.yannickl88.language.matcher.buildins.Number;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LanguageProcessorTest {
    @Test
    public void testGetIntent() {
        LanguageProcessor processor = new LanguageProcessor();

        Classifier matcher1 = new Classifier("Foo");
        matcher1.addUtterance(Classifier.getWords("foo"));
        Classifier matcher2 = new Classifier("Bar");
        matcher2.addUtterance(Classifier.getWords("bar"));
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

    @Test
    public void testOrganic() throws URISyntaxException {
        LanguageProcessor processor = new LanguageProcessor();
        IntentMatcherLoader loader = new IntentMatcherLoader();

        File testFile = new File(this.getClass().getClassLoader().getResource("organic.xml").toURI());

        loader.load(processor, testFile);

        assertEquals("Generic.Help", processor.getIntent("help").action);
        assertEquals("Food.Create.Order", processor.getIntent("can I order pizza?").action);
    }
}
