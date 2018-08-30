package cz.muni.fi.pb162.project.geometry;


/**
 * @author Stefan Ockay
 */
public class Snowman {
    private final RegularPolygon[] balls;
    private static final double DEF_FACTOR = 0.8;
    private static final byte N_BALLS = 3;

    public Snowman(RegularPolygon botPolygon, double factor) {
        balls = new RegularPolygon[N_BALLS];
        if (factor <= 0 || factor > 1) {
            factor = DEF_FACTOR;
        }
        balls[0] = botPolygon;
        double centerDist;
        Vertex2D newCenter;
        double newRadius;
        for (byte i = 1; i < N_BALLS; i++) {
            centerDist = balls[i - 1].getRadius() * (1 + factor);
            newRadius = balls[i - 1].getRadius() * factor;
            newCenter = balls[i - 1].getCenter().move(new Vertex2D(0, centerDist));
            balls[i] = new RegularOctagon(newCenter, newRadius);
        }
    }

    public RegularPolygon[] getBalls() {
        RegularPolygon[] newBalls = new RegularPolygon[N_BALLS];
        Vertex2D newCenter;
        for (int i = 0; i < balls.length; i++) {
            newCenter = new Vertex2D(balls[i].getCenter().getX(), balls[i].getCenter().getY());
            newBalls[i] = new RegularOctagon(newCenter, balls[i].getRadius());
        }
        return newBalls;
    }

}
