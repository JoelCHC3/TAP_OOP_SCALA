# TAP_OOP_SCALA
This project combines the first and second assignment of Andvanced Programming
Techniques (TAP) subject, those being TAP_OOP and TAP_SCALA.

== OOP Part ==    
Design of a DataFrame library in Java using Design Patterns. It accepts CSV, JSON
and .txt files (.txt files must have a first line with only the delimiter between
sharps).

= Part 1 - DATA FILES + FACTORY =   
Implementations for each type of file and its creation with the factory pattern.   
Unit tests and main program for this part.

= Part 2 - COMPOSITE =   
Implementation of the composite pattern in order to create composite DataFrames
that contain several files in a directory.   
DataFrame Interface implemented by CompositeDataFrame and AbstractDataFrame (last
one being in Part_1 package).   
Unit tests and main program for this part.   

= Part 4 - STREAMS AND MAPREDUCE =   
Use of Java Streams and funtional programming. MapReduce computing design pattern
to work over a collection of DataFrames.   
Unit tests and main program for this part.

= Part 5 - VISITOR =   
Adding of the visitor pattern to the Composite structure.   
Unit tests and main program for this part.

= Part 6 - OBSERVER AND DYNAMIC PROXY =   
Use of a dynamic proxy to create an interceptor of a DataFrame.   
Use of an observer pattern to enable its subscription to the interceptor.   
Unit tests and main program for this part.

== SCALA Part ==      
Design of a layer in Scala that interacts with the Java implementation of the
DataFrame library.

= Composite =   
Reimplementation of the composite pattern. Leaf objects are proxy objects to the
Java implementation of an AbstractDataFrame.   
Main program for the Scala part includes the testing for:
- Composite implementation.
- Visitor implementation.
- Recursion: Tail and stack recursion with a listFilterMap function.
- Multiple inheritance.
- For loops.
- Fold and tabulate use.
- Curry and partial parametrization for the listFilerMap function.

= Visitor =      
Implementation of the visitor pattern to be combined with the Scala composite.
