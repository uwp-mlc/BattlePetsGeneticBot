package edu.dselent.event;

import edu.dselent.skill.Skills;

// TODO
// Should sub class this for each skill
// This class should not have the predictedSkillEnum, since it is only applicable for shoot the moon
public class AttackEvent extends BaseEvent
{
	private final int attackingPlayableIndex;
	private final int victimPlayableIndex;
	private final Skills attackingSkillChoice;
	private final Skills predictedSkillEnum;
	
	// TODO
	// For future classes enforce all groups use a Damage class holding all type of damage
	private final double randomDamage;
	private final double conditionalDamage;
	
	public AttackEvent(AttackEventBuilder builder)
	{
		super(EventTypes.ATTACK);
		
		// Let null pointer exceptions propagate, would indicate programmer error in this case
		
		this.attackingPlayableIndex = builder.attackingPlayableIndex;
		this.victimPlayableIndex = builder.victimPlayableIndex;
		this.attackingSkillChoice = builder.attackingSkillChoice;
		this.predictedSkillEnum = builder.predictedSkillEnum;
		
		randomDamage = builder.randomDamage;
		conditionalDamage = builder.conditionalDamage;
	}
	
	public int getAttackingPlayableIndex()
	{
		return attackingPlayableIndex;
	}

	public int getVictimPlayableIndex()
	{
		return victimPlayableIndex;
	}

	public Skills getAttackingSkillChoice()
	{
		return attackingSkillChoice;
	}

	public Skills getPredictedSkillEnum()
	{
		return predictedSkillEnum;
	}

	public double getRandomDamage()
	{
		return randomDamage;
	}

	public double getConditionalDamage()
	{
		return conditionalDamage;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + attackingPlayableIndex;
		result = prime * result + ((attackingSkillChoice == null) ? 0 : attackingSkillChoice.hashCode());
		long temp;
		temp = Double.doubleToLongBits(conditionalDamage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((predictedSkillEnum == null) ? 0 : predictedSkillEnum.hashCode());
		temp = Double.doubleToLongBits(randomDamage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + victimPlayableIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AttackEvent))
			return false;
		
		AttackEvent other = (AttackEvent) obj;
		
		if (attackingPlayableIndex != other.attackingPlayableIndex)
			return false;
		if (attackingSkillChoice != other.attackingSkillChoice)
			return false;
		if (Double.doubleToLongBits(conditionalDamage) != Double.doubleToLongBits(other.conditionalDamage))
			return false;
		if (predictedSkillEnum != other.predictedSkillEnum)
			return false;
		if (Double.doubleToLongBits(randomDamage) != Double.doubleToLongBits(other.randomDamage))
			return false;
		if (victimPlayableIndex != other.victimPlayableIndex)
			return false;
		
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("AttackEvent [attackingPlayerIndex=");
		builder.append(attackingPlayableIndex);
		builder.append(", victimPlayerIndex=");
		builder.append(victimPlayableIndex);
		builder.append(", attackingSkillChoice=");
		builder.append(attackingSkillChoice);
		builder.append(", predictedSkillEnum=");
		builder.append(predictedSkillEnum);
		builder.append(", randomDamage=");
		builder.append(randomDamage);
		builder.append(", conditionalDamage=");
		builder.append(conditionalDamage);
		builder.append("]");
		
		return builder.toString();
	}


	public static class AttackEventBuilder
	{
		private Integer attackingPlayableIndex;
		private Integer victimPlayableIndex;
		private Skills attackingSkillChoice;
		private Skills predictedSkillEnum;
		private Double randomDamage;
		private Double conditionalDamage;
		
		public AttackEventBuilder()
		{
			
		}

		public AttackEventBuilder withAttackingPlayableIndex(int attackingPlayableIndex) 
		{
			this.attackingPlayableIndex = attackingPlayableIndex;
			return this;
		}

		public AttackEventBuilder withVictimPlayableIndex(int victimPlayableIndex) 
		{
			this.victimPlayableIndex = victimPlayableIndex;
			return this;
		}

		public AttackEventBuilder withAttackingSkillChoice(Skills attackingSkillChoice) 
		{
			this.attackingSkillChoice = attackingSkillChoice;
			return this;
		}

		public AttackEventBuilder withPredictedSkillEnum(Skills predictedSkillEnum) 
		{
			this.predictedSkillEnum = predictedSkillEnum;
			return this;
		}

		public AttackEventBuilder withRandomDamage(double randomDamage) 
		{
			this.randomDamage = randomDamage;
			return this;
		}

		public AttackEventBuilder withConditionalDamage(double conditionalDamage) 
		{
			this.conditionalDamage = conditionalDamage;
			return this;
		}

		public AttackEvent build()
		{
			return new AttackEvent(this);
		}
	}
	
}
