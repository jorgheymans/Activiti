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

package org.activiti.engine.impl.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.activiti.engine.impl.util.ClassNameUtil;


/**
 * @author Tom Baeyens
 */
public class PvmTestCase extends TestCase {

  protected static final String EMPTY_LINE = "                                                                                           ";

  protected static Logger log = LoggerFactory.getLogger(PvmTestCase.class.getName());

  protected boolean isEmptyLinesEnabled = true;

  public void assertTextPresent(String expected, String actual) {
    if ( (actual==null)
         || (actual.indexOf(expected)==-1)
       ) {
      throw new AssertionFailedError("expected presence of ["+expected+"], but was ["+actual+"]");
    }
  }
  
  @Override
  protected void runTest() throws Throwable {

    if (log.isDebugEnabled()) {
      if (isEmptyLinesEnabled) {
        log.debug(EMPTY_LINE);
      }
      log.debug("#### START " + ClassNameUtil.getClassNameWithoutPackage(this) + "." + getName() + " ###########################################################");
    }

    try {
      
      super.runTest();

    }  catch (AssertionFailedError e) {
      log.error(EMPTY_LINE);
      log.error("ASSERTION FAILED: " + e, e);
      throw e;
      
    } catch (Throwable e) {
      log.error(EMPTY_LINE);
      log.error("EXCEPTION: " + e, e);
      throw e;
      
    } finally {
      log.debug("#### END " + ClassNameUtil.getClassNameWithoutPackage(this) + "." + getName() + " #############################################################");
    }
  }

}
