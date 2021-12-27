package Visitor

import Composite.Directory
import Part_1.AbstractDataFrame


class CounterVisitor extends ScalaVisitor {
  var files = 0
  var directories = 0

  /**
   * @return Current visited files
   */
  def getFiles: Int = files

  /**
   * @return Current visited directories
   */
  def getDirectories: Int = directories

  /**
   * Implements the behavior of the visitor when visiting an AbstractDataFrame
   * @param df The AbstractDataFrame to be visited.
   */
  def visit(df:AbstractDataFrame): Unit = files += 1

  /**
   * Implements the behavior of the visitor when visiting a directory.
   * @param cdf The Directory to be visited.
   */
  override def visit(cdf:Directory): Unit = {
    directories += 1
    for(df <- cdf.getChildren) {
      df.accept(this)
    }
  }
}
