package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeliverColaOutput(newStorage: Int)
object DeliverColaOutput extends ShapeTag.Companion[DeliverColaOutput] {
  val id: ShapeId = ShapeId("playSmithy", "DeliverColaOutput")

  val hints : Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[DeliverColaOutput] = struct(
    int.required[DeliverColaOutput]("newStorage", _.newStorage).addHints(smithy.api.Required()),
  ){
    DeliverColaOutput.apply
  }.withId(id).addHints(hints)
}