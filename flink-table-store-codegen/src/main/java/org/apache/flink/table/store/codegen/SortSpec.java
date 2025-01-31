/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.store.codegen;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** {@link SortSpec} describes how the data will be sorted. */
public class SortSpec {

    /** SortSpec does not require sort. */
    public static final SortSpec ANY = new SortSpec(new SortFieldSpec[0]);

    private final SortFieldSpec[] fieldSpecs;

    public SortSpec(SortFieldSpec[] fieldSpecs) {
        this.fieldSpecs = fieldSpecs;
    }

    /** Gets all {@link SortFieldSpec} in the SortSpec. */
    public SortFieldSpec[] getFieldSpecs() {
        return fieldSpecs;
    }

    /** Gets {@link SortFieldSpec} of field at given index. */
    public SortFieldSpec getFieldSpec(int index) {
        return fieldSpecs[index];
    }

    /** Gets num of field in the spec. */
    public int getFieldSize() {
        return fieldSpecs.length;
    }

    public static SortSpecBuilder builder() {
        return new SortSpecBuilder();
    }

    /** SortSpec builder. */
    public static class SortSpecBuilder {

        private final List<SortFieldSpec> fieldSpecs = new LinkedList<>();

        public SortSpecBuilder addField(
                int fieldIndex, boolean isAscendingOrder, boolean nullIsLast) {
            fieldSpecs.add(new SortFieldSpec(fieldIndex, isAscendingOrder, nullIsLast));
            return this;
        }

        public SortSpec build() {
            return new SortSpec(fieldSpecs.toArray(new SortFieldSpec[0]));
        }
    }

    /** Sort info for a Field. */
    public static class SortFieldSpec {

        /** 0-based index of field being sorted. */
        private final int fieldIndex;

        /** in ascending order or not. */
        private final boolean isAscendingOrder;

        /** put null at last or not. */
        private final boolean nullIsLast;

        public SortFieldSpec(int fieldIndex, boolean isAscendingOrder, boolean nullIsLast) {
            this.fieldIndex = fieldIndex;
            this.isAscendingOrder = isAscendingOrder;
            this.nullIsLast = nullIsLast;
        }

        public int getFieldIndex() {
            return fieldIndex;
        }

        public boolean getIsAscendingOrder() {
            return isAscendingOrder;
        }

        public boolean getNullIsLast() {
            return nullIsLast;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SortFieldSpec that = (SortFieldSpec) o;
            return fieldIndex == that.fieldIndex
                    && isAscendingOrder == that.isAscendingOrder
                    && nullIsLast == that.nullIsLast;
        }

        @Override
        public int hashCode() {
            return Objects.hash(fieldIndex, isAscendingOrder, nullIsLast);
        }

        @Override
        public String toString() {
            return "SortField{"
                    + "fieldIndex="
                    + fieldIndex
                    + ", isAscendingOrder="
                    + isAscendingOrder
                    + ", nullIsLast="
                    + nullIsLast
                    + '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SortSpec sortSpec = (SortSpec) o;
        return Arrays.equals(fieldSpecs, sortSpec.fieldSpecs);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(fieldSpecs);
    }

    @Override
    public String toString() {
        return "Sort{" + "fields=" + Arrays.toString(fieldSpecs) + '}';
    }
}
