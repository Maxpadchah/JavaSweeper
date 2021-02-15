package sweeper;

import org.w3c.dom.ranges.Range;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    public Bomb( int totalBombs ) {
        this.totalBombs = totalBombs;
        fixBombCount();
    }


    Box get( Coord coord ) {
        return bombMap.get(coord);
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            plaseBomb();
        }
    }

    // метод фиксированного колличества бомб
    private void fixBombCount() {
        int maxBomb = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBomb) totalBombs = maxBomb;
    }

    // метод размещения бомб
    private void plaseBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord)) continue;
            bombMap.set(coord, Box.BOMB);
            incNumbersArraundBomb(coord);
            break;
        }

    }

    private void incNumbersArraundBomb( Coord coord ) {
        // вокруг каждой бомбы поставили единицу
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (Box.BOMB != bombMap.get(around)) {
                bombMap.set(around, bombMap.get(around).getNextNumbersBox());
            }

        }
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
