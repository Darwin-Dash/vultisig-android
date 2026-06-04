package com.vultisig.wallet.data.common

object Endpoints {
    // Relay URL used for keygen/keysign sessions. For the Tier-3 emulator
    // co-sign harness, a debug-only override can point this at a local relay
    // reachable from the emulator (http://10.0.2.2:<port>), set via:
    //   adb shell setprop debug.vultisig.relay.url http://10.0.2.2:<port>
    // Java's System.getProperty() does NOT read Android native properties, so we
    // read the native property store via reflection. Unset (production) → the
    // public relay, so behaviour is unchanged when no override is present.
    val VULTISIG_RELAY_URL: String
        get() = debugRelayOverride() ?: "https://api.vultisig.com/router"

    const val LOCAL_MEDIATOR_SERVER_URL = "http://127.0.0.1:18080"
    const val THORCHAIN_BROADCAST_TX: String =
        "https://thornode.ninerealms.com/cosmos/tx/v1beta1/txs"

    private fun debugRelayOverride(): String? =
        try {
            val clazz = Class.forName("android.os.SystemProperties")
            val get = clazz.getMethod("get", String::class.java)
            (get.invoke(null, "debug.vultisig.relay.url") as? String)?.takeIf { it.isNotBlank() }
        } catch (_: Throwable) {
            null
        }
}
