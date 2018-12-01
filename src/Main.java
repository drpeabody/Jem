
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

    boolean bMoveCamLeft = false, bMoveCamRight = false;

    private Main(){
        super(new Camera(800, 600));
        circle = new Circle(500, 1f, new RadialColorShader());
        rect = new Rectangle(new Vec3(), new Vec3(), 0.2f, 0.2f,
                new SolidColorShader(new Vec4(1f, 1f, 0f, 1f)));
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
        });
        rect.load();
        circle.load();
        add(circle);
        add(rect);
    }

    @Override
    public void update() {
        float dx = 0.0005f * ((bMoveCamLeft?-1f:1f) +(bMoveCamRight?1f:-1f));
        getCamera().moveCam(dx, 0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

