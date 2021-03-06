/*
  Copyright 2013-2019 Wix.com

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

package com.wix.accord.java8.tests

import java.time.temporal.{ChronoUnit, Temporal}
import java.time.{Duration, LocalDateTime, ZoneOffset}

import com.wix.accord.Validator
import com.wix.accord.scalatest.CombinatorTestSpec

class TemporalCombinatorTests extends CombinatorTestSpec {
  import com.wix.accord.java8.{Before, After, Within}

  "Before combinator" should {
    "successfully validate a temporal that represents an instant before the specified temporal" in {
      val left = LocalDateTime.now()
      val right = left.plus( 1L, ChronoUnit.DAYS )
      val validator = new Before( right )
      validator( left ) should be( aSuccess )
    }

    "render a correct rule violation" in {
      val left = LocalDateTime.now()
      val right = left.minus( 1L, ChronoUnit.DAYS )
      val validator = new Before( right )
      validator( left ) should failWith( s"must be before $right" )
    }
  }

  "After combinator" should {
    "successfully validate a temporal that represents an instant before the specified temporal" in {
      val left = LocalDateTime.now()
      val right = left.minus( 1L, ChronoUnit.DAYS )
      val validator = new After( right )
      validator( left ) should be( aSuccess )
    }

    "render a correct rule violation" in {
      val left = LocalDateTime.now()
      val right = left.plus( 1L, ChronoUnit.DAYS )
      val validator = new After( right )
      validator( left ) should failWith( s"must be after $right" )
    }
  }

  "Within combinator" should {
    "successfully validate a temporal that represents an instant within the specified tolerance" in {
      val now = LocalDateTime.now()
      val anHourAgo = now.minus( 1L, ChronoUnit.HOURS )
      val anHourHence = now.plus( 1L, ChronoUnit.HOURS )
      val validator = new Within( now, Duration.ofDays( 1 ), "1 days" )
      validator( anHourAgo ) should be( aSuccess )
      validator( anHourHence ) should be( aSuccess )
    }

    "render a correct rule violation" in {
      val now = LocalDateTime.now()
      val aWeekAgo = now.minus( 1L, ChronoUnit.WEEKS )
      val aWeekHence = now.plus( 1L, ChronoUnit.WEEKS )
      val validator = new Within( now, Duration.ofDays( 1 ), "1 days" )
      validator( aWeekAgo ) should failWith( s"must be within 1 days of $now" )
      validator( aWeekHence ) should failWith( s"must be within 1 days of $now" )
    }
  }
}

