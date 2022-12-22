package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Level0 extends Level{

    // Enum representing the types of rooms that can be generated in this level
    enum RoomType{StaffRoom, TurretRoom, BossKey, SpawnRoom, NormalRoom}
    // The starting position for the player in this level
    private DiscreteCoordinates startPosition;
    // Constructor for the Level0 class
    public Level0(){
        // Initialize the level with the specified layout of rooms
        super(true, new DiscreteCoordinates(1, 0), new int[]{1, 3, 1, 1, 1}, 4, 6);
        // Set the starting position for the player for the case when randomMap is false
        startPosition = new DiscreteCoordinates(1, 0);
        // Set the departure room for the case when randomMap is false
        departureRoom = "icrogue/level0" + startPosition.x + startPosition.y;
    }
    // The ID of the key needed to unlock the connector to the boss room
    private final int PART_1_KEY_ID = 2;
    private final int BOSS_KEY_ID = 3;
    // The coordinates of the boss room
    private final DiscreteCoordinates BOSS_ROOM = new DiscreteCoordinates(0, 0);
    public String getTitle(){
        return departureRoom;
    }
    public void generateFixedMap(){
        //generateMap1();
        generateMap2();
    }

    // Set the boss room for the level to a new instance of Level0DarkLordRoom
    public void createBossRoom(){
        setRoom(bossRoom, new Level0DarkLordRoom(bossRoom));
    }

    // Creates a room of the specified type at the specified location in the level
    public void createTypeRoom(int index, DiscreteCoordinates location){
        switch (index){
            // Based on the Enum provided at the beginning, each index has a certain meaning which can only
            // be known by the concrete class defining each level
            case 0:
                // Set the room at the specified location to a new instance of Level0StaffRoom
                setRoom(location, new Level0StaffRoom(location));
                // Mark the room as created
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 1:
                // Set the room at the specified location to a new instance of Level0TurretRoom
                setRoom(location, new Level0TurretRoom(location));
                // Mark the room as created
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 2:
                // Generate a random key ID for the key in this room
                bossKey = RandomHelper.roomGenerator.nextInt(0, 80);
                // Set the room at the specified location to
                // a new instance of Level0KeyRoom with the generated key ID
                setRoom(location, new Level0KeyRoom(location, bossKey));
                // Mark the room as created
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 3:
                // Set the room at the specified location to a new instance of Level0Room
                setRoom(location, new Level0Room(location));
                // Set the starting position for the player to the specified location
                startPosition = location;
                // Mark the room as created
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 4:
                // Set the room at the specified location to a new instance of Level0Room
                setRoom(location, new Level0Room(location));
                // Mark the room as created
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
        }
        // Set the departure room for the level to the room at the starting position
        departureRoom = "icrogue/level0" + startPosition.x + startPosition.y;
    }
    // Sets up connectors between room, depending on the layout of the map
    // has to be implemented by each specific level
    protected void setUpConnector(MapState[][] RoomPlacement)
    {
        for( int i = 0; i < RoomPlacement.length; i++ )
            for( int j = 0; j < RoomPlacement[0].length; j++)
                if( !RoomPlacement[i][j].equals(MapState.NULL) )
                {
                    // Check if the room at the left exists (does not exceed the limits of the map)
                    if (j - 1 >= 0)
                        // If this room exists, check if it is NULL
                        if (!RoomPlacement[i][j - 1].equals(MapState.NULL)) {
                            // If it's not null, set up a connector between the current room and the room to the left
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + i + (j - 1), Level0Room.Level0Connectors.N);
                            // Check if the room is a BOSS ROOM, if it's the case, a locked connector is to be placed
                            // between the 2, the player being able to pass through it once the key collected
                            if(RoomPlacement[i][j - 1].equals(MapState.BOSS_ROOM)) {
                                lockRoomConnector(new DiscreteCoordinates(i, j),
                                        Level0Room.Level0Connectors.N, bossKey);
                            }
                        }
                    // Check if the room at the right exists (does exceed the limits of the map)
                    if (j + 2 <= RoomPlacement[0].length)
                        // If this room exists, check if it is NULL
                        if (!RoomPlacement[i][j + 1].equals(MapState.NULL)) {
                            // If it's not null, set up a connector between the current room and the room to the right
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + i + (j + 1), Level0Room.Level0Connectors.S);
                            // Check if the room is a BOSS ROOM, if it's the case, a locked connector is to be placed
                            // between the 2, the player being able to pass through it once the key collected
                            if(RoomPlacement[i][j + 1].equals(MapState.BOSS_ROOM)) {
                                lockRoomConnector(new DiscreteCoordinates(i, j),
                                        Level0Room.Level0Connectors.S, bossKey);
                            }
                        }
                    // Check if the room above exists (does exceed the limits of the map)
                    if (i - 1 >= 0)
                        // If this room exists, check if it is NULL
                        if (!RoomPlacement[i - 1][j].equals(MapState.NULL)) {
                            // If it's not null, set up a connector between the current room and the room above
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + (i - 1) + j, Level0Room.Level0Connectors.E);
                            // Check if the room is a BOSS ROOM, if it's the case, a locked connector is to be placed
                            // between the 2, the player being able to pass through it once the key collected
                            if(RoomPlacement[i - 1][j].equals(MapState.BOSS_ROOM)) {
                                lockRoomConnector(new DiscreteCoordinates(i, j),
                                        Level0Room.Level0Connectors.E, bossKey);
                            }
                        }
                    // Check if the room beneath exists (does exceed the limits of the map)
                    if (i + 2 <= RoomPlacement.length)
                        // If this room exists, check if it is NULL
                        if (!RoomPlacement[i + 1][j].equals(MapState.NULL)) {
                            // If it's not null, set up a connector between the current room and the room above
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + (i + 1) + j, Level0Room.Level0Connectors.W);
                            // Check if the room is a BOSS ROOM, if it's the case, a locked connector is to be placed
                            // between the 2, the player being able to pass through it once the key collected
                            if(RoomPlacement[i + 1][j].equals(MapState.BOSS_ROOM)) {
                                lockRoomConnector(new DiscreteCoordinates(i, j),
                                        Level0Room.Level0Connectors.W, bossKey);
                            }
                        }
                }
    }
    private void generateMap1() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, PART_1_KEY_ID));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        lockRoomConnector(room00, Level0Room.Level0Connectors.E,  PART_1_KEY_ID);
        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
    }

    private void generateMap2() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom (room00, new Level0TurretRoom(room00));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1,0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);
        lockRoomConnector(room10, Level0Room.Level0Connectors.W,  BOSS_KEY_ID);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2,0);
        setRoom(room20,  new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3,0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom(room11, new Level0Room(room11));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);
    }
}
