package nl.yannickl88.language;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntentMatcherLoaderTest {
    @Test
    public void testLoad() throws URISyntaxException {
        IntentMatcherLoader loader = new IntentMatcherLoader();

        ArrayList<Classifier> intentMatchers = new ArrayList<>();
        IntentMatcherLoadable processor = new IntentMatcherLoadable() {
            @Override
            public void addIntentMatcher(Classifier intent) {
                intentMatchers.add(intent);
            }
        };

        File testFile = new File(this.getClass().getClassLoader().getResource("intent_matcher_loader_test.xml").toURI());

        loader.load(processor, testFile);

        assertEquals(3, intentMatchers.size());

        List<String> words;
        List<EntityMatchable> matchers;

        // first
        assertEquals("Foo.Bar", intentMatchers.get(0).action);
        words = intentMatchers.get(0).getWords();
        matchers = intentMatchers.get(0).getMatchers();
        assertEquals(3, words.size());
        assertEquals(0, matchers.size());
        assertEquals("foo", words.get(1));
        assertEquals("bar", words.get(0));
        assertEquals("baz", words.get(2));

        // third
        assertEquals("Foo.Number", intentMatchers.get(1).action);
        words = intentMatchers.get(1).getWords();
        matchers = intentMatchers.get(1).getMatchers();
        assertEquals(0, words.size());
        assertEquals(1, matchers.size());

        // forth
        assertEquals("Foo.All", intentMatchers.get(2).action);
        words = intentMatchers.get(2).getWords();
        matchers = intentMatchers.get(2).getMatchers();
        assertEquals(1, words.size());
        assertEquals(1, matchers.size());
        assertEquals("foobar", words.get(0));
    }

    @Test
    public void testLoadBadMethod() throws URISyntaxException {
        IntentMatcherLoader loader = new IntentMatcherLoader();

        ArrayList<Classifier> intentMatchers = new ArrayList<>();
        IntentMatcherLoadable processor = new IntentMatcherLoadable() {
            @Override
            public void addIntentMatcher(Classifier intent) {
                intentMatchers.add(intent);
            }
        };

        File testFile = new File(this.getClass().getClassLoader().getResource("intent_matcher_loader_test_bad_matcher.xml").toURI());

        loader.load(processor, testFile);

        assertEquals(1, intentMatchers.size());

        // first
        assertEquals("Foo.Evil", intentMatchers.get(0).action);
        List<String> words = intentMatchers.get(0).getWords();
        assertEquals(0, words.size());
    }

    @Test
    public void testLoadBadFormat() throws URISyntaxException {
        IntentMatcherLoader loader = new IntentMatcherLoader();

        ArrayList<Classifier> intentMatchers = new ArrayList<>();
        IntentMatcherLoadable processor = new IntentMatcherLoadable() {
            @Override
            public void addIntentMatcher(Classifier intent) {
                intentMatchers.add(intent);
            }
        };

        File testFile = new File(this.getClass().getClassLoader().getResource("intent_matcher_loader_test_bad_format.xml").toURI());

        loader.load(processor, testFile);

        assertEquals(0, intentMatchers.size());
    }
}
