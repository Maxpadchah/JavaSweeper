package sweeper;

public class Game {
    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public Game( int cols, int rows, int bombs ) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public GameState getState() {
        return state;
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox( Coord coord ) {
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        } else return flag.get(coord);
    }

    // метод при нажатии левой кнопки мыши
    public void pressLeftButton( Coord coord ) {
        if (gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    // Проверка на победу
    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if (flag.getCountOfClosedBokes() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
            }
        }

    }

    // обработка событий при нажатии левой кнопки мыши
    private void openBox( Coord coord ) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord);
                return;
            case FLAGED:
                return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxedArraund(coord);
                        return;
                    case BOMB:
                        openBomb(coord);
                        return;
                    default:
                        flag.setOpenedToBox(coord);
                        return;
                }
        }
    }

    void setOpenedToClosedBoxesAroundNumber( Coord coord ) {
        if (bomb.get(coord) != Box.BOMB) {
            if (flag.getCountOfFlagetBoxesAround(coord) == bomb.get(coord).getNumber()) {
                for (Coord around : Ranges.getCoordsAround(coord)) {
                    if (flag.get(around) == Box.CLOSED) {
                        openBox(around);
                    }
                }
            }
        }

    }

    // метод на событие БОМБА!
    private void openBomb( Coord bombed ) {
        state = GameState.BOMBER;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedToClosedBomb(coord);
            } else {
                flag.setNobombToFlagesSafeBox(coord);
            }
        }

    }

    // открывает все клетки вокруг текущей координаты
    private void openBoxedArraund( Coord coord ) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    // метод при нажатии правой кнопки мыши
    public void pressRightButton( Coord coord ) {
        if (gameOver()) return;
        flag.taggleFlagetToBox(coord);
    }

    // проверка проиграли мы или нет
    private boolean gameOver() {
        if (state == GameState.PLAYED) return false;
        start();
        return true;
    }
}
