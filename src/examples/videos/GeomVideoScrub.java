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

  @Override
  public void mouseInAction()
  {
    System.out.println("in GeomVideoScrub : in mouseInAction()");
  }

  @Override
  public void mouseOverAction()
  {
    //System.out.println("in GeomVideoScrub : in mouseOverAction");
  }

  @Override
  public void mouseOutAction()
  {
    //System.out.println("in GeomVideoScrub : in mouseOutAction()");
  }

  @Override
  public void releaseAction()
  {
    System.out.println("in GeomVideoScrub : in releaseAction()");
     TextureVideo tv = (TextureVideo) getTexture();
     //tv.setBalance(-1f);
     //tv.setImageType(tv.getImageType() + 1);

  }

  public void selectedAction()
  {
    System.out.println("in GeomVideoScrub : in selectedAction()");
  }


  public void unselectedAction()
  {
    System.out.println("in GeomVideoScrub : in unselectedAction()");
  }

  @Override
  public void pressAction()
  {
     TextureVideo tv = (TextureVideo) getTexture();
     //tv.setBalance(1f);
    System.out.println("in GeomVideoScrub : in pressAction()");
     //tv.setImageType(6);
  }

  @Override
  public void dragAction()
  {
    super.dragAction();
    System.out.println("in GeomVideoScrub : in dragAction()");
  }

  @Override
  public void clickAction()
  {
    System.out.println("in GeomVideoScrub : in clickAction()");
  }

  @Override
  public void doubleClickAction()
  {
    System.out.println("in GeomVideoScrub : in doubleClickAction()");
  }

  @Override
  public void mouseStartedMovingAction()
  {
    //System.out.println("in mouseStartedMovingAction... ");
    TextureVideo tv = (TextureVideo) getTexture();
    tv.setRate(0);
  }

  @Override
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

  @Override
  public void mouseMovingAction()
  {
    System.out.println("in mouseMovingAction");
    Point3f p = MouseHandler.getInstance().mouseGeomPoint;
    TextureVideo tv = (TextureVideo) getTexture();

    if (scrubWhileMoving == true)
    {
     double percent = p.x / this.w;
     tv.setPlaybackPercentage(percent);
    }
  }

  /*
  @Override
  public void draw(GL gl)
  {
  if (updateTextures())
  {
  if (maxSize > 0f)
  {
  normalizeSize(getTexture().w, getTexture().h, maxSize);
  }

  gl.glColor4fv(color.array(), 0);
  this.textures.get(0).texture.bind();
  TextureCoords tc = this.textures.get(0).texture.getImageTexCoords();

  gl.glEnable(GL.GL_TEXTURE_2D);

  drawRect(gl, 0f, 0f, 0f, w, h, tc.left(), tc.right(), tc.bottom(), tc.top());
  gl.glDisable(GL.GL_TEXTURE_2D);
  }
  }
   */

  /*
  @Override
  public void doubleClickAction(MouseEvent me)
  {
  System.out.println("in GeomVideo: handleDoubleClick");
  System.out.println("mp.isPlaying = " + mp.isPlaying());

  if (mp.isPlaying() == true)
  {

  //System.out.println("about to call this.stop!");
  stopBehavior();
  this.mp.pause();

  for (int i = 0; i < 1; i++)
  {
  //this.mp.setMediaTime(this.mp.getMediaTime() - 3);
  //this.ac.setVolume(this.ac.getVolume() - .1f);
  //this.ac.setMute(!this.ac.isMuted());
  }
  }
  else //this.isPlaying == false
  {
  System.out.println("about to call this.start!");
  this.mp.play();
  mp.setRate(1f);

  startBehavior();
  }

  }

  public void stopBehavior()
  {
  System.out.println("stopping......");
  stopBehavior = BehaviorScale.scale(Utils.nowPlusMillis(0L), 150L,
  new Point3f(-2f, -2f, 0f));
  selectableObject.attachBehavior(stopBehavior);
  }

  public void startBehavior()
  {
  //isPlaying = true;

  startBehavior = BehaviorScale.scale(Utils.nowPlusMillis(0L), 150L,
  new Point3f(2f, 2f, 0f));
  selectableObject.attachBehavior(startBehavior);

  startBehavior = BehaviorRotate.rotate(Utils.nowPlusMillis(50L), 350L,
  new Point3f(0f, 720f, 0f));
  //selectableObject.attachBehavior(startBehavior);
  }

  
  public void normalizeSize(Dimension dim, float size)
  {
  int tw = (int) dim.getWidth();
  int th = (int) dim.getHeight();

  //System.out.println("tw / th = " + tw + "/" + th);
  if (tw >= th)
  {
  normalizeSizeByWidth(tw, th, size);
  }
  else
  {
  normalizeSizeByHeight(tw, th, size);
  }
  }
   */
}
