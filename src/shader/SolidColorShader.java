package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class SolidColorShader extends Shader {

    int colLoc = -1;

    public SolidColorShader() {
        super("solidColor", "base");
    }

    @Override
    public void load() {
        super.load();
        colLoc = GL20.glGetUniformLocation(getProgramID(), "col");
    }

    public void setColor(Vec4 color){
        use();
        GL20.glUniform4fv(colLoc, new float[] {
            color.getR(),
            color.getG(),
            color.getB(),
            color.getA(),
        });
    }
}
