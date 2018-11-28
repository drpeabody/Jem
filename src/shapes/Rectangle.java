package shapes;

import shader.Shader;
import shader.SolidColorShader;
import util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Rectangle {

    private Vec3 translation, rotation, scaling;
    private Shader shader;

    private int vbo;

    public Rectangle(){
        this(new Vec3(), new Vec3(), 1f, 1f, new SolidColorShader());
    }
    public Rectangle(Shader s) { this(new Vec3(), new Vec3(), 1f, 1f, s); }
    public Rectangle(Vec3 translation, Vec3 rotation, float width, float height, Shader shader) {
        this.translation = translation;
        this.rotation = rotation;
        this.scaling = new Vec3(width, height, 1f);

        this.shader = shader;
        vbo = -1;
    }

    private void uploadBuffer(float f[]){
        if(vbo == -1)
            vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, f, GL15.GL_STATIC_DRAW);

        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0L);
        GL20.glDisableVertexAttribArray(0);
    }
    private float[] generateBuffer(){
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

    public float[] getTranslationMatrix(Vec3 v){
        return new float[] {
                1.0f, 0.0f, 0.0f, v.getX(),
                0.0f, 1.0f, 0.0f, v.getY(),
                0.0f, 0.0f, 1.0f, v.getZ(),
                0.0f, 0.0f, 0.0f, 1.0f
        };
    }
    public float[] getRotationMatrix(Vec3 v){
        float cRx = (float)Math.cos(v.getX());
        float cRy = (float)Math.cos(v.getY());
        float cRz = (float)Math.cos(v.getZ());
        float sRx = (float)Math.sin(v.getX());
        float sRy = (float)Math.sin(v.getY());
        float sRz = (float)Math.sin(v.getZ());
        return new float[]{
            (cRy*cRz),               (-cRy*sRz),              (-sRy),     0f,
            (cRx*sRz - sRx*sRy*cRz), (cRx*cRz + sRx*sRy*sRz), (-sRx*cRy), 0f,
            (sRx*sRz + cRx*sRy*cRz), (sRx*cRz - cRx*sRy*sRz), (cRx*cRy),  0f,
            0.0f, 0.0f, 0.0f, 1.0f
        };
    }
    public float[] getScalingMatrix(Vec3 v){
        return new float[] {
                v.getX(), 0.0f, 0.0f, 0.0f,
                0.0f, v.getY(), 0.0f, 0.0f,
                0.0f, 0.0f, v.getZ(), 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        };
    }

    public Shader getShader() {
        return shader;
    }

    public void load(){
        shader.updateRotation(getRotationMatrix(rotation));
        shader.updateTranslation(getTranslationMatrix(translation));
        shader.updateScaling(getScalingMatrix(scaling));
        uploadBuffer(generateBuffer());

        shader.load();
        shader.use();
    }
    public void draw(){
        shader.use();
        GL20.glEnableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }
    public void dispose(){
        shader.dispose();
        GL15.glDeleteBuffers(vbo);
        vbo = -1;
    }
}
