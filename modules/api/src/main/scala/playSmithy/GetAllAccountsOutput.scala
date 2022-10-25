package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GetAllAccountsOutput(body: List[CreateDeleteAccountOutput])
object GetAllAccountsOutput extends ShapeTag.Companion[GetAllAccountsOutput] {
  val id: ShapeId = ShapeId("playSmithy", "GetAllAccountsOutput")

  val hints : Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[GetAllAccountsOutput] = struct(
    CreateDeleteAccountOutputList.underlyingSchema.required[GetAllAccountsOutput]("body", _.body).addHints(smithy.api.HttpPayload(), smithy.api.Required()),
  ){
    GetAllAccountsOutput.apply
  }.withId(id).addHints(hints)
}