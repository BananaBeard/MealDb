mapOf(
    "kotlinVersion" to "1.3.72",
    "navigationVersion" to "2.3.0",
    "cardVersion" to "1.0.0",
    "androidXVersion" to "1.0.0",
    "appcompatVersion" to "1.1.0-rc01",
    "materialVersion" to "1.3.0-alpha01",
    "coroutinesVersion" to "1.3.5",
    "androidXAnnotations" to "1.0.1",
    "archLifecycleVersion" to "2.2.0",
    "fragmentKtxVersion" to "1.3.0-alpha07",
    "legacySupportVersion" to "1.0.0",
    "moshiVersion" to "1.8.0",
    "retrofitVersion" to "2.9.0",
    "okhhtpVersion" to "3.12.3",
    "koinVersion" to "2.1.5",
    "roomVersion" to "2.2.5",
    "activityVersion" to "1.2.0-alpha07"
).forEach { (name, version) ->
    project.extra.set(name, version)
}
