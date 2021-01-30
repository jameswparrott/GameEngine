#version 330

in vec2 textureCoord;

out vec4 fragColor;

uniform vec3 baseColor;
uniform sampler2D texture_sampler;

void main() {

	vec4 color = vec4(baseColor, 1);

	vec4 textureColor = texture(texture_sampler, textureCoord);

	if(textureColor != vec4(0, 0, 0, 0) )
	
		color = textureColor * color;
	
	fragColor = color;

}