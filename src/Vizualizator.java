import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Vizualizator extends JPanel {
    private TriangleAndDot triangleAndDotList;

    public Vizualizator(TriangleAndDot triangleAndDot) {
        this.triangleAndDotList = triangleAndDot;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // белый фон
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

//        for (TriangleAndDot elem : triangleAndDotList) {
        double[] A = triangleAndDotList.getTriangle().get(0);
        double[] B = triangleAndDotList.getTriangle().get(1);
        double[] C = triangleAndDotList.getTriangle().get(2);
        // треугольник
        g2d.setColor(Color.GREEN);
        Path2D.Double triangle = new Path2D.Double();
        triangle.moveTo(A[0] + 300, A[1] + 300);
        triangle.lineTo(B[0] + 300, B[1] + 300);
        triangle.lineTo(C[0] + 300, C[1] + 300);
        triangle.closePath();
        g2d.fill(triangle);

        // обводка черным
        g2d.setColor(Color.BLACK);
        g2d.draw(triangle);

        //точка
        g2d.setColor(Color.RED);

        double[] dot = triangleAndDotList.getDot();
        int dotSize = 10;
        g2d.fillOval((int) ((dot[0] - dotSize / 2.0) + 300), (int) ((dot[1] - dotSize / 2.0) + 300), dotSize, dotSize);

        // подписи
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("A (%.2f, %.2f)", A[0], A[1]), (int) A[0] + 5, (int) A[1] - 5);
        g2d.drawString(String.format("B (%.2f, %.2f)", B[0], B[1]), (int) B[0] + 5, (int) B[1] - 5);
        g2d.drawString(String.format("C (%.2f, %.2f)", C[0], C[1]), (int) C[0] + 5, (int) C[1] - 5);
        g2d.drawString(String.format("Dot (%.2f, %.2f)", dot[0], dot[1]), (int) dot[0] + 5, (int) dot[1] - 5);
    }
}