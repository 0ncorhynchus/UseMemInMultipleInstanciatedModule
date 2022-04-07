import chisel3._
import chisel3.stage.ChiselStage
import java.io.{File, PrintWriter}
import scala.util._

object Driver extends App {
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

  val memFile0 = File.createTempFile("Driver", ".hex")
  val memFile1 = File.createTempFile("Driver", ".hex")
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

  (new ChiselStage) emitSystemVerilog(new UseMyMemory(ADDR_WIDTH, DATA_WIDTH, memFile0.getPath(), memFile1.getPath()), args)
  (new ChiselStage) emitSystemVerilog(new UseTwoMems(ADDR_WIDTH, DATA_WIDTH, memFile0.getPath(), memFile1.getPath()), args)
}
