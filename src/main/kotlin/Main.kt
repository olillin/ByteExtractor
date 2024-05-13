import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.nio.file.InvalidPathException
import java.nio.file.Path

fun main(args: Array<String>) {
    // Parse args
    val parser = ArgParser("bytex")

    val start: Long by parser.option(
        LongArgType,
        fullName = "start",
        shortName = "s",
        description = "The byte to copy from inclusive, if negative the position will be from the end of the file.",
    ).default(0)
    val end: Long by parser.option(
        LongArgType,
        fullName = "end",
        shortName = "e",
        description = "The byte to copy until inclusive, if negative the position will be from the end of the file.",
    ).default(-1)

    val inString: String by parser.argument(
        ArgType.String,
        fullName = "input",
        description = "The file to read",
    )
    val outString: String by parser.argument(
        ArgType.String,
        fullName = "output",
        description = "The file write to",
    )

    parser.parse(args)

    // Get paths
    val inPath: Path = try {
        Path.of(inString)
    } catch (_: InvalidPathException) {
        println("Invalid input path $inString")
        return
    }
    val outPath: Path = try {
        Path.of(outString)
    } catch (_: InvalidPathException) {
        println("Invalid output path $outString")
        return
    }

    // Calculate positions
    val fileLength: Long = inPath.toFile().length()
    val absoluteStart: Long = if (start < 0) fileLength + start
        else start
    val absoluteEnd: Long = if (end < 0) fileLength + end + 1
        else end
    val length = (absoluteEnd - absoluteStart).toInt()

    // Copy file
    val inStream = inPath.toFile().inputStream()
    val outStream = outPath.toFile().outputStream()
    try {
        inStream.skipNBytes(absoluteStart)
        val data = inStream.readNBytes(length)
        outStream.write(data)
        outStream.flush()
        println("Copied $length bytes to $outPath")
    } finally {
        inStream.close()
        outStream.close()
    }
}