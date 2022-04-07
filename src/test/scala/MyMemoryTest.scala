import chisel3._
import chisel3.util._
import chiseltest._
import java.io.{File, PrintWriter}
import org.scalatest.flatspec.AnyFlatSpec
import scala.util._

class MyMemoryTest extends AnyFlatSpec with ChiselScalatestTester {
  val DATA_WIDTH = 16
  val ADDR_WIDTH = 4
  val DEPTH = 1 << ADDR_WIDTH

  val rng = new Random()

  def mask(width: Int) = (1 << width) - 1

  var table = new Array[Int](DEPTH)
  for (adr <- 0 until DEPTH) {
    table(adr) = rng.nextInt & mask(DATA_WIDTH)
  }

  val memFile = File.createTempFile("MyMemoryTest", ".hex")
  new PrintWriter(memFile) {
    try {
      for (adr <- 0 until DEPTH) {
        println("%08X".format(table(adr)))
      }
    } finally {
      close()
    }
  }

  println("memFile : " + memFile.getPath())

  it should "work" in {
    test(new MyMemory(ADDR_WIDTH, DATA_WIDTH, memFile.getPath())) { c =>
      c.io.en.poke(true.B)
      c.clock.step()

      var prev = 0
      for (adr <- 0 until DEPTH) {
        c.io.adr.poke(adr.U)
        c.clock.step()
        c.io.data.expect(table(adr).U)
      }

      memFile.delete()
    }
  }
}
