package nl.yannickl88.language;

import nl.yannickl88.language.matcher.buildins.StaticWord;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This loader give the ability to load the configuration into the
 * LanguageProcessor from a file. This will allow you to configure all the
 * intents to be parsed by the processor.
 *
 * @see LanguageProcessor
 */
public class IntentMatcherLoader {
    /**
     * Load a configuration file into the given LanguageProcessor.
     */
    public void load(LanguageProcessor processor, String file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(file));

            doc.getDocumentElement().normalize();

            NodeList intents = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < intents.getLength(); i++) {
                Node node = intents.item(i);
                if (!"intent".equals(node.getNodeName())) {
                    continue;
                }

                NodeList intentMatchers = node.getChildNodes();
                IntentMatcher intent = new IntentMatcher(node.getAttributes().getNamedItem("action").getNodeValue());

                for (int j = 0; j < intentMatchers.getLength(); j++) {
                    Node matcher = intentMatchers.item(j);

                    if ("utterance".equals(matcher.getNodeName())) {
                        for (String word : this.split(matcher.getTextContent())) {
                            intent.addMatcher(new StaticWord(word));
                        }
                    } else if ("matcher".equals(matcher.getNodeName())) {
                        intent.addMatcher(this.getWordMatcherLoader(matcher.getFirstChild().getTextContent()));
                    }
                }

                processor.addIntentMatcher(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> split(String message) {
        Pattern p = Pattern.compile("([\\w\\a]+)");
        Matcher m = p.matcher(message);
        ArrayList<String> words = new ArrayList<>();

        while (m.find()) {
            words.add(m.group().toLowerCase());
        }

        return words;
    }

    private EntityMatcherInterface getWordMatcherLoader(String matcherName) {
        try {
            Class c = Class.forName(matcherName);

            return (EntityMatcherInterface) c.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            return null;
        }
    }
}
