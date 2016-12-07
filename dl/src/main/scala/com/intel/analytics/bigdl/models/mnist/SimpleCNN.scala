/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.analytics.bigdl.models.mnist

import com.intel.analytics.bigdl.nn._
import com.intel.analytics.bigdl.tensor.Tensor
import com.intel.analytics.bigdl.tensor.TensorNumericMath.TensorNumeric

import scala.reflect.ClassTag

object SimpleCNN {
  val rowN = 28
  val colN = 28
  val featureSize = rowN * colN

  def apply[T: ClassTag](classNum: Int)
    (implicit ev: TensorNumeric[T]): Module[Tensor[T], Tensor[T], T] = {
    val model = Sequential[Tensor[T], Tensor[T], T]()
    model.add(Reshape(Array(1, rowN, colN)))
    model.add(SpatialConvolution(1, 32, 5, 5))
    model.add(Tanh())
    model.add(SpatialMaxPooling(3, 3, 3, 3))
    model.add(SpatialConvolution(32, 64, 5, 5))
    model.add(Tanh())
    model.add(SpatialMaxPooling(2, 2, 2, 2))

    val linearInputNum = 64 * 2 * 2
    val hiddenNum = 200
    model.add(Reshape(Array(linearInputNum)))
    model.add(Linear(linearInputNum, hiddenNum))
    model.add(Tanh())
    model.add(Linear(hiddenNum, classNum))
    model.add(LogSoftMax())
    model
  }
}