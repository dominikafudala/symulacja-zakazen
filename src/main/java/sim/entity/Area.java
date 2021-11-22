package sim.entity;

public class Area {
    private final int width;
    private final int height;

    public Area(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean inArea(Person person){
        int x = (int)((person.getX() * 10)/.1);
        int y = (int)((person.getY() * 10) / .1);

        if(x < this.width && x > 0 && y < this.height && y > 0) return true;

        return false;
    }
}
