import java.io.*;
import java.util.*;

import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {
        Scanner scaner = new Scanner(System.in);
        System.out.print("Path to CSV file (with filename): ");
        String path = "F:\\ВолГУ\\MLCourse\\Синтез данных\\task3v2.csv"; // в релизе удалить!
//        String path = scaner.nextLine();
        boolean isExists = new File(path).canRead();
        if (!isExists) {
            System.out.println("Cant reach the file. Please check the path!");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(1);
        }

        System.out.print("Enter delimiter: ");
        String delimiter = ";"; // в релизе удалить!
//        String delimiter = scaner.nextLine();

        System.out.print("Enter number of neighbours: ");
        int nearestNeighbours = 3; // в релизе удалить!
//        int nearestNeighbours = scaner.nextInt();

        List<double[]> data = new ArrayList<>(); // в data храним оригинал объекта
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(delimiter);
                double[] numbers = new double[row.length];
                for (int i = 0; i < row.length; i++) {
                    String tmp = row[i].replace(",", ".");
                    numbers[i] = Double.parseDouble(tmp);
                }

                data.add(numbers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(Arrays.deepToString(data.toArray()));
        System.out.println();
        System.out.println();
        // находим всех ближайших соседей
        // заводим arraylist под хранение всех объектов [номер в data, дистанция] для каждого объекта
        List<List<Cord>> allNearestNeighbours = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            // берем текущий элемент
            double[] currentElement = data.get(i);

            // заводим arraylist под соседей текущего объекта
            List<Cord> currentElementNeighbours = new ArrayList<>();

            // считаем все растояния для текущего элемента
            currentElementNeighbours = getAllNeighboursForCurrentElement(currentElement, data);

            // сортируем
            currentElementNeighbours.sort((c1, c2) -> Double.compare(c1.getDistance(), c2.getDistance()));

            // отбираем только первые 3 без нулевого (до него расстояние = 0)
            if (currentElementNeighbours.size() > nearestNeighbours + 1) {
                currentElementNeighbours = new ArrayList<>(currentElementNeighbours.subList(1, nearestNeighbours + 1));
            } else {
                System.out.println("Объектов меньше, чем " + nearestNeighbours);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.exit(1);
            }
            // дрбавляем ближайших 3-х для текущего элемента
            allNearestNeighbours.add(currentElementNeighbours);
        }

//        // Печатаем текущий список
//        int counter = 1;
//        for(List<Cord> triangle : allNearestNeighbours) {
//            System.out.println("TRIANGLE " + counter);
//            for(Cord corner : triangle){
//                System.out.println(corner.getDistance() + " " + Arrays.toString(corner.getElement()));
//            }
//            System.out.println("--------------");
//            counter++;
//        }

        // ГЕНЕРАЦИЯ
        // заводим список под синтезированные объекты
        List<TriangleAndDot> allGenerated = new ArrayList<>();

        for (int i = 0; i < allNearestNeighbours.size(); i++) {


            //STOP

        }

        // записываем элементы в файл
        String savePath = "F:\\ВолГУ\\MLCourse\\Синтез данных\\1\\task3v2-1000.csv";
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(savePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (double[] element : allGenerated) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < element.length; i++) {
                s.append(element[i]).append(";");
            }
            s.deleteCharAt(s.length() - 1);
            String t = s.toString();
            String replace = t.replace(".", ",");
//            System.out.println(replace);
            try {

                bw.write(replace);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Cord> getAllNeighboursForCurrentElement(double[] currentElement, List<double[]> data) {
        List<Cord> neighbours = new ArrayList<>();
        for (double[] element : data) {
            double distance = 0;
            for (int j = 0; j < element.length; j++) {
                double pow = Math.pow(currentElement[j] - element[j], 2);
                distance += pow;
            }
            distance = Math.sqrt(distance);
            neighbours.add(new Cord(distance, element));
        }
        return neighbours;
    }
}
