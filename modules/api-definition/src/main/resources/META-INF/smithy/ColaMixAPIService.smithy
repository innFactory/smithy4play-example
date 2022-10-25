$version: "2"

namespace playSmithy

use smithy4s.api#simpleRestJson

@simpleRestJson
service ColaMixAPIService {
    version: "1.0.0",
    operations: [BuyCola GetColaMixInfo DeliverCola]
}


@http(method: "POST", uri: "/buy", code: 200)
operation BuyCola {
    input := {
        @required
        amount: Integer
        @required
        bankAccountName: String
    }
    output := {
        @required
        oldBankBalance: Double
        @required
        price: Double
        @required
        newBankBalance: Double
        @required
        storage: Integer
    }
}

@readonly
@http(method: "GET", uri: "/storage", code: 201)
operation GetColaMixInfo {
    output := {
        @required
        amount: Integer
        @required
        price: Double
    }
}

@auth([])
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








