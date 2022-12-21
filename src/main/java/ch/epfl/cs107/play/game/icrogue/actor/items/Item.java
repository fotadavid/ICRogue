package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Item extends CollectableAreaEntity {


    public Item(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    public void update(){}

    // returns a boolean indicating whether item can be interacted with by the player
    @Override
    public boolean isViewInteractable() {
        return false;
    }

    // returns a boolean indicating whether item can be interacted with by other entities
    @Override
    public boolean isCellInteractable() {
        return false;
    }
}
