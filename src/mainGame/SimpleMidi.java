/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The SimpleMidi class is a simplified version of a Midi player used to play music and
 *sounds in the game.  
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
public class SimpleMidi {
	
	// Instance variables 
	Sequencer sequencer;
	
	// Main constructor 
	public SimpleMidi() {
		// Try to play the sound through the sequencer
		try {
			sequencer = MidiSystem.getSequencer();
		} 
		// If the sound file cannot be found, throw an exception 
		catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	// Method
	// Used to play the sounds in the game 
	public void PlayMidi(String fileName) throws IOException, InvalidMidiDataException, MidiUnavailableException {
		// Play the sound 
		sequencer.open();
        InputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
        sequencer.setSequence(is);
        sequencer.start();
	}
}