/* BehaviorCoilCut.java (created on Aug 3, 2008) */

package examples.fbos;

import behaviorism.behaviors.BehaviorRange;
import behaviorism.behaviors.geom.ContinuousBehaviorBuilder;
import behaviorism.behaviors.geom.GeomUpdater;
import behaviorism.geometry.Geom;

/**
 *
 * @author Angus Forbes
 */
public class BehaviorScatterLightPos extends BehaviorRange implements GeomUpdater
{
  GeomPhotoScatterFBO geom;
  int whichLight = 1;
  
  public static BehaviorScatterLightPos changeLightPos(
    GeomPhotoScatterFBO geom,
    long startTime,
    long lengthMS,
    float rangeX, float rangeY)
  {

    ContinuousBehaviorBuilder cbb = new ContinuousBehaviorBuilder(startTime, lengthMS).
      isReversing(true).repeats(100).
      ranges(new float[]
      {
        rangeX, rangeY
      });

    BehaviorScatterLightPos b = new BehaviorScatterLightPos(geom, 1, cbb);

    b.attachGeom(geom);

    return b;

  }


  public static BehaviorScatterLightPos changeLightPos2(
    GeomPhotoScatterFBO geom,
    long startTime,
    long lengthMS,
    float rangeX, float rangeY)
  {

     ContinuousBehaviorBuilder cbb = new ContinuousBehaviorBuilder(startTime, lengthMS).
      repeats(100).isReversing(true).
      ranges(new float[]
      {
        rangeX, rangeY
      });

    BehaviorScatterLightPos b = new BehaviorScatterLightPos(geom, 2, cbb);

    b.attachGeom(geom);

    return b;

  }
  public BehaviorScatterLightPos(GeomPhotoScatterFBO geom, int whichLight, ContinuousBehaviorBuilder builder)
  {
    super(builder.startTime, builder.lengthMS, builder.ranges);
    this.isLooping = builder.isLooping;
    this.isReversing = builder.isReversing;
    this.easing = builder.easing;
    this.repeats = builder.repeats;
    this.geom = geom;
    this.whichLight = whichLight;
  }

  public void updateGeom(Geom g)
  {
    /*
    if (whichLight == 1)
    {
    geom.lightX += offsets[0];
    geom.lightY += offsets[1];
    }
    else
    {
    geom.light2X += offsets[0];
    geom.light2Y += offsets[1];
    }
    */
   // System.err.println("time = " + System.currentTimeMillis());
    if (whichLight == 1)
    {
    WorldPhotoScatter.light1_x += offsets[0];
    WorldPhotoScatter.light1_y += offsets[1];
    }
    else
    {
    WorldPhotoScatter.light2_x += offsets[0];
    WorldPhotoScatter.light2_y += offsets[1];
    }
  }
}

/*
public BehaviorCoilCut(long startNano, long lengthMS, LoopEnum loopBehavior,
float changex, CoilNode node)
{
init(startNano, lengthMS, loopBehavior, 0f, changex, 0f, node);
}
 */
/*
public void init(long startNano, long lengthMS, LoopEnum loopBehavior, 
float minx, float maxx, 
float startPercent, CoilNode node)
{
if (startPercent < 0f || startPercent > 1f)
{
System.err.println("startPercent must be between 0f and 1f!");
}

this.node = node;

BehaviorismDriver.renderer.currentWorld.registerBehavior(this);
this.startPercent = startPercent;

this.lengthNano = Utils.millisToNanos(lengthMS);

this.startTime = startNano; //nothing will happen before the startTime

//determine what will happen after startTime... ie for startPercent != 0f
this.startNano = this.startTime - (long)(this.lengthNano * startPercent);
this.lastCheck = (long)(this.lengthNano * startPercent);

this.range_x = maxx - minx;

this.loopBehavior = loopBehavior;

setAccelerationPoints();

relativeStartNano = 0L;
relativeEndNano = lengthNano;
}
 */
/*
public float getOffset()
{
return offset_x;
}

@Override
public void resetOffsets()
{
offset_x = 0f;
}
 */
/*
@Override
public void change(Geom g)
{
node.cut += offset_x;
}
 */
/*
@Override
protected void addToOffsets(float percentage, int direction)
{
offset_x += (range_x * percentage * direction);
}	

@Override
protected void subtractFromOffsets(float percentage, int direction)
{
offset_x -= (range_x * percentage * direction);
}	
 */

