package com.ka.thriftoverhttpusingspringbootserver;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransportException;

import com.oyo.platform.logger.Level;
import com.oyo.platform.logger.Logger;
import com.oyo.platform.thriftextension.TOyoHttpClient;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.ArithematicException;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.CalculationService;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.Operation;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.Result;

public class CalculatorClient {
  private static int port = 8080;
  private static String host = "localhost";

  static Logger logger = new Logger("TestApplication", CalculatorClient.class);

  public static void main(String[] args) {
    try {
      logger.log(Level.DEBUG, "Enabled log level is DEBUG, this log is DEBUG");
      logger.log(Level.DEBUG, "Log at level : {} ", Level.DEBUG.toString());
      logger.log(Level.INFO, "Log at level : {} ", Level.INFO.toString());
      logger.log(Level.WARN, "Log at level : {} ", Level.WARN.toString());
      logger.log(Level.ERROR, "Log at level : {} ", Level.ERROR.toString());
      logger.log(Level.FATAL, "Log at level : {} ", Level.FATAL.toString());
      TOyoHttpClient tOyoHttpClient =
          new TOyoHttpClient("http://" + host + ":" + port + "/calculator");
      tOyoHttpClient.setConnectTimeout(3000);

      TProtocol protocol = new TJSONProtocol(tOyoHttpClient);
      CalculationService.Client client = new CalculationService.Client(protocol);

      int x = 500;
      int y = 100;

      int n = client.multiply(x, y);
      logger.log(Level.INFO, "{}x{}={}", x, y, n);
      n = client.add(x, y);
      logger.log(Level.INFO, "{}+{}={}", x, y, n);
      n = client.divide(x, y);
      logger.log(Level.INFO, "{}/{}={}", x, y, n);
      n = client.subtract(x, y);
      logger.log(Level.INFO, "{}-{}={}", x, y, n);
      n = client.operate(Operation.MULTIPLY, x, y);
      logger.log(Level.INFO, "{}x{}={}", x, y, n);
      n = client.operate(Operation.ADD, x, y);
      logger.log(Level.INFO, "{}+{}={}", x, y, n);
      n = client.operate(Operation.DIVIDE, x, y);
      logger.log(Level.INFO, "{}/{}={}", x, y, n);
      n = client.operate(Operation.SUBTRACT, x, y);
      logger.log(Level.INFO, "{}-{}={}", x, y, n);
      try {
        client.operate(Operation.DIVIDE, x, 0);
      } catch (ArithematicException exception) {
        logger.log(Level.ERROR, exception,
            "ArithematicException Message:\"{}\" Param1:\"{}\" Param2:\"{}\"",
            exception.getMessage(), exception.getParam1(), exception.getParam2());
      }
      Result result = client.repeatMethod(x, y);
      logger.log(Level.INFO, "{}-{}={} {}+{}={} {}x{}={} {}/{}={}", x, y, result.getSubractResult(),
          x, y, result.getAddResult(), x, y, result.getMultiplyResult(), x, y,
          result.getDivideResult());
      tOyoHttpClient.close();
    } catch (TTransportException e) {
      logger.log(Level.ERROR, "Connection failed : {}", e.getMessage());
    } catch (TException x) {
      logger.log(Level.ERROR, "Api exception : {}", x.getMessage());
    }
  }

}
