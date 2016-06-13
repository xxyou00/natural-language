package nl.yannickl88.language;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

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
     *
     * @param processor Processor to load the file into
     * @param file File containing processor configuration.
     */
    public void load(IntentMatcherLoadable processor, File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList intents = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < intents.getLength(); i++) {
                Node node = intents.item(i);
                if (!"intent".equals(node.getNodeName())) {
                    continue;
                }

                NodeList intentMatchers = node.getChildNodes();
                Classifier intent = new Classifier(node.getAttributes().getNamedItem("action").getNodeValue());

                for (int j = 0; j < intentMatchers.getLength(); j++) {
                    Node matcher = intentMatchers.item(j);

                    if ("utterance".equals(matcher.getNodeName())) {
                        intent.addUtterance(Classifier.getWords(matcher.getFirstChild().getTextContent()));
                    } else if ("matcher".equals(matcher.getNodeName())) {
                        EntityMatchable matcherLoader = this.getWordMatcherLoader(matcher.getFirstChild().getTextContent());

                        if (null != matcherLoader) {
                            intent.addMatcher(matcherLoader);
                        }
                    }
                }

                processor.addIntentMatcher(intent);
            }

        } catch (Exception e) {
            return;
        }
    }

    private EntityMatchable getWordMatcherLoader(String matcherName) {
        try {
            Class c = Class.forName(matcherName);

            return (EntityMatchable) c.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            return null;
        }
    }
}
