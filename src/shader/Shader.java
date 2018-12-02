package shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    private String vertexCode, fragmentCode;
    private int programID;
    private int vert, frag;
    private int transMatLoc, rotMatLoc, scaleMatLoc, camMatLoc;
    private int timeLoc;

    private Hashtable<String, Integer> uniforms;

    Shader(String fragFile, String vertFile){
        programID = vert = frag = -1;
        transMatLoc = rotMatLoc = scaleMatLoc = camMatLoc = -1;
        timeLoc = -1;
        fragmentCode = getCodeFrom(fragFile + ".frag");
        vertexCode = getCodeFrom(vertFile + ".vert");
        uniforms = null;
    }

    public final void load(){
        programID = glCreateProgram();
        frag = glCreateShader(GL_FRAGMENT_SHADER);
        vert = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(frag, fragmentCode);
        glShaderSource(vert, vertexCode);
        glCompileShader(frag);
        glCompileShader(vert);

        if(glGetShaderi(frag, GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println("Error while Compiling Shader:");
            System.out.println(glGetShaderInfoLog(frag));
            System.exit(1);
        }

        if(glGetShaderi(vert, GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println("Error while Compiling Shader:");
            System.out.println(glGetShaderInfoLog(vert));
            System.exit(1);
        }

        glAttachShader(programID, vert);
        glAttachShader(programID, frag);
        glLinkProgram(programID);

        if(glGetProgrami(programID, GL_LINK_STATUS) == GL11.GL_FALSE){
            System.out.println("Error while Linking Shader:");
            System.out.println(glGetProgramInfoLog(programID));
            System.exit(1);
        }

        transMatLoc = glGetUniformLocation(programID, "translation");
        rotMatLoc = glGetUniformLocation(programID, "rotation");
        scaleMatLoc = glGetUniformLocation(programID, "scaling");
        camMatLoc = glGetUniformLocation(programID, "camera");
        timeLoc = GL20.glGetUniformLocation(getProgramID(), "time");

        glDeleteShader(vert);
        glDeleteShader(frag);

        uniforms = new Hashtable<>();
        loadUniforms();
    }
    
    public void dispose(){
        if(programID == -1) return;
        glUseProgram(0);
        glDetachShader(programID, vert);
        glDetachShader(programID, frag);
        glDeleteProgram(programID);
        programID = vert = frag = -1;
        transMatLoc = rotMatLoc = scaleMatLoc = -1;
    }

    public void use(){
        glUseProgram(programID);
    }
    public void unbind(){ glUseProgram(0); }

    private String getCodeFrom(String fileName){
        StringBuilder buff = new StringBuilder();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(Shader.class.getResourceAsStream(fileName)
        ));

        String line;

        try {
            while((line = reader.readLine()) != null){
                buff.append(line).append('\n');
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        return buff.toString();
    }

    private int getProgramID() {
        return programID;
    }

    void registerUniform(String name){
        uniforms.put(name, GL20.glGetUniformLocation(getProgramID(), name));
    }
    int getUniformLoc(String name){
        Integer i = uniforms.get(name);
        return (i == null)? -1: i;
    }

    public abstract void loadUniforms();

    public void updateTranslation(float f[]){
        use();
        glUniformMatrix4fv(transMatLoc, false, f);
    }
    public void updateRotation(float f[]){
        use();
        glUniformMatrix4fv(rotMatLoc, false, f);
    }
    public void updateScaling(float f[]){
        use();
        glUniformMatrix4fv(scaleMatLoc, false, f);
    }
    public void updateCamera(float f[]){
        use();
        glUniformMatrix4fv(camMatLoc, false, f);
    }
    public void setTime(float f){
        use();
        GL20.glUniform1f(timeLoc, f);
    }

}
