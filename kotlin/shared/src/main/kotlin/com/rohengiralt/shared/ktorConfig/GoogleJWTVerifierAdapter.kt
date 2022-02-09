package com.rohengiralt.shared.ktorConfig

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.json.webtoken.JsonWebSignature
import java.util.*

internal class GoogleJWTVerifierAdapter(audiences: List<String>) : JWTVerifier {
    private val verifier = GoogleIdTokenVerifier
        .Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory())
        .setAudience(audiences)
        .build()

    override fun verify(token: String): DecodedJWT {
        val googleIdToken = verifier.verify(token) ?: throw JWTVerificationException("Invalid Google JWT")
        return GoogleDecodedJWTAdapter(googleIdToken, token)
    }

    override fun verify(jwt: DecodedJWT): DecodedJWT {
        verifier.verify(jwt.token) ?: throw JWTVerificationException("Could not verify token.")
        return jwt
    }

    private class GoogleDecodedJWTAdapter(googleIdToken: GoogleIdToken, token: String) : DecodedJWT {
        private val payload: GoogleIdToken.Payload = googleIdToken.payload
        private val header: JsonWebSignature.Header = googleIdToken.header
        private val _token = token
        private val parts = token.split(".")

        override fun getIssuer(): String =
            payload.issuer

        override fun getSubject(): String =
            payload.subject

        override fun getAudience(): MutableList<String> =
            payload.audienceAsList

        override fun getExpiresAt(): Date =
            Date(payload.expirationTimeSeconds*1000)

        override fun getNotBefore(): Date =
            Date(payload.notBeforeTimeSeconds*1000)

        override fun getIssuedAt(): Date =
            Date(payload.issuedAtTimeSeconds*1000)

        override fun getId(): String =
            payload.jwtId

        override fun getClaim(name: String?): Claim? =
            payload[name]?.let(GoogleJWTVerifierAdapter::ClaimImpl)

        override fun getClaims(): MutableMap<String, Claim?> =
            payload.mapValuesTo(mutableMapOf()) { (_, value) -> ClaimImpl(value) }

        override fun getAlgorithm(): String = header.algorithm

        override fun getType(): String = header.type

        override fun getContentType(): String = header.contentType

        override fun getKeyId(): String = header.keyId

        override fun getHeaderClaim(name: String?): Claim? =
            header[name]?.let(GoogleJWTVerifierAdapter::ClaimImpl)

        override fun getToken(): String = _token

        override fun getHeader(): String = parts[0]

        override fun getPayload(): String = parts[1]

        override fun getSignature(): String = parts.getOrElse(2) { "" }
    }

    private class ClaimImpl(private val value: Any?) : Claim {
        override fun isNull(): Boolean = value == null

        override fun asBoolean(): Boolean? = value as? Boolean

        override fun asInt(): Int? = value as? Int

        override fun asLong(): Long? = value as? Long ?: (value as? Int)?.toLong()

        override fun asDouble(): Double? = value as? Double

        override fun asString(): String? = value as? String

        override fun asDate(): Date? = value as? Date ?: asLong()?.let { Date(it) }

        override fun <T : Any?> asArray(tClazz: Class<T>): Array<T>? =
            (value as? Array<*>)
                ?.let {
                    if (it.all(tClazz::isInstance)) {
                        @Suppress("UNCHECKED_CAST")
                        it as Array<T>
                    } else {
                        null
                    }
                }

        override fun <T : Any?> asList(tClazz: Class<T>): MutableList<T>? =
            asArray(tClazz)?.toMutableList()

        @Suppress("UNCHECKED_CAST")
        override fun asMap(): MutableMap<String, Any>? =
            value as? MutableMap<String, Any>

        override fun <T : Any?> `as`(tClazz: Class<T>): T =
            tClazz.cast(value)

    }
}