package examples.videos;

import behaviorism.geometry.media.GeomVideo;
import behaviorism.textures.TextureVideo;
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
//import behaviorism.textures.TextureVideo;
import behaviorism.utils.FileUtils;
import behaviorism.worlds.World;
import java.util.List;
import javax.vecmath.Point3f;
import org.grlea.log.SimpleLogger;
import static behaviorism.utils.Utils.*;
import static behaviorism.utils.RenderUtils.*;

public class WorldSimpleVideo extends World
{


  public static final SimpleLogger log = new SimpleLogger(WorldSimpleVideo.class);

  public static void main(String[] args)
  {
    System.err.println("in WorldSimpleVideo : main");
    Behaviorism.installWorld(new WorldSimpleVideo());
    System.err.println("out WorldSimpleVideo : main");
  }

  public void setUpWorld()
  {
    setColor(0f, 0f, 0f, 0f);
    getState().BLEND = true;
    testSimpleVideo();
  }

  public void testSimpleVideo()
  {
    log.entry("in testSimpleVideo()");

    //set up timing...
    long base = nowPlusMillis(3000L);

    //set up grid of photos...
    int cols = 3;
    int rows = 3;

    //TextureVideo vid = new TextureVideo(FileUtils.toURI("data/videos/sheep.mov"));
    TextureVideo vid2 = new TextureVideo(FileUtils.toURI("resources/data/videos/testvid.flv"));
    //TextureVideo131 vid2 = new TextureVideo131(FileUtils.toURI("resources/data/videos/sheep.mov"));
    TextureManager.getInstance().addTexture(vid2);
   // TextureVideo vid3 = new TextureVideo(FileUtils.toURI("data/videos/sheep.mov"));
   // TextureVideo vid4 = new TextureVideo(FileUtils.toURI("data/videos/sheep.mov"));
    //TextureVideo vid = new TextureVideo(FileUtils.toURI("data/videos/testvid.flv"));
    //TextureVideo vid = new TextureVideo(FileUtils.toURI("data/videos/mitchell.flv"));
//
//    GeomVideo gv4 = new GeomVideo(new Point3f(-2f,2f,0f), 3f, vid4);
//    gv4.setColor(1f, 1f, 1f, 1f);
//    addGeom(gv4);
//    vid4.bounce(3.5, 3.7);
//
//    GeomVideo gv3 = new GeomVideo(new Point3f(2f,-2f,0f), 3f, vid3);
//    gv3.setColor(1f, 1f, 1f, 1f);
//    addGeom(gv3);
//    vid3.bounce(4.5, 4.7);

    GeomVideo gv2 = new GeomVideo(new Point3f(-2f,-2f,0f), 3f, vid2);
    gv2.setColor(1f, 1f, 1f, 1f);
    addGeom(gv2);
    vid2.setVolume(1);
    vid2.play();

//    for(int i = 0; i < 30; i++)
//    {
//      vid2.setCurrentTime(vid2.getCurrentTime() + .1);
//      Utils.sleep(100);
//    }
//
//    for(int i = 0; i < 30; i++)
//    {
//      vid2.setCurrentTime(vid2.getCurrentTime() - .1);
//      Utils.sleep(100);
//    }

    /*
    GeomVideo gv = new GeomVideo(new Point3f(), 3f, vid);
    gv.setColor(1f, 1f, 1f, 1f);
    addGeom(gv);

    vid.bounce(3, 3.2);

    System.out.println("vid vol = " + vid.getVolume());
    Utils.sleep(3000);
    vid2.setVolume(1);
    vid.setVolume(0);
    */
    /*
    for (int i = 0; i < 2; i++)
    {
      Utils.sleep(5000);

      vid.setVideo(FileUtils.toURI("data/videos/mitchell.flv"));
      vid.bounce(3, 3.2);

      Utils.sleep(5000);

      vid.setVideo(FileUtils.toURI("data/videos/sheep.mov"));
      vid.bounce(3, 3.2);
    }
     */

    //System.gc();
    //vid.disposeTexture();

    /*
    vid.setRate(3);
    // vid.mp.setStopTime(5);
    while(true)
    {
    Utils.sleep(10);
    System.out.println("isPlaying = " + vid.isPlaying());
    if (vid.isPlaying() == false)
    {
    vid.mp.setStopTime(15);
    vid.play();
    vid.setRate(-2);
    break;
    }
    }
     */
//    vid.play();
//    vid.setRate(-2);
//    vid.mp.setMediaTime(5.7);
//    Utils.sleep(6000);
//    System.out.println("isPlaying = " + vid.isPlaying());
//
//    vid.play();
//    vid.setRate(2);
//    vid.mp.setMediaTime(0);
    //vid.mp.setStopTime(0);
    //vid.play();
    /*
    Utils.sleep(1000);
    //vid.pause();
    //Utils.sleep(200);
    System.exit(1);
    Utils.sleep(2000);
    vid.bounce();
    Utils.sleep(200);
    System.out.println("isPlaying = " + vid.isPlaying());
    Utils.sleep(3000);
    vid.pause();
    Utils.sleep(2000);
    vid.bounce();
     */
    /*
    Rectangle2D.Float worldRect = getScreenBoundsInWorldCoords();

    float minx = worldRect.x;
    float miny = worldRect.y;
    float endw = worldRect.width;
    float endh = worldRect.height;
    float incx = endw / (cols);
    float incy = endh / (rows);
    int idx = 0;

    for (int i = 0; i < cols; i++)
    {
    for (int j = 0; j < rows; j++)
    {
    Point3f anchor = new Point3f(minx + i * incx, miny + j * incy, 0f);

    GeomImage gi = new GeomImage(anchor, incx, incy, images.get(idx));

    BehaviorLauncher bl = new BehaviorLauncher(nanoPlusMillis(base, 0L, 1000L), gi, images);
    bl.schedule(true);
    }
    }
     */
  }

  /*
  class BehaviorLauncher extends BehaviorSimple
  {

    Geom geom;
    List<TextureImage> images;

    public BehaviorLauncher(long st, Geom g, List<TextureImage> images)
    {
      super(st);
      this.geom = g;
      this.images = images;
    }

    @Override
    public void update()
    {
      geom.setColor(1f, 1f, 1f, 1f);
      geom.setScaleAnchor(geom.w / 2f, geom.h / 2f, 0f);
      geom.setScale(0f, 0f, 0f);
      getWorld().addGeom(geom, nextTime, -500L); //, -1500L);

      int swaps = -1;
      BehaviorScale bs = BehaviorScale.scaleTo(geom, nextTime, 1000L,
        new Point3f(1f, 1f, 1f));
      bs.isReversing = true;
      bs.repeats = -1;
      bs.easing = new EasingPolynomial(5); //(5, 0f, 1.2f);

      BehaviorSwapImage bsi = new BehaviorSwapImage(
        nextTime, 2000L, images);
      bsi.repeats = -1;
      bsi.attachGeom(geom);

      BehaviorSpeed bspeed = new BehaviorSpeed(nextTime, 2000L, 1.1f);
      bspeed.repeats = 10;
      bspeed.attachBehavior(bsi);
      bspeed.attachBehavior(bs);
      bspeed.attachBehavior(bspeed);

    }
  }

  class BehaviorSwapImage extends BehaviorPulse implements GeomUpdater
  {

    int swaps;
    List<TextureImage> images;

    public BehaviorSwapImage(long st, long pulse, List<TextureImage> images)
    {
      super(st, pulse);
      this.images = images;
    }

    public void updateGeom(Geom g)
    {
      ((GeomImage) g).setTexture(images.get(randomInt(0, images.size() - 1)));

    }
  }
  */
}


