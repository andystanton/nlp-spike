import opennlp.tools.doccat.DocumentCategorizerME
import org.scalatest.{Matchers, FunSpec}

class NLPBastardSpec extends FunSpec with Matchers {
  val testStatements = Seq(
    Statement("I like turtles", "positive"),
    Statement("I hate turtles", "negative"),
    Statement("carpets are bad", "negative"),
    Statement("I hate chairs", "negative"),
    Statement("I don't mind bears", "neutral")
  )

  def checkTestStatements(implicit categorizer: DocumentCategorizerME) {
    testStatements.foreach(testStatement => {
      val sentiment = categorizer.getBestCategory(categorizer.categorize(testStatement.sentence))
      println(s"Checking '${testStatement.sentence}'")
      println(s" - Expected: '${testStatement.sentiment}' Actual: '$sentiment")
      sentiment shouldBe testStatement.sentiment
    })
  }

  describe("the NLP Bastard") {
    it("can generate a model based on a training file") {
      implicit val categorizer = new DocumentCategorizerME(NLPBastard.learnTrainingFile)

      checkTestStatements
    }

    it("can load a model from a binary file") {
      NLPBastard.writeBinFile(NLPBastard.learnTrainingFile) // Create binary file

      implicit val categorizer = new DocumentCategorizerME(NLPBastard.loadModelFromBinFile)

      checkTestStatements
    }
  }
}
