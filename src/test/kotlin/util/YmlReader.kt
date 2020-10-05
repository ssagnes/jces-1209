package util

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun main(args: Array<String>) {

    val pathname = Paths.get("*/test-properties.yml")

    println(readPropertiesFromYml(pathname).duration)
    println(readPropertiesFromYml(pathname).region)
}

fun readPropertiesFromYml(path: Path): TestPropertiesDto {
    val yaml = Yaml(Constructor(TestPropertiesDto::class.java))
    return Files.newBufferedReader(path).use {
        yaml.load(it) as TestPropertiesDto
    }
}
