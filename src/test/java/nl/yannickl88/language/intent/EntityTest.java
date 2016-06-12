package nl.yannickl88.language.intent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityTest {
    @Test
    public void testGeneric() {
        Entity e = new Entity("foobar");

        assertEquals("foobar", e.value);
    }
}
