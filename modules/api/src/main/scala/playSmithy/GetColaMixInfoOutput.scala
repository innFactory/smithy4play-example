package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class GetColaMixInfoOutput(amount: Int, price: Double)
object GetColaMixInfoOutput extends ShapeTag.Companion[GetColaMixInfoOutput] {
  val id: ShapeId = ShapeId("playSmithy", "GetColaMixInfoOutput")

  val hints : Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[GetColaMixInfoOutput] = struct(
    int.required[GetColaMixInfoOutput]("amount", _.amount).addHints(smithy.api.Required()),
    double.required[GetColaMixInfoOutput]("price", _.price).addHints(smithy.api.Required()),
  ){
    GetColaMixInfoOutput.apply
  }.withId(id).addHints(hints)
}