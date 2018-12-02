package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class RadialColor extends Shader {

    public RadialColor() {
        super("radialColor", "base");
    }

    @Override
    public void loadUniforms() {
        registerUniform("col");
        registerUniform("ring");
    }

    public void setInnerColor(Vec4 color){
        use();
        GL20.glUniform4fv(getUniformLoc("col"), color.getArray());
    }
    public void setOuterColor(Vec4 color){
        use();
        GL20.glUniform4fv(getUniformLoc("ring"), color.getArray());
    }
}
