package config;

import java.util.Observable;
import event.BaseEvent;

public class StupidObservable extends Observable
{
	public StupidObservable()
	{
		
	}

	public void fireEvent(BaseEvent event)
	{
		this.setChanged();
		this.notifyObservers(event);
		this.clearChanged();
	}
}
