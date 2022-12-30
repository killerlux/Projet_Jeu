package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Cette classe gère les sons du jeu.
 */
public class Sound {

    // Objet Clip utilisé pour jouer le son
    Clip clip;
    // Tableau de URL contenant les chemins vers les fichiers audio
    URL soundURL[] = new URL[30];

    /**
     * Constructeur de la classe.
     * Initialise les URL des fichiers audio dans le tableau.
     */
    public Sound(){
        soundURL[0] = getClass().getResource("/Sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/Sound/coin.wav");
        soundURL[2] = getClass().getResource("/Sound/powerup.wav");
        soundURL[3] = getClass().getResource("/Sound/unlock.wav");
        soundURL[4] = getClass().getResource("/Sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/Sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/Sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/Sound/cuttree.wav");
        soundURL[8] = getClass().getResource("/Sound/levelup.wav");
        soundURL[8] = getClass().getResource("/Sound/cursor.wav");
    }

    /**
     * Méthode pour charger un fichier audio dans l'objet Clip.
     * @param index L'index du fichier audio dans le tableau soundURL.
     */
    public void setFile(int index){
        try {

            AudioInputStream stream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(stream);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Méthode pour jouer le son chargé dans l'objet Clip.
     */
    public void play(){

        clip.start();

    }

    /**
     * Méthode pour jouer en boucle le son chargé dans l'objet Clip.
     */
    public void loop(){

        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    /**
     * Méthode pour arrêter de jouer le son chargé dans l'objet Clip.
     */
    public void stop(){

        clip.stop();

    }



}
