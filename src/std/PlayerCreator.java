package std;

import kernel.net.Connection;
import kernel.net.ConnectionHandler;
import kernel.net.TermCap;
import kernel.Kernel;
import util.*;

import java.io.IOException;
import java.util.*;

public class PlayerCreator implements ConnectionHandler {

    private static enum State {
        CHOOSE_RACE,
        CHOOSE_GENDER,
        CHOOSE_PROFILE,
        CHOOSE_RACIAL_FEATURES,
        CHOOSE_CAREER,
        CHOOSE_ADVANCE,
        CHOOSE_PHYSICAL_FEATURES,
        CHOOSE_BACKGROUND,
        CHOOSE_ACCEPT
    }

    private static final Table<DistinguishingMark> distinguishingMarkTable = new Table<DistinguishingMark>()
            .addRow(5, DistinguishingMark.POX_MARKS)
            .addRow(10, DistinguishingMark.RUDDY_FACED)
            .addRow(15, DistinguishingMark.SCAR)
            .addRow(20, DistinguishingMark.TATOO)
            .addRow(25, DistinguishingMark.EARRING)
            .addRow(29, DistinguishingMark.RAGGED_EAR)
            .addRow(35, DistinguishingMark.NOSE_RING)
            .addRow(39, DistinguishingMark.WART)
            .addRow(45, DistinguishingMark.BROKEN_NOSE)
            .addRow(50, DistinguishingMark.MISSING_TOOTH)
            .addRow(55, DistinguishingMark.SNAGGLE_TEETH)
            .addRow(60, DistinguishingMark.LAZY_EYE)
            .addRow(65, DistinguishingMark.MISSING_EYEBROW)
            .addRow(70, DistinguishingMark.MISSING_DIGIT)
            .addRow(75, DistinguishingMark.MISSING_NAIL)
            .addRow(80, DistinguishingMark.DISTINCTIVE_GAIT)
            .addRow(84, DistinguishingMark.CURIOUS_SMELL)
            .addRow(89, DistinguishingMark.HUGE_NOSE)
            .addRow(94, DistinguishingMark.LARGE_MOLE)
            .addRow(98, DistinguishingMark.SMALL_BOLD_PATCH)
            .addRow(100, DistinguishingMark.STRANGE_COLORED_EYES);

    private static final Table<StarSign> starSignTable = new Table<StarSign>()
            .addRow(5, StarSign.WYMUND_THE_ANCHORITE)
            .addRow(10, StarSign.THE_BIG_CROSS)
            .addRow(15, StarSign.THE_LIMNERS_LINE)
            .addRow(20, StarSign.GNUTHUS_THE_EX)
            .addRow(25, StarSign.DRAGOMAS_THE_DRAGE)
            .addRow(30, StarSign.THE_GLOAMING)
            .addRow(35, StarSign.GRUNGNIS_BALDRIC)
            .addRow(40, StarSign.MAMMIT_THE_WISE)
            .addRow(45, StarSign.MUMMIT_THE_FOOL)
            .addRow(50, StarSign.THE_TWO_BULLOCKS)
            .addRow(55, StarSign.THE_DANCER)
            .addRow(60, StarSign.THE_DRUMMER)
            .addRow(65, StarSign.THE_PIPER)
            .addRow(70, StarSign.VOBIST_THE_FAINT)
            .addRow(75, StarSign.THE_BROKEN_CART)
            .addRow(80, StarSign.THE_GREASED_GOAT)
            .addRow(85, StarSign.RHYAS_CAULDRON)
            .addRow(90, StarSign.CACKELFAX_THE_COCKEREL)
            .addRow(98, StarSign.THE_BONESAW)
            .addRow(100, StarSign.THE_WITCHLING_STAR);

    private static final Table<Province> provinceTable = new Table<Province>()
            .addRow(1, Province.AVERLAND)
            .addRow(2, Province.HOCHLAND)
            .addRow(3, Province.MIDDENLAND)
            .addRow(4, Province.NORDLAND)
            .addRow(5, Province.OSTERMARK)
            .addRow(6, Province.OSTLAND)
            .addRow(7, Province.REIKLAND)
            .addRow(8, Province.STIRLAND)
            .addRow(9, Province.TALABECLAND)
            .addRow(10, Province.WISSENLAND);

    private static final Table<Settlement> settlementTable = new Table<Settlement>()
            .addRow(1, Settlement.CITY)
            .addRow(2, Settlement.PROSPEROUS_TOWN)
            .addRow(3, Settlement.MARKET_TOWN)
            .addRow(4, Settlement.FORTIFIED_TOWN)
            .addRow(5, Settlement.FARMING_VILLAGE)
            .addRow(6, Settlement.POOR_VILLAGE)
            .addRow(7, Settlement.SMALL_SETTLEMENT)
            .addRow(8, Settlement.CATTLE_FARM)
            .addRow(9, Settlement.ARABLE_FARM)
            .addRow(10, Settlement.HOVEL);

    private final Kernel kernel;
    private final Player player;
    private Connection cnt = null;
    private State state = State.CHOOSE_RACE;
    private Race race = null;
    private Gender gender = null;
    private Career career = null;
    private Stat advance = null;
    private Profile profile = null;
    private RacialFeatures racialFeatures = null;
    private PhysicalFeatures physicalFeatures = null;
    private Background background = null;

    public PlayerCreator(Kernel kernel, Player player) {
        this.kernel = kernel;
        this.player = player;
    }

    @Override
    public void handleConnect(Connection cnt) {
        this.cnt = cnt;
        changeState(State.CHOOSE_RACE);
    }

    @Override
    public void handleDisconnect() {
        // ignore
    }

    @Override
    public void handleInput(String str) {
        if (str.isEmpty()) {
            return;
        }
        String argv[] = str.split(" ", 2);
        if (argv[0].equals("help")) {
            if (argv.length == 1) {
                cnt.output("Usage: help <topic>\n");
                return;
            }
            try {
                cnt.output(FileTk.readFile(argv[1]));
            } catch (IOException e) {
                cnt.output("No help available for '" + argv[1] + "'.");
            }
            return;
        }
        switch (state) {
        case CHOOSE_RACE:
            if (argv[0].equals("pick")) {
                try {
                    race = Race.valueOf(argv[1]);
                    changeState(State.CHOOSE_GENDER);
                } catch (IllegalArgumentException e) {
                    cnt.output("No such race, try again.\n");
                }
            }
            break;
        case CHOOSE_GENDER:
            try {
                gender = Gender.valueOf(argv[0]);
                changeState(State.CHOOSE_PROFILE);
            } catch (IllegalArgumentException e) {
                cnt.output("No such gender, try again.\n");
            }
            return;
        case CHOOSE_PROFILE:
            if (!Boolean.valueOf(argv[0])) {
                changeState(state);
                return;
            }
            changeState(State.CHOOSE_RACIAL_FEATURES);
            break;
        case CHOOSE_RACIAL_FEATURES:
            if (!Boolean.valueOf(argv[0])) {
                changeState(state);
                return;
            }
            changeState(State.CHOOSE_CAREER);
            break;
        case CHOOSE_CAREER:
            if (argv[0].equals("pick")) {
                try {
                    career = Career.valueOf(argv[1]);
                    if (!race.getCareerChoices().contains(career)) {
                        cnt.output("That career is not available to your race, pick another.\n");
                    } else {
                        changeState(State.CHOOSE_ADVANCE);
                    }
                } catch (IllegalArgumentException e) {
                    cnt.output("No such career, try again.\n");
                }
                return;
            }
            break;
        case CHOOSE_ADVANCE:
            if (argv[0].equals("advance")) {
                try {
                    advance = Stat.valueOf(argv[1]);
                    if (!career.getAdvances().containsKey(advance)) {
                        cnt.output("That advance is not available to your career, pick another.\n");
                    } else {
                        changeState(State.CHOOSE_PHYSICAL_FEATURES);
                    }
                } catch (IllegalArgumentException e) {
                    cnt.output("No such stat, try again.\n");
                }
                return;
            }
            break;
        case CHOOSE_PHYSICAL_FEATURES:
            if (!Boolean.valueOf(argv[0])) {
                changeState(state);
                return;
            }
            changeState(State.CHOOSE_BACKGROUND);
            break;
        case CHOOSE_BACKGROUND:
            if (!Boolean.valueOf(argv[0])) {
                changeState(state);
                return;
            }
            changeState(State.CHOOSE_ACCEPT);
            break;
        case CHOOSE_ACCEPT:
            if (!Boolean.valueOf(argv[0])) {
                changeState(State.CHOOSE_RACE);
                return;
            }
            gotoAtlantis();
            break;
        }
    }

    public void changeState(State state) {
        this.state = state;
        cnt.output(TermCap.CLS.toString());
        switch (state) {
        case CHOOSE_RACE:
            cnt.output("What race do you want to play? You can choose from " +
                       Grammar.makeList((Object[])Race.values()) + " " +
                       "Each race has different strengths and weaknesses. An overview of each can be found through " +
                       "\"help <race>\". Once you have read these over, \"pick <the race>\" that appeals to you most.\n");
            break;
        case CHOOSE_GENDER:
            cnt.output("Are you male or female? ");
            break;
        case CHOOSE_PROFILE:
            rollProfile();
            cnt.output("Now that you have chosen your race, you can generate your stats. Your PC is " +
                       "defined by stats, which represent your character’s raw ability in a variety of " +
                       "physical and mental areas. These are broken into two groups, the Main Profile and the " +
                       "Secondary Profile. The stats in the Main Profile are rated on a scale of 0-100, " +
                       "with higher scores being better. The stats in the Secondary Profile are usually " +
                       "rated on a scale of 0-10 and again higher scores are better.\n\n" +
                       profile + "\n\n" +
                       "Do you accept these? ");
            break;
        case CHOOSE_RACIAL_FEATURES:
            rollRacialFeatures();
            cnt.output("Your character’s race provides some additional abilities, known as " +
                       "skills and talents. Your character will get more skills and talents from his " +
                       "starting career a little later. These abilities help define where you came " +
                       "from and what you can do. For more information in skills and talents, " +
                       "see \"help skills\" and \"help talents\".\n\n" +
                       racialFeatures + "\n\n" +
                       "Do you accept these? ");
            break;
        case CHOOSE_CAREER:
            cnt.output("Each character has a starting career. This represents what your character did before " +
                       "becoming an adventurer. You may choose among:\n\n" +
                       Grammar.makeList(race.getCareerChoices()) + "\n\n" +
                       "For more information about a career, see \"help <career>\". Once you have read these over, " +
                       "\"pick <the race>\" that appeals to you most.\n");
            break;
        case CHOOSE_ADVANCE:
            cnt.output("Your character is not an absolute beginner. To represent previous experience, your character " +
                       "is allowed one free \"advance\".\n\n" +
                       career.getAdvances() + "\n\n" +
                       "Once you have decided, do \"advance <stat>\".\n");
            break;
        case CHOOSE_PHYSICAL_FEATURES:
            rollPhysicalFeatures();
            cnt.output(physicalFeatures + "\n\n" +
                       "Do you accept this? ");
            break;
        case CHOOSE_BACKGROUND:
            rollBackground();
            cnt.output(background + "\n\n" +
                       "Do you accept this? ");
            break;
        case CHOOSE_ACCEPT:
            cnt.output(this + "\n\n" +
                       "Do you accept this? ");
            break;
        }
    }

    private void rollProfile() {
        profile = new Profile();
        for (Stat key : Stat.values()) {
            profile.stats.put(key, race.getStatBase(key) +
                                   race.getStatDice(key).roll());
        }
    }

    private void rollRacialFeatures() {
        Random rnd = new Random(System.nanoTime());
        racialFeatures = new RacialFeatures();
        for (Choice<Skill> choice : race.getSkillChoices()) {
            int numChosen = 0;
            List<Skill> options = choice.getOptions();
            while (++numChosen <= choice.getNumRequired() &&
                   !options.isEmpty()) {
                racialFeatures.skills.add(options.remove(rnd.nextInt(options.size())));
            }
        }
        for (Choice<Talent> choice : race.getTalentChoices()) {
            int numChosen = 0;
            List<Talent> options = choice.getOptions();
            while (++numChosen <= choice.getNumRequired() &&
                   !options.isEmpty()) {
                racialFeatures.talents.add(options.remove(rnd.nextInt(options.size())));
            }
        }
    }

    private void rollPhysicalFeatures() {
        physicalFeatures = new PhysicalFeatures();
        physicalFeatures.height = race.getHeightBase(gender) + race.getHeightDice().roll();
        physicalFeatures.weight = race.getWeightBase() + race.getWeightDice().roll();
        physicalFeatures.hairColor = race.getHairColorTable().roll(Die.D10);
        physicalFeatures.eyeColor = race.getEyeColorTable().roll(Die.D10);
        physicalFeatures.distinguishingMark = distinguishingMarkTable.roll(Die.D100);
    }

    private void rollBackground() {
        background = new Background();
        background.siblings = race.getSiblingsTable().roll(Die.D10);
        background.starSign = starSignTable.roll(Die.D100);
        background.age = race.getAgeBase() + race.getAgeDice().roll();
        background.province = provinceTable.roll(Die.D10);
        background.settlement = settlementTable.roll(Die.D10);
    }

    private void gotoAtlantis() {
        // TODO: apply content to player
        if (player.save()) {
            cnt.setAvatar(player);
        } else {
            try {
                cnt.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Profile {
        final Map<Stat, Integer> stats = new HashMap<Stat, Integer>();

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder();
            for (Map.Entry<Stat, Integer> entry : stats.entrySet()) {
                ret.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }
            return ret.toString();
        }
    }

    private static class RacialFeatures {
        final List<Skill> skills = new ArrayList<Skill>();
        final List<Talent> talents = new ArrayList<Talent>();

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder();
            ret.append("Skills: ").append(Grammar.makeList(skills)).append("\n");
            ret.append("Talents: ").append(Grammar.makeList(talents)).append("\n");
            return ret.toString();
        }
    }

    private static class PhysicalFeatures {
        int height;
        int weight;
        String hairColor;
        String eyeColor;
        DistinguishingMark distinguishingMark;

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder();
            ret.append("You are ").append(height).append(" cm tall, and your weight is ").append(weight).append(" kilograms. ");
            ret.append("You have ").append(hairColor).append(" hair, and ").append(eyeColor).append(" eyes. ");
            ret.append("You have ").append(distinguishingMark).append(".");
            return ret.toString();
        }
    }

    private static class Background {
        int siblings;
        StarSign starSign;
        int age;
        Province province;
        Settlement settlement;

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder();
            ret.append("You were born under ").append(starSign)
                    .append(", which signifies ").append(starSign.getSignificance()).append(". ");
            ret.append("You have ").append(Grammar.convertNumber(siblings)).append(" siblings. ");
            ret.append("You are ").append(age).append(" years old. ");
            ret.append("You were born in a ").append(settlement).append(" in ").append(province).append(".");
            return ret.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        return ret.toString();
    }
}
