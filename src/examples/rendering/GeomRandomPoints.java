/* GeomDisplayListTest.java ~ Apr 15, 2010 */
package examples.rendering;

import behaviorism.geometry.Geom;
import behaviorism.utils.RenderUtils;
import behaviorism.utils.Utils;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;

/**
 *
 * @author angus
 */
public class GeomRandomPoints extends Geom
{
  float[] pts;
  float[] colors;

  public GeomRandomPoints()
  {
  }

  public void makePoints()
  {
    pts = new float[WorldFastVertexRendering.numPoints * 3];
    for (int i = 0; i < WorldFastVertexRendering.numPoints * 3; i+=3)
    {
      pts[i] = Utils.randomFloat(-5f, 5f);
      pts[i + 1] = Utils.randomFloat(-4f, 4f);
      pts[i + 2] = 0f;
    }
    colors = new float[WorldFastVertexRendering.numPoints * 4];
    for (int i = 0; i < WorldFastVertexRendering.numPoints * 4; i+=4)
    {
      colors[i] = Utils.randomFloat(0f, 1f);
      colors[i + 1] = Utils.randomFloat(0f, 0f);
      colors[i + 2] = Utils.randomFloat(0f, 0f);
      colors[i + 3] = Utils.randomFloat(0f, 1f);
    }
  }

  boolean firstTime = true;

  public void draw()
  {
    GL2 gl = RenderUtils.getGL();

    if (firstTime == true)
    {
      makePoints();
      firstTime = false;
    }

    gl.glPointSize(1f);
    gl.glBegin(GL_POINTS);

    for (int idx = 0, i = 0, j = 0; idx < WorldFastVertexRendering.numPoints; idx++, i+=3, j+=4)
    {
      gl.glColor4f(colors[j], colors[j+1], colors[j+2], colors[j+3]);
      gl.glVertex3f(pts[i], pts[i+1], pts[i+2]);
    }
    gl.glEnd();
  }
}
