package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class ICRogueActor extends MovableAreaEntity {
    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public ICRogueActor(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    // draw the actor on the game screen
    @Override
    public boolean draw(Canvas canvas) {
        return false;
    }

    // returns a list of the cells that the actor occupies
    // used to determine which cells the actor can move through
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return null;
    }

    // returns a boolean indicating whether the actor occupies space within the game area
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    //boolean indicating whether the actor can be interacted with by other actors
    //returns false, other actors are not able to interact with the actor when they are in the same cell
    @Override
    public boolean isCellInteractable() {
        return false;
    }

    // boolean indicating whether the actor can be interacted with by the player
    @Override
    public boolean isViewInteractable() {
        return false;
    }

    // boolean indicating whether the actor is alive or not
    public boolean isAlive(){return true;}

    // allows the actor to accept and respond to interactions from other actors or the player
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }

}
