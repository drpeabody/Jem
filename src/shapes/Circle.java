package shapes;

import org.lwjgl.opengl.GL11;
import shader.Shader;
import shader.SolidColor;
import util.Vec3;

public class Circle extends Shape {

    private int res;
    private float radius;

    public Circle(){
        this(3, 1f, new SolidColor());
    }
    public Circle(int res, float radius){
        this(res, radius, new SolidColor());
    }
    public Circle(int res, float radius, Shader shader){
        super(new Vec3(), new Vec3(), new Vec3(1f, 1f, 1f), shader);
        this.radius = radius;
        this.res = (res > 2)? res: 3;
    }

    public void setRadius(float r){
        scaling.set(r);
        getShader().updateScaling(getScalingMatrix(scaling));
    }

    @Override
    public float[] generateBuffer() {
        int num = getNumVertices();
        float f[] = new float[num];

        f[0] = 1f; f[1] = 0f; f[2] = 0f;
        int i;
        float c = (float)(2*Math.PI/res/3);
        for (i = 3; i < num - 3; i += 6){
            float angle = i * c;
            f[i] = f[i+3] = (float)(Math.cos(angle));
            f[i+1] = -(float)(Math.sin(angle));
            f[i+2] = f[i+5] = 0f;
            f[i+4] = -f[i+1];
        }
        if((res & 1) == 0) f[i] = -1f;
        return f;
    }

    @Override
    public int getNumVertices() {
        return 3*res;
    }

    @Override
    public int getDrawMode() {
        return GL11.GL_TRIANGLE_STRIP;
    }
}
