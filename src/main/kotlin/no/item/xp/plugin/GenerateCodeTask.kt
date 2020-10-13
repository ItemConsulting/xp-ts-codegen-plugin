package no.item.xp.plugin

import no.item.xp.plugin.parser.resolveMixinGraph
import no.item.xp.plugin.util.IS_MIXIN
import no.item.xp.plugin.util.getTargetFile
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.work.ChangeType
import org.gradle.work.Incremental
import org.gradle.work.InputChanges
import org.gradle.workers.WorkerExecutor
import javax.inject.Inject

open class GenerateCodeTask @Inject constructor(objects: ObjectFactory, private val workerExecutor: WorkerExecutor) : DefaultTask() {
  @Incremental
  @InputFiles
  val inputFiles: ConfigurableFileCollection = objects.fileCollection()

  @OutputFiles
  val outputFiles: ConfigurableFileCollection = objects.fileCollection()

  @Input
  var fileExtension: String? = null

  @TaskAction
  private fun execute(inputChanges: InputChanges) {
    val workQueue = workerExecutor.noIsolation()

    val mixins = resolveMixinGraph(
      inputFiles.filter { file -> IS_MIXIN.matches(file.absolutePath) }
    )

    inputChanges.getFileChanges(inputFiles).forEach { change ->
      val targetFile = getTargetFile(change.file, fileExtension ?: ".ts")

      if (change.changeType == ChangeType.REMOVED && targetFile.delete()) {
        logger.lifecycle("Removed ${targetFile.absolutePath}")
      } else {
        workQueue.submit(GenerateTypeScriptWorkAction::class.java) {
          it.getXmlFile().set(change.file)
          it.getTargetFile().set(targetFile)
          it.getMixins().value(mixins)
        }
      }
    }
  }
}
