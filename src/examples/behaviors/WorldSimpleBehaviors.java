package examples.behaviors;

import examples.sequences.*;
import behaviorism.Behaviorism;
import behaviorism.behaviors.Behavior;
import behaviorism.behaviors.BehaviorDiscrete;
import behaviorism.behaviors.BehaviorInterpolated;
import behaviorism.behaviors.BehaviorPulse;
import behaviorism.behaviors.BehaviorRange;
import behaviorism.behaviors.BehaviorSwitch;
import behaviorism.behaviors.behavior.BehaviorSpeed;
import behaviorism.behaviors.behavior.BehaviorUpdater;
import behaviorism.behaviors.easing.Easing;
import behaviorism.behaviors.easing.Easing.EasingEnum;
import behaviorism.behaviors.easing.EasingBounce;
import behaviorism.behaviors.easing.EasingPoints;
import behaviorism.behaviors.geom.BehaviorActivateGeom;
import behaviorism.behaviors.geom.BehaviorDeactivateGeom;
import behaviorism.behaviors.geom.BehaviorTranslate;
import behaviorism.behaviors.geom.ContinuousBehaviorBuilder;
import behaviorism.behaviors.geom.GeomUpdater;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.Geom;
import behaviorism.geometry.GeomPoint;
import behaviorism.geometry.GeomRect;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import javax.vecmath.Point3f;

public class WorldSimpleBehaviors extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldSimpleBehaviors());
  }

  public void setUpWorld()
  {
    setColor(.2f, .2f, .2f);

    testTranslateBehavior();

    //testSimpleBehaviors();
    //testCustomBehaviors();
    //testSimpleInterpolaters();
    //testSimpleRangeInterpolaters();
    //testActivate();
    //testDiscrete();
    testChangeSpeed();
  }


  public void testTranslateBehavior()
  {
    GeomRect g = new GeomRect(new Point3f(0f, 0f, 0f), .1f, .1f);
    g.setColor(1f, 0f, 0f, 1f);
    addGeom(g);

    long base = Utils.nowPlusMillis(1000);

    //translate it to the right one time...
    BehaviorTranslate b1 = new BehaviorTranslate(
      new ContinuousBehaviorBuilder(base, 1000L).isReversing(true).ranges(new float[]{2f,0f,0f}));
    b1.attachGeom(g);

    //then translate it back and forth forever...
    BehaviorTranslate b2 = new BehaviorTranslate(
      new ContinuousBehaviorBuilder(base + Utils.millisToNanos(1000L), 2000L)
      .isReversing(true).ranges(new float[]{-4f,0f,0f}).repeats(-1));
    b2.attachGeom(g);


  }

  public void testChangeSpeed()
  {
    GeomRect g = new GeomRect(new Point3f(-1f, 0f, 0f), 1f, 1f);
    addGeom(g);

    long base = Utils.nowPlusMillis(1000);
    BehaviorTranslate bd = new BehaviorTranslate(
      new ContinuousBehaviorBuilder(base, 1000L).isReversing(true).ranges(new float[]{2f,0f,0f}).repeats(3));
    bd.attachGeom(g);

    BehaviorSpeed bs = new BehaviorSpeed(Utils.nanoPlusMillis(base, 2500L), 6f);
    bs.attachBehavior(bd);
    Utils.sleep(8000);
    System.out.println("g = " + g);

  }

  public void testDiscrete()
  {
    long base = Utils.nowPlusMillis(1000);
    BehaviorDiscrete bd = new BehaviorDiscrete(base, new long[]{0, 100, 1000, 3000});
    bd.isReversing = true;
    bd.repeats = 10;
    bd.schedule(false);

    BehaviorSpeed bs = new BehaviorSpeed(Utils.nanoPlusMillis(base, 2000L), 2f);
    bs.attachBehavior(bd);

  }
  public void testActivate()
  {
    GeomRect g = new GeomRect(new Point3f(), 1f, 1f);
    addGeom(g, false);

    BehaviorActivateGeom.activateAtMillis(g, Utils.now(), 5000L);
    BehaviorDeactivateGeom.deactivateAtMillis(g, Utils.now(), 10000L);
  }

  public void testRawEasing()
  {
    Easing e = new EasingBounce();

    e.ease = EasingEnum.OUT;
    float inc = .05f;
    for (float f = 0f; f <= 1.01f; f+=inc)
    {
      e.getPercentage(f);
    }

    /*
    System.out.println("");
    for (float f = 0f; f <= 1.01f; f+=inc)
    {
      e.getPercentage(f, 2);
    }
    */
   // System.exit(0);
  }

  public void testSimpleRangeInterpolaters()
  {
    long base = Utils.nowPlusMillis(1000);

    GeomRect g = new GeomRect(-1f, -1f, 0f, .1f, .1f);
    addGeom(g);
    g.setColor(1f,0f,0f,1f);
    //Behavior bc = new BehaviorSimpleRangeTranslate(Utils.now(), 1500L, new float[]{1f,1f});
    BehaviorRange bc = new BehaviorTranslate(base, 10000L, new Point3f(2f, 0f, 0f));
    bc.isReversing = true; //false;
    bc.repeats = 10;
    //bc.easing = new EasingBounce(35, .9f);
    //bc.easing = new EasingElastic();
    bc.easing = new EasingPoints(
      new double[]{0f, .2f, .4f, .6f, .8f, 1f},
      new double[]{0f, .3f, .8f, .3f, .2f, 1f}
    );
    bc.easing.ease = Easing.EasingEnum.INOUT;
    bc.isLooping = false;
    bc.attachGeom(g);

    GeomPoint p1 = new GeomPoint(new Point3f(-1f, -1f, 0f));
    GeomPoint p2 = new GeomPoint(new Point3f(+1f, -1f, 0f));
    addGeom(p1);
    addGeom(p2);


    GeomRect g2 = new GeomRect(-1f, -.5f, 0f, .1f, .1f);
    addGeom(g2);
    g2.setColor(0f,1f,0f,1f);
    //Behavior bc = new BehaviorSimpleRangeTranslate(Utils.now(), 1500L, new float[]{1f,1f});
    BehaviorRange bc2 = new BehaviorTranslate(base, 10000L, new Point3f(2f, 0f, 0f));
    bc2.isReversing = true; //false;
    bc2.repeats = 10;
    //bc.easing = new EasingBounce(35, .9f);
    //bc.easing = new EasingElastic();
    bc2.easing = new EasingPoints(
      new double[]{0f, .2f, .4f, .6f, .8f, 1f},
      new double[]{0f, .3f, .8f, .3f, .2f, 1f}
    );
    bc2.easing.ease = Easing.EasingEnum.OUTIN;
    bc2.isLooping = false;
    bc2.attachGeom(g2);

  }
  public void testSimpleInterpolaters()
  {
    GeomRect g = new GeomRect(-2f, -1f, 0f, 1f, 1f);
    addGeom(g);
    g.setColor(1f,0f,0f,1f);
    BehaviorInterpolated bc = new BehaviorTranslate(Utils.nowPlusMillis(0L), 500L, new Point3f(3f,0f,0f));
   // bc.setStartingPercentage(.1f);
    bc.isReversing = true;
    bc.repeats = 10;
    bc.attachGeom(g);
    GeomPoint p1 = new GeomPoint(new Point3f(-1f, -1f, 0f));
    GeomPoint p2 = new GeomPoint(new Point3f(+1f, -1f, 0f));
    addGeom(p1);
    addGeom(p2);
  }

  public void testSimpleBehaviors()
  {
    GeomRect g2 = new GeomRect(-2f, -1f, 0f, 1f, 1f);
    addGeom(g2);

    Behavior bc = new BehaviorToggleColor(Utils.now(), 200L, false);

    bc.attachGeom(g2);

    BehaviorToggleSpeed bs = new BehaviorToggleSpeed(Utils.nowPlusMillis(5000L), 2000L);
    // Utils.sleep(1000);
    bs.attachBehavior(bc);
  }

  public void testCustomBehaviors()
  {
    GeomRect g2 = new GeomRect(-2f, -1f, 0f, 1f, 1f);
    addGeom(g2);

    BehaviorColor bc = new BehaviorColor(Utils.now(), 1000L);

   // Utils.sleep(1000);
    bc.attachGeom(g2);

    BehaviorAccelerateSpeed bs = new BehaviorAccelerateSpeed(Utils.nowPlusMillis(5000L), 1000L);
    // Utils.sleep(1000);
    bs.attachBehavior(bc);
  }

  class BehaviorSimpleRangeTranslate extends BehaviorRange implements GeomUpdater
  {
    public BehaviorSimpleRangeTranslate(long startTime, long lengthMS, float[] ranges)
    {
      //super(startTime, lengthMS, ranges);
      super(startTime, lengthMS, new float[]{1f, 1f});
      //isReversing = true;
    }

    public void updateGeom(Geom g)
    {
      //System.out.println("range[0] = " + ranges[0]);
      //System.out.println("offset[0] = " + getOffsets()[0]);
      System.out.println("offsetPercentage = " + offsetPercentage +
        " / rawPercentage = " + percentage);
      //g.translateX(offsetPercentage);
      g.translateX(getOffsets()[0]);
      g.translateY(getOffsets()[1]);
    }
  }

  class BehaviorSimpleTranslate extends BehaviorInterpolated implements GeomUpdater
  {
    float xdist;
    public BehaviorSimpleTranslate(long startTime, long lengthMS, float xdist)
    {
      super(startTime, lengthMS);
      this.xdist = xdist;
    }

    public void updateGeom(Geom g)
    {
      System.out.println("offsetPercentage = " + offsetPercentage +
        " / rawPercentage = " + percentage);
      g.translateX(offsetPercentage);
      if (timeToLoop == true)
      {
        //g.translateX(-xdist);
      }
    }
  }

  class BehaviorToggleColor extends BehaviorSwitch implements GeomUpdater
  {

    public BehaviorToggleColor(long startTime, long pulse, boolean on)
    {
      super(startTime, pulse);
      setOn(on);
    }

    public void updateGeom(Geom g)
    {
      if (on)
      {
        g.setColor(1f, 0f, 0f, 1f);
      }
      else
      {
        g.setColor(0f, 0f, 1f, 1f);
      }
    }
  }

  class BehaviorColor extends BehaviorPulse implements GeomUpdater
  {

    public BehaviorColor(long startTime, long pulse)
    {
      super(startTime, pulse);
    }

    public void updateGeom(Geom g)
    {
      g.setColor(new Colorf());
    }
  }



  class BehaviorToggleSpeed extends BehaviorSwitch implements BehaviorUpdater
  {

    public BehaviorToggleSpeed(long startTime, long pulse)
    {
      super(startTime, pulse);
      on();
    }

    public void updateBehavior(Behavior b)
    {
      BehaviorPulse bp = ((BehaviorPulse) b);

      if (on)
      {
        bp.setPulse(200L);
      }
      else
      {
        bp.setPulse(20L);
      }
    }
  }

  class BehaviorAccelerateSpeed extends BehaviorPulse implements BehaviorUpdater
  {

    public BehaviorAccelerateSpeed(long startTime, long pulse)
    {
      super(startTime, pulse);
    }

    int dir = -1;
    public void updateBehavior(Behavior b)
    {
      System.out.println("in updateBehavior!!!!!!");
      BehaviorPulse bp = ((BehaviorPulse) b);

      bp.setPulse(bp.getPulse() + (1L * dir));
      if (bp.getPulse() <= 0L || bp.getPulse() >= 200L)
      {
        dir *= -1;
      }


      
    }
  }

  public void sequenceDemo()
  {
    //  BehaviorPursuerEvaderModule bpem = new BehaviorPursuerEvaderModule(
    //  new DiscreteBehaviorBuilder(Utils.now(), 0L, 3000L).loop(LoopEnum.LOOP)  );
    BehaviorPursuerEvaderModule bpem = new BehaviorPursuerEvaderModule(Utils.nowPlusMillis(2000), 500);

    //attachBehavior(bpem);
  }
}
