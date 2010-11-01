package examples.behaviors;

import behaviorism.Behaviorism;
import behaviorism.behaviors.Behavior;
import behaviorism.behaviors.Scheduler;
import behaviorism.behaviors.easing.Easing;
import behaviorism.behaviors.easing.Easing.EasingEnum;
import behaviorism.behaviors.easing.EasingBounce;
import behaviorism.behaviors.easing.EasingElastic;
import behaviorism.behaviors.easing.EasingLinear;
import behaviorism.behaviors.easing.EasingPoints;
import behaviorism.behaviors.easing.EasingPolynomial;
import behaviorism.behaviors.easing.EasingSine;
import behaviorism.behaviors.easing.EasingSpline;
import behaviorism.behaviors.geom.BehaviorColor;
import behaviorism.behaviors.geom.BehaviorRotate;
import behaviorism.behaviors.geom.BehaviorScale;
import behaviorism.behaviors.geom.BehaviorTranslate;
import behaviorism.behaviors.geom.ContinuousBehaviorBuilder;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.Geom;
import behaviorism.geometry.GeomCircle;
import behaviorism.geometry.GeomRect;
import behaviorism.geometry.GeomSimpleLine;
import behaviorism.geometry.text.TextBuilder;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import javax.vecmath.Point3f;

public class WorldSimpleEasing extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldSimpleEasing());
  }

  public void setUpWorld()
  {
    setColor(.2f, .2f, .2f);
    testEasing();
  }


  public void testEasing()
  {
    this.getState().BLEND = true;
    
    EasingEnum ease = EasingEnum.OUT;

    //set up timing...
    long speed = 3000L;
    long base = Utils.nowPlusMillis(3000);

    //set up geoms...
    int numGeoms = 7;
    float stY = -1.5f;
    float enY = 1.5f;
    float incY = (enY - stY) / (numGeoms - 1);

    Geom[] transGeoms = new Geom[numGeoms];
    Geom[] rotGeoms = new Geom[numGeoms];
    Geom[] scaleGeoms = new Geom[numGeoms];
    Geom[] colorGeoms = new Geom[numGeoms];

    for (int i = 0; i < numGeoms; i++)
    {
      transGeoms[i] = new GeomRect(-1.0f, (stY + incY * i) - .1f, 0f, .2f, .2f);
      addGeom(transGeoms[i]);
      transGeoms[i].setColor(1f,0f,0f,.6f);
      
      rotGeoms[i] = new GeomRect(-1.5f, (stY + incY * i) - .125f, 0f, .25f, .25f);
      addGeom(rotGeoms[i]);
      rotGeoms[i].setColor(0f,1f,0f,.6f);
      rotGeoms[i].setRotateAnchor(.125f, .125f, 0f);

      scaleGeoms[i] = new GeomCircle(-1.95f, (stY + incY * i), 0f, .125f/2f);
      addGeom(scaleGeoms[i]);
      scaleGeoms[i].setColor(0f,0f,1f,.6f);
      
      colorGeoms[i] = new GeomRect(-2.65f, (stY + incY * i) -.125f, 0f, .25f, .25f);
      colorGeoms[i].getState().BLEND = true;
      addGeom(colorGeoms[i]);
      colorGeoms[i].setColor(1f,1f,1f,.1f);
  
    }

    //reusable builders...
    
    //for translate
    ContinuousBehaviorBuilder tb = new ContinuousBehaviorBuilder(base, speed)
      .isReversing(true)
      .repeats(-1)
      .ranges(new float[]{3f,0f,0f});

    //for rotate
    ContinuousBehaviorBuilder rb = new ContinuousBehaviorBuilder(base, speed)
      .isReversing(true)
      .repeats(-1)
      .ranges(new float[]{0f,0f,360f * 2f});
    
    //for scale
    ContinuousBehaviorBuilder sb = new ContinuousBehaviorBuilder(base, speed)
      .isReversing(true)
      .repeats(-1)
      .ranges(new float[]{2f,2f,0f});

    //for color
    ContinuousBehaviorBuilder cb = new ContinuousBehaviorBuilder(base, speed)
      .isReversing(true)
      .repeats(-1)
      .ranges(new float[]{0f,0f,0f,.8f});

    //for text
    TextBuilder textBuilder = new TextBuilder()
        //.font("Gill Sans")
        .constrainByHeight(.2f)
        .justify(-1, -1)
        .exactBounds(false)
        .textColor(new Colorf(1f,1f,1f,.7f));

    //linear
    Easing e0 = new EasingLinear(ease);
    Behavior b0 = new BehaviorTranslate(tb.easing(e0));
    Behavior rb0 = new BehaviorRotate(rb.easing(e0));
    Behavior sb0 = new BehaviorScale(sb.easing(e0));
    Behavior cb0 = new BehaviorColor(cb.easing(e0));
    addGeom(textBuilder.text("linear").anchor(transGeoms[0].translate).build());
    Scheduler.getInstance().attachGeom(b0, transGeoms[0]);
    Scheduler.getInstance().attachGeom(rb0, rotGeoms[0]);
    Scheduler.getInstance().attachGeom(sb0, scaleGeoms[0]);
    Scheduler.getInstance().attachGeom(cb0, colorGeoms[0]);

    //sine
    Easing e1 = new EasingSine(ease);
    BehaviorTranslate b1 = new BehaviorTranslate(tb.easing(e1));
    Behavior rb1 = new BehaviorRotate(rb.easing(e1));
    Behavior sb1 = new BehaviorScale(sb.easing(e1));
    Behavior cb1 = new BehaviorColor(cb.easing(e1));
    addGeom(textBuilder.text("sine").anchor(transGeoms[1].translate).build());
    Scheduler.getInstance().attachGeom(b1, transGeoms[1]);
    Scheduler.getInstance().attachGeom(rb1, rotGeoms[1]);
    Scheduler.getInstance().attachGeom(sb1, scaleGeoms[1]);
    Scheduler.getInstance().attachGeom(cb1, colorGeoms[1]);

    //bounce
    Easing e2 = new EasingBounce(ease);
    BehaviorTranslate b2 = new BehaviorTranslate(tb.easing(e2));
    //b2.attachGeom(transGeoms[2]);
    Behavior rb2 = new BehaviorRotate(rb.easing(e2));
    //rb2.attachGeom(rotGeoms[2]);
    Behavior sb2 = new BehaviorScale(sb.easing(e2));
    //sb2.attachGeom(scaleGeoms[2]);
    Behavior cb2 = new BehaviorColor(cb.easing(e2));
    //cb2.attachGeom(colorGeoms[2]);
    addGeom(textBuilder.text("bounce").anchor(transGeoms[2].translate).build());
    Scheduler.getInstance().attachGeom(b2, transGeoms[2]);
    Scheduler.getInstance().attachGeom(rb2, rotGeoms[2]);
    Scheduler.getInstance().attachGeom(sb2, scaleGeoms[2]);
    Scheduler.getInstance().attachGeom(cb2, colorGeoms[2]);

    //elastic
    Easing e3 = new EasingElastic(ease);
    BehaviorTranslate b3 = new BehaviorTranslate(tb.easing(e3));
    //b3.attachGeom(transGeoms[3]);
    Behavior rb3 = new BehaviorRotate(rb.easing(e3));
    //rb3.attachGeom(rotGeoms[3]);
    Behavior sb3 = new BehaviorScale(sb.easing(e3));
    //sb3.attachGeom(scaleGeoms[3]);
    Behavior cb3 = new BehaviorColor(cb.easing(e3));
    //cb3.attachGeom(colorGeoms[3]);
    addGeom(textBuilder.text("elastic").anchor(transGeoms[3].translate).build());
Scheduler.getInstance().attachGeom(b3, transGeoms[3]);
    Scheduler.getInstance().attachGeom(rb3, rotGeoms[3]);
    Scheduler.getInstance().attachGeom(sb3, scaleGeoms[3]);
    Scheduler.getInstance().attachGeom(cb3, colorGeoms[3]);

    //polynomial
    Easing e4 = new EasingPolynomial(ease, 5);
    BehaviorTranslate b4 = new BehaviorTranslate(tb.easing(e4));
    Behavior rb4 = new BehaviorRotate(rb.easing(e4));
    Behavior sb4 = new BehaviorScale(sb.easing(e4));
    Behavior cb4 = new BehaviorColor(cb.easing(e4));
    //cb4.attachGeom(colorGeoms[4]);
    addGeom(textBuilder.text("quintic").anchor(transGeoms[4].translate).build());
Scheduler.getInstance().attachGeom(b4, transGeoms[4]);
    Scheduler.getInstance().attachGeom(rb4, rotGeoms[4]);
    Scheduler.getInstance().attachGeom(sb4, scaleGeoms[4]);
    Scheduler.getInstance().attachGeom(cb4, colorGeoms[4]);

    //random spline
    Easing e5 = new EasingSpline(ease, 15, 0, 1);
    BehaviorTranslate b5 = new BehaviorTranslate(tb.easing(e5));
    Behavior rb5 = new BehaviorRotate(rb.easing(e5));
    Behavior sb5 = new BehaviorScale(sb.easing(e5));
    Behavior cb5 = new BehaviorColor(cb.easing(e5));
    addGeom(textBuilder.text("spline").anchor(transGeoms[5].translate).build());
Scheduler.getInstance().attachGeom(b5, transGeoms[5]);
    Scheduler.getInstance().attachGeom(rb5, rotGeoms[5]);
    Scheduler.getInstance().attachGeom(sb5, scaleGeoms[5]);
    Scheduler.getInstance().attachGeom(cb5, colorGeoms[5]);

    //random points
    Easing e6 = new EasingPoints(ease, 10, 0, 1);
    BehaviorTranslate b6 = new BehaviorTranslate(tb.easing(e6));
    Behavior rb6 = new BehaviorRotate(rb.easing(e6));
    Behavior sb6 = new BehaviorScale(sb.easing(e6));
    Behavior cb6 = new BehaviorColor(cb.easing(e6));
    addGeom(textBuilder.text("points").anchor(transGeoms[6].translate).build());
Scheduler.getInstance().attachGeom(b6, transGeoms[6]);
    Scheduler.getInstance().attachGeom(rb6, rotGeoms[6]);
    Scheduler.getInstance().attachGeom(sb6, scaleGeoms[6]);
    Scheduler.getInstance().attachGeom(cb6, colorGeoms[6]);

    GeomSimpleLine line1 = new GeomSimpleLine(
      new Point3f(-1f, -1.5f, 0f),
      new Point3f(+0f, +3f, 0f));
    line1.setColor(1f,1f,1f,1f);
    GeomSimpleLine line2 = new GeomSimpleLine(
      new Point3f(+2.2f, -1.5f, 0f),
      new Point3f(0f, +3f, 0f));
    line2.setColor(1f,1f,1f,1f);

    addGeom(line1);
    addGeom(line2);
  }
}
