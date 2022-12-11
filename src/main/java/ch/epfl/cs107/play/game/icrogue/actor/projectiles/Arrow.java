package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
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

    private Sprite arrowsprite1, arrowsprite2, arrowsprite3, arrowsprite4;


    private final static int MOVE_DURATION = 8;


    public Arrow(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        arrow = new Sprite("zelda/arrow", 1f, 1f, this, new RegionOfInterest(32*orientation.ordinal(), 0, 32, 32), new Vector(0, 0));
        damagePoints = 1;
    }

    private void throwArrow(Orientation orientation) {

    }

    public String getTitle() {
        return "zelda/arrow";
    }

    @Override
    public void update(float deltaTime) {
        move(5);
        super.update(deltaTime);
        if(isConsumed())
            getOwnerArea().unregisterActor(this);
    }

    @Override
    public boolean draw(Canvas canvas) {
        arrow.draw(canvas);
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    public boolean takeCellSpace() {
        return false;
    }
    public void interactWith(Interactable other, boolean isCellInteraction )
    {
        other.acceptInteraction(handler, isCellInteraction);
    }
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
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
    }

}

