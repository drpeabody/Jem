
import shader.PulsatingColorShader;
import shader.RadialColorShader;
import shader.SolidColorShader;
import shapes.Circle;
import shapes.Rectangle;
import util.Camera;
import util.Jem;
import util.Vec3;
import util.Vec4;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main extends Jem {

    private Circle circle;
    private Rectangle rect;

    private boolean bMoveCamLeft = false, bMoveCamRight = false,
            bMoveCamUp = false, bMoveCamDown = false;

    private Main(){
        super(new Camera(800, 600));
        circle = new Circle(500, 1f, new RadialColorShader());
        rect = new Rectangle(new Vec3(), new Vec3(), 0.2f, 0.2f,
                new PulsatingColorShader());
    }

    public void dispose(){
        circle.dispose();
        rect.dispose();
        super.dispose();
    }

    public void load() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glfwSetKeyCallback(getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_A && action == GLFW_PRESS)
                ((RadialColorShader)circle.getShader())
                        .setInnerColor(new Vec4(1f, 0f, 0f, 1f));
            else if (key == GLFW_KEY_S && action == GLFW_PRESS)
                ((RadialColorShader)circle.getShader())
                        .setOuterColor(new Vec4(0f, 1f, 0f, 1f));
            else if (key == GLFW_KEY_LEFT && action == GLFW_PRESS)
                bMoveCamLeft = true;
            else if(key == GLFW_KEY_LEFT && action == GLFW_RELEASE)
                bMoveCamLeft = false;
            else if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS)
                bMoveCamRight = true;
            else if(key == GLFW_KEY_RIGHT && action == GLFW_RELEASE)
                bMoveCamRight = false;
            else if (key == GLFW_KEY_UP && action == GLFW_PRESS)
                bMoveCamUp  = true;
            else if(key == GLFW_KEY_UP && action == GLFW_RELEASE)
                bMoveCamUp = false;
            else if (key == GLFW_KEY_DOWN && action == GLFW_PRESS)
                bMoveCamDown = true;
            else if(key == GLFW_KEY_DOWN && action == GLFW_RELEASE)
                bMoveCamDown = false;
        });
        rect.load();
        circle.load();
        add(circle);
        add(rect);
    }

    float time = 0f;

    @Override
    public void update() {
        time += 0.001f;
        float dx = -0.0005f * ((bMoveCamLeft?-1f:1f) +(bMoveCamRight?1f:-1f));
        float dy = -0.0005f * ((bMoveCamDown?-1f:1f) +(bMoveCamUp?1f:-1f));
        getCamera().moveCam(dx, dy);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        ((PulsatingColorShader)rect.getShader()).setTime(time);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

