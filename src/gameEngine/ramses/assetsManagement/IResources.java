package gameEngine.ramses.assetsManagement;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public interface IResources {
	public void load() throws IOException, UnsupportedAudioFileException, LineUnavailableException;
}
