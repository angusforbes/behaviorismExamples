/* GeomSingleQuadStrip.java ~ Apr 19, 2010 */

package examples.curves;

import behaviorism.geometry.Geom;
import behaviorism.utils.RenderUtils;
import java.util.List;
import javax.media.opengl.GL2;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class GeomSingleQuadStrip extends Geom
{
  List<Point3f> pts;
  float xwidth;
  public GeomSingleQuadStrip(Point3f p3f, List<Point3f> pts, float xwidth)
  {
    super(p3f);
    this.pts = pts;
    this.xwidth = xwidth;
  }

  public void draw()
  {
    GL2 gl = RenderUtils.getGL();

    gl.glBegin(gl.GL_QUAD_STRIP);
    gl.glColor4fv(color.array(), 0);
    for (Point3f p : pts)
    {
      gl.glVertex3f(-xwidth * .5f, p.y, p.z);
      gl.glVertex3f(+xwidth * .5f, p.y, p.z);
    }

    gl.glEnd();

  }
}
