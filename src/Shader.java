import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    private String vertexCode, fragmentCode;
    private int programID;
    private int vert, frag;

    public Shader(String fragFile, String vertFile){
        programID = vert = frag = -1;
        fragmentCode = getCodeFrom(fragFile + ".frag");
        vertexCode = getCodeFrom(vertFile + ".vert");
    }

    public void load(){
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
        }

        if(glGetShaderi(vert, GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println("Error while Compiling Shader:");
            System.out.println(glGetShaderInfoLog(vert));
        }

        glAttachShader(programID, vert);
        glAttachShader(programID, frag);
        glLinkProgram(programID);

        if(glGetProgrami(programID, GL_LINK_STATUS) == GL11.GL_FALSE){
            System.out.println("Error while Linking Shader:");
            System.out.println(glGetProgramInfoLog(programID));
        }

        glDeleteShader(vert);
        glDeleteShader(frag);
    }
    
    public void dispose(){
        if(programID == -1) return;
        glUseProgram(0);
        glDetachShader(programID, vert);
        glDetachShader(programID, frag);
        glDeleteProgram(programID);
    }

    public void use(){
        glUseProgram(programID);
    }

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

}
