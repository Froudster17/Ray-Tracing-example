package com.example.cs255coursework;

public class Camera {

    private int x;
    private int y;
    private int z;

    Vector viewPlaneNormal;
    Vector viewRightVector;
    Vector viewUpVector;

    public Camera(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.viewUpVector = new Vector(0,1,0);
    }

    public void update() {

        viewPlaneNormal = new Vector(0,0,0).sub(new Vector(x,y,z));
        viewPlaneNormal.normalise();

        viewRightVector = viewPlaneNormal.cross(viewUpVector);
        viewRightVector.normalise();

        viewUpVector = viewRightVector.cross(viewPlaneNormal);
        viewUpVector.normalise();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
