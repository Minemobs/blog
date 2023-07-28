import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import java.net.URI
import javax.imageio.ImageIO
import kotlin.math.floor

fun BufferedImage.getRGB(x: Int, y: Int, width: Int, height: Int): IntArray = getRGB(x, y, width, height, null, 0, width)
fun BufferedImage.setRGB(x: Int, y: Int, width: Int, height: Int, rgbArray: IntArray) = setRGB(x, y, width, height, rgbArray, 0, width)
fun write(output: BufferedImage, name: String) = ImageIO.write(output, "png", File(name))

@Serializable
data class Page(val height: Int, val width: Int, val type: String, val src: String)

fun main(args: Array<String>) {
    val url = if(args.isNotEmpty()) args[0] else {println("Enter your url"); readln()}
    if(!url.endsWith(".json")) throw IllegalArgumentException("The URL doesn't ends with .json")
    URI.create(url).toURL().openStream().use { getImages(it) }
}

private val json = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalSerializationApi::class)
fun getImages(it: InputStream) {
    val pages = json.decodeFromStream<JsonObject>(it)["readableProduct"]!!
        .jsonObject["pageStructure"]!!.jsonObject["pages"]!!.jsonArray
        .map { it.jsonObject }.filter { it.containsKey("src") }
        .map { json.decodeFromJsonElement<Page>(it) }
    pages.forEachIndexed { index, page -> write(URI.create(page.src).toURL().openStream().use { getImage(it) }, "IMG-$index.png") }
}

fun getImage(`is`: InputStream): BufferedImage {
    val img = ImageIO.read(`is`)
    val w = img.width
    val h = img.height
    val output = BufferedImage(img.width, img.height, img.type)
    val div = 4
    val mult = 8
    val colWidth = floor(w / (div * mult).toDouble()).toInt() * mult
    val rowHeight = floor(h / (div * mult).toDouble()).toInt() * mult

    output.setRGB(0, 0, w, h, img.getRGB(0,0, w, h))
    for(col in 0..<div) {
        for(row in 0..col) {
            output.setRGB(row * colWidth, col * rowHeight, colWidth, rowHeight, img.getRGB(col * colWidth, row * rowHeight, colWidth, rowHeight))
            if(col == row) continue
            output.setRGB(col * colWidth, row * rowHeight, colWidth, rowHeight, img.getRGB(row * colWidth, col * rowHeight, colWidth, rowHeight))
        }
    }
    return output
}