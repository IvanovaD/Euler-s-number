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

import org.apfloat.ApfloatContext;
import org.apfloat.spi.BuilderFactory;
import org.apfloat.spi.NTTBuilder;
import org.apfloat.spi.NTTStrategy;
import org.apfloat.spi.Util;

/**
 * Abstract base class for creating Number Theoretic Transforms suitable for the
 * specified length, based on available memory configured in the {@link ApfloatContext}.
 *
 * @since 1.7.0
 * @version 1.8.3
 * @author Mikko Tommila
 */

public abstract class AbstractNTTBuilder
    implements NTTBuilder
{
    /**
     * Subclass constructor.
     */

    protected AbstractNTTBuilder()
    {
    }

    public NTTStrategy createNTT(long size)
    {
        ApfloatContext ctx = ApfloatContext.getContext();
        BuilderFactory builderFactory = ctx.getBuilderFactory();
        int cacheSize = ctx.getCacheL1Size() / builderFactory.getElementSize();
        long maxMemoryBlockSize = ctx.getMaxMemoryBlockSize() / builderFactory.getElementSize();

        NTTStrategy nttStrategy;
        boolean useFactor3 = false;

        size = Util.round23up(size);        // Round up to the nearest power of two or three times a power of two
        long power2size = (size & -size);   // Power-of-two factor of the above
        if (size != power2size)
        {
            // A factor of three will be used, so the power-of-two part is one third of the whole transform length
            useFactor3 = true;
        }

        // Select transform for the power-of-two part
        if (power2size <= cacheSize / 2)
        {
            // The whole transform plus w-table fits into the cache, so use the simplest approach
            nttStrategy = createSimpleFNTStrategy(power2size);
        }
        else if (power2size <= maxMemoryBlockSize && power2size <= Integer.MAX_VALUE)
        {
            // The whole transform fits into the available main memory, so use a six-step in-memory approach
            nttStrategy = createSixStepFNTStrategy(power2size);
        }
        else
        {
            // The whole transform won't fit into available memory, so use a two-pass disk based approach
            nttStrategy = createTwoPassFNTStrategy(power2size);
        }

        if (useFactor3)
        {
            // Allow using a factor of three in any of the above selected transforms
            nttStrategy = createFactor3NTTStrategy(size, nttStrategy);
        }

        return nttStrategy;
    }

    /**
     * Create a simple NTT strategy.
     *
     * @param size The transform length that will be used.
     *
     * @return A new simple NTT strategy.
     */

    protected abstract NTTStrategy createSimpleFNTStrategy(long size);

    /**
     * Create a six-step NTT strategy.
     *
     * @param size The transform length that will be used.
     *
     * @return A new six-step NTT strategy.
     */

    protected NTTStrategy createSixStepFNTStrategy(long size)
    {
        return new SixStepFNTStrategy();
    }

    /**
     * Create a two-pass NTT strategy.
     *
     * @param size The transform length that will be used.
     *
     * @return A new two-pass NTT strategy.
     */

    protected NTTStrategy createTwoPassFNTStrategy(long size)
    {
        return new TwoPassFNTStrategy();
    }

    /**
     * Create a factor-3 NTT strategy on top of another NTT strategy.
     *
     * @param size The transform length that will be used.
     * @param nttStrategy The underlying factor-2 NTT strategy.
     *
     * @return A new factor-3 NTT strategy.
     */

    protected NTTStrategy createFactor3NTTStrategy(long size, NTTStrategy nttStrategy)
    {
        return new Factor3NTTStrategy(nttStrategy);
    }
}
