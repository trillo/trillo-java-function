/*
 * Copyright (c)  2018 Trillo Inc.
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO INC.
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 *
 */

package com.collager.trillo.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class FlowM extends BaseM { 
  private String type = null;
  private List<FlowNodeM> flowNodes;
  
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<FlowNodeM> getFlowNodes() {
    return flowNodes;
  }

  public void setFlowNodes(List<FlowNodeM> flowNodes) {
    this.flowNodes = flowNodes;
  }
  
  public void addFlowNode(FlowNodeM flowNode) {
    if (flowNodes == null) {
      flowNodes = new ArrayList<FlowNodeM>();
    }
    flowNodes.add(flowNode);
  }
}
