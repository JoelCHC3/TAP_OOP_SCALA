package Visitor

import Part_1.AbstractDataFrame


class SizeVisitor extends ScalaVisitor {
  var size = 0
  def getSize: Int = size

  /**
   * Implements the behavior of the visitor when visiting an AbstractDataFrame
   * @param df The AbstractDataFrame to be visited.
   */
  def visit(df:AbstractDataFrame): Unit = size += df.size()
}
