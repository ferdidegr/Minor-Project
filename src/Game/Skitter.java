package Game;

import java.io.BufferedInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import Game.Monster;
import Game.Player;

public class Skitter {
	/** Maximum data buffers we will need. */
	  public static final int NUM_BUFFERS = 64;

	  /** Maximum emissions we will need. */
	  public static final int NUM_SOURCES = 16;

	  /** Index of background sound */
	  public static final int background = 0;

	  /** Index of skitter sound */
	  public static final int skitter = 1;

	  /** Index of gun 2 sound */
	  public static final int GUN2 = 2;

	  /** Buffers hold sound data. */
	  IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);

	  /** Sources are points emitting sound. */
	  IntBuffer source = BufferUtils.createIntBuffer(NUM_BUFFERS);

	  /** Position of the source sound. */
	  FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3*NUM_BUFFERS);

	  /** Velocity of the source sound. */
	  FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3*NUM_BUFFERS);

	  /** Position of the listener. */
	  FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	  /** Velocity of the listener. */
	  FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	  /** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
	  FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();
	  
	  /**
	   * boolean LoadALData()
	   *
	   *  This function will load our sample data from the disk using the Alut
	   *  utility and send the data into OpenAL as a buffer. A source is then
	   *  also created to play that buffer.
	   */
	  int loadALData(ArrayList<Monster> monsterlijst) {

	    // Load wav data into a buffer.
	    AL10.alGenBuffers(buffer);

	    if(AL10.alGetError() != AL10.AL_NO_ERROR)
	      return AL10.AL_FALSE;

	  //Loads the wave file from your file system
	    java.io.FileInputStream fin = null;
	    try {
	      fin = new java.io.FileInputStream("res/Sound/button-3.wav");
	    } catch (java.io.FileNotFoundException ex) {
	      ex.printStackTrace();
	      return AL10.AL_FALSE;
	    }
	    WaveData waveFile = WaveData.create(new BufferedInputStream(fin));
	    try {
	      fin.close();
	    } catch (java.io.IOException ex) {
	    }
	    
	    AL10.alBufferData(buffer.get(background), waveFile.format, waveFile.data, waveFile.samplerate);
	    waveFile.dispose();

	    
	  //Loads the wave file from your file system
	    fin = null;
	    try {
	      fin = new java.io.FileInputStream("res/Sound/skitter.wav");
	    } catch (java.io.FileNotFoundException ex) {
	      ex.printStackTrace();
	      return AL10.AL_FALSE;
	    }
	    waveFile = WaveData.create(new BufferedInputStream(fin));
	    try {
	      fin.close();
	    } catch (java.io.IOException ex) {
	    }
	    
	    AL10.alBufferData(buffer.get(skitter), waveFile.format, waveFile.data, waveFile.samplerate);
	    waveFile.dispose();
/*
	    waveFile = WaveData.create("Gun2.wav");
	    AL10.alBufferData(buffer.get(GUN2), waveFile.format, waveFile.data, waveFile.samplerate);
	    waveFile.dispose();
	    */

	    // Bind buffers into audio sources.
	    AL10.alGenSources(source);

	    if (AL10.alGetError() != AL10.AL_NO_ERROR)
	      return AL10.AL_FALSE;

	    AL10.alSourcei(source.get(background), AL10.AL_BUFFER,   buffer.get(background));
	    AL10.alSourcef(source.get(background), AL10.AL_PITCH,    1.0f          );
	    AL10.alSourcef(source.get(background), AL10.AL_GAIN,     1.0f          );
	    AL10.alSource (source.get(background), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(background*3));
	    AL10.alSource (source.get(background), AL10.AL_VELOCITY, (FloatBuffer) sourceVel.position(background*3));
	    AL10.alSourcei(source.get(background), AL10.AL_LOOPING,  AL10.AL_TRUE  );

	    for (Monster mon:monsterlijst){
	    
	    AL10.alSourcei(source.get(mon.getID()), AL10.AL_BUFFER,   buffer.get(skitter));
	    AL10.alSourcef(source.get(mon.getID()), AL10.AL_PITCH,    1.0f          );
	    AL10.alSourcef(source.get(mon.getID()), AL10.AL_GAIN,     1.0f          );
	    AL10.alSource (source.get(mon.getID()), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(mon.getID()*3));
	    AL10.alSource (source.get(mon.getID()), AL10.AL_VELOCITY, (FloatBuffer) sourceVel.position(mon.getID()*3));
	    AL10.alSourcei(source.get(mon.getID()), AL10.AL_LOOPING,  AL10.AL_FALSE );
	    
	    }
	    
//	    AL10.alSourcei(source.get(GUN2), AL10.AL_BUFFER,   buffer.get(GUN2));
//	    AL10.alSourcef(source.get(GUN2), AL10.AL_PITCH,    1.0f          );
//	    AL10.alSourcef(source.get(GUN2), AL10.AL_GAIN,     1.0f          );
//	    AL10.alSource (source.get(GUN2), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(GUN2*3));
//	    AL10.alSource (source.get(GUN2), AL10.AL_VELOCITY, (FloatBuffer) sourceVel.position(GUN2*3));
//	    AL10.alSourcei(source.get(GUN2), AL10.AL_LOOPING,  AL10.AL_FALSE );

	    // Do another error check and return.
	    if (AL10.alGetError() == AL10.AL_NO_ERROR)
	      return AL10.AL_TRUE;

	    return AL10.AL_FALSE;
	  }
	  
	  /**
	   * void setListenerValues()
	   *
	   *  We already defined certain values for the Listener, but we need
	   *  to tell OpenAL to use that data. This function does just that.
	   */
	  void setListenerValues() {
	    AL10.alListener(AL10.AL_POSITION,    listenerPos);
	    AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
	    AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	  }

	  /**
	   * void killALData()
	   *
	   *  We have allocated memory for our buffers and sources which needs
	   *  to be returned to the system. This function frees that memory.
	   */
	  void killALData() {
	    AL10.alDeleteSources(source);
	    AL10.alDeleteBuffers(buffer);
	  }

	  public void execute(ArrayList<Monster> monsterlijst) {
		    // Initialize OpenAL and clear the error bit.
		    try{
		    	AL.create();
		    } catch (LWJGLException le) {
		    	le.printStackTrace();
		      return;
		    }
		    AL10.alGetError();

		    // Load the wav data.
		    if(loadALData(monsterlijst) == AL10.AL_FALSE) {
		      System.out.println("Error loading data.");
		      return;
		    }

		    setListenerValues();

		    // Begin the background sample to play.
		    //AL10.alSourcePlay(source.get(background));
	  }
	  
	  public void updatemonsters(ArrayList<Monster> monsterlijst){
		  for (Monster mon:monsterlijst){
			  System.out.println("mon update");
			  sourcePos.position(mon.getID()*3);
			  sourcePos.put((float) mon.getLocationX());
			  sourcePos.put((float) mon.getLocationY());
			  sourcePos.put((float) mon.getLocationZ());
			  AL10.alSource (source.get(mon.getID()), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(mon.getID()*3));
		  }
		  sourcePos.rewind();
	  }
	  
	  public void play(ArrayList<Monster> monsterlijst, Player player){
		  listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { (float)player.getLocationX(), (float)player.getLocationY(), (float)player.getLocationZ() }).rewind();
		  listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { (float)player.lookat().getX(), (float)player.lookat().getY(), (float)player.lookat().getZ(),  0.0f, 1.0f, 0.0f }).rewind();
		  setListenerValues();
		  int playing;
		  for (Monster mon:monsterlijst){
			  System.out.println("playcheck");
			  playing = AL10.alGetSourcei(source.get(mon.getID()), AL10.AL_SOURCE_STATE);
			  if(playing != AL10.AL_PLAYING){
				  AL10.alSourcePlay(source.get(mon.getID()));
				  System.out.println("playing: " + mon.getID());
				  sourcePos.position(mon.getID()*3);
				  listenerPos.rewind();
				  System.out.println("monpos: " + sourcePos.get()+" "+ sourcePos.get()+" "+ sourcePos.get());
				  System.out.println("playerpos: " + listenerPos.get()+" "+ listenerPos.get()+" "+ listenerPos.get());
			  }
			  
		  }
	  }
	  
	  public void cleanup(){
		  AL10.alDeleteSources(source);
		  AL10.alDeleteBuffers(buffer);
		  AL.destroy();
	  }

}