//
// Fragment shader for procedural bricks
//
// Authors: Dave Baldwin, Steve Koren, Randi Rost
//          based on a shader by Darwyn Peachey
//
// Copyright (c) 2002-2006 3Dlabs Inc. Ltd. 
//
// See 3Dlabs-License.txt for license information
//

//uniform vec3  BrickColor, MortarColor;
//uniform vec2  BrickSize;
//uniform vec2  BrickPct;

varying vec2  MCposition;
varying float LightIntensity;

void main()
{
vec2 BrickSize = vec2(1, 1);
vec2 BrickPct = vec2(1.0, 0.5);
vec3 BrickColor = vec3(1., 0., 0.);
vec3 MortarColor = vec3(0., 1., 0.);
    vec3  color;
    vec2  position, useBrick;
    
    position = MCposition / BrickSize;

    if (fract(position.y * 0.5) > 0.5)
        position.x += 0.5;

    position = fract(position);

    useBrick = smoothstep(position, BrickPct, vec2(0.5, 0.5));

    color  = mix(MortarColor, BrickColor, useBrick.x * useBrick.y);
    //color *= LightIntensity;
    gl_FragColor = vec4(color, 1.0);
    //gl_FragColor = vec4(1.0, 0.,0., (0.5 + MCposition.y) * 1.0);
}