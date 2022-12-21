package ch.epfl.cs107.play.game.icrogue.area;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0KeyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0TurretRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.math.RegionOfInterest;

import java.util.Collections;
import java.util.List;

public abstract class Level {
    enum RoomType{StaffRoom, TurretRoom, BossKey, SpawnRoom, NormalRoom}
    protected enum MapState {
        NULL, // Empty space
        PLACED, // The room has been placed but not yet explored by the room placement algorithm EXPLORED,
        EXPLORED, // The room has been placed and explored by the algorithm
        BOSS_ROOM, // The room is a boss room
        CREATED; // The room has been instantiated in the room map
        @Override
        public String toString() {
            return Integer.toString(ordinal()); }
        }

    private ICRogueRoom[][] map;
    /**Variable qui permet d'afficher "Game Over!" une seule fois*/
    private boolean GAME_OVER_ONCE = true;
    protected int bossKey;
    protected DiscreteCoordinates bossRoom;
    protected String departureRoom;
    private static DiscreteCoordinates playerSpawnPosition = new DiscreteCoordinates(5, 15);
    private DiscreteCoordinates startPosition;
    private boolean randomMap;
    private int[] roomsDistribution;
    protected MapState[][] RoomPlacement;

    Level(boolean randomMap, DiscreteCoordinates startPosition, int[] roomsDistribution, int width, int height) {
        this.randomMap = randomMap;
        this.startPosition = startPosition;
        this.roomsDistribution = roomsDistribution;
        map = new ICRogueRoom[width][height];
        this.bossRoom = new DiscreteCoordinates(0, 0);
        this.startPosition = startPosition;
    }
    public void addAreas(ICRogue game) {
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                if (map[i][j] != null)
                    game.addArea(map[i][j]);
    }
    public boolean checkGameStatus(){
        if (getMap()[bossRoom.x][bossRoom.y].logic() && GAME_OVER_ONCE){
            GAME_OVER_ONCE = false;
            System.out.println("You Won!");
            getMap()[bossRoom.x][bossRoom.y].registerActor(new Foreground(getMap()[bossRoom.x][bossRoom.y],
                    new RegionOfInterest(5, 5, 10000, 20000), "icrogue/WIN"));
            return true;
        }
        return false;
    }
    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room) {
        map[coords.x][coords.y] = room;
    }
    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination,
                                               ConnectorInRoom connector){
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
    public void generateMap(){
        if( !randomMap )
            generateFixedMap();
        else generateRandomMap();
    }
    public abstract void generateFixedMap();
    public abstract void createTypeRoom(int index, DiscreteCoordinates location);
    public abstract void createBossRoom();
    public void generateRandomMap(){
        RoomPlacement = generateRandomRoomPlacement();
        createBossRoom();
        for( int i = 0; i < roomsDistribution.length; i++ ){
            Collections.shuffle(availableLocations);
            for( int j = 0; j < roomsDistribution[i]; j++ ){
                DiscreteCoordinates location = availableLocations.get(j);
                createTypeRoom(i, location);
                availableLocations.remove(j);
            }
        }
        setUpConnector(RoomPlacement);
    }
    protected List<DiscreteCoordinates> availableLocations = new ArrayList<>();
    public MapState[][] generateRandomRoomPlacement() {
        availableLocations.clear();
        List<Integer> toPlace;
        List<Integer> availablePlaces = new ArrayList<>();
        int needToPlace;
        int roomsToPlace = 0;
        int freeSlots;
        for (int i = 0; i < roomsDistribution.length; i++)
            roomsToPlace += roomsDistribution[i];
        MapState[][] RoomPlacement = new MapState[map.length][map[0].length];
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                RoomPlacement[i][j] = MapState.NULL;
        RoomPlacement[map.length / 2][map[0].length / 2] = MapState.PLACED;
        roomsToPlace--;
        availableLocations.add( new DiscreteCoordinates(map.length / 2, map[0].length / 2));
        while (roomsToPlace > 0) {
            for (int i = 0; i < map.length; i++) {
                if(roomsToPlace == 0)
                    break;
                for (int j = 0; j < map[0].length; j++) {
                    if (roomsToPlace == 0)
                        break;
                    availablePlaces.clear();
                    freeSlots = 0;
                    if (RoomPlacement[i][j].equals(MapState.PLACED)) {
                        if (j - 1 >= 0)
                            if (RoomPlacement[i][j - 1].equals(MapState.NULL)) {
                                freeSlots++;
                                availablePlaces.add(0);
                            }
                        if (j + 2 <= map[0].length)
                            if (RoomPlacement[i][j + 1].equals(MapState.NULL)) {
                                freeSlots++;
                                availablePlaces.add(1);
                            }
                        if (i - 1 >= 0)
                            if (RoomPlacement[i - 1][j].equals(MapState.NULL)) {
                                freeSlots++;
                                availablePlaces.add(2);
                            }
                        if (i + 2 <= map.length)
                            if (RoomPlacement[i + 1][j].equals(MapState.NULL)) {
                                freeSlots++;
                                availablePlaces.add(3);
                            }
                        if(roomsToPlace == 0)
                            break;
                        if(freeSlots == 0)
                            break;
                        if (freeSlots < roomsToPlace)
                            needToPlace = RandomHelper.roomGenerator.nextInt(1, freeSlots + 1);
                        else
                            needToPlace = RandomHelper.roomGenerator.nextInt(1, roomsToPlace + 1);
                        toPlace = RandomHelper.chooseKInList(needToPlace, availablePlaces);
                        for (int m = 0; m < needToPlace; m++)
                            switch (toPlace.get(m)) {
                                case 0: {
                                    RoomPlacement[i][j - 1] = MapState.PLACED;
                                    availableLocations.add(new DiscreteCoordinates(i, j - 1));
                                    roomsToPlace--;
                                    break;
                                }
                                case 1: {
                                    RoomPlacement[i][j + 1] = MapState.PLACED;
                                    availableLocations.add(new DiscreteCoordinates(i, j + 1));
                                    roomsToPlace--;
                                    break;
                                }
                                case 2: {
                                    RoomPlacement[i - 1][j] = MapState.PLACED;
                                    availableLocations.add(new DiscreteCoordinates(i - 1, j));
                                    roomsToPlace--;
                                    break;
                                }
                                case 3: {
                                    RoomPlacement[i + 1][j] = MapState.PLACED;
                                    availableLocations.add(new DiscreteCoordinates(i + 1, j));
                                    roomsToPlace--;
                                    break;
                                }
                            }
                        RoomPlacement[i][j] = MapState.EXPLORED;
                    }
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (RoomPlacement[i][j] == MapState.NULL) {
                    if (j - 1 >= 0)
                        if (!RoomPlacement[i][j - 1].equals(MapState.NULL)) {
                            RoomPlacement[i][j] = MapState.BOSS_ROOM;
                            bossRoom = new DiscreteCoordinates(i, j);
                            found = true;
                        }
                    if (j + 2 <= map[0].length)
                        if (!RoomPlacement[i][j + 1].equals(MapState.NULL)) {
                            RoomPlacement[i][j] = MapState.BOSS_ROOM;
                            bossRoom = new DiscreteCoordinates(i, j);
                            found = true;
                        }
                    if (i - 1 >= 0)
                        if (!RoomPlacement[i - 1][j].equals(MapState.NULL)) {
                            RoomPlacement[i][j] = MapState.BOSS_ROOM;
                            bossRoom = new DiscreteCoordinates(i, j);
                            found = true;
                        }
                    if (i + 2 <= map.length)
                        if (!RoomPlacement[i + 1][j].equals(MapState.NULL)) {
                            RoomPlacement[i][j] = MapState.BOSS_ROOM;
                            bossRoom = new DiscreteCoordinates(i, j);
                            found = true;
                        }
                }
                if (found)
                    break;
            }
            if(found)
                break;
        }
        return RoomPlacement;
    }
    abstract protected void setUpConnector(MapState[][] mapState);
    private boolean found = false;
    public abstract String getTitle();
}
