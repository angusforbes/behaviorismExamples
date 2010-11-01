/* WorldCurves.java ~ Apr 19, 2010 */

package examples.curves;

import behaviorism.Behaviorism;
import behaviorism.behaviors.Scheduler;
import behaviorism.behaviors.geom.BehaviorTranslate;
import behaviorism.behaviors.geom.ContinuousBehaviorBuilder;
import behaviorism.geometry.GeomPoint;
import behaviorism.geometry.GeomRect;
import behaviorism.geometry.glu.GeomGluNurbsCurve;
import behaviorism.geometry.glu.GeomGluNurbsSurface;
import behaviorism.geometry.glut.GeomGlutSphere;
import behaviorism.utils.MatrixUtils;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import examples.behaviors.WorldSimpleEasing;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class WorldCurves extends World
{


  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldCurves());
  }

  public void setUpWorld()
  {

    setColor(0f, 0f, 0f, 1f);

    //need to set lighting here...

    //testNurbsSurface();
    testNurbsCurve();
    //testCameraAnimationOverCurve();
  }

  private void testCameraAnimationOverCurve()
  {
    int numPts = 50;
    float zDist = 20;
    float zInc = zDist / (float) numPts;
    float minY = -.5f;
    float maxY = .5f;

    ArrayList<Point3f> pts = new ArrayList<Point3f>();
    for (int i = 0; i < numPts; i++)
    {
      float z = -(zInc * i);
      float y = Utils.randomFloat(minY, maxY);
      pts.add(new Point3f(0f, y, z));
    }

    GeomSingleQuadStrip gsqs = new GeomSingleQuadStrip(new Point3f(), pts, 1f);
    addGeom(gsqs);
  }


  private void testNurbsSurface()
  {
    GeomRect rect = new GeomRect(new Point3f(), 1f, 1f);
    //center the rect in middle of the world
    //rect.centerGeom(new Point3f(0f, 0f, 0f));
    rect.setColor(1f,0f,0f,1f);

    //int numPointsU = 8;
    //int numPointsV = 9;
    int numPointsU = 10;
    int numPointsV = 10;
    int orderU = 3; //3;
    int orderV = 3 ; //3;

    //make point mesh
    GeomPoint[][] controlPoints = new GeomPoint[numPointsU][numPointsV];
    float[] knotArrayU = new float[numPointsU + orderU];
    float[] knotArrayV = new float[numPointsV + orderV];

    float sx = -2f;
    float sy = -2f;
    float sw = 4f;
    float sh = 4f;

    float incU = 1f / (float)(numPointsU - (orderU - 1));
    System.out.println("incU = " + incU);
    for (int i = 0; i < numPointsU + orderU; i++)
    {
      if (i < orderU)
      {
        knotArrayU[i] = 0f;
      }
      else if(i > numPointsU - 1)
      {
        knotArrayU[i] = 1f;

      }
      else
      {
        knotArrayU[i] = ((i - (orderU-1)) * incU);
      }
      System.out.println("" + i + " : " + knotArrayU[i]);

      knotArrayU[i] = (i * incU * 3);
    }


    float incV = 1f / (float)(numPointsV - (orderV - 1));
    System.out.println("incV = " + incV);
    for (int i = 0; i < numPointsV + orderV; i++)
    {
      if (i < orderV)
      {
        knotArrayV[i] = 0f;
      }
      else if(i > numPointsV - 1)
      {
        knotArrayV[i] = 1f;

      }
      else
      {
        knotArrayV[i] = ((i - (orderV-1)) * incV);
      }
      System.out.println("" + i + " : " + knotArrayV[i]);

      knotArrayV[i] = i * incV * 3;
    }


    for (int i = 0; i < numPointsU; i++)
    {
      for (int j = 0; j < numPointsV; j++)
      {
        controlPoints[i][j] = new GeomPoint(sx + (sw/numPointsU) * i, sy + (sh/numPointsV) * j, 0f);
      }
    }

    GeomGluNurbsSurface ggns = new GeomGluNurbsSurface(
       new Point3f(),
       controlPoints,
       knotArrayU, knotArrayV,
       orderU, orderV);

    addGeom(ggns);
    ggns.setColor(1f, 0f, 0f, 1f);

    for (int i = 0; i < numPointsU; i++)
    {
      for (int j = 0; j < numPointsV; j++)
      {

      BehaviorTranslate bt = new BehaviorTranslate(
      new ContinuousBehaviorBuilder(Utils.nowPlusMillis(5000L), Utils.randomLong(3000, 6000)).
        ranges(MatrixUtils.toArray(Utils.randomPoint3f(-.3f, -.3f, -5f, .3f, .3f, 3f))).
        isReversing(true).repeats(300)
       );

      Scheduler.getInstance().attachGeom(bt, controlPoints[i][j]);
      //controlPoints[i][j].attachBehavior(bt);
      }
   }

  }

  private void testNurbsCurve()
  {
//      GeomRect rect = new GeomRect(new Point3f(), 1f, 1f);
//    //center the rect in middle of the world
//    //rect.centerGeom(new Point3f(0f, 0f, 0f));
//    rect.setColor(0f,0f,1f,0f);
//    //translate rotating and scaling to the center of the rect (the default is the lower-left)
//    rect.setRotateAnchor(rect.getCenter());
//    rect.setScaleAnchor((rect.getCenter()));
//    rect.scale.x = 2f;
//    rect.rotate = new Point3d(45d, 0d, 0d);
//    //add the rect to the behaviorism scene graph
//   // addGeom(rect);
//
//    GeomRect rect2 = new GeomRect(new Point3f(-.1f,-.1f,0f),.2f, .2f);
//    rect2.setColor(0f,1f,0f,1f);
//    rect2.setRotateAnchor(rect2.getCenter());
//    rect2.setScaleAnchor((rect2.getCenter()));
//   // rect.addGeom(rect2);

//    GeomGlutSphere ggs = new GeomGlutSphere(new Point3f(-1f, 0f, 0f), .5f, 10, 10, false);
//    addGeom(ggs);


    ///// to loop a nurbs curve, add the first (order - 1) points once again to the end of the list of points
    int order = 3; //max order is 10. min order is effectively 3
    int numPts = 30;
    float[] knot = new float[numPts + order];
    List<GeomPoint> somePoints = new ArrayList<GeomPoint>();
    for (int i = 0; i < numPts - (order - 1); i++)
    {
      somePoints.add(new GeomPoint(Utils.randomPoint3f(-1f, -1f, -1f, 1f, 1f, 1f)));
    }

    //adding these points again to make a loop (only works with a uniform vector, or at least
    //when the first and last N knots are uniform, where N = the order).
    for (int i = 0; i < order - 1; i++)
    {
      somePoints.add((somePoints.get(i)));
    }

    //uniform knot vector
    float  inc = 1f / knot.length;
    for (int i = 0; i < knot.length; i++)
    {
        knot[i] = (i * inc); //somePoints.get(i).x;
    }
    GeomGluNurbsCurve ggnc = new GeomGluNurbsCurve(new Point3f(0f, 0f, 0f), somePoints, knot, order);

    addGeom(ggnc);

    for (int i = 2; i < ggnc.dynamicControlPoints.size(); i++)
    //for (int i = 0; i < 3; i++)
    {
      BehaviorTranslate bt = new BehaviorTranslate(
      new ContinuousBehaviorBuilder(Utils.now(), Utils.randomLong(2500, 5000)).
        ranges(MatrixUtils.toArray(Utils.randomPoint3f(-1f, -1f, 1f, 1f))).isReversing(true).repeats(1000)
       );
      Scheduler.getInstance().attachGeom(bt, ggnc.dynamicControlPoints.get(i));

      BehaviorTranslate bt2 = new BehaviorTranslate(
      new ContinuousBehaviorBuilder(Utils.now(), Utils.randomLong(2500, 5000)).
        ranges(MatrixUtils.toArray(Utils.randomPoint3f(-1f, -1f, 1f, 1f))).isReversing(true).repeats(1000)
       );
      Scheduler.getInstance().attachGeom(bt2, ggnc.dynamicControlPoints.get(i));

   }

  }

}
