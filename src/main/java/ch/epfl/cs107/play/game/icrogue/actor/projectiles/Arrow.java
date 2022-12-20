package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class Arrow extends Projectile{

    private int damagePoints;
    private Sprite arrow;

    private final static int MOVE_DURATION = 8;


    public Arrow(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        arrow = new Sprite("zelda/arrow", 1f, 1f, this, new RegionOfInterest(32*orientation.ordinal(), 0, 32, 32), new Vector(0, 0));
        damagePoints = 2;
    }
    public int getDamage()
    {
        return damagePoints;
    }
    // returns the string of the Arrow
    public String getTitle() {
        return "zelda/arrow";
    }

    // updates the Arrow object's position and check if it has been consumed
    @Override
    public void update(float deltaTime) {
        move(5);
        super.update(deltaTime);
        if(isConsumed())
            getOwnerArea().unregisterActor(this);
    }

    // draws the arrow on the game screen
    @Override
    public boolean draw(Canvas canvas) {
        arrow.draw(canvas);
        return false;
    }

    // returns a singleton list containing only the coordinates of the cell that the arrow is located in
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    // returns a boolean value indicating whether the Staff can be interacted with by other entities
    public boolean isCellInteractable() {
        return true;
    }

    // returns a boolean value indicating whether the Arrow object occupies space in the cells it is in
    public boolean takeCellSpace() {
        return false;
    }

    // allows the Arrow object to interact with another Interactable
    public void interactWith(Interactable other, boolean isCellInteraction )
    {
        other.acceptInteraction(handler, isCellInteraction);
    }

    // allows the Arrow to receive interactions from other objects
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    // inner class that implements the ICRogueInteractionHandler interface
    // interactWith method that determines how the Arrow object should react
    // when it interacts with a cell in the game
    Arrow.ICRogueArrowInteractionHandler handler = new Arrow.ICRogueArrowInteractionHandler();
    private  class ICRogueArrowInteractionHandler implements ICRogueInteractionHandler
    {
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction)
        {
            switch(cell.getType())
            {
                case WALL, HOLE -> consume();
            }
        }
        public void interactWith(ICRoguePlayer other, boolean isCellInteraction)
        {
            if(isCellInteraction) {
                consume();
                other.setHp(other.getHp() - getDamage());
            }
        }
    }

}

