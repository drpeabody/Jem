#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

out vec4 col;

void main(){
    gl_Position = position;
    col = color;
}