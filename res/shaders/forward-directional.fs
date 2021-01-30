#version 330 

in vec3 worldPos0;

in vec2 texCoord0;

in mat3 TBN;

out vec4 fragColor;

uniform vec3 eyePos;
uniform sampler2D diffuse;
uniform sampler2D nMap;

uniform float specularIntensity;
uniform float specularExponent;

struct BaseLight {

	vec3 color;
	
	float intensity;

};

struct DirectionalLight{

	BaseLight base;
	
	vec3 direction;

};

uniform DirectionalLight directionalLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal){

	float diffuseFactor = dot(normal, -direction);
	
	vec4 diffuseColor = vec4(0, 0, 0, 0);
	
	vec4 specularColor = vec4(0, 0, 0, 0);
	
	if(diffuseFactor > 0){
	
		diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
	
		vec3 directionToEye = normalize(eyePos - worldPos0);
		
		vec3 reflectDirection = normalize(reflect(direction, normal));
		
		float specularFactor = dot(directionToEye, reflectDirection);
		
		specularFactor = pow(specularFactor, specularExponent);
		
		if(specularFactor > 0){
		
			specularColor = vec4(base.color, 1.0) * specularIntensity * specularFactor;
		
		}
	
	}
	
	return diffuseColor + specularColor;	

}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal){

	return calcLight(directionalLight.base, -directionalLight.direction, normal);

}

void main() {

	vec3 norm = normalize(TBN * (255.0/128.0 * texture(nMap, texCoord0.xy).xyz - 1));
	
	fragColor = texture(diffuse, texCoord0.xy) * calcDirectionalLight(directionalLight, norm);

}