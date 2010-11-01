/* GeomDisplayListTest.java ~ Apr 15, 2010 */
package examples.rendering;

import behaviorism.geometry.Colorf;
import behaviorism.geometry.Geom;
import behaviorism.utils.RenderUtils;
import behaviorism.utils.Utils;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;

/**
 *
 * @author angus
 */
public class GeomDisplayListTest extends Geom
{

  int displayListId = -1;

  public GeomDisplayListTest()
  {
  }

  public void makeDisplayList(GL2 gl)
  {
    displayListId = gl.glGenLists(1);

    gl.glNewList(displayListId, GL_COMPILE);

    gl.glPointSize(1f);
    gl.glBegin(GL_POINTS);
    for (int i = 0; i < WorldFastVertexRendering.numPoints; i++)
    {
      gl.glColor4fv(new Colorf(0f, Utils.randomFloat(0f, 1f), 0f, Utils.randomFloat(0f, 1f)).array(), 0);
      gl.glVertex3f(Utils.randomFloat(-5f, 5f), Utils.randomFloat(-4f, 4f), 0f);
    }
    gl.glEnd();
    gl.glEndList();
  }

  public void draw()
  {
    GL2 gl = RenderUtils.getGL();

    if (displayListId < 0)
    {
      makeDisplayList(gl);
    }

    gl.glCallList(displayListId);

  }
}
