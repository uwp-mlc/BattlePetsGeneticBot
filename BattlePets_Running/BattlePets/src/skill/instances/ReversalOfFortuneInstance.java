package skill.instances;

import skill.Skill;

public class ReversalOfFortuneInstance extends SkillInstance
{
	public ReversalOfFortuneInstance()
	{
		super();
	}
	
	public ReversalOfFortuneInstance(Skill skill)
	{
		super(skill, 0);
	}
	
	public ReversalOfFortuneInstance(Skill skill, int currentRechargeTime)
	{
		super(skill, currentRechargeTime);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("ReversalOfFortuneInstance []");
		return builder.toString();
	}
	
	
}
