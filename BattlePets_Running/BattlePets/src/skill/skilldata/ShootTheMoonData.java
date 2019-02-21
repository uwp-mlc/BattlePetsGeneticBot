package skill.skilldata;

import skill.Skills;

public class ShootTheMoonData extends SkillData
{
	private Skills predictedSkillEnum;
		
	public ShootTheMoonData(Skills skill, Skills predictedSkillEnum)
	{
		super(skill);
		this.predictedSkillEnum = predictedSkillEnum;
	}
	
	public Skills getPredictedSkillEnum()
	{
		return predictedSkillEnum;
	}
	
}
