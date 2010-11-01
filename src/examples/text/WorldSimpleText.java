package examples.text;

import behaviorism.Behaviorism;
import behaviorism.behaviors.Scheduler;
import behaviorism.behaviors.geom.BehaviorRotate;
import behaviorism.behaviors.geom.ContinuousBehaviorBuilder;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.text.GeomSimpleText;
import behaviorism.utils.RenderUtils;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import java.awt.Font;
import javax.vecmath.Point3f;

public class WorldSimpleText extends World {

  public static void main(String[] args) {
    Behaviorism.installWorld(new WorldSimpleText());
  }

  public void setUpWorld() {
    // Utils.sleep(2000);
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
  public void simpleTextDemo() {
    long startTime = Utils.nowPlusMillis(500L);
    long startTime2 = Utils.nowPlusMillis(7000L);
    int NUM_STRINGS = 100;
    long TIME_BETWEEN_ADDS = 50L;

    int www = RenderUtils.getViewport()[2] - 50;
    int hhh = RenderUtils.getViewport()[3] - 50;
    for (int i = 0; i < NUM_STRINGS; i++) {

      GeomSimpleText g = new GeomSimpleText(
	      true,
	      Utils.randomInt(50, www),
	      Utils.randomInt(50, hhh),
	      Utils.randomString(1, 3), "Consolas", Font.PLAIN, Utils.randomFloat(8f, 48f));

      g.setColor(Colorf.newRandomColor(.3f, .7f, .6f));

      g.getState().BLEND = true;
      g.getState().DEPTH_TEST = true;

      addGeom(g, startTime, 0L + (i * TIME_BETWEEN_ADDS));

      BehaviorRotate br = new BehaviorRotate(
	      new ContinuousBehaviorBuilder(startTime2, Utils.randomLong(1000, 20000)).repeats(999).isReversing(true).ranges(0f, 0f, 360f));
      Scheduler.getInstance().attachGeom(br, g);
    }

  }
}
