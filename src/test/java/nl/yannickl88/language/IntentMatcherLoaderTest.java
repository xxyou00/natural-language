package nl.yannickl88.language;

import nl.yannickl88.language.matcher.buildins.Number;
import nl.yannickl88.language.matcher.buildins.StaticWord;
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

        ArrayList<IntentMatcher> intentMatchers = new ArrayList<>();
        IntentMatcherLoadable processor = new IntentMatcherLoadable() {
            @Override
            public void addIntentMatcher(IntentMatcher intent) {
                intentMatchers.add(intent);
            }
        };

        File testFile = new File(this.getClass().getClassLoader().getResource("intent_matcher_loader_test.xml").toURI());

        loader.load(processor, testFile);

        assertEquals(4, intentMatchers.size());

        List<EntityMatchable> words;

        // first
        assertEquals("Foo.Bar", intentMatchers.get(0).action);
        words = intentMatchers.get(0).getWords();
        assertEquals(2, words.size());
        assertEquals(true, words.get(0) instanceof StaticWord);
        assertEquals(true, words.get(1) instanceof StaticWord);
        assertEquals("foo", ((StaticWord) words.get(0)).word);
        assertEquals("bar", ((StaticWord) words.get(1)).word);

        // second
        assertEquals("Foo.Bar", intentMatchers.get(1).action);
        words = intentMatchers.get(1).getWords();
        assertEquals(1, words.size());
        assertEquals(true, words.get(0) instanceof StaticWord);
        assertEquals("baz", ((StaticWord) words.get(0)).word);

        // third
        assertEquals("Foo.Number", intentMatchers.get(2).action);
        words = intentMatchers.get(2).getWords();
        assertEquals(1, words.size());
        assertEquals(true, words.get(0) instanceof Number);

        // forth
        assertEquals("Foo.All", intentMatchers.get(3).action);
        words = intentMatchers.get(3).getWords();
        assertEquals(2, words.size());
        assertEquals(true, words.get(0) instanceof StaticWord);
        assertEquals(true, words.get(1) instanceof Number);
        assertEquals("foobar", ((StaticWord) words.get(0)).word);
    }

    @Test
    public void testLoadBadMethod() throws URISyntaxException {
        IntentMatcherLoader loader = new IntentMatcherLoader();

        ArrayList<IntentMatcher> intentMatchers = new ArrayList<>();
        IntentMatcherLoadable processor = new IntentMatcherLoadable() {
            @Override
            public void addIntentMatcher(IntentMatcher intent) {
                intentMatchers.add(intent);
            }
        };

        File testFile = new File(this.getClass().getClassLoader().getResource("intent_matcher_loader_test_bad_matcher.xml").toURI());

        loader.load(processor, testFile);

        assertEquals(1, intentMatchers.size());

        // first
        assertEquals("Foo.Evil", intentMatchers.get(0).action);
        List<EntityMatchable> words = intentMatchers.get(0).getWords();
        assertEquals(0, words.size());
    }

    @Test
    public void testLoadBadFormat() throws URISyntaxException {
        IntentMatcherLoader loader = new IntentMatcherLoader();

        ArrayList<IntentMatcher> intentMatchers = new ArrayList<>();
        IntentMatcherLoadable processor = new IntentMatcherLoadable() {
            @Override
            public void addIntentMatcher(IntentMatcher intent) {
                intentMatchers.add(intent);
            }
        };

        File testFile = new File(this.getClass().getClassLoader().getResource("intent_matcher_loader_test_bad_format.xml").toURI());

        loader.load(processor, testFile);

        assertEquals(0, intentMatchers.size());
    }
}
