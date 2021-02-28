#version 330

in vec2 textureCoord;

out vec4 fragColor;

uniform vec3 baseColor;
uniform sampler2D texture_sampler;

void main() {
	
	fragColor = texture(texture_sampler, textureCoord) * vec4(baseColor, 1);

}