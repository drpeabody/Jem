package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class PulsatingColor extends Shader {

    public PulsatingColor() {
        super("pulsatingColor", "base");
    }

    @Override
    public void loadUniforms() {
        registerUniform("startColor");
        registerUniform("endColor");
    }

    public void setStartColor(Vec4 color){
        use();
        GL20.glUniform4fv(getUniformLoc("startColor"), color.getArray());
    }
    public void setEndColor(Vec4 color){
        use();
        GL20.glUniform4fv(getUniformLoc("endColor"), color.getArray());
    }
}
