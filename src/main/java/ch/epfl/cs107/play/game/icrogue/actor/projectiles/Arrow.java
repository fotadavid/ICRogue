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
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Arrow extends Projectile{

    private int damagePoints; // represents the damage that the arrow will do when it hits something
    private Sprite arrow;

    //private final static int MOVE_DURATION = 8;


    public Arrow(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        arrow = new Sprite("zelda/arrow", 1f, 1f, this, new RegionOfInterest(32*orientation.ordinal(), 0, 32, 32), new Vector(0, 0));
        damagePoints = 2;
    }

    // returns the value of the damagePoints instance variable
    public int getDamage()
    {
        return damagePoints;
    }
    public String getTitle() {
        return "zelda/arrow";
    }

    // updates the status of the game
    // moves the arrow by 5 units and checks if it has been "consumed" (hit the player or the wall)
    // if it is, it removes the arrow
    @Override
    public void update(float deltaTime) {
        move(5);
        super.update(deltaTime);
        if(isConsumed())
            getOwnerArea().unregisterActor(this);
    }

    // draw the arrow on the game screen
    @Override
    public boolean draw(Canvas canvas) {
        arrow.draw(canvas);
        return false;
    }

    // returns a list with the arrow's current main cell coordinates
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    public boolean isCellInteractable() {
        return true;
    }
    public boolean takeCellSpace() {
        return false;
    }

    //
    public void interactWith(Interactable other, boolean isCellInteraction )
    {
        other.acceptInteraction(handler, isCellInteraction);
    }

    // a boolean indicating whether the interaction
    // is a cell interaction or a view interaction
    // calls the appropriate interaction method on the visitor
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    // if the cell is a wall or a hole, the arrow is consumed
    // it consumes the arrow and reduces the player's hit points by the arrow's damage
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
            consume();
            other.setHp(other.getHp() - getDamage());
        }
    }

}

