package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import java.util.Collections;
import java.util.List;

public class ICRogue extends AreaGame {
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    public final static String ROOM = "icrogue/Level0Room";
    private ICRoguePlayer player; // creer iCROGUe player
    private final String[] areas = {ROOM};

    private int areaIndex;
    /**
     * Add all the areas
     */
    Level0Room currentRoom;

    private void initLevel(){
        DiscreteCoordinates currentRoomCoor = new DiscreteCoordinates(0, 0);
        currentRoom = new Level0Room(currentRoomCoor);
        addArea(currentRoom);
        Level0Room area = (Level0Room) setCurrentArea(currentRoom.getTitle(), true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICRoguePlayer(area, Orientation.DOWN, coords,"ghost.1");
        player.enterArea(area, coords);
    }
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }

@Override
    public void update(float deltaTime) {
    super.update(deltaTime);
    resetMotion();
    }

    public void resetMotion(){
        Keyboard keyboard = currentRoom.getKeyboard();
        Button b = keyboard.get(Keyboard.R);
        if( b.isDown() )
            initLevel();
    }
    @Override
    public void end() {
    }

    @Override
    public String getTitle() {

        return "ICRogue";
    }

    /*protected void switchArea() {

        player.leaveArea();

        areaIndex = (areaIndex==0) ? 1 : 0;

        Level0Room currentArea = (Level0Room) setCurrentArea(areas[areaIndex], false);
        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());

        player.strengthen();
    }*/
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(player.getCurrentMainCellCoordinates());
    }

}