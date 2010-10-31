/* WorldDataGraph.java ~ Oct 18, 2009 */
package examples.data;

import behaviorism.Behaviorism;
import behaviorism.data.filterers.CategoryFilterer;
import behaviorism.data.Node;
import behaviorism.data.planes.GraphPlane;
import behaviorism.data.IsInPlaneRelationship;
import behaviorism.data.TraversalFilterer;
import behaviorism.data.collectors.Collector;
import behaviorism.geometry.Geom;
import behaviorism.utils.DataUtils;
import behaviorism.utils.FileUtils;
import behaviorism.utils.Utils;
import behaviorism.worlds.World;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author angus
 */
public class WorldDataGraph extends World
{
  public static final String NODE_DIR = FileUtils.toCrossPlatformFilename("resources/data/docNodes/");
  //public static final String NODE_DIR = FileUtils.toCrossPlatformFilename("resources/data/nodes/");
  public static void main(String[] args)
  {
    Behaviorism.installWorld(new WorldDataGraph());
    //new WorldDataGraph();
  }

  public void setUpWorld()
  {
    setColor(0f,0f,0f,0f);
   
//    Geom gmlt = new GeomMultipleLineTextTemp(
//      new Point3f(), 2f, "\u201Cthe times they are\u201D", "Georgia", Font.ITALIC, 18);
//    gmlt.setColor(1f,1f,1f,1f);
//    addGeom(gmlt);
//
//    Geom gmlt2 = new GeomMultipleLineTextTemp(
//      new Point3f(1f,1f,0f), 2f, "hey ho, ldfdf default: aaa bbb  ccc d e f fet's go!", "Georgia", Font.ITALIC, 14);
//    gmlt2.setColor(1f,1f,1f,1f);
//    addGeom(gmlt2);
//
//    addGeom( new GeomRect(new Point3f(),.2f, .2f) );
//
//    PlayerNode pn1 = new PlayerNode("IBM");
//    addGeom(pn1.makeGeom());
//    PlayerNode pn2 = new PlayerNode("DARPA");
//    addGeom(pn2.makeGeom());
//    PlayerNode pn3 = new PlayerNode("Grunberg");
//    addGeom(pn3.makeGeom());
//    PlayerNode pn4 = new PlayerNode("Fert");
//    addGeom(pn4.makeGeom());


    
    loadData2();
    printData2();
    //testData2();

    Node n = this.data.findByName("year1988");
    //Node n = this.data.findByName("playerFert");
    System.out.println("n = " + n);
    drawData2(n);
    //drawData2(this.data);
    
  }

  public WorldDataGraph()
  {
  
  }

  public void addGeomNode(Node n)
  {
    Geom gcn = n.makeGeom();

      if (gcn != null)
      {
        System.out.println("about to add a " + gcn.getClass() + " node...");
        addGeom(gcn);
        gcn.clickableObject = this;

      }

  }

  //draws this node and all data attached to this node
  public void drawData2(Node dataNode)
  {
   // Utils.sleep(5000);

    addGeomNode(dataNode);

    for (Node n : dataNode.getData())
    {
      addGeomNode(n);
    }

//    CommentNode com = (CommentNode) this.data.findByName("commentX");
//    Utils.sleep(5000);
//    com.comment = "AAAAAAAAAAA";
//
  }

  public void printData2()
  {
    System.out.println(this.data);
  }
  public void testData2()
  {
    //do some tests on it
    Utils.sleep(1000);

    System.out.println("this data::: " + this.data);
    Utils.sleep(1000);

    System.out.println("\n\nvideoB data::: " + this.data.findByName("videoB"));

     Collector collector = new Collector(
      this.data.findByName("user2"),
      //new TraversalFilterer(new CategoryFilterer(CommentNode.commentCategory)),
      //new CategoryFilterer(UserNode.userCategory, VideoNode.videoCategory),
      6, 150);

    //Set<Node> collection = collector.collect();

//    System.out.println("this data::: " + this.data);
//    System.out.println("\n\nvidA data::: " + this.data.findByName("vidA"));
//    System.out.println("\n\nuser1 data::: " + this.data.findByName("user4"));

    Utils.sleep(1000);
    System.out.println("\ncollection \n\n");
//    for (Node ddd : collection)
//    {
//      System.out.println("collected : " + ddd.name + " of type " + ddd.category);
//    }

   // System.out.println("collection size = " + collection.size());


    System.out.println("analyze collection...\n\n");

    Utils.sleep(1000);

    CommentWordCountAnalyzer analyzer = new CommentWordCountAnalyzer(collector);

    analyzer.analyze();

    System.out.println("our analysis indicates that there are " + analyzer.totalWords + " words in the" +
      " comments found by the collection.");

    System.out.println("\n\nanalyze comments by user gender...\n\n");
    Utils.sleep(1000);

    CommentGenderAnalyzer cga = new CommentGenderAnalyzer(this.data.findByCategory(CommentNode.commentCategory));

    cga.analyze();

    Utils.sleep(1000);

    System.out.println("male words = " + cga.maleWords + ", female words = " + cga.femaleWords);
    System.out.println("male users = " + cga.maleUsers + ", female users = " + cga.femaleUsers);
  }

  public void loadData2()
  {
    this.data = new GraphPlane("raw data plane");

    List<File> files = FileUtils.getFilesFromDirectory(NODE_DIR);

    for (File f : files)
    {
      processFile(f);
    }

   
  }

  public Node processFile(File f)
  {
    String nodename = f.getName().split("\\.")[0];
    System.err.println("processing file named " + nodename);

    Node loadedNode = this.data.findByName(nodename);
    if (loadedNode != null)
    {
      System.err.println("this node is already loaded...");
      return loadedNode;
    }

    System.err.println("node is loaded yet, so will process it : trying to load a file called " + f.getAbsolutePath());
    List<String> lines = FileUtils.loadTextFromFile(f);
    System.err.println("successfully loaded " + f);

    if (nodename.startsWith("comment"))
    {
      System.err.println("making new CommentNode...");
      CommentNode node = new CommentNode(nodename);
      this.data.addNode(node, new IsInPlaneRelationship());

      for (String line : lines)
      {
        if (line.startsWith("IsConnectedTo:"))
        {
          processConnection(node, line);
        }

        if (line.startsWith("Comment"))
        {
          node.comment = line.split(":")[1];
        }
      }
      return node;
    }

    if (nodename.startsWith("year"))
    {
      System.err.println("making new YearNode...");
      YearNode node = new YearNode(nodename);
      this.data.addNode(node, new IsInPlaneRelationship());

      for (String line : lines)
      {
        if (line.startsWith("IsConnectedTo:"))
        {
          processConnection(node, line);
        }

        if (line.startsWith("Year"))
        {
          node.year = line.split(":")[1];
        }
      }
      return node;
    }

    if (nodename.startsWith("player"))
    {
      System.err.println("making new PlayerNode...");
      PlayerNode node = new PlayerNode(nodename);
      this.data.addNode(node, new IsInPlaneRelationship());

      for (String line : lines)
      {
        if (line.startsWith("IsConnectedTo:"))
        {
          processConnection(node, line);
        }

        if (line.startsWith("Name"))
        {
          node.player = line.split(":")[1];
        }
      }
      return node;
    }

    if (nodename.startsWith("fact"))
    {
      System.err.println("making new FactNode...");
      FactNode node = new FactNode(nodename);
      this.data.addNode(node, new IsInPlaneRelationship());

      for (String line : lines)
      {
        if (line.startsWith("IsConnectedTo:"))
        {
          processConnection(node, line);
        }

        if (line.startsWith("Fact"))
        {
          node.fact = line.split(":")[1];
        }
      }
      return node;
    }

    if (nodename.startsWith("term"))
    {
      System.err.println("making new TermNode...");
      TermNode node = new TermNode(nodename);
      this.data.addNode(node, new IsInPlaneRelationship());

      for (String line : lines)
      {
        if (line.startsWith("IsConnectedTo:"))
        {
          processConnection(node, line);
        }

        if (line.startsWith("Term"))
        {
          node.term = line.split(":")[1];
        }
        if (line.startsWith("Definition"))
        {
          node.definition = line.split(":")[1];
        }
      }
      return node;
    }


    if (nodename.startsWith("video"))
    {
      VideoNode node = new VideoNode(nodename);
      this.data.addNode(node, new IsInPlaneRelationship());

      for (String line : lines)
      {
        if (line.startsWith("IsConnectedTo:"))
        {
          processConnection(node, line);
        }

        if (line.startsWith("Title"))
        {
          node.title = line.split(":")[1];
        }

        if (line.startsWith("URL"))
        {
          node.url = line.split("URL:")[1];
        }
      }
      return node;
    }

    if (nodename.startsWith("user"))
    {
      UserNode node = new UserNode(nodename);
      this.data.addNode(node, new IsInPlaneRelationship());

      for (String line : lines)
      {
        if (line.startsWith("IsConnectedTo:"))
        {
          processConnection(node, line);
        }

        if (line.startsWith("Name"))
        {
          node.username = line.split(":")[1];
        }


        if (line.startsWith("Gender"))
        {
          node.gender = line.split(":")[1];
        }
      }
      return node;
    }

    System.err.println("ERRROR : couldn't process " + nodename);
    return null;

  }

  public void processConnection(Node node, String line)
  {
    String[] links = (line.split(":")[1]).split(";");

          for (String link : links)
          {
            String[] sides = link.split(",");

            Node connectedNode = this.data.findByName(sides[0]);

            if (connectedNode == null)
            {
              File connectedFile = new File(NODE_DIR + sides[0] + ".txt");
              System.err.println("trying to laod a file called : " + connectedFile.getAbsolutePath());
              connectedNode = processFile(connectedFile);
            }
            double weight = 1.0;

            if (sides.length > 1)
            {
              weight = Double.parseDouble(sides[1]);
            }

            node.addNode(connectedNode, new IsConnectedRelationship(weight));
          }
  }

  public void loadData()
  {
    this.data = new GraphPlane("raw data plane");

    List<String> lines = FileUtils.loadTextFromFile("resources/data/nodes.txt");

    for (String line : lines)
    {
      if (line.startsWith("Videos:"))
      {
        String[] vids = (line.split(":")[1]).split(";");

        for (String vid : vids)
        {
          this.data.addNode(new VideoNode(vid), new IsInPlaneRelationship());
        }
      }

      if (line.startsWith("Comments:"))
      {
        String[] coms = (line.split(":")[1]).split(";");

        for (String com : coms)
        {
          this.data.addNode(new CommentNode(com), new IsInPlaneRelationship());
        }
      }

      if (line.startsWith("Users:"))
      {
        String[] users = (line.split(":")[1]).split(";");

        for (String user : users)
        {

          this.data.addNode(new UserNode(user), new IsInPlaneRelationship());
        }
      }

      if (line.startsWith("IsConnectedTo:"))
      {
        String[] links = (line.split(":")[1]).split(";");

        for (String link : links)
        {
          String[] sides = link.split(",");

          Node parentNode = this.data.findByName(sides[0]);
          Node childNode = this.data.findByName(sides[1]);

          double weight = Utils.random(-1, 1);
          parentNode.addNode(childNode, new IsConnectedRelationship(weight));
          childNode.addNode(parentNode, new IsConnectedRelationship(weight));
        }
      }
    }

    Collector collector = new Collector(
      this.data.findByName("user4"),
      new TraversalFilterer(new CategoryFilterer(CommentNode.commentCategory)),
      //new CategoryFilterer(UserNode.userCategory, VideoNode.videoCategory),
      6, 150);

    Set<Node> collection = collector.collect();

//    System.out.println("this data::: " + this.data);
//    System.out.println("\n\nvidA data::: " + this.data.findByName("vidA"));
//    System.out.println("\n\nuser1 data::: " + this.data.findByName("user4"));

    Utils.sleep(1000);
    System.out.println("\ncollection \n\n");
    for (Node ddd : collection)
    {
      System.out.println("collected : " + ddd.name + " of type " + ddd.category);
    }

    System.out.println("collection size = " + collection.size());
  }


  @Override
  public void doubleClickAction(Geom geom)
  {

    Set<Node> nodeSet = geom.data.getData();
    nodeSet.add(geom.data);

    handleDrawingNodesConnectedTo(nodeSet);
  }

  //this method makes sure that all Nodes in nodeSet are put on the screen (if not there already)
  public void handleDrawingNodesConnectedTo(Set<Node> nodeSet)
  {
    //nodeSet = the nodes that need to be visualized on the screen (if they aren't already visualized)
    //fullGeomSet = all currently attached geoms
    //bad geoms = exisiting geoms that need to be removed
    //good geoms = existing geoms that need to stay
    //good nodes = current nodes attached to the good geoms
    //new nodes = current nodes that aren't associated with a good geom (and thus need a geom to be created for them).


    Set<Geom> nodeGeoms = DataUtils.getGeomsFromNodes(nodeSet, this.geoms);
    

    Set<Geom> fullGeomSet = new HashSet<Geom>(this.geoms);

    //1. remove Geoms that should not be on the screen anymore
    Set<Geom> badGeoms = Utils.complementSets(fullGeomSet, nodeGeoms);

    removeGeoms(badGeoms);

    //2. make new Geoms for Nodes not already on screen and then add them to the screen
    Set<Geom> goodGeoms = Utils.intersectSets(fullGeomSet, nodeGeoms);
    Set<Node> goodNodes = DataUtils.getNodesFromGeoms(goodGeoms);
    Set<Node> newNodes = Utils.complementSets(nodeSet, goodNodes);

    for (Node nn : newNodes)
    {
      addGeomNode(nn);
    }
    
  }
}
