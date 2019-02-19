package edu.dselent.event;


public class RoundStartEvent extends BaseEvent
{
	private final int roundNumber;
	
	public RoundStartEvent(RoundStartEventBuilder builder)
	{
		super(EventTypes.ROUND_START);
		roundNumber = builder.roundNumber;
	}
	
	public int getRoundNumber()
	{
		return roundNumber;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + roundNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof RoundStartEvent))
			return false;
		RoundStartEvent other = (RoundStartEvent) obj;
		if (roundNumber != other.roundNumber)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RoundStartEvent [roundNumber=");
		builder.append(roundNumber);
		builder.append("]");
		return builder.toString();
	}

	public static class RoundStartEventBuilder
	{
		private Integer roundNumber;
		
		public RoundStartEventBuilder()
		{
			
		}

		public RoundStartEventBuilder withRoundNumber(Integer roundNumber) 
		{
			this.roundNumber = roundNumber;
			return this;
		}

		public RoundStartEvent Build()
		{
			return new RoundStartEvent(this);
		}
	}
	
}
