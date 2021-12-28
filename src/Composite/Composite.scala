package Composite

import scala.collection.mutable.ListBuffer
import java.util
import scala.jdk.CollectionConverters.CollectionHasAsScala

import Visitor.ScalaVisitor
import Part_1.DataFrameCSV

/**
 * Scala DataFrame logic trait
 */
trait DataFrameScala {
  def accept(visitor: ScalaVisitor): Unit

  def at(row: Int, label: String): String
  def columns(): Int
  def size(): Int
  def printDataFrame(): Unit
  def printDataFrame(x: Int): Int
  def getColByLabel(label: String): Int
  def getLabels: List[String]
  def setColumns(x: Int): Unit
}

/**
 * Basic trait to test multiple inheritance
 */
trait Talkable {
  def talk():String
}

  /**
   * Basic trait to test multiple inheritance
   */
  trait Speaker {
    def speak():String
  }

/**
 * Class to be used as a Proxy for the AbstractDataFrame Java class. Every call for an AbstractDataFrame method is made
 * on this class and then invoked for AbstractDataFrame implementation in Java.
 * @param path Path of the file.
 */
class ProxyFile(val path:String) extends DataFrameScala with Talkable with Speaker {
  val source: String = path
  val df = new DataFrameCSV(path)

  //----------------------------
  //--- Multiple inheritance ---
  //----------------------------
  override def talk():String = "Hey! I'm a ProxyFile!"
  override def speak():String = "Hey! I am able to speak!"

  //-------------------------
  //--- Structure methods ---
  //-------------------------
  def openDataFrame(): Unit = {
    df.openDataFrame()
  }

  /**
   * This method is only used by a CompositeDataFrame, in order to handle the logic relating to the number of columns.
   *
   * @param x The value to be assigned to columns.
   */
  def setColumns(x: Int): Unit = {
    df.setColumns(x)
  }

  //-----------------------
  //--- Main operations ---
  //-----------------------
  /**
   *
   * @param row The row where the value is.
   * @param label The label where the value is
   * @return The value located at a given row and column.
   */
  def at(row:Int, label:String): String = df.at(row,label)

  /**
   * @param row    The row where the value is.
   * @param column The column where the value is.
   * @return The value located at a given row and column.
   */
  def iat(row:Int, column:Int): String = df.iat(row,column)

  /**
   * @return The columns of a DataFrame.
   */
  def columns(): Int = df.columns()

  /**
   * @return The size (rows) of the DataFrame.
   */
  def size(): Int = df.size()

  /**
   * Prints the DataFrame. Intented to be used for a unique AbstractDataFrame with its own IDs for each row.
   */
  def printDataFrame(): Unit = {
    df.printDataFrame()
  }

  /**
   * Prints the DataFrame. Intented to be called by a CompositeDataFrame. For each row it prints the global ID in
   * the CompositeDataFrame, instead of the local ID from the file.
   *
   * @param x The first row's ID for this file. If this is the first file, x will be 0.
   * @return The first row's ID for the next file.
   */
  override def printDataFrame(x: Int): Int = df.printDataFrame(x)

  //----------------------------
  //--- Visitor logic method ---
  //----------------------------
  /**
   * Method to accept a Scala implemented visitor.
   *
   * @param v The visitor.
   */
  override def accept(v: ScalaVisitor): Unit = df.accept(v)

  //------------------------
  //--- Auxiliar methods ---
  //------------------------
  /**
   * Finds the corresponding column of a certain label
   * @param label The label which column is to be found.
   * @return The column number. -1 If the label does not exist.
   */
  def getColByLabel(label: String): Int = df.getColByLabel(label)

  /**
   * Returns the labels of the DataFrame
   * @return List with the labels.
   */
  def getLabels: List[String] = df.getLabels.asScala.toList

  /**
   * Makes a copy of a whole column of the DataFrame.
   * @param col The desired column.
   * @return The copy of the column.
   */
  def getListCol(col:Int): List[String] = df.getListCol(col).asScala.toList

  /**
   * Returns the data of the DataFrame.
   * @return Arraylist of String ArrayLists containing the data.
   */
  def getData: List[util.ArrayList[String]] = df.getData.asScala.toList
}

class Directory(val name:String) extends DataFrameScala {
  private val children: ListBuffer[DataFrameScala] = new ListBuffer[DataFrameScala]()
  var cols: Int = -1

  //-------------------------
  //--- Structure methods ---
  //-------------------------
  /**
   * Adds a DataFrame (child) to the list of children if it has a correct number of columns.
   * @param child The DataFrame to be added.
   */
  def addChild(child: DataFrameScala): Unit = {
    if(cols == -1) { //The number of columns has not been defined yet.
      children += child
      if(child.columns() != -1) cols = child.columns() //The number of columns is set to be the number of columns
      //of the first child
    } else if (child.columns() == -1) {  //The number of columns is already defined, but the new child is a
      //  CompositeDataFrame with the columns not defined.
      children += child
      child.setColumns(cols)
    } else if(child.columns() == cols) {  //The number of columns is already defined, and it is the same
      // as the child's number, so it can be added without modifications.
      children += child
    } else println("Error adding child")  //The number of columns is already defined, but the child's number of columns is different.
  }

  /**
   * Removes a child from the list.
   * @param child The child to be removed.
   */
  def removeChild(child: DataFrameScala): Unit = {
    children -= child
  }

  /**
   * This method is used in order to handle the logic relating to the number of columns.
   * @param x The value to be assigned to columns.
   */
  override def setColumns(x: Int): Unit = cols = x

  /**
   * @return The List of children.
   */
  def getChildren: ListBuffer[DataFrameScala] = children

  //-----------------------
  //--- Main operations ---
  //-----------------------
  /**
   * It calls the method at respect the global amount of data.
   * @param row   The global row where the value is.
   * @param label The label where the value is.
   * @return The value.
   */
  override def at(row: Int, label: String): String = {
    var global_size = 0
    var prev_size = 0
    var found = false
    var result = ""
    for(child <- children) {
      if(!found) {
        prev_size = global_size
        global_size += child.size()
        if(global_size > row) {
          found = true
          result = child.at(row-prev_size,label)
        }
      }
    }
    result
  }

  /**
   * @return The number of columns of the Composite, which is the children's column number.
   */
  def columns(): Int = cols

  /**
   * @return The size of the DataFrame, which is the sum of all the children's size.
   */
  def size(): Int = {
    var size = 0
    children.foreach(c => size += c.size())
    size
  }

  /**
   * Prints the DataFrames contained by the Composite. It initializes a value to work with the global IDs.
   */
  override def printDataFrame(): Unit = {
    var id = 0
    for(child <- children) {
      if(id==0) {
        print("\t\t|\t")
        for(label <- child.getLabels) {
          print(label+"\t|\t")
        }
        println()
      }
      id = child.printDataFrame(id)
    }
  }

  /**
   * This method is called when a Composite tries to print a child which is also a Composite. It works the same way as
   * the other printDataFrame method.
   * @param x The id of the final row of the previous file.
   * @return The first row's ID for the next file.
   */
  override def printDataFrame(x: Int): Int = {
    var y = x
    for(child <- children) {
      y = child.printDataFrame(y)
    }
    y
  }

  //----------------------------
  //--- Visitor logic method ---
  //----------------------------
  /**
   * Method to accept a Scala implemented visitor.
   * @param v The visitor.
   */
  override def accept(v:ScalaVisitor): Unit = v.visit(this)

  //------------------------
  //--- Auxiliar methods ---
  //------------------------
  /**
   * Finds the corresponding column of a certain label.
   * @param label The label which column is to be found.
   * @return The column number.
   */
  override def getColByLabel(label: String): Int = children.head.getColByLabel(label)

  /**
   * Returns the labels of the DataFrame
   * @return List with the labels.
   */
  override def getLabels: List[String] = children.head.getLabels
}
