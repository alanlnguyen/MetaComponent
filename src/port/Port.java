/**
 * A port of any kind that belongs to a parent component.
 */

package port;

import component.Component;

public interface Port<T> {
   public Component<T> getParent();

}
