package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class SolidColorShader extends Shader {

    private int colLoc;
    private Vec4 color;

    public SolidColorShader() {
        super("solidColor", "base");
        colLoc = -1;
        color = new Vec4();
    }
    public SolidColorShader(Vec4 v){
        super("solidColor", "base");
        colLoc = -1;
        this.color = v;
    }

    @Override
    public void load() {
        super.load();
        colLoc = GL20.glGetUniformLocation(getProgramID(), "col");
        setColor(color);
    }

    public void setColor(Vec4 color){
        use();
        GL20.glUniform4fv(colLoc, color.getArray());
    }
}
