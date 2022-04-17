package tool

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

class UpVersion extends DefaultTask {
    @Internal
    String group = 'build'
    @Internal
    String description = 'Up project version to the next one.'

    @TaskAction
    void up() {
        String currentVersion = project.findProperty('version')
        String nextVersion = project.findProperty('next')

        if (!currentVersion) {
            throw new IllegalStateException('No property was found: "version"')
        }
        if (!nextVersion) {
            throw new IllegalStateException('No property was found: "next"')
        }

        println "Up project version: $nextVersion..."

        println 'Updating gradle.properties...'
        def propsFile = project.file('gradle.properties')
        propsFile.text = propsFile.text.replace("version=$currentVersion", "version=$nextVersion")

        println 'Updating README.md...'
        def readmeFile = project.file('README.md')
        readmeFile.text = readmeFile.text.replace(currentVersion, nextVersion)
    }
}
