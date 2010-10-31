varying vec3 pos;
uniform float Scale;

void main()
{
  vec3 SkyColor =  vec3(0.0, 0.0, 0.7);
  vec3 CloudColor = vec3(0.7, 0.7, 0.7);

    vec4 noisevec  = noise4((pos.xyz + Scale) * 0.6 );
    float intensity = (noisevec[0] + noisevec[1] +
                       noisevec[2] + noisevec[3] + 0.03125) * 1.5;

    vec3 color   = mix(SkyColor, CloudColor, intensity) * 0.5;

    gl_FragColor = vec4(color, 1.0);
}