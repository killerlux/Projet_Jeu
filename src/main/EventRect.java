package main;

import java.awt.*;

/**
 * La classe EventRect étend la classe Rectangle et permet de définir un rectangle qui peut déclencher un événement lorsque le joueur entre en contact avec lui.
 */
public class EventRect extends Rectangle {
    /**
La position par défaut en abscisse et ordonnée du rectangle d'événement.
*/

    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;

}
