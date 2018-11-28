#version 330

out vec4 outputColor;

uniform vec4 col;
uniform vec4 ring;
uniform float width;
uniform float height;

void main(){
    float v = length(vec2(gl_FragCoord.x/width - 0.5, gl_FragCoord.y/height - 0.5));
    outputColor = ring * v + col * (1 - v);
}