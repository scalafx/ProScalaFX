package proscalafx.ch03

import scalafx.beans.property.DoubleProperty
import scalafx.beans.property.FloatProperty
import scalafx.beans.property.IntegerProperty
import scalafx.beans.property.LongProperty

object NumericPropertiesExample extends App {
  val i = new IntegerProperty(null, "i", 1024)
  val l = new LongProperty(null, "l", 0L)
  val f = new FloatProperty(null, "f", 0.0F)
  val d = new DoubleProperty(null, "d", 0.0)
  println("Constructed numerical properties i, l, f, d.")

  println("i.get = " + i.get)
  println("l.get = " + l.get)
  println("f.get = " + f.get)
  println("d.get = " + d.get)

  l <== i
  f <== l
  d <== f
  println("Bound l to i, f to l, d to f.")

  println("i.get = " + i.get)
  println("l.get = " + l.get)
  println("f.get = " + f.get)
  println("d.get = " + d.get)

  println("Calling i.set(2048).")
  i() = 2048

  println("i.get = " + i.get)
  println("l.get = " + l.get)
  println("f.get = " + f.get)
  println("d.get = " + d.get)

  d.unbind
  f.unbind
  l.unbind
  println("Unbound l to i, f to l, d to f.")

  f <== d
  l <== f
  i <== l
  println("Bound f to d, l to f, i to l.")

  println("Calling d.set(10000000000L).")
  d() = 10000000000L

  println("d.get = " + d.get)
  println("f.get = " + f.get)
  println("l.get = " + l.get)
  println("i.get = " + i.get)
}