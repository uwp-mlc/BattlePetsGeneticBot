package skill;

public class Skill
{
	private Skills skillEnum;
	private int rechargeTime;
	
	public Skill(Skills skillEnum, int rechargeTime)
	{		
		this.skillEnum = skillEnum;
		this.rechargeTime = rechargeTime;
	}
	
	public Skill(Skill otherSkill)
	{
		this.skillEnum = otherSkill.getSkillEnum();
		this.rechargeTime = otherSkill.getRechargeTime();
	}

	public Skills getSkillEnum()
	{
		return skillEnum;
	}

	public void setSkillEnum(Skills skillEnum)
	{
		this.skillEnum = skillEnum;
	}

	public int getRechargeTime() 
	{
		return rechargeTime;
	}

	public void setRechargeTime(int rechargeTime)
	{
		this.rechargeTime = rechargeTime;
	}
	
	public String getSkillName()
	{
		return skillEnum.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + rechargeTime;
		result = prime * result + ((skillEnum == null) ? 0 : skillEnum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Skill))
			return false;
		Skill other = (Skill) obj;
		if (rechargeTime != other.rechargeTime)
			return false;
		if (skillEnum != other.skillEnum)
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return skillEnum.toString();
	}
}
