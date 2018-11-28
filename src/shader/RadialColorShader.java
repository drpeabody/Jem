package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class RadialColorShader extends Shader {

    int inColLoc = -1, outColLoc, widthLoc = -1, heightLoc = -1;
    int width = -1, height = -1;

    public RadialColorShader(int windowWidth, int windowHeight) {
        super("radialColor", "base");
        width = windowWidth;
        height = windowHeight;
    }

    @Override
    public void load() {
        super.load();
        inColLoc = GL20.glGetUniformLocation(getProgramID(), "col");
        outColLoc = GL20.glGetUniformLocation(getProgramID(), "ring");
        widthLoc = GL20.glGetUniformLocation(getProgramID(), "width");
        heightLoc = GL20.glGetUniformLocation(getProgramID(), "height");
        setHeight(height);
        setWidth(width);
    }

    public void setInnerColor(Vec4 color){
        use();
        GL20.glUniform4fv(inColLoc, new float[] {
                color.getR(),
                color.getG(),
                color.getB(),
                color.getA(),
        });
    }
    public void setOuterColor(Vec4 color){
        use();
        GL20.glUniform4fv(outColLoc, new float[] {
                color.getR(),
                color.getG(),
                color.getB(),
                color.getA(),
        });
    }
    public void setWidth(int w){
        use();
        GL20.glUniform1f(widthLoc, w);
    }
    public void setHeight(int w){
        use();
        GL20.glUniform1f(heightLoc, w);
    }
}
