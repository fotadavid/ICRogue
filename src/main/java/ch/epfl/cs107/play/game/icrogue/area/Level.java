package ch.epfl.cs107.play.game.icrogue.area;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
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
    private boolean GAME_OVER_ONCE = true; // variable that allows to print "Game Over!" only once
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
    public abstract void createBossRoom();  // creates the boss room
    public void generateRandomMap(){
        // generates a random placement of rooms on the map
        RoomPlacement = generateRandomRoomPlacement();
        // Create the boss room
        createBossRoom();
        // Loop through the roomsDistribution array and create a room of each type at a random location
        for( int i = 0; i < roomsDistribution.length; i++ ){
            // Shuffle the list of available locations
            Collections.shuffle(availableLocations);
            // Loop through the number of rooms of this type to be placed
            for( int j = 0; j < roomsDistribution[i]; j++ ){
                // Get the next available location
                DiscreteCoordinates location = availableLocations.get(j);
                // Create a room of this type at the location
                createTypeRoom(i, location);
                // Remove the location from the list of available locations
                availableLocations.remove(j);
            }
        }
        // Set up the connectors between the rooms
        setUpConnector(RoomPlacement);
    }
    // List of available locations for room placement
    protected List<DiscreteCoordinates> availableLocations = new ArrayList<>();
    //Generate random placement on the map
    public MapState[][] generateRandomRoomPlacement() {
        // Clear the list of available locations
        availableLocations.clear();

        // List of places to place rooms
        List<Integer> toPlace;

        // List of available places to place rooms
        List<Integer> availablePlaces = new ArrayList<>();

        // Number of rooms to place at each iteration
        int needToPlace;

        // Total number of rooms that need to be placed
        int roomsToPlace = 0;

        // Free slots (NULL)
        int freeSlots;

        // Compute roomsToPlace
        for (int i = 0; i < roomsDistribution.length; i++)
            roomsToPlace += roomsDistribution[i];

        // Initialize RoomPlacement
        MapState[][] RoomPlacement = new MapState[map.length][map[0].length];
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                RoomPlacement[i][j] = MapState.NULL;

        // Place room at the center of the map
        RoomPlacement[map.length / 2][map[0].length / 2] = MapState.PLACED;
        roomsToPlace--;

        // Add the location of the first room to the list of available locations
        availableLocations.add( new DiscreteCoordinates(map.length / 2, map[0].length / 2));

        // Loop until all the rooms have been placed
        while (roomsToPlace > 0) {
            for (int i = 0; i < map.length; i++) {
                // If all the rooms have been placed, break out of the loop
                if(roomsToPlace == 0)
                    break;
                for (int j = 0; j < map[0].length; j++) {
                    // If all the rooms have been placed, break out of the loop
                    if (roomsToPlace == 0)
                        break;
                    // Clear the list of available places to place rooms
                    availablePlaces.clear();
                    // Set the free slots counter to 0
                    freeSlots = 0;

                    // Check if current cell is PLACED
                    if (RoomPlacement[i][j].equals(MapState.PLACED)) {

                        // Check if the cell to the left fits to the limits of the map
                        if (j - 1 >= 0)
                            // Check if the cell to the left of the current cell is available for placing a room
                            if (RoomPlacement[i][j - 1].equals(MapState.NULL)) {
                                // If the cell is available, increment the number of free slots by 1 and add
                                // it to the list of available places
                                freeSlots++;
                                availablePlaces.add(0);
                                // 0 is an identifier for "room to the left" and will be used to place a certain room
                            }

                        // Check if the cell to the right fits to the limits of the map
                        if (j + 2 <= map[0].length)
                            if (RoomPlacement[i][j + 1].equals(MapState.NULL)) {
                                // If the cell is available, increment the number of free slots by 1 and add
                                // it to the list of available places
                                freeSlots++;
                                availablePlaces.add(1);
                                // 1 is an identifier for "room to the right" and will be used to place a certain room
                            }

                        // Check if the cell above fits to the limits of the map
                        if (i - 1 >= 0)
                            if (RoomPlacement[i - 1][j].equals(MapState.NULL)) {
                                // If the cell is available, increment the number of free slots by 1 and add
                                // it to the list of available places
                                freeSlots++;
                                availablePlaces.add(2);
                                // 2 is an identifier for "room above" and will be used to place a certain room
                            }

                        // Check if the cell beneath fits to the limits of the map
                        if (i + 2 <= map.length)
                            if (RoomPlacement[i + 1][j].equals(MapState.NULL)) {
                                // If the cell is available, increment the number of free slots by 1 and add
                                // it to the list of available places
                                freeSlots++;
                                availablePlaces.add(3);
                                // 3 is an identifier for "room beneath" and will be used to place a certain room
                            }

                        // If all rooms have been placed, break out of the loop
                        if(roomsToPlace == 0)
                            break;

                        // If there are no free slots left, break out of the loop
                        if(freeSlots == 0)
                            break;

                        // Determine the number of rooms to place around the current cell
                        if (freeSlots < roomsToPlace)
                            // If there are fewer free slots than rooms to place, randomly choose a number
                            // of rooms between 1 and the number of free slots
                            needToPlace = RandomHelper.roomGenerator.nextInt(1, freeSlots + 1);
                        else
                            // If there are more or the same number of free slots as rooms to place,
                            // randomly choose a number of rooms between 1 and the number of rooms to place
                            needToPlace = RandomHelper.roomGenerator.nextInt(1, roomsToPlace + 1);

                        // Choose needToPlace number of places to place rooms from the list of available places
                        toPlace = RandomHelper.chooseKInList(needToPlace, availablePlaces);

                        // Place rooms at the chosen locations
                        for (int m = 0; m < needToPlace; m++)
                            switch (toPlace.get(m)) {
                                case 0: {
                                    // Place a room to the left of the current cell
                                    RoomPlacement[i][j - 1] = MapState.PLACED;
                                    // Add the location of the placed room to the list of available locations
                                    availableLocations.add(new DiscreteCoordinates(i, j - 1));
                                    roomsToPlace--;
                                    break;
                                }
                                case 1: {
                                    // Place a room to the right of the current cell
                                    RoomPlacement[i][j + 1] = MapState.PLACED;
                                    // Add the location of the placed room to the list of available locations
                                    availableLocations.add(new DiscreteCoordinates(i, j + 1));
                                    roomsToPlace--;
                                    break;
                                }
                                case 2: {
                                    // Place a room above the current cell
                                    RoomPlacement[i - 1][j] = MapState.PLACED;
                                    // Add the location of the placed room to the list of available locations
                                    availableLocations.add(new DiscreteCoordinates(i - 1, j));
                                    roomsToPlace--;
                                    break;
                                }
                                case 3: {
                                    // Place a room below the current cell
                                    RoomPlacement[i + 1][j] = MapState.PLACED;
                                    // Add the location of the placed room to the list of available locations
                                    availableLocations.add(new DiscreteCoordinates(i + 1, j));
                                    roomsToPlace--;
                                    break;
                                }
                            }
                        // Mark the [i ; j] position of the map as explored
                        RoomPlacement[i][j] = MapState.EXPLORED;
                    }
                }
            }
        }

        // Find a possible location for the Boss Room
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
        //Returns the RoomPlacement array
        return RoomPlacement;
    }
    //To be redefined by each particular level
    abstract protected void setUpConnector(MapState[][] mapState);
    private boolean found = false;
    public abstract String getTitle();
}
