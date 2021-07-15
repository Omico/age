package me.omico.age

import org.gradle.api.Project
import java.util.Properties

inline val Project.localProperties: Properties
    get() = Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }
