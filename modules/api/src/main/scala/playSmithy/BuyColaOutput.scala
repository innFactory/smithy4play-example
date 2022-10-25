package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class BuyColaOutput(oldBankBalance: Double, price: Double, newBankBalance: Double, storage: Int)
object BuyColaOutput extends ShapeTag.Companion[BuyColaOutput] {
  val id: ShapeId = ShapeId("playSmithy", "BuyColaOutput")

  val hints : Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[BuyColaOutput] = struct(
    double.required[BuyColaOutput]("oldBankBalance", _.oldBankBalance).addHints(smithy.api.Required()),
    double.required[BuyColaOutput]("price", _.price).addHints(smithy.api.Required()),
    double.required[BuyColaOutput]("newBankBalance", _.newBankBalance).addHints(smithy.api.Required()),
    int.required[BuyColaOutput]("storage", _.storage).addHints(smithy.api.Required()),
  ){
    BuyColaOutput.apply
  }.withId(id).addHints(hints)
}