/*
 * Apfloat arbitrary precision arithmetic library
 * Copyright (C) 2002-2017  Mikko Tommila
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.apfloat.internal;

import org.apfloat.ApfloatRuntimeException;
import org.apfloat.spi.DataStorage;

/**
 * Default data storage creation strategy for the <code>float</code> data type.
 *
 * @see FloatMemoryDataStorage
 * @see FloatDiskDataStorage
 *
 * @version 1.8.2
 * @author Mikko Tommila
 */

public class FloatDataStorageBuilder
    extends AbstractDataStorageBuilder
{
    /**
     * Default constructor.
     */

    public FloatDataStorageBuilder()
    {
    }

    @Override
    protected long getMaxCachedSize()
    {
        return (long) 4 * Integer.MAX_VALUE;
    }

    @Override
    protected DataStorage createCachedDataStorage()
        throws ApfloatRuntimeException
    {
        return new FloatMemoryDataStorage();
    }

    @Override
    protected DataStorage createNonCachedDataStorage()
        throws ApfloatRuntimeException
    {
        return new FloatDiskDataStorage();
    }

    @Override
    protected boolean isCached(DataStorage dataStorage)
        throws ApfloatRuntimeException
    {
        return (dataStorage instanceof FloatMemoryDataStorage);
    }
}
