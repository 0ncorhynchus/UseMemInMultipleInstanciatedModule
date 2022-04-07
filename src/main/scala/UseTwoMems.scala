import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class UseTwoMems(ADDR_WIDTH: Int, DATA_WIDTH: Int, filePath0: String, filePath1: String) extends Module {
  val io = IO(new Bundle {
    val en = Input(Bool())
    val adr0 = Input(UInt(ADDR_WIDTH.W))
    val adr1 = Input(UInt(ADDR_WIDTH.W))
    val data0 = Output(UInt(DATA_WIDTH.W))
    val data1 = Output(UInt(DATA_WIDTH.W))
  })

  val DEPTH = 1 << ADDR_WIDTH
  val mem0 = Mem(DEPTH, UInt(DATA_WIDTH.W))
  val mem1 = Mem(DEPTH, UInt(DATA_WIDTH.W))
  loadMemoryFromFile(mem0, filePath0)
  loadMemoryFromFile(mem1, filePath1)

  val reg0 = RegInit(0.U(DATA_WIDTH.W))
  val reg1 = RegInit(0.U(DATA_WIDTH.W))

  when (io.en) {
    reg0 := mem0(io.adr0)
    reg1 := mem1(io.adr1)
  }

  io.data0 := reg0
  io.data1 := reg1
}

