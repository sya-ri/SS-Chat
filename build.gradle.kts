plugins {
    kotlin("jvm") version "1.4.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
}

group = "me.syari.ss.chat"
version = "1.1.1"

val ssMavenRepoURL: String by extra

repositories {
    mavenCentral()
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        url = uri(ssMavenRepoURL)
    }
}

dependencies {
    implementation("com.destroystokyo.paper:paper-api:1.16.2-R0.1-SNAPSHOT")
    implementation("me.syari.ss.core:SS-Core:3.1.1")
    implementation("me.syari.discord:KtDiscord:1.0.1")
    implementation("me.syari.ss.discord:SS-Discord:2.0.2")
    implementation(kotlin("stdlib-jdk8"))
}

bukkit {
    name = project.name
    version = project.version.toString()
    main = "$group.Main"
    author = "sya_ri"
    depend = listOf("SS-Core")
    softDepend = listOf("SS-Discord")
    apiVersion = "1.16"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

val jar by tasks.getting(Jar::class) {
    from(configurations.compileOnly.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
}