namespace java com.oyo.testapp.thriftoverhttpusingspringboot.api


typedef i32 int // We can use typedef to get pretty names for the types we are using

enum Operation {
    ADD = 0,
    SUBTRACT = 1,
    MULTIPLY = 2,
    DIVIDE = 3,
}

exception ArithematicException {
    1: string message;
    2: int param1;
    3: int param2;
}

struct Result {
    1: int addResult;
    2: int multiplyResult;
    3: int divideResult;
    4: int subractResult;
}

service CalculationService
{
    int multiply(1:int n1, 2:int n2),
    int add(1:int n1, 2:int n2),
    int subtract(1:int n1, 2:int n2),
    int divide(1:int n1, 2:int n2),
    int operate(1:Operation operation, 2:int n1, 3:int n2) throws (1: ArithematicException arithematicException),
    Result repeatMethod(1:int n1, 2:int n2),
}