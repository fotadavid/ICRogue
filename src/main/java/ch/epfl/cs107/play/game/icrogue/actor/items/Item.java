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


    public boolean isCollected(){
        return true;
    }

    public void update(){}

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }
}
