package shader;

import org.lwjgl.opengl.GL20;
import util.Vec4;

public class PulsatingColorShader extends Shader {

    private int startColLoc, endColLoc, timeLoc;

    public PulsatingColorShader() {
        super("pulsatingColor", "base");
        startColLoc = endColLoc = timeLoc = -1;
    }

    @Override
    public void load() {
        super.load();
        startColLoc = GL20.glGetUniformLocation(getProgramID(), "startColor");
        endColLoc = GL20.glGetUniformLocation(getProgramID(), "endColor");
        timeLoc = GL20.glGetUniformLocation(getProgramID(), "time");
    }

    public void setStartColor(Vec4 color){
        use();
        GL20.glUniform4fv(startColLoc, color.getArray());
    }
    public void setEndColor(Vec4 color){
        use();
        GL20.glUniform4fv(endColLoc, color.getArray());
    }
    public void setTime(float f){
        use();
        GL20.glUniform1f(timeLoc, f);
    }
}
