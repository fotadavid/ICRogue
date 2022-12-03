package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICRogueRoom extends Area {

    private ICRogueBehavior behavior;
    private String behaviorName;
    private DiscreteCoordinates roomCoordinates;
    public ICRogueRoom( String behaviorName, DiscreteCoordinates roomCoordinates )
    {
        this.behaviorName = behaviorName;
        this.roomCoordinates = roomCoordinates;
    }
    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();

    /// EnigmeArea extends Area

    @Override
    public final float getCameraScaleFactor() {

        return 11;
    }

    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    /// Demo2Area implements Playable

    @Override
    public boolean begin(Window window, FileSystem behaviorName) {
        if (super.begin(window, behaviorName)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, "icrogue/Level0Room");
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }

}

