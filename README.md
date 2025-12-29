# kotlin-tx-builder

<p align="center">
  <img src="../docs/images/kmp_crypto_banner.png" alt="kotlin-tx-builder" width="100%">
</p>

<p align="center">
  <a href="#"><img src="https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin" alt="Kotlin"></a>
  <a href="#"><img src="https://img.shields.io/badge/multiplatform-android%20%7C%20ios%20%7C%20watchOS%20%7C%20JVM-orange" alt="Multiplatform"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/license-Apache%202.0-blue.svg" alt="License"></a>
</p>

<p align="center">
  <strong>🔨 Pure Kotlin Multiplatform library for building and signing cryptocurrency transactions.</strong>
</p>

# Cryptocurrency Transaction Builder

Pure Kotlin Multiplatform library for building and signing cryptocurrency transactions.

## Features

### Bitcoin (BTC)
- **Transaction Models**: Full support for Legacy and SegWit (BIP141) transactions.
- **PSBT (BIP174)**: Partially Signed Bitcoin Transaction support for interoperability.
- **Serialization**: Full raw transaction serialization/deserialization.

### Ethereum (ETH)
- **Legacy Transactions**: Original format (requesting gas price).
- **EIP-1559**: Modern fee market transactions (Base Fee + Priority Fee).
- **RLP Encoding**: Recursive Length Prefix encoding for all transaction types.
- **EIP-155**: Replay attack protection.

## Usage

### Bitcoin PSBT

```kotlin
val tx = Transaction(...)
val psbt = Psbt(
    global = PsbtGlobal().apply { 
        // Add unsigned tx
    },
    inputs = listOf(...),
    outputs = listOf(...)
)
val serialized = psbt.serialize()
```

### Ethereum EIP-1559

```kotlin
val tx = Eip1559Transaction(
    chainId = 1,
    nonce = 0,
    maxPriorityFeePerGas = ...,
    maxFeePerGas = ...,
    gasLimit = 21000,
    to = "0x...",
    value = ...,
    data = byteArrayOf()
)

val rlpEncoded = tx.encode(forSigning = true)
```
