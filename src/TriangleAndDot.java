import java.util.Arrays;
import java.util.List;

public class TriangleAndDot {
    private List<double[]> triangle;
    private double[] dot;

    public TriangleAndDot() {
    }

    public TriangleAndDot(List<double[]> triangle, double[] dot) {
        this.triangle = triangle;
        this.dot = dot;
    }

    public List<double[]> getTriangle() {
        return triangle;
    }

    public void setTriangle(List<double[]> triangle) {
        this.triangle = triangle;
    }

    public double[] getDot() {
        return dot;
    }

    public void setDot(double[] dot) {
        this.dot = dot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TriangleAndDot{");
        for(int i = 0; i <= 2; i++) {
            double[] temp = triangle.get(i);
            sb.append("Corner ").append(i).append(" :").append(Arrays.toString(temp)).append("\n");
        }
        sb.append(", dot=").append(Arrays.toString(dot)).append("}");

        return sb.toString();
    }
}