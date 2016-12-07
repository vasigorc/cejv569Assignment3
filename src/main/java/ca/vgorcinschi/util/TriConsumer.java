package ca.vgorcinschi.util;

/**
 * this is a custom consumer that is based on
 * Java's BiConsumer idea - https://docs.oracle.com/javase/8/docs/api/java/util/function/BiConsumer.html
 * @author vgorcinschi
 */
@FunctionalInterface
public interface TriConsumer<P, D, T> {
    public void accept(P p, D d, T t);
}
