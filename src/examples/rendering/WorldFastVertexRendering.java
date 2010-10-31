/* WorldFastVertexRendering.java ~ Apr 15, 2010 */

package examples.rendering;

import behaviorism.Behaviorism;
import behaviorism.geometry.Geom;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import java.awt.event.KeyEvent;
import java.util.Properties;

/**
 *
 * @author angus
 */
public class WorldFastVertexRendering extends World
{
  public static int numPoints = 300000;

  Geom geom;
  public static void main(String[] args)
  {
    Properties properties = loadPropertiesFile("behaviorism.properties");
    Behaviorism.installWorld(new WorldFastVertexRendering(), properties);
  }

  public void setUpWorld()
  {
    setColor(0f,0f,0f,1f);
    getState().BLEND = true;
    //geom = new GeomRandomPoints();
    //addGeom(geom);
    geom = new GeomVertexBufferObjectTest();
    addGeom(geom);
  }

  public boolean checkKeys(boolean[] keys, boolean[] keysPressing)
  {
    if (keys[KeyEvent.VK_1])
    {
      if (keysPressing[KeyEvent.VK_1] == false)
      {
        keysPressing[KeyEvent.VK_1] = true;

        removeGeom(geom);
        Utils.sleep(100);
        geom = new GeomRandomPoints();
        addGeom(geom);

        return true;
      }
    }

    else if (keys[KeyEvent.VK_2])
    {
      if (keysPressing[KeyEvent.VK_2] == false)
      {
        keysPressing[KeyEvent.VK_2] = true;

        removeGeom(geom);
        Utils.sleep(100);
        geom = new GeomDisplayListTest();
        addGeom(geom);

        return true;
      }
    }

    else if (keys[KeyEvent.VK_3])
    {
      if (keysPressing[KeyEvent.VK_3] == false)
      {
        keysPressing[KeyEvent.VK_3] = true;
        removeGeom(geom);
        Utils.sleep(100);
        geom = new GeomVertexArrayTest();
        addGeom(geom);

        return true;
      }
    }

    else if (keys[KeyEvent.VK_4])
    {
      if (keysPressing[KeyEvent.VK_4] == false)
      {
        keysPressing[KeyEvent.VK_4] = true;
        removeGeom(geom);
        Utils.sleep(100);
        geom = new GeomVertexBufferObjectTest();
        addGeom(geom);

        return true;
      }
    }

    return false;
  }
}
