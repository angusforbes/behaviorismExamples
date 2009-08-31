package examples.text;

import behaviorism.Behaviorism;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.text.GeomSimpleText;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import java.awt.Font;

public class WorldSimpleText extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldSimpleText());
  }

  public void setUpWorld()
  {
    setColor(.2f, .2f, .2f);
    simpleTextDemo();
  }

  /**
   * Demonstrates the use of GeomSimpleText, which uses no level-of-detail, but looks great
   * as long as the z position doesn't change drastically.
   *
   * Things to investigate:
   * a) is CONTINUE not working for BehaviorRotate???
   * b) is the State not being inhereted properly? Do we need to explicitly
   * add Geoms to a layer to get state? (or set the default Layer's state?)
   * c) seems to stutter slightly when a new GeomText, or textrenderer is added
   * to scene graph? 
   */
  public void simpleTextDemo()
  {
    long startTime = Utils.nowPlusMillis(500L);
    for (int i = 0; i < 75; i++)
    {
      GeomSimpleText g = new GeomSimpleText(
        false,
        Utils.randomInt(-100, 300), Utils.randomInt(-100, 300),
        Utils.randomString(1,3), "Arial", Font.PLAIN, 72f);

      //      g.setMargins(1f, 0f, 2f, 0f);

//      TextBuilder builder = new TextBuilder(Utils.randomString(3,10)).
//        anchor(Utils.randomInt(-100, 300), Utils.randomInt(-100, 300)).
//        font("Gill Sans", Font.ITALIC, 45);
//
//      Geom g = builder.build();
      
      g.setColor(Colorf.newRandomColor(.3f, .7f, .6f));

      g.getState().BLEND = true;
      g.getState().DEPTH_TEST = true;

      addGeom(g, startTime, 0L + (i * 100L));

      /*
      BehaviorRotate.rotate(g,
        startTime, Utils.randomLong(3000, 10000), LoopEnum.LOOP,
        new Point3f(0f, 0f, 360f));
      
      BehaviorTranslate.translate(g,
        startTime, Utils.randomLong(20000, 30000), LoopEnum.REVERSE,
        new Point3f(0f, 0f, 3f));
       */
    }

    /*
    BehaviorRotate.rotate(
      this,
      startTime, 10000L, LoopEnum.LOOP,
      new Point3f(360f, 0f, 360f * 3)
      );
     */
  }
}
