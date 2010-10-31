/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Node;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class TermNode extends Node 
{
 public static Category termCategory = new Category("term");

 public String term = "";
 public String definition = "";
 
  public TermNode(String name)
  {
    this.name = name;
    this.category = termCategory;
    //setTerm(name);
  }

  public void setTerm(String term)
  {
    this.term = term;
    hasChanged = true;
  }
  
  @Override
  public GeomTermNode makeGeom()
  {
    return new GeomTermNode(new Point3f(), this);
  }
}
