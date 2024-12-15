import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // параметры (в релизе раскомментировать)
//        System.out.print("Path to CSV file (with filename): ");
        String path = "F:\\ВолГУ\\MLCourse\\Синтез данных\\task3v2.csv"; // для тестов
        // String path = scanner.nextLine();

        if (!new File(path).canRead()) {
            System.out.println("Can't reach the file. Please check the path!");
            System.exit(1);
        }

//        System.out.print("Enter delimiter: ");
        String delimiter = ";"; // для тестов
        // String delimiter = scanner.nextLine();

//        System.out.print("Enter number of neighbours: ");
        int nearestNeighbours = 3; // для тестов
        // int nearestNeighbours = scanner.nextInt();

        List<double[]> data = new ArrayList<>();

        // читаем csv
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(delimiter);
                double[] numbers = Arrays.stream(row)
                        .mapToDouble(s -> Double.parseDouble(s.replace(",", ".")))
                        .toArray();
                data.add(numbers);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(Arrays.deepToString(data.toArray()));

        // ищем ближайших соседей
        List<List<Cord>> allNearestNeighbours = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            double[] currentElement = data.get(i);
            List<Cord> neighbours = getAllNeighboursForCurrentElement(currentElement, data);

            neighbours.sort(Comparator.comparingDouble(Cord::getDistance));

            if (neighbours.size() > nearestNeighbours + 1) {
                neighbours = new ArrayList<>(neighbours.subList(1, nearestNeighbours + 1));
            } else {
                System.out.println("Not enough objects for element " + i);
                System.exit(1);
            }
            allNearestNeighbours.add(neighbours);
        }

        // генерация точки
        List<TriangleAndDot> allGenerated = new ArrayList<>();
        int count = 0;

        for (List<Cord> triangle : allNearestNeighbours) {
            double[] A = triangle.get(0).getElement();
            double[] B = triangle.get(1).getElement();
            double[] C = triangle.get(2).getElement();

            double S = 0.5 * Math.abs(A[0] * (B[1] - C[1]) + B[0] * (C[1] - A[1]) + C[0] * (A[1] - B[1]));

            if (S == 0) {
                System.out.println("Triangle " + count + " has zero area!");
                count++;
                continue;
            }
            count++;

            Random random = new Random();
            double r1 = random.nextDouble();
            double r2 = random.nextDouble();

            if (r1 + r2 > 1.0) {
                r1 = 1.0 - r1;
                r2 = 1.0 - r2;
            }

            double l1 = 1.0 - r1 - r2, l2 = r1, l3 = r2;

            double newX = l1 * A[0] + l2 * B[0] + l3 * C[0];
            double newY = l1 * A[1] + l2 * B[1] + l3 * C[1];

            TriangleAndDot triangleAndDot = new TriangleAndDot();
            triangleAndDot.setTriangle(Arrays.asList(A, B, C));
            triangleAndDot.setDot(new double[]{newX, newY});

            allGenerated.add(triangleAndDot);
        }

        // записываем в файл
        String savePath = "F:\\ВолГУ\\MLCourse\\Синтез данных\\1\\task3v2-10000.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            for (TriangleAndDot element : allGenerated) {
                StringBuilder s = new StringBuilder();

                for (double[] triangle : element.getTriangle()) {
                    s.append(triangle[0]).append(",").append(triangle[1]).append(";");
                }

                double[] dot = element.getDot();
                s.append(dot[0]).append(",").append(dot[1]);

                String result = s.toString();
                System.out.println(result);
                bw.write(result);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // рисуем
        SwingUtilities.invokeLater(()->

        {
            JFrame frame = new JFrame("Triglavian Method");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Vizualizator(allGenerated));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            frame.setBounds(dimension.width / 2 - 500, dimension.height / 2 - 300, 1000, 600);
            frame.setVisible(true);
        });
    }

    private static List<Cord> getAllNeighboursForCurrentElement(double[] currentElement, List<double[]> data) {
        List<Cord> neighbours = new ArrayList<>();
        for (double[] element : data) {
            double distance = 0;
            for (int j = 0; j < element.length; j++) {
                distance += Math.pow(currentElement[j] - element[j], 2);
            }
            neighbours.add(new Cord(Math.sqrt(distance), element));
        }
        return neighbours;
    }
}