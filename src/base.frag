#version 330

out vec4 outputColor;

in vec4 col;

void main(){
    outputColor = col;
    //outputColor = vec4(1.0, 0.0, 0.0, 1.0);
}