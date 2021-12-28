package Visitor



import Part_1.AbstractDataFrame

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters.CollectionHasAsScala

class FilterVisitor(condition:Any => Boolean, column:Int) extends ScalaVisitor {

  var elements:ListBuffer[String] = new ListBuffer[String]
  var firstChildren = 0
  var globalId = 0

  /**
   * Prints the filtered elements
   */
  def printElements(): Unit = {
    for(elem <- elements) {
      println(elem)
    }
  }

  /**
   * Implements the behavior of the visitor when visiting an AbstractDataFrame
   * @param df The AbstractDataFrame to be visited.
   */
  def visit(df:AbstractDataFrame): Unit = {
    val listaux = df.getListCol(column).asScala.toList
    val list = listaux.tail //Getting rid of the first value (the label)
    val data = df.getData
    val idList:ListBuffer[Int] = new ListBuffer[Int]

    val theList: ListBuffer[Any] = new ListBuffer[Any]()
    for(s <- list) {
      theList += s
    }

    var id = 0
    for(e <- theList) { //For every element, its ID is added if it fulfills the condition
      if(condition(e)) idList += id
      id += 1
    }

    val sb = new StringBuilder("\t")
    for(id <- idList) {
      sb ++= ""+(id+globalId)+"\t|\t"
      for(i <- 1 until data.size()) {
        sb ++= data.get(i).get(id+1)
        sb ++= "\t|\t"
      }
      elements += sb.toString()
      sb.delete(0,sb.size-1)
    }
    globalId += (data.get(0).size() - 1)
  }
}
