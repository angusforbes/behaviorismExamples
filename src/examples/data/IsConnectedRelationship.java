/* VideoNode.java ~ Oct 18, 2009 */

package examples.data;

import behaviorism.data.Category;
import behaviorism.data.Relationship;

/**
 *
 * @author angus
 */
public class IsConnectedRelationship extends Relationship
{
 public static Category isConnectedCategory = new Category("is connected to");

 public IsConnectedRelationship()
 {
  super(isConnectedCategory);
 }

 public IsConnectedRelationship(double weight)
 {
  super(isConnectedCategory, weight);
 }
}
