import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class MyMemoryTest extends AnyFlatSpec with ChiselScalatestTester {
  val DATA_WIDTH = 16
  val ADDR_WIDTH = 4
  val DEPTH = 1 << ADDR_WIDTH

  it should "work" in {
    test(new MyMemory(ADDR_WIDTH, DATA_WIDTH, "data0.hex")) { c =>
      c.io.en.poke(true.B)

      for (adr <- 0 until DEPTH) {
        c.io.adr.poke(adr.U)
        c.clock.step()
        c.io.data.expect(adr.U)
      }
    }
  }
}
