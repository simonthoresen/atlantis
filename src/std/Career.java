package std;

import std.Choice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import std.Stat;
import std.Skill;
import std.Talent;

public enum Career {
    AGITATOR(new Profile().putAdvance(Stat.WEAPON_SKILL, 10)),
    BODY_GUARD(new Profile()),
    BURGLER(new Profile()),
    COACHMAN(new Profile()),
    ENTERTAINER(new Profile()),
    HUNTER(new Profile()),
    JAILER(new Profile()),
    MARINE(new Profile()),
    MERCENARY(new Profile()),
    MILITIAMAN(new Profile()),
    MINER(new Profile()),
    NOBLE(new Profile()),
    OUTLAW(new Profile()),
    PIT_FIGHTER(new Profile()),
    PROTAGONIST(new Profile()),
    RAT_CATCHER(new Profile()),
    RUNEBEARER(new Profile()),
    SCRIBE(new Profile()),
    SEAMAN(new Profile()),
    SERVANT(new Profile()),
    SHIELDBREAKER(new Profile()),
    SMUGGLER(new Profile()),
    SOLDIER(new Profile()),
    STUDENT(new Profile()),
    THIEF(new Profile()),
    TOLL_KEEPER(new Profile()),
    TOMB_ROBBER(new Profile()),
    TRADESMAN(new Profile()),
    TROLL_SLAYER(new Profile()),
    WATCHMAN(new Profile());

    private final Map<Stat, Integer> advances;
    private final List<Choice<Skill>> skillChoices;
    private final List<Choice<Talent>> talentChoices;
    private final List<Career> careerExits;
    private final List<Career> careerEntries;

    private Career(Profile profile) {
        advances = profile.advances;
        skillChoices = profile.skillChoices;
        talentChoices = profile.talentChoices;
        careerExits = profile.careerExits;
        careerEntries = profile.careerEntries;
    }

    public Map<Stat, Integer> getAdvances() {
        return advances;
    }

    public List<Choice<Skill>> getSkillChoices() {
        return skillChoices;
    }

    public List<Choice<Talent>> getTalentChoices() {
        return talentChoices;
    }

    public List<Career> getCareerExits() {
        return careerExits;
    }

    public List<Career> getCareerEntries() {
        return careerEntries;
    }


    private static class Profile {

        Map<Stat, Integer> advances = new HashMap<Stat, Integer>();
        List<Choice<Skill>> skillChoices = Collections.emptyList();
        List<Choice<Talent>> talentChoices = Collections.emptyList();
        List<Career> careerExits = Collections.emptyList();
        List<Career> careerEntries = Collections.emptyList();

        Profile putAdvance(Stat stat, Integer inc) {
            this.advances.put(stat, inc);
            return this;
        }

        Profile setSkillChoices(List<Choice<Skill>> skillChoices) {
            this.skillChoices = skillChoices;
            return this;
        }

        Profile setTalentChoices(List<Choice<Talent>> talentChoices) {
            this.talentChoices = talentChoices;
            return this;
        }

        Profile setCareerExits(List<Career> careerExits) {
            this.careerExits = careerExits;
            return this;
        }

        Profile setCareerEntries(List<Career> careerEntries) {
            this.careerEntries = careerEntries;
            return this;
        }
    }
}
