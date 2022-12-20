package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Canvas;


import java.util.Collections;
import java.util.List;


public class Fire extends Projectile {

    private int damagePoints;
    private int framePoints;
    private Sprite fire;
    private Keyboard keyboard;

    private final static int MOVE_DURATION = 8;


    public Fire(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        fire = new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0));
        damagePoints = 1;
        framePoints = 5;
    }

    // returns the string of the Fire
    public String getTitle() {
        return "zelda/fire";
    }

    // moves the projectile forward by calling the move method with a fixed distance
    // and calls the update method of the superclass
    // checks if the projectile has been consumed (has reached its target or hit an obstacle)
    // if so removes it from the area
    @Override
    public void update(float deltaTime) {
        move(5);
        super.update(deltaTime);
        if(isConsumed())
            getOwnerArea().unregisterActor(this);
    }

    // draws the Fire object on the screen using its Sprite
    @Override
    public boolean draw(Canvas canvas) {
        fire.draw(canvas);
        return false;
    }

    // returns a list containing the coordinates of the cell the Fire
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    // returns false, indicating that the Fire does not occupy a cell on the game grid
    public boolean takeCellSpace() {
        return false;
    }

    // allows the Fire to interact with other objects in the game
    public void interactWith(Interactable other, boolean isCellInteraction )
    {
        other.acceptInteraction(handler, isCellInteraction);
    }

    // allows the Fire object to be interacted with by other objects in the game
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    // inner class that implements the ICRogueInteractionHandler interface
    // method defines how the Fire object should interact with other objects in the game
    // if the cell is a wall or a hole, the Fire object is consumed (removed from the game)
    // if the other object is a Turret, it is killed
    ICRogueFireInteractionHandler handler = new ICRogueFireInteractionHandler();
    private class ICRogueFireInteractionHandler implements ICRogueInteractionHandler
    {
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction)
        {
            switch(cell.getType())
            {
                case WALL, HOLE -> consume();
            }
        }
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction)
        {
            if(isCellInteraction)
                turret.die();
        }
    }

}
