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
    private int keyId;
    private Sprite key;
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
    // key is only drawn if it has not been collected by the player.
    @Override
    public boolean draw(Canvas canvas) {
        if(!this.isCollected())
            key.draw(canvas);
        return true;
    }
    // returns the identifier of the key
    public int getKeyId(){return keyId;}

    // returns a list of DiscreteCoordinates representing the cells occupied by the key
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    // indicates that the key does not occupy space on the game map (can be overlapped by other actors)
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    // indicates that the key can be interacted with by the player when it is in view
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    // cannot be interacted directly by other actors
    @Override
    public boolean isCellInteractable() {
        return false;
    }

    // used to handle interactions with the key
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

}
