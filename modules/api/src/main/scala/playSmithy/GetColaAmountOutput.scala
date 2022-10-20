package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GetColaAmountOutput(amount: Int)
object GetColaAmountOutput extends ShapeTag.Companion[GetColaAmountOutput] {
  val id: ShapeId = ShapeId("playSmithy", "GetColaAmountOutput")

  val hints : Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[GetColaAmountOutput] = struct(
    int.required[GetColaAmountOutput]("amount", _.amount).addHints(smithy.api.Required()),
  ){
    GetColaAmountOutput.apply
  }.withId(id).addHints(hints)
}