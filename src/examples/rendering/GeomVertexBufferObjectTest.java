/* GeomVertexBufferObjectTest.java ~ Apr 15, 2010 */
package examples.rendering;

import behaviorism.geometry.Geom;
import behaviorism.utils.RenderUtils;
import behaviorism.utils.Utils;
import com.sun.opengl.util.BufferUtil;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;

/**
 *
 * @author angus
 */
public class GeomVertexBufferObjectTest extends Geom
{

  int numPoints = WorldFastVertexRendering.numPoints;
  int stride = 7;
  //float[] v_arr = new float[ (numPoints * 3) + (numPoints * 3)];
  float[] v_arr = new float[(numPoints * 3) + (numPoints * 4)];
  private FloatBuffer bigArraySystem;
  private FloatBuffer bigArray;
  private ByteBuffer bigArrayVBOBytes;
  private FloatBuffer bigArrayVBO;
  FloatBuffer vs, cs, bs;
  VBOBuffer vboBuffer;
  private int bigBufferObject;
  int bufsize;

  static class VBOBuffer
  {

    public FloatBuffer vertices;
    public FloatBuffer colors;
    public int vertexOffset;
    public int colorOffset;
  }

  public GeomVertexBufferObjectTest()
  {
    for (int i = 0; i < v_arr.length; i += stride)
    {
      //vertices
      v_arr[i] = Utils.randomFloat(-5f, 5f);
      v_arr[i + 1] = Utils.randomFloat(-4f, 4f);
      v_arr[i + 2] = 0f;

      //colors
      v_arr[i + 3] = Utils.randomFloat();
      v_arr[i + 4] = Utils.randomFloat();
      v_arr[i + 5] = Utils.randomFloat();
      if (stride == 7)
      {
        v_arr[i + 6] = Utils.randomFloat();
      }
    }

    bufsize = v_arr.length * BufferUtil.SIZEOF_FLOAT;
    System.out.println("bufsize = " + bufsize);
  }

  private void allocateBigArray(GL2 gl)
  {
    bigArraySystem = setupBuffer(ByteBuffer.allocateDirect(bufsize));

    int[] tmp = new int[1];
    gl.glGenBuffers(1, tmp, 0);
    bigBufferObject = tmp[0];
    gl.glBindBuffer(GL_ARRAY_BUFFER, bigBufferObject);
    // Initialize data store of buffer object
    gl.glBufferData(GL_ARRAY_BUFFER, bufsize, (Buffer) null, GL_DYNAMIC_DRAW);
    bigArrayVBOBytes = gl.glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY);
    bigArrayVBO = setupBuffer(bigArrayVBOBytes);
    gl.glUnmapBuffer(GL_ARRAY_BUFFER);
    // Unbind buffer; will be bound again in main loop
    gl.glBindBuffer(GL_ARRAY_BUFFER, 0);

    float megabytes = (bufsize / 1000000.f);
    System.err.println("Allocated " + megabytes + " megabytes of fast memory");

    vboBuffer = new VBOBuffer();

    bigArray = bigArrayVBO;
  }

  private void setupBuffers()
  {
    System.out.println("in sliceBuffer()");
    vboBuffer.vertices = sliceBuffer(bigArray, 0, bufsize / BufferUtil.SIZEOF_FLOAT);
    vboBuffer.colors = sliceBuffer(vboBuffer.vertices, 3,
      vboBuffer.vertices.limit() - 3);
    vboBuffer.vertexOffset = 0;
    vboBuffer.colorOffset = 3 * BufferUtil.SIZEOF_FLOAT;
  }

  private FloatBuffer setupBuffer(ByteBuffer buf)
  {
    buf.order(ByteOrder.nativeOrder());
    return buf.asFloatBuffer();
  }

  private FloatBuffer sliceBuffer(FloatBuffer array,
    int sliceStartIndex, int sliceLength)
  {
    array.position(sliceStartIndex);
    FloatBuffer ret = array.slice();
    System.out.println("ret capacity = " + ret.capacity());
    System.out.println("slice length = " + sliceLength);
    array.position(0);
    ret.limit(sliceLength);
    return ret;
  }

  boolean firstTime = true;

  public void draw()
  {
    GL2 gl = RenderUtils.getGL();

    if (firstTime == true)
    {
      System.out.println("allocating...");
      allocateBigArray(gl);
      setupBuffers();

      gl.glEnableClientState(GL_VERTEX_ARRAY);
      gl.glEnableClientState(GL_COLOR_ARRAY);

      bigArray.put(v_arr);
      bigArray.rewind();

      firstTime = false;
    }

    gl.glEnableClientState(GL_VERTEX_ARRAY);
    gl.glEnableClientState(GL_COLOR_ARRAY);

    gl.glBindBuffer(GL_ARRAY_BUFFER, bigBufferObject);


    ByteBuffer tmp = gl.glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY);
    if (tmp == null)
    {
      throw new RuntimeException("Unable to map vertex buffer object");
    }

    if (tmp != bigArrayVBOBytes)
    {
      System.out.println("recreating 1...");
      bigArrayVBOBytes = tmp;
      bigArrayVBO = setupBuffer(tmp);
    }

    if (bigArray != bigArrayVBO)
    {
      System.out.println("recreating 2...");
      bigArray = bigArrayVBO;
      setupBuffers();
    }

    gl.glColorPointer(4, GL_FLOAT, stride * BufferUtil.SIZEOF_FLOAT, vboBuffer.colorOffset);
    gl.glVertexPointer(3, GL_FLOAT, stride * BufferUtil.SIZEOF_FLOAT, vboBuffer.vertexOffset);

    gl.glUnmapBuffer(GL_ARRAY_BUFFER);
    //UNMAPPED...

    gl.glPointSize(1f);

    //gl.glDrawArrays(GL_TRIANGLES, 0, numPoints);
    gl.glDrawArrays(GL_POINTS, 0, numPoints);

    gl.glDisableClientState(GL_VERTEX_ARRAY);
    gl.glDisableClientState(GL_COLOR_ARRAY);


  }
}

