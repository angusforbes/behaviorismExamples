package examples.videos;

import behaviorism.Behaviorism;
import behaviorism.behaviors.BehaviorPulse;
import behaviorism.behaviors.BehaviorSimple;
import behaviorism.behaviors.behavior.BehaviorSpeed;
import behaviorism.behaviors.easing.EasingPolynomial;
import behaviorism.behaviors.geom.BehaviorScale;
import behaviorism.behaviors.geom.GeomUpdater;
import behaviorism.geometry.Geom;
import behaviorism.geometry.media.GeomImage;
import behaviorism.textures.TextureImage;
import behaviorism.textures.TextureManager;
import behaviorism.textures.TextureVideo;
import behaviorism.utils.FileUtils;
import behaviorism.worlds.World;
import java.util.List;
import javax.vecmath.Point3f;
import static behaviorism.utils.Utils.*;
import static behaviorism.utils.RenderUtils.*;

public class WorldVideoScrubbing extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldVideoScrubbing());
  }

  public void setUpWorld()
  {
    setColor(0f, 0f, 0f, 0f);
    // setColor(1f,1f,1f,1f);
    getState().BLEND = true;
    testScrubbing();
  }

  public void testScrubbing()
  {
    //TextureVideo vid2 = new TextureVideo(FileUtils.toURI("data/videos/mitchell.flv"));
    TextureVideo vid2 = new TextureVideo(FileUtils.toURI("resources/data/videos/sheep.mov"));
    TextureManager.getInstance().addTexture(vid2);
    
    GeomVideoScrub gv2 = new GeomVideoScrub(new Point3f(-2f,-2f,0f), 3f, vid2);
    gv2.setColor(1f, 1f, 1f, 1f);
    addGeom(gv2);
    vid2.setVolume(1);
    //vid2.play();
    //Utils.sleep(100);
    
    vid2.bounce(3, 5.2);
    //vid2.bounce();

    //Utils.sleep(1000);
//    vid2.setBalance(-100);
//    for (int i = 0; i < 100; i++)
//    {
//      //vid2.changeBalance(.05f);
//      vid2.setBalance(vid2.getBalance() * -1);
//      Utils.sleep(1000);
//    }
  }

}


