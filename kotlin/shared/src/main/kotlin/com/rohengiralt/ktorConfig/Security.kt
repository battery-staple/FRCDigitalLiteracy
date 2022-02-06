package com.rohengiralt.ktorConfig

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    authentication {
        jwt {
            val jwtAudience = environment.config.property("jwt.audience").getString()

            verifier(
                GoogleJWTVerifierAdapter(listOf(jwtAudience))
            )

            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }

}
