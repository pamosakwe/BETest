package betest

import java.io.File
import org.scalatest.matchers.must.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper


class DocConversionTest extends AnyFlatSpec with Matchers {

  val csvFile = new File(getClass.getResource("workbook2.csv").getPath)
  val prnFile = new File(getClass.getResource("workbook2.prn").getPath)

  "The test" should "convert files" in {
    val conversion = DocConversion.readCSV(csvFile)
    conversion should be ()
  }

  "Produced HTML" should "convert PRN files" in {
    val conversion = DocConversion.readPRN(prnFile)
    conversion should be ()
  }

}
