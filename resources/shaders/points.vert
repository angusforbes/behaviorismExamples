// [Vertex shader]
// Circles with GL_POINTS

//uniform vec2 windowSize;
varying vec2 screenPos;
varying float radius;

void main()
{
  vec2 windowSize = vec2(600., 400.);
 //   gl_Position = ftransform();

gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
  gl_PointSize = 20.0;

  // Convert position to window coordinates
  vec2 halfsize = vec2(windowSize.x, windowSize.y) * 0.5;
  screenPos = halfsize + ((gl_Position.xy / gl_Position.w) * halfsize);

  // Convert radius to window coordinates
  radius = gl_PointSize * 0.5;
}
