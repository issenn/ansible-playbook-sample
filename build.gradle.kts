plugins {
    distribution
    `maven-publish`
}

group = "com.issenn.ansible"
// version = "1.0-SNAPSHOT"
version = "1.0.0"

distributions {
    main {
        contents {
            from(".")
            into("/")
            setIncludes(listOf("ansible.cfg", "inventories/**", "roles/**", "*.yaml", "*.yml", "virtual_environment"))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("Distribution") {
            artifact(tasks.distZip.get())
        }
    }

    repositories {
        maven {
            setUrl(project.findProperty("nexusRepoURL") as String? ?: System.getenv("NEXUS_REPO_URL"))
            credentials {
                username = project.findProperty("nexusUsername") as String? ?: System.getenv("NEXUS_USERNAME")
                password = project.findProperty("nexusPassword") as String? ?: System.getenv("NEXUS_PASSWORD")
            }
            isAllowInsecureProtocol = true
        }
    }
}
