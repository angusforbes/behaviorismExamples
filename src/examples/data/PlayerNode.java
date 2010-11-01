/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Node;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class PlayerNode extends Node 
{
 public static Category playerCategory = new Category("player");

 public String player = "";
 
  public PlayerNode(String name)
  {
    this.name = name;
    this.category = playerCategory;
    //setPlayer(name);
  }

  public void setPlayer(String player)
  {
    this.player = player;
    hasChanged = true;
  }
  
  @Override
  public GeomPlayerNode makeGeom()
  {
    return new GeomPlayerNode(new Point3f(), this);
  }
}
