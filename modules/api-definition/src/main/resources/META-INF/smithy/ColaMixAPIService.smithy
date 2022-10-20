$version: "2"

namespace playSmithy

use smithy4s.api#simpleRestJson

@simpleRestJson
service ColaMixAPIService {
    version: "1.0.0",
    operations: [BuyCola, HaggleAskingPrice, GetColaAmount, DeliverCola]
}


@http(method: "POST", uri: "/buy", code: 200)
operation BuyCola {
    input := {
        @required
        amount: Integer
    }
    output := {
        @required
        price: Double
        @required
        storage: Integer
    }
}

@http(method: "POST", uri: "/haggle", code: 200)
operation HaggleAskingPrice {
    input: HaggleAskingPriceRequest
    output := {
        price: Double
        message: String
    }
}

@readonly
@http(method: "GET", uri: "/storage", code: 201)
operation GetColaAmount {
    output := {
        @required
        amount: Integer
    }
}

@http(method: "POST", uri: "/storage", code: 201)
operation DeliverCola {
    input := {
        @required
        amount: Integer
    }
    output := {
        @required
        newStorage: Integer
    }
}


structure HaggleAskingPriceRequest {
    @required
    price: Double
    @required
    name: String
}








