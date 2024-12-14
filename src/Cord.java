public class Cord {
    private double distance;
    private double[] element;

    public Cord(double distance, double[] element) {
        this.distance = distance;
        this.element = element;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double[] getElement() {
        return element;
    }

    public void setElement(double[] element) {
        this.element = element;
    }
}
