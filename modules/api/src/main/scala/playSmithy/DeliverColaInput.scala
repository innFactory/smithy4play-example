package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeliverColaInput(amount: Int)
object DeliverColaInput extends ShapeTag.Companion[DeliverColaInput] {
  val id: ShapeId = ShapeId("playSmithy", "DeliverColaInput")

  val hints : Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[DeliverColaInput] = struct(
    int.required[DeliverColaInput]("amount", _.amount).addHints(smithy.api.Required()),
  ){
    DeliverColaInput.apply
  }.withId(id).addHints(hints)
}