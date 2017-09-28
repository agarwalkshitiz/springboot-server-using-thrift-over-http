package com.ka.thriftoverhttpusingspringbootserver;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Component;

import com.oyo.platform.logger.Level;
import com.oyo.platform.logger.Logger;
import com.oyo.platform.logger.annotations.LogMethodInfo;
import com.oyo.platform.thriftextension.TOyoHttpClient;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.ArithematicException;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.CalculationService;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.Operation;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.Result;

@Component
public class CalculationServiceHandler implements CalculationService.Iface {

  private static int port = 8080;
  private static String host = "localhost";

  Logger logger = new Logger("TestApp", CalculationServiceHandler.class);

  @Override
  @LogMethodInfo
  public int multiply(int n1, int n2) throws TException {
    logger.log(Level.FATAL, "multiply");
    logger.log(Level.ERROR, "multiply");
    logger.log(Level.WARN, "multiply");
    logger.log(Level.INFO, "multiply");
    logger.log(Level.DEBUG, "multiply");
    return n1 * n2;
  }

  @Override
  public int add(int n1, int n2) throws TException {
    return n1 + n2;
  }

  @Override
  public int subtract(int n1, int n2) throws TException {
    return n1 - n2;
  }

  @Override
  public int divide(int n1, int n2) throws TException {
    return n1 / n2;
  }

  @Override
  public int operate(Operation operation, int n1, int n2) throws ArithematicException, TException {
    switch (operation) {
      case ADD:
        return n1 + n2;
      case DIVIDE:
        if (n2 == 0) {
          throw new ArithematicException("divisor cannot be zero for division operation", n1, n1);
        }
        return n1 / n2;
      case MULTIPLY:
        return n1 * n2;
      case SUBTRACT:
        return n1 - n2;
      default:
        break;
    }
    return 0;
  }

  @Override
  public Result repeatMethod(int n1, int n2) throws TException {
    Result result = new Result();
    try {
      TOyoHttpClient tOyoHttpClient =
          new TOyoHttpClient("http://" + host + ":" + port + "/calculator");
      tOyoHttpClient.setConnectTimeout(3000);

      TProtocol protocol = new TJSONProtocol(tOyoHttpClient);
      CalculationService.Client client = new CalculationService.Client(protocol);

      int n = client.multiply(n1, n2);
      result.setMultiplyResult(n);

      n = client.add(n1, n2);
      result.setAddResult(n);

      n = client.divide(n1, n2);
      result.setDivideResult(n);

      n = client.subtract(n1, n2);
      result.setSubractResult(n);

      tOyoHttpClient.close();
    } catch (TTransportException e) {
    } catch (TException x) {
    }
    return result;
  }
}
