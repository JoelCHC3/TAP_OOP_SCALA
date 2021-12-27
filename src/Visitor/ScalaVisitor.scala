package Visitor
import Composite.Directory
import Part_1.AbstractDataFrame

abstract class ScalaVisitor {
  def visit(df:AbstractDataFrame): Unit
  def visit(cdf:Directory): Unit = {
    for(df <- cdf.getChildren) {
      df.accept(this)
    }
  }
}
