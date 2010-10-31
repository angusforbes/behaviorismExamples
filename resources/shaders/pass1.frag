
uniform sampler2D theTexture;
uniform int allBlue;
void main()
{
  vec4 base = texture2D(theTexture, gl_TexCoord[0].st);


  if (base.r > 0.3)
  {
    base.r = 1.0;
    base.g = 0.0;
    base.b = 0.0;
  }

  vec4 useColor;
  if (allBlue == 0)
  {
    useColor = vec4(base); //0.0, 1.0, 0.0, 0.5);
  }
  else
  {
    useColor = vec4(0,0,1,base.a);
  }

  //gl_FragColor = vec4(base.rgb, 1.0); //0.0, 1.0, 0.0, 0.5);
  gl_FragColor = vec4(useColor);
  //gl_FragData[0] = vec4(.5 - base.r, .5 - base.g, 1.0 - base.g, 1.0);
  //gl_FragData[0] = vec4(1.0, 1.0, base.b, 1.0);
 // gl_FragColor = vec4(gl_Color.r, gl_Color.g, gl_Color.b, 1.0);
  //gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
  //gl_FragColor = vec4(1.0, 0.0, 0.0, base.a * .99);
  //gl_FragData[0] = base;
  //pass2();

  gl_FragColor = vec4(1, 0, 0, 1);

}

