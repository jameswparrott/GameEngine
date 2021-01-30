#version 330

layout (location = 0) in vec3 pos;

layout (location = 1) in vec2 texCoord;

layout (location = 2) in vec3 normal;

layout (location = 3) in vec3 tangent;

out vec3 worldPos0;

out vec2 texCoord0;

out mat3 TBN;

uniform mat4 model;

uniform mat4 MVP;

void main() {

	gl_Position = MVP * vec4(pos, 1.0);
	
	worldPos0 = (model * vec4(pos, 1.0)).xyz;
	
	texCoord0 = texCoord;
	
	vec3 n = normalize((model * vec4(normal, 0.0)).xyz);
	
	vec3 t = normalize((model * vec4(tangent, 0.0)).xyz);
	
	t = normalize(t - dot(t, n) * n);
	
	vec3 b = cross(t, n);
	
	TBN = mat3(t, b, n);

}