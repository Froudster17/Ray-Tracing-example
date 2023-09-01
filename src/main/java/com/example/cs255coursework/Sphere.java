package com.example.cs255coursework;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Sphere {

    private int xPosition;
    private int yPosition;
    private int zPosition;
    private int R;
    private int G;
    private int B;
    private int radius;
    private static int sphereID;

    public Sphere (int xPosition, int yPosition, int zPosition, int R, int G, int B, int radius) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
        this.R = R;
        this.G = G;
        this.B = B;
        this.radius = radius;
        sphereID += 1;
    }

    public void Render(WritableImage image, Vector o, Vector d, int i, int j, PixelWriter imageWriter, Vector light) {
        Vector cs = new Vector(xPosition, yPosition, zPosition);
        double radius = this.radius;
        double t;
        Vector p = new Vector(0,0,0);

        //double c = green_col / 255.0;
        double col = 0.0;

        //ray origin = (i, j, 4)

        Vector v = o.sub(cs);
        double a = d.dot(d);
        double b = v.dot(d) * 2;
        double c = v.dot(v) - radius * radius;
        double disc = b * b - 4 * a * c;
        if (disc < 0) {
            //col = 0.0;
            return;
        } else {
            col = 1.0;
        }
        t = (-b-Math.sqrt(disc))/2*a;
        p = o.add(d.mul(t));
        Vector Lv = light.sub(p);
        Lv.normalise();
        Vector n = p.sub(cs);
        n.normalise();
        double dp = Lv.dot(n);
        if (dp<0) {
            dp = 0;
        }
        if (dp > 1) {
            dp = 1;
        }

        double redValue = ((RFloat() * dp) * 0.7) + (RFloat() * 0.3);
        double greenValue = ((GFloat() * dp) * 0.7) + (GFloat() * 0.3);
        double blueValue = ((BFloat() * dp) * 0.7) + (BFloat() * 0.3);
        Color sphereColor = new Color(redValue, greenValue, blueValue, 1.0);

            imageWriter.setColor(i, j, sphereColor);
        }

    public double getDisk(Vector d, Vector o) {

        Vector cs = new Vector(xPosition, yPosition, zPosition);
        double a = d.dot(d);
        Vector v = o.sub(cs);
        double b = v.dot(d) * 2;
        double c = v.dot(v) - radius * radius;

        return b * b - 4 * a * c;
    }

    public double getT(int i, int j, Vector d, Vector o) {

        Vector cs = new Vector(xPosition, yPosition, zPosition);
        Vector v = o.sub(cs);
        double b = v.dot(d) * 2;
        double a = d.dot(d);
        double t = (-b-Math.sqrt(getDisk(d, o)))/2*a;

        if (t < 0) {
            t = (-b+Math.sqrt(getDisk(d, o)))/2*a;
        } if (t >= 0) {
            return t;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }


    //Getters & Setters

    public float RFloat() {
        return (float)R/255;
    }
    public float GFloat() {
        return (float)G/255;
    }
    public float BFloat() {
        return (float)B/255;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getzPosition() {
        return zPosition;
    }

    public void setzPosition(int zPosition) {
        this.zPosition = zPosition;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public static int getSphereID() {
        return sphereID;
    }

    public static void setSphereID(int sphereID) {
        Sphere.sphereID = sphereID;
    }
}