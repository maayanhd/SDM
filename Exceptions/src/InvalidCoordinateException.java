public class InvalidCoordinateException extends Exception {
    private int coordinateX;
    private int coordinateY;

    public InvalidCoordinateException(String message, int coordinateX, int coordinateY) {
        super(message);
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public InvalidCoordinateException(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public InvalidCoordinateException(String message, int coordinateX) {
        super(message);
        this.coordinateX = coordinateX;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
}
