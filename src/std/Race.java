package std;

import java.util.*;

public enum Race {

    DWARF(new Profile()
            .putStatBase(Stat.WEAPON_SKILL, 30)
            .putStatBase(Stat.BALLISTIC_SKILL, 20)
            .putStatBase(Stat.STRENGTH, 20)
            .putStatBase(Stat.TOUGHNESS, 30)
            .putStatBase(Stat.AGILITY, 10)
            .putStatBase(Stat.INTELLIGENCE, 20)
            .putStatBase(Stat.WILL_POWER, 20)
            .putStatBase(Stat.FELLOWSHIP, 10)
            .putStatBase(Stat.WOUNDS, 10)
            .putStatDice(Stat.WOUNDS, new Dice(new Die(4)))
            .putStatBase(Stat.MOVEMENT, 3)
            .putStatDice(Stat.FATE_POINTS, new Dice(new Die(3)))
            .setSkillChoices(
                    Arrays.asList(
                            new Choice<Skill>(1, Skill.COMMON_KNOWLEDGE_DWARFS),
                            new Choice<Skill>(1, Skill.SPEAK_LANGUAGE_KHAZALID),
                            new Choice<Skill>(1, Skill.SPEAK_LANGUAGE_REIKSPIEL),
                            new Choice<Skill>(1, Skill.TRADE_MINER, Skill.TRADE_SMITH, Skill.TRADE_STONEWORKER)))
            .setTalentChoices(
                    Arrays.asList(
                            new Choice<Talent>(1, Talent.DWARFCRAFT),
                            new Choice<Talent>(1, Talent.GRUDGEBORN_FURY),
                            new Choice<Talent>(1, Talent.NIGHT_VISION),
                            new Choice<Talent>(1, Talent.RESISTANCE_TO_MAGIC),
                            new Choice<Talent>(1, Talent.STOUT_HEARTED),
                            new Choice<Talent>(1, Talent.STURDY)))
            .setCareerChoices(
                    Arrays.asList(
                            Career.AGITATOR,
                            Career.BODY_GUARD,
                            Career.BURGLER,
                            Career.COACHMAN,
                            Career.ENTERTAINER,
                            Career.HUNTER,
                            Career.JAILER,
                            Career.MARINE,
                            Career.MERCENARY,
                            Career.MILITIAMAN,
                            Career.MINER,
                            Career.NOBLE,
                            Career.OUTLAW,
                            Career.PIT_FIGHTER,
                            Career.PROTAGONIST,
                            Career.RAT_CATCHER,
                            Career.RUNEBEARER,
                            Career.SCRIBE,
                            Career.SEAMAN,
                            Career.SERVANT,
                            Career.SHIELDBREAKER,
                            Career.SMUGGLER,
                            Career.SOLDIER,
                            Career.STUDENT,
                            Career.THIEF,
                            Career.TOLL_KEEPER,
                            Career.TOMB_ROBBER,
                            Career.TRADESMAN,
                            Career.TROLL_SLAYER,
                            Career.WATCHMAN))
            .putHeightBase(Gender.FEMALE, 135)
            .putHeightBase(Gender.MALE, 140)
            .setWeightBase(45)
            .setWeightDice(new Dice(new Die(45)))
            .setAgeBase(20)
            .setAgeDice(new Dice(new Die(95)))
            .setHairColorTable(
            new Table<String>()
                    .addRow(1, "ash blond")
                    .addRow(2, "yellow")
                    .addRow(3, "red")
                    .addRow(4, "copper")
                    .addRow(5, "light brown")
                    .addRow(6, "brown")
                    .addRow(7, "brown")
                    .addRow(8, "dark brown")
                    .addRow(9, "blue black")
                    .addRow(10, "black"))
            .setEyeColorTable(
            new Table<String>()
                    .addRow(1, "pale grey")
                    .addRow(2, "blue")
                    .addRow(3, "copper")
                    .addRow(4, "light brown")
                    .addRow(5, "light brown")
                    .addRow(6, "brown")
                    .addRow(7, "brown")
                    .addRow(8, "dark brown")
                    .addRow(9, "dark brown")
                    .addRow(10, "purple"))
            .setSiblingsTable(
                    new Table<Integer>()
                            .addRow(1, 0)
                            .addRow(3, 0)
                            .addRow(5, 1)
                            .addRow(7, 1)
                            .addRow(9, 2)
                            .addRow(10, 3))
    ),
    ELF(new Profile()),
    HALFLING(new Profile()),
    HUMAN(new Profile());

    private final Map<Stat, Integer> statBase;
    private final Map<Stat, Dice> statDice;
    private final List<Choice<Skill>> skillChoices;
    private final List<Choice<Talent>> talentChoices;
    private final List<Career> careerChoices;
    private final Map<Gender, Integer> heightBase;
    private final Dice heightDice;
    private final int weightBase;
    private final Dice weightDice;
    private final int ageBase;
    private final Dice ageDice;
    private final Table<String> hairColorTable;
    private final Table<String> eyeColorTable;
    private final Table<Integer> siblingsTable;
    private final Map<Gender, Table<String>> nameTable;

    private Race(Profile profile) {
        statBase = profile.statBase;
        statDice = profile.statDice;
        skillChoices = profile.skillChoices;
        talentChoices = profile.talentChoices;
        careerChoices = profile.careerChoices;
        heightBase = profile.heightBase;
        heightDice = profile.heightDice;
        weightBase = profile.weightBase;
        weightDice = profile.weightDice;
        ageBase = profile.ageBase;
        ageDice = profile.ageDice;
        hairColorTable = profile.hairColorTable;
        eyeColorTable = profile.eyeColorTable;
        siblingsTable = profile.siblingsTable;
        nameTable = profile.nameTable;
    }

    public int getStatBase(Stat stat) {
        System.err.println(stat);
        return statBase.get(stat);
    }

    public Dice getStatDice(Stat stat) {
        return statDice.get(stat);
    }

    public List<Choice<Skill>> getSkillChoices() {
        return skillChoices;
    }

    public List<Choice<Talent>> getTalentChoices() {
        return talentChoices;
    }

    public List<Career> getCareerChoices() {
        return careerChoices;
    }

    public int getHeightBase(Gender gender) {
        return heightBase.get(gender);
    }

    public Dice getHeightDice() {
        return heightDice;
    }

    public int getWeightBase() {
        return weightBase;
    }

    public Dice getWeightDice() {
        return weightDice;
    }

    public int getAgeBase() {
        return ageBase;
    }

    public Dice getAgeDice() {
        return ageDice;
    }
    
    public Table<String> getHairColorTable() {
        return hairColorTable;
    }

    public Table<String> getEyeColorTable() {
        return eyeColorTable;
    }

    public Table<Integer> getSiblingsTable() {
        return siblingsTable;
    }

    public Table<String> getNameTable(Gender gender) {
        return nameTable.get(gender);
    }

    private static class Profile {

        Map<Stat, Integer> statBase = new HashMap<Stat, Integer>();
        Map<Stat, Dice> statDice = new HashMap<Stat, Dice>();
        Table<Integer> fatePointsTable = Table.emptyTable();
        List<Choice<Skill>> skillChoices = Collections.emptyList();
        List<Choice<Talent>> talentChoices = Collections.emptyList();
        List<Career> careerChoices = Collections.emptyList();
        Map<Gender, Integer> heightBase = new HashMap<Gender, Integer>();
        Dice heightDice = new Dice(Die.D10, Die.D10, Die.D5);
        int weightBase;
        Dice weightDice;
        int ageBase;
        Dice ageDice;
        Table<String> hairColorTable = Table.emptyTable();
        Table<String> eyeColorTable = Table.emptyTable();
        Table<Integer> siblingsTable = Table.emptyTable();
        Map<Gender, Table<String>> nameTable = new HashMap<Gender, Table<String>>();
        
        Profile() {
            Dice D20 = new Dice(Die.D10, Die.D10);
            putStatDice(Stat.WEAPON_SKILL, D20);
            putStatDice(Stat.BALLISTIC_SKILL, D20);
            putStatDice(Stat.STRENGTH, D20);
            putStatDice(Stat.TOUGHNESS, D20);
            putStatDice(Stat.AGILITY, D20);
            putStatDice(Stat.INTELLIGENCE, D20);
            putStatDice(Stat.WILL_POWER, D20);
            putStatDice(Stat.FELLOWSHIP, D20);
            putStatBase(Stat.ATTACKS, 1);
            putStatDice(Stat.ATTACKS, Dice.NO_DICE);
            putStatDice(Stat.MOVEMENT, Dice.NO_DICE);
            putStatBase(Stat.MAGIC, 0);
            putStatDice(Stat.MAGIC, Dice.NO_DICE);
            putStatBase(Stat.INSANITY_POINTS, 0);
            putStatDice(Stat.INSANITY_POINTS, Dice.NO_DICE);
            putStatBase(Stat.FATE_POINTS, 0);
            putStatDice(Stat.FATE_POINTS, Dice.NO_DICE);
        }

        Profile putStatBase(Stat stat, int base) {
            statBase.put(stat, base);
            return this;
        }

        Profile putStatDice(Stat stat, Dice dice) {
            statDice.put(stat, dice);
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

        Profile setCareerChoices(List<Career> careerChoices) {
            this.careerChoices = careerChoices;
            return this;
        }

        Profile putHeightBase(Gender gender, int heightBase) {
            this.heightBase.put(gender, heightBase);
            return this;
        }

        Profile setHeightDice(Dice heightDice) {
            this.heightDice = heightDice;
            return this;
        }

        Profile setWeightBase(int weightBase) {
            this.weightBase = weightBase;
            return this;
        }

        Profile setWeightDice(Dice weightDice) {
            this.weightDice = weightDice;
            return this;
        }

        Profile setAgeBase(int ageBase) {
            this.ageBase = ageBase;
            return this;
        }

        Profile setAgeDice(Dice ageDice) {
            this.ageDice = ageDice;
            return this;
        }

        Profile setHairColorTable(Table<String> hairColorTable) {
            this.hairColorTable = hairColorTable;
            return this;
        }

        Profile setEyeColorTable(Table<String> eyeColorTable) {
            this.eyeColorTable = eyeColorTable;
            return this;
        }

        Profile setSiblingsTable(Table<Integer> siblingsTable) {
            this.siblingsTable = siblingsTable;
            return this;
        }

        Profile putNameTable(Gender gender, Table<String> nameTable) {
            this.nameTable.put(gender, nameTable);
            return this;
        }
    }
}
