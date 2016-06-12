package nl.yannickl88.language.matcher;

import nl.yannickl88.language.intent.Entity;

public class EntityMatch {
    public final float score;
    private String entityType;
    private Entity entity;

    public EntityMatch() {
        this(0);
    }

    public EntityMatch(float score) {
        this.score = score;
    }

    public EntityMatch(float score, String entityType, Entity entity) {
        this.score = score;
        this.entityType = entityType;
        this.entity = entity;
    }

    public boolean hasEntity() {
        return entity != null;
    }

    public String getEntityType() {
        return entityType;
    }

    public Entity getEntity() {
        return entity;
    }
}
