package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * Specific area
 */
public class Level0Room extends ICRogueRoom {
    DiscreteCoordinates roomCoordinates;
    private static List<DiscreteCoordinates> getConnectorsCoordinates(){
        ArrayList<DiscreteCoordinates> connectorsCoordinates = new ArrayList<DiscreteCoordinates>();
        connectorsCoordinates.add( new DiscreteCoordinates(4, 5));
        connectorsCoordinates.add( new DiscreteCoordinates(2, 3));
        return connectorsCoordinates;
    }
    private static List<Orientation> getConnectorOrientations(){
        ArrayList<Orientation> orientations = new ArrayList<Orientation>();
        orientations.add(Orientation.LEFT);
        orientations.add(Orientation.DOWN);
        return orientations;
    }

    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super( getConnectorsCoordinates(), getConnectorOrientations(), "icrogue/Level0Room", roomCoordinates );
        this.roomCoordinates = roomCoordinates;
    }

    public String GetName(){return "icrogue/Level0Room";}
    @Override
    public String getTitle() {
        String ret = "icrogue/level0" + roomCoordinates.x + roomCoordinates.y;
        return "icrogue/Level0Room";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {return new DiscreteCoordinates(3, 3);}

    protected void createArea() {

        // Base
        registerActor(new Background(this));
        registerActor(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(6, 3)));
        registerActor((new Staff(this, Orientation.DOWN, new DiscreteCoordinates(4, 3))));
        registerActor((new Connector(this, Orientation.DOWN, new DiscreteCoordinates(4, 9), Connector.ConnectorType.INVISIBLE)));
        registerActor((new Connector(this, Orientation.UP, new DiscreteCoordinates(4, 0), Connector.ConnectorType.LOCKED)));
    }

}


