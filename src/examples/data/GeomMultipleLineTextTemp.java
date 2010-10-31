/* GeomMultipleLineText.java ~ Nov 3, 2009 */

package examples.data;

import behaviorism.geometry.text.GeomText;
import behaviorism.geometry.text.TextBuilder;
import behaviorism.renderers.Renderer;
import behaviorism.utils.RenderUtils;
import com.sun.opengl.util.awt.TextRenderer;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.media.opengl.GL2;
import javax.vecmath.Point3f;

/**
 *
 * @author angus
 */
public class GeomMultipleLineTextTemp extends GeomText
{
  List<String> lines = new ArrayList<String>();

  public GeomMultipleLineTextTemp(Point3f p3f, float width, String text, String fontName, int fontStyle, float fontSize)
  {
    super(p3f, new TextBuilder(text).font(fontName, fontStyle, fontSize));
    this.w = width;
  
  }

  int pixW = 0;

   public boolean createLayout(TextRenderer renderer, float pxW)
  {
    //System.out.println("in createLayout()");
    lines.clear();
    double pxHeightTaken = 0;
    double pxMaxWidth = 0;
    double pxMaxHeight = 0;
    float maxDescent = 0f;
    float maxAscent = 0f;
    int longestLineIdx = 0;

    FontRenderContext frc_layout = renderer.getFontRenderContext();
    Font font_layout = renderer.getFont();

    Map attrs = new HashMap();
    attrs.put(TextAttribute.FONT, font_layout);
    AttributedString str = new AttributedString(this.text, attrs);
    LineBreakMeasurer measurer = new LineBreakMeasurer(str.getIterator(), frc_layout);
    int curPos = 0;
    pxHeightTaken = 0;

    while (measurer.getPosition() < this.text.length())
    {
      //System.out.println("measurer.getPosition = " + measurer.getPosition());
      int nextPos = measurer.nextOffset(pxW);

      //System.out.println("nextPos = " + nextPos);
      String line = this.text.substring(curPos, nextPos);
      Rectangle2D bounds = renderer.getBounds(line);
      lines.add(line);
      pxHeightTaken += (int) bounds.getHeight();

      /*
      //if (pxHeightTaken > pxHeight * 1.5) //then this font size is too big
      if (pxHeightTaken > pxH * 1) //then this font size is too big
      {
        //exit now unless we are forcing the use of this font
        if (forceUseOfRenderer != true)
        {
          lines.clear();
          return false;
        }
      }
      */
      curPos = nextPos;
      measurer.setPosition(curPos);

      //GlyphVector gv1 = font_layout.createGlyphVector(frc_layout, line);
      //Rectangle2D bounds1 = gv1.getPixelBounds(null, 0f, 0f);
      Rectangle2D bounds1 = font_layout.getStringBounds(line, frc_layout);

      LineMetrics lineMetrics_layout = font_layout.getLineMetrics(line, frc_layout);

      if (bounds1.getHeight() > pxMaxHeight)
      {
        pxMaxHeight = bounds1.getHeight();
      }
      if (bounds1.getWidth() > pxMaxWidth)
      {
        //System.out.println("line : " + line + ", bounds1 = " + bounds1);
        pxMaxWidth = bounds1.getWidth();
        longestLineIdx = lines.size() - 1;
      }

      if (lineMetrics_layout.getDescent() > maxDescent)
      {
        maxDescent = lineMetrics_layout.getDescent();
      }

      if (lineMetrics_layout.getAscent() > maxAscent)
      {
        maxAscent = lineMetrics_layout.getAscent();
      }
    }

    calculateBoundsIgnoringLOD(lines.get(longestLineIdx));
    return true;
  }


   private void calculateBoundsIgnoringLOD(String line)
  {
    frc = textRenderer.getFontRenderContext();
    font = textRenderer.getFont();

    Rectangle2D bounds;

    if (exactPixelBounds == true)
    {
      GlyphVector gv1 = font.createGlyphVector(frc, line);
      this.stringBounds = gv1.getPixelBounds(null, 0f, 0f);
    }
    else
    {
      this.stringBounds = font.getStringBounds(line, frc);
    }

    bounds = this.stringBounds;

    int www = RenderUtils.getViewport()[2];
    int hhh = RenderUtils.getViewport()[3];

    float worldHeight = Renderer.screenBoundsInWorldCoords.height; //Behaviorism.world.getWorldRect().h;
    this.scaleVal = ((worldHeight / (float) hhh));

    this.metrics = font.getLineMetrics(line, frc);


    //initialize width
    //this.w = (float) bounds.getWidth() * scaleVal;
    //this.h = (float) (bounds.getHeight() * lines.size()) * scaleVal;
    strHeightInWorldCoords = (float) (bounds.getHeight() * scaleVal);
    strWidthInWorldCoords = (float) (bounds.getWidth() * scaleVal);
   }

  float strWidthInWorldCoords = 0f;
  float strHeightInWorldCoords = 0f;
  boolean firsttime = true;

  public void draw()
  {
    if (firsttime)
    {
      pixW = RenderUtils.getWidthOfObjectInPixels(this, 0f);
    
      createLayout(textRenderer, pixW);
      //firsttime = false;
    }

    /*
    textRenderer.beginRendering(600, 400);
   textRenderer.setColor(color.toJavaColor());

    for (String line : lines)
    {
        GL2 gl = RenderUtils.getGL();

        textRenderer.draw(line, 100, 100);
    }
    textRenderer.endRendering();
    */
    textRenderer.setSmoothing(false);
    textRenderer.begin3DRendering();

    yinc = strHeightInWorldCoords;
    float inc = yinc;

    textRenderer.setColor(color.toJavaColor());
    for (String line : lines)
    {
    textRenderer.draw3D(line,
      useX,
      useY - inc,
      offset,
      scaleVal);

      inc += yinc;
    }

    textRenderer.end3DRendering();

    //textRenderer.flush();
  }

  float yinc = 0f;
 
   @Override
  public void drawPickingBackground()
  {
    GL2 gl = RenderUtils.getGL();
    //gl.glColor4f(1f, 0f, 0f, .2f); //for testing
    gl.glColor4f(0f, 0f, 0f, 0f);
    offset = 0f;

    translateAnchor.x = 0f; //-transX;
    translateAnchor.y = 0f; //transY;
    float x = 0f; //-transX;
    float y = 0f; //transY;

    float maxw = strWidthInWorldCoords;
    float totalh = -strHeightInWorldCoords * lines.size();

    //this.w = maxw;
    this.h = totalh;
    
      totalh = -yinc * lines.size();

    gl.glBegin(gl.GL_QUADS);
    gl.glVertex3f(x, y, offset);
    gl.glVertex3f(x + maxw, y, offset);
    gl.glVertex3f(x + maxw, y + totalh, offset);
    gl.glVertex3f(x, y + totalh, offset);
    gl.glEnd();
   }
}
