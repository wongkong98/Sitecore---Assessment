package Q1;

public class Aggregation {
    GeometricFigures[] items;

    public Aggregation() {
        // Randomly generate a number from 1 to 10
        int size = (int) (Math.random() * (10 - 1 + 1) + 1);

        // Use the number to set the size of the array
        this.items = new GeometricFigures[size];

        for(int i = 0; i < size; i++) {
            // Randomly generate a number to create a shape
            int shapes = (int) (Math.random() * (2 - 0 + 1) + 0);

            switch(shapes) {
                case 0 -> {
                    this.items[i] = new Point((int) (Math.random() * (5 - (-5) + 1) + (-5)), (int) (Math.random() * (5 - (-5) + 1) + (-5)));
                }
                case 1 -> {
                    this.items[i] = new Line(new Point((int) (Math.random() * (5 - (-5) + 1) + (-5)), (int) (Math.random() * (5 - (-5) + 1) + (-5))), new Point((int) (Math.random() * (5 - (-5) + 1) + (-5)), (int) (Math.random() * (5 - (-5) + 1) + (-5))));
                }
                case 2 -> {
                    this.items[i] = new Circle(new Point((int) (Math.random() * (5 - (-5) + 1) + (-5)), (int) (Math.random() * (5 - (-5) + 1) + (-5))), (int) (Math.random() * (5 - 1 + 1) + 1));
                }
            }
        }
    }

    public void move(double x, double y) {
        for(int i = 0; i < this.items.length; i++) {
            items[i].move(x, y);
        }
    }

    public void rotate(double angle) {
        for(int i = 0; i < this.items.length; i++) {
            items[i].rotate(angle);
        }
    }
}
