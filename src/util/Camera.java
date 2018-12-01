package util;

public class Camera {
    private float aspectRatio;
    private float x, y;

    public Camera(int screenWidth, int screenHeight){
        aspectRatio = (float)(screenHeight) / (float) (screenWidth);
        x = y = 0f;
    }

    public float[] getMatrix(){
        return new float[]{ //We send the transposed matrix
            aspectRatio, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            -x*aspectRatio, -y, 0f, 1f
        };
    }
    public void moveCam(float dx, float dy){
        x += dx;
        y += dy;
    }
}
