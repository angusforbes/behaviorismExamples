/* BehaviorCounter.java ~ Aug 18, 2008 */

package examples.sequences;

import behaviorism.behaviors.BehaviorPulse;
import behaviorism.behaviors.geom.GeomUpdater;
import behaviorism.geometry.Geom;
import behaviorism.geometry.GeomRect;
import behaviorism.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Point3f;
//hj
/**
 *
 * @author angus
 */
public class BehaviorPursuerEvaderModule extends BehaviorPulse implements GeomUpdater //BehaviorGeomDiscrete
{
  List<Geom> pursuers = new ArrayList<Geom>();
  List<Geom> evaders = new ArrayList<Geom>();
  
  public BehaviorPursuerEvaderModule(long startTime, long pulse)
  {
    super(startTime, pulse);
  }
  
  @Override
  public void updateGeom(Geom geom)
  {
    System.out.println("in updateGeom!");

    GeomRect rect = new GeomRect(new Point3f(), .1f, .1f);

    if (Utils.random() > .5)
    {
      pursuers.add(rect);
      //BehaviorPursuer bp = new BehaviorPursuer(
    //  BehaviorTranslate.translate(rect, Utils.now(), 10000L, new Point3f(-3f, 0f, 0f));
      rect.setColor(1f,0f,0f,1f);
    }
    else
    {
      evaders.add(rect);
      //BehaviorPursuer bp = new BehaviorPursuer(
     // BehaviorTranslate.translate(rect, Utils.now(), 10000L, new Point3f(3f, 0f, 0f));
      rect.setColor(0f,0f,1f,1f);
    }

    geom.addGeom(rect);
  }



}
