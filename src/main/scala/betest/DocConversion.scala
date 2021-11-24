package betest

import java.io.{BufferedReader, File, FileReader}
import scala.io.Source
import com.opencsv.{CSVParserBuilder, CSVReaderBuilder}
import scala.collection.JavaConverters._

object DocConversion extends App {

  private val parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build()

  def readCSV(file: File): Unit = {
    val csvReader = {
      val buffReader = new BufferedReader(new FileReader(file))
      new CSVReaderBuilder(buffReader).withSkipLines(1).withCSVParser(parser).build()
    }

    val csvData = csvReader.readAll().asScala.toList.map{
      _ match {
        case Array(name,address,postcode,phone,creditLimit,birthday) =>
          Some(CSVMatch(name,address,postcode,phone,creditLimit,birthday))
        case _ => None
      }
    }

    val rows = csvData.flatten
    generateHTMLDocsForCSVFile(rows)
  }

  def generateHTMLDocsForCSVFile(data: List[CSVMatch]): Unit ={
    println("<html><body><table>")
    for (datum <- data){
      println(generateHTMLContentForCSVFile(datum))
    }
    println("</table></body></html>")
  }

  def generateHTMLContentForCSVFile(data: CSVMatch): String = {
    "<tr><td>" + data.name + "</td><td>" + data.address + "</td><td>" + data.postcode + "</td><td>" + data.creditLimit + "</td><td>" + data.birthday + "</td>"
  }

  def readPRN(file: File): Unit = {
    println("<html><body><table>")
    val result = generateHTMLContentForPRN(file)
    result.foreach(println(_))
    println("</table></body></html>")
  }

  def generateHTMLContentForPRN(file: File): List[String] = {
    val result = for {
      line <- Source.fromFile(file).getLines
    } yield "<tr><td>" + line + "</td></tr>"
    //println(s"line ${result.toList}")
    result.toList
  }

}
