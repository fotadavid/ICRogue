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

// constructor class
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
    @Override
    public boolean draw(Canvas canvas) {
        if(!this.isCollected())
            key.draw(canvas);
        return true;
    }
    public int getKeyId(){return keyId;}
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
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

}
