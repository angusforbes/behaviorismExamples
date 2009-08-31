uniform float exposure;
uniform float decay;
uniform float density;
uniform float weight;
uniform vec2 lightPositionOnScreen;
uniform vec2 lightPositionOnScreen2;
uniform sampler2D myTexture;
uniform int NUM_SAMPLES;
//const int NUM_SAMPLES = 30 ; //100
varying vec2  MCposition;

void main()
{
	vec2 deltaTextCoord = vec2( gl_TexCoord[0].st - lightPositionOnScreen.xy );
  vec2 deltaTextCoord2 = vec2( gl_TexCoord[0].st - lightPositionOnScreen2.xy );

  vec2 textCoo = gl_TexCoord[0].st;
  vec2 textCoo2 = gl_TexCoord[0].st;

	deltaTextCoord *= 1.0 / float(NUM_SAMPLES) * density;
	deltaTextCoord2 *= 1.0 / float(NUM_SAMPLES) * density;
	float illuminationDecay = 1.0;
	
	vec4 theColor;
	//vec4 theColor = vec4(1., 1., 1., 1.);
  vec4 sample;
  vec4 sample2;

	for(int i=0; i < NUM_SAMPLES ; i++)
	{
			textCoo -= deltaTextCoord;
			sample = texture2D(myTexture, textCoo );

      textCoo2 -= deltaTextCoord2;
			sample2 = texture2D(myTexture, textCoo2 );
			
			sample *= illuminationDecay * weight;
			sample2 *= illuminationDecay * weight;

      //theColor -= (sample + sample2);
      theColor += sample + sample2;
			//gl_FragColor += sample;
			
			illuminationDecay *= decay;
	}
  theColor *= exposure;

//  if (theColor.r < 0.01 && theColor.g < 0.01 && theColor.b < 0.01)
  //  {
    //  theColor = vec4(1.0, 1.0, 1.0, 0.1);
      //discard;
    //}


   //gl_FragColor = vec4(1., 1., 1., 1.) - theColor;
   gl_FragColor = theColor;
}
