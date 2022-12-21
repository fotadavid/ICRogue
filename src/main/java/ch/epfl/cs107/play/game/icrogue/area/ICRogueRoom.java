package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICRogueRoom extends Area {

    private ICRogueBehavior behavior;
    private String behaviorName;
    private List<Connector> connectors = new ArrayList<Connector>();
    private DiscreteCoordinates roomCoordinates;
    private List<DiscreteCoordinates> connectorsCoordinates;
    private List<Orientation> orientations;
    private List <String> connectorDestinationRooms;

    // constructor that initializes the instance variables
    // and creates a list of Connector based
    // on the connectorsCoordinates, orientations, and connectorDestinationRooms parameters
    public ICRogueRoom(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations,
                       List <String> connectorDestinationRooms, String behaviorName, DiscreteCoordinates roomCoordinates)
    {
        this.behaviorName = behaviorName;
        this.roomCoordinates = roomCoordinates;
        this.connectorsCoordinates = connectorsCoordinates;
        this.orientations = orientations;
        this.connectorDestinationRooms = connectorDestinationRooms;
        for( int i = 0; i < connectorsCoordinates.size(); i++ ) {
            connectors.add(new Connector(this, orientations.get(i), connectorsCoordinates.get(i), connectorDestinationRooms.get(i)));
        }
    }

    // returns the list of connectors in the room
    public List<Connector> getConnectors(){
        return connectors;
    }

    public void setConnectorDestinationRoom(int index, String destinationRoom) {
        connectorDestinationRooms.set(index, destinationRoom);
    }

    public void setConnectorsKeys(int key){

    }

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected void createArea(){
        for(Connector connector : connectors)
            registerActor(connector);
    };

    // EnigmeArea extends Area

    @Override
    public final float getCameraScaleFactor() {
        return 11;
    }

    // returns a DiscreteCoordinates object representing the spawn position for the player
    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    // method that updates the state of the connectors based on user input
    // and calls a logic method to determine whether to open the connectors
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Keyboard keyboard = this.getKeyboard();
         if(keyboard.get(Keyboard.O).isPressed())
            for(Connector connector : connectors)
                connector.setCurrentState(Connector.ConnectorType.OPEN);
         else if(keyboard.get(Keyboard.T).isPressed())
             for(Connector connector : connectors)
                 connector.setCurrentState(Connector.ConnectorType.CLOSED);
         else if(keyboard.get(Keyboard.L).isPressed())
             for(Connector connector : connectors)
                 if( connector.getCoordinates().equals(new DiscreteCoordinates(0, 4)))
                     connector.setCurrentState(Connector.ConnectorType.LOCKED);
             if(logic() == true)
                 for(Connector connector : connectors) {
                     if (connector.getType() == Connector.ConnectorType.CLOSED)
                         connector.setCurrentState(Connector.ConnectorType.OPEN);
                 }

    }

    // check if the player has entered cells in the area
    // returns true if this condition is met
    public boolean logic(){
        getPlayerSpawnPosition();
        if(enterAreaCells()){
            return true;}
        return false;
    }

    // sets the behavior for the area and calls the createArea method to add actors to the area
    @Override
    public boolean begin(Window window, FileSystem behaviorName) {
        if (super.begin(window, behaviorName)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, this.behaviorName);
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }
}