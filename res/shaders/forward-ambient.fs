#version 330

in vec2 textureCoord;

out vec4 fragColor;

uniform vec3 ambientIntensity;
uniform sampler2D texture_sampler;

void main() {
	
	//Alpha testing (MAY CAUSE SLOWDOWN IF LOTS OF SPRITES ARE USED)
	
	//Find a better way to do this
	
	if(texture(texture_sampler, textureCoord.xy).a == 0)
	
		discard;
	
	fragColor = texture(texture_sampler, textureCoord) * vec4(ambientIntensity, 1);

}