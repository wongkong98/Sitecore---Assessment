package Q1;

public class Circle extends GeometricFigures{
    Point position;
    int radius;

    public Circle(Point position, int radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    public void move(double x, double y) {
        this.position = new Point(this.position.x + x, this.position.y + y);
    }

    @Override
    public void rotate(double angle) {
        this.position.rotate(angle);
    }

    @Override
    public String toString() {
        return "Circle: position = (x at " + this.position.x + ", y at " + this.position.y + ") (radius=" + radius + '}';
    }
}
