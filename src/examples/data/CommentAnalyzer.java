/* CommentAnalyzer.java ~ Oct 20, 2009 */

package examples.data;

import behaviorism.data.analyzers.NodeAnalyzer;
import behaviorism.data.Node;

/**
 *
 * @author angus
 */
public class CommentAnalyzer extends NodeAnalyzer
{
  int wordCount = -1; //actually character count at the moment...

  public void analyze(Node data)
  {
    if (data.category == CommentNode.commentCategory)
    {
      this.wordCount = countWords((CommentNode)data);
    }
  }

  public static int countWords(CommentNode node)
  {
   return node.comment.length();
  }
}
