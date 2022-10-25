package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BuyColaInput(amount: Int, bankAccountName: String)
object BuyColaInput extends ShapeTag.Companion[BuyColaInput] {
  val id: ShapeId = ShapeId("playSmithy", "BuyColaInput")

  val hints : Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[BuyColaInput] = struct(
    int.required[BuyColaInput]("amount", _.amount).addHints(smithy.api.Required()),
    string.required[BuyColaInput]("bankAccountName", _.bankAccountName).addHints(smithy.api.Required()),
  ){
    BuyColaInput.apply
  }.withId(id).addHints(hints)
}