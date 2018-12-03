package shapes;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import shader.Shader;
import util.Vec3;

public abstract class Shape {

    protected Vec3 translation, rotation, scaling;
    private Shader shader;
    private int vbo;

    Shape(Vec3 translation, Vec3 rotation, Vec3 scaling, Shader s) {
        this.translation = translation;
        this.rotation = rotation;
        this.scaling = scaling;
        this.shader = s;
        vbo = -1;
    }

    public abstract float[] generateBuffer();
    public abstract int getNumVertices();
    public abstract int getDrawMode();
//    public abstract boolean contains(float x, float y);

    private void uploadBuffer(float f[]){
        if(vbo == -1)
            vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, f, GL15.GL_STATIC_DRAW);
    }

    public void load(){
        uploadBuffer(generateBuffer());
        shader.load();
        shader.use();
        shader.updateRotation(getRotationMatrix(rotation));
        shader.updateTranslation(getTranslationMatrix(translation));
        shader.updateScaling(getScalingMatrix(scaling));
        shader.unbind();
    }

    public void setTranslation(Vec3 v){
        translation.set(v);
        shader.updateTranslation(getTranslationMatrix(translation));
    }

    protected static float[] getTranslationMatrix(Vec3 v){
        return new float[] {
                1.0f, 0.0f, 0.0f, v.getX(),
                0.0f, 1.0f, 0.0f, v.getY(),
                0.0f, 0.0f, 1.0f, v.getZ(),
                0.0f, 0.0f, 0.0f, 1.0f
        };
    }
    protected static float[] getRotationMatrix(Vec3 v){
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
    protected static float[] getScalingMatrix(Vec3 v){
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

    public void draw(){
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0L);
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(getDrawMode(), 0, getNumVertices());
    }

    public void dispose(){
        shader.dispose();
        GL15.glDeleteBuffers(vbo);
        vbo = -1;
    }
}
