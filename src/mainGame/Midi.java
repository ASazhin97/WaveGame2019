/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Midi class creates all the music files used for the entire game. 
 */
package mainGame;

// Imports
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

// Start of class 
public class Midi {
	
	// Instance variables 
	float beatsPerMinute;
	Sequencer sequencer;
	boolean isOpen;
	boolean isPlaying;
	
	// Main constructor 
	public Midi() {
		// Gets the sequencer of the song on the device 
		try {
			sequencer = MidiSystem.getSequencer();
		} 
		// If the music file cannot be found, throw exception 
		catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
	}

	// Methods 
	// Used to identify songs and play them 
	public void PlayMidi(String fileName) throws IOException, InvalidMidiDataException, MidiUnavailableException {
		if (isPlaying == false) {
			sequencer.open();
			isOpen = true;	
			// Create a stream of the file 
			InputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
			sequencer.setSequence(is);
        	// Plays the song 
			sequencer.setLoopStartPoint(0);
			sequencer.setLoopEndPoint(sequencer.getTickLength());
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			// Gets the BPM of the song 
			beatsPerMinute = sequencer.getTempoInBPM();
			sequencer.start();
			isPlaying = true;
        }
	}

	// Used to stop playing the Midi file 
	public void StopMidi() {
		// If the song is playing, stop it 
		if (isOpen == true && isPlaying == true) {
			sequencer.stop();
			isOpen = false;
			isPlaying = false;
		}
		// Else do not do anything 
		else {
		}
	}
	
	// Used to get the tempo of the song 
	public float getTempo() {
		return beatsPerMinute;
	}
	
	// Used to set the tempo of the song 
	public void setTempo(float i) {
		sequencer.setTempoInBPM(i);
	}
}