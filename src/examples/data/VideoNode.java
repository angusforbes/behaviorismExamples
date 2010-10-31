/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Node;
import behaviorism.textures.TextureManager;
import behaviorism.textures.TextureVideo;
import behaviorism.utils.FileUtils;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class VideoNode extends Node
{
 public static Category videoCategory = new Category("video");
  public String title = "";
  public String url = "";
  public TextureVideo video = null;

  public VideoNode(String name)
  {
    this.name = name;
    this.category = videoCategory;
  }

  @Override
  public GeomVideoNode makeGeom()
  {
    //load video...
    video = new TextureVideo(FileUtils.toURI("resources/data/videos/testvid.flv"));
    TextureManager.getInstance().addTexture(video);
    //video.play();

    return new GeomVideoNode(new Point3f(), this);
  }
}
