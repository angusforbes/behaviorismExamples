/* GeomScatter.java ~ Jun 2, 2009 */
package examples.fbos;

import behaviorism.Behaviorism;
import behaviorism.geometry.GeomRect;
import behaviorism.handlers.MouseHandler;
import behaviorism.shaders.FragmentShader;
import behaviorism.shaders.Program;
import behaviorism.shaders.VertexShader;
import behaviorism.utils.RenderUtils;
import static javax.media.opengl.GL2.*;
import javax.media.opengl.GL2;
import javax.vecmath.Point3f;
import org.grlea.log.SimpleLogger;

import static javax.media.opengl.GL.*;

/**
 *
 * @author angus
 */
public class GeomPhotoScatterFBO extends GeomRect
{

  Program program;
  Point3f directionVector;
  int[] fboTextureId = new int[1];
  int[] fboId = new int[1];
  boolean fboUsed = false;
  float OFF_SCREEN_RENDER_RATIO = 1f;
  TextureFBOScatter ti;
  boolean renderOrtho = false;
  public float lineWidth = 1f;
  float renderW;
  float renderH;

  public static final SimpleLogger log = new SimpleLogger(GeomPhotoScatterFBO.class);

  //TextureImage ti = null;
  public GeomPhotoScatterFBO(Point3f origin, float w, float h, TextureFBOScatter ti, boolean renderOrtho)
  {
    super(origin, w, h);
    this.ti = ti;
    //attachTexture(ti);
    this.program = new Program(
      new VertexShader("shaders/scatter.vert"),
      new FragmentShader("shaders/scatter.frag"));

    this.renderOrtho = renderOrtho;
  }

  @Override
  public void draw()
  {
    GL2 gl = RenderUtils.getGL();

    if (program.programId <= 0)
    {
      program.install();
    }

    if (ti == null)
    {
      log.debug("in GeomPhotoScatterFBO.draw() : texture image is null");
      return;
    }

    //if (this.textures.get(0).texture == null)
    if (ti.texture == null)
    {
      log.debug("in GeomPhotoScatterFBO.draw() : texture is null");
      return;
    }

    log.debug("in GeomPhotoScatterFBO.draw() : texture is valid");
    ti.applyFBO();

    renderW = Behaviorism.getInstance().canvasWidth;
    renderH = Behaviorism.getInstance().canvasHeight;

    gl.glPushMatrix();
    {
      gl.glEnable(GL_DEPTH_TEST);
      gl.glEnable(GL_BLEND);

      gl.glColor4f(0f, 0f, 0f, 0f);
      gl.glBegin(GL_QUADS);
      gl.glVertex3f(0f, 0f, offset);
      gl.glVertex3f(w, 0f, offset);
      gl.glVertex3f(w, h, offset);
      gl.glVertex3f(0f, h, offset);
      gl.glEnd();
    }
    gl.glPopMatrix();

    //step 6. pass the texture to a light scattering shader and apply to it geometry
    gl.glPushMatrix();
    {
      if (renderOrtho == true)
      {
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-renderW / 2, renderW / 2, -renderH / 2, renderH / 2, 000, 50000.0);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
      }

      gl.glActiveTexture(GL_TEXTURE0);
//      this.textures.get(0).texture.bind();
      ti.texture.bind();
      boolean useShader = true;
      if (useShader == true)
      {
        program.bind();

        log.debugObject("light1_x", WorldPhotoScatter.light1_x);
        log.debugObject("light1_y", WorldPhotoScatter.light1_y);

        gl.glUniform1i(program.uniform("NUM_SAMPLES"), WorldPhotoScatter.samples);
        gl.glUniform2f(program.uniform("lightPositionOnScreen"),
          WorldPhotoScatter.light1_x, WorldPhotoScatter.light1_y);
        gl.glUniform2f(program.uniform("lightPositionOnScreen2"),
          WorldPhotoScatter.light2_x, WorldPhotoScatter.light2_y);
        gl.glUniform1f(program.uniform("exposure"), WorldPhotoScatter.exposure);//.0034f);
        gl.glUniform1f(program.uniform("decay"), WorldPhotoScatter.decay); //.99f);
        gl.glUniform1f(program.uniform("density"), WorldPhotoScatter.density); //.84f);  //10f; // //.84f);
        gl.glUniform1f(program.uniform("weight"), WorldPhotoScatter.weight); //5.65f);
        gl.glUniform1i(program.uniform("myTexture"), 0);
      }

      gl.glDisable(GL_BLEND);
      gl.glDisable(GL_DEPTH_TEST);
      gl.glEnable(GL_TEXTURE_2D);

      if (renderOrtho == true)
      {
        gl.glBegin(GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(-renderW / 2, -renderH / 2);

        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(renderW / 2, -renderH / 2);

        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(renderW / 2, renderH / 2);

        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(-renderW / 2, renderH / 2);
        gl.glEnd();
      }
      else
      {
        drawSquare(w, h);
      }


      if (useShader == true)
      {
        program.unbind();
      }


      gl.glActiveTexture(GL_TEXTURE0);
      gl.glBindTexture(GL_TEXTURE_2D, 0);

    }
    gl.glPopMatrix();


    gl.glBindTexture(GL_TEXTURE_2D, 0);
  }
 
  public void drawSquare(float w, float h)
  {
    GL2 gl = RenderUtils.getGL();

    gl.glBegin(GL_QUADS);

    gl.glTexCoord2f(0, 0);
    gl.glVertex2f(0f, 0f);
    gl.glTexCoord2f(1, 0);
    gl.glVertex2f(w, 0f);
    gl.glTexCoord2f(1, 1);
    gl.glVertex2f(w, h);
    gl.glTexCoord2f(0, 1);
    gl.glVertex2f(0, h);

    gl.glEnd();
  }

  @Override
  //public void clickAction()
  public void mouseMovingAction()
  {
    Point3f mg = MouseHandler.getInstance().mouseGeomPoint;
    float px = mg.x / this.w;
    float py = mg.y / this.h;
// System.out.println("you clicked! (" + mg + ") px/py = " + px + "/" + py);
    if (WorldPhotoScatter.useLight == 1)
    {
      WorldPhotoScatter.light1_x = px;
      WorldPhotoScatter.light1_y = py;
    }
    else if (WorldPhotoScatter.useLight == 2)
    {
      WorldPhotoScatter.light2_x = px;
      WorldPhotoScatter.light2_y = py;
    }
  }
}