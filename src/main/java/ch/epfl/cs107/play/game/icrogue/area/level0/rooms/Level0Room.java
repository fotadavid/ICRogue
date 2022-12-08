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
    public enum Level0Connectors implements ConnectorInRoom{

        W(new DiscreteCoordinates(0, 4), new DiscreteCoordinates(8, 5), Orientation.RIGHT),
        S(new DiscreteCoordinates(4, 0), new DiscreteCoordinates(5, 8), Orientation.UP),
        E(new DiscreteCoordinates(9, 4), new DiscreteCoordinates(1, 5), Orientation.LEFT),
        N(new DiscreteCoordinates(4, 9), new DiscreteCoordinates(5, 1), Orientation.DOWN);
        private final DiscreteCoordinates position;
        private final DiscreteCoordinates destination;
        private final Orientation orientation;
        private Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation)
        {
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
        }
        public int getIndex(){
            return this.ordinal();
        }
        public DiscreteCoordinates getDestination(){
            return destination;
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

    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super( getConnectorsCoordinates(), getConnectorOrientations(),  "icrogue/Level0Room", roomCoordinates );
        this.roomCoordinates = roomCoordinates;
    }

    public String GetName(){return "icrogue/Level0Room";}
    @Override
    public String getTitle() {
        String ret = "icrogue/level0" + roomCoordinates.x + roomCoordinates.y;
        return "icrogue/Level0Room";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {return new DiscreteCoordinates(5, 15);}

    protected void createArea() {
        // Base
        super.createArea();
        registerActor(new Background(this));
        registerActor(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(6, 3)));
        registerActor((new Staff(this, Orientation.DOWN, new DiscreteCoordinates(4, 3))));
    }

}


