package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.tutosSolution.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Ferme;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Village;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

/**
 * Specific area
 */
public class Level0Room extends ICRogueRoom {
    DiscreteCoordinates roomCoordinates;
    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super("icrogue/Level0Room", roomCoordinates);
        this.roomCoordinates = roomCoordinates;
    }

    public String GetName(){return "icrogue/Level0Room";}
    @Override
    public String getTitle() {
        String ret = "icrogue/level0" + roomCoordinates.x + roomCoordinates.y;
        return ret;
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {return new DiscreteCoordinates(0, 0);}

    protected void createArea() {
        // Base
        registerActor(new Background(this));
        registerActor(new Foreground(this));
    }
}


