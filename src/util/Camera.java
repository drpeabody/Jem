package util;

public class Camera {
    private float screenWidth, screenHeight;
    private float x, y;

    public Camera(int screenWidth, int screenHeight){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    float[] getMatrix(){
        float f = screenHeight / screenWidth;
        return new float[]{ //We send the transposed matrix
            f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            -x * f, -y, 0f, 1f
        };
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void moveCam(float dx, float dy){
        x += dx;
        y += dy;
    }
}
