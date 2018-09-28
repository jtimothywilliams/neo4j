/*
 * Copyright (c) 2002-2018 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.index.schema;

import org.apache.commons.lang3.ArrayUtils;

import org.neo4j.kernel.api.index.IndexEntryUpdate;
import org.neo4j.storageengine.api.schema.IndexDescriptor;
import org.neo4j.storageengine.api.schema.StoreIndexDescriptor;
import org.neo4j.values.storable.DurationValue;
import org.neo4j.values.storable.RandomValues;
import org.neo4j.values.storable.ValueGroup;
import org.neo4j.values.storable.Values;

public class DurationValueCreatorUtil extends ValueCreatorUtil<DurationIndexKey,NativeIndexValue>
{
    private static final DurationValue[] ALL_EXTREME_VALUES = new DurationValue[]
    {
            DurationValue.duration( -999999999L * 12 * 2, 0, 0, 0),
            DurationValue.duration( 999999999L * 12 * 2, 0, 0, 0),
            DurationValue.duration( 0, -999999999L * 12 * 28, 0, 0),
            DurationValue.duration( 0, 999999999L * 12 * 28, 0, 0),
            DurationValue.duration( 0, 0, Long.MIN_VALUE, 0),
            DurationValue.duration( 0, 0, Long.MAX_VALUE, 0),
            DurationValue.duration( 0, 0, 0, Long.MIN_VALUE),
            DurationValue.duration( 0, 0, 0, Long.MAX_VALUE),
    };

    DurationValueCreatorUtil( StoreIndexDescriptor schemaIndexDescriptor )
    {
        super( schemaIndexDescriptor );
    }

    @Override
    IndexEntryUpdate<IndexDescriptor>[] someUpdates()
    {
        return someUpdatesWithDuplicateValues();
    }

    @Override
    RandomValues.Type[] supportedTypes()
    {
        return RandomValues.typesOfGroup( ValueGroup.DURATION );
    }

    @Override
    int compareIndexedPropertyValue( DurationIndexKey key1, DurationIndexKey key2 )
    {
        return Values.COMPARATOR.compare( key1.asValue(), key2.asValue() );
    }

    @Override
    IndexEntryUpdate<IndexDescriptor>[] someUpdatesNoDuplicateValues()
    {
        return generateAddUpdatesFor( ALL_EXTREME_VALUES );
    }

    @Override
    IndexEntryUpdate<IndexDescriptor>[] someUpdatesWithDuplicateValues()
    {
        return generateAddUpdatesFor( ArrayUtils.addAll( ALL_EXTREME_VALUES, ALL_EXTREME_VALUES ) );
    }
}