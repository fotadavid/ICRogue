package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;


public class Level0 extends Level{
    private DiscreteCoordinates playerRoomTransitionPosition = new DiscreteCoordinates(0, 2);
    enum RoomType{StaffRoom, TurretRoom, BossKey, SpawnRoom, NormalRoom}
    private final RoomType[] RoomTypes = {RoomType.StaffRoom, RoomType.TurretRoom, RoomType.BossKey,
            RoomType.SpawnRoom, RoomType.NormalRoom};
    private int[] roomsDistribution;
    private DiscreteCoordinates startPosition;
    public Level0(){
        super(true, new DiscreteCoordinates(1, 0), new int[]{1, 3, 1, 1, 1}, 4, 6);
        roomsDistribution = new int[]{1, 1, 1, 1, 1};
        startPosition = new DiscreteCoordinates(1, 0);
        departureRoom = "icrogue/level0" + startPosition.x + startPosition.y;
    }
    private final int PART_1_KEY_ID = 2;
    private final int BOSS_KEY_ID = 3;
    private final DiscreteCoordinates BOSS_ROOM = new DiscreteCoordinates(0, 0);
    public String getTitle(){
        return departureRoom;
    }
    public void generateFixedMap(){
        //generateMap1();
        generateMap2();
    }
    //method that has to be implemeted in each level
    public void createBossRoom(){
        setRoom(bossRoom, new Level0DarkLordRoom(bossRoom));
    }
    public void createTypeRoom(int index, DiscreteCoordinates location){
        switch (index){
            case 0:
                setRoom(location, new Level0StaffRoom(location));
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 1:
                setRoom(location, new Level0TurretRoom(location));
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 2:
                bossKey = RandomHelper.roomGenerator.nextInt(0, 80);
                setRoom(location, new Level0KeyRoom(location, bossKey));
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 3:
                setRoom(location, new Level0Room(location));
                startPosition = location;
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
            case 4:
                setRoom(location, new Level0Room(location));
                RoomPlacement[location.x][location.y] = MapState.CREATED;
                break;
        }
        departureRoom = "icrogue/level0" + startPosition.x + startPosition.y;
    }
    //method that has to be implemented by each level
    protected void setUpConnector(MapState[][] RoomPlacement)
    {
        for( int i = 0; i < RoomPlacement.length; i++ )
            for( int j = 0; j < RoomPlacement[0].length; j++)
                if( !RoomPlacement[i][j].equals(MapState.NULL) )
                {
                    if (j - 1 >= 0)
                        if (!RoomPlacement[i][j - 1].equals(MapState.NULL)) {
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + i + (j - 1), Level0Room.Level0Connectors.N);
                            if(RoomPlacement[i][j - 1].equals(MapState.BOSS_ROOM)) {
                                lockRoomConnector(new DiscreteCoordinates(i, j),
                                        Level0Room.Level0Connectors.N, bossKey);
                            }
                        }
                    if (j + 2 <= RoomPlacement[0].length)
                        if (!RoomPlacement[i][j + 1].equals(MapState.NULL)) {
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + i + (j + 1), Level0Room.Level0Connectors.S);
                            if(RoomPlacement[i][j + 1].equals(MapState.BOSS_ROOM)) {
                                lockRoomConnector(new DiscreteCoordinates(i, j),
                                        Level0Room.Level0Connectors.S, bossKey);
                            }
                        }
                    if (i - 1 >= 0)
                        if (!RoomPlacement[i - 1][j].equals(MapState.NULL)) {
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + (i - 1) + j, Level0Room.Level0Connectors.E);
                            if(RoomPlacement[i - 1][j].equals(MapState.BOSS_ROOM)) {
                                lockRoomConnector(new DiscreteCoordinates(i, j),
                                        Level0Room.Level0Connectors.E, bossKey);
                            }
                        }
                    if (i + 2 <= RoomPlacement.length)
                        if (!RoomPlacement[i + 1][j].equals(MapState.NULL)) {
                            setRoomConnector(new DiscreteCoordinates(i, j),
                                    "icrogue/level0" + (i + 1) + j, Level0Room.Level0Connectors.W);
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
