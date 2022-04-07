import chisel3._
import chisel3.util._
import chiseltest._
import java.io.{File, PrintWriter}
import org.scalatest.flatspec.AnyFlatSpec
import scala.util._

class UseMyMemoryTest extends AnyFlatSpec with ChiselScalatestTester {
  val DATA_WIDTH = 16
  val ADDR_WIDTH = 4
  val DEPTH = 1 << ADDR_WIDTH

  val rng = new Random()

  def mask(width: Int) = (1 << width) - 1

  var table0 = new Array[Int](DEPTH)
  var table1 = new Array[Int](DEPTH)
  for (adr <- 0 until DEPTH) {
    table0(adr) = rng.nextInt & mask(DATA_WIDTH)
    table1(adr) = rng.nextInt & mask(DATA_WIDTH)
  }

  val memFile0 = File.createTempFile("UseMyMemoryTest", ".hex")
  val memFile1 = File.createTempFile("UseMyMemoryTest", ".hex")
  new PrintWriter(memFile0) {
    try {
      for (adr <- 0 until DEPTH) {
        println("%08X".format(table0(adr)))
      }
    } finally {
      close()
    }
  }
  new PrintWriter(memFile1) {
    try {
      for (adr <- 0 until DEPTH) {
        println("%08X".format(table1(adr)))
      }
    } finally {
      close()
    }
  }

  println("memFile0: " + memFile0.getPath())
  println("memFile1: " + memFile1.getPath())

  it should "work" in {
    test(new UseMyMemory(ADDR_WIDTH, DATA_WIDTH, memFile0.getPath(), memFile1.getPath())) { c =>
      c.io.en.poke(true.B)

      for (adr <- 0 until DEPTH) {
        c.io.adr0.poke(adr.U)
        c.io.adr1.poke(adr.U)

        c.clock.step()

        c.io.data0.expect(table0(adr).U)
        c.io.data1.expect(table1(adr).U)
      }

      memFile0.delete()
      memFile1.delete()
    }
  }
}
