package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DelieverColaOutput(newStorage: Int)
object DelieverColaOutput extends ShapeTag.Companion[DelieverColaOutput] {
  val id: ShapeId = ShapeId("playSmithy", "DelieverColaOutput")

  val hints : Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[DelieverColaOutput] = struct(
    int.required[DelieverColaOutput]("newStorage", _.newStorage).addHints(smithy.api.Required()),
  ){
    DelieverColaOutput.apply
  }.withId(id).addHints(hints)
}