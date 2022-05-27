plugins {
    groovy
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation("com.badlogicgames.gdx:gdx-jnigen:2.4.0")
    implementation("org.reflections:reflections:0.10.2")
}
