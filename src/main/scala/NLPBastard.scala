import java.io._

import opennlp.tools.doccat.{DoccatModel, DocumentCategorizerME, DocumentSampleStream}
import opennlp.tools.util.PlainTextByLineStream

case class Statement(sentence: String, sentiment: String)

object NLPBastard {

  val testStatements = Seq(
    Statement("I like turtles", "positive"),
    Statement("I hate turtles", "negative"),
    Statement("carpets are bad", "negative"),
    Statement("I hate chairs", "negative"),
    Statement("I don't mind bears", "neutral")
  )

  def learnTrainingFile = {
    val inputStream = Thread.currentThread().getContextClassLoader.getResourceAsStream("en-sentiment.train")
    val lineStream = new PlainTextByLineStream(inputStream, "UTF-8")
    val sampleStream = new DocumentSampleStream(lineStream)
    val model = DocumentCategorizerME.train("en", sampleStream)
    inputStream.close()    
    model
  }
  
  def writeBinFile(model: DoccatModel) {
    val binFile = new File("en-sentiment.bin")
    if (binFile.exists()) binFile.delete()

    val fos: FileOutputStream = new FileOutputStream("en-sentiment.bin")

    val modelOut = new BufferedOutputStream(fos)
    model.serialize(modelOut)
  }

  def loadModelFromBinFile = {
    val inputStream: FileInputStream = new FileInputStream(new File("en-sentiment.bin"))
    val model = new DoccatModel(inputStream)
    inputStream.close()
    model
  }
}
