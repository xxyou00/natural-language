# natural-language
Small library for natural language parsing. This project was heavily inspired by the Microsoft Bot Framework in combination with their LUIS service.

## Usage
First you need to create your intent file. This is an XML describing the intents you are planning to use in your application.

An example of this would be:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE intents PUBLIC "-//yannickl88//DTD Intents//EN"
  "https://yannickl88.github.io/natural-language/intents.dtd">

<intents>

    <!-- Greetings -->
    <intent action="Generic.Greeting">
        <utterance>hey</utterance>
    </intent>
    <intent action="Generic.Greeting">
        <utterance>hi</utterance>
    </intent>

    <!-- Orderings -->
    <intent action="Create.Order">
        <utterance>i would like to order number</utterance>
        <matcher>nl.yannickl88.language.matcher.buildins.Number</matcher>
    </intent>

    <!-- Queries -->
    <intent action="Query.Order">
        <utterance>what did i order?</utterance>
    </intent>

</intents>
```

Now you are ready to create a processor and load your configuration.

```java
LanguageProcessor   processor    = new LanguageProcessor();
IntentMatcherLoader intentLoader = new IntentMatcherLoader();

intentLoader.load(processor, "intents.xml");
```

To use the processor, simply call the `getIntent` method.
```java
Intent intent = this.processor.getIntent("...");

if (intent.action.equals("Create.Order")) {
    Entity item = intent.getEntity("Buildin.Number");
    if (null != item) {
        System.out.println("OK! Thanks for your order");
    } else {
        System.out.println("Sorry, you need to give me a number to order.");
    }
}
```

And that is it.