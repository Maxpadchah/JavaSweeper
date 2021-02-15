package sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges {
    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();

    // функция для установки размера
    public static void setSize( Coord _size ) {
        size = _size;
        // Создаем новый массив с координатами и пробегаемся по всем координатам
        allCoords = new ArrayList<Coord>();
        for (int y = 0; y < _size.y; y++) {
            for (int x = 0; x < _size.x; x++) {
                allCoords.add(new Coord(x, y));
            }
        }
    }

    public static Coord getSize() {
        return size;
    }

    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    // проверяет находимся ли мы в массиве или поле
    static boolean inRange( Coord coord ) {
        return coord.x >= 0 && coord.x < size.x && coord.y >= 0 && coord.y < size.y;
    }

    // метод получения случайной координаты
    static Coord getRandomCoord() {
        return new Coord(random.nextInt(size.x), random.nextInt(size.y));
    }

    static ArrayList<Coord> getCoordsAround( Coord coord ) {
        Coord arraund;
        ArrayList<Coord> list = new ArrayList<>();
        for (int x = coord.x - 1; x <= coord.x + 1; x++) {
            for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                if (inRange(arraund = new Coord(x, y))) {
                    if (!arraund.equals(coord)) {
                        list.add(arraund);
                    }
                }
            }
        }
        return list;
    }
}
