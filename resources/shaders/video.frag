uniform sampler2D theTexture;

void main()
{
  vec4 theColor = texture2D( theTexture, gl_TexCoord[0].st);
  gl_FragColor = vec4(theColor);

    //gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
}

