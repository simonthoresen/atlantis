package std;

public class Living extends Container {

    private final String name;

    public Living(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
