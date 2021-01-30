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

struct Attenuation{

	float constant;
	
	float linear;
	
	float exponent;

};

struct PointLight{

	BaseLight base;
	
	Attenuation att;
	
	vec3 pos;	
	
	float range;

};

struct SpotLight{

	PointLight pointLight;
	
	vec3 direction;
	
	float focus;

};

uniform SpotLight spotLight;

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

vec4 calcPointLight(PointLight pointLight, vec3 normal){

	vec3 lightDirection = worldPos0 - pointLight.pos;
	
	float distanceToLight = length(lightDirection);
	
	if(distanceToLight > pointLight.range)
		
		return vec4(0, 0, 0, 0);
	
	lightDirection = normalize(lightDirection);
	
	vec4 color = calcLight(pointLight.base, lightDirection, normal);
	
	float attenuation = pointLight.att.constant + pointLight.att.linear * distanceToLight + pointLight.att.exponent * distanceToLight * distanceToLight + 0.0001f;
	
	return color / attenuation;

}

vec4 calcSpotLight(SpotLight spotLight, vec3 normal){

	vec3 lightDirection = normalize(worldPos0 - spotLight.pointLight.pos);
	
	float spotFactor = dot(lightDirection, normalize(spotLight.direction));
	
	vec4 color = vec4(0, 0, 0, 0);
	
	if (spotFactor > spotLight.focus)
	
		color = calcPointLight(spotLight.pointLight, normal) * (1.0 - (1.0 - spotFactor)/(1.0 - spotLight.focus));
		
	return color;

}

void main() {

	vec3 n = normalize(TBN * (255.0/128.0 * texture(nMap, texCoord0.xy).xyz - 1));
	
	fragColor = texture(diffuse, texCoord0.xy) * calcSpotLight(spotLight, n);

}