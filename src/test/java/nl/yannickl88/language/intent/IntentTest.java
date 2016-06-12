package nl.yannickl88.language.intent;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class IntentTest {
    @Test
    public void testGeneric() {
        Entity entity = new Entity("foobar");

        HashMap<String,Entity> entities = new HashMap<>();
        entities.put("Foo.Entity", entity);

        Intent i = new Intent("Foo.Bar", entities);

        assertEquals("Foo.Bar", i.action);
        assertEquals(entity, i.getEntity("Foo.Entity"));
        assertEquals(null, i.getEntity("Bar.Entity"));
    }

    @Test
    public void testEmptyIntent() {
        Intent i = new Intent("Foo.Bar");

        assertEquals("Foo.Bar", i.action);
        assertEquals(null, i.getEntity("Foo.Entity"));
        assertEquals(null, i.getEntity("Bar.Entity"));
    }
}
