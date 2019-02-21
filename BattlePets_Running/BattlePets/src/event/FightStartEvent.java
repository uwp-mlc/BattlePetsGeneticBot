package event;

import java.util.List;


public class FightStartEvent extends BaseEvent
{
	// TODO CHANGE TO FIGHT INDEX
	private final int fightNumber;
	private final List<PlayerEventInfo> playerEventInfo;
	
	public FightStartEvent(FightStartEventBuilder builder)
	{
		super(EventTypes.FIGHT_START);
		
		fightNumber = builder.fightNumber;
		playerEventInfo = builder.playerEventInfo;
		
	}
	
	public int getFightNumber()
	{
		return fightNumber;
	}

	public List<PlayerEventInfo> getPlayerEventInfo()
	{
		return playerEventInfo;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + fightNumber;
		result = prime * result + ((playerEventInfo == null) ? 0 : playerEventInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof FightStartEvent))
			return false;
		FightStartEvent other = (FightStartEvent) obj;
		if (fightNumber != other.fightNumber)
			return false;
		if (playerEventInfo == null) {
			if (other.playerEventInfo != null)
				return false;
		} else if (!playerEventInfo.equals(other.playerEventInfo))
			return false;
		return true;
	}


	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FightStartEvent [fightNumber=");
		builder.append(fightNumber);
		builder.append(", playerEventInfo=");
		builder.append(playerEventInfo);
		builder.append("]");
		return builder.toString();
	}



	public static class FightStartEventBuilder
	{
		private Integer fightNumber;
		private List<PlayerEventInfo> playerEventInfo;

		
		public FightStartEventBuilder()
		{
			
		}

		public FightStartEventBuilder withFightNumber(Integer fightNumber) 
		{
			this.fightNumber = fightNumber;
			return this;
		}

		public FightStartEventBuilder withPlayerEventInfo(List<PlayerEventInfo> playerEventInfo) 
		{
			this.playerEventInfo = playerEventInfo;
			return this;
		}

		public FightStartEvent Build()
		{
			return new FightStartEvent(this);
		}
	}
	
}
