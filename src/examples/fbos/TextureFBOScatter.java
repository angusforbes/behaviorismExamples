/* TextureFBOScatter.java ~ Jun 4, 2009 */
package examples.fbos;

import behaviorism.Behaviorism;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.Geom;
import behaviorism.textures.TextureFBO;
import behaviorism.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.grlea.log.SimpleLogger;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;
import behaviorism.utils.RenderUtils;
import javax.media.opengl.glu.GLU;
import static behaviorism.utils.RenderUtils.*;


/**
 *
 * @author angus
 */
public class TextureFBOScatter extends TextureFBO
{

  List<Geom> lights = new ArrayList<Geom>();
  List<Geom> occluders = new ArrayList<Geom>();
  long timePassed = 10000L;
  long addLength = 3000L;
  boolean addSlowly = false;

  public static final SimpleLogger log = new SimpleLogger(TextureFBOScatter.class);

  public TextureFBOScatter(int offScreenWidth, int offScreenHeight, List<Geom> lights, List<Geom> occluders, long addLength)
  {
    super(offScreenWidth, offScreenHeight);
    log.entry("in TextureFBOScatter() constructor");

    //this.offScreenWidth = offScreenWidth;
    //this.offScreenHeight = offScreenHeight;
    this.lights = lights;
    this.occluders = occluders;
    this.addLength = addLength;
    this.timePassed = Utils.nowMillis();
    if (addLength > 0L)
    {
      addSlowly = true;
    }
    log.exit("out TextureFBOScatter() constructor");
  }

  int max = 1;
  int dir = 1;

  @Override
  public void applyFBO()
  {
    log.entry("in applyFBO()");
    GL2 gl = RenderUtils.getGL();

    //0. bind our FBO and start drawing on it
    bindFBO();

//    gl.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fboId);
//    gl.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
//    gl.glViewport(0, 0, offScreenWidth, offScreenHeight);

    gl.glPushMatrix();
    {
      //step 1. set matrix to basic camera (without rotations etc)

      //gl.glLoadIdentity();
      //gl.glLoadMatrixd(Behaviorism.getInstance().renderer.modelviewMatrix, 0);
      //MatrixUtils.printMatrix(Behaviorism.getInstance().renderer.modelviewMatrix);


      //step 2. set light geometry -- the colored shapes that light will come from
      gl.glEnable(GL_BLEND);
      gl.glDisable(GL_DEPTH_TEST);

      int idx = 0;

      //System.out.println("is now > time?" + Utils.nowMillis() + " > " +timePassed);
      if (Utils.nowMillis() > timePassed)
      {

        if (max > lights.size() || max < 1)
        {
          dir *= -1;
        }

        max += dir;

        timePassed += addLength;
      }

      for (Geom light : lights)
      {
        if (addSlowly == true)
        {
          if (idx >= max)
          {
            break;
          }
        }

        //hmm i think we need the option to have the light geoms in ortho view too... think about this
        //i want the fbo to be full screen and stay full screen even after reshapes
        gl.glLoadMatrixd(light.modelview, 0);
        light.draw();
        idx++;

      }

      //System.out.println("max = " + max);
      gl.glEnable(GL_DEPTH_TEST);

      /*
      gl.glColor4f(1f, 0f, 0f, 1f);
      //gl.glTranslatef(lightPosition[0] + lightX, lightPosition[1], lightPosition[2]);
      //gl.glTranslatef(.0f, .0f, lightPosition[2]);
      //drawSquare(gl);
      Behaviorism.getInstance().renderer.glut.glutSolidSphere(.25f, 10, 10);
      gl.glColor4f(1f, 1f, 1f, 1f);
      //drawTexturedSqaure(gl);
      //Behaviorism.getInstance().renderer.glut.glutWireCube(1f);
       */
      // Step 3. Draw occuding source black onto texture
      gl.glDisable(GL_BLEND);
      for (Geom occluder : occluders)
      {
        //hmm have to force color to be black...
        Colorf prevColor = occluder.getColor();
        //float prevA = occluder.a;
        occluder.setColor(1f, 1f, 1f, 1f);
        //occluder.setColor(1f, 0f, 0f, 1f);
        gl.glLoadMatrixd(occluder.modelview, 0);
        occluder.draw();
        occluder.setColor(prevColor);
      }

    //      gl.glTranslatef(.65f, 0f, 0f);
//      gl.glColor4f(0f, 0f, 0f, 1f);
//      //drawSquare(gl);
//      Behaviorism.getInstance().renderer.glut.glutSolidSphere(1f / 6f, 10, 10);
      gl.glEnable(GL_BLEND);

    }
    gl.glPopMatrix();

    //step 4. we are finished drawing to our FBO, so unbind it and return to our original viewport, etc
    unbindFBO();
    // gl.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
   // gl.glViewport(0, 0, Behaviorism.getInstance().canvasWidth, Behaviorism.getInstance().canvasHeight); //(int) renderHeight);
  //gl.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

    log.entry("out applyFBO()");

  }

  /*
  public void initializeTexture()
  {
  GL gl = GLU.getCurrentGL();

  if (!generateFBO(gl))
  {
  System.err.println("in GeomScatter : couldn't create FBO");
  System.exit(0);
  return;
  }
  }

  public void disposeTexture()
  {
  GL gl = GLU.getCurrentGL();

  gl.glDeleteFramebuffersEXT(1, new int[]
  {
  this.fboId
  }, 0);
  gl.glDeleteRenderbuffersEXT(1, new int[]
  {
  this.rboId
  }, 0);
  }
  

  public boolean generateFBO(GL gl)
  {
  //  int offScreenWidth = (int) (renderWidth / OFF_SCREEN_RENDER_RATIO);
  //  int offScreenHeight = (int) (renderHeight / OFF_SCREEN_RENDER_RATIO);
  boolean fboUsed;

  //create a texture object to store color info
  //int[] fboTextureId = new int[1];
  //gl.glGenTextures(1, fboTextureId, 0);
  this.texture = TextureIO.newTexture(GL_TEXTURE_2D);
  this.texture.bind();

  //    int[] fboTextureId = new int[1];
  //    gl.glGenTextures(1, fboTextureId, 0);
  //    this.texture = TextureIO.newTexture(fboTextureId[0]);
  //    gl.glBindTexture(GL_TEXTURE_2D, fboTextureId[0]);

  gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
  gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
  //gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
  //gl.glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE); // automatic mipmap
  //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
  //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
  gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, offScreenWidth, offScreenHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, null);
  gl.glBindTexture(GL_TEXTURE_2D, 0);

  // create a renderbuffer object to store depth info
  int[] rboBindId = new int[1];
  gl.glGenRenderbuffersEXT(1, rboBindId, 0);
  this.rboId = rboBindId[0];
  gl.glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, this.rboId);
  gl.glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT, offScreenWidth, offScreenHeight);
  gl.glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, 0);

  // create a framebuffer object and attach the color texture and depth renderbuffer
  int[] fboBindId = new int[1];
  gl.glGenFramebuffersEXT(1, fboBindId, 0);
  this.fboId = fboBindId[0];
  gl.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, this.fboId);

  // attach the texture to FBO color attachment point
  gl.glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, this.texture.getTextureObject(), 0);
  // attach the renderbuffer to depth attachment point
  gl.glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, this.rboId);

  // check FBO status
  int status = gl.glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);
  if (status != GL_FRAMEBUFFER_COMPLETE_EXT)
  {
  fboUsed = false;
  System.out.println("GL_FRAMEBUFFER_COMPLETE_EXT failed, CANNOT use FBO\n");
  }
  else
  {
  fboUsed = true;
  System.out.printf("GL_FRAMEBUFFER_COMPLETE_EXT OK, using FBO\n");
  System.out.printf("fbo offScreenWidth =%d\n", offScreenWidth);
  System.out.printf("fbo offScreenHeight =%d\n", offScreenHeight);
  System.out.printf("fbo texture id=%d\n", this.texture.getTextureObject());
  System.out.printf("fbo id=%d\n", fboId);
  System.out.printf("fbo's rbo id=%d\n", rboId);
  }

  // switch back to window-system-provided framebuffer
  gl.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

  return fboUsed;
  }
   */
}
