package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class SolidColor extends Shader {

    public SolidColor() {
        super("solidColor", "base");
    }

    @Override
    public void loadUniforms() {
        registerUniform("col");
    }

    public void setColor(Vec4 color){
        use();
        GL20.glUniform4fv(getUniformLoc("col"), color.getArray());
    }
}
