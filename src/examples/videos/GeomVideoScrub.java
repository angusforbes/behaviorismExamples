///* GeomVideo.java ~ Dec 14, 2008 */
package examples.videos;

import behaviorism.geometry.media.GeomVideo;
import behaviorism.handlers.MouseHandler;
import behaviorism.textures.TextureVideo;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
//mayeb should extends from a superclass called GeomTexture?
public class GeomVideoScrub extends GeomVideo
{
  boolean scrubWhileMoving = true;

  public GeomVideoScrub(Point3f p3f, float w, float h, TextureVideo ti)
  {
    super(p3f, w, h, ti);
  }

  public GeomVideoScrub(TextureVideo ti)
  {
    super(new Point3f(), 1f, 1f, ti);
  }

  public GeomVideoScrub(Point3f p3f, float maxSize, TextureVideo ti)
  {
    super(p3f, maxSize, ti);
  }

  
  public void mouseInAction()
  {
    //System.out.println("in GeomVideoScrub : in mouseInAction()");
  }

  
  public void mouseOverAction()
  {
    //System.out.println("in GeomVideoScrub : in mouseOverAction");
  }

  
  public void mouseOutAction()
  {
    //System.out.println("in GeomVideoScrub : in mouseOutAction()");
  }

  
  public void releaseAction()
  {
    //System.out.println("in GeomVideoScrub : in releaseAction()");
     TextureVideo tv = (TextureVideo) getTexture();
  }

  
  public void selectedAction()
  {
    //System.out.println("in GeomVideoScrub : in selectedAction()");
  }


  
  public void unselectedAction()
  {
    //System.out.println("in GeomVideoScrub : in unselectedAction()");
  }

  
  public void pressAction()
  {
     TextureVideo tv = (TextureVideo) getTexture();
    //System.out.println("in GeomVideoScrub : in pressAction()");
  }

  
  public void dragAction()
  {
    //super.dragAction();
    //System.out.println("in GeomVideoScrub : in dragAction()");
  }

  
  public void clickAction()
  {
    //System.out.println("in GeomVideoScrub : in clickAction()");
  }

  
  public void doubleClickAction()
  {
    //System.out.println("in GeomVideoScrub : in doubleClickAction()");
  }

  
  public void mouseStartedMovingAction()
  {
    //System.out.println("in mouseStartedMovingAction... ");
    TextureVideo tv = (TextureVideo) getTexture();
    tv.setRate(0);
  }

  
  public void mouseStoppedMovingAction()
  {
    //System.out.println("in mouseStoppedMovingAction... ");
    TextureVideo tv = (TextureVideo) getTexture();
    tv.play();
    tv.setRate(1);
    if (scrubWhileMoving == false)
    {
    Point3f p = MouseHandler.getInstance().mouseGeomPoint;
    double percent = p.x / this.w;
    tv.setPlaybackPercentage(percent);
    }
  }

  public void mouseMovingAction()
  {
    //System.out.println("in mouseMovingAction");
    Point3f p = MouseHandler.getInstance().mouseGeomPoint;
    TextureVideo tv = (TextureVideo) getTexture();

    if (scrubWhileMoving == true)
    {
     double percent = p.x / this.w;
     tv.setPlaybackPercentage(percent);
    }
  }

}
