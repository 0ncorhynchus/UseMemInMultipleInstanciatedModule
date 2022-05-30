import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class UseMyMemoryTest extends AnyFlatSpec with ChiselScalatestTester {
  val DATA_WIDTH = 16
  val ADDR_WIDTH = 4
  val DEPTH = 1 << ADDR_WIDTH

  it should "work" in {
    test(new UseMyMemory(ADDR_WIDTH, DATA_WIDTH, "data0.hex", "data1.hex")) { c =>
      c.io.en.poke(true.B)

      for (adr <- 0 until DEPTH) {
        c.io.adr0.poke(adr.U)
        c.io.adr1.poke(adr.U)

        c.clock.step()

        c.io.data0.expect(adr.U)
        c.io.data1.expect((16 * adr).U)
      }
    }
  }
}
