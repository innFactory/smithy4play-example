package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class TransferInput(amount: Double, name: String)
object TransferInput extends ShapeTag.Companion[TransferInput] {
  val id: ShapeId = ShapeId("playSmithy", "TransferInput")

  val hints : Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[TransferInput] = struct(
    double.required[TransferInput]("amount", _.amount).addHints(smithy.api.Required()),
    string.required[TransferInput]("name", _.name).addHints(smithy.api.Required()),
  ){
    TransferInput.apply
  }.withId(id).addHints(hints)
}