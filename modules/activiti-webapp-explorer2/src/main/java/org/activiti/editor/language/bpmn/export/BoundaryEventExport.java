/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.editor.language.bpmn.export;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
public class BoundaryEventExport extends BaseEventExport {

  protected String getElementName() {
    return "boundaryEvent";
  }
  
  protected void writeAdditionalAttributes(ObjectNode objectNode,
      IndentingXMLStreamWriter xtw, ObjectNode modelNode) throws Exception {
    
    boolean attachedToRefWritten = false;
    for (JsonNode childNode : modelNode.get(EDITOR_CHILD_SHAPES)) {
      
      ArrayNode outgoingNode = (ArrayNode) childNode.get("outgoing");
      if (outgoingNode != null && outgoingNode.size() > 0) {
        for (JsonNode outgoingChildNode : outgoingNode) {
          JsonNode resourceNode = outgoingChildNode.get(EDITOR_SHAPE_ID);
          
          if (resourceNode != null && objectNode.get(EDITOR_SHAPE_ID).asText().equals(resourceNode.asText())) {
            xtw.writeAttribute("attachedToRef", childNode.get(EDITOR_SHAPE_ID).asText());
            attachedToRefWritten = true;
            break;
          }
        }
        
        if (attachedToRefWritten) {
          break;
        }
      }
    }
    
    super.writeAdditionalAttributes(objectNode, xtw, modelNode);
  }



  protected void writeAdditionalChildElements(ObjectNode objectNode, 
      IndentingXMLStreamWriter xtw, ObjectNode modelNode) throws Exception {
    
  	String stencilId = getStencilId(objectNode);
    
    if (STENCIL_EVENT_BOUNDARY_TIMER.equals(stencilId)) {
    	writeTimerDefinition(objectNode, xtw);
    
    } else if (STENCIL_EVENT_BOUNDARY_ERROR.equals(stencilId)) {
    	writeErrorDefinition(objectNode, xtw);
      
    } else if (STENCIL_EVENT_BOUNDARY_SIGNAL.equals(stencilId)) {
      writeSignalDefinition(objectNode, xtw);
    }
    
    super.writeAdditionalChildElements(objectNode, xtw, modelNode);
  }

}
