plugins {
    groovy
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation("com.badlogicgames.gdx:gdx-jnigen:2.4.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("com.lordcodes.turtle:turtle:0.6.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
}
