/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Node;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class FactNode extends Node 
{
 public static Category factCategory = new Category("fact");

 public String fact = "";
 
  public FactNode(String name)
  {
    this.name = name;
    this.category = factCategory;
    //setFact(name);
  }

  public void setFact(String fact)
  {
    this.fact = fact;
    hasChanged = true;
  }
  
  @Override
  public GeomFactNode makeGeom()
  {
    return new GeomFactNode(new Point3f(), this);
  }
}
