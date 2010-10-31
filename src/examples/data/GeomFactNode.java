/* CommentNodeGeom.java ~ Nov 3, 2009 */

package examples.data;

import behaviorism.geometry.Geom;
import behaviorism.utils.RenderUtils;
import java.awt.Font;
import javax.vecmath.Point3f;

/**
 *
 * @author angu s
 */
public class GeomFactNode extends GeomMultipleLineTextTemp //GeomSimpleText
{
  public GeomFactNode(Point3f p3f, FactNode node)
  {
    //Point3f p3f, float width, String text, String fontName, int fontStyle, float fontSize)

    super(p3f, 2f, node.fact, "Arial", Font.PLAIN, 20);
    this.data = node;
    //setJustify(1f ,1f);
    setColor(1f,1f,1f,1f);
  }

  public void draw()
  {
    //this update and those like it should be handled by behaviors at the beginning of the display loop
    if (this.data.hasChanged == true)
    {
      setText(((FactNode) data).fact);
      //this.data.hasChanged = false;
    }
    super.draw();
  }

  
}
