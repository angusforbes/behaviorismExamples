package examples.text;

import behaviorism.Behaviorism;
import behaviorism.behaviors.Scheduler;
import behaviorism.behaviors.geom.BehaviorRotate;
import behaviorism.behaviors.geom.ContinuousBehaviorBuilder;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.text.GeomSimpleText;
import behaviorism.geometry.text.GeomText;
import behaviorism.geometry.text.TextBuilder;
import behaviorism.utils.RenderUtils;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import java.awt.Font;

public class WorldSimpleTextLOD extends World {

  public static void main(String[] args) {
    Behaviorism.installWorld(new WorldSimpleTextLOD());
  }

  public void setUpWorld() {
    // Utils.sleep(2000);
    //getState().BLEND = false;
    setColor(.2f, .2f, .2f);
    simpleTextDemo();
  }

  /**
   * Demonstrates the use of GeomeText, which uses level-of-detail at various z positions
   *
   * Things to investigate:
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

      GeomText g = randomText();

      g.getState().BLEND = true;
      g.getState().DEPTH_TEST = true;

      addGeom(g, startTime, 0L + (i * TIME_BETWEEN_ADDS));

      BehaviorRotate br = new BehaviorRotate(
	      new ContinuousBehaviorBuilder(startTime2, Utils.randomLong(1000, 20000)).repeats(999).isReversing(true).ranges(0f, 0f, 360f));
      Scheduler.getInstance().attachGeom(br, g);
    }

  }

  public GeomText randomText() {
    return new TextBuilder(Utils.randomString(1, 3)).
      font("Consolas", Font.PLAIN).
      constrainByWidth(Utils.randomFloat(.25f, 1f)).
      exactBounds(true).
      anchor(Utils.randomFloat(-2f, 2f), Utils.randomFloat(-1.5f, 1.5f), 0f).
      textColor(Colorf.newRandomColor(.3f, .7f, .6f)).
      build();
  }
}
