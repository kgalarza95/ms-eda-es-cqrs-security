package ec.com.sofka.generics.utils;

//3. Generics creation to apply DDD: Entity
/*An Entity receives an I (ID, but this I must extends from Identity) */
public abstract class Entity<I extends Identity> {
    private final I id;

    protected Entity(final I id) {
        this.id = id;
    }

    public I getId() {
        return id;
    }

    //Other methods that can be useful
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
