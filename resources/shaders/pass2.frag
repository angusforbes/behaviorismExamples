uniform sampler2D theTexture;

void main()
{
  vec4 base = texture2D( theTexture, gl_TexCoord[0].st);

  //vec4 theColor = texture2D( theTexture, gl_TexCoord[0].st);

  //gl_FragData[0] = (theColor);
  //gl_FragColor = vec4(0.0, 1.0, 0.0, 0.5);
  gl_FragColor = vec4(base.r, base.g, base.b, base.a * 0.1); //0.0, 1.0, 0.0, 0.5);
  //gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);
  //pass2();
  gl_FragColor = vec4(0, 1, 0, 1);

}

