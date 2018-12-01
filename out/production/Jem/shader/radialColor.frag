#version 330

in vec4 fragPos;
out vec4 outputColor;

uniform vec4 col = vec4(0.0, 0.0, 1.0, 1.0);
uniform vec4 ring = vec4(1.0, 0.0, 0.0, 1.0);

void main(){
    float v = length(vec2(fragPos.xy));
    outputColor = ring * v + col * (1 - v);
}
