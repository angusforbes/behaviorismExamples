uniform sampler2D tex1;
uniform sampler2D tex2;
uniform float opacity;
uniform int effect;

void main()
{
  vec4 base = texture2D( tex1, gl_TexCoord[0].st);
  vec4 blend = texture2D( tex2, gl_TexCoord[1].st);
  vec4 result;
  vec4 white = vec4(1., 1., 1., 1.);
  
  if (effect == 0) //normal blend
  {
      result = blend;
  }
  else if (effect == 1) //mulitply
  {
      result = (base * blend);
  }
  else if (effect == 2) //color burn
  {
      result = white - ((white - base) / blend);
  }
  else if (effect == 3) //color dodge
  {
      result = base / (white - blend);
  }

  gl_FragColor = mix(base, result, opacity);
}

