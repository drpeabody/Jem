#version 330

//in vec4 fragPos;
out vec4 outputColor;

uniform vec4 startColor = vec4(1.0, 1.0, 1.0, 1.0);
uniform vec4 endColor = vec4(0.0, 0.0, 0.0, 1.0);
uniform float time = 0.0;

void main(){
    float f = cos(time) * cos(time);
    outputColor = startColor * f + endColor * (1 - f);
}
