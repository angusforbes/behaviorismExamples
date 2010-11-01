/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Node;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class UserNode extends Node
{
  public static Category userCategory = new Category("user");

  public String username = "";
  public String gender = "";
  
  public UserNode(String name)
  {
    this.name = name;
    this.category = userCategory;
  }

  @Override
  public GeomUserNode makeGeom()
  {
    return new GeomUserNode(new Point3f(), this);
  }
}
