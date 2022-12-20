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


    private int DEFAULT_MOVE_DURATION = 10; // default move duration
    private int DEFAULT_IMAGE = 1; // default image
    private boolean isConsumed; // check whether the projectile has been consumed or not

    public Projectile(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    // update the state of the projectile
    public void update() {}

    // mark the projectile as consumed
    public void consume() {
        isConsumed = true;
    }

    // check if the projectile has been consumed
    public boolean isConsumed() {
        return isConsumed;
    }

    // indicate that the projectile does not occupy a cell
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    // get the cells occupied by the projectile
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    // get the cells that the projectile can interact with
    public List<DiscreteCoordinates>getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    // indicate that the projectile wants cell interactions
    public boolean wantsCellInteraction() {
        return true;
    }

    // indicate that the projectile wants view interactions
    public boolean wantsViewInteraction() {
        return true;
    }
}
