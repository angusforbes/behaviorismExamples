varying vec2 MCposition;

void main()
{
  MCposition = gl_Vertex.xy;
    gl_Position = ftransform();
}
