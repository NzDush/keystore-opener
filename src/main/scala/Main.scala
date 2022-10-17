package main

import com.typesafe.config.{Config, ConfigFactory}
import util.KeystoreUtil.{getAliases, getCertificate, getFingerprintSHA1, getKeyStoreInstance}

import java.io.{File, PrintWriter}
import java.text.SimpleDateFormat
import java.util.TimeZone

object Main {

  def config(): Config = ConfigFactory.load()

  def printLineSeparator(printWriter: PrintWriter, count: Int): Unit = {
    val separatorString = "=" * 202
    for (i <- 0 until count) {
      println(separatorString)
      printWriter.println(separatorString)
    }
  }

  def main(args: Array[String]): Unit = {

    val keystorePath = config().getString("app.keystore.path")
    val keystorePassword = config().getString("app.keystore.password")
    val outputFilePath = config().getString("app.outputFilePath")
    val timeZone = config().getString("app.timeZone")

    val keystore = getKeyStoreInstance(new File(keystorePath), keystorePassword)
    val aliases = getAliases(keystore)
    java.util.Collections.sort(aliases)

    val printWriter = new PrintWriter(new File(outputFilePath))

    printLineSeparator(printWriter, 1)
    println(s"Total certificate count: ${aliases.size()}")
    printWriter.println(s"Total certificate count: ${aliases.size()}")
    printLineSeparator(printWriter, 2)

    val tablePattern = "%-6s%-43s%-51s%-37s%-43s%-22s\n"
    printf(tablePattern, "No.", "Serial Number (Hex)", "Serial Number (Decimal)", "Alias", "Fingerprint (SHA1)", "Valid until")
    printWriter.printf(tablePattern, "No.", "Serial Number (Hex)", "Serial Number (Decimal)", "Alias", "Fingerprint (SHA1)", "Valid until")
    printLineSeparator(printWriter, 1)

    for (i <- 0 until aliases.size()) {
      val alias = aliases.get(i)
      val cert = getCertificate(keystore, alias)
      val serialNumberInDecimal = cert.getSerialNumber
      val serialNumberInHex = serialNumberInDecimal.toString(16)
      val fingerprint = getFingerprintSHA1(cert).toUpperCase
      val simpleDateFormat = (new SimpleDateFormat("yyyy-MM-dd HH:mm Z"))
      simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone))
      val expireDate = simpleDateFormat.format(cert.getNotAfter)
      printf(tablePattern, (i + 1), serialNumberInHex.toUpperCase, serialNumberInDecimal.toString, alias, fingerprint, expireDate)
      printWriter.printf(tablePattern, (i + 1).toString, serialNumberInHex.toUpperCase, serialNumberInDecimal.toString, alias, fingerprint, expireDate)
    }
    printLineSeparator(printWriter, 1)
    printWriter.close()

  }

}
