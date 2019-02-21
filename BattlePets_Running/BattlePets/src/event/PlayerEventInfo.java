package event;

import java.util.Set;

import player.PetTypes;
import player.PlayerTypes;
import skill.Skills;

public class PlayerEventInfo
{
	// TODO have UID for pets assigned by controlling classes
	private final String petName;
	private final PetTypes petType;
	private final PlayerTypes playerType;
	private final double startingHp;
	private final Set<Skills> skillSet;
	
	private PlayerEventInfo(PlayerEventInfoBuilder builder)
	{
		petName = builder.petName;
		petType = builder.petType;
		playerType = builder.playerType;
		startingHp = builder.startingHp;
		skillSet = builder.skillSet;
	}
	
	
	
	public String getPetName()
	{
		return petName;
	}

	public PetTypes getPetType()
	{
		return petType;
	}

	public PlayerTypes getPlayerType()
	{
		return playerType;
	}

	public double getStartingHp()
	{
		return startingHp;
	}

	public Set<Skills> getSkillSet()
	{
		return skillSet;
	}
	
	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(startingHp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((petName == null) ? 0 : petName.hashCode());
		result = prime * result + ((petType == null) ? 0 : petType.hashCode());
		result = prime * result + ((playerType == null) ? 0 : playerType.hashCode());
		result = prime * result + ((skillSet == null) ? 0 : skillSet.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PlayerEventInfo))
			return false;
		PlayerEventInfo other = (PlayerEventInfo) obj;
		if (Double.doubleToLongBits(startingHp) != Double.doubleToLongBits(other.startingHp))
			return false;
		if (petName == null) {
			if (other.petName != null)
				return false;
		} else if (!petName.equals(other.petName))
			return false;
		if (petType != other.petType)
			return false;
		if (playerType != other.playerType)
			return false;
		if (skillSet == null) {
			if (other.skillSet != null)
				return false;
		} else if (!skillSet.equals(other.skillSet))
			return false;
		return true;
	}


	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerEventInfo [petName=");
		builder.append(petName);
		builder.append(", petType=");
		builder.append(petType);
		builder.append(", playerType=");
		builder.append(playerType);
		builder.append(", StartingHp=");
		builder.append(startingHp);
		builder.append(", skillSet=");
		builder.append(skillSet);
		builder.append("]");
		return builder.toString();
	}

	public static class PlayerEventInfoBuilder
	{
		private String petName;
		private PetTypes petType;
		private PlayerTypes playerType;
		private Double startingHp;
		private Set<Skills> skillSet;
		
		public PlayerEventInfoBuilder()
		{
			
		}
		
		public PlayerEventInfoBuilder withPetName(String petName) 
		{
			this.petName = petName;
			return this;
		}
		
		public PlayerEventInfoBuilder withPetType(PetTypes petType) 
		{
			this.petType = petType;
			return this;
		}
		
		public PlayerEventInfoBuilder withPlayerType(PlayerTypes playerType) 
		{
			this.playerType = playerType;
			return this;
		}
		
		public PlayerEventInfoBuilder withStartingHp(Double startingHp) 
		{
			this.startingHp = startingHp;
			return this;
		}
		
		public PlayerEventInfoBuilder withSkillSet(Set<Skills> skillSet) 
		{
			this.skillSet = skillSet;
			return this;
		}
		
		public PlayerEventInfo build()
		{
			return new PlayerEventInfo(this);
		}
	}

}
