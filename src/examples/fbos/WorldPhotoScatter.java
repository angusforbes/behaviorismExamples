package examples.fbos;

import behaviorism.Behaviorism;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.Geom;
import behaviorism.geometry.GeomRect;
import behaviorism.geometry.media.GeomImage;
import behaviorism.geometry.media.GeomVideo;
import behaviorism.textures.Texture;
import behaviorism.textures.TextureImage;
import behaviorism.textures.TextureManager;
import behaviorism.textures.TextureVideo;
import behaviorism.utils.FileUtils;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.vecmath.Point3f;
import org.grlea.log.SimpleLogger;

/**
 *
 * @author angus
 */
public class WorldPhotoScatter extends World
{

  float rx, ry, rw, rh;
  public static final SimpleLogger log = new SimpleLogger(WorldPhotoScatter.class);

  public static void main(String[] args)
  {
    Properties properties = loadPropertiesFile("behaviorism.properties");

    World world = new WorldPhotoScatter();
    Behaviorism.installWorld(world, properties);
  }

  public void setUpWorld()
  {
    
    log.entry("in setUpWorld()");
    //setColor(1f,1f,1f,1f);
    setColor(0f, 0f, 0f, 1f);

    getState().DEPTH_TEST = true;
    getState().BLEND = false;

    Rectangle2D.Float r2df = getScreenBoundsInWorldCoords();
    rx = r2df.x;
    ry = r2df.y;
    rw = r2df.width;
    rh = r2df.height;

    try
    {

    //Texture ti = loadOnePhoto();
    Texture ti = new TextureVideo(FileUtils.toURI("data/videos/sheep.mov"), true);
    //TextureVideo ti = new TextureVideo(FileUtils.toURI("data/videos/mitchell.flv"), true);

    log.debugObject("TextureImage ti " , ti);
    TextureManager.getInstance().addTexture(ti);

//    while (ti.texture == null)
//    {
//      Utils.sleep(100);
//      System.err.println("sleeping...");
//    }


      System.err.println("canvas w/h = " + Behaviorism.getInstance().canvasWidth + "/" + Behaviorism.getInstance().canvasHeight);
    testVideoScatterPHOTO(
      Behaviorism.getInstance().canvasWidth / 1, Behaviorism.getInstance().canvasHeight / 1,
      ti);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    log.exit("out setUpWorld()");
  }

  public TextureImage loadOnePhoto()
  {
    List<TextureImage> images = FileUtils.loadTexturesFromDirectory("data/photos/historical/", 10); //cols * rows
    Collections.shuffle(images);
    return images.get(0);
  }

  public Geom testVideoScatterPHOTO(int fboW, int fboH, Texture ti)
  {
    log.entry("in testVideoScatterPHOTO()");
    List<Geom> lights = new ArrayList<Geom>();

//    GeomImage testImage = new GeomImage(new Point3f(), 1f, 1f, ti);
//    addGeom(testImage);
    
    log.debug("making GeomImage from Texture to be used as light source.");
    Geom image;
    System.err.println("ti is a " + ti.getClass());
    if (ti instanceof TextureVideo)
    {
      System.err.println("ti is TextureVideo");
      image = new GeomVideo(new Point3f(-.25f, -.25f, 1f), rw, rh, (TextureVideo) ti);
    }
    else
    {
      System.err.println("ti is TextureImage");
      image = new GeomImage(new Point3f(-.25f, -.25f, 1f), rw, rh, (TextureImage) ti);
    }
      //image.setColor(new Colorf(0f, .3f, 1f, 1f).invert());
      image.setColor(new Colorf(0f, 0f, 0f, 1f).invert()); //for forest
      //image.setColor(new Colorf(1f, 1f, 1f, 1f).invert());
      image.color.a = 1f;
      image.isVisible = false; //true;
      image.getState().DEPTH_TEST = false;
      image.getState().BLEND = true;
      image.setRotateAnchor((new Point3f(.0f, .0f, 0f)));

      lights.add(image);

    log.debug("not using any occluders...");
    //occluders...
    Geom occ = new GeomRect(new Point3f(0f, 0f, 1f), 2f, 2f);
    //  addGeom(occ, false);
    List<Geom> occluders = new ArrayList<Geom>();
    // occluders.add(occ);

    log.debug("making FBO");
    TextureFBOScatter fboScatter = new TextureFBOScatter(fboW, fboH,
      lights, occluders, -10L);
    System.err.println("before added a TextureFBOScatter to the TextureManager... " + TextureManager.getInstance().numActiveTextures());
    TextureManager.getInstance().addTexture(fboScatter);
    System.err.println("after added a TextureFBOScatter to the TextureManager... " + TextureManager.getInstance().numActiveTextures());


    GeomPhotoScatterFBO geomScatter = new GeomPhotoScatterFBO(new Point3f(rx, ry, 0f), rw, rh, fboScatter, false);
    geomScatter.setColor(1f, 1f, 1f, 1f);

    for (Geom light : lights)
    {
      geomScatter.addGeom(light);
    }


    addGeom(geomScatter);

    return geomScatter;
  }
  public static int useLight = 1;
  public static float light1_x = .5f;
  public static float light1_y = .5f;
  public static float light2_x = .5f;
  public static float light2_y = .5f;
  public static float light3_x = .5f;
  public static float light3_y = .5f;
  public static float density = .3f;
  public static float decay = 1f;
  public static float weight = 50f;
  public static int samples = 50;
  public static float exposure = .0002f;

  public boolean checkKeys(boolean[] keys, boolean[] keysPressing)
  {
    if (keys[KeyEvent.VK_LEFT])
    {
      switch (useLight)
      {
        case 1:
          light1_x -= .01f;
          break;
        case 2:
          light2_x -= .01f;
          break;
        case 3:
          light3_x -= .01f;
          break;
      }
      return true;
    }
    if (keys[KeyEvent.VK_RIGHT])
    {
      switch (useLight)
      {
        case 1:
          light1_x += .01f;
          break;
        case 2:
          light2_x += .01f;
          break;
        case 3:
          light3_x += .01f;
          break;
      }
      return true;
    }
    if (keys[KeyEvent.VK_DOWN])
    {
      switch (useLight)
      {
        case 1:
          light1_y -= .01f;
          break;
        case 2:
          light2_y -= .01f;
          break;
        case 3:
          light3_y -= .01f;
          break;
      }
      return true;
    }
    if (keys[KeyEvent.VK_UP])
    {
      switch (useLight)
      {
        case 1:
          light1_y += .01f;
          break;
        case 2:
          light2_y += .01f;
          break;
        case 3:
          light3_y += .01f;
          break;
      }
      return true;
    }

    if (keys[KeyEvent.VK_G])
    {
      decay += .001f;
      return true;
    }

    if (keys[KeyEvent.VK_B])
    {
      decay -= .001f;
      return true;
    }

    if (keys[KeyEvent.VK_F])
    {
      density += .01f;
      return true;
    }

    if (keys[KeyEvent.VK_V])
    {
      density -= .01f;
      return true;
    }

    if (keys[KeyEvent.VK_D])
    {
      exposure += .00005f;
      return true;
    }

    if (keys[KeyEvent.VK_C])
    {
      exposure -= .00005f;
      return true;
    }
    if (keys[KeyEvent.VK_S])
    {
      samples++;
      return true;
    }

    if (keys[KeyEvent.VK_X])
    {
      samples--;
      return true;
    }

    if (keys[KeyEvent.VK_A])
    {
      weight += 1f;
      return true;
    }

    if (keys[KeyEvent.VK_Z])
    {
      weight -= 1f;
      return true;
    }

    if (keys[KeyEvent.VK_1])
    {
      useLight = 1;
      return true;
    }

    if (keys[KeyEvent.VK_2])
    {
      useLight = 2;
      return true;
    }

    if (keys[KeyEvent.VK_3])
    {
      useLight = 3;
      return true;
    }

    return false;
  }
}
