/* CommentNodeGeom.java ~ Nov 3, 2009 */

package examples.data;

import behaviorism.geometry.text.GeomSimpleText;
import java.awt.Font;
import javax.vecmath.Point3f;

/**
 *
 * @author angu s
 */
public class GeomTermNode extends GeomSimpleText
{
  public GeomTermNode(Point3f p3f, TermNode node)
  {
    super(p3f, node.term, "Arial", Font.PLAIN, 20);
    this.data = node;
    //setJustify(1f ,1f);
    setColor(1f,1f,1f,1f);
  }

  public void draw()
  {
    //this update and those like it should be handled by behaviors at the beginning of the display loop
    if (this.data.hasChanged == true)
    {
      setText(((TermNode) data).term);
      //this.data.hasChanged = false;
    }
    super.draw();
  }

}
