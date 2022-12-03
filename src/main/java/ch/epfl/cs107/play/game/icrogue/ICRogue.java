package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class ICRogue extends AreaGame {
    public final static float CAMERA_SCALE_FACTOR = 13.f;

    private ICRoguePlayer player; // creer iCROGUe player
    private final String[] areas = {"zelda/Chateau1", "zelda/Temple1"};

    private int areaIndex;
    /**
     * Add all the areas
     */

    Level0Room currentRoom;
    private void initLevel(){
        currentRoom = new Level0Room(0,0);
        addArea(currentRoom);
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {


        if (super.begin(window, fileSystem)) {
            initLevel();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            return true;
        }
        return false;
    }

    private void initArea(String areaKey) {

        Level0Room area = (Level0Room) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICRoguePlayer(area, Orientation.DOWN, coords,"ghost.1");
        player.enterArea(area, coords);
        player.centerCamera();

    }
    @Override
    public void update(float deltaTime) {
        if(player.isWeak()){
            switchArea();
        }
        super.update(deltaTime);

    }

    @Override
    public void end() {
    }

    @Override
    public String getTitle() {

        return "ICRogue";
    }

    protected void switchArea() {

        player.leaveArea();

        areaIndex = (areaIndex==0) ? 1 : 0;

        Level0Room currentArea = (Level0Room) setCurrentArea(areas[areaIndex], false);
        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());

        player.strengthen();
    }

}