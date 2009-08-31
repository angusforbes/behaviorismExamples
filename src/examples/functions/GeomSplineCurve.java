/* GeomSplineCurve.java ~ Jul 1, 2009 */
package examples.functions;

import behaviorism.geometry.Geom;
import behaviorism.utils.MatrixUtils;
import behaviorism.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Point3f;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.SplineInterpolator;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.UnivariateRealInterpolator;
import static javax.media.opengl.GL.*;
import behaviorism.utils.RenderUtils;
import javax.media.opengl.GL2;

/**
 *
 * @author angus
 */
public class GeomSplineCurve extends Geom
{
  int resolution = 50;
  double[] x;
  double[] y;
  UnivariateRealFunction function = null;
  List<Point3f> ctrlPoints;
  List<Point3f> evalPoints;

  public GeomSplineCurve(Point3f p3f, int pts, double minY, double maxY)
  {
    super(p3f);

    this.x = new double[pts];
    this.y = new double[pts];

    x[0] = 0;
    y[0] = 0;

    for (int i = 1; i < pts - 1; i++)
    {
      x[i] = i * (1 / (double) (pts - 1));
      y[i] = Utils.random(minY, maxY);
    }

    x[pts - 1] = 1;
    y[pts - 1] = 1;

    initSpline();

  }

  public GeomSplineCurve(Point3f p3f, double[] x, double[] y)
  {
    super(p3f);
    this.x = x;
    this.y = y;
    initSpline();
  }

  public void initSpline()
  {

    ctrlPoints = new ArrayList<Point3f>();

    for (int i = 0; i < x.length; i++)
    {
      ctrlPoints.add(new Point3f((float) x[i], (float) y[i], 0f));
    }

    UnivariateRealInterpolator interpolator = new SplineInterpolator();

    try
    {
      this.function = interpolator.interpolate(x, y);
    }
    catch (MathException me)
    {
      me.printStackTrace();
    }

    evaluate();
  }

  public void evaluate()
  {
    evalPoints = new ArrayList<Point3f>();
    float inc = 1f / (float) resolution;
    try
    {
      for (float iX = 0f; iX <= 1f; iX += inc)
      {
        evalPoints.add(new Point3f(iX, (float) function.value(iX), 0f));
      }
    }
    catch (FunctionEvaluationException fee)
    {
      fee.printStackTrace();
    }
  }

  public void draw()
  {
    GL2 gl = RenderUtils.getGL();
    
    gl.glColor4fv(getColor().array(), 0);

    gl.glBegin(GL_LINE_STRIP);
    for (Point3f p : evalPoints)
    {
      gl.glVertex3fv(MatrixUtils.toArray(p), 0);
    }
    gl.glEnd();

    gl.glBegin(GL_POINTS);
    for (Point3f p : ctrlPoints)
    {
      gl.glVertex3fv(MatrixUtils.toArray(p), 0);
    }
    gl.glEnd();
  }
}
