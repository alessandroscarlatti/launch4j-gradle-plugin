package com.scarlatti.launch4j

import com.scarlatti.testing.util.GradleBuildSpecification
import org.gradle.testkit.runner.TaskOutcome

import static BuildTemplates.applyLaunch4jPlugin
import static BuildTemplates.common
import static BuildTemplates.tasks
import static Launch4jHelperExtension.*
import static Launch4jHelperExtension.DEFAULT_HELPER_TASK_DESCRIPTION_TEMPLATE
import static com.scarlatti.launch4j.task.Launch4jHelperTask.fillInLaunch4jTemplateTaskName

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 7/23/2018
 */
class HelperTaskNameTest extends GradleBuildSpecification {

    def "helper task description uses default name when not connected to task"() {
        setup:
            String helperTask = """
                task helperTask(type: Launch4jHelperTask) {
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(DEFAULT_HELPER_TASK_DESCRIPTION)
    }

    def "helper task description uses default template when connected to task"() {
        setup:
            String helperTask = """
                task helperTask(type: Launch4jHelperTask) {
                    launch4jTask(project.tasks.createExe)
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(fillInLaunch4jTemplateTaskName(
                    DEFAULT_HELPER_TASK_DESCRIPTION_TEMPLATE, DEFAULT_LAUNCH4J_TASK_VARIABLE, "createExe"))
    }

    def "helper task name description custom template when configured in extension"() {
        setup:
            String template = "special description about task #task"
            String extensionConfig = """
                launch4jHelper {
                    meta {
                        descriptionTemplate = "${template}"
                    }
                }
            """
            String helperTask = """
                task helperTask(type: Launch4jHelperTask) {
                    launch4jTask(project.tasks.createExe)
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .appendBuildFileContents(extensionConfig)
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(fillInLaunch4jTemplateTaskName(
                    template, DEFAULT_LAUNCH4J_TASK_VARIABLE, "createExe"))
    }

    def "helper task description uses custom template when configured in task"() {
        setup:
            String template = "description configured from task for #task"
            String helperTask = """
                task helperTask(type: Launch4jHelperTask) {
                    meta {
                        descriptionTemplate = "${template}"
                    }
                    launch4jTask(project.tasks.createExe)
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(fillInLaunch4jTemplateTaskName(
                    template, DEFAULT_LAUNCH4J_TASK_VARIABLE, "createExe"))
    }

    def "helper task description uses default when turned off from extension"() {
        setup:
            String extensionConfig = """
                launch4jHelper {
                    meta {
                        generateDescription = false
                    }
                }
            """
            String helperTask = """
                task helperTask(type: Launch4jHelperTask) {
                    launch4jTask(project.tasks.createExe)
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .appendBuildFileContents(extensionConfig)
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(DEFAULT_HELPER_TASK_DESCRIPTION)
    }

    def "helper task description filled in from template when description set in task before launch4jTask associated"() {
        setup:
            String template = "this description should be used for #task"
            String helperTask = """
                task helperTask(type: Launch4jHelperTask) {
                    description = "${template}"
                    launch4jTask(project.tasks.createExe)
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(fillInLaunch4jTemplateTaskName(
                    template, DEFAULT_LAUNCH4J_TASK_VARIABLE, "createExe"))
    }

    def "helper task description filled in from template when description set in task after launch4jTask associated"() {
        setup:
            String template = "this description should be used for #task"
            String helperTask = """
                task helperTask(type: Launch4jHelperTask) {
                    launch4jTask(project.tasks.createExe)
                    description = "${template}"
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(fillInLaunch4jTemplateTaskName(
                    template, DEFAULT_LAUNCH4J_TASK_VARIABLE, "createExe"))
    }

    def "helper task description filled in from template when description set in task creation"() {
        setup:
            String template = "description in construction - for #task"
            String helperTask = """
                task helperTask(type: Launch4jHelperTask, description: "${template}") {
                    launch4jTask(project.tasks.createExe)
                }                
            """
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .appendBuildFileContents(helperTask)
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
            result.output.contains(fillInLaunch4jTemplateTaskName(
                    template, DEFAULT_LAUNCH4J_TASK_VARIABLE, "createExe"))
    }
}
