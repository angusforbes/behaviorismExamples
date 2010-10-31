/* CommentWordCountAnalyzer.java ~ Oct 20, 2009 */

package examples.data;

import behaviorism.data.analyzers.CollectorAnalyzer;
import behaviorism.data.Node;
import behaviorism.data.collectors.Collector;
import java.util.Set;

/**
 *
 * @author angus
 */
public class CommentWordCountAnalyzer extends CollectorAnalyzer
{
  public int totalWords = 0;
  public double avgWords = -1;

  public CommentWordCountAnalyzer(Collector collector)
  {
    this.collector = collector;
  }

  public void analyze()
  {
    Set<Node> commentNodes = collector.collect();
    for (Node d : commentNodes )
    {
       if (d.category == CommentNode.commentCategory)
      {
      totalWords += CommentAnalyzer.countWords((CommentNode)d);
       }
    }

    avgWords = (double) totalWords / (double) commentNodes.size();

  }


}
