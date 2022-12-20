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

public class Staff extends Item{

    private Sprite staff;

    public Staff(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        staff = new Sprite("zelda/staff_water.icon", .5f, .5f, this);
    }


    // returns the string of the staff
    public String getTitle() {
        return "zelda/staff";
    }

    /**
     * une fois le baton ramassé il disparait (visuellement)
     */
    @Override
    public boolean draw(Canvas canvas) {
        if(!this.isCollected())
            staff.draw(canvas);
        return false;
    }

    // returns a singleton list containing only the coordinates of the cell that the staff is located in
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    // returns true if the staff has not been collected by the player yet, false otherwise
    @Override
    public boolean takeCellSpace() {
        if(!this.isCollected())
            return true;
        return false;
    }

    // returns a boolean value indicating whether the staff can be interacted with by the player
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    // returns a boolean value indicating whether the Staff can be interacted with by other entities
    @Override
    public boolean isCellInteractable() {
        return false;
    }

    // allows the Staff to interact
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}