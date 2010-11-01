/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package examples.fbos;

import behaviorism.renderers.Renderer;
import behaviorism.utils.RenderUtils;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import javax.swing.JApplet;

/**
 *
 * @author angus
 */
public class ScatterApplet extends JApplet {

  boolean needsToBeStarted = true;
  World world = null;

  public void init()
    {
      System.err.println("in MainApplet : initialize applet...()");

      this.world = new WorldPhotoScatter(this);

//      String version = getParameter("version");
//      System.err.println("The version is : " + version);
//
//      if (version == null)
//      {
//        version = "nightToBlueVision";
//      }

      // TODO start asynchronous download of heavy resources
    }

    public void start()
    {
      System.err.println("in MainApplet : start()");

      if (needsToBeStarted == true)
      {
        world.setUpWorld(this);
        
        needsToBeStarted = false;
      }

    }

    public void stop()
    {
      System.err.println("in MainApplet : stop()");
//      Renderer.getInstance().animator.stop();
    }

    public void destroy()
    {
      System.err.println("in MainApplet : destroy()");
      //yes we need to really destroy everything here!
      Renderer.getInstance().animator.stop();


      System.err.println("in MainApplet : destroyed?");

    }

}
