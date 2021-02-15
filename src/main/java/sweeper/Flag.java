package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBokes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBokes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get( Coord coord ) {
        return flagMap.get(coord);
    }

    // открывает ячейку
    void setOpenedToBox( Coord coord ) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBokes--;
    }

    public void taggleFlagetToBox( Coord coord ) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setClosetBox(coord);
                break;
            case CLOSED:
                setFlagetToBox(coord);
                break;
        }
    }

    private void setClosetBox( Coord coord ) {
        flagMap.set(coord, Box.CLOSED);
    }

    private void setFlagetToBox( Coord coord ) {
        flagMap.set(coord, Box.FLAGED);
    }

    int getCountOfClosedBokes() {
        return countOfClosedBokes;
    }

    void setBombedToBox( Coord coord ) {
        flagMap.set(coord, Box.BOMBED);
    }

    // где есть бомбы но было закрыто - откроем
    void setOpenedToClosedBomb( Coord coord ) {
        if (flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord, Box.OPENED);
        }
    }

    // где поставили флажок но бомбы не было
    void setNobombToFlagesSafeBox( Coord coord ) {
        if (flagMap.get(coord) == Box.FLAGED) {
            flagMap.set(coord, Box.NOBOMB);
        }
    }


    int getCountOfFlagetBoxesAround( Coord coord ) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (flagMap.get(around) == Box.FLAGED) count++;
        }
        return count;
    }
}
