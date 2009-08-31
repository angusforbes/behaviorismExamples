package examples.images;

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
import behaviorism.utils.FileUtils;
import behaviorism.worlds.World;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.vecmath.Point3f;
import static behaviorism.utils.Utils.*;
import static behaviorism.utils.RenderUtils.*;

public class WorldSimpleImage extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldSimpleImage());
  }

  public void setUpWorld()
  {
    setColor(0f, 0f, 0f, 0f);
    // setColor(1f,1f,1f,1f);
    getState().BLEND = true;
    testSimpleImage();
  }

  public void testSimpleImage()
  {

    //set up timing...
    long base = nowPlusMillis(3000L);

    //set up grid of photos...
    int cols = 3;
    int rows = 3;
    List<TextureImage> images = FileUtils.loadTexturesFromDirectory("data/photos/historical/", 3); //cols * rows

    for (TextureImage image : images)
    {
      TextureManager.getInstance().addTexture(image);
    }
    
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
  }

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

}


