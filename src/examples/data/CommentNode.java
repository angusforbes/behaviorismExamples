/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Node;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class CommentNode extends Node 
{
 public static Category commentCategory = new Category("comment");

 public String comment = "";
 
  public CommentNode(String name)
  {
    this.name = name;
    this.category = commentCategory;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
    hasChanged = true;
  }
  
  @Override
  public GeomCommentNode makeGeom()
  {
    return new GeomCommentNode(new Point3f(), this);
  }
}
