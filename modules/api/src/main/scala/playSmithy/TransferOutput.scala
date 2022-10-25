package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class TransferOutput(balance: Double, name: String, message: Option[String] = None) extends AccountOutput
object TransferOutput extends ShapeTag.Companion[TransferOutput] {
  val id: ShapeId = ShapeId("playSmithy", "TransferOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[TransferOutput] = struct(
    double.required[TransferOutput]("balance", _.balance).addHints(smithy.api.Required()),
    string.required[TransferOutput]("name", _.name).addHints(smithy.api.Required()),
    string.optional[TransferOutput]("message", _.message),
  ){
    TransferOutput.apply
  }.withId(id).addHints(hints)
}