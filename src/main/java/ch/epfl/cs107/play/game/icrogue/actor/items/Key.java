package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Key extends Item{
    private final int keyId; // represents the identifier of the key
    private final Sprite key;
    public Key(Area area, Orientation orientation, DiscreteCoordinates position, int value)
    {
        super(area, orientation, position);
        this.keyId = value;
        key = new Sprite("icrogue/key", 0.6f, 0.6f, this);
    }
    public String getTitle(){
        return "icrogue/key";
    }

    // draws the key on the game screen
    // if it hasn't been collected
    @Override
    public boolean draw(Canvas canvas) {
        if(!this.isCollected())
            key.draw(canvas);
        return true;
    }

    // returns the key's identifier
    public int getKeyId(){return keyId;}

    // returns a list of DiscreteCoordinates representing the cells that the key occupies
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }
    @Override
    public boolean isViewInteractable() {return true;}

    // a boolean indicating whether the interaction
    // is a cell interaction or a view interaction
    // calls the appropriate interaction method on the visitor
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

}
