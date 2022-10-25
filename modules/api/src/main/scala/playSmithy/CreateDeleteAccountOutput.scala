package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class CreateDeleteAccountOutput(balance: Double, name: String) extends AccountOutput
object CreateDeleteAccountOutput extends ShapeTag.Companion[CreateDeleteAccountOutput] {
  val id: ShapeId = ShapeId("playSmithy", "CreateDeleteAccountOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[CreateDeleteAccountOutput] = struct(
    double.required[CreateDeleteAccountOutput]("balance", _.balance).addHints(smithy.api.Required()),
    string.required[CreateDeleteAccountOutput]("name", _.name).addHints(smithy.api.Required()),
  ){
    CreateDeleteAccountOutput.apply
  }.withId(id).addHints(hints)
}