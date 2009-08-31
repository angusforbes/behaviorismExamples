// [Fragment shader]
// Circles with GL_POINTS

varying vec2 screenPos;
varying float radius;

void main() {

  // Sphere shaded
  //if( distance(gl_FragCoord.xy, screenPos) > radius ) discard;
  if( distance(gl_FragCoord.xy, screenPos) > radius ) 
    {
  gl_FragColor = vec4(0., 1., 0., 1.);
      
      //discard;
  }
else
{
  //gl_FragData[0] = gl_Color;
  gl_FragColor = gl_Color;
  //gl_FragColor = vec4(1., 0., 0., 1.);
}
}
