package examples.functions;

import behaviorism.Behaviorism;
import behaviorism.geometry.Geom;
import behaviorism.worlds.World;
import javax.vecmath.Point3f;

public class WorldSpline extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldSpline());
  }

  public void setUpWorld()
  {
    setColor(0f,0f,0f,0f);
    testSplinePolynomial();
  }

  public void testSplinePolynomial()
  {
    Geom g = new GeomSplineCurve(new Point3f(), 10, -1, +1);
    g.setColor(1f,1f,1f,1f);
    addGeom(g);
  }


}
