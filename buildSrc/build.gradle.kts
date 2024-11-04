plugins {
    groovy
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation("com.badlogicgames.gdx:gdx-jnigen:2.5.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("com.lordcodes.turtle:turtle:0.6.0")
    implementation("fr.inria.gforge.spoon:spoon-core:10.3.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.18.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
}
