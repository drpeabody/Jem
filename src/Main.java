
import shader.PulsatingColor;
import shader.RadialColor;
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
        circle = new Circle(500, 1f, new RadialColor());
        rect = new Rectangle(new Vec3(), new Vec3(), 0.2f, 0.2f,
                new PulsatingColor());
    }

    public void dispose(){
        circle.dispose();
        rect.dispose();
        super.dispose();
    }

    public void load() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        rect.load();
        circle.load();
        ((RadialColor)circle.getShader()).setInnerColor(new Vec4(0f,0f,0f,0f));
        ((RadialColor)circle.getShader()).setOuterColor(new Vec4(0.6f,0.6f,0.7f,0f));
        add(circle);
        add(rect);
    }

    @Override
    public void keyPressed(int key) {
        switch(key) {
            case GLFW_KEY_LEFT:
                bMoveCamLeft = true; break;
            case GLFW_KEY_RIGHT:
                bMoveCamRight = true; break;
            case GLFW_KEY_UP:
                bMoveCamUp = true; break;
            case GLFW_KEY_DOWN:
                bMoveCamDown = true; break;
        }
    }
    @Override
    public void keyReleased(int key) {
        switch(key){
            case GLFW_KEY_LEFT:
                bMoveCamLeft = false; break;
            case GLFW_KEY_RIGHT:
                bMoveCamRight = false; break;
            case GLFW_KEY_UP:
                bMoveCamUp = false; break;
            case GLFW_KEY_DOWN:
                bMoveCamDown = false; break;
        }
    }
    @Override
    public void mousePressed(int key) {
        if(key == GLFW_MOUSE_BUTTON_1){
            float x = getMouseX(), y = getMouseY();
            animate((t) -> {
                ((RadialColor)circle.getShader())
                        .setOuterColor(new Vec4(0.6f,0.6f,1f,1-t));
                circle.setRadius(t);
                circle.setTranslation(new Vec3(x, y, 0f));
            }, .4f);
        }
    }

    @Override
    public void update() {
        float dx = -0.0005f * ((bMoveCamLeft?-1f:1f) + (bMoveCamRight?1f:-1f));
        float dy = -0.0005f * ((bMoveCamDown?-1f:1f) + (bMoveCamUp?1f:-1f));
        getCamera().moveCam(dx, dy);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

