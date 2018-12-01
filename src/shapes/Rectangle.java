package shapes;

import org.lwjgl.opengl.GL11;
import shader.Shader;
import shader.SolidColorShader;
import util.Vec3;

public class Rectangle extends Shape{

    public Rectangle(){
        this(new Vec3(), new Vec3(), 1f, 1f, new SolidColorShader());
    }
    public Rectangle(Shader s) { this(new Vec3(), new Vec3(), 1f, 1f, s); }
    public Rectangle(Vec3 translation, Vec3 rotation, float width, float height, Shader shader) {
        super(translation, rotation, new Vec3(width, height, 1f), shader);
    }

    public float[] generateBuffer(){
        return new float[] {
            //Vertex Positions
            -1f, -1f, 0f,
            -1f, +1f, 0f,
            +1f, +1f, 0f,
            +1f, +1f, 0f,
            +1f, -1f, 0f,
            -1f, -1f, 0f,
        };
    }

    @Override
    public int getNumVertices() {
        return 6;
    }

    @Override
    public int getDrawMode() {
        return GL11.GL_TRIANGLES;
    }
}
