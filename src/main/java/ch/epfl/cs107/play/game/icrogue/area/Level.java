package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.List;
import java.util.Map;

public class Level extends ICRogueRoom {

    private ICRogueRoom[][] map = new ICRogueRoom[4][2];
    private DiscreteCoordinates coordinates;
    private String destination;

    public Level(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations, String behaviorName, DiscreteCoordinates roomCoordinates) {
        super(connectorsCoordinates, orientations, behaviorName, roomCoordinates);
    }


    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room) {
        map[coords.x][coords.y] = room;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
    }

    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){

    }


    public DiscreteCoordinates getCoordinates(){return coordinates;}


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return null;
    }
}
