package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
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
    public final static String behaviorName = "icrogue/level0Room";
    public enum Level0Connectors implements ConnectorInRoom{

        W(new DiscreteCoordinates(0, 4), new DiscreteCoordinates(8, 5), Orientation.RIGHT, "icrogue/level0room"),
        S(new DiscreteCoordinates(4, 0), new DiscreteCoordinates(5, 8), Orientation.UP, "icrogue/level0room"),
        E(new DiscreteCoordinates(9, 4), new DiscreteCoordinates(1, 5), Orientation.LEFT, "icrogue/level0room"),
        N(new DiscreteCoordinates(4, 9), new DiscreteCoordinates(5, 1), Orientation.DOWN, "icrogue/level0room");
        private final DiscreteCoordinates position;
        private final DiscreteCoordinates destination;
        private final Orientation orientation;
        private String destinationRoom;
        private Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation, String destinationRoom)
        {
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
            this.destinationRoom = destinationRoom;
        }
        public int getIndex(){
            return this.ordinal();
        }
        public DiscreteCoordinates getDestination(){
            return destination;
        }
        public String getDestinationRoom(){
            return destinationRoom;
        }
    }
    private static List<DiscreteCoordinates> getConnectorsCoordinates(){
        ArrayList<DiscreteCoordinates> connectorsCoordinates = new ArrayList<DiscreteCoordinates>();
        for( Level0Connectors level0Connectors : Level0Connectors.values())
            connectorsCoordinates.add(level0Connectors.position);
        return connectorsCoordinates;
    }
    private static List<Orientation> getConnectorOrientations(){
        ArrayList<Orientation> orientations = new ArrayList<Orientation>();
        for( Level0Connectors level0Connectors : Level0Connectors.values())
            orientations.add(level0Connectors.orientation);
        return orientations;
    }

    private static List<String> getConnectorDestinationNames(){
        ArrayList<String> connectorsDestinationRooms = new ArrayList<String>();
        for( Level0Connectors level0Connectors : Level0Connectors.values())
            connectorsDestinationRooms.add(level0Connectors.destinationRoom);
        return connectorsDestinationRooms;
    }
    private static String getBehaviorName(){
        return behaviorName;
    }
    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super( getConnectorsCoordinates(), getConnectorOrientations(),  getConnectorDestinationNames(), getBehaviorName(), roomCoordinates );
        this.roomCoordinates = roomCoordinates;
    }

    public String GetName(){return "icrogue/Level0Room";}
    @Override
    public String getTitle() {
        String ret = "icrogue/level0" + roomCoordinates.x + roomCoordinates.y;
        return ret;
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {return new DiscreteCoordinates(2, 5);}

    protected void createArea() {
        // Base
        super.createArea();
        registerActor(new Background(this, getBehaviorName()));
        //registerActor(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(6, 3)));
        //registerActor((new Staff(this, Orientation.DOWN, new DiscreteCoordinates(4, 3))));
    }

}


