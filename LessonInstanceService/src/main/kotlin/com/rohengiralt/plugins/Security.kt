package com.rohengiralt.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.rohengiralt.persistence.GoogleJWTVerifierAdapter
import io.ktor.server.application.*

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
