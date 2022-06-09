package com.collager.trillo.model;

public class Workflow extends FlowM {
  
  public FlowNodeM addFunction(String name, String functionName) {
    FlowNodeM flowNode = new FlowNodeM();
    flowNode.setName(name);
    flowNode.setType(FlowNodeM.ACTIVITY_TYPE);
    flowNode.setActivityType(FlowNodeM.FUNCTION_ACTIVITY_TYPE);
    flowNode.setFunctionName(functionName);
    addFlowNode(flowNode);
    return flowNode;
  }

  public FlowNodeM addFunction(String name, String functionName, String failureFunctionName) {
    FlowNodeM flowNode = addFunction(name, functionName);
    flowNode.setFailureFunctionName(failureFunctionName);
    return flowNode;
  }
  
  public static Workflow newWorkflow(String name) {
    Workflow wfl = new Workflow();
    wfl.setName(name);
    return wfl;
  }
}
