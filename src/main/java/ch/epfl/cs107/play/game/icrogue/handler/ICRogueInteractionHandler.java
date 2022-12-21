package ch.epfl.cs107.play.game.icrogue.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICRogueCell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Coin;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.SkullFire;

public interface ICRogueInteractionHandler extends AreaInteractionVisitor {
        default void interactWith(ICRogueCell cell, boolean isCellInteraction) {}
        default void interactWith(Cherry cherry, boolean isCellInteraction) {}
        default void interactWith(Staff staff, boolean isCellInteraction){}
        default void interactWith(Fire fire, boolean isCellInteraction){}
        default void interactWith(Key key, boolean isCellInteraction){}
        default void interactWith(Connector connector, boolean isCellInteraction){}
        default void interactWith(ICRoguePlayer player, boolean isCellInteraction) {}
        default void interactWith(Arrow arrow, boolean isCellInteraction){}
        default void interactWith(Turret turret, boolean isCellInteraction){}
        default void interactWith(Coin coin, boolean isCellInteraction) {}
        default void interactWith(SkullFire skullFire, boolean isCellInteraction){}
        default void interactWith(DarkLord boss, boolean isCellInteraction){}
}
