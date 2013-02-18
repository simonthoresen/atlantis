package std;

public enum StarSign {

    WYMUND_THE_ANCHORITE("enduring"),
    THE_BIG_CROSS("clarity"),
    THE_LIMNERS_LINE("precision"),
    GNUTHUS_THE_EX("dutiful service"),
    DRAGOMAS_THE_DRAGE("courage"),
    THE_GLOAMING("illusion and mystery"),
    GRUNGNIS_BALDRIC("martial pursuits"),
    MAMMIT_THE_WISE("wisdom"),
    MUMMIT_THE_FOOL("instinct"),
    THE_TWO_BULLOCKS("fertility and craftsmanship"),
    THE_DANCER("love and attraction"),
    THE_DRUMMER("excess and hedonism"),
    THE_PIPER("the trickster"),
    VOBIST_THE_FAINT("darkness and uncertainty"),
    THE_BROKEN_CART("pride"),
    THE_GREASED_GOAT("denied passions"),
    RHYAS_CAULDRON("mercy, death and creation"),
    CACKELFAX_THE_COCKEREL("money and merchants"),
    THE_BONESAW("skill and learning"),
    THE_WITCHLING_STAR("magic");

    private final String significance;

    private StarSign(String significance) {
        this.significance = significance;
    }

    public String getSignificance() {
        return significance;
    }
}
