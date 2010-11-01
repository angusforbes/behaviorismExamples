/* CommentGenderAnalyzer.java ~ Oct 20, 2009 */

package examples.data;

import behaviorism.data.Node;
import behaviorism.data.analyzers.SetAnalyzer;
import java.util.Set;

/**
 *
 * @author angus
 */
public class CommentGenderAnalyzer extends SetAnalyzer
{
  public int maleWords = 0;
  public int femaleWords = 0;
  public int maleUsers = 0;
  public int femaleUsers = 0;

  public CommentGenderAnalyzer(Set<Node> set)
  {
    super(set);
  }

  public void analyze()
  {
    for (Node node : getSet())
    {
      CommentNode c = (CommentNode) node;

      //was it written by a male or female?
      Set<Node> attachedUsers = c.findByCategory(UserNode.userCategory); //should be exactly 1!
      
      if (attachedUsers.size() != 1)
      {
        System.err.println("in CommentGenderAnalyzer : analyze() : ERROR, who is the user???");
      }
      
      UserNode user = attachedUsers.toArray(new UserNode[0])[0];

      if (user.gender.equals("male"))
      {
        maleWords += c.comment.length();
        maleUsers += 1;
      }
      else
      {
        femaleWords += c.comment.length();
        femaleUsers += 1;
      }
    }
  }
}
