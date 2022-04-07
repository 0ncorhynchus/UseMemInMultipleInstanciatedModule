import chisel3._
import chisel3.util._

class UseMyMemory(ADDR_WIDTH: Int, DATA_WIDTH: Int, filePath0: String, filePath1: String) extends Module {
  val io = IO(new Bundle {
    val en = Input(Bool())
    val adr0 = Input(UInt(ADDR_WIDTH.W))
    val adr1 = Input(UInt(ADDR_WIDTH.W))
    val data0 = Output(UInt(DATA_WIDTH.W))
    val data1 = Output(UInt(DATA_WIDTH.W))
  })

  val mem0 = Module(new MyMemory(ADDR_WIDTH, DATA_WIDTH, filePath0))
  mem0.io.en := io.en
  mem0.io.adr := io.adr0
  io.data0 := mem0.io.data

  val mem1 = Module(new MyMemory(ADDR_WIDTH, DATA_WIDTH, filePath1))
  mem1.io.en := io.en
  mem1.io.adr := io.adr1
  io.data1 := mem1.io.data
}
