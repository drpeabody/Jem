package util;

public class Vec4 {
    private final float r,g,b,a;

    public Vec4(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Vec4(){
        r = g = b = a = 1f;
    }

    public float getR() {
        return r;
    }
    public float getG() {
        return g;
    }
    public float getB() {
        return b;
    }
    public float getA() {
        return a;
    }

    public float[] getArray(){
        return new float[]{r,g,b,a};
    }
}
