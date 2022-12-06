package ch.epfl.cs107.play.game.icrogue.actor.projectiles;
public interface Consumable {
    default void consume(){};
    boolean isConsumed();

    void update();
}
