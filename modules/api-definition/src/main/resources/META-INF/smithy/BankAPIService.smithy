$version: "2"

namespace playSmithy

use smithy4s.api#simpleRestJson

@simpleRestJson
@httpBearerAuth
service BankAPIService {
    version: "1.0.0",
    operations: [CreateAccount DeleteAccount Transfer GetAllAccounts]
}


@auth([httpBearerAuth])
@readonly
@http(method: "GET", uri: "/account", code: 200)
operation GetAllAccounts {
    output := {
        @required
        @httpPayload
        body: CreateDeleteAccountOutputList
    }
}

list CreateDeleteAccountOutputList {
    member: CreateDeleteAccountOutput
}

@auth([])
@http(method: "POST", uri: "/account", code: 200)
operation CreateAccount {
    input := {
        @required
        startingCredit: Double
        @required
        @length(min: 3)
        name: String
    }
    output: CreateDeleteAccountOutput
}


@auth([])
@idempotent
@http(method: "DELETE", uri: "/account/{name}", code: 200)
operation DeleteAccount {
    input := {
        @required
        @httpLabel
        name: String
    }
    output: CreateDeleteAccountOutput
}


@auth([])
@http(method: "POST", uri: "/account/transfer", code: 201)
operation Transfer {
    input := {
        @required
        amount: Double
        @required
        name: String
    }
    output: TransferOutput
}

@mixin
structure AccountOutput {
    @required
    balance: Double
    @required
    name: String
}

structure CreateDeleteAccountOutput with [AccountOutput] {

}

structure TransferOutput with [AccountOutput] {
    message: String
}









