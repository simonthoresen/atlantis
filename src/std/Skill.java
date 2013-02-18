package std;

import std.Stat;

public enum Skill {

    COMMON_KNOWLEDGE_DWARFS(Stat.INTELLIGENCE),
    SPEAK_LANGUAGE_KHAZALID(Stat.INTELLIGENCE),
    SPEAK_LANGUAGE_REIKSPIEL(Stat.INTELLIGENCE),
    TRADE_MINER(Stat.STRENGTH),
    TRADE_SMITH(Stat.STRENGTH),
    TRADE_STONEWORKER(Stat.STRENGTH);

    private final Stat stat;

    private Skill(Stat stat) {
        this.stat = stat;
    }

    public Stat getStat() {
        return stat;
    }
}
