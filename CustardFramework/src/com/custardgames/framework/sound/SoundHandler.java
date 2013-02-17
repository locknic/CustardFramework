package com.custardgames.framework.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundHandler
{
	private Map<String, Clip> sounds = new HashMap<String, Clip>();
	
	public SoundHandler()
	{
		
	}
	
	public void loadSound(String id, String backgroundLocation)
	{
        try
		{
        	AudioInputStream sample = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(backgroundLocation));
        	Clip sound = AudioSystem.getClip();
			sound.open(sample);
			sounds.put(id, sound);
		}
		catch (LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedAudioFileException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playSoundLoop(String id)
	{
		sounds.get(id).stop();
		sounds.get(id).loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void playSound(String id)
	{
		sounds.get(id).stop();
		sounds.get(id).setFramePosition(0);
		sounds.get(id).start();
		sounds.get(id).setFramePosition(0);
	}
	
	public void stopSound(String id)
	{
		if (sounds.containsKey(id))
		{
			sounds.get(id).stop();
		}
	}
	
	public void unloadAll()
	{
		for (Map.Entry<String, Clip> entry : sounds.entrySet()) 
		{
			  String key = entry.getKey();
			  sounds.get(key).stop();
		}
		sounds = null;
	}
}
