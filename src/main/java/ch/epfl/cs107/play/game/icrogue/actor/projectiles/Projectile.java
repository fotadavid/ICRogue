package ch.epfl.cs107.play.game.icrogue.actor.projectiles;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;

import java.util.Collections;
import java.util.List;

public abstract class Projectile extends ICRogueActor implements Interactor {
    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */


    private int DEFAULT_MOVE_DURATION = 10;
    private int DEFAULT_IMAGE = 1;
    private int moveDuration;
    private boolean isConsumed;
    public Projectile(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    public void update() {}

    public void consume() {
        isConsumed = true;
        getOwnerArea().unregisterActor(this);
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    public List<DiscreteCoordinates>getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    public boolean wantsCellInteraction() {
        return true;
    }

    public boolean wantsViewInteraction() {
        return true;
    }
}
