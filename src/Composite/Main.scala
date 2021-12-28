package Composite

import Visitor.{CounterVisitor, SizeVisitor, FilterVisitor}

import java.lang.Math.round
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

object Main extends scala.App{

  compositeTest()
  visitorTest(makeComposite())
  recursionCurryForTest()
  foldTabulateTest()
  multipleInheritanceTest()

  /**
   * Tests the creation of a Composite DataFrame
   */
  def compositeTest(): Unit = {
    println("--- RUNNING COMPOSITE TEST ---")
    val root = makeComposite()
    root.printDataFrame()
    println("\nAt row 127 label state: "+root.at(127,"State"))
    println("Number of columns: "+root.columns())
    println("Size: "+root.size())
  }

  /**
   * Tests the visitors
   * @param dir The direcory to accept the visitors.
   */
  def visitorTest(dir: Directory): Unit = {
    println("\n--- RUNNING VISITOR TEST ---")
    val vs = new SizeVisitor()
    dir.accept(vs)
    println("Size with visitor: "+vs.getSize)

    val vc = new CounterVisitor()
    dir.accept(vc)
    println("Files visited: "+vc.getFiles+"\tDirectories visited: "+vc.getDirectories)

    //Conditional functions to filter the values
    def greaterThan(x: Any): Boolean = {
      val y: String = x.asInstanceOf[String]
      y.toInt > 40
    }

    def equalString(x: Any): Boolean = {
      x.equals("OH")
    }

    val vfNum = new FilterVisitor(greaterThan, 1)
    dir.accept(vfNum)
    println("Filtered: column 1 value greater than 40 ")
    for(e <- vfNum.elements) {
      println(e)
    }

    val vfString = new FilterVisitor(equalString, 10)
    dir.accept(vfString)
    println("\nFiltered: column 10 value equals OH")
    for(e <- vfString.elements) {
      println(e)
    }
  }

  /**
   * Tests tail and stack recursion, currying for the recursive functions and the use of for.
   */
  def recursionCurryForTest(): Unit = {
    println("\n--- RUNNING RECURSION TEST ---")

    /**
     * Tail recursion function.
     * @param l List to be filtered.
     * @param f Condition to filter by.
     * @param m Operation to be apllied to the filtered values.
     * @tparam A Generic type for input.
     * @tparam B Generic type for output.
     * @return Filtered list.
     */
    def listFilterMapTail[A,B](l:List[A],f:A=>Boolean,m:A=>B):List[B]= {
      @tailrec
      def opAccumulator(l:List[A],f:A=>Boolean,m:A=>B,acc:List[B]): List[B] = {
        l match {
          case Nil => acc
          case x :: tail =>
            if(f(x)) {
              val list = acc ::: m(x) :: Nil
              opAccumulator(tail,f,m,list)
            } else {
              opAccumulator(tail, f, m, acc)
            }
        }
      }
      opAccumulator(l,f,m,Nil)
    }

    /**
     * Stack recursion function.
     * @param l List to be filtered.
     * @param f Condition to filter by.
     * @param m Operation to be apllied to the filtered values.
     * @tparam A Generic type for input.
     * @tparam B Generic type for output.
     * @return Filtered list.
     */
    def listFilterMapStack [A,B](l:List[A],f:A=>Boolean,m:A=>B):List[B]= {
      l match {
        case Nil => Nil
        case x :: tail =>
          if(f(x)){
            m(x) :: listFilterMapStack(tail,f,m)
          } else {
            listFilterMapStack(tail,f,m)
          }
      }
    }

    //Conditions to filter by
    def bThan(x:Any): Boolean = {
      val y: Float = x.asInstanceOf[Float]
      y > 45.0
    }

    def containsWord(x:Any): Boolean = {
      val y: String = x.asInstanceOf[String]
      y.contains("City")
    }

    //Functions to be applied to the filtered values
    def roundVal(x:Any): Any = {
      val y: Float = x.asInstanceOf[Float]
      val z: Int = round(y)
      z
    }

    def replaceWord(x:Any): Any = {
      val y: String = x.asInstanceOf[String]
      val z = y.replace("City","Ciutat")
      z
    }

    //Use of recursion for a float List
    val file1 = new ProxyFile("C:/Users/joelc/Desktop/cities_float.csv")
    file1.openDataFrame()
    val column = file1.getListCol(1)
    val columnValues = column.tail  //Getting rid of the first value (the label)
    val floatList: ListBuffer[Float] = new ListBuffer[Float]()
    for(floatVal <- columnValues) {
      floatList += floatVal.toFloat
    }

    println("--- Tail recursion to round values of a floating-point column ---")
    val tailRecFloat = listFilterMapTail(floatList.toList,bThan,roundVal)
    for(a <- tailRecFloat) println(a)
    println("\n--- Stack recursion to round values of a floating-point column ---")
    val stackRecFloat = listFilterMapStack(floatList.toList,bThan,roundVal)
    for(a <- stackRecFloat) println(a)

    //Use of recursion for a string list
    val file2 = new ProxyFile("C:/Users/joelc/Desktop/cities.csv")
    file2.openDataFrame()
    val column2 = file2.getListCol(9)
    val column2Values = column2.tail  //Getting rid of the first value (the label)
    val stringList: ListBuffer[String] = new ListBuffer[String]()
    for(stringVal <- column2Values) {
      stringList += stringVal
    }

    println("\n--- Tail recursion to replace a certain word ---")
    val tailRecString = listFilterMapTail(stringList.toList,containsWord,replaceWord)
    for(a <- tailRecString) println(a)

    println("\n--- Stack recursion to replace a certain word ---")
    val stackRecString = listFilterMapStack(stringList.toList,containsWord,replaceWord)
    for(a <- stackRecString) println(a)

    //--- For use --- Same result as in the recursion part for the string list.
    val z = for(s <- stringList; if containsWord(s)) yield replaceWord(s)
    println("\n--- For example --- ")
    for(a <- z) println(a)

    //--- Currying ---

    /**
     * Stack recursion function supporting currying.
     * @param l List to be filtered.
     * @param f Condition to filter by.
     * @param m Operation to be apllied to the filtered values.
     * @tparam A Generic type for input.
     * @tparam B Generic type for output.
     * @return Filtered list.
     */
    def listFilterMapStackCurry [A,B](l:List[A])(f:A=>Boolean)(m:A=>B):List[B]= {
      l match {
        case Nil => Nil
        case x :: tail =>
          if(f(x)){
            m(x) :: listFilterMapStack(tail,f,m)
          } else {
            listFilterMapStack(tail,f,m)
          }
      }
    }

    println("\n--- Currying example ---")
    println("Currying the first parameter")
    val curryList1 = listFilterMapStackCurry(_:List[Float])(bThan)(roundVal)
    val auxCurry1 = curryList1(floatList.toList)
    for(a <- auxCurry1) println(a)

    println("\nCurrying the second parameter")
    val curryList2 = listFilterMapStackCurry(floatList.toList)(_:Any=>Boolean)(roundVal)
    val auxCurry2 = curryList2(bThan)
    for(a <- auxCurry2) println(a)

    println("\nCurrying the third parameter")
    val curryList3 = listFilterMapStackCurry(floatList.toList)(bThan)(_:Any=>Any)
    val auxCurry3 = curryList3(roundVal)
    for(a <- auxCurry3) println(a)
  }

  /**
   * Tests the uses and differences of folding functions and tabulate.
   */
  def foldTabulateTest(): Unit = {
    //--- Fold and tabulate ---
    val valueList = 47 :: (49 :: (50 :: (48 :: (47 :: Nil))))
    def mult2(x:Int): Int = x*2 //Function to be used as binary operator when folding

    println("\n--- Fold and tabulate example ---")
    println("Sample list: "+valueList)
    println("FoldLeft: "+valueList.foldLeft(0)((x,y)=> mult2(x)+mult2(y)))
    println("FoldRight: "+valueList.foldRight(0)((x,y)=>mult2(x)+mult2(y)))
    println("Fold: "+valueList.fold(0)((x,y)=>mult2(x)+mult2(y)))

    val tabList = List.tabulate(7)(n=>mult2(n))
    println("List created by tabulating: "+tabList)
  }

  /**
   * Tests the uses and results of multiple inheritance.
   */
  def multipleInheritanceTest(): Unit = {
    println("\n--- Multiple inheritance example ---")
    val file1 = new ProxyFile("C:/Users/joelc/Desktop/cities_float.csv")
    //--- Multiple inheritance ---
    println(file1.talk())
    println(file1.speak())

    trait Classified {def condition():String = "By the way, I'm classified."}
    val file2 = new ProxyFile("C:/Users/joelc/Desktop/cities_float.csv") with Classified
    println("\n"+file2.talk())
    println(file2.speak())
    println(file2.condition())
  }

  /**
   * Creates a CompositeDataFrame for the default directories structure. That structure is:
   * root
   * |- dfFile_root_1
   * |- dfFile_root_2
   * |- A
   *    |- dfFile_A_1
   *    |- dfFile_A_2
   *    |- B
   *       |- dfFile_B_1
   *       |- dfFile_B_1
   * The size of the CompositeDataFrame is 128.
   * @return The root Directory
   */
  def makeComposite(): Directory = {
    val rootFile1 = new ProxyFile("C:/Users/joelc/Desktop/root/dfFile_root_1.csv")
    val rootFile2 = new ProxyFile("C:/Users/joelc/Desktop/root/dfFile_root_2.csv")
    rootFile1.openDataFrame()
    rootFile2.openDataFrame()
    val root = new Directory("root")
    root.addChild(rootFile1)
    root.addChild(rootFile2)

    val dirA = new Directory("A")
    val AFile1 = new ProxyFile("C:/Users/joelc/Desktop/root/A/dfFile_A_1.csv")
    val AFile2 = new ProxyFile("C:/Users/joelc/Desktop/root/A/dfFile_A_2.csv")
    AFile1.openDataFrame()
    AFile2.openDataFrame()
    dirA.addChild(AFile1)
    dirA.addChild(AFile2)

    val dirB = new Directory("B")
    val BFile1 = new ProxyFile("C:/Users/joelc/Desktop/root/A/B/dfFile_B_1.csv")
    val BFile2 = new ProxyFile("C:/Users/joelc/Desktop/root/A/B/dfFile_B_2.csv")
    BFile1.openDataFrame()
    BFile2.openDataFrame()
    dirB.addChild(BFile1)
    dirB.addChild(BFile2)
    dirA.addChild(dirB)

    root.addChild(dirA)

    root
  }
}
