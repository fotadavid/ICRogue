package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;

import java.util.List;
import java.util.Map;

public abstract class Level {

    private ICRogueRoom[][] map;
    private boolean GAME_OVER_ONCE = true; //Variable qui permet d'afficher Game Over! une seule fois
    private DiscreteCoordinates arrivalCoordinates;
    private DiscreteCoordinates bossRoom;
    private DiscreteCoordinates departure;
    private String departureRoom;
    private List<Connector> connectors;
    private static DiscreteCoordinates playerSpawnPosition = new DiscreteCoordinates(5, 15);

    public Level( DiscreteCoordinates departure, int width, int length ) {
        map = new ICRogueRoom[width][length];
        this.bossRoom = new DiscreteCoordinates(0, 0);
        this.departure = departure;
    }
    public DiscreteCoordinates getDeparture() {
        return departure;
    }
    public void addAreas(ICRogue game) {
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                if (map[i][j] != null)
                    game.addArea(map[i][j]);
    }
    public void checkGameStatus(){
        if (getMap()[0][0].logic() && GAME_OVER_ONCE){
            GAME_OVER_ONCE = false;
            System.out.println("You Won!");
        }
    }
    public void setDepartureRoom(DiscreteCoordinates departure) {
        departureRoom = map[departure.x][departure.y].getTitle();
    }

    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room) {
        map[coords.x][coords.y] = room;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setDestination(destination);
    }

    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setDestination(destination);
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setCurrentState(Connector.ConnectorType.CLOSED);
    }
    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyId)
    {
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setKeyId(keyId);
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setCurrentState(Connector.ConnectorType.LOCKED);
    }
    protected void openRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector)
    {
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setCurrentState(Connector.ConnectorType.OPEN);
    }
    public DiscreteCoordinates getPlayerSpawnPosition() {return new DiscreteCoordinates(5, 15);}

    public ICRogueRoom[][] getMap() {
        return map;
    }

    public abstract void generateFixedMap();
    public abstract String getTitle();
}
