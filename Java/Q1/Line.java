package Q1;

public class Line extends GeometricFigures{
    Point startPoint;
    Point endPoint;

    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public void move(double x, double y) {
        this.startPoint.move(x, y);
        this.endPoint.move(x, y);
    }

    @Override
    public void rotate(double angle) {
        angle = Math.toRadians(angle);
        this.startPoint.rotate(angle);
        this.endPoint.rotate(angle);
    }

    @Override
    public String toString() {
        return "Line: start = (x at " + this.startPoint.x + ", y at " + this.startPoint.y + "), end = {x at " + this.endPoint.x + ", y at " + this.endPoint.y + ")";
    }
}
