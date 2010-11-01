package examples.text;

import behaviorism.Behaviorism;
import behaviorism.geometry.Colorf;
import behaviorism.geometry.GeomPoint;
import behaviorism.geometry.GeomRect;
import behaviorism.geometry.text.GeomSimpleText;
import behaviorism.geometry.text.GeomText;
import behaviorism.geometry.text.TextBuilder;
//import behaviorism.handlers.FontHandler;
import behaviorism.handlers.FontHandler;
import behaviorism.handlers.FontId;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import java.awt.Font;
import javax.vecmath.Point3f;

public class WorldText extends World
{

  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldText());
  }

  public void setUpWorld()
  {
  //  Utils.sleep(5000);
    //setColor(.2f, .2f, .2f);
    //simpleTextDemo();
    textDemo();
    //nonDynamicDemo();
  // pixelDemo();
  }

  public void pixelDemo()
  {
    GeomRect r = new GeomRect(100, 100, 100, 100);
    addGeom(r);
    r.setColor(1f, 0f, 0f, 1f);
    GeomRect r2 = new GeomRect(false, 0, 0, 50, 50);
    r.addGeom(r2);
    r2.setColor(0f, 0f, 1f, 1f);
    GeomRect r3 = new GeomRect(false, 50, -100, 50, 50);
    r2.addGeom(r3);
    r3.setColor(0f, 1f, 0f, 1f);
  }

 

  public void nonDynamicDemo()
  {

    GeomText g = null;
    GeomRect r = null;
    int test = 2;

    if (test == 1)
    {
      r = new GeomRect(false, -100, 0, 100, 100);
      addGeom(r);
      r.setColor(1f, 0f, 0f, .5f);

      TextBuilder gtb = new TextBuilder("Qaaaaaa"). //font("").
        anchor(-100, 0, false).
        justify(0f, -1f).
       // font("Gill Sans", Font.PLAIN).
        //constrainByHeight(100).
        //constrainByWidth(64).
        exactBounds(false).
        adjustDescent(false).
        exactPadding(0, 0, 0, 0).
        //fitInBox(100, 100).
        //percentagePadding(0f, 0f).
        backgroundColor(new Colorf(0f, 1f, 0f, 1f)).
        textColor(new Colorf(0f, 0f, 1f, 1f));

      g = gtb.build();
      addGeom(g);

      GeomPoint gp = new GeomPoint(false, -100, 0);
      gp.setColor(1f, 1f, 1f, 1f);
      addGeom(gp);
    }
    if (test == 2)
    {
      r = new GeomRect(new Point3f(-1f, -.5f, 0f), 2f, 1f);
      addGeom(r);
      r.setColor(1f, 0f, 0f, .5f);

      TextBuilder gtb = new TextBuilder("Qaaa").anchor(-1f, -.5f, 0f).
        justify(-1f, -1f).
      //  font("Gill Sans", Font.PLAIN).
        constrainByWidth(1f).
        //constrainByHeight(1f).
        exactBounds(true).
        //adjustDescent(true).
        exactPadding(0, 0, 0, 0).
        //percentagePadding(1f, .1f, 1f, 0f).
        //fitInBox(2f, 1f).
        backgroundColor(new Colorf(0f, 1f, 0f, 1f)).
        textColor(new Colorf(0f, 0f, 1f, 1f));

      g = gtb.build();

      addGeom(g);

      GeomPoint gp = new GeomPoint(0f, 0f, 0f);
      gp.setColor(1f, 1f, 1f, 1f);
      addGeom(gp);

    }

//    Utils.sleep(2000);
//    g.setFont("Georgia", Font.ITALIC);
//    Utils.sleep(2000);
//    g.setFont("Gill Sans", Font.PLAIN, 100);
//    Utils.sleep(2000);
//    g.setText("awawbQlajh");
//    Utils.sleep(2000);
//    g.setFont("Georgia", Font.ITALIC);

/*

    BehaviorGeomContinuous bgc = new BehaviorGeomContinuous(
      (new ContinuousBehaviorBuilder(10000L)).ranges(new float[]
      {
        2f, 2f
      }).loop(LoopEnum.REVERSE))
    {

      public void updateGeom(Geom geom)
      {
        GeomText gt = ((GeomText) geom);
        gt.setJustify(
          gt.justifyX + getOffsets()[0],
          gt.justifyY + getOffsets()[1]);
      }
    };
    g.attachBehavior(bgc);


    BehaviorGeomContinuous bgc2 = new BehaviorGeomContinuous(
      (new ContinuousBehaviorBuilder(5000L)).ranges(new float[]
      {
        1, 1, 1, 1
      }).loop(LoopEnum.REVERSE))
    {

      public void updateGeom(Geom geom)
      {
        GeomText gt = ((GeomText) geom);
        gt.setMargins(
          gt.marginLeft + getOffsets()[0],
          gt.marginRight + getOffsets()[1],
          gt.marginBottom + getOffsets()[2],
          gt.marginTop + getOffsets()[3]);
      }
    };
    g.attachBehavior(bgc2);
    */
  }

   public void simpleTextDemo()
  {
    //FontHandler.getInstance().createFont("Gill Sans", Font.PLAIN);
  //  GeomSimpleText g = new GeomSimpleText(true, 300, 200, "greetings", "Gill Sans", Font.PLAIN, 100);
    GeomSimpleText g = new GeomSimpleText(new Point3f(0f, 0f, 0f), "greetings", "Gill Sans", Font.ITALIC, 100);
    g.backgroundColor = new Colorf(1f, 1f, 1f, 1f);
    g.color = new Colorf(1f, 0f, 0f, 1f);
    g.setMargins(.3f,.3f,0f, .3f);

//    Geom g = new GeomRect(new Point3f(-.5f,-.5f,0f), 1f, 1f);
    addGeom(g);
  }

  public void textDemo()
  {
//    FontHandler.getInstance().createFont(new FontId("Arial", Font.PLAIN));
    //FontHandler.getInstance().createFont("Univers 55", Font.ITALIC);
//
//    GeomRect r = new GeomRect(100, 100, 100, 100);
//    addGeom(r);
//    r.setColor(1f,0f,0f,1f);
    GeomRect r2 = new GeomRect(false, -100, 0, 100, 300);
    addGeom(r2);
    r2.setColor(0f, 0f, 1f, .5f);
    System.out.println("r2 = " + r2);
    long startTime = Utils.nowPlusMillis(500L);


    GeomText g = new TextBuilder("hellothere").
      font("Consolas", Font.PLAIN).
      //constrainByWidth(200).
      constrainByWidth(1.5f).
      exactBounds(true).
      //exactPadding(0, 0).
      //percentagePadding(.5f, 0f).
      backgroundColor(new Colorf(0f, 1f, 0f, 1f)).
      textColor(new Colorf(0f, 0f, 1f, 1f)).
      justify(-1, 1).
      //anchor(-100, 0, false).
      //fitInBox(100, 300).
      build();
    
    g.setScaleAnchor(g.getCenter());
    g.setRotateAnchor(g.getCenter());
    
    addGeom(g); 

    Utils.sleep(3000);
    g.setFont("Menlo", Font.BOLD);
    Utils.sleep(3000);
    g.setFont("Times", Font.BOLD);
    Utils.sleep(3000);
    g.setFont("Arial", Font.BOLD);


    System.out.println("g = " + g);

    GeomPoint gp = new GeomPoint(false, -100, 0);
    gp.setColor(1f, 1f, 1f, 1f);
    addGeom(gp);
//      g.state = new State();
//      g.state.BLEND = true;
//      g.state.DEPTH_TEST = false;

//    System.out.println("g = " + g);
//
//    Geom g2 = gtb.justify(0, 0).anchor(50, 50).
//      build();
//    g2.setScaleAnchor(g2.getCenter());
//    r2.addGeom(g2, startTime, 100L);

  //
//      BehaviorRotate.rotate(g,
//        startTime, Utils.randomLong(3000, 10000), LoopEnum.LOOP,
//        new Point3f(0f, 0f, 360f));
//
//      BehaviorTranslate.translate(g,
//        startTime, 10000L, LoopEnum.REVERSE,
//        new Point3f(0f, 0f, 3f));
//      BehaviorScale.scale(g,
//        startTime, 10000L, LoopEnum.REVERSE,
//        new Point3f(12f, 12f, 0f));
//      BehaviorScale.scale(g2,
//        startTime, 10000L, LoopEnum.REVERSE,
//        new Point3f(12f, 12f, 0f));
  }
//
//    BehaviorRotate.rotate(
//      this,
//      startTime, 10000L, LoopEnum.LOOP,
//      new Point3f(360f, 0f, 360f * 3)
//      );
}
