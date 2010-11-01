//uniform sampler2D theTexture;
varying vec2 MCposition;

void main()
{
  float xpos = 3.0 / float(MCposition.x);
  //gl_FragColor = vec4(1., 0., 0., xpos);
  gl_FragColor = vec4(1., 0., 0., xpos);

  //vec4 theColor = texture2D( theTexture, gl_TexCoord[0].st);
  //gl_FragColor = vec4(theColor);

    //gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
}

