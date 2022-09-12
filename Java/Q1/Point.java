package Q1;

public class Point extends GeometricFigures{
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void rotate(double angle) {
        angle = Math.toRadians(angle);
        double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        double newY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        this.move(newX, newY);
    }

    @Override
    public String toString() {
        return "Point: x =" + this.x + ", y = " + this.y;
    }
}
