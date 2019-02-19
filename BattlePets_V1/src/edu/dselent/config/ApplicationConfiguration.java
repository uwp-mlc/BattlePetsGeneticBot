package edu.dselent.config;

import java.util.Observable;

import edu.dselent.io.IoManager;

/**
 * Instead of passing certain objects throughout the entire application and creating a mess
 * Create this singleton for the configurations such as the I/O related classes
 * 
 * @author Doug
 *
 */
public enum ApplicationConfiguration
{
	INSTANCE;
	
	// Cannot set at compile time
	// Game type is not determined until runtime
	private IoManager ioManager;

	// Stupid Observerable
	// Just shoved this thing in the wrong place for now
	// Remove for next time
	//TODO
	private StupidObservable observable;
	
	public IoManager getIoManager()
	{
		return ioManager;
	}

	public void setIoManager(IoManager ioManager)
	{
		this.ioManager = ioManager;
	}

	public StupidObservable getObservable()
	{
		return observable;
	}

	public void setObservable(StupidObservable observable)
	{
		this.observable = observable;
	}
	
}
