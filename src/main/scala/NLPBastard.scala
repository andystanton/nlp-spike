import opennlp.tools.doccat.{DocumentCategorizerME, DocumentSampleStream}
import opennlp.tools.util.PlainTextByLineStream

object NLPBastard {

  def main(args: Array[String]): Unit = {
    val is = Thread.currentThread().getContextClassLoader.getResourceAsStream("en-sentiment.train")
    val lineStream = new PlainTextByLineStream(is, "UTF-8")
    val sampleStream = new DocumentSampleStream(lineStream)

    val model = DocumentCategorizerME.train("en", sampleStream)

    is.close()

    val d = new DocumentCategorizerME(model)

    val likeStatement = "I like turtles"
    val hateStatement = "I hate turtles"

    val likeOutcome = d.getBestCategory(d.categorize(likeStatement))
    val hateOutcome = d.getBestCategory(d.categorize(hateStatement))

    println(s"$likeStatement is $likeOutcome")
    println(s"$hateStatement is $hateOutcome")
  }
}
