/* GeomVertexArrayTest.java ~ Apr 15, 2010 */

package examples.rendering;

import behaviorism.geometry.Geom;
import behaviorism.utils.RenderUtils;
import behaviorism.utils.Utils;
import com.sun.opengl.util.BufferUtil;
import java.nio.FloatBuffer;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;

/**
 *
 * @author angus
 */
public class GeomVertexArrayTest extends Geom
{
  FloatBuffer vertexBuffer = null;
  FloatBuffer colorBuffer = null;

  public GeomVertexArrayTest()
  {
  }

  private void makePoints(GL2 gl)
  {

    vertexBuffer = BufferUtil.newFloatBuffer(WorldFastVertexRendering.numPoints * 3);

    for (int i = 0; i < WorldFastVertexRendering.numPoints * 3; i+=3)
    {
      vertexBuffer.put(Utils.randomFloat(-5f, 5f));
      vertexBuffer.put(Utils.randomFloat(-4f, 4f));
      vertexBuffer.put(0f);
    }
    vertexBuffer.rewind();


    colorBuffer = BufferUtil.newFloatBuffer(WorldFastVertexRendering.numPoints * 4);

    for (int i = 0; i < WorldFastVertexRendering.numPoints * 4; i+=4)
    {
      colorBuffer.put(Utils.randomFloat(0f, 0f));
      colorBuffer.put(Utils.randomFloat(0f, 0f));
      colorBuffer.put(Utils.randomFloat(0f, 1f));
      colorBuffer.put(Utils.randomFloat(0f, 1f));
    }
    colorBuffer.rewind();
  }

  boolean firstTime = true;
  public void draw()
  {
    GL2 gl = RenderUtils.getGL();
    if (firstTime == true)
    {
      makePoints(gl);
      System.err.println("made points...");
      firstTime = false;
    }

    gl.glPointSize(1f);

    gl.glEnableClientState(GL_VERTEX_ARRAY);
    gl.glEnableClientState(GL_COLOR_ARRAY);
    gl.glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
    gl.glColorPointer(4, GL_FLOAT, 0, colorBuffer);

    gl.glDrawArrays(GL_POINTS, 0, WorldFastVertexRendering.numPoints);

    gl.glDisableClientState(GL_VERTEX_ARRAY);
    gl.glDisableClientState(GL_COLOR_ARRAY);
  }
}
