import jaxb.schema.generated.SDMLocation;

import javax.xml.bind.JAXBContext;
import java.awt.geom.Point2D;
import java.util.Objects;

public class Location {
    private int coordinateX;
    private int coordinateY;
    private final CoordinatesRange upperLimit = CoordinatesRange.UPPER_LIMIT;
    private final CoordinatesRange lowerLimit = CoordinatesRange.LOWER_LIMIT;


    public Location(int coordinateX, int coordinateY) throws InvalidCoordinateException {
        if(isCoordinateInRange(coordinateX) && isCoordinateInRange(coordinateY)){
            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
        }
        else{
            throw new InvalidCoordinateException("Error: coordinates not in range!", coordinateX, coordinateY);
        }
    }

    public Location(SDMLocation JAXBLocation) throws InvalidCoordinateException {
        if(isCoordinateInRange(JAXBLocation.getX()) && isCoordinateInRange(JAXBLocation.getY())){
            this.coordinateX = JAXBLocation.getX();
            this.coordinateY = JAXBLocation.getY();
        }
        else{
            throw new InvalidCoordinateException("Error: coordinates not in range!", JAXBLocation.getX(), JAXBLocation.getY());
        }
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    @Override
    public String toString() {
        return "(" +
                + coordinateX +
                ", " + coordinateY +
                ')';
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public double getAirDistanceToOtherLocation(Location destLocation) {
        return Point2D.distance(this.getCoordinateX(),this.coordinateY, destLocation.getCoordinateX(), destLocation.getCoordinateY());
    }

    public enum CoordinatesRange
    {
        UPPER_LIMIT(50),
        LOWER_LIMIT(1);

        private final int value;

        @Override
        public String toString() {
            return "CoordinatesRange{" +
                    "value=" + value +
                    '}';
        }

        CoordinatesRange(int value) {
            this.value = value;
        }

        public int getLimit() {
            return value;
        }
    }

    private boolean isCoordinateInRange(int coordinate)
     {
         return coordinate >= lowerLimit.value && coordinate <= upperLimit.value;
     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return coordinateX == location.coordinateX &&
                coordinateY == location.coordinateY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinateX, coordinateY);
    }
}
