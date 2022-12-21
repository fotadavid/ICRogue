package ch.epfl.cs107.play.game.icrogue.actor.items;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;


import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Cherry extends Item{

    private Sprite cherry;


    public Cherry(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        cherry = new Sprite("icrogue/cherry", 0.6f, 0.6f, this);
    }

    // returns a string representing the title of the cherry
    public String getTitle() {
        return "icrogue/cherry";
    }

    // draw the cherry on the game's screen
    @Override
    public boolean draw(Canvas canvas) {cherry.draw(canvas);
        return false;
    }

    // returns a list with the DiscreteCoordinates of the cherry's cell coordinates
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
        }

    // returns a boolean value indicating whether the cherry occupies cell space
    @Override
    public boolean takeCellSpace() {
        return false;
    }


    // returns a boolean value indicating whether the cherry can be interacted with by the player
    @Override
    public boolean isViewInteractable() {
        return false;
    }

    // allows the Cherry to interact with
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    // returns a boolean value indicating whether the cherry can be interacted with by other entities
    @Override
    public boolean isCellInteractable() {
        return true;
    }
}


