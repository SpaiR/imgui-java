package tool

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

@CompileStatic
class UpVersion extends DefaultTask {
    @Internal
    String group = 'build'
    @Internal
    String description = 'Up project version to the next one.'

    private final String currentVersion = project.findProperty('version')
    private final String nextVersion = project.findProperty('next')

    @TaskAction
    void up() {
        if (!currentVersion) {
            throw new IllegalArgumentException('No property was found: "version"')
        }
        if (!nextVersion) {
            throw new IllegalArgumentException('No property was found: "next"')
        }

        def propsFile = project.file('gradle.properties')
        propsFile.text = propsFile.text.replace("version=$currentVersion", "version=$nextVersion")

        def readmeFile = project.file('README.md')
        readmeFile.text = readmeFile.text.replace(currentVersion, nextVersion)
    }
}
