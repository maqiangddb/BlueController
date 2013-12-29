package com.some.blue;

import java.util.Date;

/**
 * Created by mqddb on 13-12-29.
 */
public class DistanceKeeper {
    private long lastTime = -1;
    private double sx;
    private double sy;
    private double sz;
    private double vx;
    private double vz;
    private double vy;


    public double[] accelerateToDistance(double ax, double ay, double az) {
        Date now = new Date();
        long time = now.getTime();
        System.out.println(time);
        long delta = time - lastTime;
        if (lastTime == -1) {
            vx = vy = vz = 0;
            sx = sy = sz = 0;
        }

        sx = (vx + ax * delta / 2) * delta;
        sy = (vy + ay * delta / 2) * delta;
        sz = (vz + az * delta / 2) * delta;

        vx += ax * delta;
        vy += ay * delta;
        vz += az * delta;

        lastTime = time;

        double ret[] = {sx, sy, sz};
        System.out.printf("%f, %f, %f\n", ret[0], ret[1], ret[2]);
        return ret;
    }

    public double[] accelerateToDistance(float a[]) {
        return this.accelerateToDistance(a[0], a[1], a[2]);
    }
    
    public double[] accelerateToDistance(double a[]) {
        return this.accelerateToDistance(a[0], a[1], a[2]);
    }
}
