package edu.rcos.pkmnrpi.main.util;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

//how to find midi files
public class MusicPlayer {
	public static void player(String filename) {
		try {
			// From file
			Sequence sequence = MidiSystem.getSequence(new File(filename));
			// Create a sequencer for the sequence
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);

			// Start playing
			sequencer.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		MusicPlayer.player("data/team-rocket.mid");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		MusicPlayer.player("data/team-rocket.mid");
	}
}