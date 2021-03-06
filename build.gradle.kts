import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import me.omico.age.spotless.configureSpotless
import me.omico.age.spotless.gradleVersionCatalogs
import me.omico.age.spotless.intelliJIDEARunConfiguration
import me.omico.age.spotless.kotlin
import me.omico.age.spotless.kotlinGradle

plugins {
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
}

allprojects {
    group = "me.omico.age"
    version = "1.0.0-SNAPSHOT"
    configureDependencyUpdates()
    configureSpotless {
        gradleVersionCatalogs()
        intelliJIDEARunConfiguration()
        kotlin()
        kotlinGradle()
    }
}

fun Project.configureDependencyUpdates() {
    apply(plugin = "com.github.ben-manes.versions")
    tasks.withType<DependencyUpdatesTask> {
        rejectVersionIf {
            when {
                stableList.contains(candidate.group) -> isNonStable(candidate.version)
                else -> false
            }
        }
    }
}

val stableList = listOf(
    "org.jetbrains.kotlin",
)

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
