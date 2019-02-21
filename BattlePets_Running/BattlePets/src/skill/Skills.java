package skill;

import utils.Utils;

public enum Skills
{
	ROCK_THROW,
	SCISSORS_POKE,
	PAPER_CUT,
	SHOOT_THE_MOON,
	REVERSAL_OF_FORTUNE;
	
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
	
	// for team 2
	// TODO
	public int getRechargeTime()
	{
		switch (this) {
		case ROCK_THROW:
		case SCISSORS_POKE: 
		case PAPER_CUT: 
			return 1;
		case SHOOT_THE_MOON: 
		case REVERSAL_OF_FORTUNE: 
			return 6;
		default: return 0;
			
		}
	}

	// For team 9
	/**
	* Returns if the skill is a 'basic' skill
	* @return true if basic skill, else false
	*/
	public boolean isBasicSkill()
    {
    	if((this == Skills.ROCK_THROW || this == Skills.SCISSORS_POKE ||
    			this == Skills.PAPER_CUT))
    		return true;
    	else
    		return false;
    }
	
	// For team 12
	public static final int NUM_SKILLS = 5;
	
	// for team 13
	/**
	 * Returns a Skills array
	 * @return
	 */
	public static Skills[] getNumSkills(){
		return Skills.values();
	}
		

}
