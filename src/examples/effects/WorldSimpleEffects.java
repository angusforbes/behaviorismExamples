package examples.effects;

import behaviorism.Behaviorism;
import behaviorism.geometry.GeomRect;
import behaviorism.utils.EffectUtils;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import javax.vecmath.Point3f;

public class WorldSimpleEffects extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldSimpleEffects());
  }

  public void setUpWorld()
  {
    setColor(.2f, .2f, .2f);
    zoomInDemo();
  }

  private void zoomInDemo()
  {
    GeomRect gr = new GeomRect(new Point3f(), 1f, 1f);
    gr.setColor(1f,1f,1f,1f);
    addGeom(gr);

    EffectUtils.effectZoomIn(gr, Utils.nowPlusMillis(1000L), 4000L, false);



  }
}
