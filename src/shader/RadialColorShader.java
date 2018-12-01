package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class RadialColorShader extends Shader {

    private int inColLoc, outColLoc;

    public RadialColorShader() {
        super("radialColor", "base");
        inColLoc = outColLoc = -1;
    }

    @Override
    public void load() {
        super.load();
        inColLoc = GL20.glGetUniformLocation(getProgramID(), "col");
        outColLoc = GL20.glGetUniformLocation(getProgramID(), "ring");
    }

    public void setInnerColor(Vec4 color){
        use();
        GL20.glUniform4fv(inColLoc, color.getArray());
    }
    public void setOuterColor(Vec4 color){
        use();
        GL20.glUniform4fv(outColLoc, color.getArray());
    }
}
