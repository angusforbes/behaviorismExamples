/* GeomVideoNode.java ~ Nov 3, 2009 */

package examples.data;

import behaviorism.geometry.media.GeomVideo;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class GeomVideoNode extends GeomVideo
{
  public GeomVideoNode(Point3f p3f, VideoNode videoNode)
  {
    super(videoNode.video);
  }
}
