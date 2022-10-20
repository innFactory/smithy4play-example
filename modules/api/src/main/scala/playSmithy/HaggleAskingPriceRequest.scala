package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class HaggleAskingPriceRequest(price: Double, name: String)
object HaggleAskingPriceRequest extends ShapeTag.Companion[HaggleAskingPriceRequest] {
  val id: ShapeId = ShapeId("playSmithy", "HaggleAskingPriceRequest")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[HaggleAskingPriceRequest] = struct(
    double.required[HaggleAskingPriceRequest]("price", _.price).addHints(smithy.api.Required()),
    string.required[HaggleAskingPriceRequest]("name", _.name).addHints(smithy.api.Required()),
  ){
    HaggleAskingPriceRequest.apply
  }.withId(id).addHints(hints)
}