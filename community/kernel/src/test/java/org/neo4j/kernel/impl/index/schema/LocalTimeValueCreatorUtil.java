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

import java.time.LocalTime;

import org.neo4j.kernel.api.index.IndexEntryUpdate;
import org.neo4j.storageengine.api.schema.IndexDescriptor;
import org.neo4j.storageengine.api.schema.StoreIndexDescriptor;
import org.neo4j.values.storable.RandomValues;
import org.neo4j.values.storable.ValueGroup;
import org.neo4j.values.storable.Values;

public class LocalTimeValueCreatorUtil extends ValueCreatorUtil<LocalTimeIndexKey,NativeIndexValue>
{
    private static final LocalTime[] ALL_EXTREME_VALUES = new LocalTime[]
    {
            LocalTime.of(0, 0, 0,  0),
            LocalTime.of(0,0,0,1 ),
            LocalTime.of(0,0,0,2 ),
            LocalTime.of(0,0,0,3 ),
            LocalTime.of(23,59,59,999_999_998 ),
            LocalTime.of(23,59,59,999_999_999 )
    };

    LocalTimeValueCreatorUtil( StoreIndexDescriptor indexDescriptor )
    {
        super( indexDescriptor );
    }

    @Override
    IndexEntryUpdate<IndexDescriptor>[] someUpdates()
    {
        return someUpdatesWithDuplicateValues();
    }

    @Override
    RandomValues.Type[] supportedTypes()
    {
        return RandomValues.typesOfGroup( ValueGroup.LOCAL_TIME );
    }

    @Override
    int compareIndexedPropertyValue( LocalTimeIndexKey key1, LocalTimeIndexKey key2 )
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