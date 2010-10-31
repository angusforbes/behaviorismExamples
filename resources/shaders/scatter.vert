varying vec2  MCposition;
void main() 
{

  MCposition      = gl_Vertex.xy;

	gl_TexCoord[0] = gl_MultiTexCoord0;
	gl_Position = ftransform();
} 
