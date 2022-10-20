package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DelieverColaInput(amount: Int)
object DelieverColaInput extends ShapeTag.Companion[DelieverColaInput] {
  val id: ShapeId = ShapeId("playSmithy", "DelieverColaInput")

  val hints : Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[DelieverColaInput] = struct(
    int.required[DelieverColaInput]("amount", _.amount).addHints(smithy.api.Required()),
  ){
    DelieverColaInput.apply
  }.withId(id).addHints(hints)
}