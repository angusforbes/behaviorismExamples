/* GeomUserNode.java ~ Nov 3, 2009 */

package examples.data;

import behaviorism.geometry.GeomRect;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class GeomUserNode extends GeomRect
{
   public GeomUserNode(Point3f p3f, UserNode node)
  {
    super(p3f, 1f, 1f);
  }

}
