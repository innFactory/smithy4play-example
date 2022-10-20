package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class HaggleAskingPriceOutput(price: Option[Double] = None, message: Option[String] = None)
object HaggleAskingPriceOutput extends ShapeTag.Companion[HaggleAskingPriceOutput] {
  val id: ShapeId = ShapeId("playSmithy", "HaggleAskingPriceOutput")

  val hints : Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[HaggleAskingPriceOutput] = struct(
    double.optional[HaggleAskingPriceOutput]("price", _.price),
    string.optional[HaggleAskingPriceOutput]("message", _.message),
  ){
    HaggleAskingPriceOutput.apply
  }.withId(id).addHints(hints)
}