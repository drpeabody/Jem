package util;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import shader.Shader;
import shapes.Shape;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class Jem {

    private long window;
    private Camera camera;
    private HashMap<Shader, ArrayList<Shape>> table;

    public Jem(Camera c){
        window = -1L;
        camera = c;
        table = new HashMap<>();
    }

    public void add(Shape s){
        ArrayList a = table.get(s.getShader());
        if(a == null){
            a = new ArrayList();
            a.add(s);
            table.put(s.getShader(), a);
        }
        else {
            a.add(s);
        }
    }

    public void remove(Shape s){
        ArrayList a = table.get(s.getShader());
        if(a != null)
            a.remove(s);
    }

    protected long getWindow(){
        return window;
    }

    public Camera getCamera() {
        return camera;
    }

    public abstract void load();
    public abstract void update();

    public void dispose(){
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        int pWidth[] = new int[1], pHeight[] = new int[1];
        glfwGetWindowSize(window, pWidth, pHeight);
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,
                (vidmode.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
        );

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();
        glfwShowWindow(window);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);

        load();
    }

    public void run() {
        init();
        loop();
        dispose();
    }

    public void draw(){
        update();
        table.forEach((shader, shapes) -> {
            shader.use();
            shader.updateCamera(camera.getMatrix());
            shapes.forEach(Shape::draw);
        });
    }

    private void loop(){
        while ( !glfwWindowShouldClose(getWindow()) ) {
            draw();
            glfwSwapBuffers(getWindow());
            glfwPollEvents();
        }
    }
}
