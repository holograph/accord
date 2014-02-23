/*
  Copyright 2013-2014 Wix.com

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.wix.accord.tests.dsl

import com.wix.accord.scalatest.ResultMatchers
import org.scalatest.{WordSpec, Matchers}
import com.wix.accord._

class BooleanOpsTests extends WordSpec with Matchers with ResultMatchers {
  import BooleanOpsTests._

  "true" should {
    "succeed on true" in {
      validate( Test( f = true ) )( trueValidator ) should be( aSuccess )
    }
    "fail on false" in {
      validate( Test( f = false ) )( trueValidator ) should be( aFailure )
    }
  }

  "false" should {
    "succeed on false" in {
      validate( Test( f = false ) )( falseValidator ) should be( aSuccess )
    }
    "fail on true" in {
      validate( Test( f = true ) )( falseValidator ) should be( aFailure )
    }
  }
}

object BooleanOpsTests {
  case class Test( f: Boolean )

  import com.wix.accord.dsl._
  val trueValidator = validator[ Test ] {  _.f is true }
  val falseValidator = validator[ Test ] {  _.f is false }
}