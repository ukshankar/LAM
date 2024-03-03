import com.github.gradle.node.npm.task.NpmTask

plugins {
  id ("com.github.node-gradle.node") version "7.0.2"
}
group = "com.t4a"
version = "1.0-SNAPSHOT"

node {
  download = true
  workDir = file("${project.buildDir}/node")
  nodeProjectDir = file("${project.projectDir}")
}


task<NpmTask>("build"){
  dependsOn ("deletePublic")
  dependsOn ("npmInstall")
  args.set(listOf("run-script","build"))
}

task<Delete>("deletePublic"){
  delete("${project.projectDir}/../src/main/resources/public")
}
