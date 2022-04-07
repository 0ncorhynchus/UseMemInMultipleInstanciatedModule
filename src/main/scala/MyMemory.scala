import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class MyMemory(ADDR_WIDTH: Int, DATA_WIDTH: Int, filePath: String) extends Module {
  val io = IO(new Bundle {
    val en = Input(Bool())
    val adr = Input(UInt(ADDR_WIDTH.W))
    val data = Output(UInt(DATA_WIDTH.W))
  })

  val DEPTH = 1 << ADDR_WIDTH
  val mem = Mem(DEPTH, UInt(DATA_WIDTH.W))
  loadMemoryFromFile(mem, filePath)

  val reg = RegInit(0.U(DATA_WIDTH.W))

  when (io.en) {
    reg := mem(io.adr)
    // printf(p"${filePath}(${io.adr}) => ${Hexadecimal(mem(io.adr))}\n")
  }

  io.data := reg
}
