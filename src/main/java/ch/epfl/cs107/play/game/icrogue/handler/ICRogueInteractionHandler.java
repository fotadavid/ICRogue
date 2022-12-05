package ch.epfl.cs107.play.game.icrogue.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICRogueCell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
public interface ICRogueInteractionHandler extends AreaInteractionVisitor {
        default void interactWith(ICRogueCell cell)
        {

        }
        default void interactWith(Cherry cherry)
        {

        }
        default void interactWith(Staff staff)
        {

        }
        default void interactWith(Fire fire)
        {

        }
        default void interactWith(ICRoguePlayer player)
        {

        }
}
