/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Node;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class YearNode extends Node 
{
 public static Category yearCategory = new Category("year");

 public String year = "";
 
  public YearNode(String name)
  {
    this.name = name;
    this.category = yearCategory;
    //setYear(name);
  }

  public void setYear(String year)
  {
    this.year = year;
    hasChanged = true;
  }
  
  @Override
  public GeomYearNode makeGeom()
  {
    return new GeomYearNode(new Point3f(), this);
  }
}
