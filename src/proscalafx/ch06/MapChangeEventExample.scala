/*
 * Copyright (c) 2000-$today.year Jarek Sacha. All Rights Reserved.
 *
 * Author's e-mail: jarek at ieee.org
 *
 */
package proscalafx.ch06


import scalafx.collections.ObservableMap.{Change, Remove, Replace, Add}
import scalafx.collections.{ObservableMap, ObservableHashMap}


/** Example of processing of "change" notifications from ScalaFX `ObservableMap`, wrapper for JavaFX `ObservableMap`.
  *
  * ScalaFX used a different way of passing information about modification to `ObservableMap`.
  * Each modification is represented by a [[scalafx.collections.ObservableMap.Change]] object.
  *
  * @author Jarek Sacha
  */
object MapChangeEventExample extends App {

  // `println` statements show JavaFX API, for easier comparison to ScalaFX API used in the code.


  val myListener = (source: ObservableMap[String, Int], change: Change[String, Int]) => {
    println("\tmap = " + source)
    println("\tChange event data:")
    println(
      change match {
        case Add(key, added)              => "\t\tAdded: " + key + "->" + added
        case Remove(key, removed)         => "\t\tRemoved: " + key + "->" + removed
        case Replace(key, added, removed) => "\t\tReplaced: " + key + "->" + removed + "->" + added
      }
    )
    println()
  }

  val map = new ObservableHashMap[String, Int]()
  map.onChange(myListener)

  println( """Calling put("First", 1): """)
  map.put("First", 1)

  println( """Calling put("First", 100): """)
  map.put("First", 100)

  println("Calling putAll(anotherMap): ")
  val anotherMap = Map(
    "Second" -> 2,
    "Third" -> 3
  )
  map ++= anotherMap

  println( """Remove elements with key == "Second"""")
  map -= "Second"

  println("Remove elements with value == 3")
  map --= map.filter {case (_, v) => {v == 3}}.keys
}
